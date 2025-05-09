
// Seat selection kismi hazir olmadigı ıcın hata veriyor
package controller;

import model.TrainJourney;
//import view.SeatSelectionView;
import view.SeatSelectionPageView;
import view.TrainSearchResultsView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

//private String departure, arrival, date, tripType;


public class TrainSearchResultsController {
    private TrainSearchResultsView view;
    private ArrayList<TrainJourney> journeys;
    private String departure, arrival, date, tripType;
    private int tickets;

    public TrainSearchResultsController(TrainSearchResultsView view, String departure, String arrival, String date, int tickets, String tripType) {
        this.view = view;
        this.departure = departure;
        this.arrival = arrival;
        this.date = date;
        this.tickets = tickets;
        this.tripType = tripType;

        this.journeys = createDummyData(); // veya getFilteredJourneys() kullanıyorsan ona göre
        loadTable();

        view.proceedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                proceed(tickets);
            }
        });

    }



    private void loadTable() {
        DefaultTableModel model = (DefaultTableModel) view.journeyTable.getModel();
        for (TrainJourney journey : journeys) {
            model.addRow(new Object[]{
                    journey.getTrainName(),
                    journey.getAvailableSeats() + "/" + journey.getTotalSeats(),
                    journey.getDepartureTime(),
                    journey.getDuration(),
                    journey.getRoute()
            });
        }
    }

//    private void proceed() {
//        int selectedRow = view.journeyTable.getSelectedRow();
//        if (selectedRow == -1) {
//            JOptionPane.showMessageDialog(view, "Lütfen bir sefer seçin.", "Uyarı", JOptionPane.WARNING_MESSAGE);
//        } else {
//            // Seçilen tren bilgisini al
//            TrainJourney selectedJourney = journeys.get(selectedRow);
//
//            // Örnek: 1 kişi için koltuk seçimi yapılıyor gibi düşünelim (ileride HomePage'deki kişi sayısını da taşıyabiliriz)
//            int ticketCount = 1; // Şimdilik sabit, istersen HomePage'den gelen gerçek ticketCount bilgisini taşıyabiliriz
//
//            view.dispose(); // Bu ekranı kapat
//            SeatSelectionView seatSelectionView = new SeatSelectionView(selectedJourney.getTotalSeats());
//            new SeatSelectionController(seatSelectionView, ticketCount);
//            seatSelectionView.setVisible(true);
//        }
//    }
private void proceed(int ticketCount) {
    int selectedRow = view.journeyTable.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(view, "Please select a train journey.", "Warning", JOptionPane.WARNING_MESSAGE);
    } else {
        // Get selected train journey
        TrainJourney selectedJourney = journeys.get(selectedRow);



        view.dispose(); // Close current view
        SeatSelectionPageView seatSelectionView = new SeatSelectionPageView();
        new SeatSelectionPageController(seatSelectionView, departure, arrival, date, tickets, tripType);

        seatSelectionView.setVisible(true);
    }
}


    //dummy veri içinden filtreleme yapan metod
    private ArrayList<TrainJourney> getFilteredJourneys(String departure, String arrival, String date) {
        ArrayList<TrainJourney> allJourneys = createDummyData();
        ArrayList<TrainJourney> filtered = new ArrayList<>();

        for (TrainJourney j : allJourneys) {
            if (j.getRoute().toLowerCase().contains(departure.toLowerCase())
                    && j.getRoute().toLowerCase().contains(arrival.toLowerCase())) {
                filtered.add(j);
            }
        }

        return filtered;
    }



    private ArrayList<TrainJourney> createDummyData() {
        ArrayList<TrainJourney> list = new ArrayList<>();
        list.add(new TrainJourney("YHT 802", 75, 100, "08:00", "2 saat 30 dakika", "Ankara -> Eskişehir"));
        list.add(new TrainJourney("Anadolu Express", 20, 80, "10:00", "4 saat", "İstanbul -> Ankara"));
        list.add(new TrainJourney("Pamukkale Ekspresi", 50, 90, "12:30", "6 saat", "Denizli -> İzmir"));
        list.add(new TrainJourney("Doğu Ekspresi", 5, 100, "15:45", "24 saat", "Ankara -> Kars"));
        return list;
    }

}
