package com.example.app_giay.view.activities.Ba.loaiSanPham;

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
import com.example.app_giay.dao.loaiSanPhamDao;

public class addLspActivity extends AppCompatActivity {
    Button btnSave, btnCancel;
    ImageButton imgbtnBack;
    EditText edtTenSanPham, edtMota;
    com.example.app_giay.dao.loaiSanPhamDao loaiSanPhamDao = new loaiSanPhamDao(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_lsp);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);
        edtTenSanPham = findViewById(R.id.edtTenSanPham);
        edtMota = findViewById(R.id.edtMoTa);
        btnCancel.setOnClickListener(view -> {
            finish();
        });
        imgbtnBack = findViewById(R.id.imgbtnBack);
        imgbtnBack.setOnClickListener(view -> {
            finish();
        });
        btnSave.setOnClickListener(view -> {
            String lsp_ten = edtTenSanPham.getText().toString();
            String lsp_mota = edtMota.getText().toString();
            loaiSanPhamDao.addLoaiSanPham(lsp_ten, lsp_mota);
            setResult(RESULT_OK);
            finish();
        });
    }
}