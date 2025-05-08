package view;

import javax.swing.*;
import java.awt.*;

public class RegisterView extends JFrame {
    public JTextField emailField;
    public JPasswordField passwordField;
    public JPasswordField confirmPasswordField;
    public JTextField firstNameField;
    public JTextField lastNameField;
    public JButton registerButton;
    public JLabel loginLabel;
    public JTextField birthDateField;

//    public RegisterView() {
//        setTitle("Register");
//        setSize(300, 300);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setLocationRelativeTo(null);
//
//        JPanel panel = new JPanel(new GridLayout(6, 2));
//
//        emailField = new JTextField();
//        passwordField = new JPasswordField();
//        confirmPasswordField = new JPasswordField();
//        firstNameField = new JTextField();
//        lastNameField = new JTextField();
//        birthDateField = new JTextField();
//
//        registerButton = new JButton("Register");
//        loginLabel = new JLabel("Already have an account? Login.", SwingConstants.CENTER);
//        loginLabel.setForeground(Color.BLUE);
//
//        panel.add(new JLabel("First Name:"));
//        panel.add(firstNameField);
//        panel.add(new JLabel("Last Name:"));
//        panel.add(lastNameField);
//        panel.add(new JLabel("Email:"));
//        panel.add(emailField);
//        panel.add(new JLabel("Birthdate:"));
//        panel.add(birthDateField);
//        panel.add(new JLabel("Password:"));
//        panel.add(passwordField);
//        panel.add(new JLabel("Confirm Password:"));
//        panel.add(confirmPasswordField);
//        panel.add(loginLabel);
//        panel.add(registerButton);
//
//        add(panel);
//    }
public RegisterView() {
    setTitle("Register");
    setSize(400, 400);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);

    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

    panel.add(createLabeledField("First Name:", firstNameField = new JTextField()));
    panel.add(createLabeledField("Last Name:", lastNameField = new JTextField()));
    panel.add(createLabeledField("Email:", emailField = new JTextField()));
    panel.add(createLabeledField("Birthdate (yyyy-MM-dd):", birthDateField = new JTextField()));
    panel.add(createLabeledField("Password:", passwordField = new JPasswordField()));
    panel.add(createLabeledField("Confirm Password:", confirmPasswordField = new JPasswordField()));

    loginLabel = new JLabel("Already have an account? Login.");
    loginLabel.setForeground(Color.BLUE);
    loginLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    panel.add(loginLabel);

    registerButton = new JButton("Register");
    registerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    panel.add(registerButton);

    add(panel);
}

    private JPanel createLabeledField(String labelText, JComponent field) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel(labelText), BorderLayout.WEST);
        panel.add(field, BorderLayout.CENTER);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        return panel;
    }
}
