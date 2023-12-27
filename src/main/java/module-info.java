module com.example.projet {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires lombok;
    requires fontawesomefx;
    opens com.example.projet to javafx.fxml;
    exports com.example.projet;
    opens com.example.projet.metier to javafx.base;

}