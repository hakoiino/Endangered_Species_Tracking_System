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

    @FXML
    private TextField Fname_input;

    @FXML
    private TextField Lname_input;

    @FXML
    private TextField email_input;

    @FXML
    private Button habitat_deleteButton;


    @FXML
    private TextField organization_input;

    @FXML
    private TextField phone_input;

    @FXML
    private TextField to_delete;

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
        Connection connection = DatabaseConnection.getConnection();
        String Fname = Fname_input.getText();
        String Lname = Lname_input.getText();
        String email = email_input.getText();
        String phone = phone_input.getText();
        int organiztion = Integer.parseInt(organization_input.getText());

        String query = "INSERT INTO managers (manager_first_name, manager_last_name, manager_email, manager_phone, organization_id) " +
                "VALUES ('" + Fname + "', '" + Lname + "', '" + email + "', '" + phone + "', '" + organiztion + "');";

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);

            query = "SELECT * FROM managers";
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            int id = 0;
            while (resultSet.next()) {
                id = resultSet.getInt("manager_id");
                Fname = resultSet.getString("manager_first_name");
                Lname = resultSet.getString("manager_last_name");
                email = resultSet.getString("manager_email");
                phone = resultSet.getString("manager_phone");
                organiztion = resultSet.getInt("organization_id");
            }

            Manager manager = new Manager();
            manager.setId(id);
            manager.setFirstName(Fname);
            manager.setLastName(Lname);
            manager.setEmail(email);
            manager.setPhoneNumber(phone);
            manager.setOrganizationId(organiztion);

            Fname_input.clear();
            Lname_input.clear();
            email_input.clear();
            phone_input.clear();
            organization_input.clear();
            managerList.add(manager);
            managerTableView.setItems(managerList);



        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void deleteManager() {
        Connection connection = DatabaseConnection.getConnection();
        int id = Integer.parseInt(to_delete.getText());
        String query = "DELETE FROM managers WHERE manager_id = " + id +";";
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            to_delete.clear();
            for(int i = 0; i < managerList.size(); i++) {
                if(managerList.get(i).getId() == id) {
                    managerList.remove(i);
                }
            }
            managerTableView.setItems(managerList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
