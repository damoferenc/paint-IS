module com.projectis.paint {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.projectis.paint to javafx.fxml;
    exports com.projectis.paint;
}