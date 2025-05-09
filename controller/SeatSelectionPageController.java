package controller;

import view.SeatSelectionPageView;
import view.PaymentSuccessfulView;
import view.TrainSearchResultsView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SeatSelectionPageController {
    private SeatSelectionPageView view;
    private JButton selectedSeat = null;
    private String departure, arrival, date, tripType;
    private int tickets;
    public SeatSelectionPageController(SeatSelectionPageView view, String departure, String arrival, String date, int tickets, String tripType) {
        this.view = view;
        this.departure = departure;
        this.arrival = arrival;
        this.date = date;
        this.tickets = tickets;
        this.tripType = tripType;


        int[] occupiedSeats = {2, 7, 13};
        markOccupiedSeats(occupiedSeats);

        for (int i = 0; i < view.rows; i++) {
            for (int j = 0; j < view.cols; j++) {
                JButton seat = view.seatButtons[i][j];
                if (seat.isEnabled()) {
                    seat.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (selectedSeat != null) {
                                selectedSeat.setBackground(Color.GREEN);
                            }
                            selectedSeat = seat;
                            seat.setBackground(Color.BLUE);
                        }
                    });
                }
            }
        }

        view.proceedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedSeat == null) {
                    JOptionPane.showMessageDialog(view, "Please select a seat before proceeding.");
                } else {
                    view.dispose();
                    PaymentSuccessfulView view = new PaymentSuccessfulView();
                    new PaymentSuccessfulController(view);
                    view.setVisible(true);

                }
            }
        });

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
