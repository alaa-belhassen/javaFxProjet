//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package tn.esprit.javafxproject;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import tn.esprit.javafxproject.models.Categorie;
import tn.esprit.javafxproject.models.Evenement;
import tn.esprit.javafxproject.services.EvenementServiceImpl;

public class EventsController implements Initializable {
    @FXML
    private HBox cardLayout;
    @FXML
    private GridPane eventContainer;
    private List<Evenement> evenements;
    private List<Evenement> evenementslist;
    public SidebarController sidebarController;



    public EventsController() {
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {




        try {
            this.evenements = new ArrayList(this.afficherHighlights());


            for(int i = 0; i < this.evenements.size(); ++i) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(this.getClass().getResource("card1.fxml"));
                HBox cardBox = (HBox)fxmlLoader.load();
                Card1Controller card1Controller = fxmlLoader.getController();
                card1Controller.eventsController = this;

                //nabaath f data mn card l eventdetail
                card1Controller.evenement=this.evenements.get(i);


                card1Controller.setData(this.evenements.get(i));
                this.cardLayout.getChildren().add(cardBox);
            }
        } catch (Exception var9) {
            var9.printStackTrace();
        }




        int column = 0;
        int row = 1;
        try{
            List<Evenement> evenements1 = new ArrayList<>(this.afficher());
            System.out.println(evenements1);
            for(Evenement event : evenements1){
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("CardEventGrid.fxml"));
                VBox vbox = fxmlLoader.load();
                CardEventGridController cardEventGridController = fxmlLoader.getController();
                cardEventGridController.eventsController = this;
                cardEventGridController.evenement = event;
                cardEventGridController.setData(event);
                if(column==4){
                    column=0;
                    ++row;
                }
                eventContainer.add(vbox,column++,row);
                GridPane.setMargin(vbox, new Insets(15));
            }

        }catch (Exception e) {
            throw new RuntimeException(e);
        }






    }
    @FXML
    void goToListReserver(MouseEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("MyListReservation.fxml"));
        Parent eventDetailRoot = (Parent)fxmlLoader.load();
        MyListReservationController myListReservationController = fxmlLoader.getController();
        myListReservationController.eventsController=this;

        this.sidebarController.borderPane.setCenter(eventDetailRoot);
    }





    private List<Evenement> afficher() throws SQLException {
        EvenementServiceImpl evenementService = new EvenementServiceImpl();
        return evenementService.getAll();
    }
  private List<Evenement> afficherHighlights() throws SQLException {
        EvenementServiceImpl evenementService = new EvenementServiceImpl();
        return evenementService.getListHighlight();
    }








    private List<Evenement> evenements() {
        List<Evenement> evenements = new ArrayList();
        Evenement evenement = new Evenement();
        evenement.setLibelle("Ons JABEUR & Coco GAUFF");
        evenement.setPhoto("/image/oj.jpg");
        evenement.setPrix(400.0F);
        evenements.add(evenement);
        evenement = new Evenement();
        evenement.setLibelle("Ons JABEUR & Coco GAUFF");
        evenement.setPhoto("/image/psg.jpg");
        evenement.setPrix(400.0F);
        evenements.add(evenement);
        evenement = new Evenement();
        evenement.setLibelle("Ons JABEUR & Coco GAUFF");
        evenement.setPhoto("/image/oj.jpg");
        evenement.setPrix(400.0F);
        evenements.add(evenement);
        return evenements;
    }
}
