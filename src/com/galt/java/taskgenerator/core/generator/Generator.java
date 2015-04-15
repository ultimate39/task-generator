package com.galt.java.taskgenerator.core.generator;

import com.galt.java.taskgenerator.core.model.Block;
import com.galt.java.taskgenerator.core.model.Chunk;
import com.galt.java.taskgenerator.core.model.Floor;
import com.galt.java.taskgenerator.core.model.Hall;
import com.galt.java.taskgenerator.ui.FloorScreen;

import java.awt.*;
import java.util.Random;

/**
 * Created by vladislav on 2/15/15.
 */
public class Generator {
    private float hallRate = 10;
    private float minSplittableArea = 10;
    private Random random;
    Graphics2D g;

    public Generator(Random random) {
        this.random = random;
    }

    public Floor generateFloor(int width, int height) {
        Floor floor = new Floor(width, height);
        toBlocks(floor);
        return floor;
    }

    private void toBlocks(Floor floor) {
        while (!floor.getChunks().isEmpty() && ((floor.getHallArea() / floor.getArea()) * 100 < hallRate)) {
            Chunk chunk = floor.getChunks().get(0);
            if (chunk.getArea() > minSplittableArea) {
                splitToBlocks(floor, chunk);
            } else {
                floor.getBlocks().add(new Block(chunk.x, chunk.y, chunk.x2, chunk.y2));
                floor.getChunks().remove(chunk);
            }
        }
    }

    private void splitToBlocks(Floor floor, Chunk chunk) {
        switch(random.nextInt(2)) {
            case 0: splitChunkHorizontally(floor, chunk); break;
            case 1: splitChunkVertically(floor, chunk); break;
        }
    }

    private void splitChunkHorizontally(Floor floor, Chunk chunk) {
            Chunk chunkA = new Chunk(chunk.x, chunk.y, chunk.x2, chunk.y + (chunk.y2 - chunk.y) / 2);
            Chunk chunkB = new Chunk(chunk.x, chunk.y + (chunk.y2 - chunk.y) / 2 + 2, chunk.x2, chunk.y2);
            Hall hall = new Hall(chunk.x, chunk.y + (chunk.y2 - chunk.y) / 2, chunk.x2, chunk.y + (chunk.y2 - chunk.y) / 2 + 2);
            floor.getChunks().remove(chunk);
            floor.getChunks().add(chunkA);
            floor.getChunks().add(chunkB);
            floor.getHalls().add(hall);
    }

    private void splitChunkVertically(Floor floor, Chunk chunk) {
            Chunk chunkA = new Chunk(chunk.x, chunk.y, chunk.x + (chunk.x2 - chunk.x) / 2, chunk.y2);
            Chunk chunkB = new Chunk(chunk.x + (chunk.x2 - chunk.x) / 2 + 2, chunk.y, chunk.x2, chunk.y2);
            Hall hall = new Hall(chunk.x + (chunk.x2 - chunk.x) / 2, chunk.y, chunk.x + (chunk.x2 - chunk.x) / 2 + 2, chunk.y2);
            floor.getChunks().remove(chunk);
            floor.getChunks().add(chunkA);
            floor.getChunks().add(chunkB);
            floor.getHalls().add(hall);
    }
}
