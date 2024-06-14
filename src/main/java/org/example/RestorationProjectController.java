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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RestorationProjectController {

    @FXML
    private TextField Edate_input;

    @FXML
    private TableColumn<?, ?> projectSDateColumn;

    @FXML
    private TextField Sdate_input;

    @FXML
    private TextField description_input;

    @FXML
    private TextField habitat_input;

    @FXML
    private TextField manager_input;

    @FXML
    private TextField name_input;

    @FXML
    private TextField organization_input;

    @FXML
    private TableColumn<?, ?> projectDescriptionColumn;

    @FXML
    private TableColumn<?, ?> projectEDateColumn;

    @FXML
    private TableColumn<?, ?> projectHabitatColumn;

    @FXML
    private TableColumn<?, ?> projectIDColumn;

    @FXML
    private TableColumn<?, ?> projectManagerColumn;

    @FXML
    private TableColumn<?, ?> projectNameColumn;

    @FXML
    private TableColumn<?, ?> projectOrganizationColumn;

    @FXML
    private TableView<Project> projectTableView;

    @FXML
    private Button project_add_button;

    @FXML
    private Button project_deleteButton;

    @FXML
    private TextField to_delete;

    private ObservableList<Project> projectList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        projectIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        projectDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        projectEDateColumn.setCellValueFactory(new PropertyValueFactory<>("end_date"));
        projectNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        projectOrganizationColumn.setCellValueFactory(new PropertyValueFactory<>("organization_id"));
        projectManagerColumn.setCellValueFactory(new PropertyValueFactory<>("manager_id"));
        projectSDateColumn.setCellValueFactory(new PropertyValueFactory<>("start_date"));
        projectHabitatColumn.setCellValueFactory(new PropertyValueFactory<>("habitat_id"));

        loadProjects();
    }

    private void loadProjects() {
        Connection connection = DatabaseConnection.getConnection();
        String query = "SELECT * FROM restoration_projects"; // Make sure the table name is correct

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                Project project = new Project(resultSet.getInt("project_id"),
                        resultSet.getString("project_name"),
                        resultSet.getString("start_date"),
                        resultSet.getString("end_date"),
                        resultSet.getString("project_description"),
                        resultSet.getInt("manager_id"),
                        resultSet.getInt("organization_id"),
                        resultSet.getInt("habitat_id"));
                projectList.add(project);

            }

            projectTableView.setItems(projectList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void addProject(){
        Connection connection = DatabaseConnection.getConnection();
        String name = name_input.getText();
        String start_date = Sdate_input.getText();
        String end_date = Edate_input.getText();
        String description = description_input.getText();
        int manager_id = Integer.parseInt(manager_input.getText());
        int organization_id = Integer.parseInt(manager_input.getText());
        int habitat_id = Integer.parseInt(manager_input.getText());




        String query = "INSERT INTO restoration_projects (project_name, start_date, end_date, project_description, manager_id, organization_id, habitat_id) " +
                "VALUES ('" + name + "', '" + start_date + "', '" + end_date + "', '" + description + "', '" + manager_id + "', '" + organization_id + "', '" + habitat_id + "');";

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);

            query = "SELECT * FROM restoration_projects";
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            int id = 0;
            while (resultSet.next()) {
                id = resultSet.getInt("project_id");
                name = resultSet.getString("project_name");
                start_date = resultSet.getString("start_date");
                end_date = resultSet.getString("end_date");
                description = resultSet.getString("project_description");
                manager_id = resultSet.getInt("manager_id");
                organization_id = resultSet.getInt("organization_id");
                habitat_id = resultSet.getInt("habitat_id");
            }
            Project project = new Project(id, name, start_date, end_date, description, manager_id, organization_id, habitat_id);
            projectList.add(project);
            name_input.clear();
            description_input.clear();
            manager_input.clear();
            organization_input.clear();
            habitat_input.clear();
            Sdate_input.clear();
            Edate_input.clear();
            projectTableView.setItems(projectList);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void deleteProject() {
        Connection connection = DatabaseConnection.getConnection();
        int id = Integer.parseInt(to_delete.getText());
        String query = "DELETE FROM restoration_projects WHERE project_id = " + id +";";
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            to_delete.clear();
            for(int i = 0; i < projectList.size(); i++) {
                if(projectList.get(i).getId() == id) {
                    projectList.remove(i);
                }
            }
            projectTableView.setItems(projectList);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
