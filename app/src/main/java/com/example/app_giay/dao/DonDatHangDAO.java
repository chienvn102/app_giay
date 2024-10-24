package com.example.app_giay.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.app_giay.database.DatabaseHelper;

public class DonDatHangDAO {
    private DatabaseHelper dbHelper;
    public DonDatHangDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void addDonDatHang(String dh_ngaylap, String dh_noigiao) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "INSERT INTO dondathang (dh_ngaylap, dh_noigiao) VALUES (?, ?)";
        Object[] args = {dh_ngaylap, dh_noigiao};
        db.execSQL(sql, args);
    }
}
