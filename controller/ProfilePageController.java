package controller;

import model.Session;
import model.entities.User;
import view.ProfilePageView;
import view.HomePageView;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static database.DBConnection.getConnection;

public class ProfilePageController {
    private final ProfilePageView view;

    public ProfilePageController(ProfilePageView view) {
        this.view = view;
        User currentUser = Session.getCurrentUser();

        // Alanları doldur
        view.firstNameField.setText(currentUser.getFirstName());
        view.lastNameField.setText(currentUser.getLastName());
        view.emailField.setText(currentUser.getEmail());
        view.birthDateField.setText(currentUser.getBirthDate() != null ? currentUser.getBirthDate().toString() : "");
        view.passwordField.setText(currentUser.getPassword());

        view.updateButton.addActionListener(e -> {
            String firstName = view.firstNameField.getText().trim();
            String lastName = view.lastNameField.getText().trim();
            String email = view.emailField.getText().trim();
            String birthDate = view.birthDateField.getText().trim();
            String password = new String(view.passwordField.getPassword()).trim();

            if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || birthDate.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Lütfen tüm alanları doldurun.", "Uyarı", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try (Connection conn = getConnection()) {
                String updateSQL = "UPDATE users SET first_name=?, last_name=?, email=?, birth_date=?, password=? WHERE user_id=?";
                try (PreparedStatement stmt = conn.prepareStatement(updateSQL)) {
                    stmt.setString(1, firstName);
                    stmt.setString(2, lastName);
                    stmt.setString(3, email);
                    stmt.setString(4, birthDate); // doğrudan string verildi (yyyy-MM-dd)
                    stmt.setString(5, password);
                    stmt.setInt(6, currentUser.getUserId());
                    stmt.executeUpdate();
                }

                // Session güncelle
                currentUser.setFirstName(firstName);
                currentUser.setLastName(lastName);
                currentUser.setEmail(email);
                currentUser.setBirthDate(java.sql.Date.valueOf(birthDate));
                currentUser.setPassword(password);

                JOptionPane.showMessageDialog(view, "Bilgiler başarıyla güncellendi.");
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(view, "Güncelleme sırasında hata oluştu.", "Hata", JOptionPane.ERROR_MESSAGE);
            }
        });

        view.backButton.addActionListener(e -> {
            view.dispose();
            HomePageView homeView = new HomePageView();
            new HomePageController(homeView);
            homeView.setVisible(true);
        });
    }
}