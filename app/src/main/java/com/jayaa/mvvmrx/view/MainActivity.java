package com.jayaa.mvvmrx.view;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.jayaa.mvvmrx.MyApplication;
import com.jayaa.mvvmrx.R;
import com.jayaa.mvvmrx.databaseHelper.MyLocalDb;
import com.jayaa.mvvmrx.databinding.ActivityMainBinding;
import com.jayaa.mvvmrx.model.NewsModelItem;
import com.jayaa.mvvmrx.model.NewsModelResponse;
import com.jayaa.mvvmrx.util.ConnectivityReceiver;
import com.jayaa.mvvmrx.util.Logger;
import com.jayaa.mvvmrx.viewModel.NewsModelViewModel;

import java.util.ArrayList;

import io.reactivex.observers.DisposableObserver;

public class MainActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener,SwipeRefreshLayout.OnRefreshListener {

    private ActivityMainBinding mMainActivityBinding;
    private NewsModelViewModel mNewsModelViewModel;
    private DisposableObserver mNewsobserver;
    private RecyclerView mVersionList;
    private MainAdapter mMainAdapter;
    private SwipeRefreshLayout refreshLayout;

    private MyLocalDb localDb;


    public void onRetry(View v) {

        if (ConnectivityReceiver.isConnected()) {

            getAdapterdata();

        } else {

            Toast.makeText(this, "kindly connect internet to get data", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        localDb = new MyLocalDb(this);
        initDataBinding();
        setUpView();


    }

    private void initDataBinding() {
        mMainActivityBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mNewsModelViewModel = new NewsModelViewModel(this);
        mMainActivityBinding.setNewsModelViewModel(mNewsModelViewModel);
    }

    @SuppressLint("ResourceAsColor")
    private void setUpView() {
        setSupportActionBar(mMainActivityBinding.toolbar);

        mVersionList = mMainActivityBinding.rlVersionlist;
        refreshLayout=mMainActivityBinding.srl;
        refreshLayout.setOnRefreshListener(this);


        refreshLayout.setColorSchemeColors(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        mVersionList.setLayoutManager(new LinearLayoutManager(this));
        mMainAdapter = new MainAdapter();
        mMainAdapter.showList(mNewsModelViewModel.getDataList());
        mVersionList.setAdapter(mMainAdapter);
    }

    private void getAdapterdata() {

        if (ConnectivityReceiver.isConnected()) {


            mNewsobserver = new DisposableObserver<NewsModelResponse>() {
                @Override
                public void onNext(NewsModelResponse data) {
                    if (data != null && data.getData().size() > 0) {

                        ArrayList<NewsModelItem> rowArrayList = new ArrayList<>();

                        for (NewsModelItem row : data.getData()) {


                            if (row.getTitle() != null) {

                                rowArrayList.add(row);
                                if (!localDb.isRowExist(row.getTitle(), row.getDescription(), row.getImageHref())) {
                                    localDb.insertNewsRecords(row.getTitle(), row.getTitle(), row.getDescription(), row.getImageHref());
                                }

                            }

                        }

                        mNewsModelViewModel.updateNewsListInViewmodel(rowArrayList);
                        mMainActivityBinding.setNewsModelViewModel(mNewsModelViewModel);
                        updateList();
                    }
                }

                @Override
                public void onError(Throwable e) {
                    Logger.d("Android error", e.getMessage());
                }

                @Override
                public void onComplete() {
                    Logger.d("Android complete", "done");
                }
            };

            mNewsModelViewModel.getNewsListByAddObserver(mNewsobserver);

        } else {


            ArrayList<NewsModelItem> newslist = localDb.getNewsList(this);
            mNewsModelViewModel.updateNewsListInViewmodel(newslist);
            mMainActivityBinding.setNewsModelViewModel(mNewsModelViewModel);
            updateList();
        }
    }

    private void updateList() {
        mMainAdapter.showList(mNewsModelViewModel.getDataList());
    }

    @Override
    protected void onResume() {
        super.onResume();
        MyApplication.getInstance().setConnectivityListener(this);
        getAdapterdata();
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {

         if(isConnected){


             Toast.makeText(this, "Internet Connected", Toast.LENGTH_SHORT).show();


         }else {

             Toast.makeText(this, "" + "Internet disconnected.", Toast.LENGTH_SHORT).show();

         }

    }

    @Override
    public void onRefresh() {
        getAdapterdata();

        if (refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }
    }
}
