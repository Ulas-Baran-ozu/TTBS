
// Seat selection kismi hazir olmadigı ıcın hata veriyor
package controller;

import model.TrainJourney;
//import view.SeatSelectionView;
import view.TrainSearchResultsView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class TrainSearchResultsController {
    private TrainSearchResultsView view;
    private ArrayList<TrainJourney> journeys;

    public TrainSearchResultsController(TrainSearchResultsView view) {
        this.view = view;
        this.journeys = createDummyData();

        loadTable();

        view.proceedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                proceed();
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

    private ArrayList<TrainJourney> createDummyData() {
        ArrayList<TrainJourney> list = new ArrayList<>();
        list.add(new TrainJourney("YHT 802", 75, 100, "08:00", "2 saat 30 dakika", "Ankara -> Eskişehir"));
        list.add(new TrainJourney("Anadolu Express", 20, 80, "10:00", "4 saat", "İstanbul -> Ankara"));
        list.add(new TrainJourney("Pamukkale Ekspresi", 50, 90, "12:30", "6 saat", "Denizli -> İzmir"));
        list.add(new TrainJourney("Doğu Ekspresi", 5, 100, "15:45", "24 saat", "Ankara -> Kars"));
        return list;
    }
}
