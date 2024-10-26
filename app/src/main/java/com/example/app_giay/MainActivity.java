package com.example.app_giay;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.app_giay.dao.DonDatHangDAO;
import com.example.app_giay.dao.RoleDao;
import com.example.app_giay.dao.UserDao;
import com.example.app_giay.database.DatabaseHelper;
import com.example.app_giay.view.activities.Ba.MainBAActivity;
import com.example.app_giay.view.activities.RegisterActivity;
import com.example.app_giay.view.activities.SigninActivity;
import com.example.app_giay.dao.cartDao;
import com.example.app_giay.dao.trangThaiDao;

public class MainActivity extends AppCompatActivity {
    Button btnSignin, btnRegister;
//        RoleDao roleDao = new RoleDao(this); // Tạo đối tượng RoleDao
//        UserDao userDao = new UserDao(this);
        trangThaiDao trangThaiDao = new trangThaiDao(this);

    DatabaseHelper dbHelper = new DatabaseHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btnSignin = findViewById(R.id.btnSignin);
        btnSignin.setOnClickListener(v -> signin());  // Gọi hàm signin khi nhấn nút
        btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(v -> register());  // Gọi hàm register khi nhấn nút
    }

    public void signin() {
       Intent intent = new Intent(this, SigninActivity.class);
       startActivity(intent);
//         roleDao.addRole("Admin");
//         roleDao.addRole("User");
//         userDao.addUser("admin", "admin", 1, "admin");
//         userDao.addUser("user", "user", 2, "user");
//           trangThaiDao.addTrangThai("Da Huy Don Hang");
//           trangThaiDao.addTrangThai("Dang Xac Nhan");
//           trangThaiDao.addTrangThai("Dang Giao Hang");
//           trangThaiDao.addTrangThai("Giao Hang Thanh Cong");
    }

    public void register() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

}