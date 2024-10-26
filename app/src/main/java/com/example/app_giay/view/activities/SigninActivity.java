package com.example.app_giay.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.app_giay.R;
import com.example.app_giay.dao.UserDao;
import com.example.app_giay.view.activities.Ba.MainBAActivity;
import com.example.app_giay.view.activities.Fe.MainFEActivity;

public class SigninActivity extends AppCompatActivity {
    ImageButton imgBtnBack;
    Button btnSignin;
    UserDao userDao = new UserDao(this);
    EditText edtUsername, edtPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        btnSignin = findViewById(R.id.btnSignIn2);

        btnSignin.setOnClickListener(v -> {
            String username = edtUsername.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();
            boolean check = userDao.checkLogin(username, password);
            if (check) {
                Toast.makeText(getApplicationContext(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                int id = userDao.getRoleId(username);
                int userId = userDao.getUserId(username);
                if (id != -1) {
                    // Lưu user_id vào SharedPreferences
                    getSharedPreferences("user_prefs", MODE_PRIVATE)
                            .edit()
                            .putInt("user_id", userId)
                            .apply();

                    Intent intent;
                    if (id == 1) {
                        intent = new Intent(SigninActivity.this, MainBAActivity.class);
                    } else if (id == 2) {
                        intent = new Intent(SigninActivity.this, MainFEActivity.class);
                    } else {
                        Toast.makeText(getApplicationContext(), "Role không hợp lệ", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Không tìm thấy người dùng", Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(getApplicationContext(), "Sai username hoặc password", Toast.LENGTH_SHORT).show();
            }

        });

        imgBtnBack = findViewById(R.id.imgBtnBack);
        imgBtnBack.setOnClickListener(v -> finish());
    }
}
