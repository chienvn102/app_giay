package com.example.app_giay.model;

import android.graphics.Bitmap;

public class SanPham {
    private int sp_ma;
    private String sp_ten;
    private double sp_gia;
    private String sp_ngaycapnhat;
    private int sp_soluong;
    private int lsp_ma;
    private int nsx_ma;
    private Bitmap sp_hinhanh;
    private String sp_mota;
    private String sp_doituong;

    public SanPham(int sp_ma, String sp_ten, double sp_gia, String sp_ngaycapnhat, int sp_soluong, int lsp_ma, int nsx_ma, Bitmap sp_hinhanh, String sp_mota, String sp_doituong) {
        this.sp_ma = sp_ma;
        this.sp_ten = sp_ten;
        this.sp_gia = sp_gia;
        this.sp_ngaycapnhat = sp_ngaycapnhat;
        this.sp_soluong = sp_soluong;
        this.lsp_ma = lsp_ma;
        this.nsx_ma = nsx_ma;
        this.sp_hinhanh = sp_hinhanh;
        this.sp_mota = sp_mota;
        this.sp_doituong = sp_doituong;
    }

    public SanPham() {
        // Constructor rỗng
    }
    // Các phương thức getter và setter cho các thuộc tính
    public int getSp_ma() {
        return sp_ma;
    }

    public void setSp_ma(int sp_ma) {
        this.sp_ma = sp_ma;
    }

    public String getSp_ten() {
        return sp_ten;
    }

    public void setSp_ten(String sp_ten) {
        this.sp_ten = sp_ten;
    }

    public double getSp_gia() {
        return sp_gia;
    }

    public void setSp_gia(double sp_gia) {
        this.sp_gia = sp_gia;
    }

    public String getSp_ngaycapnhat() {
        return sp_ngaycapnhat;
    }

    public void setSp_ngaycapnhat(String sp_ngaycapnhat) {
        this.sp_ngaycapnhat = sp_ngaycapnhat;
    }

    public int getSp_soluong() {
        return sp_soluong;
    }

    public void setSp_soluong(int sp_soluong) {
        this.sp_soluong = sp_soluong;
    }

    public int getLsp_ma() {
        return lsp_ma;
    }

    public void setLsp_ma(int lsp_ma) {
        this.lsp_ma = lsp_ma;
    }

    public int getNsx_ma() {
        return nsx_ma;
    }

    public void setNsx_ma(int nsx_ma) {
        this.nsx_ma = nsx_ma;
    }

    public Bitmap getSp_hinhanh() {
        return sp_hinhanh;
    }

    public void setSp_hinhanh(Bitmap sp_hinhanh) {
        this.sp_hinhanh = sp_hinhanh;
    }

    public String getSp_mota() {
        return sp_mota;
    }

    public void setSp_mota(String sp_mota) {
        this.sp_mota = sp_mota;
    }

    public String getSp_doituong() {
        return sp_doituong;
    }

    public void setSp_doituong(String sp_doituong) {
        this.sp_doituong = sp_doituong;
    }

}
