package com.galt.java.taskgenerator.ui;

import javax.swing.*;

/**
 * Created by vladislav on 2/15/15.
 */
public class Screen extends JFrame {
    private Canvas canvas;

    public Screen(Canvas canvas) {
        this.canvas = canvas;
        initUi();
    }

    private void initUi() {
        setTitle("Screen");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(canvas);
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
