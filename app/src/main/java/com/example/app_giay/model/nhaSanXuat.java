package com.example.app_giay.model;

public class nhaSanXuat {
    private int nsx_ma; // Manufacturer ID
    private String nsx_ten; // Manufacturer name

    public nhaSanXuat(int nsx_ma, String nsx_ten) {
        this.nsx_ma = nsx_ma;
        this.nsx_ten = nsx_ten;

    }

    public nhaSanXuat() {
    }

    public int getNsx_ma() {
        return nsx_ma;
    }

    public void setNsx_ma(int nsx_ma) {
        this.nsx_ma = nsx_ma;
    }

    public String getNsx_ten() {
        return nsx_ten;
    }

    public void setNsx_ten(String nsx_ten) {
        this.nsx_ten = nsx_ten;
    }

}
