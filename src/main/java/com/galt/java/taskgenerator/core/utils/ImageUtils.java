package com.galt.java.taskgenerator.core.utils;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;

import javax.imageio.ImageIO;

import java.io.File;

/**
 * Created by Grishechko on 08.06.2015.
 */
public class ImageUtils {
    public static void saveImage(String name, Canvas canvas) {
        WritableImage wim = new WritableImage((int)Math.round(canvas.getWidth()), (int)Math.round(canvas.getHeight()));
        GraphicsContext gc = canvas.getGraphicsContext2D();
        canvas.snapshot(null, wim);
        File file = new File(name);
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(wim, null), "png", file);
        } catch (Exception s) {
        }
    }
}
