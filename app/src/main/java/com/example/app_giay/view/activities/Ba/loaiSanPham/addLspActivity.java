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

        // Đặt lại padding khi có sự thay đổi về hệ thống điều hướng
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Ánh xạ các thành phần giao diện
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);
        edtTenSanPham = findViewById(R.id.edtTenSanPham);
        edtMota = findViewById(R.id.edtMoTa);
        imgbtnBack = findViewById(R.id.imgbtnBack);

        // Xử lý sự kiện hủy bỏ
        btnCancel.setOnClickListener(view -> finish());

        // Xử lý sự kiện quay lại
        imgbtnBack.setOnClickListener(view -> finish());

        // Xử lý sự kiện lưu
        btnSave.setOnClickListener(view -> {
            String lsp_ten = edtTenSanPham.getText().toString().trim();
            String lsp_mota = edtMota.getText().toString().trim();

            // Kiểm tra nếu trường tên sản phẩm hoặc mô tả bị để trống
            if (TextUtils.isEmpty(lsp_ten)) {
                edtTenSanPham.setError("Tên sản phẩm không được để trống");
                return;
            }

            if (TextUtils.isEmpty(lsp_mota)) {
                edtMota.setError("Mô tả không được để trống");
                return;
            }

            // Thêm sản phẩm vào cơ sở dữ liệu
            loaiSanPhamDao.addLoaiSanPham(lsp_ten, lsp_mota);

            // Hiển thị thông báo thành công
            Toast.makeText(addLspActivity.this, "Đã thêm loại sản phẩm thành công!", Toast.LENGTH_SHORT).show();

            // Trả về kết quả thành công và đóng activity
            setResult(RESULT_OK);
            finish();
        });
    }
}
