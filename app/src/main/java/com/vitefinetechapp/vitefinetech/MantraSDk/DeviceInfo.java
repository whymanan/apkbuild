package com.vitefinetechapp.vitefinetech.MantraSDk;

public class DeviceInfo {
    public String dpId;
    public String rdsId;
    public String rdsVer;
    public String dc;
    public String mi;
    public String mc;

    public DeviceInfo(){}

    public DeviceInfo(String dpId, String rdsId, String rdsVer, String dc, String mi, String mc) {
        this.dpId = dpId;
        this.rdsId = rdsId;
        this.rdsVer = rdsVer;
        this.dc = dc;
        this.mi = mi;
        this.mc = mc;
    }

    public String getDpId() {
        return dpId;
    }

    public void setDpId(String dpId) {
        this.dpId = dpId;
    }

    public String getRdsId() {
        return rdsId;
    }

    public void setRdsId(String rdsId) {
        this.rdsId = rdsId;
    }

    public String getRdsVer() {
        return rdsVer;
    }

    public void setRdsVer(String rdsVer) {
        this.rdsVer = rdsVer;
    }

    public String getDc() {
        return dc;
    }

    public void setDc(String dc) {
        this.dc = dc;
    }

    public String getMi() {
        return mi;
    }

    public void setMi(String mi) {
        this.mi = mi;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }
}
