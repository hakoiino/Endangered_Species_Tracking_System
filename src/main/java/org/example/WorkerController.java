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

public class WorkerController {

    @FXML
    private TableView<Worker> workerTableView;
    @FXML
    private TableColumn<Worker, Integer> workerIdColumn;
    @FXML
    private TableColumn<Worker, String> workerFirstNameColumn;
    @FXML
    private TableColumn<Worker, String> workerLastNameColumn;
    @FXML
    private TableColumn<Worker, String> workerEmailColumn;
    @FXML
    private TableColumn<Worker, String> workerPhoneNumberColumn;
    @FXML
    private TableColumn<Worker, Integer> workerManagerIdColumn;
    @FXML
    private TableColumn<Worker, Integer> workerOrganizationIdColumn;
    @FXML
    private TableColumn<Worker, Integer> workerProjectIdColumn;

    private ObservableList<Worker> workerList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        workerIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        workerFirstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        workerLastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        workerEmailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        workerPhoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        workerManagerIdColumn.setCellValueFactory(new PropertyValueFactory<>("managerId"));
        workerOrganizationIdColumn.setCellValueFactory(new PropertyValueFactory<>("organizationId"));
        workerProjectIdColumn.setCellValueFactory(new PropertyValueFactory<>("projectId"));

        loadWorkers();
    }

    private void loadWorkers() {
        Connection connection = DatabaseConnection.getConnection();
        String query = "SELECT * FROM workers"; // Ensure the table name is correct

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Worker worker = new Worker();
                worker.setId(resultSet.getInt("worker_id"));  // Ensure this matches your column name
                worker.setFirstName(resultSet.getString("worker_first_name"));  // Ensure this matches your column name
                worker.setLastName(resultSet.getString("worker_last_name"));  // Ensure this matches your column name
                worker.setEmail(resultSet.getString("worker_email"));  // Ensure this matches your column name
                worker.setPhoneNumber(resultSet.getString("worker_phone"));  // Ensure this matches your column name
                worker.setManagerId(resultSet.getInt("manager_id"));  // Ensure this matches your column name
                worker.setOrganizationId(resultSet.getInt("organization_id"));  // Ensure this matches your column name
                worker.setProjectId(resultSet.getInt("project_id"));  // Ensure this matches your column name

                workerList.add(worker);
            }

            workerTableView.setItems(workerList);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void addWorker() {
        // Code to add a new worker
    }
}
