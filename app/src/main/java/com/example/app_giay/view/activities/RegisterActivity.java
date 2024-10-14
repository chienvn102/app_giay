package com.example.app_giay.view.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.app_giay.R;
import com.example.app_giay.dao.RoleDao;
import com.example.app_giay.dao.UserDao;
import com.example.app_giay.model.User;

public class RegisterActivity extends AppCompatActivity {
    ImageButton imgBtnBack;
    Button btnCreate;
    EditText edtUsername, edtPassword, edtYourname;
    UserDao userDao = new UserDao(this);
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
        btnCreate.setOnClickListener(v -> {
            addUser();
            setResult(RESULT_OK);
            finish();
        });
    }
    public void addUser() {
        String username = edtUsername.getText().toString();
        String password = edtPassword.getText().toString();
        String yourname = edtYourname.getText().toString();
        userDao.addUser(username, password,2, yourname);
    }
}