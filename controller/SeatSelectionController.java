
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