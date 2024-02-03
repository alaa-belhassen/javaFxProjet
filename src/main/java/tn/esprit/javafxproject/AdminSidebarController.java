package tn.esprit.javafxproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import tn.esprit.javafxproject.models.User;
import tn.esprit.javafxproject.services.UserServiceImpl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminSidebarController  implements Initializable {




    @FXML
    private Hyperlink profil;
    @FXML
    public BorderPane borderPane;
    private UserServiceImpl userService = new UserServiceImpl();
    @FXML
    private ImageView image;
    @FXML
    private Button deconnecter;


    @FXML
    void goAcceuil(MouseEvent event) {
        try {
            FXMLLoader root = new FXMLLoader(getClass().getResource("feed.fxml"));
            Parent parent = root.load();
            Object feedController = root.getController();
            if (root.getController() instanceof FeedController) {
                ((FeedController) feedController).setAdminsidebarController(this);
                this.borderPane.setCenter(parent);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading Acceuil: " + e.getMessage());
        }
    }


    @FXML
    void profil (ActionEvent event) throws IOException {
        FXMLLoader root = new FXMLLoader(getClass().getResource("Profil.fxml"));

        Parent parent = (Parent)root.load();

        Object profilController = root.getController();
        if(root.getController() instanceof ProfilController){
            ((ProfilController) profilController).adminsidebarController= this;
            borderPane.setCenter(parent);
        }

        System.out.println(root);
    }
    @FXML
    void goUsers(MouseEvent event) throws IOException {

        FXMLLoader root = new FXMLLoader(this.getClass().getResource( "Users.fxml"));
        Parent parent = (Parent)root.load();
        Object usersController = root.getController();
        if (root.getController() instanceof UsersController) {
            ((UsersController)usersController).AdminsidebarController = this;
            this.borderPane.setCenter(parent);
        }

    }
    @FXML
    void goRoles(MouseEvent event) throws IOException {
        FXMLLoader root = new FXMLLoader(this.getClass().getResource( "Roles.fxml"));
        Parent parent = (Parent)root.load();
        Object rolesController = root.getController();
        if (root.getController() instanceof RolesController) {
            ((RolesController)rolesController).AdminsidebarController = this;
            this.borderPane.setCenter(parent);
        }
    }
    @FXML
    void goDeconect(MouseEvent event) throws IOException {

        Stage stage = (Stage) deconnecter.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        User.UserConnecte=-1;
        User.Role_User_Connecte=-1;
    }

    @FXML
    void goDonation(MouseEvent event) throws IOException {
        FXMLLoader root = new FXMLLoader(this.getClass().getResource("EmojiAdminLV.fxml"));
        Parent parent = (Parent) root.load();
        Object emojicontroller = root.getController();
        if (root.getController() instanceof EmojiAdminLVController) {
            ((EmojiAdminLVController) emojicontroller).sidebarController = this;
            this.borderPane.setCenter(parent);
        }

    }

    @FXML
    void goEvenement(MouseEvent event) throws IOException {
        FXMLLoader root = new FXMLLoader(this.getClass().getResource("AdminEvents.fxml"));
        Parent parent = (Parent) root.load();
        Object adminEvents = root.getController();
        if (root.getController() instanceof AdminEventsController) {
            ((AdminEventsController) adminEvents).sidebarController = this;
            this.borderPane.setCenter(parent);
        }

    }

    @FXML
    void goReclamation(MouseEvent event) throws IOException {
        FXMLLoader root = new FXMLLoader(this.getClass().getResource("ReclamationAdmin.fxml"));
        Parent parent = (Parent) root.load();
        Object reclamationAdmin = root.getController();
        if (root.getController() instanceof ReclamationAdminController) {
            ((ReclamationAdminController) reclamationAdmin).adminSideBar = this;
            this.borderPane.setCenter(parent);
        }

    }

    @FXML
    void goShop(MouseEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        User user = userService.getUser(User.UserConnecte);
        profil.setText(user.getNom());
        Image image = null;
        try {
            image = new Image(new FileInputStream(user.getImage()));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        this.image.setImage(image);

        System.out.println("Loading Acceuil...");
        goAcceuil(null);
        System.out.println("Acceuil loaded successfully.");

    }
}
