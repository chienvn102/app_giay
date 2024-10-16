package com.example.app_giay.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;

import com.example.app_giay.database.DatabaseHelper;

public class SanPhamDao {
    private DatabaseHelper dbHelper;

    public SanPhamDao(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void addSanPham(String sp_ten, double sp_gia, String sp_ngaycapnhat, int sp_soluong, int lsp_ma, int nsx_ma, byte[] sp_hinhanh, String sp_mota, String sp_doituong) {
        SQLiteDatabase db = dbHelper.getWritableDatabase(); // Mở cơ sở dữ liệu
        String sql = "INSERT INTO sanpham (sp_ten, sp_gia, sp_ngaycapnhat, sp_soluong, lsp_ma, nsx_ma, sp_hinhanh, sp_mota, sp_doituong) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Object[] args = {sp_ten, sp_gia, sp_ngaycapnhat, sp_soluong, lsp_ma, nsx_ma, sp_hinhanh, sp_mota, sp_doituong};
        db.execSQL(sql, args); // Thực hiện câu lệnh SQL
        db.close(); // Đóng cơ sở dữ liệu
    }

}
