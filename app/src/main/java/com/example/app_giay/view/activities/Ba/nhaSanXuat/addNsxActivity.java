package com.example.app_giay.view.activities.Ba.nhaSanXuat; // Update the package path

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
import com.example.app_giay.dao.nhaSanXuatDao; // Update DAO import
import com.example.app_giay.view.activities.Ba.loaiSanPham.addLspActivity;

public class addNsxActivity extends AppCompatActivity { // Rename class to addNsxActivity
    Button btnSave, btnCancel;
    ImageButton imgbtnBack;
    EditText edtTenNhaSanXuat;
    com.example.app_giay.dao.nhaSanXuatDao nhaSanXuatDao = new nhaSanXuatDao(this);// Update EditText variable names

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_nsx); // Update layout resource if necessary
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);
        edtTenNhaSanXuat = findViewById(R.id.edtTenNhaSanXuat); // Update EditText ID
        btnCancel.setOnClickListener(view -> {
            finish();
        });
        imgbtnBack = findViewById(R.id.imgbtnBack);
        imgbtnBack.setOnClickListener(view -> {
            finish();
        });

        btnSave.setOnClickListener(view -> {
            String nsx_ten = edtTenNhaSanXuat.getText().toString().trim();

            // Kiểm tra nếu trường tên sản phẩm hoặc mô tả bị để trống
            if (TextUtils.isEmpty(nsx_ten)) {
                edtTenNhaSanXuat.setError("Tên nhà sản xuất không được để trống");
                return;
            }

            // Thêm nhà sản xuất vào cơ sở dữ liệu
            nhaSanXuatDao.addNhaSanXuat(nsx_ten);

            // Hiển thị thông báo thành công
            Toast.makeText(addNsxActivity.this, "Đã thêm nhà sản xuất thành công!", Toast.LENGTH_SHORT).show();

            // Trả về kết quả thành công và đóng activity
            setResult(RESULT_OK);
            finish();
        });

    }
}
