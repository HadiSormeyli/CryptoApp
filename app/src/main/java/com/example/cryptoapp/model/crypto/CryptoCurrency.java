package com.example.cryptoapp.model.crypto;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CryptoCurrency implements Parcelable {

    public static final Creator<CryptoCurrency> CREATOR = new Creator<CryptoCurrency>() {
        @Override
        public CryptoCurrency createFromParcel(Parcel in) {
            return new CryptoCurrency(in);
        }

        @Override
        public CryptoCurrency[] newArray(int size) {
            return new CryptoCurrency[size];
        }
    };
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("symbol")
    @Expose
    private String symbol;
    @SerializedName("slug")
    @Expose
    private String slug;
    @SerializedName("cmcRank")
    @Expose
    private Integer cmcRank;
    @SerializedName("marketPairCount")
    @Expose
    private Integer marketPairCount;
    @SerializedName("circulatingSupply")
    @Expose
    private Double circulatingSupply;
    @SerializedName("totalSupply")
    @Expose
    private Double totalSupply;
    @SerializedName("maxSupply")
    @Expose
    private Long maxSupply;
    @SerializedName("ath")
    @Expose
    private Double ath;
    @SerializedName("atl")
    @Expose
    private Double atl;
    @SerializedName("high24h")
    @Expose
    private Double high24h;
    @SerializedName("low24h")
    @Expose
    private Double low24h;
    @SerializedName("isActive")
    @Expose
    private Integer isActive;
    @SerializedName("lastUpdated")
    @Expose
    private String lastUpdated;
    @SerializedName("dateAdded")
    @Expose
    private String dateAdded;
    @SerializedName("quotes")
    @Expose
    private List<Quote> quotes = null;
    @SerializedName("isAudited")
    @Expose
    private Boolean isAudited;

    protected CryptoCurrency(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        name = in.readString();
        symbol = in.readString();
        slug = in.readString();
        if (in.readByte() == 0) {
            cmcRank = null;
        } else {
            cmcRank = in.readInt();
        }
        if (in.readByte() == 0) {
            marketPairCount = null;
        } else {
            marketPairCount = in.readInt();
        }
        if (in.readByte() == 0) {
            circulatingSupply = null;
        } else {
            circulatingSupply = in.readDouble();
        }
        if (in.readByte() == 0) {
            totalSupply = null;
        } else {
            totalSupply = in.readDouble();
        }
        if (in.readByte() == 0) {
            maxSupply = null;
        } else {
            maxSupply = in.readLong();
        }
        if (in.readByte() == 0) {
            ath = null;
        } else {
            ath = in.readDouble();
        }
        if (in.readByte() == 0) {
            atl = null;
        } else {
            atl = in.readDouble();
        }
        if (in.readByte() == 0) {
            high24h = null;
        } else {
            high24h = in.readDouble();
        }
        if (in.readByte() == 0) {
            low24h = null;
        } else {
            low24h = in.readDouble();
        }
        if (in.readByte() == 0) {
            isActive = null;
        } else {
            isActive = in.readInt();
        }
        lastUpdated = in.readString();
        dateAdded = in.readString();
        byte tmpIsAudited = in.readByte();
        isAudited = tmpIsAudited == 0 ? null : tmpIsAudited == 1;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public Integer getCmcRank() {
        return cmcRank;
    }

    public void setCmcRank(Integer cmcRank) {
        this.cmcRank = cmcRank;
    }

    public Integer getMarketPairCount() {
        return marketPairCount;
    }

    public void setMarketPairCount(Integer marketPairCount) {
        this.marketPairCount = marketPairCount;
    }

    public Double getCirculatingSupply() {
        return circulatingSupply;
    }

    public void setCirculatingSupply(Double circulatingSupply) {
        this.circulatingSupply = circulatingSupply;
    }

    public Double getTotalSupply() {
        return totalSupply;
    }

    public void setTotalSupply(Double totalSupply) {
        this.totalSupply = totalSupply;
    }

    public Long getMaxSupply() {
        return maxSupply;
    }

    public void setMaxSupply(Long maxSupply) {
        this.maxSupply = maxSupply;
    }

    public Double getAth() {
        return ath;
    }

    public void setAth(Double ath) {
        this.ath = ath;
    }

    public Double getAtl() {
        return atl;
    }

    public void setAtl(Double atl) {
        this.atl = atl;
    }

    public Double getHigh24h() {
        return high24h;
    }

    public void setHigh24h(Double high24h) {
        this.high24h = high24h;
    }

    public Double getLow24h() {
        return low24h;
    }

    public void setLow24h(Double low24h) {
        this.low24h = low24h;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public List<Quote> getQuotes() {
        return quotes;
    }

    public void setQuotes(List<Quote> quotes) {
        this.quotes = quotes;
    }

    public Boolean getIsAudited() {
        return isAudited;
    }

    public void setIsAudited(Boolean isAudited) {
        this.isAudited = isAudited;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(symbol);
        dest.writeString(lastUpdated);
        dest.writeInt(cmcRank);
        dest.writeInt(marketPairCount);
        dest.writeDouble(circulatingSupply);
        dest.writeDouble(totalSupply);
        dest.writeDouble(maxSupply);
        dest.writeDouble(ath);
        dest.writeDouble(atl);
        dest.writeDouble(high24h);
        dest.writeDouble(low24h);
        dest.writeInt(isActive);
        dest.writeString(dateAdded);
        dest.writeString(slug);
    }
}
