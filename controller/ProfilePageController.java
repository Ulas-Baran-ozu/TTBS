package controller;

import view.ProfilePageView;
import view.HomePageView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProfilePageController {
    private ProfilePageView view;

    public ProfilePageController(ProfilePageView view) {
        this.view = view;


        view.updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = view.nameField.getText();
                String email = view.emailField.getText();
                String phone = view.phoneField.getText();
                System.out.println("Updated Info:");
                System.out.println("Name: " + name);
                System.out.println("Email: " + email);
                System.out.println("Phone: " + phone);
                JOptionPane.showMessageDialog(view, "Information updated successfully.");
            }
        });

        view.addBalanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                while (true) {
                    String input = JOptionPane.showInputDialog(view, "Enter the amount to add:");
                    if (input == null) break;

                    input = input.trim();
                    if (input.matches("\\d+(\\.\\d{1,2})?")) {
                        double currentBalance = Double.parseDouble(view.balanceField.getText().replace(" TL", "").trim());
                        double add = Double.parseDouble(input);
                        double updated = currentBalance + add;
                        view.balanceField.setText(String.format("%.2f TL", updated));
                        JOptionPane.showMessageDialog(view, "New balance: " + updated + " TL");
                        break;
                    } else {
                        JOptionPane.showMessageDialog(view, "Please enter a valid numeric amount (e.g. 100 or 25.50).", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                    }
                }
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
