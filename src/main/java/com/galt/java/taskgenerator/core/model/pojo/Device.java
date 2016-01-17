
package com.galt.java.taskgenerator.core.model.pojo;

import com.google.gson.annotations.Expose;


public class Device {
    @Expose
    private String name;
    @Expose
    private String count;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "Device{" +
                "count='" + count + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
