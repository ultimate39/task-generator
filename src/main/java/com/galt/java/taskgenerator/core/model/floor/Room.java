package com.galt.java.taskgenerator.core.model.floor;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Created by vladislav on 3/22/15.
 */
public class Room extends Chunk {
    public Room(int x, int y, int x2, int y2) {
        super(x, y, x2, y2);
    }

    //Not to draw
    private Door door;
    private boolean isElevator = false;

    @Override
    public void render(GraphicsContext g) {
        g.setLineWidth(5);
        g.setStroke(javafx.scene.paint.Color.BLACK);
        g.setFill(javafx.scene.paint.Color.BLACK);
        g.strokeRect(x * SQUARE_SIZE, y * SQUARE_SIZE, getWidth() * SQUARE_SIZE, getHeight() * SQUARE_SIZE);
        if (name != null) {
            g.fillText(name, (x + getWidth() / 2) * SQUARE_SIZE, (y2 - getHeight() / 2) * SQUARE_SIZE);
        }
        if(isElevator) {
            g.setFill(Color.RED);
            g.fillText("Лифт", (x + getWidth() / 3) * SQUARE_SIZE, (y2 - getHeight() / 4) * SQUARE_SIZE);
        }
    }

    public Door getDoor() {
        return door;
    }

    public void setDoor(Door door) {
        this.door = door;
    }

    public boolean isElevator() {
        return isElevator;
    }

    public void setElevator(boolean elevator) {
        isElevator = elevator;
    }
}
