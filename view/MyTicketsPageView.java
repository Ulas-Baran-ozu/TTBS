package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Kullanıcının biletlerini gösterdiği sayfa
 */
public class MyTicketsPageView extends JFrame {
    public JTable ticketTable;
    public JButton cancelButton;
    public JButton backButton;
    public JButton refreshButton;
    public JLabel titleLabel;
    public JPanel filterPanel;
    public JComboBox<String> statusFilter;
    public JTextField searchField;

    public MyTicketsPageView() {
        setTitle("My Tickets");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Ana panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Başlık
        titleLabel = new JLabel("My Tickets", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Filtre paneli
        filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JLabel statusLabel = new JLabel("Status:");
        filterPanel.add(statusLabel);

        String[] statusOptions = {"All", "Active", "Completed", "Cancelled"};
        statusFilter = new JComboBox<>(statusOptions);
        filterPanel.add(statusFilter);

        JLabel searchLabel = new JLabel("Search:");
        filterPanel.add(searchLabel);

        searchField = new JTextField(15);
        filterPanel.add(searchField);

        refreshButton = new JButton("Refresh");
        filterPanel.add(refreshButton);

        mainPanel.add(filterPanel, BorderLayout.NORTH);

        // Tablo oluştur
        String[] columnNames = {"Ticket ID", "Train", "Date", "From", "To", "Seat", "Status"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Tüm hücreleri salt okunur yap
            }
        };

        ticketTable = new JTable(model);
        ticketTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ticketTable.getTableHeader().setReorderingAllowed(false);

        // Tablo için kaydırma bileşeni
        JScrollPane scrollPane = new JScrollPane(ticketTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Buton paneli
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        backButton = new JButton("Back");
        buttonPanel.add(backButton);

        cancelButton = new JButton("Cancel Ticket");
        buttonPanel.add(cancelButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    /**
     * Tabloya yeni bir satır (bilet) ekler
     */
    public void addTicketToTable(int ticketId, String trainName, String date, String from, String to, String seat, String status) {
        DefaultTableModel model = (DefaultTableModel) ticketTable.getModel();
        model.addRow(new Object[]{ticketId, trainName, date, from, to, seat, status});
    }

    /**
     * Tablodaki tüm biletleri temizler
     */
    public void clearTickets() {
        DefaultTableModel model = (DefaultTableModel) ticketTable.getModel();
        model.setRowCount(0);
    }

    /**
     * Seçili bilet ID'sini döndürür, eğer seçili değilse -1
     */
    public int getSelectedTicketId() {
        int selectedRow = ticketTable.getSelectedRow();
        if (selectedRow == -1) {
            return -1;
        }
        return (int) ticketTable.getValueAt(selectedRow, 0);
    }

    /**
     * Uyarı mesajı gösterir
     */
    public void showWarningMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Warning", JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Onay mesajı gösterir
     */
    public boolean showConfirmationDialog(String message) {
        int choice = JOptionPane.showConfirmDialog(this, message, "Confirm", JOptionPane.YES_NO_OPTION);
        return choice == JOptionPane.YES_OPTION;
    }

    /**
     * Başarı mesajı gösterir
     */
    public void showSuccessMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Hata mesajı gösterir
     */
    public void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}