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

public class ManagerController {

    @FXML
    private TableView<Manager> managerTableView;
    @FXML
    private TableColumn<Manager, Integer> managerIdColumn;
    @FXML
    private TableColumn<Manager, String> managerFirstNameColumn;
    @FXML
    private TableColumn<Manager, String> managerLastNameColumn;
    @FXML
    private TableColumn<Manager, String> managerEmailColumn;
    @FXML
    private TableColumn<Manager, String> managerPhoneNumberColumn;
    @FXML
    private TableColumn<Manager, Integer> managerOrganizationIdColumn;

    private ObservableList<Manager> managerList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        managerIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        managerFirstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        managerLastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        managerEmailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        managerPhoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        managerOrganizationIdColumn.setCellValueFactory(new PropertyValueFactory<>("organizationId"));

        loadManagers();
    }

    private void loadManagers() {
        Connection connection = DatabaseConnection.getConnection();
        String query = "SELECT * FROM managers"; // Ensure the table name is correct

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Manager manager = new Manager();
                manager.setId(resultSet.getInt("manager_id"));  // Ensure this matches your column name
                manager.setFirstName(resultSet.getString("manager_first_name"));  // Ensure this matches your column name
                manager.setLastName(resultSet.getString("manager_last_name"));  // Ensure this matches your column name
                manager.setEmail(resultSet.getString("manager_email"));  // Ensure this matches your column name
                manager.setPhoneNumber(resultSet.getString("manager_phone"));  // Ensure this matches your column name
                manager.setOrganizationId(resultSet.getInt("organization_id"));  // Ensure this matches your column name

                managerList.add(manager);
            }

            managerTableView.setItems(managerList);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void addManager() {
        // Code to add a new manager
    }
}
