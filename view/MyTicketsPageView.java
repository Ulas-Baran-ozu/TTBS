package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class MyTicketsPageView extends JFrame {
    public JTable ticketTable;
    public JButton cancelButton, backButton;

    public MyTicketsPageView() {
        setTitle("My Tickets");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        String[] columns = {"Ticket ID", "Train", "Date", "From", "To", "Seat No"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        ticketTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(ticketTable);

        JPanel buttonPanel = new JPanel();
        cancelButton = new JButton("Cancel Ticket");
        backButton = new JButton("Back to Home");
        buttonPanel.add(cancelButton);
        buttonPanel.add(backButton);

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}
