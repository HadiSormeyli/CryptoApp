package com.example.cryptoapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.cryptoapp.R;
import com.example.cryptoapp.model.crypto.AllMarket;
import com.example.cryptoapp.model.room.AllMarketEntity;
import com.example.cryptoapp.retrofit.Repository;

import java.util.ArrayList;
import java.util.concurrent.Future;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;

@HiltViewModel
public class MainViewModel extends AndroidViewModel {

    @Inject
    public Repository repository;
    private MutableLiveData<ArrayList<Integer>> mutableLiveData = new MutableLiveData<>();

    @Inject
    public MainViewModel(@NonNull Application application) {
        super(application);
        getViewPagerData();
    }

    private void getViewPagerData() {
        ArrayList<Integer> pics = new ArrayList<>();
        pics.add(R.drawable.pic1);
        pics.add(R.drawable.pic2);
        pics.add(R.drawable.pic3);

        mutableLiveData.postValue(pics);
    }

    public MutableLiveData<ArrayList<Integer>> getMutableLiveData() {
        return mutableLiveData;
    }

    public Future<Observable<AllMarket>> getAllMarket() {
        return repository.getAllMarket();
    }

    //All Market Room Database
    public void insert(AllMarket allMarket) {
        repository.insert(allMarket);
    }

    public Flowable<AllMarketEntity> getAllMarketEntity() {
        return repository.getAllMarketEntity();
    }
}
