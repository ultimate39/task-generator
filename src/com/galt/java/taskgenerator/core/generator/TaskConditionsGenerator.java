package com.galt.java.taskgenerator.core.generator;

import com.galt.java.taskgenerator.core.model.pojo.Device;
import com.galt.java.taskgenerator.core.model.pojo.Organization;
import com.galt.java.taskgenerator.core.model.pojo.Subdivision;
import com.galt.java.taskgenerator.core.model.pojo.TaskData;
import com.galt.java.taskgenerator.core.model.task.TaskConditions;
import com.galt.java.taskgenerator.core.uitls.Logger;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Grishechko on 05.05.2015.
 */
public class TaskConditionsGenerator {
    private final String DATA_PATH = "assets/data.json";
    private Random random;
    private TaskData taskData;

    public TaskConditionsGenerator(Random random) {
        this.random = random;
    }

    public TaskConditions generateTaskConditions() {
        TaskConditions taskConditions = new TaskConditions();

        taskData = getTaskData();

        int positionOfOrganization = random.nextInt(taskData.getOrganizations().size());
        Organization organizationData = taskData.getOrganizations().get(positionOfOrganization);
        taskConditions.setOrganization(generateOrganization(organizationData));

        taskConditions.setBuildings(getRandomInRange(taskData.getBuildings()));
        if (taskConditions.getBuildings().equals("1")) {
            taskConditions.setFloors("2");
        } else {
            taskConditions.setFloors("1");
        }

        int positionOfEquipment = random.nextInt(taskData.getEquipments().size());
        taskConditions.setEquipment(taskData.getEquipments().get(positionOfEquipment));

        int positionOfExternalLan = random.nextInt(taskData.getExternalLans().size());
        taskConditions.setExternalLan(taskData.getExternalLans().get(positionOfExternalLan));
        Logger.d(taskData.toString());
        return taskConditions;
    }

    public TaskData getTaskData() {
        File file = new File(DATA_PATH);
        TaskData taskData = null;
        try {
            taskData = new Gson().fromJson(new FileReader(file), TaskData.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return taskData;
    }

    private Organization generateOrganization(Organization organizationData) {
        Organization organization = new Organization();
        organization.setName(organizationData.getName());
        List<Subdivision> subdivisions = new ArrayList<>();
        for(Subdivision subdivisionData : organizationData.getSubdivisions()) {
            Subdivision subdivision = new Subdivision();
            List<Device> devices = new ArrayList<>();
            for(Device deviceData : subdivisionData.getDevices()) {
                Device device = new Device();
                device.setName(deviceData.getName());
                device.setCount(getRandomInRange(deviceData.getCount()));
                devices.add(device);
            }
            subdivision.setPlaces(getRandomInRange(subdivisionData.getPlaces()));
            subdivision.setDevices(devices);
            subdivision.setName(subdivisionData.getName());
            subdivisions.add(subdivision);
        }
        organization.setSubdivision(subdivisions);
        return organization;
    }

    /**
     *
     * @param range in format "number_from..number_to" or "number"
     */
    private String getRandomInRange(String range) {
        Logger.d("Range:" + range);
        String[] ranges = range.split("\\.\\.");
        int from = Integer.valueOf(ranges[0]);
        int to = ranges.length > 1 ? Integer.valueOf(ranges[1]) : -1;
        if(to != -1) {
            return String.valueOf(random.nextInt(to - from + 1) + from);
        } else {
            return String.valueOf(from);
        }
    }
}
