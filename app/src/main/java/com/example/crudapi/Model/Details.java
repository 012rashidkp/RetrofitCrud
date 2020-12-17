package com.example.crudapi.Model;

import com.google.gson.annotations.SerializedName;

public class Details {
    @SerializedName("id")
    private int id;
    @SerializedName("title")
    private String title;
    @SerializedName("body")
    private String body;
    @SerializedName("completed")
    private Boolean completed;

    public Details(int id, String title, String body, Boolean completed) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.completed = completed;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public Boolean getCompleted() {
        return completed;
    }
}
