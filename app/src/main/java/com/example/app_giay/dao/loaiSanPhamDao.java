package com.example.app_giay.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.app_giay.database.DatabaseHelper;
import com.example.app_giay.model.loaiSanPham;

import java.util.ArrayList;

public class loaiSanPhamDao {
    private DatabaseHelper loaiSanPhamdb;

    public loaiSanPhamDao(Context context) {
        loaiSanPhamdb = new DatabaseHelper(context);
    }
    public loaiSanPham getLoaiSanPhamById(int lsp_ma) {
        loaiSanPham loaiSanPham = null;
        String sql = "SELECT * FROM loaiSanPham WHERE lsp_ma = ?";
        android.database.Cursor cursor = loaiSanPhamdb.getReadableDatabase().rawQuery(sql, new String[]{String.valueOf(lsp_ma)});
        try {
            if (cursor.moveToFirst()) {
                loaiSanPham = new loaiSanPham();
                loaiSanPham.setLsp_ma(cursor.getInt(0));
                loaiSanPham.setLsp_ten(cursor.getString(1));
                loaiSanPham.setLsp_mota(cursor.getString(2));
            }
        } finally {
            cursor.close();
        }
        return loaiSanPham;
    }


    public void addLoaiSanPham(String lsp_ten, String lsp_mota) {
        loaiSanPhamdb.getWritableDatabase();
        String sql = "INSERT INTO loaiSanPham (lsp_ten, lsp_mota) VALUES (?, ?)";
        Object[] args = {lsp_ten, lsp_mota};
        loaiSanPhamdb.getWritableDatabase().execSQL(sql, args);
    }
    public void deleteLoaiSanPham(int lsp_ma) {
        SQLiteDatabase db = null; // Khai báo biến cơ sở dữ liệu
        try {
            db = loaiSanPhamdb.getWritableDatabase(); // Lấy cơ sở dữ liệu
            String sql = "DELETE FROM loaiSanPham WHERE lsp_ma = ?";
            db.execSQL(sql, new Object[]{lsp_ma}); // Thực hiện lệnh xóa
        } catch (Exception e) {
            // Xử lý ngoại lệ
            Log.e("loaiSanPhamDao", "Lỗi khi xóa loại sản phẩm: " + e.getMessage());
        } finally {
            if (db != null) {
                db.close(); // Đảm bảo đóng cơ sở dữ liệu
            }
        }
    }
    public ArrayList<String> getAllTenLoaiSanPham() {
        ArrayList<String> tenLoaiSanPhamList = new ArrayList<>();
        String sql = "SELECT lsp_ten FROM loaiSanPham";
        android.database.Cursor cursor = loaiSanPhamdb.getReadableDatabase().rawQuery(sql, null);
        try {
            while (cursor.moveToNext()) {
                tenLoaiSanPhamList.add(cursor.getString(0));  // Chỉ lấy tên loại sản phẩm
            }
        } finally {
            cursor.close();
        }
        return tenLoaiSanPhamList;
    }

    public void updateLoaiSanPham(int lsp_ma, String lsp_ten, String lsp_mota) {
        loaiSanPhamdb.getWritableDatabase();
        String sql = "UPDATE loaiSanPham SET lsp_ten = ?, lsp_mota = ? WHERE lsp_ma = ?";
        Object[] args = {lsp_ten, lsp_mota, lsp_ma};
        loaiSanPhamdb.getWritableDatabase().execSQL(sql, args);
    }
    public int getLoaiSanPhamMaByTen(String tenLoaiSanPham) {
        String sql = "SELECT lsp_ma FROM loaiSanPham WHERE lsp_ten = ?";
        Cursor cursor = loaiSanPhamdb.getReadableDatabase().rawQuery(sql, new String[]{tenLoaiSanPham});
        try {
            if (cursor.moveToFirst()) {
                return cursor.getInt(0);
            }
        } finally {
            cursor.close();
        }
        return -1; // Trả về -1 nếu không tìm thấy
    }


    public ArrayList<loaiSanPham> getAllLoaiSanPham() {
        ArrayList<loaiSanPham> data = new ArrayList<>();
        String sql = "SELECT * FROM loaiSanPham";
        android.database.Cursor cursor = loaiSanPhamdb.getReadableDatabase().rawQuery(sql, null);
        try {
            while (cursor.moveToNext()) {
                loaiSanPham loaiSanPham = new loaiSanPham();
                loaiSanPham.setLsp_ma(cursor.getInt(0));
                loaiSanPham.setLsp_ten(cursor.getString(1));
                loaiSanPham.setLsp_mota(cursor.getString(2));
                data.add(loaiSanPham);
            }
        } finally {
            cursor.close();
        }
        return data;
    }}
