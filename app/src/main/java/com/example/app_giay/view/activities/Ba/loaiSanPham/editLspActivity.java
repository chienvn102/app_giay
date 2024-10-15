package com.example.app_giay.view.activities.Ba.loaiSanPham;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.app_giay.R;
import com.example.app_giay.dao.loaiSanPhamDao;

public class editLspActivity extends AppCompatActivity {
    EditText edtTenLsp, edtMota;
    loaiSanPhamDao dao = new loaiSanPhamDao(this);
    Button btnSave, btnCancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_lsp);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        edtTenLsp = findViewById(R.id.edtTenSanPham);
        edtMota = findViewById(R.id.edtMoTa);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(view -> {
            finish();
        });
        btnSave.setOnClickListener(view -> {
            String lsp_ten = edtTenLsp.getText().toString();
            String lsp_mota = edtMota.getText().toString();
            dao.updateLoaiSanPham(getIntent().getIntExtra("lsp_ma", 0),lsp_ten, lsp_mota);
            setResult(RESULT_OK);
            finish();
        });
        edtTenLsp.setText(getIntent().getStringExtra("lsp_ten"));
        edtMota.setText(getIntent().getStringExtra("lsp_mota"));


    }
}