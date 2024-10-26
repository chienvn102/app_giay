package com.example.app_giay.dao;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.app_giay.database.DatabaseHelper;
import com.example.app_giay.model.cart;

import java.util.ArrayList;

public class cartDao {
    private DatabaseHelper dbHelper;

    public cartDao(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void addToCart(int users_id, int sp_ma, int sp_soluong) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String checkSql = "SELECT sp_soluong FROM cart WHERE users_id = ? AND sp_ma = ?";
        Cursor cursor = db.rawQuery(checkSql, new String[]{String.valueOf(users_id), String.valueOf(sp_ma)});

        if (cursor.moveToFirst()) {
            // Sản phẩm đã tồn tại trong giỏ hàng, cộng số lượng mới với số lượng cũ
            int currentQuantity = cursor.getInt(0);
            int newQuantity = currentQuantity + sp_soluong;

            // Cập nhật số lượng sản phẩm
            String updateSql = "UPDATE cart SET sp_soluong = ? WHERE users_id = ? AND sp_ma = ?";
            db.execSQL(updateSql, new Object[]{newQuantity, users_id, sp_ma});
        } else {
            // Sản phẩm chưa tồn tại trong giỏ hàng, thêm mới
            String insertSql = "INSERT INTO cart (users_id, sp_ma, sp_soluong) VALUES (?, ?, ?)";
            db.execSQL(insertSql, new Object[]{users_id, sp_ma, sp_soluong});
        }
        cursor.close();
        db.close();
    }

    public ArrayList<cart> getAllCart() {
        ArrayList<cart> cartItems = new ArrayList<>();
        String sql = "SELECT * FROM cart";
        Cursor cursor = dbHelper.getReadableDatabase().rawQuery(sql, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") int users_id = cursor.getInt(cursor.getColumnIndex("users_id"));
                @SuppressLint("Range") int sp_ma = cursor.getInt(cursor.getColumnIndex("sp_ma"));
                @SuppressLint("Range") int sp_soluong = cursor.getInt(cursor.getColumnIndex("sp_soluong"));
                cart cartItem = new cart();
                cartItem.setUsers_id(users_id);
                cartItem.setSp_ma(sp_ma);
                cartItem.setSp_soluong(sp_soluong);
                cartItems.add(cartItem);
            }
            cursor.close();
        }
        return cartItems;

    }
    public ArrayList<cart> getCartItems(int users_id) {
        ArrayList<cart> cartItems = new ArrayList<>();
        String sql = "SELECT * FROM cart WHERE users_id = ?";
        String[] args = {String.valueOf(users_id)};
        Cursor cursor = dbHelper.getReadableDatabase().rawQuery(sql, args);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") int sp_ma = cursor.getInt(cursor.getColumnIndex("sp_ma"));
                @SuppressLint("Range") int sp_soluong = cursor.getInt(cursor.getColumnIndex("sp_soluong"));
                cart cartItem = new cart();
                cartItem.setUsers_id(users_id);
                cartItem.setSp_ma(sp_ma);
                cartItem.setSp_soluong(sp_soluong);
                cartItems.add(cartItem);
            }
            cursor.close();
        }
        return cartItems;
    }
    public void deleteCartItem(int users_id, int sp_ma) {
        String sql = "DELETE FROM cart WHERE users_id = ? AND sp_ma = ?";
        String[] args = {String.valueOf(users_id), String.valueOf(sp_ma)};
        dbHelper.getWritableDatabase().execSQL(sql, args);
    }
    public void deleteAllCartItems() {
        String sql = "DELETE FROM cart";
        dbHelper.getWritableDatabase().execSQL(sql);

    }
    public void updateCartItemQuantity(int users_id, int sp_ma, int sp_soluong) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "UPDATE cart SET sp_soluong = ? WHERE users_id = ? AND sp_ma = ?";
        Object[] args = {sp_soluong, users_id, sp_ma};
        db.execSQL(sql, args);
    }
    public ArrayList<cart> getCartItemsByUserId(int userId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ArrayList<cart> cartItems = new ArrayList<>();
        String sql = "SELECT sp_ma, sp_soluong FROM cart WHERE users_id = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(userId)});

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int spMa = cursor.getInt(cursor.getColumnIndex("sp_ma"));
                @SuppressLint("Range") int spSoLuong = cursor.getInt(cursor.getColumnIndex("sp_soluong"));
                cart cartItem = new cart();
                cartItem.setSp_ma(spMa);
                cartItem.setSp_soluong(spSoLuong);
                cartItem.setUsers_id(userId);
                cartItems.add(cartItem);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return cartItems;
    }


}
