package com.galt.java.taskgenerator.ui;

import com.galt.java.taskgenerator.core.model.Floor;

import javax.swing.*;
import java.awt.*;

/**
 * Created by vladislav on 2/15/15.
 */
public class FloorScreen extends Screen {
    private Floor floor;

    public FloorScreen(Floor floor, Canvas canvas) {
        super(canvas);
        this.floor = floor;
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
    }

    @Override
    public void render(Graphics2D g) {
        if(floor != null) {
            floor.render(g);
        }
    }

    public void changeFloor(Floor floor) {
        this.floor = floor;
        revalidate();
        repaint();
        canvas.clear();
        canvas.revalidate();
        canvas.repaint();
    }
}
