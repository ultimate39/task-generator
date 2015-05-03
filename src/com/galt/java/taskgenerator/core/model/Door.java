package com.galt.java.taskgenerator.core.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Created by Grishechko on 03.05.2015.
 */
public class Door extends Chunk {
    public static int WIDTH = 2;

    public Door(int x, int y, int x2, int y2) {
        super(x, y, x2, y2);
    }

    @Override
    public void render(GraphicsContext g) {
        g.setLineWidth(5);
        g.setStroke(Color.WHITE);
        g.strokeRect(x * SQUARE_SIZE, y * SQUARE_SIZE, getWidth() * SQUARE_SIZE, getHeight() * SQUARE_SIZE);
        if (name != null) {
            g.fillText(name, (x + getWidth() / 2 ) * SQUARE_SIZE, (y2 - getHeight() / 2) * SQUARE_SIZE);
        }
    }

    public static Door leftDoor(Room b) {
        Door d = new Door(b.x, b.y, b.x2, b.y2);
        d.y = d.y + (d.y2 - d.y) / 2  - Door.WIDTH / 2;
        d.x2 = d.x;
        d.y2 = d.y + WIDTH / 2;
        return d;
    }

    public static Door rightDoor(Room b) {
        Door d = new Door(b.x, b.y, b.x2, b.y2);
        d.x = b.x2;
        d.y = d.y + (d.y2 - d.y) / 2  - Door.WIDTH / 2;
        d.y2 = d.y + Door.WIDTH / 2;
        return d;
    }

    public static Door bottomDoor(Room b) {
        Door d = new Door(b.x, b.y, b.x2, b.y2);
        d.y = d.y2;
        d.x = d.x + (d.x2 - d.x) / 2 - Door.WIDTH / 2;
        d.x2 = d.x + Door.WIDTH / 2;
        return d;
    }

    public static Door topDoor(Room b) {
        Door d = new Door(b.x, b.y, b.x2, b.y2);
        d.x = d.x + (d.x2 - d.x) / 2 - Door.WIDTH / 2;
        d.x2 = d.x + Door.WIDTH / 2;
        d.y2 = d.y;
        return d;
    }
}
