
package com.galt.java.taskgenerator.core.model.pojo;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;

public class Subdivision {

    @Expose
    private String name;
    @Expose
    private List<Device> device = new ArrayList<Device>();
    @Expose
    private String places;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Device> getDevices() {
        return device;
    }

    public void setDevices(List<Device> device) {
        this.device = device;
    }

    public String getPlaces() {
        return places;
    }

    public void setPlaces(String places) {
        this.places = places;
    }

    @Override
    public String toString() {
        return "Subdivision{" +
                "name='" + name + '\'' +
                ", device=" + device +
                ", places='" + places + '\'' +
                '}';
    }
}
