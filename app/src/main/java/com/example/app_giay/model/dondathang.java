package com.example.app_giay.model;

public class dondathang {
    private int dh_ma;
    private String dh_ngaylap;
    private String dh_noigiao;
    private int tt_ma;
    private int kh_ma;

    // Constructor có tham số
    public dondathang(int dh_ma, String dh_ngaylap, String dh_noigiao, int tt_ma, int kh_ma) {
        this.dh_ma = dh_ma;
        this.dh_ngaylap = dh_ngaylap;
        this.dh_noigiao = dh_noigiao;
        this.tt_ma = tt_ma;
        this.kh_ma = kh_ma;
    }

    // Constructor không tham số
    public dondathang() {}

    // Các getter và setter
    public int getDh_ma() {
        return dh_ma;
    }

    public void setDh_ma(int dh_ma) {
        this.dh_ma = dh_ma;
    }

    public String getDh_ngaylap() {
        return dh_ngaylap;
    }

    public void setDh_ngaylap(String dh_ngaylap) {
        this.dh_ngaylap = dh_ngaylap;
    }

    public String getDh_noigiao() {
        return dh_noigiao;
    }

    public void setDh_noigiao(String dh_noigiao) {
        this.dh_noigiao = dh_noigiao;
    }

    public int getTt_ma() {
        return tt_ma;
    }

    public void setTt_ma(int tt_ma) {
        this.tt_ma = tt_ma;
    }

    public int getKh_ma() {
        return kh_ma;
    }

    public void setKh_ma(int kh_ma) {
        this.kh_ma = kh_ma;
    }
}
