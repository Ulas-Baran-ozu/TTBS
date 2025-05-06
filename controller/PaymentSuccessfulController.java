package controller;

import view.HomePageView;
import view.PaymentSuccessfulView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PaymentSuccessfulController {
    private PaymentSuccessfulView view;

    public PaymentSuccessfulController(PaymentSuccessfulView view) {
        this.view = view;

        view.backButton.addActionListener(e -> {
            view.dispose();
            HomePageView homeView = new HomePageView();
            new HomePageController(homeView);
            homeView.setVisible(true);
        });

    }
}
