package com.example.app_giay.dao;

import android.content.Context;

import com.example.app_giay.database.DatabaseHelper;

public class trangThaiDao {
    private DatabaseHelper dbHelper;

    public trangThaiDao(Context context) {
        dbHelper = new DatabaseHelper(context);
    }
    public void addTrangThai(String tt_ten) {
        dbHelper.getWritableDatabase();
        String sql = "INSERT INTO trangthai (tt_ten) VALUES (?)";
        Object[] args = {tt_ten};
        dbHelper.getWritableDatabase().execSQL(sql, args);
    }
}
