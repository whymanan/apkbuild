package com.vitefinetechapp.vitefinetech.History;

public class HistoryModel {

    private String course_name;
    private String person_name;
    private String date;
    private String days_ago;
    private String transaction_type;
    private int history_image;
    private String amount;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public HistoryModel() {
        this.course_name = course_name;
        this.person_name = person_name;
        this.date = date;
        this.days_ago = days_ago;
        this.transaction_type = transaction_type;
        this.history_image = history_image;

    }

    public HistoryModel(String course_name, String person_name, String date, String days_ago, String transaction_type, int history_image) {
        this.course_name = course_name;
        this.person_name = person_name;
        this.date = date;
        this.days_ago = days_ago;
        this.transaction_type = transaction_type;
        this.history_image = history_image;
    }

    public HistoryModel(String course_name, String person_name, int history_image,String amount) {
        this.course_name = course_name;
        this.person_name = person_name;
        this.history_image = history_image;
        this.amount= amount;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getPerson_name() {
        return person_name;
    }

    public void setPerson_name(String person_name) {
        this.person_name = person_name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDays_ago() {
        return days_ago;
    }

    public void setDays_ago(String days_ago) {
        this.days_ago = days_ago;
    }

    public int getHistory_image() {
        return history_image;
    }

    public void setHistory_image(int history_image) {
        this.history_image = history_image;
    }

    public String getTransaction_type() {
        return transaction_type;
    }

    public void setTransaction_type(String transaction_type) {
        this.transaction_type = transaction_type;
    }
}
