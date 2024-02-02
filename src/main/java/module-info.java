module tn.esprit.javafxproject {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires kernel;
    requires layout;
    opens tn.esprit.javafxproject to javafx.fxml;
    exports tn.esprit.javafxproject;
    opens tn.esprit.javafxproject.models to javafx.base;
}