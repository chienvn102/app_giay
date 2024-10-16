package com.example.app_giay.dao;

import android.content.Context;
import android.database.Cursor;

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
        String sql = "INSERT INTO nhaSanXuat (nsx_ten) VALUES (?)";
        Object[] args = {nsx_ten};
        nhaSanXuatdb.getWritableDatabase().execSQL(sql, args);
    }

    public void deleteNhaSanXuat(int nsx_ma) {
        String sql = "DELETE FROM nhaSanXuat WHERE nsx_ma = ?";
        Object[] args = {nsx_ma};
        nhaSanXuatdb.getWritableDatabase().execSQL(sql, args);
    }

    public void updateNhaSanXuat(int nsx_ma, String nsx_ten) {
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
}
