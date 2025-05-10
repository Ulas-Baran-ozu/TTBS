
package controller;

import database.dao.UserDAO;
import database.dao.UserDAOImpl;
import model.entities.User;
import view.AdminHomePageView;
import view.UserManagementView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class UserManagementController {
    private UserManagementView view;
    private UserDAO userDAO;

    public UserManagementController(UserManagementView view) {
        this.view = view;
        this.userDAO = new UserDAOImpl();

        loadUsers();

        view.updateUserButton.addActionListener(e -> updateUser());
        view.backButton.addActionListener(e -> {
            view.dispose();
            AdminHomePageView adminHomePageView = new AdminHomePageView();
            new AdminHomePageController(adminHomePageView);
            adminHomePageView.setVisible(true);
        });
    }

    private void loadUsers() {
        try {
            List<User> users = userDAO.getAll();
            DefaultTableModel model = (DefaultTableModel) view.userTable.getModel();
            model.setRowCount(0);
            for (User user : users) {
                model.addRow(new Object[]{
                        user.getUserId(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getEmail(),
                        user.getBirthDate(),
                        user.isAdmin()
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view, "Failed to load users.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateUser() {
        String userIdStr = view.userIdField.getText().trim();
        String firstName = view.firstNameField.getText().trim();
        String lastName = view.lastNameField.getText().trim();
        String email = view.emailField.getText().trim();
        String birthDateStr = view.birthDateField.getText().trim();
        String password = new String(view.passwordField.getPassword()).trim();
        boolean isAdmin = view.isAdminCheckBox.isSelected();

        if (userIdStr.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Please select a user to update.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(view, "First Name, Last Name, and Email must be filled.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int userId = Integer.parseInt(userIdStr);
            Date sqlBirthDate = null;
            if (!birthDateStr.isEmpty()) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date utilBirthDate = formatter.parse(birthDateStr);
                sqlBirthDate = new Date(utilBirthDate.getTime());
            }

            User user = new User(userId, firstName, lastName, email, null, sqlBirthDate, isAdmin);


            User existingUser = userDAO.getById(userId);
            if (existingUser == null) {
                JOptionPane.showMessageDialog(view, "User not found.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            user.setPassword(!password.isEmpty() ? password : existingUser.getPassword());

            userDAO.update(user);
            JOptionPane.showMessageDialog(view, "User updated successfully.");
            loadUsers();
            clearFields();
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(view, "Invalid birth date format. Use yyyy-MM-dd.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view, "Failed to update user: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        view.userIdField.setText("");
        view.firstNameField.setText("");
        view.lastNameField.setText("");
        view.emailField.setText("");
        view.birthDateField.setText("");
        view.passwordField.setText("");
        view.isAdminCheckBox.setSelected(false);
    }
}
