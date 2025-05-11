package controller;

import view.SeatSelectionPageView;
import view.PaymentSuccessfulView;
import view.TrainSearchResultsView;

import javax.swing.*;
import java.awt.*;

public class SeatSelectionPageController {
    private SeatSelectionPageView view;
    private JButton selectedSeat = null;
    private String departure, arrival, date, tripType;
    private int tickets;

    public SeatSelectionPageController(SeatSelectionPageView view, String departure, String arrival,
                                       String date, int tickets, String tripType) {
        this.view = view;
        this.departure = departure;
        this.arrival = arrival;
        this.date = date;
        this.tickets = tickets;
        this.tripType = tripType;

        int[] occupiedSeats = {2, 7, 13}; // örnek dolu koltuklar
        markOccupiedSeats(occupiedSeats);

        // Koltuklara tıklama işlemi
        for (int i = 0; i < view.rows; i++) {
            for (int j = 0; j < view.cols; j++) {
                JButton seat = view.seatButtons[i][j];
                if (seat.isEnabled()) {
                    seat.addActionListener(e -> {
                        if (selectedSeat != null) {
                            selectedSeat.setBackground(Color.GREEN);
                        }
                        selectedSeat = seat;
                        seat.setBackground(Color.BLUE);
                        view.selectedCountLabel.setText("Seçilen Koltuk: " + seat.getText());
                        view.purchaseButton.setEnabled(true);
                    });
                }
            }
        }

        // Satın alma işlemi
        view.purchaseButton.addActionListener(e -> {
            if (selectedSeat == null) {
                JOptionPane.showMessageDialog(view, "Lütfen bir koltuk seçin.");
            } else {
                view.dispose();
                PaymentSuccessfulView paymentView = new PaymentSuccessfulView();
                new PaymentSuccessfulController(paymentView);
                paymentView.setVisible(true);
            }
        });

        // Geri butonu işlemi
        view.backButton.addActionListener(e -> {
            view.dispose();
            TrainSearchResultsView trainListView = new TrainSearchResultsView();
            new TrainSearchResultsController(trainListView, departure, arrival, date, tickets, tripType);
            trainListView.setVisible(true);
        });
    }

    private void markOccupiedSeats(int[] seats) {
        for (int seatNum : seats) {
            int index = seatNum - 1;
            int row = index / view.cols;
            int col = index % view.cols;
            JButton seat = view.seatButtons[row][col];
            seat.setBackground(Color.RED);
            seat.setEnabled(false);
        }
    }
}
