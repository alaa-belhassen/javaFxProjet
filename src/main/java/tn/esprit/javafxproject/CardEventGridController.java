package tn.esprit.javafxproject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import tn.esprit.javafxproject.models.Don;
import tn.esprit.javafxproject.models.Emoji;
import tn.esprit.javafxproject.models.Evenement;
import tn.esprit.javafxproject.services.EmojiServiceImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

public class CardEventGridController {
    @FXML
    private Button btnDonate;

    @FXML
    private Label exp;

    @FXML
    private Label name;

    @FXML
    private Label prix;

    @FXML
    private ImageView image = new ImageView();
    EventsController eventsController;
    public Evenement evenement;

    @FXML
    private ImageView imageCard;



    @FXML
    void goToEventDetail(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("EventDetail.fxml"));
        Parent eventDetailRoot = (Parent)fxmlLoader.load();
        EventDetailController eventDetailController = fxmlLoader.getController();
        eventDetailController.cardEventGridController=this;
        eventDetailController.setData(evenement);
        this.eventsController.sidebarController.borderPane.setCenter(eventDetailRoot);



    }
    public void setData(Evenement evenement) throws FileNotFoundException {
        // Image image =  new Image(new FileInputStream(emoji.getImageUrl()));

        Image image =  new Image(new FileInputStream(evenement.getPhoto()));
        this.imageCard.setImage(image);
        exp.setText(evenement.getLibelle());
        //name.setText(evenement.getDuration()+" minutes");
        name.setText(String.valueOf(evenement.getDate_event()));
        prix.setText(evenement.getPrix()+" TND");
    }


    public void loadPage(String name) throws IOException {
        FXMLLoader root = new FXMLLoader(getClass().getResource(name+".fxml"));
        Parent parent = root.load();

        System.out.println(parent);
    }
}
