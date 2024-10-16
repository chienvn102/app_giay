package com.example.app_giay.view.activities.Ba.sanPham;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.app_giay.R;
import com.example.app_giay.dao.SanPhamDao;

import java.io.ByteArrayOutputStream;

public class addSanPhamActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1; // Mã yêu cầu để mở thư viện
    Button btnSave, btnCancel;
    ImageButton btnOpen, btnBack;
    ImageView imgSanPham;
    EditText edtTenSanPham, edtGia, edtNgayCapNhat, edtSoLuong, edtMaSanPham, edtMaNhaSanXuat, edtMoTa, edtDoiTuong;
    SanPhamDao dao = new SanPhamDao(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_san_pham);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);
        btnOpen = findViewById(R.id.imageButton);
        btnBack = findViewById(R.id.imgbtnBack);
        imgSanPham = findViewById(R.id.imgSanPham);
        edtTenSanPham = findViewById(R.id.edtTenSanPham);
        edtGia = findViewById(R.id.edtGiaSanPham);
        edtNgayCapNhat = findViewById(R.id.edtNgayCapNhat);
        edtSoLuong = findViewById(R.id.edtSoLuongSanPham);
        edtMaSanPham = findViewById(R.id.edtMaLoaiSanPham);
        edtMaNhaSanXuat = findViewById(R.id.edtMaNSX);
        edtMoTa = findViewById(R.id.edtMoTaSanPham);
        edtDoiTuong = findViewById(R.id.edtDoiTuongSanPham);

        btnBack.setOnClickListener(v -> finish());
        btnCancel.setOnClickListener(v -> finish());

        btnOpen.setOnClickListener(v -> openImageChooser());
        btnSave.setOnClickListener(v -> {
            String tenSanPham = edtTenSanPham.getText().toString();
            double gia = Double.parseDouble(edtGia.getText().toString());
            String ngayCapNhat = edtNgayCapNhat.getText().toString();
            int soLuong = Integer.parseInt(edtSoLuong.getText().toString());
            int maSanPham = Integer.parseInt(edtMaSanPham.getText().toString());
            int maNhaSanXuat = Integer.parseInt(edtMaNhaSanXuat.getText().toString());
            String moTa = edtMoTa.getText().toString();
            String doiTuong = edtDoiTuong.getText().toString();

            // Lấy bitmap từ ImageView và chuyển đổi thành byte[]
            Bitmap bitmap = ((BitmapDrawable) imgSanPham.getDrawable()).getBitmap();
            byte[] imageData = convertBitmapToByteArray(bitmap);

            // Gọi phương thức thêm sản phẩm
            dao.addSanPham(tenSanPham, gia, ngayCapNhat, soLuong, maSanPham, maNhaSanXuat, imageData, moTa, doiTuong);
            finish();
        });
    }

    private void openImageChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData(); // Lấy URI của ảnh
            try {
                // Chuyển đổi URI thành Bitmap
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                imgSanPham.setImageBitmap(bitmap); // Hiển thị ảnh trong ImageView
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Phương thức chuyển đổi Bitmap thành byte[]
    private byte[] convertBitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream); // Chất lượng nén (0-100)
        return stream.toByteArray();
    }
}
