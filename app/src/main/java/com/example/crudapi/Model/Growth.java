package com.example.crudapi.Model;

import com.google.gson.annotations.SerializedName;

public class Growth {
    @SerializedName("year")
    private String year;
    @SerializedName("growthrate")
    private String growthrate;

    public Growth(String year, String growthrate) {
        this.year = year;
        this.growthrate = growthrate;
    }

    public String getYear() {
        return year;
    }

    public String getGrowthrate() {
        return growthrate;
    }
}
