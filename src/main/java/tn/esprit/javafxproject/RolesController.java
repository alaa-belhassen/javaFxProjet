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
import tn.esprit.javafxproject.models.Role;
import tn.esprit.javafxproject.models.User;
import tn.esprit.javafxproject.services.ReservationServiceImpl;
import tn.esprit.javafxproject.services.RoleServiceImpl;
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

public class RolesController implements  Initializable {
    public AdminSidebarController AdminsidebarController;



    public void setAdminsidebarController(AdminSidebarController adminsidebarController) {
        AdminsidebarController = adminsidebarController;
    }




    @FXML
    private TableView<Role> table;
    @FXML
    private Label Label_Role;


    @FXML
    private Button valider;


    @FXML
    void valider(ActionEvent event) {
        Role r = new Role();
        r.setName(role.getText());
        RoleServiceImpl roleService = new RoleServiceImpl();
        roleService.add(r);

    }

    @FXML
    private TextField role;

    @FXML
    private TableColumn<Role, Void> btn;



    @FXML
    private TableColumn<Role, String> nom;

    @FXML
    private TableColumn<Role, String> status;


    @FXML
    private Button ajout;

    @FXML
    void ajout(ActionEvent event) throws IOException {
        role.setVisible(true);
        Label_Role.setVisible(true);
        valider.setVisible(true);

    }


    private ObservableList<Role> getAllList() {
       RoleServiceImpl roleService = new RoleServiceImpl();
        ObservableList    <Role> oblist=FXCollections.observableArrayList(roleService.getAll());
        System.out.println(oblist);
        return oblist;

    }

    public void shoxwList(ObservableList observableList){


        nom.setCellValueFactory(data ->{
            Role yourData = data.getValue();
            return new SimpleObjectProperty<>(yourData.getName());
        });

        status.setCellValueFactory(data ->{
            Role yourData = data.getValue();
            return new SimpleObjectProperty<>(yourData.getStatus());
        });



        btn.setCellFactory(param -> new TableCell<>() {
            private final Button button = new Button("supprimer");

            {

                button.setStyle("-fx-background-color: red;-fx-text-fill: white;");

                // Handle button action
                button.setOnAction(event -> {
                    Role role = getTableView().getItems().get(getIndex());
                    RoleServiceImpl roleService = new RoleServiceImpl();
                    roleService.delete(role);
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
    }
}
