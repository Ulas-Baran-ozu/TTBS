package view;

import javax.swing.*;
import java.awt.*;

public class SeatSelectionPageView extends JFrame {
    public JButton[][] seatButtons;
    public JButton purchaseButton;
    public JLabel selectedCountLabel;
    public JLabel journeyInfoLabel;
    public JPanel seatPanel;
    public JButton backButton;

    public final int rows = 5;
    public final int cols = 4;

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
                seat.setBackground(Color.GREEN);
                seatButtons[i][j] = seat;
                seatPanel.add(seat);
            }
        }

        selectedCountLabel = new JLabel("Selected Seats: 0");
        journeyInfoLabel = new JLabel("Information: ");
        purchaseButton = new JButton("Buy");
        purchaseButton.setEnabled(false);
        backButton = new JButton("Back");

        JPanel bottomPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        bottomPanel.add(selectedCountLabel);
        bottomPanel.add(journeyInfoLabel);
        bottomPanel.add(backButton);
        bottomPanel.add(purchaseButton);

        setLayout(new BorderLayout(10, 10));
        add(seatPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }
}
