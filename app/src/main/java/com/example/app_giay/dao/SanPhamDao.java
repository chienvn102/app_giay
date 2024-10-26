package com.example.app_giay.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.app_giay.database.DatabaseHelper;
import com.example.app_giay.model.SanPham;

import java.util.ArrayList;

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
    public boolean updateSanPham(int sp_ma, String sp_ten, double sp_gia, String sp_ngaycapnhat, int sp_soluong, int lsp_ma, int nsx_ma, byte[] sp_hinhanh, String sp_mota, String sp_doituong) {
        SQLiteDatabase db = dbHelper.getWritableDatabase(); // Mở cơ sở dữ liệu
        String sql = "UPDATE sanpham SET sp_ten = ?, sp_gia = ?, sp_ngaycapnhat = ?, sp_soluong = ?, lsp_ma = ?, nsx_ma = ?, sp_hinhanh = ?, sp_mota = ?, sp_doituong = ? WHERE sp_ma = ?";

        try {
            Object[] args = {sp_ten, sp_gia, sp_ngaycapnhat, sp_soluong, lsp_ma, nsx_ma, sp_hinhanh, sp_mota, sp_doituong, sp_ma};
            db.execSQL(sql, args); // Thực hiện
            return true; // Cập nhật thành công
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Cập nhật thất bại
        } finally {
            db.close(); // Đóng cơ sở dữ liệu
        }
    }



    public void deleteSanPham(int sp_ma) {
        SQLiteDatabase db = dbHelper.getWritableDatabase(); // Mở cơ sở dữ liệu
        String sql = "DELETE FROM sanpham WHERE sp_ma = ?";
        Object[] args = {sp_ma};
        db.execSQL(sql, args); // Thực hiện câu lệnh SQL
        db.close(); // Đóng cơ sở dữ liệu
    }
    public void deleteAllSanPham() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "DELETE FROM sanpham"; // Câu lệnh xóa tất cả sản phẩm
        db.execSQL(sql);
        db.close();
    }

    public ArrayList<SanPham> getAllSanPham() {
        ArrayList<SanPham> sanPhamList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase(); // Mở cơ sở dữ liệu
        String sql = "SELECT * FROM sanpham"; // Truy vấn lấy tất cả sản phẩm
        Cursor cursor = db.rawQuery(sql, null); // Thực hiện truy vấn

        try {
            while (cursor.moveToNext()) { // Duyệt qua từng dòng kết quả
                SanPham sanPham = new SanPham(); // Khởi tạo đối tượng SanPham

                // Lấy chỉ số cột
                int spMaIndex = cursor.getColumnIndex("sp_ma");
                int spTenIndex = cursor.getColumnIndex("sp_ten");
                int spGiaIndex = cursor.getColumnIndex("sp_gia");
                int spNgayCapNhatIndex = cursor.getColumnIndex("sp_ngaycapnhat");
                int spSoLuongIndex = cursor.getColumnIndex("sp_soluong");
                int lspMaIndex = cursor.getColumnIndex("lsp_ma");
                int nsxMaIndex = cursor.getColumnIndex("nsx_ma");
                int spHinhAnhIndex = cursor.getColumnIndex("sp_hinhanh");
                int spMoTaIndex = cursor.getColumnIndex("sp_mota");
                int spDoiTuongIndex = cursor.getColumnIndex("sp_doituong");

                // Kiểm tra chỉ số cột trước khi truy cập
                if (spMaIndex != -1) sanPham.setSp_ma(cursor.getInt(spMaIndex));
                if (spTenIndex != -1) sanPham.setSp_ten(cursor.getString(spTenIndex));
                if (spGiaIndex != -1) sanPham.setSp_gia(cursor.getDouble(spGiaIndex));
                if (spNgayCapNhatIndex != -1) sanPham.setSp_ngaycapnhat(cursor.getString(spNgayCapNhatIndex));
                if (spSoLuongIndex != -1) sanPham.setSp_soluong(cursor.getInt(spSoLuongIndex));
                if (lspMaIndex != -1) sanPham.setLsp_ma(cursor.getInt(lspMaIndex));
                if (nsxMaIndex != -1) sanPham.setNsx_ma(cursor.getInt(nsxMaIndex));
                if (spHinhAnhIndex != -1) {
                    byte[] imageBytes = cursor.getBlob(spHinhAnhIndex);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                    sanPham.setSp_hinhanh(bitmap);
                }
                if (spMoTaIndex != -1) sanPham.setSp_mota(cursor.getString(spMoTaIndex));
                if (spDoiTuongIndex != -1) sanPham.setSp_doituong(cursor.getString(spDoiTuongIndex));

                sanPhamList.add(sanPham); // Thêm sản phẩm vào danh sách
            }
        } finally {
            cursor.close(); // Đóng cursor
            db.close(); // Đóng cơ sở dữ liệu
        }

        return sanPhamList; // Trả về danh sách sản phẩm
    }
    public int getSp_ma(String sp_ten) {
        SQLiteDatabase db = dbHelper.getReadableDatabase(); // Mở cơ sở dữ liệu
        String sql = "SELECT sp_ma FROM sanpham WHERE sp_ten = ?";
        String[] args = {sp_ten};
        Cursor cursor = db.rawQuery(sql, args); // Thực hiện truy vấn
        try {
            if (cursor.moveToFirst()) {
                return cursor.getInt(0);
            }
        } finally {
            cursor.close();
            db.close();
        }
        return -1;
    }
    public String getSp_ten(int sp_ma) {
        SQLiteDatabase db = dbHelper.getReadableDatabase(); // Mở cơ sở dữ liệu
        String sql = "SELECT sp_ten FROM sanpham WHERE sp_ma = ?";
        String[] args = {String.valueOf(sp_ma)};
        Cursor cursor = db.rawQuery(sql, args); // Thực hiện truy vấn
        try {
            if (cursor.moveToFirst()) {
                return cursor.getString(0);
            }
        } finally {
            cursor.close();
            db.close();
        }
        return null;
    }

    public double getSp_gia(int sp_ma) {
        SQLiteDatabase db = dbHelper.getReadableDatabase(); // Mở cơ sở dữ liệu
        String sql = "SELECT sp_gia FROM sanpham WHERE sp_ma = ?";
        String[] args = {String.valueOf(sp_ma)};
        Cursor cursor = db.rawQuery(sql, args); // Thực hiện truy vấn
        try {
            if (cursor.moveToFirst()) {
                return cursor.getDouble(0);
            }
        } finally {
            cursor.close();
            db.close();
        }
        return -1;
    }
    public Bitmap getsp_img(int sp_ma) {
        SQLiteDatabase db = dbHelper.getReadableDatabase(); // Mở cơ sở dữ liệu
        String sql = "SELECT sp_hinhanh FROM sanpham WHERE sp_ma = ?";
        String[] args = {String.valueOf(sp_ma)};
        Cursor cursor = db.rawQuery(sql, args); // Thực hiện truy vấn
        try {
            if (cursor.moveToFirst()) {
                byte[] imageBytes = cursor.getBlob(0);
                Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                return bitmap;
            }
        } finally {
            cursor.close();
            db.close();
        }
        return null;
    }

}
