package com.example.app_giay.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.app_giay.database.DatabaseHelper;
import com.example.app_giay.model.OrderWithProducts;
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

        String query = "SELECT sp.sp_ten, sp.sp_gia, sp.sp_hinhanh, sp.sp_mota, ddh.dh_noigiao, ddh.dh_ma, ddh.tt_ma " +
                "FROM sanpham_dondathang sp_ddh " +
                "JOIN sanpham sp ON sp_ddh.sp_ma = sp.sp_ma " +
                "JOIN dondathang ddh ON sp_ddh.dh_ma = ddh.dh_ma";

        Cursor cursor = db.rawQuery(query, null); // No parameters needed

        if (cursor != null) {
            while (cursor.moveToNext()) {
                String spTen = cursor.getString(cursor.getColumnIndexOrThrow("sp_ten"));
                double spGia = cursor.getDouble(cursor.getColumnIndexOrThrow("sp_gia"));
                byte[] spHinhAnh = cursor.getBlob(cursor.getColumnIndexOrThrow("sp_hinhanh"));
                String spMoTa = cursor.getString(cursor.getColumnIndexOrThrow("sp_mota"));
                String dhNoiGiao = cursor.getString(cursor.getColumnIndexOrThrow("dh_noigiao"));
                String dhMa = cursor.getString(cursor.getColumnIndexOrThrow("dh_ma"));
                int ttMa = cursor.getInt(cursor.getColumnIndexOrThrow("tt_ma"));

                // Tạo một đối tượng donhang cho sản phẩm hiện tại
                donhang product = new donhang(spTen, spGia, spHinhAnh, spMoTa, dhNoiGiao, dhMa, ttMa);

                // Kiểm tra xem đơn hàng đã tồn tại chưa
                donhang existingOrder = null;
                for (donhang order : donHangList) {
                    if (order.getDhMa().equals(dhMa)) {
                        existingOrder = order;
                        break;
                    }
                }

                // Nếu đã tồn tại, có thể cập nhật sản phẩm vào đơn hàng hoặc làm gì đó khác
                if (existingOrder != null) {
                    // Cập nhật thông tin của existingOrder nếu cần, hoặc thêm sản phẩm vào đơn hàng
                    // existingOrder.addProduct(product); // Nếu bạn có phương thức để thêm sản phẩm
                } else {
                    // Nếu không tồn tại, thêm đơn hàng mới vào danh sách
                    donHangList.add(product);
                }
            }
            cursor.close();
        }

        db.close();
        return donHangList;
    }

    public ArrayList<OrderWithProducts> ThongTinAllDonHang(int userId) {
        ArrayList<OrderWithProducts> orderList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query = "SELECT sp.sp_ten, sp.sp_gia, sp.sp_hinhanh, sp.sp_mota, ddh.dh_noigiao, ddh.dh_ma,ddh.tt_ma " +
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

                donhang product = new donhang(spTen, spGia, spHinhAnh, spMoTa, dhNoiGiao, dhMa, ttMa);

                // Kiểm tra xem đơn hàng đã tồn tại chưa
                OrderWithProducts existingOrder = null;
                for (OrderWithProducts order : orderList) {
                    if (order.getDhMa().equals(dhMa)) {
                        existingOrder = order;
                        break;
                    }
                }

                // Nếu đã tồn tại, thêm sản phẩm vào; nếu không, tạo đơn hàng mới
                if (existingOrder != null) {
                    existingOrder.addProduct(product);
                } else {
                    OrderWithProducts newOrder = new OrderWithProducts(dhMa, dhNoiGiao);
                    newOrder.addProduct(product);
                    orderList.add(newOrder);
                }
            }
            cursor.close();
        }

        db.close();
        return orderList;
    }

}
