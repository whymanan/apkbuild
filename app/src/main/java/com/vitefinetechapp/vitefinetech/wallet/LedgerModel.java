package com.vitefinetechapp.vitefinetech.wallet;

public class LedgerModel {

    private String ledger_ref,ledger_amt,ledger_mode,ledger_stock_type,ledger_commision,ledger_bank,ledger_narration,
            ledger_wallet_id,ledger_wallet_transaction_id,ledger_member_to,ledger_member_from,ledger_service_id,ledger_surcharge,ledger_balance,
            ledger_closebalance,ledger_type, ledger_trans_type,ledger_updated,ledger_date,ledger_status,ledger_member1,ledger_member2,jsonObject;

    public LedgerModel() {
        this.ledger_ref = ledger_ref;
        this.ledger_amt = ledger_amt;
        this.ledger_mode = ledger_mode;
        this.ledger_stock_type = ledger_stock_type;
        this.ledger_commision = ledger_commision;
        this.ledger_bank = ledger_bank;
        this.ledger_narration = ledger_narration;
        this.ledger_wallet_id = ledger_wallet_id;
        this.ledger_wallet_transaction_id = ledger_wallet_transaction_id;
        this.ledger_member_to =ledger_member_to ;
        this.ledger_member_from = ledger_member_from;
        this.ledger_service_id = ledger_service_id;
        this.ledger_surcharge = ledger_surcharge;
        this.ledger_balance = ledger_balance;
        this.ledger_closebalance = ledger_closebalance;
        this.ledger_type = ledger_type;
        this.ledger_trans_type = ledger_trans_type;
        this.ledger_updated =ledger_updated ;
        this.ledger_date =ledger_date ;
        this.ledger_status = ledger_status;
        this.ledger_member1 = ledger_member1;
        this.ledger_member2 = ledger_member2;
        this.jsonObject = jsonObject;
    }


    // Constructor
    public LedgerModel(String ledger_ref, String ledger_amt, String ledger_mode,String ledger_stock_type,
                       String ledger_commision,String ledger_bank,String ledger_narration,String ledger_wallet_id)
    {
        this.ledger_ref = ledger_ref;
        this.ledger_amt = ledger_amt;
        this.ledger_mode = ledger_mode;
        this.ledger_stock_type = ledger_stock_type;
        this.ledger_commision = ledger_commision;
        this.ledger_bank = ledger_bank;
        this.ledger_narration = ledger_narration;
        this.ledger_wallet_id = ledger_wallet_id;

    }

    public String getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(String jsonObject) {
        this.jsonObject = jsonObject;
    }

    public String getLedger_wallet_transaction_id() {
        return ledger_wallet_transaction_id;
    }

    public void setLedger_wallet_transaction_id(String ledger_wallet_transaction_id) {
        this.ledger_wallet_transaction_id = ledger_wallet_transaction_id;
    }

    public String getLedger_member_to() {
        return ledger_member_to;
    }

    public void setLedger_member_to(String ledger_member_to) {
        this.ledger_member_to = ledger_member_to;
    }

    public String getLedger_member_from() {
        return ledger_member_from;
    }

    public void setLedger_member_from(String ledger_member_from) {
        this.ledger_member_from = ledger_member_from;
    }

    public String getLedger_service_id() {
        return ledger_service_id;
    }

    public void setLedger_service_id(String ledger_service_id) {
        this.ledger_service_id = ledger_service_id;
    }

    public String getLedger_surcharge() {
        return ledger_surcharge;
    }

    public void setLedger_surcharge(String ledger_surcharge) {
        this.ledger_surcharge = ledger_surcharge;
    }

    public String getLedger_balance() {
        return ledger_balance;
    }

    public void setLedger_balance(String ledger_balance) {
        this.ledger_balance = ledger_balance;
    }

    public String getLedger_closebalance() {
        return ledger_closebalance;
    }

    public void setLedger_closebalance(String ledger_closebalance) {
        this.ledger_closebalance = ledger_closebalance;
    }

    public String getLedger_type() {
        return ledger_type;
    }

    public void setLedger_type(String ledger_type) {
        this.ledger_type = ledger_type;
    }

    public String getLedger_trans_type() {
        return ledger_trans_type;
    }

    public void setLedger_trans_type(String ledger_trans_type) {
        this.ledger_trans_type = ledger_trans_type;
    }

    public String getLedger_updated() {
        return ledger_updated;
    }

    public void setLedger_updated(String ledger_updated) {
        this.ledger_updated = ledger_updated;
    }

    public String getLedger_date() {
        return ledger_date;
    }

    public void setLedger_date(String ledger_date) {
        this.ledger_date = ledger_date;
    }

    public String getLedger_status() {
        return ledger_status;
    }

    public void setLedger_status(String ledger_status) {
        this.ledger_status = ledger_status;
    }

    public String getLedger_member1() {
        return ledger_member1;
    }

    public void setLedger_member1(String ledger_member1) {
        this.ledger_member1 = ledger_member1;
    }

    public String getLedger_member2() {
        return ledger_member2;
    }

    public void setLedger_member2(String ledger_member2) {
        this.ledger_member2 = ledger_member2;
    }

    public String getLedger_ref() {
        return ledger_ref;
    }

    public void setLedger_ref(String ledger_ref) {
        this.ledger_ref = ledger_ref;
    }

    public String getLedger_amt() {
        return ledger_amt;
    }

    public void setLedger_amt(String ledger_amt) {
        this.ledger_amt = ledger_amt;
    }

    public String getLedger_mode() {
        return ledger_mode;
    }

    public void setLedger_mode(String ledger_mode) {
        this.ledger_mode = ledger_mode;
    }

    public String getLedger_stock_type() {
        return ledger_stock_type;
    }

    public void setLedger_stock_type(String ledger_stock_type) {
        this.ledger_stock_type = ledger_stock_type;
    }

    public String getLedger_commision() {
        return ledger_commision;
    }

    public void setLedger_commision(String ledger_commision) {
        this.ledger_commision = ledger_commision;
    }

    public String getLedger_bank() {
        return ledger_bank;
    }

    public void setLedger_bank(String ledger_bank) {
        this.ledger_bank = ledger_bank;
    }

    public String getLedger_narration() {
        return ledger_narration;
    }

    public void setLedger_narration(String ledger_narration) {
        this.ledger_narration = ledger_narration;
    }

    public String getLedger_wallet_id() {
        return ledger_wallet_id;
    }

    public void setLedger_wallet_id(String ledger_wallet_id) {
        this.ledger_wallet_id = ledger_wallet_id;
    }
}
