package com.example.cryptoapp.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.cryptoapp.model.room.AllMarketEntity;
import com.example.cryptoapp.room.converters.AllMarketConverters;

@TypeConverters({AllMarketConverters.class})
@Database(entities = {AllMarketEntity.class}, version = 1, exportSchema = false)
public abstract class CryptoRoomDataBase extends RoomDatabase {


    private static final String DB_NAME = "crypto";
    private static CryptoRoomDataBase instance;

    public static synchronized CryptoRoomDataBase getInstance(Context context) {
        if (instance == null) {
            instance = Room
                    .databaseBuilder(context.getApplicationContext()
                            , CryptoRoomDataBase.class, DB_NAME)
                    .fallbackToDestructiveMigration()//recreate database if anything changed
                    .build();
        }
        return instance;
    }

    public abstract RoomDao roomDao();
}
