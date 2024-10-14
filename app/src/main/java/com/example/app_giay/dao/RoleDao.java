package com.example.app_giay.dao;

import android.content.Context;

import com.example.app_giay.database.DatabaseHelper;

public class RoleDao {
    private DatabaseHelper dbHelper;

    public RoleDao(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void addRole(String roleName) {
        dbHelper.getWritableDatabase();
        String sql = "INSERT INTO roles (role_name) VALUES (?)";
        Object[] args = {roleName};
        dbHelper.getWritableDatabase().execSQL(sql, args);
    }
}
