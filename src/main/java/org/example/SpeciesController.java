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
    private TextField habitat_input;

    @FXML
    private TextField name_input;

    @FXML
    private TextField population_input;

    @FXML
    private Button species_deleteButton;

    @FXML
    private TextField status_input;

    @FXML
    private TextField to_delete;

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
        Connection connection = DatabaseConnection.getConnection();
        String name = name_input.getText();
        String conservationStatus = status_input.getText();
        int population = Integer.parseInt(population_input.getText());
        int habitatId = Integer.parseInt(habitat_input.getText());

        String query = "INSERT INTO species (species_name, conservation_status, population, habitat_id) " +
                "VALUES ('" + name + "', '" + conservationStatus + "', '" + population + "', '" + habitatId + "');";

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);

            query = "SELECT * FROM species";
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            int id = 0;

            while (resultSet.next()) {
                id = resultSet.getInt("species_id");
                name = resultSet.getString("species_name");
                conservationStatus = resultSet.getString("conservation_status");
                population = resultSet.getInt("population");
                habitatId = resultSet.getInt("habitat_id");
            }
            Species species = new Species();
            species.setId(id);
            species.setName(name);
            species.setConservationStatus(conservationStatus);
            species.setPopulation(population);
            species.setHabitatId(habitatId);
            speciesList.add(species);
            name_input.clear();
            status_input.clear();
            population_input.clear();
            habitat_input.clear();
            speciesTableView.setItems(speciesList);
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    @FXML
    void deleteSpecies() {
        Connection connection = DatabaseConnection.getConnection();
        int id = Integer.parseInt(to_delete.getText());
        String query = "DELETE FROM species WHERE species_id = " + id +";";
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            to_delete.clear();
            for(int i = 0; i < speciesList.size(); i++) {
                if(speciesList.get(i).getId() == id) {
                    speciesList.remove(i);
                }
            }
            speciesTableView.setItems(speciesList);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
