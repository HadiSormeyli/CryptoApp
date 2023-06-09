package com.example.cryptoapp.room.converters;

import androidx.room.TypeConverter;

import com.example.cryptoapp.model.crypto.AllMarket;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class AllMarketConverters {

    @TypeConverter
    public String toJson(AllMarket allMarket) {
        if (allMarket == null) return null;
        Gson gson = new Gson();
        Type type = new TypeToken<AllMarket>() {
        }.getType();
        return gson.toJson(allMarket, type);
    }

    @TypeConverter
    public AllMarket fromJson(String json) {
        if (json == null) return null;
        Gson gson = new Gson();
        Type type = new TypeToken<AllMarket>() {
        }.getType();
        return gson.fromJson(json, type);
    }
}
