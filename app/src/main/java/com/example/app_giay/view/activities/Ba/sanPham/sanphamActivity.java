package com.example.app_giay.view.activities.Ba.sanPham;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.app_giay.R;
import com.example.app_giay.adapter.sanPhamAdapter;
import com.example.app_giay.dao.SanPhamDao;
import com.example.app_giay.model.SanPham;

import java.util.ArrayList;

public class sanphamActivity extends AppCompatActivity {
    ImageButton btnAdd, btnBack;
    ListView lvSanPham;
    sanPhamAdapter adapter;
    ArrayList<SanPham> data = new ArrayList<>();
    SanPhamDao dao = new SanPhamDao(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sanpham);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btnBack = findViewById(R.id.imgbtnBack);
        btnBack.setOnClickListener(v -> finish());
        btnAdd = findViewById(R.id.imgbtnAdd);
        lvSanPham = findViewById(R.id.lvSanPham);

        data = dao.getAllSanPham();
        adapter = new sanPhamAdapter(this, R.layout.layout_list_sp_lv, data);
        lvSanPham.setAdapter(adapter);

        btnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(this, addSanPhamActivity.class);
            startActivity(intent);
        });
    }
}