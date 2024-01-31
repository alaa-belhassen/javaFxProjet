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
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1315, 890);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {


        DbConnection db = DbConnection.getInstance();
        ServiceAchat SA = new ServiceAchat();
        ServiceProduit SP = new ServiceProduit();
        Produit p = new Produit();
        p.setProductID(19);
        SP.add(p);

        //Achat a = new Achat ();
        //a.setIdAchat(4);
        //LocalDate dateAchat = LocalDate.now();
        //a.setDateAchat(dateAchat);
        //a.setIdClient(2);
        //SA.update(a);
        //SA.add(a);
        //SA.delete(4);
        ArrayList<Achat> LesAchats = new ArrayList<>();
        LesAchats = SA.getAll();
        for (Achat ac : LesAchats) {
            System.out.println(ac.toString());

        }

        ArrayList<Produit> Lesproduits = new ArrayList<>();
        Lesproduits = SP.getAll();
        for (Produit pc : Lesproduits) {
            System.out.println(pc.toString());

        }

    }}