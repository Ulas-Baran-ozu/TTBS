package view;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class TrainSearchResultsView extends JFrame {
    public JTable journeyTable;
    public JButton proceedButton;

    public TrainSearchResultsView() {
        setTitle("Train Journeys");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        String[] columns = {"Train Name", "Available/Total", "Departure Time", "Duration", "From" ,"To"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        journeyTable = new JTable(model);

        journeyTable.setFillsViewportHeight(true);
        journeyTable.setRowHeight(25);
        journeyTable.setGridColor(Color.LIGHT_GRAY);
        journeyTable.setShowGrid(true);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < journeyTable.getColumnCount(); i++) {
            journeyTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JTableHeader header = journeyTable.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 14));

        JScrollPane scrollPane = new JScrollPane(journeyTable);
        proceedButton = new JButton("Forward");

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(proceedButton);

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }
}
