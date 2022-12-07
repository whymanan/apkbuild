package com.vitefinetechapp.vitefinetech.KYCDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class KYCModel {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("body")
    @Expose
    private String body;
    @SerializedName("userId")
    @Expose
    private Integer userId;
    @SerializedName("id")
    @Expose
    private Integer id;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "KYCModel{" +
                "type='" + type + '\'' +
                ", body='" + body + '\'' +
                ", userId=" + userId +
                ", id=" + id +
                '}';
    }
}
