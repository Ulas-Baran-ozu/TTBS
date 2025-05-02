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
        profileButton = new JButton("Profilim");
        ticketsButton = new JButton("Biletlerim");
        logoutButton = new JButton("Çıkış Yap");
        topPanel.add(profileButton);
        topPanel.add(ticketsButton);
        topPanel.add(logoutButton);

        JPanel centerPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        ticketCountSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
        departureField = new JTextField();
        arrivalField = new JTextField();
        oneWayRadio = new JRadioButton("Tek Yön");
        roundTripRadio = new JRadioButton("Gidiş-Dönüş");
        tripTypeGroup = new ButtonGroup();
        tripTypeGroup.add(oneWayRadio);
        tripTypeGroup.add(roundTripRadio);
        dateField = new JTextField();
        searchButton = new JButton("Ara");

        centerPanel.add(new JLabel("Kaç Bilet:"));
        centerPanel.add(ticketCountSpinner);
        centerPanel.add(new JLabel("Kalkış Noktası:"));
        centerPanel.add(departureField);
        centerPanel.add(new JLabel("Varış Noktası:"));
        centerPanel.add(arrivalField);
        centerPanel.add(oneWayRadio);
        centerPanel.add(roundTripRadio);
        centerPanel.add(new JLabel("Tarih (YYYY-MM-DD):"));
        centerPanel.add(dateField);
        centerPanel.add(new JLabel(""));
        centerPanel.add(searchButton);

        setLayout(new BorderLayout());
        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
    }
}
