package com.example.app_giay.view.activities.Ba.sanPham;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.app_giay.R;
import com.example.app_giay.dao.SanPhamDao;
import com.example.app_giay.dao.loaiSanPhamDao;
import com.example.app_giay.dao.nhaSanXuatDao;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class addSanPhamActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1; // Mã yêu cầu để mở thư viện
    Button btnSave, btnCancel;
    ImageButton btnOpen, btnBack;
    ImageView imgSanPham;
    EditText edtTenSanPham, edtGia, edtSoLuong, edtMoTa, edtDoiTuong;
    SanPhamDao dao = new SanPhamDao(this);
    loaiSanPhamDao loaiSanPhamDao = new loaiSanPhamDao(this);
    nhaSanXuatDao nhaSanXuatDao = new nhaSanXuatDao(this);
    Spinner spinnerLoaiSanPham, spinnerNhaSanXuat;
    ArrayList<String> listLoaiSanPham = new ArrayList<>();
    ArrayList<String> listNhaSanXuat = new ArrayList<>();

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

        // Khởi tạo các thành phần giao diện
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);
        btnOpen = findViewById(R.id.imageButton);
        btnBack = findViewById(R.id.imgbtnBack);
        imgSanPham = findViewById(R.id.imgSanPham);
        edtTenSanPham = findViewById(R.id.edtTenSanPham);
        edtGia = findViewById(R.id.edtGiaSanPham);
        edtSoLuong = findViewById(R.id.edtSoLuongSanPham);
        spinnerLoaiSanPham = findViewById(R.id.spinnerLoaiSanPham);
        spinnerNhaSanXuat = findViewById(R.id.spinnerNhaSanXuat);
        edtMoTa = findViewById(R.id.edtMoTaSanPham);
        edtDoiTuong = findViewById(R.id.edtDoiTuongSanPham);

        btnBack.setOnClickListener(v -> finish());
        btnCancel.setOnClickListener(v -> finish());

        // Lấy danh sách loại sản phẩm
        listLoaiSanPham = loaiSanPhamDao.getAllTenLoaiSanPham();
        listLoaiSanPham.add(0, "Chọn loại sản phẩm");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listLoaiSanPham);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLoaiSanPham.setAdapter(adapter);
        spinnerLoaiSanPham.setSelection(0);

        // Lấy danh sách nhà sản xuất
        listNhaSanXuat = nhaSanXuatDao.getAllTenNhaSanXuat();
        listNhaSanXuat.add(0, "Chọn nhà sản xuất");
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listNhaSanXuat);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerNhaSanXuat.setAdapter(adapter2);
        spinnerNhaSanXuat.setSelection(0);

        btnOpen.setOnClickListener(v -> openImageChooser());
        btnSave.setOnClickListener(v -> validateAndSave());
    }

    private void validateAndSave() {
        // Kiểm tra thông tin sản phẩm
        String tenSanPham = edtTenSanPham.getText().toString();
        if (tenSanPham.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập tên sản phẩm", Toast.LENGTH_SHORT).show();
            return;
        }

        String giaText = edtGia.getText().toString();
        if (giaText.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập giá sản phẩm", Toast.LENGTH_SHORT).show();
            return;
        }
        double gia;
        try {
            gia = Double.parseDouble(giaText);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Giá sản phẩm không hợp lệ", Toast.LENGTH_SHORT).show();
            return;
        }

        String soLuongText = edtSoLuong.getText().toString();
        if (soLuongText.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập số lượng sản phẩm", Toast.LENGTH_SHORT).show();
            return;
        }
        int soLuong;
        try {
            soLuong = Integer.parseInt(soLuongText);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Số lượng sản phẩm không hợp lệ", Toast.LENGTH_SHORT).show();
            return;
        }

        // Kiểm tra loại sản phẩm
        int selectedPosition = spinnerLoaiSanPham.getSelectedItemPosition();
        if (selectedPosition == 0) {
            Toast.makeText(this, "Vui lòng chọn loại sản phẩm hợp lệ", Toast.LENGTH_SHORT).show();
            return;
        }
        String lsp_ten = spinnerLoaiSanPham.getSelectedItem().toString();
        int maSanPham = loaiSanPhamDao.getLoaiSanPhamMaByTen(lsp_ten);

        // Kiểm tra nhà sản xuất
        int selectedPosition2 = spinnerNhaSanXuat.getSelectedItemPosition();
        if (selectedPosition2 == 0) {
            Toast.makeText(this, "Vui lòng chọn nhà sản xuất hợp lệ", Toast.LENGTH_SHORT).show();
            return;
        }
        String moTa = edtMoTa.getText().toString();
        if (moTa.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập mô tả sản phẩm", Toast.LENGTH_SHORT).show();
            return;
        }
        String doiTuong = edtDoiTuong.getText().toString();
        if (doiTuong.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đối tượng sử dụng sản phẩm", Toast.LENGTH_SHORT).show();
            return;
        }
        String nsx_ten = spinnerNhaSanXuat.getSelectedItem().toString();
        int maNhaSanXuat = nhaSanXuatDao.getNhaSanXuatMaByTen(nsx_ten);



        // Kiểm tra hình ảnh
        if (imgSanPham.getDrawable() == null) {
            Toast.makeText(this, "Vui lòng chọn hình ảnh sản phẩm", Toast.LENGTH_SHORT).show();
            return;
        }

        // Lấy bitmap từ ImageView và chuyển đổi thành byte[]
        Bitmap bitmap = ((BitmapDrawable) imgSanPham.getDrawable()).getBitmap();
        byte[] imageData = convertBitmapToByteArray(bitmap);

        // Lấy ngày hiện tại (ngày cập nhật)
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String ngayCapNhat = sdf.format(new Date());

        // Gọi phương thức thêm sản phẩm
        dao.addSanPham(tenSanPham, gia, ngayCapNhat, soLuong, maSanPham, maNhaSanXuat, imageData, moTa, doiTuong);

        // Đóng màn hình hiện tại
        setResult(RESULT_OK);
        finish();
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
