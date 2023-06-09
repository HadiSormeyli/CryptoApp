package com.example.cryptoapp.retrofit;

import android.util.Log;

import com.example.cryptoapp.model.crypto.AllMarket;
import com.example.cryptoapp.model.room.AllMarketEntity;
import com.example.cryptoapp.room.RoomDao;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Repository {

    private final String TAG = "Repository";
    private RequestApi requestApi;
    private RoomDao roomDao;

    public Repository(RequestApi requestApi, RoomDao roomDao) {
        this.requestApi = requestApi;
        this.roomDao = roomDao;
    }

    public Future<Observable<AllMarket>> getAllMarket() {
        final ExecutorService executorService = Executors.newSingleThreadExecutor();

        final Callable<Observable<AllMarket>> myNetWorkCallable = () -> requestApi.getAllMarket();

        final Future<Observable<AllMarket>> future = new Future<Observable<AllMarket>>() {
            @Override
            public boolean cancel(boolean mayInterruptIfRunning) {
                if (mayInterruptIfRunning) {
                    executorService.shutdown();
                }
                return false;
            }

            @Override
            public boolean isCancelled() {
                return executorService.isShutdown();
            }

            @Override
            public boolean isDone() {
                return executorService.isTerminated();
            }

            @Override
            public Observable<AllMarket> get() throws ExecutionException, InterruptedException {
                return executorService.submit(myNetWorkCallable).get();
            }

            @Override
            public Observable<AllMarket> get(long timeout, TimeUnit unit) throws ExecutionException, InterruptedException, TimeoutException {
                return executorService.submit(myNetWorkCallable).get(timeout, unit);
            }
        };

        return future;
    }

    public void insert(AllMarket allMarket) {
        Completable.fromAction(() -> roomDao.insert(new AllMarketEntity(allMarket)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.d(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: " + allMarket);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(TAG, "onError: " + e.getMessage());
                    }
                });
    }

    public Flowable<AllMarketEntity> getAllMarketEntity() {
        return roomDao.getAllMarketEntity();
    }
}
