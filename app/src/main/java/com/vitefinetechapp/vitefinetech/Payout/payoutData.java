package com.vitefinetechapp.vitefinetech.Payout;

public class payoutData {

    String account_no,
            bank_name,
            ifsc_code;
    String phone_no;

    public payoutData(String account_no, String bank_name, String ifsc_code, String phone_no) {
        this.account_no = account_no;
        this.bank_name = bank_name;
        this.ifsc_code = ifsc_code;
        this.phone_no = phone_no;
    }

    public String getAccount_no() {
        return account_no;
    }

    public void setAccount_no(String account_no) {
        this.account_no = account_no;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getIfsc_code() {
        return ifsc_code;
    }

    public void setIfsc_code(String ifsc_code) {
        this.ifsc_code = ifsc_code;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }
}
