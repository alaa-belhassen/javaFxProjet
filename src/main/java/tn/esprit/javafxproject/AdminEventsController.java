package tn.esprit.javafxproject;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import tn.esprit.javafxproject.models.Evenement;
import tn.esprit.javafxproject.models.Reserver;
import tn.esprit.javafxproject.services.EvenementServiceImpl;
import tn.esprit.javafxproject.services.ReservationServiceImpl;
import tn.esprit.javafxproject.utils.Status;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.ResourceBundle;

public class AdminEventsController implements Initializable {


    public AdminSidebarController sidebarController;

    @FXML
    private TableColumn<Evenement, Void> acceptdeclineEvent;

    @FXML
    private TableColumn<Evenement, String> categorieEvent;

    @FXML
    private TableColumn<Evenement, LocalDate> dateEvent;

    @FXML
    private TableColumn<Evenement, Integer> dureeEvent;

    @FXML
    private TableColumn<Evenement, Integer> idEvent;

    @FXML
    private TableColumn<Evenement, String> libelleEvent;

    @FXML
    private TableColumn<Evenement, String> lieuEvent;

    @FXML
    private TableColumn<Evenement, Integer> placesEvent;

    @FXML
    private TableColumn<Evenement, Float> prixEvent;

    @FXML
    private TableColumn<Evenement, LocalTime> timeEvent;

    @FXML
    private TableView<Evenement> eventTable;


    @FXML
    private Button btn_categorie;


    @FXML
    void goToCategorie(MouseEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("GestionCategory.fxml"));
        Parent eventDetailRoot = (Parent)fxmlLoader.load();
        GestionCategoryController gestionCategoryController = fxmlLoader.getController();
        gestionCategoryController.adminEventsController=this;
        this.sidebarController.borderPane.setCenter(eventDetailRoot);

    }


    

    private List<Evenement> afficher() throws SQLException {
        EvenementServiceImpl evenementService = new EvenementServiceImpl();
        return evenementService.getAll_admin();
    }



    private ObservableList<Evenement> getAllList() throws SQLException {
        EvenementServiceImpl evenementService=new EvenementServiceImpl();
        ObservableList    <Evenement> oblist= FXCollections.observableArrayList(evenementService.getAll_admin());
        System.out.println(oblist);
        return oblist;
    }

    public void shoxwList(ObservableList observableList){


        idEvent.setCellValueFactory(cellData -> {
            Evenement yourDataModel = cellData.getValue();
            return new SimpleObjectProperty<>(yourDataModel.getIdEvenement());
        });


        dateEvent.setCellValueFactory(new PropertyValueFactory<>("date_event"));

        libelleEvent.setCellValueFactory(cellData -> {
            Evenement yourDataModel = cellData.getValue();
            return new SimpleObjectProperty<>(yourDataModel.getLibelle());
        });
        prixEvent.setCellValueFactory(new PropertyValueFactory<>("prix"));
        lieuEvent.setCellValueFactory(new PropertyValueFactory<>("lieu"));
        placesEvent.setCellValueFactory(new PropertyValueFactory<>("max_places"));
        timeEvent.setCellValueFactory(new PropertyValueFactory<>("time_event"));
        dureeEvent.setCellValueFactory(new PropertyValueFactory<>("duration"));



        categorieEvent.setCellValueFactory(cellData -> {
            Evenement yourDataModel = cellData.getValue();
            return new SimpleObjectProperty<>(yourDataModel.getId_categorie().getNom());
        });


        acceptdeclineEvent.setCellFactory(param -> new TableCell<Evenement, Void>() {
            private final Button buttonDecline = new Button("Decline");
            private final Button buttonAccept = new Button("Accept");

            {
                buttonDecline.setStyle("-fx-background-color: #DD571C;-fx-text-fill: white;");
                buttonAccept.setStyle("-fx-background-color: #597d35;-fx-text-fill: white;");

                // Handle decline button action
                buttonDecline.setOnAction(event -> {
                    Evenement evenement = getTableView().getItems().get(getIndex());
                    try {
                        EvenementServiceImpl evenementService = new EvenementServiceImpl();
                        evenementService.delete(evenement);
                        observableList.remove(evenement);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });

                // Handle accept button action
                buttonAccept.setOnAction(event -> {
                    Evenement evenement = getTableView().getItems().get(getIndex());
                    try {
                        EvenementServiceImpl evenementService = new EvenementServiceImpl();
                        evenementService.update_status(evenement, Status.VALID.toString());
                        observableList.remove(evenement);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    // Implement your accept logic here
                });
            }

            // Display the buttons in the cell
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox buttonBox = new HBox(buttonDecline, buttonAccept);
                    setGraphic(buttonBox);
                }
            }
        });




        eventTable.setItems(observableList);

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
