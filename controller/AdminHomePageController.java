
package controller;

import model.Session;
import view.AdminHomePageView;
import view.LoginView;
import view.RouteManagementView;
import view.UserManagementView;

import javax.swing.*;

public class AdminHomePageController {
    private AdminHomePageView view;

    public AdminHomePageController(AdminHomePageView view) {
        this.view = view;

        view.routeManagementButton.addActionListener(e -> {
            view.dispose();
            RouteManagementView routeManagementView = new RouteManagementView();
            new RouteManagementController(routeManagementView);
            routeManagementView.setVisible(true);
        });

        view.userManagementButton.addActionListener(e -> {
            view.dispose();
            UserManagementView userManagementView = new UserManagementView();
            new UserManagementController(userManagementView);
            userManagementView.setVisible(true);
        });

        view.logoutButton.addActionListener(e -> logout());
    }

    private void logout() {
        JOptionPane.showMessageDialog(view, "Çıkış yapılıyor.");
        Session.clear();
        view.dispose();
        LoginView loginView = new LoginView();
        new LoginController(loginView);
        loginView.setVisible(true);
    }
}
