
        package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class UserManagementView extends JFrame {
    public JTable userTable;
    public JTextField userIdField;
    public JTextField firstNameField;
    public JTextField lastNameField;
    public JTextField emailField;
    public JTextField birthDateField;
    public JPasswordField passwordField;
    public JCheckBox isAdminCheckBox;
    public JButton updateUserButton;
    public JButton backButton;

    public UserManagementView() {
        setTitle("Manage Users");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));


        String[] columns = {"User ID", "First Name", "Last Name", "Email", "Birth Date", "Is Admin"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        userTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(userTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);


        JPanel formPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        formPanel.add(new JLabel("User ID:"));
        userIdField = new JTextField();
        userIdField.setEditable(false);
        formPanel.add(userIdField);

        formPanel.add(new JLabel("First Name:"));
        firstNameField = new JTextField();
        formPanel.add(firstNameField);

        formPanel.add(new JLabel("Last Name:"));
        lastNameField = new JTextField();
        formPanel.add(lastNameField);

        formPanel.add(new JLabel("Email:"));
        emailField = new JTextField();
        formPanel.add(emailField);

        formPanel.add(new JLabel("Birth Date (yyyy-MM-dd):"));
        birthDateField = new JTextField();
        formPanel.add(birthDateField);

        formPanel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        formPanel.add(passwordField);

        formPanel.add(new JLabel("Is Admin:"));
        isAdminCheckBox = new JCheckBox();
        formPanel.add(isAdminCheckBox);

        mainPanel.add(formPanel, BorderLayout.NORTH);


        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        updateUserButton = new JButton("Update User");
        backButton = new JButton("Back");
        buttonPanel.add(updateUserButton);
        buttonPanel.add(backButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);


        userTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = userTable.getSelectedRow();
                if (selectedRow != -1) {
                    userIdField.setText(userTable.getValueAt(selectedRow, 0).toString());
                    firstNameField.setText(userTable.getValueAt(selectedRow, 1).toString());
                    lastNameField.setText(userTable.getValueAt(selectedRow, 2).toString());
                    emailField.setText(userTable.getValueAt(selectedRow, 3).toString());
                    birthDateField.setText(userTable.getValueAt(selectedRow, 4) != null ? userTable.getValueAt(selectedRow, 4).toString() : "");
                    isAdminCheckBox.setSelected(Boolean.parseBoolean(userTable.getValueAt(selectedRow, 5).toString()));
                    passwordField.setText("");
                }
            }
        });
    }
}
