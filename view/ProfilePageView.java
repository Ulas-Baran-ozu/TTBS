package view;

import javax.swing.*;
import java.awt.*;

public class ProfilePageView extends JFrame {
    public JTextField firstNameField, lastNameField, emailField;
    public JPasswordField passwordField;
    public JTextField birthDateField;
    public JButton updateButton, backButton;

    public ProfilePageView() {
        setTitle("Profilim");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel infoPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        firstNameField = new JTextField();
        lastNameField = new JTextField();
        emailField = new JTextField();
        birthDateField = new JTextField();
        passwordField = new JPasswordField();

        infoPanel.add(new JLabel("Ad:"));
        infoPanel.add(firstNameField);
        infoPanel.add(new JLabel("Soyad:"));
        infoPanel.add(lastNameField);
        infoPanel.add(new JLabel("E-Posta:"));
        infoPanel.add(emailField);
        infoPanel.add(new JLabel("Doğum Tarihi (yyyy-MM-dd):"));
        infoPanel.add(birthDateField);
        infoPanel.add(new JLabel("Şifre:"));
        infoPanel.add(passwordField);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        updateButton = new JButton("Güncelle");
        backButton = new JButton("Ana Sayfa");
        buttonPanel.add(updateButton);
        buttonPanel.add(backButton);

        setLayout(new BorderLayout());
        add(infoPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}