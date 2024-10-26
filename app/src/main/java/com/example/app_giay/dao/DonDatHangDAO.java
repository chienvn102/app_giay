package com.example.app_giay.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.app_giay.database.DatabaseHelper;

public class DonDatHangDAO {
    private DatabaseHelper dbHelper;
    public DonDatHangDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public int addDonDatHang(String dh_ngaylap, String dh_noigiao, int tt_ma, int kh_ma) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("dh_ngaylap", dh_ngaylap);
        values.put("dh_noigiao", dh_noigiao);
        values.put("tt_ma", tt_ma);
        values.put("kh_ma", kh_ma);

        long dhMa = db.insert("dondathang", null, values); // Thực hiện thêm đơn hàng và lấy mã đơn hàng
        db.close();

        return (int) dhMa; // Trả về mã đơn hàng vừa thêm dưới dạng int
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
        String sql = "SELECT COUNT(*) FROM dondathang WHERE tt_ma = 4";
        Cursor cursor = db.rawQuery(sql, null);
        int count = 0;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        cursor.close();
        return count;
    }
    public void updateDonDatHang(int dh_ma, int tt_ma) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tt_ma", tt_ma);
        String whereClause = "dh_ma = ?";
        String[] whereArgs = {String.valueOf(dh_ma)};
        db.update("dondathang", values, whereClause, whereArgs);
        db.close();
    }
}
