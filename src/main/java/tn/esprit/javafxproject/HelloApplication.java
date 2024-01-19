package tn.esprit.javafxproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tn.esprit.javafxproject.models.Role;
import tn.esprit.javafxproject.models.User;
import tn.esprit.javafxproject.services.UserServiceImpl;
import tn.esprit.javafxproject.utils.DbConnection;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
       // launch();
        DbConnection db = DbConnection.getInstance();
        UserServiceImpl us = new UserServiceImpl ();

        Role r = new Role (1,"admin","active");
        User u = new User ("salma@gmail.com","khouloud","active",r,"password");
       // boolean ajoutReussi = us.add(u);


   if( us.changePassword("salma@gmail.com","password","pass"))
   {
       System.out.println("mdp changed");
   }else
   { System.out.println(" mdp no changed");}

    boolean connecte = us.authenticate("salma@gmail.com", "pass");
        if (connecte)
    {
        System.out.println("connected");
    }else
    { System.out.println(" no connected");}
}}