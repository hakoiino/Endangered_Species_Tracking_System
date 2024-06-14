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
    private TextField Fname_input;

    @FXML
    private TextField Lname_input;

    @FXML
    private TextField email_input;

    @FXML
    private TextField manager_input;

    @FXML
    private TextField organization_input;

    @FXML
    private TextField phone_input;

    @FXML
    private TextField project_input;

    @FXML
    private TextField to_delete;

    @FXML
    private Button worker_deleteButton;


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
        Connection connection = DatabaseConnection.getConnection();
        String firstName = Fname_input.getText();
        String lastName = Lname_input.getText();
        String email = email_input.getText();
        String phoneNumber = phone_input.getText();
        int managerId = Integer.parseInt(manager_input.getText());
        int organizationId = Integer.parseInt(organization_input.getText());
        int projectId = Integer.parseInt(project_input.getText());

        String query = "INSERT INTO workers (worker_first_name, worker_last_name, worker_email, worker_phone, manager_id, organization_id, project_id) " +
                "VALUES ('" + firstName + "', '" + lastName + "', '" + email + "', '" + phoneNumber + "', '" + managerId + "', '" + organizationId + "', '" + projectId + "');";

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);

            query = "SELECT * FROM workers";
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            int id = 0;
            while (resultSet.next()) {
                id = resultSet.getInt("worker_id");
                firstName = resultSet.getString("worker_first_name");
                lastName = resultSet.getString("worker_last_name");
                email = resultSet.getString("worker_email");
                phoneNumber = resultSet.getString("worker_phone");
                managerId = resultSet.getInt("manager_id");
                organizationId = resultSet.getInt("organization_id");
                projectId = resultSet.getInt("project_id");
            }
            Worker worker = new Worker();
            worker.setId(id);
            worker.setFirstName(firstName);
            worker.setLastName(lastName);
            worker.setEmail(email);
            worker.setPhoneNumber(phoneNumber);
            worker.setManagerId(managerId);
            worker.setOrganizationId(organizationId);
            worker.setProjectId(projectId);
            workerList.add(worker);
            Fname_input.clear();
            Lname_input.clear();
            email_input.clear();
            phone_input.clear();
            project_input.clear();
            manager_input.clear();
            organization_input.clear();
            workerTableView.setItems(workerList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void deleteWorker() {
        Connection connection = DatabaseConnection.getConnection();
        int id = Integer.parseInt(to_delete.getText());
        String query = "DELETE FROM workers WHERE worker_id = " + id +";";
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            to_delete.clear();
            for(int i = 0; i < workerList.size(); i++) {
                if(workerList.get(i).getId() == id) {
                    workerList.remove(i);
                }
            }
            workerTableView.setItems(workerList);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
