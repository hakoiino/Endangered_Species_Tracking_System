package org.example;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ThreatController {

    @FXML
    private TableView<Threat> threatTableView;
    @FXML
    private TableColumn<Threat, Integer> threatIdColumn;
    @FXML
    private TableColumn<Threat, String> threatNameColumn;
    @FXML
    private TableColumn<Threat, String> threatDescriptionColumn;
    @FXML
    private TableColumn<Threat, String> threatCategoryColumn;
    @FXML
    private TableColumn<Threat, Integer> threatHabitatIdColumn;

    private ObservableList<Threat> threatList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        threatIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        threatNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        threatDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        threatCategoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        threatHabitatIdColumn.setCellValueFactory(new PropertyValueFactory<>("habitatId"));

        loadThreats();
    }

    private void loadThreats() {
        Connection connection = DatabaseConnection.getConnection();
        String query = "SELECT * FROM threats";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Threat threat = new Threat();
                threat.setId(resultSet.getInt("threat_id"));
                threat.setName(resultSet.getString("threat_name"));
                threat.setDescription(resultSet.getString("threat_description"));
                threat.setCategory(resultSet.getString("threat_category"));
                threat.setHabitatId(resultSet.getInt("habitat_id"));

                threatList.add(threat);
            }

            threatTableView.setItems(threatList);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void addThreat() {
        // Code to add a new threat
    }
}
