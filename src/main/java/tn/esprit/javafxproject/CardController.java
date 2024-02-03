package tn.esprit.javafxproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import tn.esprit.javafxproject.models.Achat;
import tn.esprit.javafxproject.models.LigneDeCommande;
import tn.esprit.javafxproject.models.Produit;
import tn.esprit.javafxproject.services.LigneDeCommandeService;
import tn.esprit.javafxproject.services.ServiceAchat;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CardController  {
    LigneDeCommandeService ligneService = new LigneDeCommandeService();
    ServiceAchat serviceAchat= new ServiceAchat();

    public int idachat;
    public Produit produit;

    @FXML
    public Label nom;

    @FXML
    public ImageView image = new ImageView() ;
    ShopController shopController;
    @FXML
    public Label prix;




    @FXML
    private Label qtLb;


    @FXML
    void addToLigneDeCommande(MouseEvent event) {

        LigneDeCommande ligneCommande = new LigneDeCommande();
        ligneCommande.setQuantityOrdred(Integer.parseInt(qtLb.getText()));
        ligneCommande.setProductID(produit.getProductID());
        ligneCommande.setPurchaseID(idachat);
        ligneCommande.setPrice(produit.getPrice()*Integer.parseInt(qtLb.getText()));
        ligneService.add(ligneCommande);

    }


    @FXML
    void minus(MouseEvent event) {

        if(produit.getQuantityInStock()!=0) {
            int currentValue = Integer.parseInt(qtLb.getText());
            int newValue = currentValue - 1;
            // Ensure newValue is not below 0
            newValue = Math.max(newValue, 1);

            qtLb.setText(String.valueOf(newValue));
        }
    }
    @FXML
    private Button plusBtn;
    @FXML
    private Button moinBtn;
    @FXML
    private Button btnProduit;
    @FXML
    void plus(MouseEvent event) {
        if(produit.getQuantityInStock()!=0) {
            int currentValue = Integer.parseInt(qtLb.getText());
            int newValue = currentValue + 1;
            newValue = Math.min(newValue, produit.getQuantityInStock());
            qtLb.setText(String.valueOf(newValue));
        }    }
    @FXML
    void donate(MouseEvent event) throws IOException, SQLException {
        loadPage("donate-Details");
    }
    public void setDataEmoji(Produit produit) throws FileNotFoundException {
        /* Image image =  new Image(new FileInputStream(emoji.getImageUrl()));
        exp.setText(Integer.toString(emoji.getRank()));
        name.setText(emoji.getNomEmoji());
        prix.setText(Integer.toString(emoji.getPrix()));*/

        // houni hasb el model elli lfou9 abi el carte

        if(produit.getQuantityInStock()==0){
            plusBtn.setDisable(true);
            moinBtn.setDisable(true);
            btnProduit.setDisable(true);
        }

    }


    public void loadPage(String name) throws IOException {
        FXMLLoader root = new FXMLLoader(getClass().getResource(name+".fxml"));
        Parent parent = root.load();
        /*DonateDetailsController donateDetailsController = root.getController();
        donateDetailsController.setName(this.name.getText());
        donateDetailsController.setExp(this.exp.getText());
        donateDetailsController.setPrix(this.prix.getText());
        donateDetailsController.donationCardController = this;
        donController.sidebarController.getScreen().setCenter(parent);
        System.out.println(parent);*/


        //houni bech temchi lel blasa elli ba3ed sta3mel el logique hedha
    }


}

