package tn.esprit.javafxproject;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import tn.esprit.javafxproject.models.Evenement;
import tn.esprit.javafxproject.models.Reserver;
import tn.esprit.javafxproject.models.User;
import tn.esprit.javafxproject.services.ReservationServiceImpl;
import tn.esprit.javafxproject.utils.DbConnection;
import tn.esprit.javafxproject.utils.Status;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MyListReservationController implements Initializable {


    public EventsController eventsController;;

    @FXML
    private TableView<Reserver> table;

    @FXML
    private TableColumn<Reserver, LocalDate> date_Res;

    @FXML
    private TableColumn<Reserver, Integer> id_Res;

    @FXML
    private TableColumn<Reserver, Float> prix_Res;

    @FXML
    private TableColumn<Reserver, String> status_Res;

    @FXML
    private TableColumn<Reserver, String > titre_Res;

    @FXML
    private TableColumn<Reserver, String> categorie_Res;

    @FXML
    private TableColumn<Reserver, Void> btn;




    @FXML
    void goBack(MouseEvent event) throws IOException {

        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("Events.fxml"));
        VBox vBox=  fxmlLoader.load();
        EventsController eventsController1=fxmlLoader.getController();

        eventsController1.sidebarController=this.eventsController.sidebarController;
        eventsController.sidebarController.borderPane.setCenter(vBox);

    }



    private List<Reserver> reservers;



    private ObservableList<Reserver> getAllList() throws SQLException {
        ReservationServiceImpl reservationService=new ReservationServiceImpl();
        ObservableList    <Reserver> oblist=FXCollections.observableArrayList(reservationService.getMyList(2));
        System.out.println(oblist);
    return oblist;
}

        public void shoxwList(ObservableList observableList){


            id_Res.setCellValueFactory(cellData -> {
                Reserver yourDataModel = cellData.getValue();
                return new SimpleObjectProperty<>(yourDataModel.getId_Res());
            });

            date_Res.setCellValueFactory(new PropertyValueFactory<>("date_res"));

            titre_Res.setCellValueFactory(cellData -> {
                Reserver yourDataModel = cellData.getValue();
                return new SimpleObjectProperty<>(yourDataModel.getEvenement().getLibelle());
            });
            prix_Res.setCellValueFactory(new PropertyValueFactory<>("prix_total"));
            status_Res.setCellValueFactory(new PropertyValueFactory<>("status"));
            categorie_Res.setCellValueFactory(cellData -> {
                Reserver yourDataModel = cellData.getValue();
                return new SimpleObjectProperty<>(yourDataModel.getEvenement().getId_categorie().getNom());
            });

            btn.setCellFactory(param -> new TableCell<Reserver, Void>() {
                private final Button button = new Button("Cancel reservation");

                {

                    button.setStyle("-fx-background-color: red;-fx-text-fill: white;");

                    // Handle button action
                    button.setOnAction(event -> {
                        Reserver reserver = getTableView().getItems().get(getIndex());
                        try {
                            ReservationServiceImpl reservationService=new ReservationServiceImpl();
                            reservationService.delete(reserver);
                            observableList.remove(reserver);

                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }

                    });
                }

                // Display the button in the cell
                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(button);
                    }
                }
            });


            table.setItems(observableList);

        }


    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            shoxwList(getAllList());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}


