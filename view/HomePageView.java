package view;

import javax.swing.*;
import java.awt.*;

public class HomePageView extends JFrame {
    public JButton profileButton, ticketsButton, logoutButton;
    public JButton searchButton;
    public JTextField departureField, arrivalField, dateField;
    public JSpinner ticketCountSpinner;
    public JRadioButton oneWayRadio, roundTripRadio;
    public ButtonGroup tripTypeGroup;

    public HomePageView() {
        setTitle("Home Page");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        profileButton = new JButton("My Profile");
        ticketsButton = new JButton("My Tickets");
        logoutButton = new JButton("Exit");
        topPanel.add(profileButton);
        topPanel.add(ticketsButton);
        topPanel.add(logoutButton);

        JPanel centerPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        ticketCountSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
        departureField = new JTextField();
        arrivalField = new JTextField();
        oneWayRadio = new JRadioButton("One-Way");
        roundTripRadio = new JRadioButton("Round Trip");
        tripTypeGroup = new ButtonGroup();
        tripTypeGroup.add(oneWayRadio);
        tripTypeGroup.add(roundTripRadio);
        dateField = new JTextField();
        searchButton = new JButton("Search");

        centerPanel.add(new JLabel("How Many Tickets:"));
        centerPanel.add(ticketCountSpinner);
        centerPanel.add(new JLabel("Departure:"));
        centerPanel.add(departureField);
        centerPanel.add(new JLabel("Arrival:"));
        centerPanel.add(arrivalField);
        centerPanel.add(oneWayRadio);
        centerPanel.add(roundTripRadio);
        centerPanel.add(new JLabel("Date (YYYY-MM-DD):"));
        centerPanel.add(dateField);
        centerPanel.add(new JLabel(""));
        centerPanel.add(searchButton);

        setLayout(new BorderLayout());
        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        oneWayRadio.setSelected(true); // eklendi varsayılan olarak tekyön seçili
        setVisible(true);              // eklendi pencereyi görünür hale getir
    }
}
