package com.example.app_giay.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.app_giay.database.DatabaseHelper;

public class DonDatHangDAO {
    private DatabaseHelper dbHelper;
    public DonDatHangDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void addDonDatHang(String dh_ngaylap, String dh_noigiao,int tt_ma, int kh_ma) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "INSERT INTO dondathang (dh_ngaylap, dh_noigiao, tt_ma, kh_ma) VALUES (?, ?, ?, ?)";
        Object[] args = {dh_ngaylap, dh_noigiao, tt_ma, kh_ma};
        db.execSQL(sql, args);
    }
    public int getdonbihuuy() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "SELECT COUNT(*) FROM dondathang WHERE tt_ma = 1";
        Cursor cursor = db.rawQuery(sql, null);
        int count = 0;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        cursor.close();
        return count;
    }

    public int getdonthanhcong() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "SELECT COUNT(*) FROM dondathang WHERE tt_ma = 2";
        Cursor cursor = db.rawQuery(sql, null);
        int count = 0;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        cursor.close();
        return count;
    }
}
