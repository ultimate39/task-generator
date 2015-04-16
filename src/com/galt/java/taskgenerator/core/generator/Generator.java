package com.galt.java.taskgenerator.core.generator;

import com.galt.java.taskgenerator.core.model.Block;
import com.galt.java.taskgenerator.core.model.Chunk;
import com.galt.java.taskgenerator.core.model.Floor;
import com.galt.java.taskgenerator.core.model.Hall;

import java.util.List;
import java.util.Random;

/**
 * Created by vladislav on 2/15/15.
 */
public class Generator {
    private final float MIN_HALlRATE = 10;
    private final int MIN_BLOCKS = 4;
    private float initHallRate = 0;
    private float minSplittableArea = 10;
    private int MIN_DIMENSION_OF_BLOCK = 6;
    private int HALL_WIDTH = 2;
    private Random random;

    public Generator(Random random) {
        this.random = random;
    }

    public Floor generateFloor(int width, int height) {
        Floor floor = new Floor(width, height);
        toBlocks(floor);
        initHallRate = MIN_HALlRATE;
        System.out.println(floor.toString());
        return floor;
    }

    private void toBlocks(Floor floor) {
        while (!floor.getChunks().isEmpty() && ((floor.getHallArea() / floor.getArea()) * 100 < MIN_HALlRATE)) {
            Chunk chunk = floor.getChunks().get(0);
            if (chunk.getArea() > minSplittableArea) {
                splitToBlocks(floor, chunk);
            } else {
                floor.getBlocks().add(new Block(chunk.x, chunk.y, chunk.x2, chunk.y2));
                floor.getChunks().remove(chunk);
            }
        }

/*
        if(floor.getChunks().size() < MIN_BLOCKS) {
            initHallRate += 1;
            System.out.println(initHallRate);
            System.out.println(floor.getChunks().size());
            System.out.println(floor.toString());
            toBlocks(floor);
        } else {
            chunksToBlocks(floor);
        }
*/
    }

    private void chunksToBlocks(Floor floor) {
        List<Chunk> chunks = floor.getChunks();
        List<Block> blocks = floor.getBlocks();
        for (Chunk chunk : floor.getChunks()) {
            blocks.add(new Block(chunk.x, chunk.y, chunk.x2, chunk.y2));
        }
        chunks.clear();
    }

    private void splitToBlocks(Floor floor, Chunk chunk) {
        System.out.println("-------------------");
        System.out.println("Chunk");
        System.out.println("Width:" + chunk.getWidth());
        System.out.println("Height:" + chunk.getHeight());

        switch (random.nextInt(2)) {
            case 0:
                if (chunk.getHeight() >= MIN_DIMENSION_OF_BLOCK * 2 + HALL_WIDTH) {
                    splitChunkHorizontally(floor, chunk);
                } else if (chunk.getWidth() > MIN_DIMENSION_OF_BLOCK * 2 + HALL_WIDTH) {
                    splitChunkVertically(floor, chunk);
                } else {
                    floor.getBlocks().add(new Block(chunk.x, chunk.y, chunk.x2, chunk.y2));
                    floor.getChunks().remove(chunk);
                }
                break;
            case 1:
                if (chunk.getWidth() > MIN_DIMENSION_OF_BLOCK * 2 + HALL_WIDTH) {
                    splitChunkVertically(floor, chunk);
                } else if (chunk.getWidth() > MIN_DIMENSION_OF_BLOCK * 2 + HALL_WIDTH) {
                    splitChunkVertically(floor, chunk);
                } else {
                    floor.getBlocks().add(new Block(chunk.x, chunk.y, chunk.x2, chunk.y2));
                    floor.getChunks().remove(chunk);
                }
                break;
        }
    }


    private void splitChunkHorizontally(Floor floor, Chunk chunk) {
        int range = (chunk.getHeight() - MIN_DIMENSION_OF_BLOCK * 2 - 2);
        int deltaPositionOfHall = 0;
        if (range == 0) {
            deltaPositionOfHall = MIN_DIMENSION_OF_BLOCK;
        } else {
            deltaPositionOfHall = random.nextInt(range) + MIN_DIMENSION_OF_BLOCK;
        }


        Chunk chunkA = new Chunk(chunk.x, chunk.y, chunk.x2, chunk.y + deltaPositionOfHall);
        Chunk chunkB = new Chunk(chunk.x, chunk.y + deltaPositionOfHall + 2, chunk.x2, chunk.y2);
        Hall hall = new Hall(chunk.x, chunk.y + deltaPositionOfHall, chunk.x2, chunk.y + deltaPositionOfHall + 2);
        floor.getChunks().remove(chunk);
        floor.getChunks().add(chunkA);
        floor.getChunks().add(chunkB);
        floor.getHalls().add(hall);
    }

    private void splitChunkVertically(Floor floor, Chunk chunk) {
        int range = (chunk.getWidth() - MIN_DIMENSION_OF_BLOCK * 2 - 2);
        int deltaPositionOfHall = 0;
        if (range == 0) {
            deltaPositionOfHall = MIN_DIMENSION_OF_BLOCK;
        } else {
            deltaPositionOfHall = random.nextInt(range) + MIN_DIMENSION_OF_BLOCK;
        }

        Chunk chunkA = new Chunk(chunk.x, chunk.y, chunk.x + deltaPositionOfHall, chunk.y2);
        Chunk chunkB = new Chunk(chunk.x + deltaPositionOfHall + 2, chunk.y, chunk.x2, chunk.y2);
        Hall hall = new Hall(chunk.x + deltaPositionOfHall, chunk.y, chunk.x + deltaPositionOfHall + 2, chunk.y2);
        floor.getChunks().remove(chunk);
        floor.getChunks().add(chunkA);
        floor.getChunks().add(chunkB);
        floor.getHalls().add(hall);
    }
}
