package controller;

//import model.User;
import database.dao.UserDAO;
import database.dao.UserDAOImpl;
import model.entities.User;
import view.LoginView;
import view.RegisterView;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

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

    //    private void register() {
//        String email = registerView.emailField.getText();
//        String password = new String(registerView.passwordField.getPassword());
//        String confirmPassword = new String(registerView.confirmPasswordField.getPassword());
//        String firstName = registerView.firstNameField.getText();
//        String lastName = registerView.lastNameField.getText();
//        String birthDateStr = registerView.birthDateField.getText(); // format: yyyy-MM-dd
//
//        if (!password.equals(confirmPassword)) {
//            JOptionPane.showMessageDialog(registerView, "Passwords do not match.", "Error", JOptionPane.ERROR_MESSAGE);
//            return;
//        }
//
//        if (email.isEmpty() || password.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || birthDateStr.isEmpty()) {
//            JOptionPane.showMessageDialog(registerView, "All fields must be filled.", "Error", JOptionPane.ERROR_MESSAGE);
//            return;
//        }
//
//        Date birthDate;
//        try {
//            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//            birthDate = formatter.parse(birthDateStr);
//        } catch (ParseException e) {
//            JOptionPane.showMessageDialog(registerView, "Invalid birth date format. Use yyyy-MM-dd.", "Error", JOptionPane.ERROR_MESSAGE);
//            return;
//        }
//
//        User user = new User(firstName, lastName,  email, password, (java.sql.Date) birthDate);
//        UserDAO userDAO = new UserDAOImpl();
//
//        try {
//            userDAO.add(user);
//            JOptionPane.showMessageDialog(registerView, "Registration Successful!");
//            openLogin();
//        } catch (Exception e) {
//            e.printStackTrace();
//            JOptionPane.showMessageDialog(registerView, "Registration Failed!", "Error", JOptionPane.ERROR_MESSAGE);
//        }
//    }
    private void register() {
        String email = registerView.emailField.getText();
        String password = new String(registerView.passwordField.getPassword());
        String confirmPassword = new String(registerView.confirmPasswordField.getPassword());
        String firstName = registerView.firstNameField.getText();
        String lastName = registerView.lastNameField.getText();
        String birthDateStr = registerView.birthDateField.getText(); // format: yyyy-MM-dd

        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(registerView, "Passwords do not match.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (email.isEmpty() || password.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || birthDateStr.isEmpty()) {
            JOptionPane.showMessageDialog(registerView, "All fields must be filled.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date utilBirthDate = formatter.parse(birthDateStr);
            java.sql.Date sqlBirthDate = new java.sql.Date(utilBirthDate.getTime());

            User user = new User(firstName, lastName, email, password, sqlBirthDate);
            UserDAO userDAO = new UserDAOImpl();

            userDAO.add(user);
            JOptionPane.showMessageDialog(registerView, "Registration Successful!");
            openLogin();
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(registerView, "Invalid birth date format. Use yyyy-MM-dd.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(registerView, "Registration Failed!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void openLogin() {
        registerView.dispose();
        LoginView loginView = new LoginView();
        new LoginController(loginView);
        loginView.setVisible(true);
    }
}