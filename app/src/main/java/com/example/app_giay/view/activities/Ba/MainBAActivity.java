package com.example.app_giay.view.activities.Ba;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.app_giay.R;
import com.example.app_giay.view.activities.Ba.loaiSanPham.lspActivity;
import com.example.app_giay.view.activities.Ba.nhaSanXuat.nsxActivity;
import com.example.app_giay.view.activities.Ba.sanPham.sanphamActivity;

public class MainBAActivity extends AppCompatActivity {
    ImageButton imgbtnlsp, imgbtnnsx, imgbtnsp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_baactivity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        imgbtnlsp = findViewById(R.id.imgbtnlsp);
        imgbtnnsx = findViewById(R.id.imgbtnnsx);
        imgbtnsp = findViewById(R.id.imgbtnsp);
        imgbtnlsp.setOnClickListener(v -> {
            Intent intent = new Intent(this, lspActivity.class);
            startActivity(intent);
        });
        imgbtnnsx.setOnClickListener(v -> {
            Intent intent = new Intent(this, nsxActivity.class);
            startActivity(intent);
        });

        imgbtnsp.setOnClickListener(v -> {
            Intent intent = new Intent(this, sanphamActivity.class);
            startActivity(intent);
        });
    }
}