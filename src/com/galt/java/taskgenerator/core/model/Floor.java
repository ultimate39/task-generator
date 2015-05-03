package com.galt.java.taskgenerator.core.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vladislav on 2/15/15.
 */
public class Floor extends Chunk {
    int[][] field;
    private List<Room> rooms;
    private List<Hall> halls;
    private List<Block> blocks;
    private List<Chunk> chunks;

    public Floor(int width, int height) {
        super(0, 0, width, height);
        field = new int[height][width];
        rooms = new ArrayList<Room>();
        halls = new ArrayList<Hall>();
        blocks = new ArrayList<Block>();
        chunks = new ArrayList<Chunk>();
        chunks.add(new Chunk(0, 0, width, height));
    }

    @Override
    public void render(GraphicsContext g) {
        g.setStroke(Color.BLACK);
        g.setLineWidth(5);
        g.strokeRect(0, 0, getWidth() * SQUARE_SIZE, getHeight() * SQUARE_SIZE);

        int index = 0;
        for(Chunk chunk : chunks) {
            index++;
            chunk.setName("C" + index);
            chunk.render(g);
        }

        for(Block block : blocks) {
            index++;
            block.setName("B" + index);
            block.render(g);
        }

        System.out.println(toString());

    /*    for(Hall hall : halls) {
            hall.render(g);
        }*/
    }

    public int[][] getField() {
        return field;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public List<Block> getBlocks() {
        return blocks;
    }

    public List<Chunk> getChunks() {
        return chunks;
    }

    public List<Hall> getHalls() {
        return halls;
    }

    public float getHallArea() {
        float hallArea = 0;
        for (Hall hall : halls) {
            hallArea += hall.getArea();
        }
        return hallArea;
    }

    @Override
    public String toString() {
        return "Floor{" +
                "x=" + x +
                ", y =" + y +
                ", x2 =" + x2 +
                ", y2 =" + y2 +
                " rooms=" + rooms +
                ", halls=" + halls +
                ", blocks=" + blocks +
                ", chunks=" + chunks +
                '}';
    }
}
