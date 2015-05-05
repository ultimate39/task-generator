
package com.galt.java.taskgenerator.core.model.pojo;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class TaskData {
    @Expose
    private List<Organization> organization = new ArrayList<Organization>();
    @Expose
    private List<String> equipment = new ArrayList<String>();
    @SerializedName("external_lan")
    @Expose
    private List<String> externalLan = new ArrayList<String>();
    @Expose
    private String floors;
    @Expose
    private String buildings;

    public List<Organization> getOrganizations() {
        return organization;
    }

    public void setOrganization(List<Organization> organization) {
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
