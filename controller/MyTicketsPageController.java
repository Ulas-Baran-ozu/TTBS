package controller;

import view.MyTicketsPageView;
import view.HomePageView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class MyTicketsPageController {
    private MyTicketsPageView view;
    private int userId;

    public MyTicketsPageController(MyTicketsPageView view) {
        this.view = view;


        loadTicketsFromDatabase();

        view.cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = view.ticketTable.getSelectedRow();
                if (selectedRow != -1) {
                    int ticketId = (int) view.ticketTable.getValueAt(selectedRow, 0);
                    int confirm = JOptionPane.showConfirmDialog(view, "Are you sure you want to cancel this ticket?", "Confirm", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        cancelTicket(ticketId);
                        ((DefaultTableModel) view.ticketTable.getModel()).removeRow(selectedRow);
                        JOptionPane.showMessageDialog(view, "Ticket cancelled.");
                    }
                } else {
                    JOptionPane.showMessageDialog(view, "Please select a ticket to cancel.", "Warning", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        view.backButton.addActionListener(e -> {
            view.dispose();
            HomePageView homeView = new HomePageView();
            new HomePageController(homeView);
            homeView.setVisible(true);
        });

    }

    private void loadTicketsFromDatabase() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ttbs", "root", "yourpassword");
            String query = "SELECT ticket_id, train_name, travel_date, departure, arrival, seat_no FROM tickets WHERE user_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            DefaultTableModel model = (DefaultTableModel) view.ticketTable.getModel();
            model.setRowCount(0); // Clear previous rows

            while (rs.next()) {
                Object[] row = {
                        rs.getInt("ticket_id"),
                        rs.getString("train_name"),
                        rs.getString("travel_date"),
                        rs.getString("departure"),
                        rs.getString("arrival"),
                        rs.getString("seat_no")
                };
                model.addRow(row);
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(view, "Failed to load tickets.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cancelTicket(int ticketId) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ttbs", "root", "yourpassword");
            String query = "DELETE FROM tickets WHERE ticket_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, ticketId);
            stmt.executeUpdate();
            stmt.close();
            conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(view, "Failed to cancel ticket.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
