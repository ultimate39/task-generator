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

    @Override
    public String toString() {
        return "TaskConditions{" +
                "organization=" + organization +
                ", equipment='" + equipment + '\'' +
                ", externalLan='" + externalLan + '\'' +
                '}';
    }
}
