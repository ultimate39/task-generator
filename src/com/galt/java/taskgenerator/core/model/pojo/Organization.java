
package com.galt.java.taskgenerator.core.model.pojo;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Organization {

    @Expose
    private String name;
    @SerializedName("subdivision")
    @Expose
    private List<Subdivision> subdivisions = new ArrayList<>();
    @SerializedName("resources")
    private List<String> resources = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Subdivision> getSubdivisions() {
        return subdivisions;
    }

    public List<String> getResources() {
        return resources;
    }

    public void setSubdivision(List<Subdivision> subdivision) {
        this.subdivisions = subdivision;
    }

    public void setSubdivisions(List<Subdivision> subdivisions) {
        this.subdivisions = subdivisions;
    }

    public void setResources(List<String> resources) {
        this.resources = resources;
    }

    @Override
    public String toString() {
        return "Organization{" +
                "name='" + name + '\'' +
                ", subdivisions=" + subdivisions +
                ", resources=" + resources +
                '}';
    }
}
