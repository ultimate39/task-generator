package com.galt.java.taskgenerator.core.model;

import java.awt.*;

/**
 * Created by vladislav on 2/15/15.
 */
public class Chunk {
    public int x, y, x2, y2;
    public static final int SQUARE_SIZE = 10;

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

    public void render(Graphics2D g) {
        g.setStroke(new BasicStroke(5));
        g.setColor(Color.BLACK);
        if (name != null) {
            System.out.println(name);
            g.drawString(name, (x + getWidth() / 2 ) * SQUARE_SIZE, (y2 - getHeight() / 2) * SQUARE_SIZE);
        }
        g.drawRect(x * SQUARE_SIZE, y * SQUARE_SIZE, getWidth() * SQUARE_SIZE, getHeight() * SQUARE_SIZE);
    }

    public float getArea() {
        return (Math.abs(x2 - x)) * Math.abs((y2 - y));
    }

    @Override
    public String toString() {
        return "Chunk{" +
                "x=" + x +
                ", y=" + y +
                ", x2=" + x2 +
                ", y2=" + y2 +
                '}';
    }

    public int getWidth() {
        return Math.abs(x2 - x);
    }

    public int getHeight() {
        return Math.abs(y2 - y);
    }
}
