package com.galt.java.taskgenerator.core.generator;

import com.galt.java.taskgenerator.core.model.floor.Floor;
import com.galt.java.taskgenerator.core.model.task.TaskConditions;

import java.util.Random;

/**
 * Created by vladislav on 2/15/15.
 */
public class Generator {
    FloorGenerator floorGenerator;
    TaskConditionsGenerator taskConditionsGenerator;

    public Generator(Random random) {
        floorGenerator = new FloorGenerator(random);
        taskConditionsGenerator = new TaskConditionsGenerator(random);
    }

    public Floor generateFloor(int width, int height) {
        return floorGenerator.generateFloor(width, height);
    }

    public TaskConditions generateTaskConditions() {
        return taskConditionsGenerator.generateTaskConditions();
    }
}
