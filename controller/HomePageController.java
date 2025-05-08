package controller;

import view.HomePageView;
import view.MyTicketsPageView;
import view.ProfilePageView;
import model.entities.User;
import model.Session;
import javax.swing.*;

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
        homePageView.logoutButton.addActionListener(e -> logout());
            homePageView.dispose();
            MyTicketsPageView ticketsView = new MyTicketsPageView();
            int currentUserId = 1; // Şimdilik sabit. Giriş yapan kullanıcıdan alınmalı.
            new MyTicketsPageController(ticketsView, currentUserId);
            ticketsView.setVisible(true);
        });
        homePageView.logoutButton.addActionListener(e -> logout());
        homePageView.searchButton.addActionListener(e -> searchTickets());
    }

//    private void logout() {
//        JOptionPane.showMessageDialog(homePageView, "Çıkış yapılıyor.");
//        homePageView.dispose();
//        view.LoginView loginView = new view.LoginView();
//        new LoginController(loginView);
//        loginView.setVisible(true);
//    }
    private void logout() {
        JOptionPane.showMessageDialog(homePageView, "Çıkış yapılıyor.");
        model.Session.clear();
        homePageView.dispose();
        view.LoginView loginView = new view.LoginView();
        new LoginController(loginView);
        loginView.setVisible(true);
    }

    private void searchTickets() {
        String departure = homePageView.departureField.getText();
        String arrival = homePageView.arrivalField.getText();
        String date = homePageView.dateField.getText();
        int tickets = (Integer) homePageView.ticketCountSpinner.getValue();
        String tripType = homePageView.oneWayRadio.isSelected() ? "Tek Yön" : "Gidiş-Dönüş";

        JOptionPane.showMessageDialog(homePageView, "Aranıyor: " + departure + " -> " + arrival + ", Tarih: " + date + ", Bilet: " + tickets + ", Tip: " + tripType);

        homePageView.dispose();
        view.TrainSearchResultsView resultsView = new view.TrainSearchResultsView();
        new controller.TrainSearchResultsController(resultsView, departure, arrival, date, tickets, tripType);
        resultsView.setVisible(true);

    }
}