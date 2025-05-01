package controller;

//import model.User;
import view.HomePageView;
import view.LoginView;
import view.RegisterView;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginController {
    private LoginView loginView;

    public LoginController(LoginView loginView) {
        this.loginView = loginView;

        loginView.loginButton.addActionListener(e -> login());
        loginView.registerLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                openRegister();
            }
        });
    }

    private void login() {
        String email = loginView.emailField.getText();
        String password = new String(loginView.passwordField.getPassword());

        // Database classları geldikten sonra tekrardan yazılacaktır.
        if (email.equals("admin") && password.equals("admin")) {
            JOptionPane.showMessageDialog(loginView, "Login Successful!");
            loginView.dispose();  // Login ekranını kapat
            HomePageView homePageView = new HomePageView();  // Home Page aç
            new HomePageController(homePageView);  // Controller bağla
            homePageView.setVisible(true);  // Home Page görünür yap
        } else {
            JOptionPane.showMessageDialog(loginView, "Invalid credentials.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void openRegister() {
        loginView.dispose();
        RegisterView registerView = new RegisterView();
        new RegisterController(registerView);
        registerView.setVisible(true);
    }
}
