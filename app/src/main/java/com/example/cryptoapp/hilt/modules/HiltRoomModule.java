package com.example.cryptoapp.hilt.modules;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.cryptoapp.room.CryptoRoomDataBase;
import com.example.cryptoapp.room.RoomDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class HiltRoomModule {

    @Provides
    @Singleton
    CryptoRoomDataBase provideCryptoRoomDataBase(@ApplicationContext Context context) {
        return CryptoRoomDataBase.getInstance(context);
    }

    @Provides
    @Singleton
    RoomDao provideRoomDao(@NonNull CryptoRoomDataBase cryptoRoomDataBase) {
        return cryptoRoomDataBase.roomDao();
    }
}
