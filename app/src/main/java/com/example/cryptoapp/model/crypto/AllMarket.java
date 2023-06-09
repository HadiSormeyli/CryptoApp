package com.example.cryptoapp.model.crypto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllMarket {

    @SerializedName("data")
    @Expose
    private Data data;

    @SerializedName("status")
    @Expose
    private Status status;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
