package com.example.cryptoapp.model.crypto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Status {

    @SerializedName("timestamp")
    @Expose
    private String timestamp;

    @SerializedName("error_code")
    @Expose
    private String errorCode;

    @SerializedName("error_message")
    @Expose
    private String errorMessage;

    @SerializedName("elapsed")
    @Expose
    private String elapsed;

    @SerializedName("credit_count")
    @Expose
    private Integer creditCount;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getElapsed() {
        return elapsed;
    }

    public void setElapsed(String elapsed) {
        this.elapsed = elapsed;
    }

    public Integer getCreditCount() {
        return creditCount;
    }

    public void setCreditCount(Integer creditCount) {
        this.creditCount = creditCount;
    }

}