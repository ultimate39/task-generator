package com.galt.java.taskgenerator.core.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


/**
 * Created by vladislav on 3/22/15.
 */
public class Block extends Chunk {

    public Block(int x, int y, int x2, int y2) {
        super(x, y, x2, y2);
    }

    @Override
    public void render(GraphicsContext g) {
        g.setLineWidth(5);
        g.setStroke(Color.AQUAMARINE);
        g.strokeRect(x * SQUARE_SIZE, y * SQUARE_SIZE, getWidth() * SQUARE_SIZE, getHeight() * SQUARE_SIZE);
        if (name != null) {
            g.fillText(name, (x + getWidth() / 2 ) * SQUARE_SIZE, (y2 - getHeight() / 2) * SQUARE_SIZE);
        }
    }
}
