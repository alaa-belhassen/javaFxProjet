package tn.esprit.javafxproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import tn.esprit.javafxproject.models.User;
import tn.esprit.javafxproject.services.UserServiceImpl;
import tn.esprit.javafxproject.utils.DbConnection;

import java.io.IOException;

public class HelloController {

    UserServiceImpl UserService = new UserServiceImpl();
    DbConnection db = DbConnection.getInstance();


    @FXML
    private Label welcomeText;
    @FXML
    private TextField email;
    @FXML
    private TextField password;

    @FXML
    private Button connect;

    @FXML
    private Button CreateAccount;

    @FXML
    private Hyperlink resetPassword;

    @FXML
    private Button liste;
    @FXML
    private Button ajout;
    @FXML
    void DisplayUsers (ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        stage = (Stage) liste.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("UsersList.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    void connect (ActionEvent event) throws IOException {
        if (controle())
        { User u =UserService.authenticate(email.getText(),password.getText());
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Sidebar.fxml"));
            loader.load();
            SidebarController sidebarController = loader.getController();

            sidebarController.user=u;


            if( u.getRole().getIdRole()==1)
       {
           Stage stage;
           Parent root;
           stage = (Stage) connect.getScene().getWindow();
           root = FXMLLoader.load(getClass().getResource("AdminSidebar.fxml"));
           Scene scene = new Scene(root);
           stage.setScene(scene);
           stage.show();

       }
       else
        if( (u.getRole().getIdRole()==2)||(u.getRole().getIdRole()==3))
        {
            Stage stage;
            Parent root;
            stage = (Stage) connect.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("Sidebar.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            System.out.println(u);

            //Passage de variable


        }
       else {
           welcomeText.setText("Utilisateur inexistant ");
       } }
        else
        {welcomeText.setText("Veuillez remplir les champs ");}

    }
    @FXML
    void resetPassword (ActionEvent event)
    {
        try{ Stage stage;
            Parent root;
            stage = (Stage) connect.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("ResetPassword.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        catch(Exception errr){

        }
    }

    @FXML
    void ajout (ActionEvent event)
    {
        try{ Stage stage;
            Parent root;
            stage = (Stage) ajout.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("AjouterEquipe.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        catch(Exception errr){

        }
    }
    @FXML
    void creerCompte (ActionEvent event)
    {
        try{ Stage stage;
            Parent root;
            stage = (Stage) CreateAccount.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("AccountCreation.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        catch(Exception errr){

        }

    }
    public Boolean controle ()
    { Boolean test=true;

        String id = email.getText();
        String pass=password.getText();

        if ((id == ""))
        { test = false; }
        int count = 0;
        for (int i = 0; i < id.length(); i++)
        {
            if (id.charAt(i) == ' ')
            { count = count + 1; }
        }

        if (count == id.length())
        { test = false; }


        if ((pass == ""))
        { test = false; }
        int count1 = 0;
        for (int i = 0; i < pass.length(); i++)
        {
            if (pass.charAt(i) == ' ')
            { count = count + 1; }
        }

        if (count == pass.length())
        { test = false; }


        return test;}

    public static class ResetPasswordController {

    }
}