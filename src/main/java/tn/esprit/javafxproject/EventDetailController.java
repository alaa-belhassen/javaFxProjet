package tn.esprit.javafxproject;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import tn.esprit.javafxproject.models.Evenement;

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

public void setData(Evenement event){
    Image image=new Image(getClass().getResourceAsStream(event.getPhoto()));
    eventimage.setImage(image);
    eventdate.setText(event.getLibelle());
    eventduration.setText(String.valueOf(event.getDuration()));
    eventprice.setText(String.valueOf(event.getPrix()));
    eventplace.setText(event.getLieu());
}

}




