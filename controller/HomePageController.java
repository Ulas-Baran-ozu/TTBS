package controller;

import view.HomePageView;

import javax.swing.*;

public class HomePageController {
    private HomePageView homePageView;

    public HomePageController(HomePageView homePageView) {
        this.homePageView = homePageView;

        homePageView.profileButton.addActionListener(e -> JOptionPane.showMessageDialog(homePageView, "Profil sayfası açılacak."));
        homePageView.ticketsButton.addActionListener(e -> JOptionPane.showMessageDialog(homePageView, "Biletlerim sayfası açılacak."));
        homePageView.logoutButton.addActionListener(e -> logout());
        homePageView.searchButton.addActionListener(e -> searchTickets());
    }

    private void logout() {
        JOptionPane.showMessageDialog(homePageView, "Çıkış yapılıyor.");
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
        new controller.TrainSearchResultsController(resultsView);
        resultsView.setVisible(true);

    }
}