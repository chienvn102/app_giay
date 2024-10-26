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
import com.example.app_giay.dao.RoleDao;
import com.example.app_giay.dao.UserDao;

public class RegisterActivity extends AppCompatActivity {
    ImageButton imgBtnBack;
    Button btnCreate, btnSignIn;
    EditText edtUsername, edtPassword, edtYourname;
    UserDao userDao = new UserDao(this);
    RoleDao roleDao = new RoleDao(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        edtYourname = findViewById(R.id.edtYourname);
        imgBtnBack = findViewById(R.id.imgBtnBack);
        imgBtnBack.setOnClickListener(v -> finish());
        btnCreate = findViewById(R.id.btnCreateAccount);
        btnSignIn = findViewById(R.id.btnSignIn);
        btnSignIn.setOnClickListener(v -> {
            Intent intent = new Intent(this, SigninActivity.class);
            startActivity(intent);
            finish();
        });
        btnCreate.setOnClickListener(v -> {
            String password = edtPassword.getText().toString();
            if (checkUsername(edtUsername.getText().toString())) {
                Toast.makeText(getApplicationContext(), "Tên đăng nhập đã tồn tại", Toast.LENGTH_SHORT).show();
            } else if (password.length() <= 8) {
                Toast.makeText(getApplicationContext(), "Mật khẩu phải có hơn 8 ký tự", Toast.LENGTH_SHORT).show();
            } else {

                addUser();
                Toast.makeText(getApplicationContext(), "Tạo tài khoản thành công", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, SigninActivity.class);
                startActivity(intent);
                finish();
            }

        });
    }
    public boolean checkUsername(String username) {
        return userDao.checkUsernameExists(username);
    }
    public void addUser() {
        String username = edtUsername.getText().toString();
        String password = edtPassword.getText().toString();
        String yourname = edtYourname.getText().toString();
        userDao.addUser(username, password,2, yourname);
    }
}