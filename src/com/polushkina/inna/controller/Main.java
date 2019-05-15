package com.polushkina.inna.controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Class Main have application entry point
 */

public class Main extends Application {

   @Override
    public void start(Stage primaryStage) throws Exception{
       Parent root = FXMLLoader.load(getClass().getResource("/com/polushkina/inna/view/sample.fxml"));
       primaryStage.setTitle("Products");
       primaryStage.setScene(new Scene(root, 900, 820));
       primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
