package controller;

import model.entities.Route;
import model.entities.User;
import model.Session;
import view.HomePageView;
import view.PaymentSuccessfulView;

import java.util.List;

public class PaymentSuccessfulController {
    private PaymentSuccessfulView view;

    public PaymentSuccessfulController(Route route, List<Integer> seatNumbers, int ticketCount) {
        // Kullanıcıyı al
        User user = Session.getCurrentUser();

        // Dinamik bilgi metnini oluştur
        StringBuilder ticketText = new StringBuilder("Ödeme Başarılı!\n\nBilet Bilgileri:\n");
        ticketText.append("- Kullanıcı: ").append(user.getFirstName()).append(" ").append(user.getLastName()).append("\n");
        ticketText.append("- Tren: ").append(route.getTrainName()).append("\n");
        ticketText.append("- Tarih: ").append(route.getDepartureTime().toLocalDateTime().toLocalDate()).append("\n");
        ticketText.append("- Saat: ").append(route.getDepartureTime().toLocalDateTime().toLocalTime()).append("\n");
        ticketText.append("- Güzergah: ").append(route.getSourceStation()).append(" → ").append(route.getDestinationStation()).append("\n");
        ticketText.append("- Koltuk(lar): ").append(seatNumbers.toString());

        // View oluştur ve başlat
        view = new PaymentSuccessfulView(ticketText.toString());
        view.setVisible(true);

        view.backButton.addActionListener(e -> {
            view.dispose();
            HomePageView homeView = new HomePageView();
            new HomePageController(homeView);
            homeView.setVisible(true);
        });
    }
}