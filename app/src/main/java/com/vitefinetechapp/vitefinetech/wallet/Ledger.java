package com.vitefinetechapp.vitefinetech.wallet;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public abstract class Ledger {


    @Expose
    @SerializedName("data")
    private Data data;
    @Expose
    @SerializedName("msg")
    private String msg;
    @Expose
    @SerializedName("status")
    private boolean status;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public static class Data {
        @Expose
        @SerializedName("data")
        private List<List<Integer>> data;
        @Expose
        @SerializedName("recordsFiltered")
        private int recordsFiltered;
        @Expose
        @SerializedName("recordsTotal")
        private int recordsTotal;
        @Expose
        @SerializedName("draw")
        private int draw;

        public List<List<Integer>> getData() {
            return data;
        }

        public void setData(List<List<Integer>> data) {
            this.data = data;
        }

        public int getRecordsFiltered() {
            return recordsFiltered;
        }

        public void setRecordsFiltered(int recordsFiltered) {
            this.recordsFiltered = recordsFiltered;
        }

        public int getRecordsTotal() {
            return recordsTotal;
        }

        public void setRecordsTotal(int recordsTotal) {
            this.recordsTotal = recordsTotal;
        }

        public int getDraw() {
            return draw;
        }

        public void setDraw(int draw) {
            this.draw = draw;
        }
    }
}
