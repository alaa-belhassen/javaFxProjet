package tn.esprit.javafxproject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


import java.io.IOException;


public class ClientLayoutController  {

    @FXML // fx:id="vbEvents"
    private HBox vbEvents;
    @FXML
    public BorderPane screen;

    @FXML
    void initialize() {
        assert screen != null : "fx:id=\"screen\" was not injected: check your FXML file 'clientLayout.fxml'.";

    }

    @FXML
     public void goEvents(javafx.scene.input.MouseEvent event) throws IOException {
        this.loadPage("events");
    }
    @FXML
    void goDonation(javafx.scene.input.MouseEvent event) throws IOException {
        this.loadPage("donation");

    }

    @FXML
    void goHistoriqueDonation(MouseEvent event) throws IOException {
        this.loadPage("historiqueDonation");

    }


    public void loadPage(String name) throws IOException {
        FXMLLoader root = new FXMLLoader(getClass().getResource(name+".fxml"));
        Parent parent = root.load();
        Object eventsController = root.getController();
        if(root.getController() instanceof EventsController){
            ((EventsController) eventsController).clientLayoutController= this;
            screen.setCenter(parent);
        }else if (root.getController() instanceof DonController){
            ((DonController) eventsController).clientLayoutController= this;
            screen.setCenter(parent);
        }else if (root.getController() instanceof DonationHistoriqueController){
            ((DonationHistoriqueController) eventsController).clientLayoutController= this;
            screen.setCenter(parent);
        }

        System.out.println(root);
    }
    public BorderPane getScreen() {
        return screen;
    }
}