package com.jayaa.mvvmrx.api;

import com.jayaa.mvvmrx.model.NewsModelResponse;


import io.reactivex.Observable;
import retrofit2.http.GET;

public interface AndroidUserAPI {

    @GET("s/2iodh4vg0eortkl/facts.json")
    Observable<NewsModelResponse> getAndroidVersion();
}


//https://dl.dropboxusercontent.com/s/2iodh4vg0eortkl/facts.json