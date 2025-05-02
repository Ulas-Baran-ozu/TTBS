package view;

import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {
    public JTextField emailField;
    public JPasswordField passwordField;
    public JButton loginButton;
    public JLabel registerLabel;

    public LoginView() {
        setTitle("Login");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 1));

        emailField = new JTextField();
        passwordField = new JPasswordField();
        loginButton = new JButton("Login");
        registerLabel = new JLabel("No account? Register here.", SwingConstants.CENTER);
        registerLabel.setForeground(Color.BLUE);

        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        panel.add(registerLabel);
        panel.add(loginButton);

        add(panel);
    }
}
