package com.vitefinetechapp.vitefinetech.Aeps2;

public class Ministatement {
    private String date;
    private String narriation;
    private String amount;
    private String txntype;


    public Ministatement(String date, String narriation, String amount, String txntype) {
        this.date = date;
        this.narriation = narriation;
        this.amount = amount;
        this.txntype = txntype;
    }

    public Ministatement() {

    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNarriation() {
        return narriation;
    }

    public void setNarriation(String narriation) {
        this.narriation = narriation;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTxntype() {
        return txntype;
    }

    public void setTxntype(String txntype) {
        this.txntype = txntype;
    }
}
