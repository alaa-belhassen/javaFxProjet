package tn.esprit.javafxproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tn.esprit.javafxproject.models.Role;
import tn.esprit.javafxproject.models.User;
import tn.esprit.javafxproject.services.UserServiceImpl;
import tn.esprit.javafxproject.utils.DbConnection;
import tn.esprit.javafxproject.utils.Status;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1315, 890);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
       //UserServiceImpl us = new UserServiceImpl();
        launch();
       // Role r = new Role (1,"admin","active");
       // User u = new User ("sarraa.aloui@gmail.com","sarah", Status.VALID.toString(),r,"password");
       //boolean ajoutReussi = us.CreateAccount(u);

    }}