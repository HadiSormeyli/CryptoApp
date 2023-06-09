package com.example.cryptoapp.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.cryptoapp.model.room.AllMarketEntity;

import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface RoomDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(AllMarketEntity allMarketEntity);

    @Delete
    void delete(AllMarketEntity allMarketEntity);

    @Query("SELECT * FROM ALL_MARKET")
    Flowable<AllMarketEntity> getAllMarketEntity();
}
