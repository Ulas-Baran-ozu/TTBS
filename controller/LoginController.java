package controller;

import database.dao.UserDAO;
import database.dao.UserDAOImpl;
import model.Session;
import model.entities.User;
import view.HomePageView;
import view.LoginView;
import view.RegisterView;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

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

        UserDAO userDAO = new UserDAOImpl();
        try {
            User user = userDAO.getByEmailAndPassword(email, password);

            if (user != null) {
                JOptionPane.showMessageDialog(loginView, "Giriş başarılı! Hoş geldin, " + user.getFirstName() + "!");
                loginView.dispose();
                Session.setCurrentUser(user);

                HomePageView homePageView = new HomePageView();
                new HomePageController(homePageView);
                homePageView.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(loginView, "Geçersiz kullanıcı bilgileri.", "Hata", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(loginView, "Veritabanı hatası: " + e.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void openRegister() {
        loginView.dispose();
        RegisterView registerView = new RegisterView();
        new RegisterController(registerView);
        registerView.setVisible(true);
    }
}
