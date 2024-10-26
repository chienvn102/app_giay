package com.example.app_giay.dao;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;

import com.example.app_giay.database.DatabaseHelper;
import com.example.app_giay.model.cart;

import java.util.ArrayList;

public class cartDao {
    private DatabaseHelper dbHelper;

    public cartDao(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void addToCart(int users_id, int sp_ma, int sp_soluong) {
        dbHelper.getWritableDatabase();
        String sql = "INSERT INTO cart (users_id, sp_ma, sp_soluong) VALUES (?, ?, ?)";
        Object[] args = {users_id, sp_ma, sp_soluong};
        dbHelper.getWritableDatabase().execSQL(sql, args);
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
}
