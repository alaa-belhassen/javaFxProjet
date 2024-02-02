package tn.esprit.javafxproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tn.esprit.javafxproject.utils.DbConnection;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        DbConnection.getInstance();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Sidebar.fxml"));
        Scene scene = new Scene((Parent)fxmlLoader.load(), 1315.0, 890.0);
        stage.setTitle("Acceuil");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();

    }


}

