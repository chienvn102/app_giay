package com.example.app_giay.view.activities.Ba.loaiSanPham;

import android.os.Bundle;
import android.text.TextUtils;
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
import com.example.app_giay.dao.loaiSanPhamDao;

public class editLspActivity extends AppCompatActivity {
    EditText edtTenLsp, edtMota;
    loaiSanPhamDao dao = new loaiSanPhamDao(this);
    Button btnSave, btnCancel;
    ImageButton imgbtnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_lsp);

        // Đặt lại padding khi có sự thay đổi về hệ thống điều hướng
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Ánh xạ các thành phần giao diện
        edtTenLsp = findViewById(R.id.edtTenSanPham);
        edtMota = findViewById(R.id.edtMoTa);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);
        imgbtnBack = findViewById(R.id.imgbtnBack);
        imgbtnBack.setOnClickListener(v -> finish());
        // Cài đặt dữ liệu hiện có vào các EditText
        edtTenLsp.setText(getIntent().getStringExtra("lsp_ten"));
        edtMota.setText(getIntent().getStringExtra("lsp_mota"));

        // Xử lý sự kiện khi nhấn nút "Hủy"
        btnCancel.setOnClickListener(view -> finish());

        // Xử lý sự kiện khi nhấn nút "Lưu"
        btnSave.setOnClickListener(view -> {
            String lsp_ten = edtTenLsp.getText().toString().trim();
            String lsp_mota = edtMota.getText().toString().trim();

            // Kiểm tra nếu trường tên sản phẩm hoặc mô tả bị để trống
            if (TextUtils.isEmpty(lsp_ten)) {
                edtTenLsp.setError("Tên sản phẩm không được để trống");
                return;
            }

            if (TextUtils.isEmpty(lsp_mota)) {
                edtMota.setError("Mô tả không được để trống");
                return;
            }

            // Cập nhật thông tin sản phẩm vào cơ sở dữ liệu
            dao.updateLoaiSanPham(getIntent().getIntExtra("lsp_ma", 0), lsp_ten, lsp_mota);

            // Hiển thị thông báo thành công
            Toast.makeText(editLspActivity.this, "Cập nhật loại sản phẩm thành công!", Toast.LENGTH_SHORT).show();

            // Trả về kết quả thành công và đóng activity
            setResult(RESULT_OK);
            finish();
        });
    }
}
