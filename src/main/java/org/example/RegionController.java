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

public class RegionController {

    @FXML
    private TableView<Region> regionTableView;
    @FXML
    private TableColumn<Region, Integer> regionIdColumn;
    @FXML
    private TableColumn<Region, String> regionNameColumn;
    @FXML
    private TableColumn<Region, String> regionLocationColumn;
    @FXML
    private TableColumn<Region, String> regionDescriptionColumn;


    @FXML
    private TextField description_input;

    @FXML
    private TextField location_input;

    @FXML
    private TextField name_input;


    @FXML
    private Button region_deleteButton;

    @FXML
    private TextField to_delete;



    private ObservableList<Region> regionList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        regionIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        regionNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        regionLocationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        regionDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        loadRegions();
    }

    private void loadRegions() {
        Connection connection = DatabaseConnection.getConnection();
        String query = "SELECT * FROM regions"; // Make sure the table name is correct

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Region region = new Region();
                region.setId(resultSet.getInt("region_id"));
                region.setName(resultSet.getString("region_name"));
                region.setLocation(resultSet.getString("region_location"));
                region.setDescription(resultSet.getString("region_description"));

                regionList.add(region);
            }

            regionTableView.setItems(regionList);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void addRegion() {
        Connection connection = DatabaseConnection.getConnection();
        String name = name_input.getText();
        String location = location_input.getText();
        String description = description_input.getText();

        String query = "INSERT INTO regions (region_name, region_location, region_description) " +
                "VALUES ('" + name + "', '" + location + "', '" + description + "');";

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);

            query = "SELECT * FROM regions";
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            int id = 0;
            while (resultSet.next()) {
                id = resultSet.getInt("region_id");
                name = resultSet.getString("region_name");
                location = resultSet.getString("region_location");
                description = resultSet.getString("region_description");
            }

            Region region = new Region();
            region.setId(id);
            region.setName(name);
            region.setLocation(location);
            region.setDescription(description);
            regionList.add(region);
            name_input.clear();
            location_input.clear();
            description_input.clear();
            regionTableView.setItems(regionList);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void deleteRegion() {
        Connection connection = DatabaseConnection.getConnection();
        int id = Integer.parseInt(to_delete.getText());
        String query = "DELETE FROM regions WHERE region_id = " + id +";";
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            to_delete.clear();
            for(int i = 0; i < regionList.size(); i++) {
                if(regionList.get(i).getId() == id) {
                    regionList.remove(i);
                }
            }
            regionTableView.setItems(regionList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
