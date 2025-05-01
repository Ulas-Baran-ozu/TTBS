package view;

import javax.swing.*;
import java.awt.*;

public class RegisterView extends JFrame {
    public JTextField emailField;
    public JPasswordField passwordField;
    public JPasswordField confirmPasswordField;
    public JTextField phoneField;
    public JTextField fullNameField;
    public JButton registerButton;
    public JLabel loginLabel;

    public RegisterView() {
        setTitle("Register");
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(6, 2));

        emailField = new JTextField();
        passwordField = new JPasswordField();
        confirmPasswordField = new JPasswordField();
        phoneField = new JTextField();
        fullNameField = new JTextField();
        registerButton = new JButton("Register");
        loginLabel = new JLabel("Already have an account? Login.", SwingConstants.CENTER);
        loginLabel.setForeground(Color.BLUE);

        panel.add(new JLabel("Full Name:"));
        panel.add(fullNameField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Phone:"));
        panel.add(phoneField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        panel.add(new JLabel("Confirm Password:"));
        panel.add(confirmPasswordField);
        panel.add(loginLabel);
        panel.add(registerButton);

        add(panel);
    }
}
