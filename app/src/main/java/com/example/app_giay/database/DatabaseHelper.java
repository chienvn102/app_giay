package com.example.app_giay.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(Context context) {
        super(context, "shop_shoes.db", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng loaisanpham
        String createTableLoaiSanPham = "CREATE TABLE loaisanpham (" +
                "lsp_ma INTEGER PRIMARY KEY," +
                "lsp_ten NVARCHAR(50)," +
                "lsp_mota NVARCHAR(200)" +
                ");";
        db.execSQL(createTableLoaiSanPham);

        // Tạo bảng nhasanxuat
        String createTableNhaSanXuat = "CREATE TABLE nhasanxuat (" +
                "nsx_ma INTEGER PRIMARY KEY," +
                "nsx_ten NVARCHAR(50)" +

                ");";
        db.execSQL(createTableNhaSanXuat);

        // Tạo bảng sanpham
        String createTableSanPham = "CREATE TABLE sanpham (" +
                "sp_ma INTEGER PRIMARY KEY," +
                "sp_ten NVARCHAR(50)," +
                "sp_gia DECIMAL," +
                "sp_ngaycapnhat DATE," +
                "sp_soluong INTEGER," +
                "lsp_ma INTEGER," +
                "nsx_ma INTEGER," +
                "sp_hinhanh NVARCHAR(200)," +
                "sp_mota NVARCHAR(200)," +
                "sp_doituong NVARCHAR(50)," +
                "FOREIGN KEY (lsp_ma) REFERENCES loaisanpham(lsp_ma)," +
                "FOREIGN KEY (nsx_ma) REFERENCES nhasanxuat(nsx_ma)" +
                ");";
        db.execSQL(createTableSanPham);

        // Tạo bảng dondathang
        String createTableDonDatHang = "CREATE TABLE dondathang (" +
                "dh_ma INTEGER PRIMARY KEY," +
                "dh_ngaylap DATE," +
                "dh_noigiao NVARCHAR(200)," +
                "tt_ma INTEGER," +
                "kh_ma INTEGER," +
                "FOREIGN KEY (tt_ma) REFERENCES trangthai(tt_ma)," +
                "FOREIGN KEY (kh_ma) REFERENCES khachhang(kh_ma)" +
                ");";
        db.execSQL(createTableDonDatHang);

        // Tạo bảng trangthai
        String createTableTrangThai = "CREATE TABLE trangthai (" +
                "tt_ma INTEGER PRIMARY KEY," +
                "tt_ten NVARCHAR(50)" +
                ");";
        db.execSQL(createTableTrangThai);

        // Tạo bảng khachhang
        String createTableKhachHang = "CREATE TABLE khachhang (" +
                "kh_ma INTEGER PRIMARY KEY," +
                "kh_hoten NVARCHAR(50)," +
                "kh_email NVARCHAR(50)," +
                "kh_sdt NVARCHAR(50)," +
                "kh_diachi NVARCHAR(50)" +
                ");";
        db.execSQL(createTableKhachHang);

        // Tạo bảng users
        String createTableUsers = "CREATE TABLE users (" +
                "users_id INTEGER PRIMARY KEY," +
                "username NVARCHAR(50)," +
                "password NVARCHAR(20)," +
                "role_id INTEGER," +
                "name NVARCHAR(50)," +
                "FOREIGN KEY (role_id) REFERENCES roles(role_id)" +
                ");";
        db.execSQL(createTableUsers);

        // Tạo bảng roles
        String createTableRoles = "CREATE TABLE roles (" +
                "role_id INTEGER PRIMARY KEY," +
                "role_name NVARCHAR(50)" +
                ");";
        db.execSQL(createTableRoles);

        // Tạo bảng sanpham_dondathang (liên kết giữa sanpham và dondathang)
        String createTableSanPhamDonDatHang = "CREATE TABLE sanpham_dondathang (" +
                "sp_ma INTEGER," +
                "dh_ma INTEGER," +
                "sp_dh_soluong INTEGER," +
                "PRIMARY KEY (sp_ma, dh_ma)," +
                "FOREIGN KEY (sp_ma) REFERENCES sanpham(sp_ma)," +
                "FOREIGN KEY (dh_ma) REFERENCES dondathang(dh_ma)" +
                ");";
        db.execSQL(createTableSanPhamDonDatHang);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }

    @Override
    public void onUpgrade(android.database.sqlite.SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS user");
        onCreate(db);
    }

}
