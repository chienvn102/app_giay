package com.example.app_giay.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
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
        EdgeToEdge.enable(this);
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
                if (id != -1) { // Kiểm tra nếu role_id hợp lệ
                    Intent intent;
                    if (id == 1) {
                        intent = new Intent(SigninActivity.this, MainBAActivity.class);
                    } else if (id == 2) {
                        intent = new Intent(SigninActivity.this, MainFEActivity.class);
                    } else {
                        // Xử lý trường hợp role_id không hợp lệ
                        Toast.makeText(getApplicationContext(), "Role không hợp lệ", Toast.LENGTH_SHORT).show();
                        return; // Dừng thực hiện nếu role không hợp lệ
                    }
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Không tìm thấy người dùng", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Tên người dùng hoặc mật khẩu không chính xác", Toast.LENGTH_SHORT).show();
            }
        });



        imgBtnBack = findViewById(R.id.imgBtnBack);
        imgBtnBack.setOnClickListener(v -> finish());
    }
}