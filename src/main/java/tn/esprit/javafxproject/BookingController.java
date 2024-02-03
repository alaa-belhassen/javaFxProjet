package tn.esprit.javafxproject;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import tn.esprit.javafxproject.models.LigneDeCommande;
import tn.esprit.javafxproject.services.LigneDeCommandeService;
import tn.esprit.javafxproject.services.ServiceAchat;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class BookingController implements Initializable {

    public int idAchat;
    @FXML
    private TextField CodeReduction;

    @FXML
    private Button Submit;

    @FXML
    private TableColumn<LigneDeCommande, String> description;

    @FXML
    private TableColumn<LigneDeCommande, Double> price;

    @FXML
    private TableColumn<LigneDeCommande, String> productName;

    @FXML
    private TableColumn<LigneDeCommande, Integer> quantiter;

    @FXML
    private TableView<LigneDeCommande> table;
    private List<LigneDeCommande> afficher() throws SQLException {
        LigneDeCommandeService ligneDeCommandeService = new LigneDeCommandeService();
        ServiceAchat serviceAchat = new ServiceAchat();
        return ligneDeCommandeService.getMyList(serviceAchat.getLastId());
    }


    private ObservableList<LigneDeCommande> getAllList() throws SQLException {
        ObservableList<LigneDeCommande> oblist= FXCollections.observableArrayList(afficher());
        return oblist;
    }

    public void shoxwList(ObservableList observableList){

        price.setCellValueFactory(cellData -> {
            LigneDeCommande yourDataModel = cellData.getValue();
            return new SimpleObjectProperty<>(yourDataModel.getPrice());
        });
        productName.setCellValueFactory(cellData -> {
            LigneDeCommande yourDataModel = cellData.getValue();
            return new SimpleObjectProperty<>(yourDataModel.getProduit().getProductName());
        });
        quantiter.setCellValueFactory(cellData -> {
            LigneDeCommande yourDataModel = cellData.getValue();
            return new SimpleObjectProperty<>(yourDataModel.getQuantityOrdred());
        });
        description.setCellValueFactory(cellData -> {
            LigneDeCommande yourDataModel = cellData.getValue();
            return new SimpleObjectProperty<>(yourDataModel.getProduit().getProductDescription());
        });

        table.setItems(observableList);

    }



    @FXML
    void Submit(ActionEvent event) {

    }

    @FXML
    void generaterPDF(MouseEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            shoxwList(getAllList());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
