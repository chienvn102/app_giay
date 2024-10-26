package com.example.app_giay.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.app_giay.database.DatabaseHelper;
import com.example.app_giay.model.donhang;

import java.util.ArrayList;

public class sp_dondathangDao {
    private DatabaseHelper dbHelper;

    public sp_dondathangDao(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void addsp_dondathang(int sp_ma, int dh_ma, int sp_dh_soluong,int user_id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "INSERT INTO sanpham_dondathang (sp_ma, dh_ma, sp_dh_soluong,user_id) VALUES (?, ?, ?,?)";
        db.execSQL(sql, new Object[]{sp_ma, dh_ma, sp_dh_soluong, user_id});
        db.close();
    }
    public ArrayList<donhang> ThongTinDonHang(int userId) {
        ArrayList<donhang> donHangList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query = "SELECT sp.sp_ten, sp.sp_gia, sp.sp_hinhanh, sp.sp_mota, ddh.dh_noigiao, ddh.dh_ma , ddh.tt_ma " +
                "FROM sanpham_dondathang sp_ddh " +
                "JOIN sanpham sp ON sp_ddh.sp_ma = sp.sp_ma " +
                "JOIN dondathang ddh ON sp_ddh.dh_ma = ddh.dh_ma " +
                "WHERE sp_ddh.user_id = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});

        if (cursor != null) {
            while (cursor.moveToNext()) {
                String spTen = cursor.getString(cursor.getColumnIndexOrThrow("sp_ten"));
                double spGia = cursor.getDouble(cursor.getColumnIndexOrThrow("sp_gia"));
                byte[] spHinhAnh = cursor.getBlob(cursor.getColumnIndexOrThrow("sp_hinhanh"));
                String spMoTa = cursor.getString(cursor.getColumnIndexOrThrow("sp_mota"));
                String dhNoiGiao = cursor.getString(cursor.getColumnIndexOrThrow("dh_noigiao"));
                String dhMa = cursor.getString(cursor.getColumnIndexOrThrow("dh_ma"));
                int ttMa = cursor.getInt(cursor.getColumnIndexOrThrow("tt_ma"));

                donhang dh = new donhang(spTen, spGia, spHinhAnh, spMoTa, dhNoiGiao, dhMa, ttMa);
                donHangList.add(dh);
            }
            cursor.close();
        }

        db.close();
        return donHangList;
    }
    public ArrayList<donhang> ThongTinAllDonHang() {
        ArrayList<donhang> donHangList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query = "SELECT sp.sp_ten, sp.sp_gia, sp.sp_hinhanh, sp.sp_mota, ddh.dh_noigiao, ddh.dh_ma,ddh.tt_ma " +
                "FROM sanpham_dondathang sp_ddh " +
                "JOIN sanpham sp ON sp_ddh.sp_ma = sp.sp_ma " +
                "JOIN dondathang ddh ON sp_ddh.dh_ma = ddh.dh_ma";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                String spTen = cursor.getString(cursor.getColumnIndexOrThrow("sp_ten"));
                double spGia = cursor.getDouble(cursor.getColumnIndexOrThrow("sp_gia"));
                byte[] spHinhAnh = cursor.getBlob(cursor.getColumnIndexOrThrow("sp_hinhanh"));
                String spMoTa = cursor.getString(cursor.getColumnIndexOrThrow("sp_mota"));
                String dhNoiGiao = cursor.getString(cursor.getColumnIndexOrThrow("dh_noigiao"));
                String dhMa = cursor.getString(cursor.getColumnIndexOrThrow("dh_ma"));
                int ttMa = cursor.getInt(cursor.getColumnIndexOrThrow("tt_ma"));

                donhang dh = new donhang(spTen, spGia, spHinhAnh, spMoTa, dhNoiGiao, dhMa, ttMa);
                donHangList.add(dh);
            }
            cursor.close();
        }

        db.close();
        return donHangList;
    }

}
