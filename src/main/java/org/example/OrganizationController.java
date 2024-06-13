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

public class OrganizationController {

    @FXML
    private TableView<Organization> organizationTableView;
    @FXML
    private TableColumn<Organization, Integer> organizationIdColumn;
    @FXML
    private TableColumn<Organization, String> organizationNameColumn;
    @FXML
    private TableColumn<Organization, String> organizationEmailColumn;
    @FXML
    private TableColumn<Organization, String> organizationPhoneNumberColumn;
    @FXML
    private TableColumn<Organization, String> organizationDescriptionColumn;

    private ObservableList<Organization> organizationList = FXCollections.observableArrayList();



    @FXML
    private TextField description_input;

    @FXML
    private TextField email_input;

    @FXML
    private TextField name_input;

    @FXML
    private Button organization_deleteButton;

    @FXML
    private TextField phone_input;

    @FXML
    private TextField to_delete;


    @FXML
    private void initialize() {
        organizationIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        organizationNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        organizationEmailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        organizationPhoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        organizationDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        loadOrganizations();
    }

    private void loadOrganizations() {
        Connection connection = DatabaseConnection.getConnection();
        String query = "SELECT * FROM organizations"; // Ensure the table name is correct

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Organization organization = new Organization();
                organization.setId(resultSet.getInt("organization_id"));  // Ensure this matches your column name
                organization.setName(resultSet.getString("organization_name"));  // Ensure this matches your column name
                organization.setEmail(resultSet.getString("organization_email"));  // Ensure this matches your column name
                organization.setPhoneNumber(resultSet.getString("organization_phone"));  // Ensure this matches your column name
                organization.setDescription(resultSet.getString("organization_description"));  // Ensure this matches your column name

                organizationList.add(organization);
            }

            organizationTableView.setItems(organizationList);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void addOrganization() {
        Connection connection = DatabaseConnection.getConnection();
        String name = name_input.getText();
        String email = email_input.getText();
        String phoneNumber = phone_input.getText();
        String description = description_input.getText();

        String query = "INSERT INTO organizations (organization_name, organization_email, organization_phone, organization_description) " +
                "VALUES ('" + name + "', '" + email + "', '" + phoneNumber + "', '" + description + "');";

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);

            query = "SELECT * FROM organizations";
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            int id = 0;
            while (resultSet.next()) {
                id = resultSet.getInt("organization_id");
                name = resultSet.getString("organization_name");
                email = resultSet.getString("organization_email");
                phoneNumber = resultSet.getString("organization_phone");
                description = resultSet.getString("organization_description");
            }

            Organization organization = new Organization();
            organization.setId(id);
            organization.setName(name);
            organization.setEmail(email);
            organization.setPhoneNumber(phoneNumber);
            organization.setDescription(description);

            organizationList.add(organization);
            name_input.clear();
            email_input.clear();
            phone_input.clear();
            description_input.clear();

            organizationTableView.setItems(organizationList);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    @FXML
    void deleteOrganization() {
        Connection connection = DatabaseConnection.getConnection();
        int id = Integer.parseInt(to_delete.getText());
        String query = "DELETE FROM organizations WHERE organization_id = " + id +";";
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            to_delete.clear();
            for(int i = 0; i < organizationList.size(); i++) {
                if(organizationList.get(i).getId() == id) {
                    organizationList.remove(i);
                }
            }
            organizationTableView.setItems(organizationList);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
