package view;

import javax.swing.*;
import java.awt.*;

public class ProfilePageView extends JFrame {
    public JTextField nameField, emailField, phoneField, balanceField;
    public JButton updateButton, addBalanceButton, backButton;

    public ProfilePageView() {
        setTitle("My Profile");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);


        JPanel infoPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        nameField = new JTextField();
        emailField = new JTextField();
        phoneField = new JTextField();
        balanceField = new JTextField();
        balanceField.setEditable(false);

        infoPanel.add(new JLabel("Name Surname:"));
        infoPanel.add(nameField);
        infoPanel.add(new JLabel("Email:"));
        infoPanel.add(emailField);
        infoPanel.add(new JLabel("Phone No:"));
        infoPanel.add(phoneField);
        infoPanel.add(new JLabel("Balance:"));
        infoPanel.add(balanceField);


        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        updateButton = new JButton("Update");
        addBalanceButton = new JButton("Add Balance");
        backButton = new JButton("Home");
        buttonPanel.add(updateButton);
        buttonPanel.add(addBalanceButton);
        buttonPanel.add(backButton);

        setLayout(new BorderLayout());
        add(infoPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}
