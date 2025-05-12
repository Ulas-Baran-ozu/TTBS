// HomePageController.java
package controller;

import view.HomePageView;
import view.MyTicketsPageView;
import view.ProfilePageView;
import model.entities.User;
import model.Session;

import javax.swing.*;
import java.text.ParseException;

public class HomePageController {
    private final HomePageView homePageView;

    public HomePageController(HomePageView homePageView) {
        this.homePageView = homePageView;
        User user = Session.getCurrentUser();

        homePageView.profileButton.addActionListener(e -> {
            homePageView.dispose();
            ProfilePageView profileView = new ProfilePageView();
            new ProfilePageController(profileView);
            profileView.setVisible(true);
        });

        homePageView.ticketsButton.addActionListener(e -> {
            homePageView.dispose();
            MyTicketsPageView ticketsView = new MyTicketsPageView();
            new MyTicketsPageController(ticketsView);
            ticketsView.setVisible(true);
        });

        homePageView.logoutButton.addActionListener(e -> logout());

        homePageView.searchButton.addActionListener(e -> {
            try {
                searchTickets();
            } catch (ParseException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(homePageView, "Tarih formatı hatalı!", "Hata", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void logout() {
        JOptionPane.showMessageDialog(homePageView, "Çıkış yapılıyor.");
        Session.clear();
        homePageView.dispose();
        view.LoginView loginView = new view.LoginView();
        new LoginController(loginView);
        loginView.setVisible(true);
    }

    private void searchTickets() throws ParseException {
        String departure = homePageView.departureField.getText().trim();
        String arrival = homePageView.arrivalField.getText().trim();
        String date = homePageView.dateField.getText().trim();
        int tickets = (Integer) homePageView.ticketCountSpinner.getValue();

        if (departure.isEmpty() || arrival.isEmpty() || date.isEmpty()) {
            StringBuilder message = new StringBuilder("Lütfen aşağıdaki alanları doldurun:\n");
            if (departure.isEmpty()) message.append("- Kalkış Noktası\n");
            if (arrival.isEmpty()) message.append("- Varış Noktası\n");
            if (date.isEmpty()) message.append("- Tarih\n");

            JOptionPane.showMessageDialog(homePageView, message.toString(), "Eksik Bilgi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        homePageView.dispose();
        view.TrainSearchResultsView resultsView = new view.TrainSearchResultsView();
        new controller.TrainSearchResultsController(resultsView, departure, arrival, date, tickets);
        resultsView.setVisible(true);
    }
}