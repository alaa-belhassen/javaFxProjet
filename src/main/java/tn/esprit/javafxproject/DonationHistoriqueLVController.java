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
import tn.esprit.javafxproject.models.*;
import tn.esprit.javafxproject.services.DonServiceImpl;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.ResourceBundle;

public class DonationHistoriqueLVController implements Initializable {

    public DonController donController;
    public SidebarController sidebarController;

    @FXML
    private TableColumn<Don, Void> acceptdeclineEvent;

    @FXML
    private Button btn_categorie;

    @FXML
    private TableView<Don> eventTable;

    @FXML
    private TableColumn<Don, String> commentaire;

    @FXML
    private TableColumn<Don, Integer> id;

    @FXML
    private TableColumn<Don, Integer> montant;

    @FXML
    private TableColumn<Don, String> nomdonneur;

    @FXML
    private TableColumn<Don, String> nomemoji;

    @FXML
    private TableColumn<Don, String> nomreceveur;

    @FXML
    private TableColumn<Don, Double> quantiter;






    private List<Don> afficher() throws SQLException {
        DonServiceImpl donController = new DonServiceImpl();
        return donController.getAll();
    }



    private ObservableList<Don> getAllList() throws SQLException {
        ObservableList<Don> oblist= FXCollections.observableArrayList(afficher());
        return oblist;
    }

    public void shoxwList(ObservableList observableList){

        commentaire.setCellValueFactory(new PropertyValueFactory<>("commentaire"));

        montant.setCellValueFactory(cellData -> {
            Don yourDataModel = cellData.getValue();
            return new SimpleObjectProperty<>(yourDataModel.getEmoji().getPrix());
        });
        nomemoji.setCellValueFactory(cellData -> {
            Don yourDataModel = cellData.getValue();
            return new SimpleObjectProperty<>(yourDataModel.getEmoji().getNomEmoji());
        });
        nomdonneur.setCellValueFactory(cellData -> {
            Don yourDataModel = cellData.getValue();
            return new SimpleObjectProperty<>(yourDataModel.getDonneur().getNom());
        });
        nomreceveur.setCellValueFactory(cellData -> {
            Don yourDataModel = cellData.getValue();
            System.out.println(yourDataModel+" "+yourDataModel.getReceveur().getNom());
            return new SimpleObjectProperty<>(yourDataModel.getReceveur().getNom());
        });
        quantiter.setCellValueFactory(cellData -> {
            Don yourDataModel = cellData.getValue();
            System.out.println(yourDataModel+" "+yourDataModel.getReceveur().getNom());
            return new SimpleObjectProperty<>(yourDataModel.getMontant()/yourDataModel.getEmoji().getPrix());
        });

        id.setCellValueFactory(cellData -> {
            Don yourDataModel = cellData.getValue();
            return new SimpleObjectProperty<>(yourDataModel.getIdDon());
        });
        acceptdeclineEvent.setCellFactory(param -> new TableCell<Don, Void>() {
            private final Button buttonDecline = new Button("refund");
            private final Button buttonAccept = new Button("update");

            {
                buttonDecline.setStyle("-fx-background-color: #B90E0A;-fx-text-fill: white;");
                buttonAccept.setStyle("-fx-background-color: #3CB043;-fx-text-fill: white;");

                // Handle decline button action
                buttonDecline.setOnAction(event -> {
                    Don don = getTableView().getItems().get(getIndex());
                    try {
                        DonServiceImpl Service = new DonServiceImpl();
                        Service.delete(don);
                        observableList.remove(don);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });

                // Handle accept button action
              /*  buttonAccept.setOnAction(event -> {
                    Don don = getTableView().getItems().get(getIndex());
                    try {
                        DonServiceImpl donService = new DonServiceImpl();
                        donService.update(don);
                        observableList.remove(don);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    // Implement your accept logic here
                });*/

                buttonAccept.setOnAction(event -> {
                    Don don = getTableView().getItems().get(getIndex());
                    //pdf display
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