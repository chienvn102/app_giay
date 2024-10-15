package com.example.app_giay.view.activities.Ba.loaiSanPham;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.app_giay.R;
import com.example.app_giay.adapter.loaiSanPhamAdapter;
import com.example.app_giay.dao.loaiSanPhamDao;
import com.example.app_giay.model.loaiSanPham;

import java.util.ArrayList;

public class lspActivity extends AppCompatActivity {
    ImageButton imgbtnBack, imgbtnAdd;
    ListView lvLsp;
    ArrayList<loaiSanPham> data = new ArrayList<>();
    loaiSanPhamAdapter adapter;
    loaiSanPhamDao dao = new loaiSanPhamDao(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lsp);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        imgbtnBack = findViewById(R.id.imgbtnBack);
        imgbtnBack.setOnClickListener(v -> finish());
        lvLsp = findViewById(R.id.lvLsp);
        imgbtnAdd = findViewById(R.id.imgbtnAdd);
        imgbtnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(lspActivity.this, addLspActivity.class);
            startActivity(intent);
        });
        data = dao.getAllLoaiSanPham();
        adapter = new loaiSanPhamAdapter(this, R.layout.layout_list_lsp, data);
        lvLsp.setAdapter(adapter);

    }


}