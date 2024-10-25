package com.example.app_giay.model;

public class cart {
    private int cart_id;
    private int users_id;
    private int sp_ma;
    private int sp_soluong;
    // Constructor có tham số
    public cart(int cart_id, int users_id, int sp_ma, int sp_soluong) {
        this.cart_id = cart_id;
        this.users_id = users_id;
        this.sp_ma = sp_ma;
        this.sp_soluong = sp_soluong;
    }
    // Constructor không tham số
    public cart() {
    }
    // Các getter và setter
    public int getCart_id() {
        return cart_id;
    }

    public void setCart_id(int cart_id) {
        this.cart_id = cart_id;
    }

    public int getUsers_id() {
        return users_id;
    }

    public void setUsers_id(int users_id) {
        this.users_id = users_id;
    }

    public int getSp_ma() {
        return sp_ma;
    }

    public void setSp_ma(int sp_ma) {
        this.sp_ma = sp_ma;
    }

    public int getSp_soluong() {
        return sp_soluong;
    }

    public void setSp_soluong(int sp_soluong) {
        this.sp_soluong = sp_soluong;
    }
}
