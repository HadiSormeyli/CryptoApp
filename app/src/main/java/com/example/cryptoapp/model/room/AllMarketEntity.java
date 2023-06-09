package com.example.cryptoapp.model.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.cryptoapp.model.crypto.AllMarket;

@Entity(tableName = "all_market")
public class AllMarketEntity {

    @PrimaryKey
    private int uid;

    @ColumnInfo(name = "all_market")
    private AllMarket allMarket;

    public AllMarketEntity(AllMarket allMarket) {
        this.allMarket = allMarket;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public AllMarket getAllMarket() {
        return allMarket;
    }

    public void setAllMarket(AllMarket allMarket) {
        this.allMarket = allMarket;
    }
}
