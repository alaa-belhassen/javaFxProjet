package tn.esprit.javafxproject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import tn.esprit.javafxproject.models.Evenement;
import tn.esprit.javafxproject.services.EvenementServiceImpl;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class EventsController implements Initializable {

    @FXML
    private HBox cardLayout;


    @FXML
    private GridPane eventContainer;
    private List<Evenement> evenements;
    private List<Evenement> evenementslist;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)  {



        try {
            evenements=new ArrayList<>(afficher());
            int column=0;
            int row=1;
            for (int i = 0; i < evenements.size() ; i++) {
            FXMLLoader fxmlLoader=new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("card1.fxml"));

                HBox cardBox= fxmlLoader.load();
                Card1Controller card1Controller=fxmlLoader.getController();
                card1Controller.setData(evenements.get(i));
                cardLayout.getChildren().add(cardBox);


            }


            
            
            }catch (Exception e) {
                e.printStackTrace();

        }


    }
private List<Evenement> afficher() throws SQLException {
    EvenementServiceImpl evenementService=new EvenementServiceImpl();
    return evenementService.getAll();

}


private List<Evenement> evenements(){
    List<Evenement> evenements=new ArrayList<>();
    Evenement evenement=new Evenement();
    evenement.setLibelle("Ons JABEUR & Coco GAUFF");
    evenement.setPhoto("/image/oj.jpg");
    evenement.setPrix(400.0f);
    evenements.add(evenement);

    evenement=new Evenement();
    evenement.setLibelle("Ons JABEUR & Coco GAUFF");
    evenement.setPhoto("/image/psg.jpg");
    evenement.setPrix(400.0f);
    evenements.add(evenement);

    evenement=new Evenement();
    evenement.setLibelle("Ons JABEUR & Coco GAUFF");
    evenement.setPhoto("/image/oj.jpg");
    evenement.setPrix(400.0f);
    evenements.add(evenement);

    return evenements;
}


}
