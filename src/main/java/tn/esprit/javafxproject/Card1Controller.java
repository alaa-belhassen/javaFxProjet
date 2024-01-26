package tn.esprit.javafxproject;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import tn.esprit.javafxproject.models.Evenement;

public class Card1Controller {



    @FXML
    private HBox box;

    @FXML
    private Button btn_details;

    @FXML
    private Label date_id;

    @FXML
    private ImageView image;

    @FXML
    private Label tarif_id;

    @FXML
    private Label titre_id;

    @FXML
    void goToEventDetail(MouseEvent event) {

    }

    private String [] colors={"B9E5FF","BDB2FE","FB9AA8","FF5056"};
    public void setData(Evenement evenement){


        // Image image1=new Image(getClass().getResourceAsStream(String.valueOf(evenement.getPhoto())));
        //image.setImage(image1);



        titre_id.setText(evenement.getLibelle());
        date_id.setText(evenement.getDate_event().toString());
        tarif_id.setText(String.valueOf(evenement.getPrix()));


        box.setStyle("-fx-background-color: #"+ colors[(int)(Math.random()*colors.length)]+";"+
        "-fx-background-radius: 15;"+
        "-fx-effect: dropShadow(three-pass-box,rgba(0,0,0,0),10,0,0,10);");


    }


}
