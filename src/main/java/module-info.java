module tn.esprit.javafxproject {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens tn.esprit.javafxproject to javafx.fxml;
    exports tn.esprit.javafxproject;
}