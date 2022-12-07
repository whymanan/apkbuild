package com.vitefinetechapp.vitefinetech.KYCDetails;

public class DataPart {

    private String fileName;
    private byte[] content;
    private String type;
    private String user_id;

    public DataPart(String s, byte[] fileDataFromDrawable) {
    }

    public DataPart(String name, byte[] data,String user_id,String type) {
        fileName = name;
        content = data;
        user_id = user_id;
        type = type;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getFileName() {
        return fileName;
    }

    byte[] getContent() {
        return content;
    }

    String getType() {
        return type;
    }

}
