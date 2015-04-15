package com.galt.java.taskgenerator.core.model;

import java.awt.*;

/**
 * Created by vladislav on 2/15/15.
 */
public class Hall extends Chunk {

    public Hall(int x, int y, int x2, int y2) {
        super(x, y, x2, y2);
    }

    @Override
    public void render(Graphics2D g) {
        g.setStroke(new BasicStroke(5));
        g.setColor(Color.GREEN);
        g.drawRect(x * SQUARE_SIZE, y * SQUARE_SIZE, getWidth() * SQUARE_SIZE, getHeight() * SQUARE_SIZE);
    }
}
