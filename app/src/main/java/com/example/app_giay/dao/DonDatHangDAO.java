package com.example.app_giay.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.app_giay.database.DatabaseHelper;

import java.text.DecimalFormat;

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

    public String TongTien(int orderStatusId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        double total = 0.0;

        // Query to calculate the total sum for a specific order status
        String query = "SELECT SUM(sp.sp_gia * spd.sp_dh_soluong) AS total " +
                "FROM sanpham sp " +
                "JOIN sanpham_dondathang spd ON sp.sp_ma = spd.sp_ma " +
                "JOIN dondathang dh ON spd.dh_ma = dh.dh_ma " +
                "WHERE dh.tt_ma = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(orderStatusId)});
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndex("total");
                if (columnIndex != -1) {
                    total = cursor.getDouble(columnIndex);
                }
            }
            cursor.close();
        }

        // Format the total to two decimal places and append " VNĐ"
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        return decimalFormat.format(total) + " VNĐ";
    }
    public int gettt_ma(String  dh_ma) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "SELECT tt_ma FROM dondathang WHERE dh_ma = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(dh_ma)});
        int tt_ma = 0;
        if (cursor.moveToFirst()) {
            tt_ma = cursor.getInt(0);
        }
        cursor.close();
        return tt_ma;

    }

}
