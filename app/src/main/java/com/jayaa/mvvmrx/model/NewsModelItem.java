package com.jayaa.mvvmrx.model;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.jayaa.mvvmrx.R;

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

    @BindingAdapter({"app:imageUrl"})
    public static void loadImage(ImageView view, String url) {

        Log.i("III", "Image - " + url);
        Context context = view.getContext();

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.nph);
        requestOptions.error(R.drawable.nph);
        //requestOptions.override(200,200);
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        requestOptions.fitCenter();


        Glide.with(context).setDefaultRequestOptions(requestOptions).load(url).into(view);
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
