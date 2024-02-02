package tn.esprit.javafxproject;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TouchEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import tn.esprit.javafxproject.models.Categorie;
import tn.esprit.javafxproject.models.Reserver;
import tn.esprit.javafxproject.services.CategorieServiceImpl;
import tn.esprit.javafxproject.services.ReservationServiceImpl;
import tn.esprit.javafxproject.utils.Status;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;

import static javafx.scene.paint.Color.RED;
import static javafx.scene.paint.Color.color;

public class GestionCategoryController implements Initializable {


    public EventsController eventsController;

    @FXML
    private Button btn_add;

    @FXML
    private Button btn_delete;

    @FXML
    private Button btn_update;

    @FXML
    private TableColumn<Categorie, Integer> idCategory;

    @FXML
    private TableColumn<Categorie, String> nameCategory;

    @FXML
    private TableView<Categorie> table;

    @FXML
    private TextField textfield;

    public AdminEventsController adminEventsController;

    @FXML
    private Label cat_check;








    @FXML
    void addCategory(MouseEvent event) throws SQLException {

        if(textfield.getText().equals("")){
            cat_check.setVisible(true);
            cat_check.setText("saisir le nom du catégorie svp");
            cat_check.setTextFill(Color.RED);
        }else {
            CategorieServiceImpl categorieService=new CategorieServiceImpl();
            categorieService.add(new Categorie(textfield.getText(), Status.VALID.toString()));
            shoxwList(getAllList());
        }


    }

    @FXML
    private Label nvl;
    @FXML
    void deleteCategory(MouseEvent event) throws SQLException {
        Categorie selectedCategory = table.getSelectionModel().getSelectedItem();

        if (selectedCategory != null) {
            // Remove selected category from the observable list
            CategorieServiceImpl categorieService=new CategorieServiceImpl();
            categorieService.delete(selectedCategory);
            table.getItems().remove(selectedCategory);
        }
        if (selectedCategory==null){

            cat_check.setVisible(true);
            cat_check.setText("Choisissez un enregistrement !! ");
            cat_check.setTextFill(Color.RED);


        }
    }

    @FXML
    void goBack(MouseEvent event) throws IOException {

        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("AdminEvents.fxml"));
        AnchorPane anchorPane=  fxmlLoader.load();
        AdminEventsController adminEventsController = fxmlLoader.getController();
        adminEventsController.sidebarController = this.adminEventsController.sidebarController;
        this.adminEventsController.sidebarController.borderPane.setCenter(anchorPane);

    }
    @FXML
    void updateCategory(MouseEvent event) {

        Categorie selectedCategory = table.getSelectionModel().getSelectedItem();

        if(selectedCategory==null){
            cat_check.setVisible(true);
            cat_check.setText("choisissez une catégorie svp !");
            cat_check.setTextFill(Color.RED);
        }else if(textfield.getText().equals("")){
            cat_check.setVisible(true);
            cat_check.setText("saisissez le nouveau nom du catégorie ");
            cat_check.setTextFill(Color.RED);
        }else{
            CategorieServiceImpl categorieService= null;
            try {
                categorieService = new CategorieServiceImpl();
                selectedCategory.setNom(textfield.getText());
                categorieService.update(selectedCategory);
                shoxwList(getAllList());

            } catch (SQLException e) {
                System.out.println("pro");;
            }
        }




    }


    private ObservableList<Categorie> getAllList() throws SQLException {
        CategorieServiceImpl categorieService=new CategorieServiceImpl();
        ObservableList    <Categorie> oblist= FXCollections.observableArrayList(categorieService.getAll());
        System.out.println(oblist);
        return oblist;
    }

    public void shoxwList(ObservableList observableList){


        idCategory.setCellValueFactory(cellData -> {
            Categorie yourDataModel = cellData.getValue();
            return new SimpleObjectProperty<>(yourDataModel.getIdCategorie());
        });

        nameCategory.setCellValueFactory(new PropertyValueFactory<>("nom"));

        table.setItems(observableList);

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
