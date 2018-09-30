package com.jayaa.mvvmrx.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class NewsModelResponse {

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("rows")

    @Expose
    public ArrayList<NewsModelItem> data;

    public ArrayList<NewsModelItem> getData() {
        return data;
    }

    public void setData(ArrayList<NewsModelItem> data) {
        this.data = data;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
