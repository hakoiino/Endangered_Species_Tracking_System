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

public class SpeciesController {

    @FXML
    private TableView<Species> speciesTableView;
    @FXML
    private TableColumn<Species, Integer> speciesIdColumn;
    @FXML
    private TableColumn<Species, String> speciesNameColumn;
    @FXML
    private TableColumn<Species, String> speciesConservationStatusColumn;
    @FXML
    private TableColumn<Species, Integer> speciesPopulationColumn;
    @FXML
    private TableColumn<Species, Integer> speciesHabitatIdColumn;

    private ObservableList<Species> speciesList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        speciesIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        speciesNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        speciesConservationStatusColumn.setCellValueFactory(new PropertyValueFactory<>("conservationStatus"));
        speciesPopulationColumn.setCellValueFactory(new PropertyValueFactory<>("population"));
        speciesHabitatIdColumn.setCellValueFactory(new PropertyValueFactory<>("habitatId"));

        loadSpecies();
    }

    private void loadSpecies() {
        Connection connection = DatabaseConnection.getConnection();
        String query = "SELECT * FROM species"; // Make sure the table name is correct

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Species species = new Species();
                species.setId(resultSet.getInt("species_id"));
                species.setName(resultSet.getString("species_name"));
                species.setConservationStatus(resultSet.getString("conservation_status"));
                species.setPopulation(resultSet.getInt("population"));
                species.setHabitatId(resultSet.getInt("habitat_id"));

                speciesList.add(species);
            }

            speciesTableView.setItems(speciesList);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void addSpecies() {
        // Code to add a new species
    }
}
