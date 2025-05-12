// MyTicketsPageView.java
package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class MyTicketsPageView extends JFrame {
    public JTable ticketTable;
    public JButton cancelButton, backButton;

    public MyTicketsPageView() {
        setTitle("Biletlerim");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        String[] columns = {"Bilet ID", "Tren Adı", "Tarih", "Kalkış", "Varış", "Koltuk"};
        ticketTable = new JTable(new DefaultTableModel(columns, 0));
        JScrollPane scrollPane = new JScrollPane(ticketTable);

        cancelButton = new JButton("Bileti İptal Et");
        backButton = new JButton("Ana Sayfaya Dön");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(cancelButton);
        buttonPanel.add(backButton);

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}