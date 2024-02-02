package tn.esprit.javafxproject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import tn.esprit.javafxproject.models.Emoji;

import java.io.IOException;

public class SidebarController {
    @FXML
    private BorderPane borderPane;

    @FXML
    void goAcceuil(MouseEvent event) {

    }

    @FXML
    void goDeconect(MouseEvent event) {

    }

    @FXML
    void goDonation(MouseEvent event) throws IOException {
        loadPage("donation");
    }

    @FXML
    void goEvenement(MouseEvent event) throws IOException {
        loadPage("events");
    }

    @FXML
    void goReclamation(MouseEvent event) throws IOException {
        loadPage("EmoteAddCardAdmin");
    }

    @FXML
    void goShop(MouseEvent event) throws IOException {
        loadPage("EmojiAdminLV");
    }

    public void loadPage(String name) throws IOException {
        FXMLLoader root = new FXMLLoader(getClass().getResource(name+".fxml"));
        Parent parent = root.load();
        Object eventsController = root.getController();
        if(root.getController() instanceof EventsController){
            ((EventsController) eventsController).sidebarController= this;
            borderPane.setCenter(parent);
        }else if (root.getController() instanceof DonController){
           ((DonController) eventsController).sidebarController= this;
            borderPane.setCenter(parent);
        }else if (root.getController() instanceof EmoteAddCardAdmin){
            ((EmoteAddCardAdmin) eventsController).sidebarController= this;
            borderPane.setCenter(parent);
        }else if (root.getController() instanceof EmojiAdminLVController) {
            ((EmojiAdminLVController) eventsController).sidebarController = this;
            borderPane.setCenter(parent);
        }

        System.out.println(root);
    }
    public BorderPane getScreen() {
        return borderPane;
    }
}
