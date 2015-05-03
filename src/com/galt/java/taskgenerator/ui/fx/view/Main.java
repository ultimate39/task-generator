package com.galt.java.taskgenerator.ui.fx.view;

import com.galt.java.taskgenerator.App;
import com.galt.java.taskgenerator.core.generator.Generator;
import com.galt.java.taskgenerator.core.model.Chunk;
import com.galt.java.taskgenerator.core.model.Floor;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.util.Random;

/**
 * Created by vladislav on 4/16/15.
 */
public class Main {
    @FXML
    private Canvas canvas;
    @FXML
    private TextField input;

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
    }
}
