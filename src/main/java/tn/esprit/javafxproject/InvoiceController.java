package tn.esprit.javafxproject;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class InvoiceController {

    @FXML
    private Label prix;

    @FXML
    private Label qt;

    @FXML
    private Label totale;

    public Label getQt() {
        return qt;
    }

    public void setQt(String name) {
        this.qt.setText(name);
    }


    public Label getPrix() {
        return prix;
    }

    public void setPrix(String prix) {
        this.prix.setText(Double.toString( Double.parseDouble(prix)/Double.parseDouble(qt.getText())));
        this.totale.setText(prix);
    }


}
