package org.example;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;

import javafx.scene.control.TextField;

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

    @FXML
    private TextField name_input;

    @FXML
    private TextField region_input;

    @FXML
    private TextField description_input;

    private ObservableList<Habitat> habitatList = FXCollections.observableArrayList();

    @FXML
    private TextField to_delete;

    @FXML
    private Button habitat_deleteButton;

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
        Connection connection = DatabaseConnection.getConnection();
        String name = name_input.getText();
        int region = Integer.parseInt(region_input.getText());
        String description = description_input.getText();
        String query = "INSERT INTO habitats (habitat_name, habitat_description, region_id) " +
                "VALUES ('" + name + "', '" + description + "', '" + region + "');";
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);

            query = "SELECT * FROM habitats";
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            int id = 0;
            while(resultSet.next()) {
                id = resultSet.getInt("habitat_id");
                name = resultSet.getString("habitat_name");
                description = resultSet.getString("habitat_description");
                region = resultSet.getInt("region_id");


            }
            Habitat habitat = new Habitat();
            habitat.setHabitatId(id);
            habitat.setHabitatName(name);
            habitat.setHabitatDescription(description);
            habitat.setRegionId(region);

            name_input.clear();
            region_input.clear();
            description_input.clear();

            habitatList.add(habitat);
            habitatTableView.setItems(habitatList);

        } catch (SQLException e) {
            e.printStackTrace();
        }



    }

    @FXML
    void deleteHabitat() {
        Connection connection = DatabaseConnection.getConnection();
        int id = Integer.parseInt(to_delete.getText());
        String query = "DELETE FROM habitats WHERE habitat_id = " + id +";";
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            to_delete.clear();
            for(int i = 0; i < habitatList.size(); i++) {
                if(habitatList.get(i).getHabitatId() == id) {
                    habitatList.remove(i);
                }
            }
            habitatTableView.setItems(habitatList);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}

