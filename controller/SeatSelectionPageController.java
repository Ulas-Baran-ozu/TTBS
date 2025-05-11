package controller;

import database.dao.RouteSeatDAO;
import database.dao.RouteSeatDAOImpl;
import database.dao.TicketDAO;
import database.dao.TicketDAOImpl;
import model.Session;
import model.entities.Route;
import model.entities.Ticket;
import view.SeatSelectionPageView;
import view.PaymentSuccessfulView;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SeatSelectionPageController {
    private final SeatSelectionPageView view;
    private final Route selectedRoute;
    private final int ticketCount;
    private final List<JButton> selectedSeats = new ArrayList<>();

    public SeatSelectionPageController(SeatSelectionPageView view, Route selectedRoute, int ticketCount) {
        this.view = view;
        this.selectedRoute = selectedRoute;
        this.ticketCount = ticketCount;

        try {
            RouteSeatDAO seatDAO = new RouteSeatDAOImpl();
            List<Integer> occupiedSeatIds = seatDAO.getOccupiedSeatIdsByRouteId(selectedRoute.getRouteId());
            markOccupiedSeats(occupiedSeatIds);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view, "Dolu koltuklar alınamadı!", "Veri Tabanı Hatası", JOptionPane.ERROR_MESSAGE);
        }

        // 🎯 Koltuklara numara ver ve dinleyici ata
        int seatNumber = 1;
        for (int i = 0; i < view.rows; i++) {
            for (int j = 0; j < view.cols; j++) {
                final JButton seat = view.seatButtons[i][j]; // ✅ final tanım
                final int seatId = i * view.cols + j + 1;     // ✅ final seatId hesapla

                seat.setText(String.valueOf(seatId));
                seat.setActionCommand(String.valueOf(seatId));
                seat.setBackground(Color.GREEN); // boş koltuklar yeşil

                if (seat.isEnabled()) {
                    seat.addActionListener(e -> toggleSeatSelection(seat));
                }
            }
        }

        view.proceedButton.addActionListener(e -> proceedToPayment());
    }

    private void toggleSeatSelection(JButton seat) {
        if (selectedSeats.contains(seat)) {
            selectedSeats.remove(seat);
            seat.setBackground(Color.GREEN); // yeniden boş hale getir
        } else {
            if (selectedSeats.size() < ticketCount) {
                selectedSeats.add(seat);
                seat.setBackground(Color.BLUE); // seçili hale getir
            } else {
                JOptionPane.showMessageDialog(view, "Yalnızca " + ticketCount + " koltuk seçebilirsiniz.");
            }
        }
    }

    private void proceedToPayment() {
        if (selectedSeats.size() != ticketCount) {
            JOptionPane.showMessageDialog(view,
                    "Lütfen " + ticketCount + " koltuk seçin.",
                    "Uyarı", JOptionPane.WARNING_MESSAGE);
            return;
        }

        List<Integer> seatNumbers = new ArrayList<>();
        for (JButton btn : selectedSeats) {
            try {
                seatNumbers.add(Integer.parseInt(btn.getActionCommand())); // ✅ DOĞRU YOL
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
            }
        }

        try {
            RouteSeatDAO seatDAO = new RouteSeatDAOImpl();
            TicketDAO ticketDAO = new TicketDAOImpl();
            int userId = Session.getCurrentUser().getUserId();

            for (int seatId : seatNumbers) {
                if (seatDAO.exists(selectedRoute.getRouteId(), seatId)) {
                    seatDAO.markSeatAsUnavailable(selectedRoute.getRouteId(), seatId);
                    Ticket ticket = new Ticket(userId, selectedRoute.getRouteId(), seatId);
                    ticketDAO.add(ticket);
                } else {
                    JOptionPane.showMessageDialog(view,
                            "Seçilen koltuk sistemde tanımlı değil: " + seatId,
                            "Kayıt Hatası", JOptionPane.ERROR_MESSAGE);
                }
            }

            view.dispose();
            new PaymentSuccessfulController(selectedRoute, seatNumbers, ticketCount);

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(view, "Koltuklar kaydedilirken hata oluştu!", "Veritabanı Hatası", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void markOccupiedSeats(List<Integer> occupiedIds) {
        for (int id : occupiedIds) {
            int index = id - 1;
            int row = index / view.cols;
            int col = index % view.cols;
            JButton seat = view.seatButtons[row][col];
            seat.setBackground(Color.RED);
            seat.setEnabled(false);
        }
    }
}