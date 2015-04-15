package com.galt.java.taskgenerator.ui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by vladislav on 2/15/15.
 */
public abstract class Screen extends JFrame implements Canvas.OnDrawListener {
    protected Canvas canvas;

    public Screen(Canvas canvas) {
        this.canvas = canvas;
        canvas.setOnDrawListener(this);
        initUi();
    }

    private void initUi() {
        setTitle("Screen");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container content = getContentPane();
        content.add(canvas);
        setSize(canvas.getCanvasWidth(), canvas.getCanvasHeight());
        setLocationRelativeTo(null);
    }

    public void display() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Screen.this.setVisible(true);
            }
        });
    }
}
