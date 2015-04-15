package com.galt.java.taskgenerator.core.model;

import java.awt.*;

/**
 * Created by vladislav on 3/22/15.
 */
public class Block extends Chunk {

    public Block(int x, int y, int x2, int y2) {
        super(x, y, x2, y2);
    }

    @Override
    public void render(Graphics2D g) {
        g.setStroke(new BasicStroke(5));
        g.setColor(Color.BLACK);
        g.drawRect(x, y, x2, y2);

    }


}
