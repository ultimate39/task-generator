package com.galt.java.taskgenerator.core.model.floor;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.Comparator;
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
    private List<Door> doors;

    public Floor(int width, int height) {
        super(0, 0, width, height);
        field = new int[height][width];
        rooms = new ArrayList<>();
        halls = new ArrayList<>();
        blocks = new ArrayList<>();
        chunks = new ArrayList<>();
        doors = new ArrayList<>();
        chunks.add(new Chunk(0, 0, width, height));
    }

    @Override
    public void render(GraphicsContext g) {
        int index = 0;
        for (Chunk chunk : chunks) {
            index++;
            chunk.render(g);
        }

        for (Block block : blocks) {
            index++;
            block.setName("B" + index);
            block.render(g);
        }

        index = 0;

        for (Room room : rooms) {
            index++;
            room.setName("R" + index);
            room.render(g);
        }

        for (Door door : doors) {
            door.render(g);
        }

        System.out.println(toString());

        g.setLineWidth(5);
        g.setStroke(javafx.scene.paint.Color.BLACK);
        g.strokeRect(0, 0, getWidth() * SQUARE_SIZE, getHeight() * SQUARE_SIZE);
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

    public List<Door> getDoors() {
        return doors;
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

    public void sortBlocksByArea() {
        blocks.sort(new Comparator<Block>() {
            @Override
            public int compare(Block o1, Block o2) {
                if (o1.getArea() > o2.getArea()) {
                    return 1;
                } else if (o1.getArea() < o2.getArea()) {
                    return 0;
                } else {
                    return 0;
                }
            }
        });
    }
}
