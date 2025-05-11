package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Kullanıcı profil sayfası görünümü
 */
public class ProfilePageView extends JFrame {
    // Kullanıcı bilgi alanları
    public JTextField firstNameField;
    public JTextField lastNameField;
    public JTextField emailField;
    public JPasswordField passwordField;
    public JPasswordField confirmPasswordField;
    public JTextField birthDateField;

    // Butonlar
    public JButton saveButton;
    public JButton backButton;
    public JButton changePasswordButton;

    public ProfilePageView() {
        setTitle("My Profile");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Ana panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Başlık
        JLabel titleLabel = new JLabel("My Profile", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Form paneli
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));

        // Ad alanı
        formPanel.add(new JLabel("First Name:"));
        firstNameField = new JTextField();
        formPanel.add(firstNameField);

        // Soyad alanı
        formPanel.add(new JLabel("Last Name:"));
        lastNameField = new JTextField();
        formPanel.add(lastNameField);

        // Email alanı
        formPanel.add(new JLabel("Email:"));
        emailField = new JTextField();
        formPanel.add(emailField);

        // Doğum tarihi alanı
        formPanel.add(new JLabel("Birth Date (yyyy-MM-dd):"));
        birthDateField = new JTextField();
        formPanel.add(birthDateField);

        // Şifre değiştirme butonu
        formPanel.add(new JLabel("Password:"));
        JPanel passwordPanel = new JPanel(new BorderLayout());
        passwordField = new JPasswordField();
        passwordField.setEnabled(false); // Varsayılan olarak devre dışı
        passwordPanel.add(passwordField, BorderLayout.CENTER);
        changePasswordButton = new JButton("Change");
        passwordPanel.add(changePasswordButton, BorderLayout.EAST);
        formPanel.add(passwordPanel);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Buton paneli
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        backButton = new JButton("Back");
        buttonPanel.add(backButton);

        saveButton = new JButton("Save Changes");
        buttonPanel.add(saveButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Şifre değiştirme alanı (başlangıçta gizli)
        confirmPasswordField = new JPasswordField();

        add(mainPanel);
    }

    /**
     * Kullanıcı verilerini form alanlarına doldurur
     */
    public void setUserData(String firstName, String lastName, String email, Date birthDate) {
        firstNameField.setText(firstName);
        lastNameField.setText(lastName);
        emailField.setText(email);

        if (birthDate != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            birthDateField.setText(dateFormat.format(birthDate));
        } else {
            birthDateField.setText("");
        }
    }

    /**
     * Şifre değiştirme dialogunu gösterir
     */
    public void showChangePasswordDialog(ActionListener savePasswordListener) {
        JDialog passwordDialog = new JDialog(this, "Change Password", true);
        passwordDialog.setSize(300, 200);
        passwordDialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPasswordField currentPassword = new JPasswordField();
        JPasswordField newPassword = new JPasswordField();
        JPasswordField confirmPassword = new JPasswordField();

        panel.add(new JLabel("Current Password:"));
        panel.add(currentPassword);
        panel.add(new JLabel("New Password:"));
        panel.add(newPassword);
        panel.add(new JLabel("Confirm Password:"));
        panel.add(confirmPassword);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton cancelButton = new JButton("Cancel");
        JButton saveButton = new JButton("Save");

        cancelButton.addActionListener(e -> passwordDialog.dispose());

        saveButton.addActionListener(e -> {
            String current = new String(currentPassword.getPassword());
            String newPass = new String(newPassword.getPassword());
            String confirm = new String(confirmPassword.getPassword());

            if (current.isEmpty() || newPass.isEmpty() || confirm.isEmpty()) {
                JOptionPane.showMessageDialog(passwordDialog,
                        "All fields are required!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!newPass.equals(confirm)) {
                JOptionPane.showMessageDialog(passwordDialog,
                        "New passwords do not match!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Şifreleri controller'a gönder
            // savePasswordListener aksiyon olayına mevcut şifre ve yeni şifre bilgilerini ekle
            ActionListener[] listeners = saveButton.getActionListeners();
            for (ActionListener listener : listeners) {
                saveButton.removeActionListener(listener);
            }

            saveButton.addActionListener(savePasswordListener);
            saveButton.doClick();

            passwordDialog.dispose();
        });

        buttonPanel.add(cancelButton);
        buttonPanel.add(saveButton);

        passwordDialog.setLayout(new BorderLayout());
        passwordDialog.add(panel, BorderLayout.CENTER);
        passwordDialog.add(buttonPanel, BorderLayout.SOUTH);

        passwordDialog.setVisible(true);
    }

    /**
     * Başarı mesajı gösterir
     */
    public void showSuccessMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Hata mesajı gösterir
     */
    public void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}