package com.example.cryptoapp.model.crypto;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data{

    @SerializedName("cryptoCurrencyList")
    @Expose
    private List<CryptoCurrency> cryptoCurrencyList = null;

    @SerializedName("totalCount")
    @Expose
    private String totalCount;

    public List<CryptoCurrency> getCryptoCurrencyList() {
        return cryptoCurrencyList;
    }

    public void setCryptoCurrencyList(List<CryptoCurrency> cryptoCurrencyList) {
        this.cryptoCurrencyList = cryptoCurrencyList;
    }

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }

}
