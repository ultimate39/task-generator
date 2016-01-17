package com.galt.java.taskgenerator.core.model.floor;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


/**
 * Created by vladislav on 2/15/15.
 */
public class Chunk {
    public int x, y, x2, y2;
    public static final int SQUARE_SIZE = 8;

    public Chunk(int x, int y, int x2, int y2) {
        this.x = x;
        this.y = y;
        this.x2 = x2;
        this.y2 = y2;
    }

    String name;

    public void setName(String name) {
        this.name = name;
    }

    public void render(GraphicsContext g) {
        g.setLineWidth(5);
        g.setStroke(Color.BLACK);
        if (name != null) {
            g.fillText(name, (x + getWidth() / 2) * SQUARE_SIZE, (y2 - getHeight() / 2) * SQUARE_SIZE);
        }
        g.strokeRect(x * SQUARE_SIZE, y * SQUARE_SIZE, getWidth() * SQUARE_SIZE, getHeight() * SQUARE_SIZE);
    }

    public float getArea() {
        return (Math.abs(x2 - x)) * Math.abs((y2 - y));
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "name='" + name + '\'' +
                ", y2=" + y2 +
                ", x2=" + x2 +
                ", y=" + y +
                ", x=" + x +
                ", width=" + getWidth() +
                ", height=" + getHeight() +
                '}';
    }

    public int getWidth() {
        return Math.abs(x2 - x);
    }

    public int getHeight() {
        return Math.abs(y2 - y);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Chunk) {
            Chunk c = (Chunk) obj;
            return x == c.x &&
                    y == c.y &&
                    x2 == c.x2 &&
                    y2 == c.y2;
        }
        return this == obj;
    }

    public boolean contains(Point point) {
        return point.x < x2 && point.x > x && point.y > y && point.y < y2;
    }
}
