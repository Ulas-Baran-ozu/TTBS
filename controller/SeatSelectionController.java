
//Hatali calismiyor

//package controller;
//
//import view.SeatSelectionView;
//import view.HomePageView;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionListener;
//import java.util.ArrayList;
//
//public class SeatSelectionController {
//    private SeatSelectionView view;
//    private int ticketCount;
//    private int selectedCount = 0;
//    private ArrayList<JButton> selectedSeats = new ArrayList<>();
//
//    public SeatSelectionController(SeatSelectionView view, int ticketCount) {
//        this.view = view;
//        this.ticketCount = ticketCount;
//
//        for (Component comp : view.seatPanel.getComponents()) {
//            if (comp instanceof JButton) {
//                JButton seatButton = (JButton) comp;
//                seatButton.addActionListener(e -> selectSeat(seatButton));
//            }
//        }
//
//        view.purchaseButton.addActionListener(e -> purchaseTickets());
//    }
//
//    private void selectSeat(JButton seatButton) {
//        if (seatButton.getBackground().equals(Color.RED)) {
//            return; // dolu koltuk seçilemez
//        }
//
//        if (selectedSeats.contains(seatButton)) {
//            seatButton.setBackground(Color.GREEN);
//            selectedSeats.remove(seatButton);
//            selectedCount--;
//        } else {
//            if (selectedCount < ticketCount) {
//                seatButton.setBackground(Color.BLUE);
//                selectedSeats.add(seatButton);
//                selectedCount++;
//            }
//        }
//
//        view.selectedCountLabel.setText("Seçilen Koltuklar: " + selectedCount);
//        view.purchaseButton.setEnabled(selectedCount == ticketCount);
//    }
//
//    private void purchaseTickets() {
//        JOptionPane.showMessageDialog(view, "Ödeme tamamlandı!", "Başarılı", JOptionPane.INFORMATION_MESSAGE);
//        view.dispose();
//        HomePageView homePageView = new HomePageView();
//        new HomePageController(homePageView);
//        homePageView.setVisible(true);
//    }
//}

package controller;

import model.TrainJourney;
import view.SeatSelectionPageView;
import view.HomePageView;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class SeatSelectionPageController {
    private SeatSelectionPageView view;
    private TrainJourney selectedJourney;
    private int ticketCount;
    private int selectedCount = 0;
    private ArrayList<JButton> selectedSeats = new ArrayList<>();

    public SeatSelectionPageController(SeatSelectionPageView view, TrainJourney selectedJourney, int ticketCount) {
        this.view = view;
        this.selectedJourney = selectedJourney;
        this.ticketCount = ticketCount;

        // Tüm koltukları dinleyiciye bağla
        for (Component comp : view.seatPanel.getComponents()) {
            if (comp instanceof JButton) {
                JButton seatButton = (JButton) comp;
                seatButton.addActionListener(e -> selectSeat(seatButton));
            }
        }

        // Satın alma butonuna tıklama işlemi
        view.purchaseButton.addActionListener(e -> purchaseTickets());

        // Başlangıçta bilgiler
        view.selectedCountLabel.setText("Seçilen Koltuklar: 0");
        view.journeyInfoLabel.setText("Sefer: " + selectedJourney.getRoute() + " - " + selectedJourney.getDepartureTime());
    }

    private void selectSeat(JButton seatButton) {
        if (seatButton.getBackground().equals(Color.RED)) {
            return; // Dolu koltuk
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
            }
        }

        view.selectedCountLabel.setText("Seçilen Koltuklar: " + selectedCount);
        view.purchaseButton.setEnabled(selectedCount == ticketCount);
    }

    private void purchaseTickets() {
        JOptionPane.showMessageDialog(view, "Ödeme tamamlandı!\nSeçilen koltuk sayısı: " + selectedCount, "Başarılı", JOptionPane.INFORMATION_MESSAGE);
        view.dispose();

        HomePageView homePageView = new HomePageView();
        new HomePageController(homePageView);
        homePageView.setVisible(true);
    }
}

