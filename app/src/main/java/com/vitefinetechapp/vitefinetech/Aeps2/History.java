package com.vitefinetechapp.vitefinetech.Aeps2;

public class History {
    private String aepstype;
    private String date;
    private String amount;
    private String trastatus;


    public History(String aepstype, String date, String amount, String trastatus) {
        this.aepstype = aepstype;
        this.date = date;
        this.amount = amount;
        this.trastatus = trastatus;
    }

    public History() {
    }

    public String getAepstype() {
        return aepstype;
    }

    public void setAepstype(String aepstype) {
        this.aepstype = aepstype;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTrastatus() {
        return trastatus;
    }

    public void setTrastatus(String trastatus) {
        this.trastatus = trastatus;
    }
}
