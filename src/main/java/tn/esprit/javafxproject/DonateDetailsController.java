package tn.esprit.javafxproject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import tn.esprit.javafxproject.models.Don;
import tn.esprit.javafxproject.models.Emoji;
import tn.esprit.javafxproject.models.User;
import tn.esprit.javafxproject.services.DonServiceImpl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

public class DonateDetailsController {
    @FXML
    private Label errortext;

    @FXML
    private TextField Receveur = new TextField();

    @FXML
    private TextField comment = new TextField();

    @FXML
    private ImageView found;

    @FXML
    private Label exp;

    @FXML
    private Label name;

    @FXML
    private Label prix;

    @FXML
    private Label qt;

    @FXML
    private Label totale;

    public DonationCardController donationCardController;

    public Label getName() {
        return name;
    }

    public void setName(String name) {
        this.name.setText(name);
    }

    public Label getExp() {
        return exp;
    }

    public void setExp(String exp) {
        this.exp.setText(exp);
    }

    public Label getPrix() {
        return prix;
    }

    public void setPrix(String prix) {
        this.prix.setText(prix);
        this.totale.setText(prix);
    }
    @FXML
    void moin(MouseEvent event) {
        if(Integer.parseInt(qt.getText())>1){
            qt.setText(Integer.toString(Integer.parseInt(qt.getText())-1));
            calculTotal();
        }
    }

    @FXML
    void plus(MouseEvent event) {
        qt.setText(Integer.toString(Integer.parseInt(qt.getText())+1));
        calculTotal();
    }
    private void calculTotal(){
        this.totale.setText(Integer.toString(Integer.parseInt(qt.getText())*Integer.parseInt(prix.getText())));
    }

    @FXML
    void donate(MouseEvent event) throws SQLException, IOException {
        Don don = new Don();
        User receveur = new User();
        User donneur = new User();
        Emoji emoji = new Emoji();
        //fill don
        don.setCommentaire(comment.getText());
        receveur.setNom(Receveur.getText());
        don.setReceveur(receveur);
        System.out.println(Receveur.getText());
        donneur.setNom(Receveur.getText());
        don.setDonneur(donneur);
        emoji.setNomEmoji(name.getText());
        don.setEmoji(emoji);
        don.setMontant(Integer.parseInt(totale.getText()));
        if(checkUserByName()){
            addDon(don);
            loadPage("donation");
        }else{
            errortext.setText("userNotFound");
            errortext.setVisible(true);
        }
    }

    @FXML
    void checkuser(MouseEvent event) throws SQLException, FileNotFoundException {
        if(checkUserByName()){
            Image image =  new Image(new FileInputStream("C:/Users/msi/Desktop/projectjavaGeeksFInale/javaFxProject/src/main/resources/tn/esprit/javafxproject/img/R.png"));
            found.setImage(image);
            found.setVisible(true);
        }else{
            Image image =  new Image(new FileInputStream("C:/Users/msi/Desktop/projectjavaGeeksFInale/javaFxProject/src/main/resources/tn/esprit/javafxproject/img/error.jpg"));
            found.setImage(image);
            found.setVisible(true);
        }

    }
    private boolean checkUserByName() throws SQLException {
        DonServiceImpl donService = new DonServiceImpl();
        return donService.chearchUserByName(Receveur.getText());
    }
    private void addDon(Don don) throws SQLException {
        //db call
        DonServiceImpl donService = new DonServiceImpl();
        donService.add(don);
    }

    public void loadPage(String name) throws IOException {
        FXMLLoader root = new FXMLLoader(getClass().getResource(name+".fxml"));
        Parent parent = root.load();
        DonController donController = root.getController();
        donController.sidebarController = donationCardController.donController.sidebarController;
        donationCardController.donController.sidebarController.getScreen().setCenter(parent);
    }


}
