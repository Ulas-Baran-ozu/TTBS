//Hatali calismiyor!!


//package view;
//
//import javax.swing.*;
//import java.awt.*;
//
//public class SeatSelectionView extends JFrame {
//    public JPanel seatPanel;
//    public JButton purchaseButton;
//    public JLabel selectedCountLabel;
//
//    public SeatSelectionView(int totalSeats) {
//        setTitle("Seat Selection");
//        setSize(1000, 800);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setLocationRelativeTo(null);
//
//        JPanel mainPanel = new JPanel();
//        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
//
//        int seatsPerWagon = 30; // 3+3 koltuk x 5 sıra = 30 koltuk
//        int numberOfWagons = (int) Math.ceil((double) totalSeats / seatsPerWagon);
//
//        for (int w = 1; w <= numberOfWagons; w++) {
//            JPanel wagonPanel = new JPanel();
//            wagonPanel.setLayout(new GridLayout(5, 7, 15, 15)); // 5 sıra, 7 hücre (3+ boşluk +3)
//            wagonPanel.setBorder(BorderFactory.createTitledBorder("Vagon " + w));
//
//            for (int row = 0; row < 5; row++) {
//                for (int col = 0; col < 7; col++) {
//                    if (col == 3) {
//                        wagonPanel.add(new JLabel()); // koridor boşluğu
//                    } else {
//                        JButton seat = new JButton();
//                        seat.setPreferredSize(new Dimension(60, 60));
//                        seat.setBackground(new Color(0, 200, 0));
//                        seat.setFocusPainted(false);
//                        seat.setBorderPainted(false);
//                        seat.setContentAreaFilled(true);
//                        seat.setOpaque(true);
//                        seat.setText("💺"); // Koltuk ikonu
//                        wagonPanel.add(seat);
//                    }
//                }
//            }
//            mainPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Vagonlar arası boşluk
//            mainPanel.add(wagonPanel);
//        }
//
//        JScrollPane scrollPane = new JScrollPane(mainPanel);
//
//        purchaseButton = new JButton("Biletleri Al");
//        purchaseButton.setEnabled(false);
//
//        selectedCountLabel = new JLabel("Seçilen Koltuklar: 0");
//        selectedCountLabel.setHorizontalAlignment(SwingConstants.CENTER);
//
//        JPanel bottomPanel = new JPanel(new BorderLayout());
//        bottomPanel.add(selectedCountLabel, BorderLayout.CENTER);
//        bottomPanel.add(purchaseButton, BorderLayout.SOUTH);
//
//        add(scrollPane, BorderLayout.CENTER);
//        add(bottomPanel, BorderLayout.SOUTH);
//    }
//}