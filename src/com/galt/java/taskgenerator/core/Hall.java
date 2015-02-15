package com.galt.java.taskgenerator.core;

import java.awt.*;

/**
 * Created by vladislav on 2/15/15.
 */
public class Hall extends FloorObject {
    int width;
    int height;
    int orientation;
    public static final int VERTICAL = 0, HORIZONTAL = 1;
    Point pointStart, pointEnd;

    public Hall(int x1, int y1, int width, int height, int orientation) {
        this.width = width;
        this.height = height;
        this.orientation = orientation;
        pointStart = new Point(x1, y1);
        switch (orientation) {
            case VERTICAL:
             pointEnd = new Point(x1, y1 + height);
                break;
            case HORIZONTAL:
                pointEnd = new Point(x1 + height, y1);
                break;
        }
    }

    @Override
    public void render(Graphics2D graphics2D) {
        graphics2D.setColor(Color.BLACK);
        graphics2D.setStroke(new BasicStroke(5));
        graphics2D.drawLine(pointStart.getX(), pointStart.getY(), pointEnd.getX(), pointEnd.getY());
        System.out.println(pointEnd.getX() + ":" + pointEnd.getY());
        switch (orientation) {
            case VERTICAL:
                graphics2D.drawLine(pointStart.getX(), pointStart.getY(), pointEnd.getX(), pointEnd.getY());
                graphics2D.drawLine(pointStart.getX() + width * FloorObject.CELL_PX_SIZE, pointStart.getY(), pointEnd.getX() + width * FloorObject.CELL_PX_SIZE, pointEnd.getY());
            case HORIZONTAL:
                graphics2D.drawLine(pointStart.getX(), pointStart.getY(), pointEnd.getX(), pointEnd.getY());
                graphics2D.drawLine(pointStart.getX(), pointStart.getY() + width * FloorObject.CELL_PX_SIZE, pointEnd.getX() , pointEnd.getY() + width * FloorObject.CELL_PX_SIZE);
        }
    }
}
