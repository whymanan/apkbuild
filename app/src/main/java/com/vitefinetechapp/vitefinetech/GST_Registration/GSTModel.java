package com.vitefinetechapp.vitefinetech.GST_Registration;

public class GSTModel {

    String firm_name, nature_of_prot, nature_of_bussi, state, district, bussi_add, comp_type,message,name,
    email,mobile_no,f_h_name,dob,pan_no,adhar_no,address,din_no;
    int status;

    public GSTModel(String firm_name, String nature_of_prot, String nature_of_bussi, String state, String district, String bussi_add, String comp_type, String message, String name, String email, String mobile_no, String f_h_name, String dob, String pan_no, String adhar_no, String address, String din_no, int status) {
        this.firm_name = firm_name;
        this.nature_of_prot = nature_of_prot;
        this.nature_of_bussi = nature_of_bussi;
        this.state = state;
        this.district = district;
        this.bussi_add = bussi_add;
        this.comp_type = comp_type;
        this.message = message;
        this.name = name;
        this.email = email;
        this.mobile_no = mobile_no;
        this.f_h_name = f_h_name;
        this.dob = dob;
        this.pan_no = pan_no;
        this.adhar_no = adhar_no;
        this.address = address;
        this.din_no = din_no;
        this.status = status;
    }

    public GSTModel(String message , String firm_name, String nature_of_prot, String nature_of_bussi, String state, String district, String bussi_add, String comp_type, int status) {
        this.firm_name = firm_name;
        this.nature_of_prot = nature_of_prot;
        this.nature_of_bussi = nature_of_bussi;
        this.state = state;
        this.district = district;
        this.bussi_add = bussi_add;
        this.comp_type = comp_type;
        this.status = status;
        this.message = message;
    }

    public GSTModel(String firm_name) {
        this.firm_name = firm_name;

    }

    public GSTModel() {
        this.firm_name = firm_name;
        this.nature_of_prot = nature_of_prot;
        this.nature_of_bussi = nature_of_bussi;
        this.state = state;
        this.district = district;
        this.bussi_add = bussi_add;
        this.comp_type = comp_type;
        this.message = message;
        this.name = name;
        this.email = email;
        this.mobile_no = mobile_no;
        this.f_h_name = f_h_name;
        this.dob = dob;
        this.pan_no = pan_no;
        this.adhar_no = adhar_no;
        this.address = address;
        this.din_no = din_no;
        this.status = status;
    }


    public String getFirm_name() {
        return firm_name;
    }

    public void setFirm_name(String firm_name) {
        this.firm_name = firm_name;
    }

    public String getNature_of_prot() {
        return nature_of_prot;
    }

    public void setNature_of_prot(String nature_of_prot) {
        this.nature_of_prot = nature_of_prot;
    }

    public String getNature_of_bussi() {
        return nature_of_bussi;
    }

    public void setNature_of_bussi(String nature_of_bussi) {
        this.nature_of_bussi = nature_of_bussi;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getBussi_add() {
        return bussi_add;
    }

    public void setBussi_add(String bussi_add) {
        this.bussi_add = bussi_add;
    }

    public String getComp_type() {
        return comp_type;
    }

    public void setComp_type(String comp_type) {
        this.comp_type = comp_type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getF_h_name() {
        return f_h_name;
    }

    public void setF_h_name(String f_h_name) {
        this.f_h_name = f_h_name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPan_no() {
        return pan_no;
    }

    public void setPan_no(String pan_no) {
        this.pan_no = pan_no;
    }

    public String getAdhar_no() {
        return adhar_no;
    }

    public void setAdhar_no(String adhar_no) {
        this.adhar_no = adhar_no;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDin_no() {
        return din_no;
    }

    public void setDin_no(String din_no) {
        this.din_no = din_no;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "GSTModel{" +
                "firm_name='" + firm_name + '\'' +
                ", nature_of_prot='" + nature_of_prot + '\'' +
                ", nature_of_bussi='" + nature_of_bussi + '\'' +
                ", state='" + state + '\'' +
                ", district='" + district + '\'' +
                ", bussi_add='" + bussi_add + '\'' +
                ", comp_type='" + comp_type + '\'' +
                ", message='" + message + '\'' +
                ", status=" + status +
                '}';
    }
}
