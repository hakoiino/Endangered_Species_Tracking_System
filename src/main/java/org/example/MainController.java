package org.example;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class MainController {

    @FXML
    private TabPane tabPane;

    @FXML
    private Tab habitatTab;

    @FXML
    private Tab threatTab;

    @FXML
    private Tab regionTab;

    @FXML
    private Tab speciesTab;

    @FXML
    private Tab organizationTab;

    // Add more Tabs here if needed

    @FXML
    public void initialize() {
        // This method is called by the FXMLLoader when initialization is complete
        // You can add initialization code here if necessary
    }

    // Add methods to handle tab actions if needed

}
