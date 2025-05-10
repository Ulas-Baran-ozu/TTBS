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
    private SeatSelectionPageView view;
    private Route selectedRoute;
    private int ticketCount;
    private List<JButton> selectedSeats = new ArrayList<>();

    public SeatSelectionPageController(SeatSelectionPageView view, Route selectedRoute, int ticketCount) {
        this.view = view;
        this.selectedRoute = selectedRoute;
        this.ticketCount = ticketCount;

        // Dolu koltukları işaretle
        try {
            RouteSeatDAO seatDAO = new RouteSeatDAOImpl();
            List<Integer> occupiedSeatIds = seatDAO.getOccupiedSeatIdsByRouteId(selectedRoute.getRouteId());
            markOccupiedSeats(occupiedSeatIds);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view, "Dolu koltuklar alınamadı!", "Veri Tabanı Hatası", JOptionPane.ERROR_MESSAGE);
        }

        // Koltuklara tıklama dinleyicileri ata
        for (int i = 0; i < view.rows; i++) {
            for (int j = 0; j < view.cols; j++) {
                JButton seat = view.seatButtons[i][j];
                if (seat.isEnabled()) {
                    seat.addActionListener(e -> toggleSeatSelection(seat));
                }
            }
        }

        view.proceedButton.addActionListener(e -> {
            if (selectedSeats.size() != ticketCount) {
                JOptionPane.showMessageDialog(view,
                        "Lütfen " + ticketCount + " koltuk seçin.",
                        "Uyarı", JOptionPane.WARNING_MESSAGE);
            } else {
                view.dispose();

                List<Integer> seatNumbers = new ArrayList<>();
                for (JButton btn : selectedSeats) {
                    try {
                        seatNumbers.add(Integer.parseInt(btn.getText()));
                    } catch (NumberFormatException ex) {
                        ex.printStackTrace();
                    }
                }

                // 🔗 Koltukları rezerve et ve biletleri kaydet
                try {
                    RouteSeatDAO seatDAO = new RouteSeatDAOImpl();
                    TicketDAO ticketDAO = new TicketDAOImpl();
                    int userId = Session.getCurrentUser().getUserId(); // Kullanıcı ID

                    for (int seatId : seatNumbers) {
                        seatDAO.markSeatAsUnavailable(selectedRoute.getRouteId(), seatId);
                        Ticket ticket = new Ticket(userId, selectedRoute.getRouteId(), seatId);
                        ticketDAO.add(ticket); // ✅ Veritabanına kayıt
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(view, "Koltuklar kaydedilirken hata oluştu!", "Veritabanı Hatası", JOptionPane.ERROR_MESSAGE);
                }

                // Ödeme başarı ekranına geç
                new PaymentSuccessfulController(selectedRoute, seatNumbers, ticketCount);
            }
        });
    }

    private void toggleSeatSelection(JButton seat) {
        if (selectedSeats.contains(seat)) {
            seat.setBackground(Color.GREEN);
            selectedSeats.remove(seat);
        } else {
            if (selectedSeats.size() < ticketCount) {
                seat.setBackground(Color.BLUE);
                selectedSeats.add(seat);
            } else {
                JOptionPane.showMessageDialog(view, "Yalnızca " + ticketCount + " koltuk seçebilirsiniz.");
            }
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