package tn.esprit.javafxproject;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import tn.esprit.javafxproject.models.Evenement;

public class Card1Controller {


    @FXML
    private Label author;

    @FXML
    private Label bookName;

    @FXML
    private HBox box;

    @FXML
    private ImageView image;

    private String [] colors={"B9E5FF","BDB2FE","FB9AA8","FF5056"};
    public void setData(Evenement evenement){
       // Image image1=new Image(getClass().getResourceAsStream(String.valueOf(evenement.getPhoto())));
        //image.setImage(image1);

        bookName.setText(evenement.getLibelle());
        author.setText("2024-01-20");
        box.setStyle("-fx-background-color: #"+ colors[(int)(Math.random()*colors.length)]+";"+
        "-fx-background-radius: 15;"+
        "-fx-effect: dropShadow(three-pass-box,rgba(0,0,0,0),10,0,0,10);");


    }


}
