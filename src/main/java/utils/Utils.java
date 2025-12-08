package utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;

public class Utils {
    public static <T> boolean showMissingSelectionAlert(ListView<T> list, String message) {
        if (list.getSelectionModel().getSelectedItem() == null) {
            Alert missingSelection = new Alert(Alert.AlertType.WARNING);
            missingSelection.setTitle("Warning");
            missingSelection.setHeaderText("Missing Selection");
            missingSelection.setContentText(message);
            missingSelection.showAndWait();

            return true;
        }

        return false;
    }

    public static <T> boolean showMissingSelectionAlert(TableView<T> table, String message) {
        if (table.getSelectionModel().getSelectedItem() == null) {
            Alert missingSelection = new Alert(Alert.AlertType.WARNING);
            missingSelection.setTitle("Warning");
            missingSelection.setHeaderText("Missing Selection");
            missingSelection.setContentText(message);
            missingSelection.showAndWait();

            return true;
        }

        return false;
    }
}
