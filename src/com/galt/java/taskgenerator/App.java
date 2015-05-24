package com.galt.java.taskgenerator;

import javafx.application.Application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class App extends Application {

    @Override
    public void start(final Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("ui/fx/view/controller/main.fxml"));
        primaryStage.setTitle("Генератор курсовых без регистраций и смс!!!!!");
        //Parent root = FXMLLoader.load(getClass().getResource("ui/fx/view/controller/task_loader.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        App.launch(args);
    }

}
