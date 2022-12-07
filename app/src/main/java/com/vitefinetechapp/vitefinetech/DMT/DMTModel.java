package com.vitefinetechapp.vitefinetech.DMT;

public class DMTModel {

    String Status , user_detail_id,fk_user_id, country_code,created_at, created_by,first_name,gender,has_deposit_permission,has_trading_permission,
            has_withdrawal_permission, last_name, middle_name,phone_no, secret_key,two_fa_type,updated_at,use_my_trade_point,transection_amoun,aadhar,gstno;

    String beneficiary_id,account_name,acct_no,ifsc_code,ba_primary;

    public DMTModel(String status, String user_detail_id, String fk_user_id, String country_code, String created_at, String created_by, String first_name, String gender, String has_deposit_permission, String has_trading_permission, String has_withdrawal_permission, String last_name, String middle_name, String phone_no, String secret_key, String two_fa_type, String updated_at, String use_my_trade_point, String transection_amoun, String aadhar, String gstno) {
        Status = status;
        this.user_detail_id = user_detail_id;
        this.fk_user_id = fk_user_id;
        this.country_code = country_code;
        this.created_at = created_at;
        this.created_by = created_by;
        this.first_name = first_name;
        this.gender = gender;
        this.has_deposit_permission = has_deposit_permission;
        this.has_trading_permission = has_trading_permission;
        this.has_withdrawal_permission = has_withdrawal_permission;
        this.last_name = last_name;
        this.middle_name = middle_name;
        this.phone_no = phone_no;
        this.secret_key = secret_key;
        this.two_fa_type = two_fa_type;
        this.updated_at = updated_at;
        this.use_my_trade_point = use_my_trade_point;
        this.transection_amoun = transection_amoun;
        this.aadhar = aadhar;
        this.gstno = gstno;
    }

    public DMTModel() {
        Status = Status;
        this.user_detail_id = user_detail_id;
        this.fk_user_id = fk_user_id;
        this.country_code = country_code;
        this.created_at = created_at;
        this.created_by = created_by;
        this.first_name = first_name;
        this.gender = gender;
        this.has_deposit_permission = has_deposit_permission;
        this.has_trading_permission = has_trading_permission;
        this.has_withdrawal_permission = has_withdrawal_permission;
        this.last_name = last_name;
        this.middle_name = middle_name;
        this.phone_no = phone_no;
        this.secret_key = secret_key;
        this.two_fa_type = two_fa_type;
        this.updated_at = updated_at;
        this.use_my_trade_point = use_my_trade_point;
        this.transection_amoun = transection_amoun;
        this.aadhar = aadhar;
        this.gstno = gstno;
        this.beneficiary_id =beneficiary_id;
        this.account_name = account_name;
        this.acct_no = acct_no;
        this.ifsc_code = ifsc_code;
        this.ba_primary=ba_primary;


    }

    public String getBa_primary() {
        return ba_primary;
    }

    public void setBa_primary(String ba_primary) {
        this.ba_primary = ba_primary;
    }

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public String getAcct_no() {
        return acct_no;
    }

    public void setAcct_no(String acct_no) {
        this.acct_no = acct_no;
    }

    public String getIfsc_code() {
        return ifsc_code;
    }

    public void setIfsc_code(String ifsc_code) {
        this.ifsc_code = ifsc_code;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getUser_detail_id() {
        return user_detail_id;
    }

    public void setUser_detail_id(String user_detail_id) {
        this.user_detail_id = user_detail_id;
    }

    public String getFk_user_id() {
        return fk_user_id;
    }

    public void setFk_user_id(String fk_user_id) {
        this.fk_user_id = fk_user_id;
    }

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHas_deposit_permission() {
        return has_deposit_permission;
    }

    public void setHas_deposit_permission(String has_deposit_permission) {
        this.has_deposit_permission = has_deposit_permission;
    }

    public String getHas_trading_permission() {
        return has_trading_permission;
    }

    public void setHas_trading_permission(String has_trading_permission) {
        this.has_trading_permission = has_trading_permission;
    }

    public String getHas_withdrawal_permission() {
        return has_withdrawal_permission;
    }

    public void setHas_withdrawal_permission(String has_withdrawal_permission) {
        this.has_withdrawal_permission = has_withdrawal_permission;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getMiddle_name() {
        return middle_name;
    }

    public void setMiddle_name(String middle_name) {
        this.middle_name = middle_name;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getSecret_key() {
        return secret_key;
    }

    public void setSecret_key(String secret_key) {
        this.secret_key = secret_key;
    }

    public String getTwo_fa_type() {
        return two_fa_type;
    }

    public void setTwo_fa_type(String two_fa_type) {
        this.two_fa_type = two_fa_type;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getUse_my_trade_point() {
        return use_my_trade_point;
    }

    public void setUse_my_trade_point(String use_my_trade_point) {
        this.use_my_trade_point = use_my_trade_point;
    }

    public String getTransection_amoun() {
        return transection_amoun;
    }

    public void setTransection_amoun(String transection_amoun) {
        this.transection_amoun = transection_amoun;
    }

    public String getAadhar() {
        return aadhar;
    }

    public void setAadhar(String aadhar) {
        this.aadhar = aadhar;
    }

    public String getGstno() {
        return gstno;
    }

    public void setGstno(String gstno) {
        this.gstno = gstno;
    }

    public String getBeneficiary_id() {
        return beneficiary_id;
    }

    public void setBeneficiary_id(String beneficiary_id) {
        this.beneficiary_id = beneficiary_id;
    }
}
