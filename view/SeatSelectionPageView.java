package view;

import javax.swing.*;
import java.awt.*;

public class SeatSelectionPageView extends JFrame {
    public JButton[][] seatButtons;
    public JButton proceedButton, backButton;
    public int rows = 5, cols = 4; // oylesine 20 koltuk

    public SeatSelectionPageView() {
        setTitle("Seat Selection");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel seatPanel = new JPanel(new GridLayout(rows, cols, 10, 10));
        seatButtons = new JButton[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                JButton seat = new JButton((i * cols + j + 1) + "");
                seatButtons[i][j] = seat;
                seatPanel.add(seat);
            }
        }

        proceedButton = new JButton("Proceed to Payment");
        backButton = new JButton("Back");

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(backButton);
        bottomPanel.add(proceedButton);

        setLayout(new BorderLayout());
        add(seatPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }
}
