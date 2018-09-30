package com.jayaa.mvvmrx.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewsModelItem {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("imageHref")
    @Expose
    private String imageHref;

    public NewsModelItem(String title, String description, String imageHref) {
        this.title = title;
        this.description = description;
        this.imageHref = imageHref;
    }


    public NewsModelItem() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageHref() {
        return imageHref;
    }

    public void setImageHref(String imageHref) {
        this.imageHref = imageHref;
    }


}
