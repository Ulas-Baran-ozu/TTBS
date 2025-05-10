
package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class RouteManagementView extends JFrame {
    public JTextField trainNameField;
    public JTextField sourceStationField;
    public JTextField destinationStationField;
    public JTextField departureTimeField;
    public JTextField arrivalTimeField;
    public JButton addRouteButton;
    public JButton deleteRouteButton;
    public JButton backButton;
    public JTable routeTable;

    public RouteManagementView() {
        setTitle("Manage Routes");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));


        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        formPanel.add(new JLabel("Train Name:"));
        trainNameField = new JTextField();
        formPanel.add(trainNameField);

        formPanel.add(new JLabel("Source Station:"));
        sourceStationField = new JTextField();
        formPanel.add(sourceStationField);

        formPanel.add(new JLabel("Destination Station:"));
        destinationStationField = new JTextField();
        formPanel.add(destinationStationField);

        formPanel.add(new JLabel("Departure Time (yyyy-MM-dd HH:mm):"));
        departureTimeField = new JTextField();
        formPanel.add(departureTimeField);

        formPanel.add(new JLabel("Arrival Time (yyyy-MM-dd HH:mm):"));
        arrivalTimeField = new JTextField();
        formPanel.add(arrivalTimeField);

        addRouteButton = new JButton("Add Route");
        formPanel.add(addRouteButton);

        mainPanel.add(formPanel, BorderLayout.NORTH);


        String[] columns = {"Route ID", "Train Name", "Source", "Destination", "Departure", "Arrival"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        routeTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(routeTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);


        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        deleteRouteButton = new JButton("Delete Selected Route");
        backButton = new JButton("Back");
        buttonPanel.add(deleteRouteButton);
        buttonPanel.add(backButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }
}
