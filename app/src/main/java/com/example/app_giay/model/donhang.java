package com.example.app_giay.model;

public class donhang {
    private String spTen;
    private double spGia;
    private byte[] spHinhAnh;
    private String spMoTa;
    private String dhNoiGiao;
    private String dhMa;
    private int tt_ma;

    // Constructor
    public donhang(String spTen, double spGia, byte[] spHinhAnh, String spMoTa, String dhNoiGiao, String dhMa, int tt_ma) {
        this.spTen = spTen;
        this.spGia = spGia;
        this.spHinhAnh = spHinhAnh;
        this.spMoTa = spMoTa;
        this.dhNoiGiao = dhNoiGiao;
        this.dhMa = dhMa;
        this.tt_ma = tt_ma;
    }

    // Getters and setters
    public String getSpTen() { return spTen; }
    public double getSpGia() { return spGia; }
    public byte[] getSpHinhAnh() { return spHinhAnh; }
    public String getSpMoTa() { return spMoTa; }
    public String getDhNoiGiao() { return dhNoiGiao; }


    public String getDhMa() {
        return dhMa;
    }

    public int getTt_ma() {
        return tt_ma;
    }

    public void setTt_ma(int tt_ma) {
        this.tt_ma = tt_ma;
    }

    public void setDhMa(String dhMa) {
        this.dhMa = dhMa;
    }

    public void setSpTen(String spTen) {
        this.spTen = spTen;
    }

    public void setSpGia(double spGia) {
        this.spGia = spGia;
    }

    public void setSpHinhAnh(byte[] spHinhAnh) {
        this.spHinhAnh = spHinhAnh;
    }

    public void setSpMoTa(String spMoTa) {
        this.spMoTa = spMoTa;
    }

    public void setDhNoiGiao(String dhNoiGiao) {
        this.dhNoiGiao = dhNoiGiao;
    }
}
