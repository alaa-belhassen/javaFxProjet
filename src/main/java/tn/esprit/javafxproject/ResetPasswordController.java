package tn.esprit.javafxproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.javafxproject.models.User;
import tn.esprit.javafxproject.services.UserServiceImpl;
import tn.esprit.javafxproject.utils.DbConnection;

public class ResetPasswordController {

    UserServiceImpl UserService = new UserServiceImpl();
    DbConnection db = DbConnection.getInstance();
    @FXML
    private Label Email;
    public Boolean verif_mail()
    {  Boolean test= true;
        String champ=email.getText();
        if(champ=="")
        {test=false;}
        int count = 0;
        for (int i = 0; i < champ.length(); i++)
        {
            if (champ.charAt(i) == ' ')
            { count = count + 1; }
        }

        if (count == champ.length())
        { test = false; }
        int count2 = 0;
        for (int i = 0; i < champ.length(); i++)
        {
            if (champ.charAt(i) == '@')
            { count2 = count2 + 1; }


        }

        if (count2 ==0)
        { test = false;
        }
        for (int i = 0; i < champ.length(); i++)
        {
            if ( (champ.length()<8))
                test = false;
        }
        return test;}
    @FXML
    private TextField email;

    @FXML
    private TextField password;
    @FXML
    private Button resetPassword;
    @FXML
    private Button retour;

    @FXML
    void retour (ActionEvent event)
    {
        try{ Stage stage;
            Parent root;
            stage = (Stage) retour.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("Login.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        catch(Exception errr){

        }
    }

    @FXML
    void resetPassword (ActionEvent event) {

        if( !verif_mail())
        {

            Alert al= new Alert(Alert.AlertType.WARNING);
            al.setTitle("Alert");al.setContentText("Vérifiez votre adresse email ");al.show();  }


        else if(verif_mail())
        {
            System.out.println("Bonjour");
            if (UserService.emailExists(email.getText()))
        { UserService.ResetPassword(email.getText());
            Alert al= new Alert(Alert.AlertType.CONFIRMATION);
            al.setTitle("Alert");al.setContentText("Votre nouveau mot de passe est envoyé par email");al.show();}
        }
        else
        {
            Alert al= new Alert(Alert.AlertType.CONFIRMATION);
            al.setTitle("Alert");al.setContentText("Vérifier l'adresse email ");al.show();
        }


    }

}
