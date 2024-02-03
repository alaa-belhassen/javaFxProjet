package tn.esprit.javafxproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tn.esprit.javafxproject.utils.DbConnection;
import tn.esprit.javafxproject.models.Achat;
import tn.esprit.javafxproject.models.Produit;

import tn.esprit.javafxproject.services.ServiceAchat;
import tn.esprit.javafxproject.services.ServiceProduit;
import tn.esprit.javafxproject.utils.DbConnection;

import java.util.ArrayList;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        DbConnection.getInstance();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Sidebar.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1315, 890);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}



