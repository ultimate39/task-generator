package com.galt.java.taskgenerator.core.model.task;

import com.galt.java.taskgenerator.core.model.pojo.*;
import com.galt.java.taskgenerator.core.model.pojo.Organization;

/**
 * Created by Grishechko on 05.05.2015.
 */
public class TaskConditions extends TaskData {
    private Organization organization;
    private String equipment;
    private String externalLan;
    private int width;
    private int height;

    public com.galt.java.taskgenerator.core.model.pojo.Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public String getExternalLan() {
        return externalLan;
    }

    public void setExternalLan(String externalLan) {
        this.externalLan = externalLan;
    }

    public int getPlaces() {
        int places = 0;
        for(Subdivision subdivision : organization.getSubdivisions()) {
            places += Integer.valueOf(subdivision.getPlaces());
        }
        return places;
    }

    public int getDevices(){
        int devices = 0;
        for(Subdivision subdivision : organization.getSubdivisions()) {
            for(Device device : subdivision.getDevices()) {
                devices += Integer.valueOf(device.getCount());
            }
        }
        return devices;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "TaskConditions{" +
                "organization=" + organization +
                ", equipment='" + equipment + '\'' +
                ", externalLan='" + externalLan + '\'' +
                '}';
    }
}
