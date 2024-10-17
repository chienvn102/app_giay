package com.example.app_giay.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.app_giay.database.DatabaseHelper;
import com.example.app_giay.model.nhaSanXuat; // Make sure to create a model for nhaSanXuat

import java.util.ArrayList;

public class nhaSanXuatDao {
    private DatabaseHelper nhaSanXuatdb;


    public nhaSanXuatDao(Context context) {
        nhaSanXuatdb = new DatabaseHelper(context);
    }


    public nhaSanXuat getNhaSanXuatById(int nsx_ma) {
        nhaSanXuat nhaSanXuat = null;
        String sql = "SELECT * FROM nhaSanXuat WHERE nsx_ma = ?";
        Cursor cursor = nhaSanXuatdb.getReadableDatabase().rawQuery(sql, new String[]{String.valueOf(nsx_ma)});
        try {
            if (cursor.moveToFirst()) {
                nhaSanXuat = new nhaSanXuat();
                nhaSanXuat.setNsx_ma(cursor.getInt(0));
                nhaSanXuat.setNsx_ten(cursor.getString(1));
            }
        } finally {
            cursor.close();
        }
        return nhaSanXuat;
    }

    public void addNhaSanXuat(String nsx_ten) {
        nhaSanXuatdb.getWritableDatabase();
        String sql = "INSERT INTO nhaSanXuat (nsx_ten) VALUES (?)";
        Object[] args = {nsx_ten};
        nhaSanXuatdb.getWritableDatabase().execSQL(sql, args);
    }

    public void deleteNhaSanXuat(int nsx_ma) {
        SQLiteDatabase db = null; // Khai báo biến cơ sở dữ liệu
        try {
            db = nhaSanXuatdb.getWritableDatabase(); // Lấy cơ sở dữ liệu
            String sql = "DELETE FROM nhaSanXuat WHERE nsx_ma = ?";
            db.execSQL(sql, new Object[]{nsx_ma}); // Thực hiện lệnh xóa
        } catch (Exception e) {
            // Xử lý ngoại lệ
            Log.e("nhaSanXuatDao", "Lỗi khi xóa nhà sản xuất : " + e.getMessage());
        } finally {
            if (db != null) {
                db.close(); // Đảm bảo đóng cơ sở dữ liệu
            }
        }
    }

    public void updateNhaSanXuat(int nsx_ma, String nsx_ten) {
        nhaSanXuatdb.getWritableDatabase();
        String sql = "UPDATE nhaSanXuat SET nsx_ten = ? WHERE nsx_ma = ?";
        Object[] args = {nsx_ten, nsx_ma};
        nhaSanXuatdb.getWritableDatabase().execSQL(sql, args);
    }

    public ArrayList<nhaSanXuat> getAllNhaSanXuat() {
        ArrayList<nhaSanXuat> data = new ArrayList<>();
        String sql = "SELECT * FROM nhaSanXuat";
        Cursor cursor = nhaSanXuatdb.getReadableDatabase().rawQuery(sql, null);
        try {
            while (cursor.moveToNext()) {
                nhaSanXuat nhaSanXuat = new nhaSanXuat();
                nhaSanXuat.setNsx_ma(cursor.getInt(0));
                nhaSanXuat.setNsx_ten(cursor.getString(1));
                data.add(nhaSanXuat);
            }
        } finally {
            cursor.close();
        }
        return data;
    }

    public ArrayList<String> getAllTenNhaSanXuat() {
        ArrayList<String> tenNhaSanXuatList = new ArrayList<>();
        String sql = "SELECT nsx_ten FROM nhaSanXuat";
        Cursor cursor = nhaSanXuatdb.getReadableDatabase().rawQuery(sql, null);
        try {
            while (cursor.moveToNext()) {
                String tenNhaSanXuat = cursor.getString(0);
                tenNhaSanXuatList.add(tenNhaSanXuat);
            }
        } finally {
            cursor.close();
        }
        return tenNhaSanXuatList;
    }
    public int getNhaSanXuatMaByTen(String nsx_ten) {
        String sql = "SELECT nsx_ma FROM nhaSanXuat WHERE nsx_ten = ?";
        Cursor cursor = nhaSanXuatdb.getReadableDatabase().rawQuery(sql, new String[]{nsx_ten});
        int nsx_ma = -1;
        try {
            if (cursor.moveToFirst()) {
                nsx_ma = cursor.getInt(0);
            }
        } finally {
            cursor.close();
        }
        return nsx_ma;
    }
}
