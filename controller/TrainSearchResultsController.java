package controller;

import database.dao.RouteDAO;
import database.dao.RouteDAOImpl;
import model.entities.Route;
import view.SeatSelectionPageView;
import view.TrainSearchResultsView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.time.Duration;

public class TrainSearchResultsController {
    private TrainSearchResultsView view;
    private List<Route> routes;
    private int ticketCount;

    public TrainSearchResultsController(TrainSearchResultsView view, String departure, String arrival, String date, int ticketCount) throws ParseException {
        this.view = view;
        this.ticketCount = ticketCount;
        this.routes = new ArrayList<>();

        // Tarih dönüşümü
        java.util.Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

        try {
            RouteDAO routeDAO = new RouteDAOImpl();
            this.routes = routeDAO.search(departure, arrival, sqlDate); // ✅ düzeltildi
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view, "Rotalar alınamadı!", "Hata", JOptionPane.ERROR_MESSAGE);
        }

        loadTable();

        view.proceedButton.addActionListener(e -> proceed());
    }

    private void loadTable() {
        DefaultTableModel model = (DefaultTableModel) view.journeyTable.getModel();
        for (Route route : routes) {
            String duration = calculateDuration(
                    route.getDepartureTime().toLocalDateTime(),
                    route.getArrivalTime().toLocalDateTime()
            );
            model.addRow(new Object[]{
                    route.getTrainName(),
                    "?", // Koltuk bilgisi ilerde eklenecek
                    route.getDepartureTime().toLocalDateTime().toLocalTime().toString(),
                    duration,
                    route.getSourceStation(),
                    route.getDestinationStation()
            });
        }
    }

    private String calculateDuration(LocalDateTime departure, LocalDateTime arrival) {
        Duration duration = Duration.between(departure, arrival);
        long hours = duration.toHours();
        long minutes = duration.toMinutesPart();
        return hours + " saat " + minutes + " dakika";
    }

    private void proceed() {
        int selectedRow = view.journeyTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Lütfen bir sefer seçin.", "Uyarı", JOptionPane.WARNING_MESSAGE);
        } else {
            Route selectedRoute = routes.get(selectedRow);
            view.dispose();
            SeatSelectionPageView seatSelectionView = new SeatSelectionPageView();
            new SeatSelectionPageController(seatSelectionView, selectedRoute, ticketCount); // ✅ ticketCount gönderildi
            seatSelectionView.setVisible(true);
        }
    }
}