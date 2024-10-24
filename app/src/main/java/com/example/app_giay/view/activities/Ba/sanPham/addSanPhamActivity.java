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
    EditText edtTenSanPham, edtGia, edtSoLuong, edtMaSanPham, edtMaNhaSanXuat, edtMoTa, edtDoiTuong;
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

        // Thêm mục "Chọn loại sản phẩm" vào đầu danh sách
        listLoaiSanPham.add(0, "Chọn loại sản phẩm");

        // Tạo adapter cho Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listLoaiSanPham);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLoaiSanPham.setAdapter(adapter);

        // Tự động chọn mục đầu tiên (mục không có gì)
        spinnerLoaiSanPham.setSelection(0);


        // Lấy danh sách nha sửa
        listNhaSanXuat = nhaSanXuatDao.getAllTenNhaSanXuat();

        // Thêm mục "Chọn nha sửa" vào đầu danh sách
        listNhaSanXuat.add(0, "Chọn nha sửa");

        // Tạo adapter cho Spinner
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listNhaSanXuat);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerNhaSanXuat.setAdapter(adapter2);

        // Tự động chọn mục đầu tiên (một)
        spinnerNhaSanXuat.setSelection(0);


        btnOpen.setOnClickListener(v -> openImageChooser());
        btnSave.setOnClickListener(v -> {
            // Lấy vị trí đã chọn từ Spinner
            int selectedPosition = spinnerLoaiSanPham.getSelectedItemPosition();

            // Kiểm tra nếu vị trí đã chọn là 0 (mục "Chọn loại sản phẩm")
            if (selectedPosition == 0) {
                // Hiển thị thông báo lỗi và dừng lại
                Toast.makeText(this, "Vui lòng chọn loại sản phẩm hợp lệ", Toast.LENGTH_SHORT).show();
                return; // Dừng không cho tiếp tục lưu sản phẩm
            }

            // Nếu đã chọn loại sản phẩm, lấy tên loại sản phẩm từ Spinner
            String lsp_ten = spinnerLoaiSanPham.getSelectedItem().toString();

            // Lấy mã loại sản phẩm dựa trên tên loại sản phẩm từ DAO
            int maSanPham = loaiSanPhamDao.getLoaiSanPhamMaByTen(lsp_ten);

            int selectedPosition2 = spinnerNhaSanXuat.getSelectedItemPosition();
            if (selectedPosition2 == 0) {
                Toast.makeText(this, "Vui lòng chọn nhà sản xuất", Toast.LENGTH_SHORT).show();
                return;
            }
            String nsx_ten = spinnerNhaSanXuat.getSelectedItem().toString();
            int maNhaSanXuat = nhaSanXuatDao.getNhaSanXuatMaByTen(nsx_ten);

            // Tiếp tục lấy thông tin sản phẩm
            String tenSanPham = edtTenSanPham.getText().toString();
            double gia = Double.parseDouble(edtGia.getText().toString());
            int soLuong = Integer.parseInt(edtSoLuong.getText().toString());
            String moTa = edtMoTa.getText().toString();
            String doiTuong = edtDoiTuong.getText().toString();

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
