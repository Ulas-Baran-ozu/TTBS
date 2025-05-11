package controller;

import model.TrainJourney;
import view.SeatSelectionPageView;
import view.HomePageView;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SeatSelectionController {
    private SeatSelectionPageView view;
    private TrainJourney selectedJourney;
    private int ticketCount;
    private int selectedCount = 0;
    private ArrayList<JButton> selectedSeats = new ArrayList<>();

    public SeatSelectionController(SeatSelectionPageView view, TrainJourney selectedJourney, int ticketCount) {
        this.view = view;
        this.selectedJourney = selectedJourney;
        this.ticketCount = ticketCount;

        // Koltuklara tıklama olaylarını bağla
        for (int i = 0; i < view.rows; i++) {
            for (int j = 0; j < view.cols; j++) {
                JButton seatButton = view.seatButtons[i][j];
                seatButton.addActionListener(e -> selectSeat(seatButton));
            }
        }

        // Bilgileri göster
        view.selectedCountLabel.setText("Seçilen Koltuklar: 0");
        view.journeyInfoLabel.setText("Sefer: " + selectedJourney.getRoute() + " - " + selectedJourney.getDepartureTime());

        // Buton işlemleri
        view.purchaseButton.addActionListener(e -> purchaseTickets());
        view.backButton.addActionListener(e -> goBack());
    }

    private void selectSeat(JButton seatButton) {
        if (seatButton.getBackground().equals(Color.RED)) {
            return; // dolu koltuk seçilemez
        }

        if (selectedSeats.contains(seatButton)) {
            seatButton.setBackground(Color.GREEN);
            selectedSeats.remove(seatButton);
            selectedCount--;
        } else {
            if (selectedCount < ticketCount) {
                seatButton.setBackground(Color.BLUE);
                selectedSeats.add(seatButton);
                selectedCount++;
            } else {
                JOptionPane.showMessageDialog(view, "Yalnızca " + ticketCount + " koltuk seçebilirsiniz.");
            }
        }

        view.selectedCountLabel.setText("Seçilen Koltuklar: " + selectedCount);
        view.purchaseButton.setEnabled(selectedCount == ticketCount);
    }

    private void purchaseTickets() {
        JOptionPane.showMessageDialog(view, "Ödeme tamamlandı!\nSeçilen koltuk sayısı: " + selectedCount,
                "Başarılı", JOptionPane.INFORMATION_MESSAGE);
        view.dispose();

        HomePageView homePageView = new HomePageView();
        new HomePageController(homePageView);
        homePageView.setVisible(true);
    }

    private void goBack() {
        view.dispose();
        // İsteğe göre önceki sayfaya yönlendirme eklenebilir
        HomePageView homePageView = new HomePageView();
        new HomePageController(homePageView);
        homePageView.setVisible(true);
    }
}
