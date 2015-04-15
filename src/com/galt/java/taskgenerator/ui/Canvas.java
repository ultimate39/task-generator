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
        Graphics2D g2d = (Graphics2D) g;
        if (onDrawListener != null) {
            System.out.println("Draw scene");
            onDrawListener.render(g2d);
        }
        /*Random r = new Random();

        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D) image.getGraphics();
        g2.setColor(new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256)));
        g2.fillRect(0, 0, 700, 400);*/
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        if (onDrawListener != null) {
            System.out.println("Draw scene");
            onDrawListener.render(g2d);
        }
    }

    protected void clear() {
    //    image.getGraphics().fillRect(0, 0, WIDTH, HEIGHT);
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
}
