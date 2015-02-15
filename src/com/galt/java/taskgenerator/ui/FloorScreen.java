package com.galt.java.taskgenerator.ui;

import com.galt.java.taskgenerator.core.Floor;

import java.awt.*;

/**
 * Created by vladislav on 2/15/15.
 */
public class FloorScreen extends Screen {
    private Floor floor;

    public FloorScreen(Floor floor, Canvas canvas) {
        super(canvas);
        this.floor = floor;
    }

    @Override
    public void render(Graphics2D g) {
        floor.render(g);
    }

}
