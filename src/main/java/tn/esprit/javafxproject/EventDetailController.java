//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package tn.esprit.javafxproject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import tn.esprit.javafxproject.models.Categorie;
import tn.esprit.javafxproject.models.Evenement;
import tn.esprit.javafxproject.models.Reserver;
import tn.esprit.javafxproject.models.User;
import tn.esprit.javafxproject.services.EvenementServiceImpl;
import tn.esprit.javafxproject.services.ReservationServiceImpl;
import tn.esprit.javafxproject.utils.Status;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class EventDetailController  {
    @FXML
    private Label eventdate;
    @FXML
    private Label eventduration;
    @FXML
    private ImageView eventimage;
    @FXML
    private Label eventplace;
    @FXML
    private Label eventprice;
    public Card1Controller card1Controller;
    public CardEventGridController cardEventGridController;

    int nb_places;
    @FXML
    private Label titre;

    public EventDetailController() {

    }

    @FXML
    private Label places;

    @FXML
    void minus(MouseEvent event) {
        int currentValue = Integer.parseInt(places.getText());
        int newValue = currentValue - 1;

        // Ensure newValue is not below 0
        newValue = Math.max(newValue, 1);

        places.setText(String.valueOf(newValue));
        eventpricetotal1.setText(String.valueOf(Integer.parseInt(places.getText())*Float.parseFloat(eventprice.getText())));

    }
    @FXML
    private Button btn_plus;

    @FXML
    private Button btn_minus;
    @FXML
    void plus(MouseEvent event) {
        int currentValue = Integer.parseInt(places.getText());
        int newValue = currentValue + 1;

        newValue = Math.min(newValue, nb_places);

        places.setText(String.valueOf(newValue));

        eventpricetotal1.setText(String.valueOf(Integer.parseInt(places.getText())*Float.parseFloat(eventprice.getText())));




    }



    @FXML
    void goBack(MouseEvent event) throws IOException {

        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("Events.fxml"));
        //nekhou akber parent ml page events
        VBox vBox=  fxmlLoader.load();
        EventsController eventsController=fxmlLoader.getController();
        if(card1Controller != null ){
            eventsController.sidebarController= card1Controller.eventsController.sidebarController;
            card1Controller.eventsController.sidebarController.borderPane.setCenter(vBox);

        }else{
            eventsController.sidebarController= cardEventGridController.eventsController.sidebarController;
            cardEventGridController.eventsController.sidebarController.borderPane.setCenter(vBox);

        }
        //narjaa lel parent bl variable card1Controller
    }



    @FXML
    private Button btn_reserver;

    @FXML
    private Label categorie1;

    @FXML
    private Label eventpricetotal1;
    @FXML
    void addReserver(MouseEvent event) throws SQLException {
        ReservationServiceImpl reservationService=new ReservationServiceImpl();
        Reserver reserver=new Reserver();
        reserver.setDate(LocalDate.now());
       //partie useeer
        User user=new User();
        user.setIdUser(2);
        reserver.setIdUser(user);
        reserver.setStatus(Status.VALID.toString());
        reserver.setCodeQR("");
        reserver.setPrix_total(Integer.parseInt(places.getText())*Float.parseFloat(eventprice.getText()));

        reserver.setEvenement(evenement);
       reservationService.add(reserver);
    }

    @FXML
    private Label expired;


    Evenement evenement=new Evenement();
    public void setData(Evenement event) throws FileNotFoundException {
        Image image =  new Image(new FileInputStream(event.getPhoto()));
        this.eventimage.setImage(image);
        this.titre.setText(event.getLibelle());
        this.eventdate.setText(event.getDate_event().toString());
        this.eventduration.setText(String.valueOf(event.getTime_event()));
        this.eventprice.setText(String.valueOf(event.getPrix()));
        this.eventplace.setText(event.getLieu());
        this.categorie1.setText(event.getId_categorie().getNom());
        nb_places=event.getMax_places();
        this.categorie1.setText(event.getId_categorie().getNom());
        evenement=event;
        eventpricetotal1.setText(String.valueOf(Integer.parseInt(places.getText())*Float.parseFloat(eventprice.getText())));

        if(event.getMax_places()==0){

            btn_reserver.setDisable(true);
            expired.setVisible(true);
            expired.setText("Tickets épuisés");
            expired.setTextFill(Color.RED);
            btn_minus.setDisable(true);
            btn_plus.setDisable(true);

        }
    }


}
