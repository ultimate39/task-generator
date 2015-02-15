package com.galt.java.taskgenerator.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Created by vladislav on 2/15/15.
 */
public class Canvas extends JPanel {
    private final int WIDTH;
    private final int HEIGHT;
    private BufferedImage image;
    private OnDrawListener onDrawListener;

    public Canvas(int width, int height) {
        WIDTH = width;
        HEIGHT = height;
        image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_ARGB);
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
        if (onDrawListener != null) {
            onDrawListener.render((Graphics2D) image.getGraphics());
        }
        g.drawImage(image, 0, 0, null);
        System.out.println("paintComponent");
    }

    @Override
    public Dimension getPreferredSize() {
        return (new Dimension(WIDTH, HEIGHT));
    }

    public void setOnDrawListener(OnDrawListener onDrawListener) {
        this.onDrawListener = onDrawListener;
    }

    public interface OnDrawListener {
        public void render(Graphics2D g);
    }

    public Graphics2D getGraphics() {
        return (Graphics2D) image.getGraphics();
    }
}
