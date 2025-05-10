
package controller;

import database.dao.RouteDAO;
import database.dao.RouteDAOImpl;
import model.entities.Route;
import view.AdminHomePageView;
import view.RouteManagementView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class RouteManagementController {
    private RouteManagementView view;
    private RouteDAO routeDAO;

    public RouteManagementController(RouteManagementView view) {
        this.view = view;
        this.routeDAO = new RouteDAOImpl();

        loadRoutes();

        view.addRouteButton.addActionListener(e -> addRoute());
        view.deleteRouteButton.addActionListener(e -> deleteRoute());
        view.backButton.addActionListener(e -> {
            view.dispose();
            AdminHomePageView adminHomePageView = new AdminHomePageView();
            new AdminHomePageController(adminHomePageView);
            adminHomePageView.setVisible(true);
        });
    }

    private void loadRoutes() {
        try {
            List<Route> routes = routeDAO.getAll();
            DefaultTableModel model = (DefaultTableModel) view.routeTable.getModel();
            model.setRowCount(0);
            for (Route route : routes) {
                model.addRow(new Object[]{
                        route.getRouteId(),
                        route.getTrainName(),
                        route.getSourceStation(),
                        route.getDestinationStation(),
                        route.getDepartureTime(),
                        route.getArrivalTime()
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view, "Failed to load routes.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addRoute() {
        String trainName = view.trainNameField.getText().trim();
        String source = view.sourceStationField.getText().trim();
        String destination = view.destinationStationField.getText().trim();
        String departureTime = view.departureTimeField.getText().trim();
        String arrivalTime = view.arrivalTimeField.getText().trim();

        if (trainName.isEmpty() || source.isEmpty() || destination.isEmpty() || departureTime.isEmpty() || arrivalTime.isEmpty()) {
            JOptionPane.showMessageDialog(view, "All fields must be filled.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            java.util.Date utilDeparture = formatter.parse(departureTime);
            java.util.Date utilArrival = formatter.parse(arrivalTime);
            Timestamp sqlDeparture = new Timestamp(utilDeparture.getTime());
            Timestamp sqlArrival = new Timestamp(utilArrival.getTime());

            Route route = new Route(0, trainName, source, destination, sqlDeparture, sqlArrival);
            routeDAO.add(route);
            JOptionPane.showMessageDialog(view, "Route added successfully.");
            loadRoutes();
            clearFields();
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(view, "Invalid date format. Use yyyy-MM-dd HH:mm.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view, "Failed to add route.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteRoute() {
        int selectedRow = view.routeTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Please select a route to delete.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int routeId = (int) view.routeTable.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(view, "Are you sure you want to delete this route?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                routeDAO.delete(routeId);
                JOptionPane.showMessageDialog(view, "Route deleted successfully.");
                loadRoutes();
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(view, "Failed to delete route. Ensure no tickets are associated.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void clearFields() {
        view.trainNameField.setText("");
        view.sourceStationField.setText("");
        view.destinationStationField.setText("");
        view.departureTimeField.setText("");
        view.arrivalTimeField.setText("");
    }
}
