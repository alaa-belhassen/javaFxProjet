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
import tn.esprit.javafxproject.models.Evenement;
import tn.esprit.javafxproject.models.Reserver;
import tn.esprit.javafxproject.services.EvenementServiceImpl;
import tn.esprit.javafxproject.services.ReservationServiceImpl;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ListEventsEquipesController implements Initializable {


    public SidebarController sidebarController;
    @FXML
    private TableColumn<Evenement,Void> btn;

    @FXML
    private TableColumn<Evenement, String> categorieEvent;

    @FXML
    private TableColumn<Evenement, LocalDate> dateEvent;

    @FXML
    private TableColumn<Evenement, Integer> idEvent;

    @FXML
    private TableColumn<Evenement, Float> prixEvent;

    @FXML
    private TableView<Evenement> table;

    @FXML
    private TableColumn<Evenement, String> titreEvent;


    private ObservableList<Evenement> getAllList() throws SQLException {
        EvenementServiceImpl evenementService=new EvenementServiceImpl();
        ObservableList    <Evenement> oblist= FXCollections.observableArrayList(evenementService.getMyList(2));
        System.out.println(oblist);
        return oblist;
    }

    public void shoxwList(ObservableList observableList){

        idEvent.setCellValueFactory(cellData -> {
            Evenement yourDataModel = cellData.getValue();
            return new SimpleObjectProperty<>(yourDataModel.getIdEvenement());
        });

        dateEvent.setCellValueFactory(new PropertyValueFactory<>("date_event"));

        titreEvent.setCellValueFactory(cellData -> {
            Evenement yourDataModel = cellData.getValue();
            return new SimpleObjectProperty<>(yourDataModel.getLibelle());
        });
        prixEvent.setCellValueFactory(new PropertyValueFactory<>("prix"));
        categorieEvent.setCellValueFactory(cellData -> {
            Evenement yourDataModel = cellData.getValue();
            return new SimpleObjectProperty<>(yourDataModel.getId_categorie().getNom());
        });

        btn.setCellFactory(param -> new TableCell<Evenement, Void>() {
            private final Button button = new Button("Cancel match");

            {

                button.setStyle("-fx-background-color: red;-fx-text-fill: white;");

                // Handle button action
                button.setOnAction(event -> {
                    Evenement evenement = getTableView().getItems().get(getIndex());
                    try {
                        EvenementServiceImpl evenementService=new EvenementServiceImpl();
                        evenementService.delete(evenement);
                        observableList.remove(evenement);

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

    @FXML
    void addEvent(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("AjoutEvenement.fxml"));
        Parent eventDetailRoot = (Parent)fxmlLoader.load();
        AjoutEvenementController ajoutEvenementController = fxmlLoader.getController();
        ajoutEvenementController.listevenementEquipe=this;
        this.sidebarController.borderPane.setCenter(eventDetailRoot);

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
