package com.example.app_giay.view.activities.Ba.nhaSanXuat;

import android.annotation.SuppressLint;
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
import com.example.app_giay.dao.nhaSanXuatDao; // Cập nhật DAO


public class editNsxActivity extends AppCompatActivity {
    ImageButton imgbtnBack;
    EditText edtTenNsx; // Đổi tên biến để phản ánh nhà sản xuất
    nhaSanXuatDao dao = new nhaSanXuatDao(this);
    Button btnSave, btnCancel;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_nsx); // Cập nhật layout resource

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edtTenNsx = findViewById(R.id.edtkh_hoten); // Cập nhật ID
        btnSave = findViewById(R.id.btnSave);
        imgbtnBack = findViewById(R.id.imgbtnBack);
        imgbtnBack.setOnClickListener(v -> finish());
        btnCancel = findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(view -> finish());
        edtTenNsx.setText(getIntent().getStringExtra("nsx_ten"));

        // Xử lý sự kiện khi nhấn nút "Lưu"
        btnSave.setOnClickListener(view -> {
            String nsx_ten = edtTenNsx.getText().toString().trim();

            // Kiểm tra nếu trường tên nhà sản xuất bị để trống
            if (TextUtils.isEmpty(nsx_ten)) {
                edtTenNsx.setError("Tên nhà sản xuất không được để trống");
                return;
            }


            // Cập nhật thông tin sản phẩm vào cơ sở dữ liệu
            dao.updateNhaSanXuat(getIntent().getIntExtra("nsx_ma", 0), nsx_ten);

            // Hiển thị thông báo thành công
            Toast.makeText(editNsxActivity.this, "Cập nhật nhà sản xuất thành công!", Toast.LENGTH_SHORT).show();

            // Trả về kết quả thành công và đóng activity
            setResult(RESULT_OK);
            finish();
        });
    }
}

