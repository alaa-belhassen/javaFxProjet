//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package tn.esprit.javafxproject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import tn.esprit.javafxproject.models.Evenement;

public class Card1Controller {
    @FXML
    private HBox box;
    @FXML
    private Button btn_details;
    @FXML
    private Label date_id;
    @FXML
    private ImageView imageCard;
    @FXML
    private Label tarif_id;
    @FXML
    private Label titre_id;
    public EventsController eventsController;

    public Evenement evenement;



   private String[] colors = new String[]{ "D0F0C0","F0FFF0"};

    public Card1Controller() {
    }

    @FXML
    void goToEventDetail(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("EventDetail.fxml"));
        Parent eventDetailRoot = (Parent)fxmlLoader.load();
        EventDetailController eventDetailController = fxmlLoader.getController();
        eventDetailController.card1Controller=this;
        eventDetailController.setData(evenement);
        this.eventsController.sidebarController.borderPane.setCenter(eventDetailRoot);



    }

    public void setData(Evenement evenement) throws FileNotFoundException {
        Image image =  new Image(new FileInputStream(evenement.getPhoto()));
        this.imageCard.setImage(image);
        this.titre_id.setText(evenement.getLibelle());
        this.date_id.setText(evenement.getDate_event().toString());
        this.tarif_id.setText(String.valueOf(evenement.getPrix()));
        HBox var10000 = this.box;
        String[] var10001 = this.colors;
       int var10002 = (int)(Math.random() * (double)this.colors.length);
       var10000.setStyle("-fx-background-color: #" + var10001[var10002] + ";-fx-background-radius: 15;-fx-effect: dropShadow(three-pass-box,rgba(0,0,0,0),10,0,0,10);");
    }
}
