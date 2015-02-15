package com.galt.java.taskgenerator.core;

/**
 * Created by vladislav on 2/15/15.
 */
public class Point {
    int x;
    int y;
    int cellPxSize = FloorObject.CELL_PX_SIZE;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public int getX() {
        return x * cellPxSize;
    }

    public int getY() {
        return y * cellPxSize;
    }
}
