package controller;

import view.MyTicketsPageView;
import view.HomePageView;
import model.Session;
import model.entities.Ticket;
import database.dao.TicketDAO;
import database.dao.TicketDAOImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Biletlerim sayfası kontrolcüsü
 */
public class MyTicketsPageController {
    private MyTicketsPageView view;
    private TicketDAO ticketDAO;
    private List<Ticket> tickets;

    public MyTicketsPageController(MyTicketsPageView view, int userId) {
        this.view = view;
        this.ticketDAO = new TicketDAOImpl();
        this.tickets = new ArrayList<>();

        // Kullanıcı oturumunu kontrol et
        if (Session.getCurrentUser() == null) {
            JOptionPane.showMessageDialog(view, "Session expired. Please login again.",
                    "Session Error", JOptionPane.ERROR_MESSAGE);
            view.dispose();
            view.LoginView loginView = new view.LoginView();
            new LoginController(loginView);
            loginView.setVisible(true);
            return;
        }

        // Event listeners
        view.backButton.addActionListener(e -> goBack());
        view.cancelButton.addActionListener(e -> cancelSelectedTicket());
        view.refreshButton.addActionListener(e -> loadTickets());

        // Arama alanı için listener
        view.searchField.addActionListener(e -> filterTickets());

        // Status filtresi için listener
        view.statusFilter.addActionListener(e -> filterTickets());

        // Kullanıcının biletlerini yükle
        loadTickets();
    }

    /**
     * Kullanıcının biletlerini veritabanından yükler ve tabloya ekler
     */
    private void loadTickets() {
        try {
            int userId = Session.getCurrentUser().getUserId();
            tickets = ticketDAO.getByUserId(userId);

            view.clearTickets();

            for (Ticket ticket : tickets) {
                // Bilet bilgilerini al (bu kısım veritabanınızın yapısına göre değişebilir)
                int ticketId = ticket.getTicketId();
                int routeId = ticket.getRouteId();
                int seatId = ticket.getSeatId();
                Date bookingTime = ticket.getBookingTime();

                // Bilet bilgilerini formatlayarak tabloya ekle
                String trainName = getTrainNameByRouteId(routeId);
                String date = getFormattedDate(bookingTime);
                String from = getDepartureByRouteId(routeId);
                String to = getArrivalByRouteId(routeId);
                String seat = String.valueOf(seatId);
                String status = getTicketStatus(ticket);

                view.addTicketToTable(ticketId, trainName, date, from, to, seat, status);
            }

            // Filtreleme işlemi
            filterTickets();
        } catch (SQLException e) {
            e.printStackTrace();
            view.showErrorMessage("Failed to load tickets: " + e.getMessage());
        }
    }

    /**
     * Filtreleme işlemi yapar
     */
    private void filterTickets() {
        String searchText = view.searchField.getText().toLowerCase();
        String status = (String) view.statusFilter.getSelectedItem();

        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>((DefaultTableModel) view.ticketTable.getModel());
        view.ticketTable.setRowSorter(sorter);

        RowFilter<DefaultTableModel, Object> rf = null;

        try {
            List<RowFilter<Object, Object>> filters = new ArrayList<>();

            // Arama metni filtresi
            if (!searchText.isEmpty()) {
                RowFilter<Object, Object> searchFilter = new RowFilter<Object, Object>() {
                    @Override
                    public boolean include(Entry<? extends Object, ? extends Object> entry) {
                        for (int i = 0; i < entry.getValueCount(); i++) {
                            if (entry.getStringValue(i).toLowerCase().contains(searchText)) {
                                return true;
                            }
                        }
                        return false;
                    }
                };
                filters.add(searchFilter);
            }

            // Status filtresi
            if (!"All".equals(status)) {
                RowFilter<Object, Object> statusFilter = RowFilter.regexFilter(status, 6); // 6 = status column index
                filters.add(statusFilter);
            }

            // Filtreleri birleştir
            if (!filters.isEmpty()) {
                rf = RowFilter.andFilter(filters);
            }
        } catch (java.util.regex.PatternSyntaxException e) {
            e.printStackTrace();
            return;
        }

        sorter.setRowFilter(rf);
    }

    /**
     * Seçili bileti iptal eder
     */
    private void cancelSelectedTicket() {
        int ticketId = view.getSelectedTicketId();

        if (ticketId == -1) {
            view.showWarningMessage("Please select a ticket to cancel.");
            return;
        }

        // Kullanıcı onayı al
        boolean confirm = view.showConfirmationDialog("Are you sure you want to cancel this ticket?");
        if (!confirm) {
            return;
        }

        try {
            // Bileti sil
            ticketDAO.delete(ticketId);

            // Tabloyu güncelle
            loadTickets();

            view.showSuccessMessage("Ticket cancelled successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            view.showErrorMessage("Failed to cancel ticket: " + e.getMessage());
        }
    }

    /**
     * Ana sayfaya geri döner
     */
    private void goBack() {
        view.dispose();
        HomePageView homeView = new HomePageView();
        new HomePageController(homeView);
        homeView.setVisible(true);
    }

    /**
     * Rota ID'sine göre tren adını döndürür
     */
    private String getTrainNameByRouteId(int routeId) {
        // TODO: Veritabanından gerçek tren adını çek
        return "Train " + routeId;
    }

    /**
     * Rota ID'sine göre kalkış noktasını döndürür
     */
    private String getDepartureByRouteId(int routeId) {
        // TODO: Veritabanından gerçek kalkış noktasını çek
        return "Departure " + routeId;
    }

    /**
     * Rota ID'sine göre varış noktasını döndürür
     */
    private String getArrivalByRouteId(int routeId) {
        // TODO: Veritabanından gerçek varış noktasını çek
        return "Arrival " + routeId;
    }

    /**
     * Tarihi formatlar
     */
    private String getFormattedDate(Date date) {
        if (date == null) {
            return "N/A";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sdf.format(date);
    }

    /**
     * Bilet durumunu döndürür
     */
    private String getTicketStatus(Ticket ticket) {
        // TODO: Bilet durumunu belirle (aktif, tamamlanmış, iptal edilmiş)
        // Bu kısım bilet durum takibinize göre değişebilir
        return "Active";
    }
}