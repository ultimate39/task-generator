package com.galt.java.taskgenerator.core.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


/**
 * Created by vladislav on 2/15/15.
 */
public class Hall extends Chunk {

    public Hall(int x, int y, int x2, int y2) {
        super(x, y, x2, y2);
    }

    @Override
    public void render(GraphicsContext g) {
        g.setLineWidth(5);
        g.setStroke(Color.GREEN);
        g.strokeRect(x * SQUARE_SIZE, y * SQUARE_SIZE, getWidth() * SQUARE_SIZE, getHeight() * SQUARE_SIZE);
    }
}
