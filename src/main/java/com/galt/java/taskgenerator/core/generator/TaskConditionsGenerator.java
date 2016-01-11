package com.galt.java.taskgenerator.core.generator;

import com.galt.java.taskgenerator.core.model.pojo.Device;
import com.galt.java.taskgenerator.core.model.pojo.Organization;
import com.galt.java.taskgenerator.core.model.pojo.Subdivision;
import com.galt.java.taskgenerator.core.model.pojo.TaskData;
import com.galt.java.taskgenerator.core.model.task.TaskConditions;
import com.galt.java.taskgenerator.core.utils.Logger;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Grishechko on 05.05.2015.
 */
public class TaskConditionsGenerator {
    private Random random;
    private TaskData taskData;
    private String data;

    public TaskConditionsGenerator(Random random) {
        this.random = random;
    }

    public TaskConditions generateTaskConditions(String data) throws Exception {
        TaskConditions taskConditions = new TaskConditions();
        taskData = getTaskData(data);

        int positionOfOrganization = random.nextInt(taskData.getOrganizations().size());
        Organization organizationData = taskData.getOrganizations().get(positionOfOrganization);
        taskConditions.setOrganization(generateOrganization(organizationData));

        taskConditions.setBuildings(getIntRandomInRange(taskData.getBuildings()));
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
        //Sizes for floor
        int width =  Integer.valueOf(getIntRandomInRange(taskData.getSizeWidth()));
        int height = Math.round(width * getFloatRandomInRange(taskData.getSizeHeightFactor()));
        taskConditions.setWidth(width);
        taskConditions.setHeight(height);
        return taskConditions;
    }

    public TaskData getTaskData(String data) throws Exception {
        taskData = new Gson().fromJson(data, TaskData.class);
        if(taskData == null) {
                throw new NullPointerException("Invalid JSON!");
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
                device.setCount(getIntRandomInRange(deviceData.getCount()));
                devices.add(device);
            }
            subdivision.setPlaces(getIntRandomInRange(subdivisionData.getPlaces()));
            subdivision.setDevices(devices);
            subdivision.setName(subdivisionData.getName());
            subdivisions.add(subdivision);
        }
        organization.setSubdivision(subdivisions);
        organization.setResources(organizationData.getResources());
        return organization;
    }

    /**
     *
     * @param range in format "number_from..number_to" or "number"
     */
    private String getIntRandomInRange(String range) {
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

    private float getFloatRandomInRange(String range) {
        Logger.d("Float Range:" + range);
        String[] ranges = range.split("\\.\\.");
        float from = Float.valueOf(ranges[0]);
        float to = ranges.length > 1 ? Float.valueOf(ranges[1]) : -1;
        if(to != -1) {
            return from + (to - from) * random.nextFloat();
        } else {
            return from;
        }
    }
}
