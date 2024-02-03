package tn.esprit.javafxproject;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import tn.esprit.javafxproject.models.Produit;
import tn.esprit.javafxproject.models.Reserver;
import tn.esprit.javafxproject.services.ServiceProduit;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ProduitsListController implements Initializable {

    public SidebarController sidebarController;
    public ServiceProduit produitService = new ServiceProduit();


    @FXML
    private TableView<Produit> table;


    @FXML
    private TableColumn<Produit, String> Nom;

    @FXML
    private TableColumn<Produit, Void> btn;

    @FXML
    private TableColumn<Produit, String> Description;

    @FXML
    private TableColumn<Produit, Double> Prix;

    @FXML
    private TableColumn<Produit, Integer> Quantite;

    @FXML
    private TableColumn<Produit, String> status;


    @FXML
    private Button ajout;
    @FXML
    private Button search;

    @FXML
    private TextField searchTerm;

    @FXML
    void search(ActionEvent event) {


        searchTerm.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                shoxwList(getAllList());
            } else {
                ObservableList<Produit> SearchList = FXCollections.observableArrayList(produitService.searchProduit(searchTerm.getText()));
                shoxwList(SearchList);
            }
        });

    }

    @FXML
    void ajout(ActionEvent event) throws IOException {
        FXMLLoader root = new FXMLLoader(this.getClass().getResource("AjoutProduit.fxml"));
        Parent parent = (Parent) root.load();
        Object AjouteProduitController = root.getController();
        if (root.getController() instanceof AjoutProduitController) {
            ((AjoutProduitController) AjouteProduitController).produitsListController = this;
            this.sidebarController.borderPane.setCenter(parent);
        }
    }


    private List<Reserver> reservers;


    private ObservableList<Produit> getAllList() {

        ObservableList<Produit> oblist = FXCollections.observableArrayList(produitService.getAll());

        return oblist;

    }

    public void shoxwList(ObservableList observableList) {

        Nom.setCellValueFactory(data -> {
            Produit yourData = data.getValue();
            return new SimpleObjectProperty<>(yourData.getProductName());
        });
        Description.setCellValueFactory(data -> {
            Produit yourData = data.getValue();
            return new SimpleObjectProperty<>(yourData.getProductDescription());
        });
        Prix.setCellValueFactory(data -> {
            Produit yourData = data.getValue();
            return new SimpleObjectProperty<>(yourData.getPrice());
        });
        Quantite.setCellValueFactory(data -> {
            Produit yourData = data.getValue();
            return new SimpleObjectProperty<>(yourData.getQuantityInStock());
        });
        status.setCellValueFactory(data -> {
            Produit yourData = data.getValue();
            return new SimpleObjectProperty<>(yourData.getStatus());
        });


        btn.setCellFactory(param -> new TableCell<>() {
            private final Button button = new Button("Supprimer Produit");

            {

                button.setStyle("-fx-background-color: red;-fx-text-fill: white;");

                // Handle button action
                button.setOnAction(event -> {
                    Produit produit = getTableView().getItems().get(getIndex());

                    if (!"SUPPRIMER".equals(produit.getStatus())) {

                    produitService.delete(produit);
                    shoxwList(getAllList());

                            }else {
                        button.setDisable(false);
                    }
                    itemProperty().addListener((obs, oldItem, newItem) -> {
                        if (newItem != null && "SUPPRIMER".equals(produit.getStatus())) {
                            button.setDisable(true);
                        } else {
                            button.setDisable(false);
                        }
                    });
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
    public void initialize(URL url, ResourceBundle resourceBundle) {

        shoxwList(getAllList());
        searchTerm.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                shoxwList(getAllList());
            } else {
                ObservableList<Produit> SearchList = FXCollections.observableArrayList(produitService.searchProduit(searchTerm.getText()));
                shoxwList(SearchList);
            }
        });
    }
}