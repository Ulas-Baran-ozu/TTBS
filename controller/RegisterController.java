package controller;

//import model.User;
import view.LoginView;
import view.RegisterView;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RegisterController {
    private RegisterView registerView;

    public RegisterController(RegisterView registerView) {
        this.registerView = registerView;

        registerView.registerButton.addActionListener(e -> register());
        registerView.loginLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                openLogin();
            }
        });
    }

    private void register() {
        String email = registerView.emailField.getText();
        String password = new String(registerView.passwordField.getPassword());
        String confirmPassword = new String(registerView.confirmPasswordField.getPassword());
        String firstName = registerView.firstNameField.getText();
        String lastName = registerView.lastNameField.getText();

        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(registerView, "Passwords do not match.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (email.isEmpty() || password.isEmpty() || firstName.isEmpty() || lastName.isEmpty()) {
            JOptionPane.showMessageDialog(registerView, "All fields must be filled.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Burada kullanıcı veritabanına kaydedilecek (şu anda sadece mesaj gösteriyoruz)
//        User user = new User(email, password, phone, fullName);
        JOptionPane.showMessageDialog(registerView, "Registration Successful!");

        openLogin();
    }

    private void openLogin() {
        registerView.dispose();
        LoginView loginView = new LoginView();
        new LoginController(loginView);
        loginView.setVisible(true);
    }
}