package view;

import javax.swing.*;
import java.awt.*;


public class SeatSelectionPageView extends JFrame {
    public JButton[][] seatButtons;
    public JButton proceedButton, backButton;
    public int rows = 5, cols = 4; // oylesine 20 koltuk
    public JPanel seatPanel; // EKLENDİ
    public JLabel selectedCountLabel; // EKLENDİ
    public JLabel journeyInfoLabel;   // EKLENDİ
    public JButton proceedButton, backButton; // zaten vardı ama netleştirildi

    public SeatSelectionPageView() {
        setTitle("Seat Selection");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        seatPanel = new JPanel(new GridLayout(rows, cols, 10, 10));
        seatButtons = new JButton[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                JButton seat = new JButton((i * cols + j + 1) + "");
                seat.setBackground(Color.GREEN); // bos koltuk
                seatButtons[i][j] = seat;
                seatPanel.add(seat);
            }
        }
        selectedCountLabel = new JLabel("Seçilen Koltuklar: 0"); // EKLENDİ
        journeyInfoLabel = new JLabel("Sefer Bilgisi: ");        // EKLENDİ
        proceedButton = new JButton("Proceed to Payment");
        backButton = new JButton("Back");

        JPanel bottomPanel = new JPanel(new GridLayout(2, 2, 10, 10)); // EKLENDİ
        bottomPanel.add(selectedCountLabel);  // EKLENDİ
        bottomPanel.add(journeyInfoLabel);   // EKLENDİ
        bottomPanel.add(backButton);
        bottomPanel.add(proceedButton);
        selectedCountLabel = new JLabel("Seçilen Koltuklar: 0");
        journeyInfoLabel = new JLabel("Sefer Bilgisi: ");

        setLayout(new BorderLayout());
        //
        add(seatPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }
}

