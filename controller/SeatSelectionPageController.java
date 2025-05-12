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
import view.HomePageView;

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
            markUnoccupiedSeats(occupiedSeatIds);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view, "Dolu koltuklar alınamadı!", "Veritabanı Hatası", JOptionPane.ERROR_MESSAGE);
        }

        // Koltuklara numara ver ve action listener ekle
        for (int i = 0; i < view.rows; i++) {
            for (int j = 0; j < view.cols; j++) {
                final JButton seat = view.seatButtons[i][j];
                final int seatId = i * view.cols + j + 1;

                seat.setText(String.valueOf(seatId));
                seat.setActionCommand(String.valueOf(seatId));
                seat.setBackground(Color.GREEN);

                if (seat.isEnabled()) {
                    seat.addActionListener(e -> toggleSeatSelection(seat));
                }
            }
        }

        view.proceedButton.addActionListener(e -> proceedToPayment());

        // ✅ Geri butonu listener
        view.backButton.addActionListener(e -> {
            view.dispose();
            HomePageView homeView = new HomePageView();
            new HomePageController(homeView);
            homeView.setVisible(true);
        });
    }

    private void toggleSeatSelection(JButton seat) {
        if (selectedSeats.contains(seat)) {
            selectedSeats.remove(seat);
            seat.setBackground(Color.GREEN);
        } else {
            if (selectedSeats.size() < ticketCount) {
                selectedSeats.add(seat);
                seat.setBackground(Color.BLUE);
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
                seatNumbers.add(Integer.parseInt(btn.getActionCommand()));
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
            seat.setEnabled(false);
        }
    }

    private void markUnoccupiedSeats(List<Integer> occupiedIds) {
        int totalSeats = view.rows * view.cols;

        for (int seatId = 1; seatId <= totalSeats; seatId++) {
            if (!occupiedIds.contains(seatId)) {
                int index = seatId - 1;
                int row = index / view.cols;
                int col = index % view.cols;
                JButton seat = view.seatButtons[row][col];

                seat.setEnabled(true);
                seat.setBackground(Color.GREEN);
                seat.setOpaque(true);
                seat.setBorderPainted(false);
            }
        }
    }
}