package com.galt.java.taskgenerator.core;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by vladislav on 2/15/15.
 */
public class Floor extends FloorObject {
    int width, height;
    ArrayList<Hall> halls = new ArrayList<Hall>();
    public Floor(int width, int height) {
        this.width = width;
        this.height = height;
        point = new Point(1, 1);
    }

    @Override
    public void render(Graphics2D graphics2D) {
        //Draw borders
        graphics2D.setColor(Color.BLACK);
        graphics2D.setStroke(new BasicStroke(5));
        graphics2D.drawRect(point.getX(), point.getY(), width * FloorObject.CELL_PX_SIZE, height * FloorObject.CELL_PX_SIZE);

        for(int i = 0; i < halls.size(); i++) {
            System.out.println("Render hall");
            halls.get(i).render(graphics2D);
        }
    }

    public ArrayList<Hall> getHalls() {
        return halls;
    }
}
