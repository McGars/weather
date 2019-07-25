package com.test.weather.domain.data.weahter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Weather implements Serializable {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("description")
    @Expose
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
