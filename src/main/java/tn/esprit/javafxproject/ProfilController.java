package tn.esprit.javafxproject;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import tn.esprit.javafxproject.SidebarController;
import tn.esprit.javafxproject.models.User;
import tn.esprit.javafxproject.services.UserServiceImpl;

public class ProfilController {

    public SidebarController SidebarController;
    public AdminSidebarController AdminSidebarController;
    private UserServiceImpl userService;

    public User user;
    public ProfilController(User user) {
        this.user = user;
    }
    public ProfilController() {

    }

    @FXML
    private Label nom ;
    @FXML
    private Label email ;
    @FXML
    private Label telephone ;
    @FXML
    private Label Adresse ;
    public void initialize() {
       User u = userService.getUser(this.user.getIdUser());
        nom.setText(u.getNom());
        email.setText(u.getEmail());
        telephone.setText(u.getTelephone());
        Adresse.setText(u.getAdresse());
    }

    public void setUser(User user) {
        this.user = user;
    }

}
