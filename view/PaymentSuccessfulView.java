package view;

import javax.swing.*;
import java.awt.*;

public class PaymentSuccessfulView extends JFrame {
    public JTextArea ticketInfoArea;
    public JButton backButton;

    public PaymentSuccessfulView(String ticketInfo) {
        setTitle("Payment Successful");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        ticketInfoArea = new JTextArea();
        ticketInfoArea.setEditable(false);
        ticketInfoArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        ticketInfoArea.setText(ticketInfo); // ✅ Dinamik içerik

        backButton = new JButton("Back to Home");

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(backButton);

        setLayout(new BorderLayout());
        add(new JScrollPane(ticketInfoArea), BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }
}