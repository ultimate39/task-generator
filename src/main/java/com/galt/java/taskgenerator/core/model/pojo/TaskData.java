
package com.galt.java.taskgenerator.core.model.pojo;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TaskData {
    @Expose
    private List<Organization> organization = new ArrayList<>();
    @Expose
    private List<String> equipment = new ArrayList<>();
    @SerializedName("external_lan")
    @Expose
    private List<String> externalLan = new ArrayList<>();
    @Expose
    private String floors;
    @Expose
    private String buildings;
    @SerializedName("size_width")
    String sizeWidth;
    @SerializedName("size_height_factor")
    String sizeHeightFactor;

    public List<Organization> getOrganizations() {
        return organization;
    }

    public void setOrganizations(List<Organization> organization) {
        this.organization = organization;
    }

    public List<String> getEquipments() {
        return equipment;
    }

    public void setEquipments(List<String> equipment) {
        this.equipment = equipment;
    }

    public List<String> getExternalLans() {
        return externalLan;
    }

    public void setExternalLans(List<String> externalLan) {
        this.externalLan = externalLan;
    }

    public String getFloors() {
        return floors;
    }

    public void setFloors(String floors) {
        this.floors = floors;
    }

    public String getBuildings() {
        return buildings;
    }

    public void setBuildings(String buildings) {
        this.buildings = buildings;
    }

    public String getSizeWidth() {
        return sizeWidth;
    }

    public void setSizeWidth(String sizeWidth) {
        this.sizeWidth = sizeWidth;
    }

    public String getSizeHeightFactor() {
        return sizeHeightFactor;
    }

    public void setSizeHeightFactor(String sizeHeightFactor) {
        this.sizeHeightFactor = sizeHeightFactor;
    }

    @Override
    public String toString() {
        return "TaskData{" +
                "organization=" + organization +
                ", equipment=" + equipment +
                ", externalLan=" + externalLan +
                ", floors='" + floors + '\'' +
                ", buildings='" + buildings + '\'' +
                '}';
    }
}
