package com.vitefinetechapp.vitefinetech.MantraSDk;

import org.json.JSONObject;

public class CaptureResponse {
    public String errCode;
    public String errInfo;
    public String fCount;
    public String fType;
    public String iCount;
    public String iType;
    public String pCount;
    public String pType;
    public String nmPoints;
    public String qScore;
    public String dpID;
    public String rdsID;
    public String rdsVer;
    public String dc;
    public String mi;
    public String mc;
    public String ci;
    public String sessionKey;
    public String hmac;
    public String PidDatatype;
    public String Piddata;
    public String additional_info;
    public String additional_info2;
    public String additional_info3;
    public String additional_info4;



    public CaptureResponse() {
    }

    public CaptureResponse(String errCode, String errInfo, String fCount, String fType, String iCount, String iType, String pCount, String pType, String nmPoints, String qScore, String dpID, String rdsID, String rdsVer, String dc, String mi, String mc, String ci, String sessionKey, String hmac, String pidDatatype, String piddata) {
        this.errCode = errCode;
        this.errInfo = errInfo;
        this.fCount = fCount;
        this.fType = fType;
        this.iCount = iCount;
        this.iType = iType;
        this.pCount = pCount;
        this.pType = pType;
        this.nmPoints = nmPoints;
        this.qScore = qScore;
        this.dpID = dpID;
        this.rdsID = rdsID;
        this.rdsVer = rdsVer;
        this.dc = dc;
        this.mi = mi;
        this.mc = mc;
        this.ci = ci;
        this.sessionKey = sessionKey;
        this.hmac = hmac;
        PidDatatype = pidDatatype;
        Piddata = piddata;
    }

    public String getAdditional_info() {
        return additional_info;
    }

    public void setAdditional_info(String additional_info) {
        this.additional_info = additional_info;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrInfo() {
        return errInfo;
    }

    public void setErrInfo(String errInfo) {
        this.errInfo = errInfo;
    }

    public String getfCount() {
        return fCount;
    }

    public void setfCount(String fCount) {
        this.fCount = fCount;
    }

    public String getfType() {
        return fType;
    }

    public void setfType(String fType) {
        this.fType = fType;
    }

    public String getiCount() {
        return iCount;
    }

    public void setiCount(String iCount) {
        this.iCount = iCount;
    }

    public String getiType() {
        return iType;
    }

    public void setiType(String iType) {
        this.iType = iType;
    }

    public String getpCount() {
        return pCount;
    }

    public void setpCount(String pCount) {
        this.pCount = pCount;
    }

    public String getpType() {
        return pType;
    }

    public void setpType(String pType) {
        this.pType = pType;
    }

    public String getNmPoints() {
        return nmPoints;
    }

    public void setNmPoints(String nmPoints) {
        this.nmPoints = nmPoints;
    }

    public String getqScore() {
        return qScore;
    }

    public void setqScore(String qScore) {
        this.qScore = qScore;
    }

    public String getDpID() {
        return dpID;
    }

    public void setDpID(String dpID) {
        this.dpID = dpID;
    }

    public String getRdsID() {
        return rdsID;
    }

    public void setRdsID(String rdsID) {
        this.rdsID = rdsID;
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

    public String getCi() {
        return ci;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public String getHmac() {
        return hmac;
    }

    public void setHmac(String hmac) {
        this.hmac = hmac;
    }

    public String getPidDatatype() {
        return PidDatatype;
    }

    public void setPidDatatype(String pidDatatype) {
        PidDatatype = pidDatatype;
    }

    public String getPiddata() {
        return Piddata;
    }

    public void setPiddata(String piddata) {
        Piddata = piddata;
    }

    public String toString() {
        return "CaptureResponse{errCode='" + this.errCode + '\'' + ", errInfo='" + this.errInfo + '\'' + ", fCount='" + this.fCount + '\'' + ", fType='" + this.fType + '\'' + ", iCount='" + this.iCount + '\'' + ", iType='" + this.iType + '\'' + ", pCount='" + this.pCount + '\'' + ", pType='" + this.pType + '\'' + ", nmPoints='" + this.nmPoints + '\'' + ", qScore='" + this.qScore + '\'' + ", dpID='" + this.dpID + '\'' + ", rdsID='" + this.rdsID + '\'' + ", rdsVer='" + this.rdsVer + '\'' + ", dc='" + this.dc + '\'' + ", mi='" + this.mi + '\'' + ", mc='" + this.mc + '\'' + ", ci='" + this.ci + '\'' + ", sessionKey='" + this.sessionKey + '\'' + ", hmac='" + this.hmac + '\'' + ", pidDatatype='" + this.PidDatatype + '\'' + ", piddata='" + this.Piddata + '\'' + '}';
    }

    public JSONObject toJSON() {
        try {
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("errCode",errCode);
            jsonObject.put("errInfo",errInfo);
            jsonObject.put("fCount",fCount);
            jsonObject.put("fType",fType);
            jsonObject.put("iCount",iCount);
            jsonObject.put("iType",iType);
            jsonObject.put("pCount",pCount);
            jsonObject.put("pType",pType);
            jsonObject.put("nmPoints",nmPoints);
            jsonObject.put("qScore",qScore);
            jsonObject.put("dpID",dpID);
            jsonObject.put("rdsID",rdsID);
            jsonObject.put("rdsVer",rdsVer);
            jsonObject.put("dc",dc);
            jsonObject.put("mi",mi);
            jsonObject.put("mc",mc);
            jsonObject.put("ci",ci);
            jsonObject.put("sessionKey",sessionKey);
            jsonObject.put("hmac",hmac);
            jsonObject.put("pidDatatype",PidDatatype);
            jsonObject.put("piddata",Piddata);
            return  jsonObject;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

}
