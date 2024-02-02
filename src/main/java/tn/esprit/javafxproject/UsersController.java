package tn.esprit.javafxproject;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import tn.esprit.javafxproject.models.Evenement;
import tn.esprit.javafxproject.models.Reserver;
import tn.esprit.javafxproject.models.User;
import tn.esprit.javafxproject.services.ReservationServiceImpl;
import tn.esprit.javafxproject.services.UserServiceImpl;
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

public class UsersController implements  Initializable {
    public AdminSidebarController AdminsidebarController;

    public UserServiceImpl userService = new UserServiceImpl();


    public void setAdminsidebarController(AdminSidebarController adminsidebarController) {
        AdminsidebarController = adminsidebarController;
    }




    @FXML
    private TableView<User> table;




    @FXML
    private TableColumn<User, String> adresse;

    @FXML
    private TableColumn<User, Void> btn;

    @FXML
    private TableColumn<User, String> email;

    @FXML
    private TableColumn<User, String> nom;

    @FXML
    private TableColumn<User, String> status;

    @FXML
    private TableColumn<User, String> role;



    @FXML
    private TableColumn<User, String> telephone;

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
                ObservableList    <User> SearchList=FXCollections.observableArrayList(userService.search(searchTerm.getText()));
                shoxwList(SearchList);
            }
        });

    }
    @FXML
    void ajout(ActionEvent event) throws IOException {
        FXMLLoader root = new FXMLLoader(this.getClass().getResource( "AjouterEquipe.fxml"));
        Parent parent = (Parent)root.load();
        Object AjouterEquipeController = root.getController();
        if (root.getController() instanceof AjouterEquipeController) {
            ((AjouterEquipeController)AjouterEquipeController).usersController = this;
            this.AdminsidebarController.borderPane.setCenter(parent);
        }
    }


    private List<Reserver> reservers;



    private ObservableList<User> getAllList() {

        ObservableList    <User> oblist=FXCollections.observableArrayList(userService.getAll());

        return oblist;

    }

    public void shoxwList(ObservableList observableList){


        nom.setCellValueFactory(data ->{
            User yourData = data.getValue();
            return new SimpleObjectProperty<>(yourData.getNom());
        });
        adresse.setCellValueFactory(data ->{
            User yourData = data.getValue();
            return new SimpleObjectProperty<>(yourData.getAdresse());
        });
        telephone.setCellValueFactory(data ->{
            User yourData = data.getValue();
            return new SimpleObjectProperty<>(yourData.getTelephone());
        });
        email.setCellValueFactory(data ->{
            User yourData = data.getValue();
            return new SimpleObjectProperty<>(yourData.getEmail());
        });
        status.setCellValueFactory(data ->{
                    User yourData = data.getValue();
                    return new SimpleObjectProperty<>(yourData.getStatus());
        });
        role.setCellValueFactory(data ->{
            User yourData = data.getValue();
            return new SimpleObjectProperty<>(yourData.getRole().getName());
        });


        btn.setCellFactory(param -> new TableCell<>() {
            private final Button button = new Button("delete user");

            {

                button.setStyle("-fx-background-color: red;-fx-text-fill: white;");

                // Handle button action
                button.setOnAction(event -> {
                    User user = getTableView().getItems().get(getIndex());
                    UserServiceImpl userService = new UserServiceImpl();
                    userService.delete(user);
                    shoxwList(getAllList());

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
    public void initialize(URL location, ResourceBundle resources) {

        shoxwList(getAllList());
        searchTerm.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                shoxwList(getAllList());
            } else {
                ObservableList    <User> SearchList=FXCollections.observableArrayList(userService.search(searchTerm.getText()));
                shoxwList(SearchList);
            }
        });
    }
}
