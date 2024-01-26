package tn.esprit.javafxproject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import tn.esprit.javafxproject.models.Don;
import tn.esprit.javafxproject.models.Emoji;
import tn.esprit.javafxproject.models.User;
import tn.esprit.javafxproject.services.DonServiceImpl;

import java.io.IOException;
import java.sql.SQLException;

public class DonateDetailsController {

    @FXML
    private TextField Receveur = new TextField();

    @FXML
    private TextField comment = new TextField();


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
        System.out.println(comment.getText());

        receveur.setIdUser(Integer.parseInt(Receveur.getText()));
        System.out.println(Integer.parseInt(Receveur.getText()));

        don.setReceveur(receveur);
        donneur.setIdUser(Integer.parseInt(Receveur.getText()));

        emoji.setNomEmoji(name.getText());
        don.setEmoji(emoji);

        don.setMontant(Integer.parseInt(totale.getText()));

        don.setDonneur(donneur);

        addDon(don);
        loadPage("donation");

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
        donController.clientLayoutController = donationCardController.donController.clientLayoutController;
        donationCardController.donController.clientLayoutController.screen.setCenter(parent);
    }


}
