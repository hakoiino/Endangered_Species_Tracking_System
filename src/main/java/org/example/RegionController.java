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
        // Code to add a new region
    }
}
