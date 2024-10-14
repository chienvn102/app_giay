package com.example.app_giay;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.app_giay.view.activities.RegisterActivity;
import com.example.app_giay.view.activities.SigninActivity;

public class MainActivity extends AppCompatActivity {
    Button btnSignin, btnRegister;
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
        // Chỉ thực hiện điều hướng ở đây, không đặt lại OnClickListener
        Intent intent = new Intent(this, SigninActivity.class);
        startActivity(intent);
    }

    public void register() {
        // Chỉ thực hiện điều hướng ở đây, không đặt lại OnClickListener
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

}