
        package view;

import javax.swing.*;
import java.awt.*;

public class AdminHomePageView extends JFrame {
    public JButton routeManagementButton;
    public JButton userManagementButton;
    public JButton logoutButton;

    public AdminHomePageView() {
        setTitle("Admin Dashboard");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Admin Dashboard", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(titleLabel);

        routeManagementButton = new JButton("Manage Routes");
        panel.add(routeManagementButton);

        userManagementButton = new JButton("Manage Users");
        panel.add(userManagementButton);

        logoutButton = new JButton("Logout");
        panel.add(logoutButton);

        add(panel);
    }
}
