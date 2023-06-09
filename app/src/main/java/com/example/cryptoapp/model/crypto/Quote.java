package com.example.cryptoapp.model.crypto;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Quote {

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("price")
    @Expose
    private Double price;

    @SerializedName("volume24h")
    @Expose
    private Double volume24h;

    @SerializedName("volume7d")
    @Expose
    private Double volume7d;

    @SerializedName("volume30d")
    @Expose
    private Double volume30d;

    @SerializedName("marketCap")
    @Expose
    private Double marketCap;

    @SerializedName("percentChange1h")
    @Expose
    private Double percentChange1h;

    @SerializedName("percentChange24h")
    @Expose
    private Double percentChange24h;

    @SerializedName("percentChange7d")
    @Expose
    private Double percentChange7d;

    @SerializedName("lastUpdated")
    @Expose
    private String lastUpdated;

    @SerializedName("percentChange30d")
    @Expose
    private Double percentChange30d;

    @SerializedName("percentChange60d")
    @Expose
    private Double percentChange60d;

    @SerializedName("percentChange90d")
    @Expose
    private Double percentChange90d;

    @SerializedName("fullyDilluttedMarketCap")
    @Expose
    private Double fullyDilluttedMarketCap;

    @SerializedName("marketCapByTotalSupply")
    @Expose
    private Double marketCapByTotalSupply;

    @SerializedName("dominance")
    @Expose
    private Double dominance;

    @SerializedName("turnover")
    @Expose
    private Double turnover;

    @SerializedName("ytdPriceChangePercentage")
    @Expose
    private Double ytdPriceChangePercentage;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getVolume24h() {
        return volume24h;
    }

    public void setVolume24h(Double volume24h) {
        this.volume24h = volume24h;
    }

    public Double getVolume7d() {
        return volume7d;
    }

    public void setVolume7d(Double volume7d) {
        this.volume7d = volume7d;
    }

    public Double getVolume30d() {
        return volume30d;
    }

    public void setVolume30d(Double volume30d) {
        this.volume30d = volume30d;
    }

    public Double getMarketCap() {
        return marketCap;
    }

    public void setMarketCap(Double marketCap) {
        this.marketCap = marketCap;
    }

    public Double getPercentChange1h() {
        return percentChange1h;
    }

    public void setPercentChange1h(Double percentChange1h) {
        this.percentChange1h = percentChange1h;
    }

    public Double getPercentChange24h() {
        return percentChange24h;
    }

    public void setPercentChange24h(Double percentChange24h) {
        this.percentChange24h = percentChange24h;
    }

    public Double getPercentChange7d() {
        return percentChange7d;
    }

    public void setPercentChange7d(Double percentChange7d) {
        this.percentChange7d = percentChange7d;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Double getPercentChange30d() {
        return percentChange30d;
    }

    public void setPercentChange30d(Double percentChange30d) {
        this.percentChange30d = percentChange30d;
    }

    public Double getPercentChange60d() {
        return percentChange60d;
    }

    public void setPercentChange60d(Double percentChange60d) {
        this.percentChange60d = percentChange60d;
    }

    public Double getPercentChange90d() {
        return percentChange90d;
    }

    public void setPercentChange90d(Double percentChange90d) {
        this.percentChange90d = percentChange90d;
    }

    public Double getFullyDilluttedMarketCap() {
        return fullyDilluttedMarketCap;
    }

    public void setFullyDilluttedMarketCap(Double fullyDilluttedMarketCap) {
        this.fullyDilluttedMarketCap = fullyDilluttedMarketCap;
    }

    public Double getMarketCapByTotalSupply() {
        return marketCapByTotalSupply;
    }

    public void setMarketCapByTotalSupply(Double marketCapByTotalSupply) {
        this.marketCapByTotalSupply = marketCapByTotalSupply;
    }

    public Double getDominance() {
        return dominance;
    }

    public void setDominance(Double dominance) {
        this.dominance = dominance;
    }

    public Double getTurnover() {
        return turnover;
    }

    public void setTurnover(Double turnover) {
        this.turnover = turnover;
    }

    public Double getYtdPriceChangePercentage() {
        return ytdPriceChangePercentage;
    }

    public void setYtdPriceChangePercentage(Double ytdPriceChangePercentage) {
        this.ytdPriceChangePercentage = ytdPriceChangePercentage;
    }

}
