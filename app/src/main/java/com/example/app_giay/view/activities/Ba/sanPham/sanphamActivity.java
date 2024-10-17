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
    private static final int REQUEST_CODE_ADD_SAN_PHAM = 1;
    private static final int REQUEST_CODE_EDIT_SAN_PHAM = 2;
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

        LoadData();

        btnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(this, addSanPhamActivity.class);
            startActivityForResult(intent, REQUEST_CODE_ADD_SAN_PHAM);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD_SAN_PHAM || requestCode == REQUEST_CODE_EDIT_SAN_PHAM) {
            if (resultCode == RESULT_OK) {
                LoadData();
            }
        }

    }
    public void LoadData() {
        data = dao.getAllSanPham();
        adapter = new sanPhamAdapter(this, R.layout.layout_list_sp_lv, data);
        lvSanPham.setAdapter(adapter);
    }
}