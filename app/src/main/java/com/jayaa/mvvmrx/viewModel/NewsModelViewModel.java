package com.jayaa.mvvmrx.viewModel;

import android.content.Context;

import com.jayaa.mvvmrx.api.AndroidUserAPI;
import com.jayaa.mvvmrx.model.NewsModelItem;
import com.jayaa.mvvmrx.util.RetrofitAPI;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class NewsModelViewModel extends Observable {

    private NewsModelItem mVersion;
    private Context mContext;
    private List<NewsModelItem> dataList;



    public NewsModelViewModel(Context mContext) {
        this.mContext = mContext;
        this.dataList = new ArrayList<NewsModelItem>();
    }

    public void getAndroidVersionList(Observer mObserver) {

        new RetrofitAPI().getRetrofit().create(AndroidUserAPI.class)
                .getAndroidVersion()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(mObserver);

    }

    public void updateVersionDataList(List<NewsModelItem> list) {
        dataList = list;
    }

    public List<NewsModelItem> getDataList() {
        return dataList;
    }


    public boolean hasData(){
         if(getDataList()!=null && getDataList().size()>0)
             return true;
         return false;
    }
}
