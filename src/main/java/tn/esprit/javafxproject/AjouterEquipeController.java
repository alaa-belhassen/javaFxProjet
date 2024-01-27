package tn.esprit.javafxproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import tn.esprit.javafxproject.models.User;
import tn.esprit.javafxproject.services.UserServiceImpl;
import tn.esprit.javafxproject.utils.DbConnection;

public class AjouterEquipeController {

    UserServiceImpl UserService = new UserServiceImpl();
    DbConnection db = DbConnection.getInstance();
    @FXML
    private Label result;
    @FXML
    private TextField nom;
    @FXML
    private TextField email;
    @FXML
    private TextField telephone;
    @FXML
    private TextField adresse;


    public Boolean verif_Telephone()
    { Boolean test=true;
        String id=telephone.getText();
        if(id=="")
        {test=false;}
        int count = 0;
        for (int i = 0; i < id.length(); i++)
        {
            if (id.charAt(i) == ' ')
            { count = count + 1; }
        }

        if (count == id.length())
        { test = false; }
        for (int i = 0; i < id.length(); i++)
        {
            if ((id.charAt(i) < '0') || (id.charAt(i) > '9') || (id.length() != 8))
                test = false;}
        return test;}


    public Boolean verif_nom(TextField t)
    {  Boolean test= true;
        String champ=t.getText();
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
        for (int i = 0; i < champ.length(); i++)
        {
            if (((champ.charAt(i)) < 'A') || (champ.charAt(i) > 'Z')&&((champ.charAt(i)) < 'a') || (champ.charAt(i) > 'z') || (champ.length()<2))
                test = false;}


        return test;}
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
    void AddUser7
            (ActionEvent event) {
        User user = new User();
        user.setNom(nom.getText());
        user.setEmail(email.getText());
        user.setTelephone(telephone.getText());
        user.setAdresse(adresse.getText());

        if(UserService.emailExists(user.getEmail()))
        {

            Alert al= new Alert(Alert.AlertType.WARNING);
            al.setTitle("Alert");al.setContentText("Participant existant !!! ");al.show();  }
        else if(!verif_nom(nom))
        {Alert al= new Alert(Alert.AlertType.WARNING);
            al.setTitle("Alert");al.setContentText("Vérifier le nom saisi ");al.show();  }
        else if(!verif_mail())
        {Alert al= new Alert(Alert.AlertType.WARNING);
            al.setTitle("Alert");al.setContentText("Ce n'est pas une adresse électronique");al.show();  }
        else if(! verif_Telephone())
        {Alert al= new Alert(Alert.AlertType.WARNING);
            al.setTitle("Alert");al.setContentText("Vérifier le numéro de téléphone saisi ");al.show();  }

        else if(verif_Telephone()&&verif_nom(nom)&&verif_mail())
        {UserService.add(user);
            Alert al= new Alert(Alert.AlertType.CONFIRMATION);
            al.setTitle("Alert");al.setContentText("Votre compte est bien enregistré");al.show();
        }


    }
}
