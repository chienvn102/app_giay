package com.example.app_giay.model;


public class loaiSanPham {
    private int lsp_ma;
    private String lsp_ten;
    private String lsp_mota;
    private String lsp_danhcho;

    public loaiSanPham(int lsp_ma, String lsp_ten, String lsp_mota, String lsp_danhcho) {
        this.lsp_ma = lsp_ma;
        this.lsp_ten = lsp_ten;
        this.lsp_mota = lsp_mota;
        this.lsp_danhcho = lsp_danhcho;
    }

    public loaiSanPham() {
    }

    public int getLsp_ma() {
        return lsp_ma;
    }

    public void setLsp_ma(int lsp_ma) {
        this.lsp_ma = lsp_ma;
    }

    public String getLsp_ten() {
        return lsp_ten;
    }

    public void setLsp_ten(String lsp_ten) {
        this.lsp_ten = lsp_ten;
    }

    public String getLsp_mota() {
        return lsp_mota;
    }

    public void setLsp_mota(String lsp_mota) {
        this.lsp_mota = lsp_mota;
    }
}
