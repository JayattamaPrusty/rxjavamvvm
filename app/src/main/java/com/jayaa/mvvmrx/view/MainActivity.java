package com.jayaa.mvvmrx.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jayaa.mvvmrx.R;
import com.jayaa.mvvmrx.databinding.ActivityMainBinding;
import com.jayaa.mvvmrx.model.NewsModelResponse;
import com.jayaa.mvvmrx.util.Logger;
import com.jayaa.mvvmrx.viewModel.NewsModelViewModel;

import io.reactivex.observers.DisposableObserver;

public class MainActivity extends AppCompatActivity{

    private ActivityMainBinding mMainActivityBinding;
    private NewsModelViewModel mNewsModelViewModel;
    private DisposableObserver mGetAndroidVersion;
    private RecyclerView mVersionList;
    private VersionAdapter mVersionAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDataBinding();
        setUpView();
    }

    private void initDataBinding() {
        mMainActivityBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mNewsModelViewModel = new NewsModelViewModel(this);
        mMainActivityBinding.setNewsModelViewModel(mNewsModelViewModel);
    }

    private void setUpView() {
        setSupportActionBar(mMainActivityBinding.toolbar);

        mVersionList = mMainActivityBinding.rlVersionlist;
        mVersionList.setLayoutManager(new LinearLayoutManager(this));
        mVersionAdapter = new VersionAdapter();
        mVersionAdapter.showList(mNewsModelViewModel.getDataList());
        mVersionList.setAdapter(mVersionAdapter);
    }

    private void getAndroidVersion() {
        mGetAndroidVersion = new DisposableObserver<NewsModelResponse>() {
            @Override
            public void onNext(NewsModelResponse data) {
                if (data != null && data.getData().size() > 0) {
                    mNewsModelViewModel.updateVersionDataList(data.getData());
                    //mMainActivityBinding.setAndroidVersionViewModel(mNewsModelViewModel);
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
                Logger.d("Android complete","done");
            }
        };

        mNewsModelViewModel.getAndroidVersionList(mGetAndroidVersion);
    }

    private void updateList() {
        mVersionAdapter.showList(mNewsModelViewModel.getDataList());
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAndroidVersion();
    }
}
