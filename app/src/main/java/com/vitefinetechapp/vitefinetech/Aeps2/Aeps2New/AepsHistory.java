package com.vitefinetechapp.vitefinetech.Aeps2.Aeps2New;

public class AepsHistory {

    String refNo,tType,status,mobile,amount,bCode,bIfsc,created;

    public AepsHistory(String refNo, String tType, String status, String mobile, String amount, String bCode, String bIfsc, String created) {
        this.refNo = refNo;
        this.tType = tType;
        this.status = status;
        this.mobile = mobile;
        this.amount = amount;
        this.bCode = bCode;
        this.bIfsc = bIfsc;
        this.created = created;
    }

    public String getRefNo() {
        return refNo;
    }

    public void setRefNo(String refNo) {
        this.refNo = refNo;
    }

    public String gettType() {
        return tType;
    }

    public void settType(String tType) {
        this.tType = tType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getbCode() {
        return bCode;
    }

    public void setbCode(String bCode) {
        this.bCode = bCode;
    }

    public String getbIfsc() {
        return bIfsc;
    }

    public void setbIfsc(String bIfsc) {
        this.bIfsc = bIfsc;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }
}
