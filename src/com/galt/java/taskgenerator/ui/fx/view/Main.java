package com.galt.java.taskgenerator.ui.fx.view;

import com.galt.java.taskgenerator.App;
import com.galt.java.taskgenerator.core.generator.Generator;
import com.galt.java.taskgenerator.core.model.floor.Chunk;
import com.galt.java.taskgenerator.core.model.floor.Floor;
import com.galt.java.taskgenerator.core.model.pojo.TaskData;
import com.google.gson.Gson;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.*;
import java.util.Random;

/**
 * Created by vladislav on 4/16/15.
 */
public class Main {
    @FXML
    private Canvas canvas;
    @FXML
    private TextField input;
    @FXML
    private TextFlow textConditions;

    App app;

    public void setApp(App mainApp) {
        this.app = mainApp;
        // Add observable list data to the table
    }

    @FXML
    private void initialize() {
        // Initialize the person table with the two columns.
        Generator generator = new Generator(new Random());
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Floor floor = generator.generateFloor(70, 40);
        canvas.setWidth(floor.getWidth() * Chunk.SQUARE_SIZE);
        canvas.setHeight(floor.getHeight() * Chunk.SQUARE_SIZE);
        floor.render(gc);
    }

    @FXML
    private void onOkClick() {
        long number = Long.parseLong(input.getText());
        Generator generator = new Generator(new Random(number));
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        Floor floor = generator.generateFloor(70, 40);
        canvas.setWidth(floor.getWidth() * Chunk.SQUARE_SIZE);
        canvas.setHeight(floor.getHeight() * Chunk.SQUARE_SIZE);
        floor.render(gc);
        textConditions.getChildren().add(new Text(generator.generateTaskConditions().toString()));
    }

    private String readText() {
        File file = new File("assets/data.json");
        TaskData task = null;
        try {
            task = new Gson().fromJson(new FileReader(file), TaskData.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return task.toString();
    }
}
