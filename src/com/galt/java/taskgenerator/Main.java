package com.galt.java.taskgenerator;

import com.galt.java.taskgenerator.ui.Canvas;
import com.galt.java.taskgenerator.ui.Screen;

import javax.swing.*;
import java.awt.*;

public class Main implements Canvas.OnDrawListener {

    public static void main(String[] args) {
        Canvas canvas = new Canvas(700, 700);
        Canvas.OnDrawListener listener = new Canvas.OnDrawListener() {
            @Override
            public void render(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setStroke(new BasicStroke(4));
                g.drawLine(10, 10, 10, 50);
            }
        };
        canvas.setOnDrawListener(listener);
        Screen screen = new Screen(canvas);
        System.out.println("D");
        screen.display();

    }

    @Override
    public void render(Graphics g) {
        g.drawLine(100, 100, 50, 50);
    }
}
