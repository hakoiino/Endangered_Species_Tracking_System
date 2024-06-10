package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cs4750.fxml"));
            AnchorPane root = loader.load();

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Endangered Species Tracking System");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

/*
public class Main extends Application {
    public static void main(String[] args) {
        launch(args); //calls the start function
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //create a Label to say Hello World
        Label helloLabel = new Label("Hello World!");

        //create a pane to act as the root Pane
        Pane root = new FlowPane();

        //add the Label to the root Pane
        root.getChildren().add(helloLabel);

        //Create a Scene containing our root Pane
        Scene scene = new Scene(root);

        //Put our scene on stage
        primaryStage.setScene(scene);

        //display the stage
        primaryStage.show();
    }
}

 */