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

public class SidebarController  {

    private UserServiceImpl userService = new UserServiceImpl();


    public void initialize() throws IOException {

        User user = userService.getUser(User.UserConnecte);
        profil.setText(user.getNom());
        System.out.println(user.getImage());
        System.out.println(user);
        if(!user.getImage().isEmpty()){
            Image image =  new Image(new FileInputStream(user.getImage()));
            this.image.setImage(image);
        }

        // Automatically load goAcceuil when the sidebar is initialized
        goAcceuil(null);


    }

    public int id;
    public static User user ;
    @FXML
    private ImageView image;
    @FXML
    private Button deconnecter;
    @FXML
    private Hyperlink profil;
    @FXML
    public BorderPane borderPane;
    @FXML
    void goAcceuil(MouseEvent event) throws IOException {
        FXMLLoader root = new FXMLLoader(this.getClass().getResource("feed.fxml"));
        Parent parent = (Parent) root.load();
        Object feedController = root.getController();
        if (root.getController() instanceof FeedController) {
            ((FeedController) feedController).sideBarController = this;
            this.borderPane.setCenter(parent);
        }

    }


    public void setId(int id) {
        this.id = id;
    }

    @FXML
    void profil (ActionEvent event) throws IOException {
        FXMLLoader root = new FXMLLoader(getClass().getResource("Profil.fxml"));

        Parent parent = (Parent)root.load();

       ProfilController profilController = root.getController();
      profilController.setUser(user);

        if(root.getController() instanceof ProfilController){
            ((ProfilController) profilController).sidebarController = this;
            borderPane.setCenter(parent);
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

    }



    @FXML
    void goDonation(MouseEvent event) {

    }

    @FXML
    void goEvenement(MouseEvent event) {

    }

    @FXML
    void goReclamation(MouseEvent event) {

    }

    @FXML
    void goShop(MouseEvent event) {

    }

    public User  setUser(User u) {
        this.user=u;

        return this.user;
    }

    public User getUser() {
        return user;
    }
}
