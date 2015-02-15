package com.galt.java.taskgenerator.core;

import java.awt.*;

/**
 * Created by vladislav on 2/15/15.
 */
public abstract class FloorObject {
    Point point;
    public static final int CELL_PX_SIZE = 12;
    int convertToScreen(int coordinates) {
       return coordinates * CELL_PX_SIZE;
    }
    public abstract void render(Graphics2D graphics2D);
}
