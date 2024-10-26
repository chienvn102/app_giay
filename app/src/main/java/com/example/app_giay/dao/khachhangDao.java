package com.example.app_giay.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.app_giay.database.DatabaseHelper;

public class khachhangDao {
    private DatabaseHelper dbHelper;

    public khachhangDao(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void addKhachHang(String kh_hoten, String kh_email, String kh_sdt, String kh_diachi) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "INSERT INTO khachhang (kh_hoten, kh_email, kh_sdt, kh_diachi) VALUES (?, ?, ?, ?)";
        Object[] args = {kh_hoten, kh_email, kh_sdt, kh_diachi};
        db.execSQL(sql, args);
        db.close();
    }


    public void updateKhachHang(int kh_ma, String kh_ten, String kh_sdt, String kh_diachi, String kh_email, String kh_matkhau) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "UPDATE khachhang SET kh_ten = ?, kh_sdt = ?, kh_diachi = ?, kh_email = ?, kh_matkhau = ? WHERE kh_ma = ?";
        Object[] args = {kh_ten, kh_sdt, kh_diachi, kh_email, kh_matkhau, kh_ma};
        db.execSQL(sql, args);
        db.close();
    }
    public int getKhachHangMaByTen(String kh_hoten) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "SELECT kh_ma FROM khachhang WHERE kh_hoten = ?";
        String[] selectionArgs = {kh_hoten};
        Cursor cursor = db.rawQuery(sql, selectionArgs);
        int kh_ma = -1;
        if (cursor.moveToFirst()) {
            kh_ma = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return kh_ma;
    }


}
