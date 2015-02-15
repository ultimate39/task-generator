package com.galt.java.taskgenerator.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Created by vladislav on 2/15/15.
 */
public class Canvas extends JPanel {
    private Graphics2D canvas;
    private final int WIDTH;
    private final int HEIGHT;
    private OnDrawListener onDrawListener;

    public Canvas(int width, int height) {
        WIDTH = width;
        HEIGHT = height;
    }

    public int getCanvasWidth() {
        return WIDTH;
    }

    public int getCanvasHeight() {
        return HEIGHT;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(onDrawListener != null) {
            onDrawListener.render(g);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return (new Dimension(WIDTH, HEIGHT));
    }


    public void setOnDrawListener(OnDrawListener onDrawListener) {
        this.onDrawListener = onDrawListener;
    }

    public Graphics2D getGraphics() {
        return canvas;
    }

    public interface OnDrawListener {
        public void render(Graphics g);
    }
}
