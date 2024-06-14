package org.example;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
    private TextField category_input;

    @FXML
    private TextField description_input;

    @FXML
    private TextField habitat_input;

    @FXML
    private TextField name_input;


    @FXML
    private Button threat_deleteButton;

    @FXML
    private TextField to_delete;

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
        Connection connection = DatabaseConnection.getConnection();
        String name = name_input.getText();
        String description = description_input.getText();
        String category = category_input.getText();
        int habitatId = Integer.parseInt(habitat_input.getText());

        String query = "INSERT INTO threats (threat_name, threat_description, threat_category, habitat_id) " +
                "VALUES ('" + name + "', '" + description + "', '" + category + "', '" + habitatId + "');";

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);

            query = "SELECT * FROM threats";
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            int id = 0;
            while (resultSet.next()) {
                id = resultSet.getInt("threat_id");
                name = resultSet.getString("threat_name");
                description = resultSet.getString("threat_description");
                category = resultSet.getString("threat_category");
                habitatId = resultSet.getInt("habitat_id");
            }
            Threat threat = new Threat();
            threat.setId(id);
            threat.setName(name);
            threat.setDescription(description);
            threat.setCategory(category);
            threat.setHabitatId(habitatId);
            threatList.add(threat);
            name_input.clear();
            description_input.clear();
            habitat_input.clear();
            threatTableView.setItems(threatList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void deleteThreat() {
        Connection connection = DatabaseConnection.getConnection();
        int id = Integer.parseInt(to_delete.getText());
        String query = "DELETE FROM threats WHERE threat_id = " + id +";";
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            to_delete.clear();
            for(int i = 0; i < threatList.size(); i++) {
                if(threatList.get(i).getId() == id) {
                    threatList.remove(i);
                }
            }
            threatTableView.setItems(threatList);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
