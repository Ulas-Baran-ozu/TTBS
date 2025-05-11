package controller;

import view.ProfilePageView;
import view.HomePageView;
import model.Session;
import model.entities.User;
import database.dao.UserDAO;

import database.dao.UserDAOImpl;



import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Profil sayfası kontrolcüsü
 */
public class ProfilePageController {
    private ProfilePageView view;
    private User currentUser;
    private UserDAO userDAO;

    public ProfilePageController(ProfilePageView view) {
        this.view = view;
        this.userDAO = new UserDAOImpl();

        // Mevcut kullanıcıyı al
        this.currentUser = Session.getCurrentUser();

        if (currentUser == null) {
            // Eğer kullanıcı oturum açmamışsa, login sayfasına yönlendir
            JOptionPane.showMessageDialog(view, "Session expired. Please login again.", "Session Error", JOptionPane.ERROR_MESSAGE);
            view.dispose();
            view.LoginView loginView = new view.LoginView();
            new LoginController(loginView);
            loginView.setVisible(true);
            return;
        }

        // Kullanıcı bilgilerini form alanlarına doldur
        view.setUserData(
                currentUser.getFirstName(),
                currentUser.getLastName(),
                currentUser.getEmail(),
                currentUser.getBirthDate()
        );

        // Buton olaylarını ayarla
        view.saveButton.addActionListener(e -> saveUserProfile());
        view.backButton.addActionListener(e -> goBack());
        view.changePasswordButton.addActionListener(e -> showChangePasswordDialog());
    }

    /**
     * Kullanıcı profilini kaydeder
     */
    private void saveUserProfile() {
        try {
            // Form alanlarından verileri al
            String firstName = view.firstNameField.getText().trim();
            String lastName = view.lastNameField.getText().trim();
            String email = view.emailField.getText().trim();
            String birthDateStr = view.birthDateField.getText().trim();

            // Validasyon
            if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty()) {
                view.showErrorMessage("First name, last name and email are required!");
                return;
            }

            // Email formatını kontrol et
            if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                view.showErrorMessage("Please enter a valid email address!");
                return;
            }

            // Tarih formatını kontrol et
            Date birthDate = null;
            if (!birthDateStr.isEmpty()) {
                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    dateFormat.setLenient(false);
                    birthDate = dateFormat.parse(birthDateStr);
                } catch (ParseException e) {
                    view.showErrorMessage("Please enter a valid birth date in format yyyy-MM-dd!");
                    return;
                }
            }

            // Kullanıcı nesnesini güncelle
            currentUser.setFirstName(firstName);
            currentUser.setLastName(lastName);
            currentUser.setEmail(email);
            currentUser.setBirthDate(new java.sql.Date(birthDate.getTime()));  // dönüşüm yapılmış oldu


            // Veritabanında güncelle
            userDAO.update(currentUser);

            // Session'daki kullanıcıyı da güncelle
            Session.setCurrentUser(currentUser);

            view.showSuccessMessage("Profile updated successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
            view.showErrorMessage("Failed to update profile: " + e.getMessage());
        }
    }

    /**
     * Ana sayfaya geri döner
     */
    private void goBack() {
        view.dispose();
        HomePageView homeView = new HomePageView();
        new HomePageController(homeView);
        homeView.setVisible(true);
    }

    /**
     * Şifre değiştirme dialogunu gösterir
     */
    private void showChangePasswordDialog() {
        view.showChangePasswordDialog(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Dialog'daki verileri alma işlemi view katmanında yapılıyor
                // ve bu aksiyon listener'a gönderiliyor.
                JButton sourceButton = (JButton) e.getSource();
                JDialog dialog = (JDialog) SwingUtilities.getWindowAncestor(sourceButton);
                JPasswordField currentPasswordField = null;
                JPasswordField newPasswordField = null;
                JPasswordField confirmPasswordField = null;

                for (java.awt.Component comp : dialog.getContentPane().getComponents()) {
                    if (comp instanceof JPanel) {
                        JPanel panel = (JPanel) comp;
                        for (java.awt.Component innerComp : panel.getComponents()) {
                            if (innerComp instanceof JPasswordField) {
                                if (currentPasswordField == null) {
                                    currentPasswordField = (JPasswordField) innerComp;
                                } else if (newPasswordField == null) {
                                    newPasswordField = (JPasswordField) innerComp;
                                } else {
                                    confirmPasswordField = (JPasswordField) innerComp;
                                }
                            }
                        }
                    }
                }

                if (currentPasswordField != null && newPasswordField != null) {
                    String currentPassword = new String(currentPasswordField.getPassword());
                    String newPassword = new String(newPasswordField.getPassword());
                    changePassword(currentPassword, newPassword);
                }
            }
        });
    }

    /**
     * Kullanıcı şifresini değiştirir
     */
    private void changePassword(String currentPassword, String newPassword) {
        try {
            // Mevcut şifreyi kontrol et
            if (!currentUser.getPassword().equals(currentPassword)) {
                view.showErrorMessage("Current password is incorrect!");
                return;
            }

            // Şifreyi güncelle
            currentUser.setPassword(newPassword);
            userDAO.update(currentUser);

            // Session'daki kullanıcıyı da güncelle
            Session.setCurrentUser(currentUser);

            view.showSuccessMessage("Password changed successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
            view.showErrorMessage("Failed to change password: " + e.getMessage());
        }
    }
}