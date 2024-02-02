//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package tn.esprit.javafxproject;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

public class SidebarController {
    @FXML
    public BorderPane borderPane;

    public SidebarController() {
    }

    @FXML
    void goAcceuil(MouseEvent event) {
    }

    @FXML
    void goDeconect(MouseEvent event) {
    }

    @FXML
    void goDonation(MouseEvent event) {
    }

    @FXML
    void goEvenement(MouseEvent event) throws IOException {
        //scenario user
        this.loadPage("Events");

        // scenario admin
        //this.loadPage("AdminEvents");

        //scenario equipe
        //this.loadPage("ListEventsEquipes");


    }

    @FXML
    void goReclamation(MouseEvent event) throws IOException {
        // scenario admin
        this.loadPage("AdminEvents");

    }

    @FXML
    void goShop(MouseEvent event) throws IOException {
        //scenario equipe
        this.loadPage("ListEventsEquipes");

    }

    public void loadPage(String name) throws IOException {
        FXMLLoader root = new FXMLLoader(this.getClass().getResource(name + ".fxml"));
        Parent parent = (Parent)root.load();
        Object eventsController = root.getController();
        if (root.getController() instanceof EventsController) {
            ((EventsController)eventsController).sidebarController = this;
            this.borderPane.setCenter(parent);
        }else  if (root.getController() instanceof AdminEventsController) {
            ((AdminEventsController)eventsController).sidebarController = this;
            this.borderPane.setCenter(parent);
        }else  if (root.getController() instanceof ListEventsEquipesController) {
            ((ListEventsEquipesController)eventsController).sidebarController = this;
            this.borderPane.setCenter(parent);
        }

        System.out.println(root);
    }






}
