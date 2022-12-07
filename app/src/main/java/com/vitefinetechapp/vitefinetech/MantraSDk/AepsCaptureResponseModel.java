package com.vitefinetechapp.vitefinetech.MantraSDk;

import org.json.JSONObject;

public class AepsCaptureResponseModel {

    public String errCode; //done
    public String errInfo;//done
    public String fCount;//done
    public String fType;//done
    public String iCount;//done
    public String iType;//done
    public String pCount;//done
    public String pType;//done
    public String nmPoints;//done
    public String qScore;//done
    public String dpID; //done
    public String rdsID; //done
    public String rdsVer;//
    public String dc; //
    public String mi; //done
    public String mc; //done
    public String ci; //doone
    public String sessionKey; //done
    public String hmac; //done
    public String pidDatatype;
    public String piddata;

    public JSONObject additional_info = new JSONObject();


    public AepsCaptureResponseModel() {
    }

    public AepsCaptureResponseModel(String errCode, String errInfo, String fCount, String fType, String iCount, String iType, String pCount, String pType, String nmPoints, String qScore, String dpID, String rdsID, String rdsVer, String dc, String mi, String mc, String ci, String sessionKey, String hmac, String pidDatatype, String piddata) {
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
        this.pidDatatype = pidDatatype;
        this.piddata = piddata;
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
        return pidDatatype;
    }

    public void setPidDatatype(String pidDatatype) {
        this.pidDatatype = pidDatatype;
    }

    public String getPiddata() {
        return piddata;
    }

    public void setPiddata(String piddata) {
        this.piddata = piddata;
    }

    @Override
    public String toString() {
        return "AepsCaptureResponseModel{" +
                "errCode='" + errCode + '\'' +
                ", errInfo='" + errInfo + '\'' +
                ", fCount='" + fCount + '\'' +
                ", fType='" + fType + '\'' +
                ", iCount='" + iCount + '\'' +
                ", iType='" + iType + '\'' +
                ", pCount='" + pCount + '\'' +
                ", pType='" + pType + '\'' +
                ", nmPoints='" + nmPoints + '\'' +
                ", qScore='" + qScore + '\'' +
                ", dpID='" + dpID + '\'' +
                ", rdsID='" + rdsID + '\'' +
                ", rdsVer='" + rdsVer + '\'' +
                ", dc='" + dc + '\'' +
                ", mi='" + mi + '\'' +
                ", mc='" + mc + '\'' +
                ", ci='" + ci + '\'' +
                ", sessionKey='" + sessionKey + '\'' +
                ", hmac='" + hmac + '\'' +
                ", pidDatatype='" + pidDatatype + '\'' +
                ", piddata='" + piddata + '\'' +
                ", additional_info=" + additional_info +
                '}';
    }

    public JSONObject getJSON(String aadharno) {
        JSONObject jsonObject = new JSONObject();
        try {
            additional_info.put("aadharno;", "" + aadharno);
            additional_info.put("errCode", "" + errCode);
            additional_info.put("errInfo", "" + errInfo);
            additional_info.put("fCount", "" + fCount);
            additional_info.put("fType", "" + fType);
            additional_info.put("iCount", "" + iCount);
            additional_info.put("iType", "" + iType);
            additional_info.put("pCount", "" + pCount);
            additional_info.put("pType", "" + pType);
            additional_info.put("nmPoints", "" + nmPoints);
            additional_info.put("qScore", "" + qScore);
            additional_info.put("dpID", "" + dpID);
            additional_info.put("rdsID", "" + rdsID);
            additional_info.put("rdsVer", "" + rdsVer);
            additional_info.put("dc", "" + dc);
            additional_info.put("mi", "" + mi);
            additional_info.put("mc", "" + mc);
            additional_info.put("ci", "" + ci);
            additional_info.put("sessionKey", "" + sessionKey);
            additional_info.put("hmac", "" + hmac);
            additional_info.put("ci", "" + ci);
            additional_info.put("pidDatatype", "" + pidDatatype);
            additional_info.put("piddata", "" + piddata);

            return additional_info;
        } catch (Exception e) {
            e.printStackTrace();
            jsonObject = null;
        }
        return jsonObject;
    }

}
