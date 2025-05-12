package controller;

import database.dao.RouteSeatDAO;
import database.dao.RouteSeatDAOImpl;
import database.dao.TicketDAO;
import database.dao.TicketDAOImpl;
import model.Session;
import model.entities.Ticket;
import model.entities.User;
import view.MyTicketsPageView;
import view.HomePageView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.util.List;

public class MyTicketsPageController {
    private final MyTicketsPageView view;
    private final TicketDAO ticketDAO = new TicketDAOImpl();
    private final RouteSeatDAO routeSeatDAO = new RouteSeatDAOImpl();

    public MyTicketsPageController(MyTicketsPageView view) {
        this.view = view;

        loadTickets();

        view.cancelButton.addActionListener(e -> cancelSelectedTicket());

        view.backButton.addActionListener(e -> {
            view.dispose();
            HomePageView homeView = new HomePageView();
            new HomePageController(homeView);
            homeView.setVisible(true);
        });
    }

    private void loadTickets() {
        try {
            User user = Session.getCurrentUser();
            List<Ticket> tickets = ticketDAO.getDetailedTicketsByUserId(user.getUserId());

            DefaultTableModel model = (DefaultTableModel) view.ticketTable.getModel();
            model.setRowCount(0); // Clear table

            for (Ticket t : tickets) {
                model.addRow(new Object[]{
                        t.getTicketId(),
                        t.getTrainName(),
                        t.getDepartureDate(),
                        t.getFrom(),
                        t.getTo(),
                        t.getSeatNumber()
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view, "Biletler yüklenirken hata oluştu.", "Veri Tabanı Hatası", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cancelSelectedTicket() {
        int selectedRow = view.ticketTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Lütfen iptal etmek için bir bilet seçin.", "Uyarı", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int ticketId = (int) view.ticketTable.getValueAt(selectedRow, 0);

        int confirm = JOptionPane.showConfirmDialog(view, "Bu bileti iptal etmek istediğinizden emin misiniz?", "Onay", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        try {
            // Koltuğu tekrar müsait yapabilmek için gerekli bilgiler alınıyor
            Ticket ticket = ticketDAO.getById(ticketId);
            if (ticket == null) throw new Exception("Bilet bulunamadı.");

            // 1. Bilet silinir
            ticketDAO.delete(ticketId);

            // 2. Koltuk tekrar müsait yapılır
            routeSeatDAO.markSeatAsAvailable(ticket.getRouteId(), ticket.getSeatId());

            JOptionPane.showMessageDialog(view, "Bilet başarıyla iptal edildi.");
            loadTickets();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view, "Bilet iptal edilirken hata oluştu.", "Veri Tabanı Hatası", JOptionPane.ERROR_MESSAGE);
        }
    }
}