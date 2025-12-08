module supermarket {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires xstream;

    opens main to javafx.fxml;
    exports main;
    exports models;
    exports controllers;
    exports utils;
    opens models to javafx.fxml, xstream;
    opens controllers to xstream;
    opens utils to xstream;
}
