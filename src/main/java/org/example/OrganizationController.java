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
        // Code to add a new organization
    }
}
