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

public class HabitatController {

    @FXML
    private TableView<Habitat> habitatTableView;
    @FXML
    private TableColumn<Habitat, Integer> habitat_IdColumn;
    @FXML
    private TableColumn<Habitat, String> habitat_NameColumn;
    @FXML
    private TableColumn<Habitat, String> habitat_DescriptionColumn;
    @FXML
    private TableColumn<Habitat, Integer> habitat_regionIdColumn;

    private ObservableList<Habitat> habitatList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        habitat_IdColumn.setCellValueFactory(new PropertyValueFactory<>("habitatId"));
        habitat_NameColumn.setCellValueFactory(new PropertyValueFactory<>("habitatName"));
        habitat_DescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("habitatDescription"));
        habitat_regionIdColumn.setCellValueFactory(new PropertyValueFactory<>("regionId"));

        loadHabitats();
    }

    private void loadHabitats() {
        Connection connection = DatabaseConnection.getConnection();
        String query = "SELECT * FROM habitats";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Habitat habitat = new Habitat();
                habitat.setHabitatId(resultSet.getInt("habitat_id"));
                habitat.setHabitatName(resultSet.getString("habitat_name"));
                habitat.setHabitatDescription(resultSet.getString("habitat_description"));
                habitat.setRegionId(resultSet.getInt("region_id"));

                habitatList.add(habitat);
            }

            habitatTableView.setItems(habitatList);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void addHabitat() {
        // Code to add a new habitat
    }
}

