package com.galt.java.taskgenerator.core.generator;

import com.galt.java.taskgenerator.core.model.floor.*;
import com.galt.java.taskgenerator.core.utils.Logger;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 * Created by Grishechko on 05.05.2015.
 */
public class FloorGenerator {
    private final float MIN_HALlRATE = 10;
    private final int MIN_BLOCKS = 4;
    private float initHallRate = 0;
    private float minSplittableArea = 10;
    private int MIN_DIMENSION_OF_BLOCK = 6;
    private int MIN_DIMENSION_OF_ROOM = 6;
    private int HALL_WIDTH = 2;
    private Random random;

    public FloorGenerator(Random random) {
        this.random = random;
    }

    public Floor generateFloor(int width, int height) {
        Floor floor = new Floor(width, height);
        toBlocks(floor);
        floor.sortBlocksByArea();
        toRooms(floor);
        initHallRate = MIN_HALlRATE;
        for (Room room : floor.getRooms()) {
            placeDoor(floor, room);
        }
        placeWallDoor(floor);
        Logger.d("Doors size:" + floor.getDoors().size());
        Collections.min(floor.getRooms(), new Comparator<Room>() {
            @Override
            public int compare(Room o1, Room o2) {
                if (o1.getArea() < o2.getArea()) {
                    return -1;
                } else if (o1.getArea() == o2.getArea()) {
                    return 0;
                } else {
                    return 1;
                }
            }
        }).setElevator(true);
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
        chunksToBlocks(floor);
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

    private void toRooms(Floor floor) {
        while ((floor.getBlocks().size() + floor.getRooms().size()) < 15) {
            Block block = floor.getBlocks().get(0);
            if (block.getArea() > minSplittableArea) {
                splitToRooms(floor, block);
            } else {
                floor.getRooms().add(new Room(block.x, block.y, block.x2, block.y2));
                floor.getBlocks().remove(block);
            }
        }
        blocksToRooms(floor);
    }

    private void chunksToBlocks(Floor floor) {
        List<Chunk> chunks = floor.getChunks();
        List<Block> blocks = floor.getBlocks();
        for (Chunk chunk : chunks) {
            blocks.add(new Block(chunk.x, chunk.y, chunk.x2, chunk.y2));
        }
        chunks.clear();
    }

    private void blocksToRooms(Floor floor) {
        List<Block> blocks = floor.getBlocks();
        List<Room> rooms = floor.getRooms();
        for (Block block : blocks) {
            rooms.add(new Room(block.x, block.y, block.x2, block.y2));
        }
        blocks.clear();
    }

    private void splitToBlocks(Floor floor, Chunk chunk) {
        System.out.println("-------------------");
        System.out.println("Chunk");
        System.out.println("Width:" + chunk.getWidth());
        System.out.println("Height:" + chunk.getHeight());
        float height;
        switch (random.nextInt(2)) {
            case 0:
                height = (float) chunk.getHeight();
                if ((chunk.getHeight() >= MIN_DIMENSION_OF_BLOCK * 2 + HALL_WIDTH) && chunk.getWidth() / height < 2) {
                    splitChunkHorizontally(floor, chunk);
                } else if (chunk.getWidth() > MIN_DIMENSION_OF_BLOCK * 2 + HALL_WIDTH) {
                    splitChunkVertically(floor, chunk);
                } else {
                    floor.getBlocks().add(new Block(chunk.x, chunk.y, chunk.x2, chunk.y2));
                    floor.getChunks().remove(chunk);
                }
                break;
            case 1:
                height = (float) chunk.getHeight();
                if (chunk.getWidth() > MIN_DIMENSION_OF_BLOCK * 2 + HALL_WIDTH && Math.round(height / chunk.getWidth()) < 2) {
                    splitChunkVertically(floor, chunk);
                } else if (chunk.getHeight() > MIN_DIMENSION_OF_BLOCK * 2 + HALL_WIDTH) {
                    splitChunkHorizontally(floor, chunk);
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
        int deltaPositionOfHall;

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

    private void splitToRooms(Floor floor, Block block) {
        switch (random.nextInt(2)) {
            case 0:
                if (block.getHeight() >= MIN_DIMENSION_OF_ROOM * 2 && block.getWidth() / block.getHeight() < 2) {
                    splitBlockHorizontally(floor, block);
                } else if (block.getWidth() > MIN_DIMENSION_OF_ROOM * 2) {
                    splitBlockVertically(floor, block);
                } else {
                    floor.getRooms().add(new Room(block.x, block.y, block.x2, block.y2));
                    floor.getBlocks().remove(block);
                }
                break;
            case 1:
                float height = (float) block.getHeight();
                if (block.getWidth() > MIN_DIMENSION_OF_ROOM * 2 && height / block.getWidth() < 2) {
                    splitBlockVertically(floor, block);
                } else if (block.getWidth() > MIN_DIMENSION_OF_ROOM * 2) {
                    splitBlockVertically(floor, block);
                } else {
                    floor.getRooms().add(new Room(block.x, block.y, block.x2, block.y2));
                    floor.getBlocks().remove(block);
                }
                break;
        }
    }

    private void splitBlockHorizontally(Floor floor, Block chunk) {
        int range = (chunk.getHeight() - MIN_DIMENSION_OF_ROOM * 2);
        int deltaPositionOfHall;
        if (range == 0) {
            deltaPositionOfHall = MIN_DIMENSION_OF_ROOM;
        } else {
            deltaPositionOfHall = random.nextInt(range) + MIN_DIMENSION_OF_ROOM;
        }
        /*Logger.d("---------------");
        Logger.d("Area" + chunk.getArea());
        Logger.d("Delta position:" + deltaPositionOfHall);
        Logger.d("Range:" + range);*/
        Block chunkA = new Block(chunk.x, chunk.y, chunk.x2, chunk.y + deltaPositionOfHall);
        Block chunkB = new Block(chunk.x, chunk.y + deltaPositionOfHall, chunk.x2, chunk.y2);
        floor.getBlocks().add(chunkA);
        floor.getBlocks().add(chunkB);
        floor.getBlocks().remove(chunk);
    }

    private void splitBlockVertically(Floor floor, Block chunk) {
        int range = (chunk.getWidth() - MIN_DIMENSION_OF_ROOM * 2);
        int deltaPositionOfHall;
        if (range == 0) {
            deltaPositionOfHall = MIN_DIMENSION_OF_ROOM;
        } else {
            deltaPositionOfHall = random.nextInt(range) + MIN_DIMENSION_OF_ROOM;
        }/*
        Logger.d("---------------");
        Logger.d("Area" + chunk.getArea());
        Logger.d("Delta position:" + deltaPositionOfHall);
        Logger.d("Range:" + range);*/
        Block chunkA = new Block(chunk.x, chunk.y, chunk.x + deltaPositionOfHall, chunk.y2);
        Block chunkB = new Block(chunk.x + deltaPositionOfHall, chunk.y, chunk.x2, chunk.y2);
        floor.getBlocks().add(chunkA);
        floor.getBlocks().add(chunkB);
        floor.getBlocks().remove(chunk);
    }

    private void placeDoor(Floor floor, Room b) {
        Point leftWall = new Point(b.x - 1, b.y + 1);
        Point rightWall = new Point(b.x2 + 1, b.y + 1);
        Point bottomWall = new Point(b.x + 1, b.y2 + 1);
        Point topWall = new Point(b.x + 1, b.y - 1);
        List<Hall> halls = floor.getHalls();
        List<Door> doors = floor.getDoors();
        int beforeDoors = doors.size();
        for (Hall hall : halls) {
            if (hall.contains(leftWall)) {
                doors.add(Door.leftDoor(b));
                Logger.d("left wall");
                break;
            } else if (hall.contains(rightWall)) {
                doors.add(Door.rightDoor(b));
                Logger.d("right wall");
                break;
            } else if (hall.contains(topWall)) {
                doors.add(Door.topDoor(b));
                Logger.d("top wall");
                break;
            } else if (hall.contains(bottomWall)) {
                doors.add(Door.bottomDoor(b));
                Logger.d("bottom wall");
                break;
            }
        }
        if (beforeDoors == doors.size()) {
            placeRandomDoor(floor, b);
        }
        b.setDoor(doors.get(doors.size() - 1));
    }

    private void placeRandomDoor(Floor floor, Room r) {
        //Left door
        if (r.x - 1 > 0) {
            floor.getDoors().add(Door.leftDoor(r));
        } else if (r.y - 1 > 0) {
            floor.getDoors().add(Door.topDoor(r));
        } else if (r.y2 + 1 < floor.y2) {
            floor.getDoors().add(Door.bottomDoor(r));
        } else if (r.x2 + 1 < floor.x2) {
            floor.getDoors().add(Door.rightDoor(r));
        }
    }

    //Поставить дверь к стене, которая не позволяет проходить к другой части здания
    private void placeWallDoor(Floor floor) {
        for (int i = 0; i < floor.getRooms().size() - 1; i++) {
            Room roomOne = floor.getRooms().get(i);
            Room roomTwo = floor.getRooms().get(i + 1);
            if ((roomOne.getWidth() + roomTwo.getWidth()) == floor.getWidth()) {
                if (roomOne.getDoor().getDoorType() != Door.DOOR_TOP && roomTwo.getDoor().getDoorType() != Door.DOOR_TOP) {
                    floor.getDoors().add(Door.topDoor(roomOne));
                } else if (roomOne.getDoor().getDoorType() != Door.DOOR_BOTTOM && roomTwo.getDoor().getDoorType() != Door.DOOR_BOTTOM) {
                    floor.getDoors().add(Door.bottomDoor(roomTwo));
                }
            }
            if ((roomOne.getHeight() + roomTwo.getHeight()) == floor.getHeight()) {
                Logger.d("R" + i + " and R" + (i + 1));
                if (roomOne.getDoor().getDoorType() != Door.DOOR_LEFT && roomTwo.getDoor().getDoorType() != Door.DOOR_LEFT) {
                    floor.getDoors().add(Door.leftDoor(roomOne));
                } else if (roomOne.getDoor().getDoorType() != Door.DOOR_RIGHT && roomTwo.getDoor().getDoorType() != Door.DOOR_RIGHT) {
                    floor.getDoors().add(Door.rightDoor(roomTwo));
                }
            }
            if (roomOne.getWidth() == floor.getWidth()) {
                if (roomTwo.getDoor().getDoorType() != Door.DOOR_TOP) {
                    floor.getDoors().add(Door.topDoor(roomTwo));
                }
                if (roomTwo.getDoor().getDoorType() != Door.DOOR_BOTTOM) {
                    floor.getDoors().add(Door.bottomDoor(roomTwo));
                }
            }
            if (roomTwo.getHeight() == floor.getHeight()) {
                if (roomOne.getDoor().getDoorType() != Door.DOOR_LEFT) {
                    floor.getDoors().add(Door.leftDoor(roomOne));
                }
                if (roomTwo.getDoor().getDoorType() != Door.DOOR_RIGHT) {
                    floor.getDoors().add(Door.rightDoor(roomTwo));
                }
            }
        }
    }
}
