package tn.esprit.javafxproject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import tn.esprit.javafxproject.models.Evenement;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class EventsController implements Initializable {

    @FXML
    private HBox CardLayout;
    List<Evenement> events ;
    ClientLayoutController clientLayoutController;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        events = new ArrayList<Evenement>(getEvents());
        try {
            for (int i = 0; i < events.size(); i++) {
                FXMLLoader fxml = new FXMLLoader();
                fxml.setLocation(getClass().getResource("card.fxml"));
                HBox cardBox = fxml.load();
                CardController cardController = fxml.getController();
                cardController.setData(events.get(i));
                CardLayout.getChildren().add(cardBox);
            }

        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public HBox getCardLayout() {
        return CardLayout;
    }

    private List<Evenement> getEvents(){
        List<Evenement> events= new ArrayList<Evenement>();
        Evenement event1 = new Evenement();
        event1.setLibelle("alaabook");
        event1.setLieu("menzeh");
        event1.setDate_event(LocalDate.of(1967, 06, 22));
        events.add(event1);
        Evenement event2 = new Evenement();
        event2.setLibelle("alaabook");
        event2.setLieu("menzeh");
        event2.setDate_event(LocalDate.of(1967, 06, 22));
        events.add(event2);
        Evenement event3 = new Evenement();
        event3.setLibelle("alaabook");
        event3.setLieu("menzeh");
        event3.setDate_event(LocalDate.of(1967, 06, 22));
        events.add(event3);
        Evenement event4 = new Evenement();
        event4.setLibelle("alaabook");
        event4.setLieu("menzeh");
        event4.setDate_event(LocalDate.of(1967, 06, 22));
        events.add(event4);
        return events ;
    }
}
