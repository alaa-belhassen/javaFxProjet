package tn.esprit.javafxproject;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import tn.esprit.javafxproject.models.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class CardController {

    @FXML
    private Button btnBuy;

    @FXML
    private Label date_event;

    @FXML
    private ImageView image;

    @FXML
    private Label lieu;

    @FXML
    private Label max_places;

    @FXML
    private Label title;

    public void setData(Evenement event) throws FileNotFoundException {
        Image image =  new Image(new FileInputStream("C:/Users/msi/Desktop/projectjavaGeeksFInale/javaFxProject/src/main/resources/tn/esprit/javafxproject/img/history.png"));
        title.setText(event.getLibelle());
        lieu.setText(event.getLieu());
        this.image.setImage(image);
        date_event.setText(event.getDate_event().toString());
        
    }
}
