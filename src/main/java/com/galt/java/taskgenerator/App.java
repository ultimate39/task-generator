package com.galt.java.taskgenerator;

import com.galt.java.taskgenerator.core.utils.CryptoUtils;
import com.galt.java.taskgenerator.core.utils.Logger;
import com.galt.java.taskgenerator.ui.fx.view.controller.Main;
import javafx.application.Application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sun.rmi.runtime.Log;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Random;


public class App extends Application{
    Stage primaryStage;

    @Override
    public void start(final Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        FXMLLoader loader = new FXMLLoader(App.class.getResource("ui/fx/view/controller/main.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Генератор курсовых работ");
        //Parent root = FXMLLoader.load(getClass().getResource("ui/fx/view/controller/task_loader.fxml"));
        //primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        primaryStage.setMinWidth(610);
        primaryStage.setMinHeight(600);
        Main main = loader.getController();
        main.setApp(this);
        Logger.d((System.getProperties().getProperty("user.dir")));
        Logger.d(CryptoUtils.getPathOfData());
    }

    public Stage getStage() {
        return primaryStage;
    }

    public boolean showLoadTaskDataDialog() {
        System.out.println("Show!");
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("ui/fx/view/controller/task_loader.fxml"));
            Parent root =  loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Загрузка условий задания");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);
            dialogStage.setResizable(false);
            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
            return true;
        } catch (IOException e) {
            // Exception gets thrown if the fxml file could not be loaded
            e.printStackTrace();
            return false;
        }
    }

    public File showFileChooserDialog() {
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(extFilter);
        return fileChooser.showSaveDialog(primaryStage);
    }

    public File showDirectoryChooserDialog() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        return directoryChooser.showDialog(primaryStage);
    }

    public static void main(String[] args) {
        App.launch(args);
    }

}
