package com.example.app_giay.view.activities.Ba.sanPham;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.app_giay.R;
import com.example.app_giay.dao.SanPhamDao;
import com.example.app_giay.dao.loaiSanPhamDao;
import com.example.app_giay.dao.nhaSanXuatDao;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Date;

public class editSanPhamActivity extends AppCompatActivity {
    private EditText edtTenSanPham, edtGiaSanPham, edtSoLuong, edtMoTa, edtDoiTuong;
    ImageButton btnBack;
    Button btnCanel;
    private ImageView imgSanPham;
    private Spinner spinnerLoaiSanPham, spinnerNhaSanXuat;
    private loaiSanPhamDao loaiSanPhamDao;
    private nhaSanXuatDao nhaSanXuatDao;
    private SanPhamDao sanPhamDao;
    private ArrayList<String> listLoaiSanPham = new ArrayList<>();
    private ArrayList<String> listNhaSanXuat = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_san_pham);
        initViews();
        initDao();
        initSpinners();
        populateFieldsFromIntent();
        setUpImagePicker();
        setUpSaveButton();
    }

    private void initViews() {
        edtTenSanPham = findViewById(R.id.edtTenSanPham);
        edtGiaSanPham = findViewById(R.id.edtGiaSanPham);
        edtSoLuong = findViewById(R.id.edtSoLuongSanPham);
        edtMoTa = findViewById(R.id.edtMoTaSanPham);
        edtDoiTuong = findViewById(R.id.edtDoiTuongSanPham);
        imgSanPham = findViewById(R.id.imgSanPham);
        spinnerLoaiSanPham = findViewById(R.id.spinnerLoaiSanPham);
        spinnerNhaSanXuat = findViewById(R.id.spinnerNhaSanXuat);
        btnBack = findViewById(R.id.imgbtnBack);
        btnBack.setOnClickListener(v -> finish());
        btnCanel = findViewById(R.id.btnCancel);
        btnCanel.setOnClickListener(v -> finish());
    }

    private void initDao() {
        loaiSanPhamDao = new loaiSanPhamDao(this);
        nhaSanXuatDao = new nhaSanXuatDao(this);
        sanPhamDao = new SanPhamDao(this);
    }

    private void initSpinners() {
        listLoaiSanPham = loaiSanPhamDao.getAllTenLoaiSanPham();
        listLoaiSanPham.add(0, "Chọn loại sản phẩm");
        ArrayAdapter<String> adapterLoai = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listLoaiSanPham);
        adapterLoai.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLoaiSanPham.setAdapter(adapterLoai);

        listNhaSanXuat = nhaSanXuatDao.getAllTenNhaSanXuat();
        listNhaSanXuat.add(0, "Chọn nhà sản xuất");
        ArrayAdapter<String> adapterNhaSX = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listNhaSanXuat);
        adapterNhaSX.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerNhaSanXuat.setAdapter(adapterNhaSX);
    }

    private void populateFieldsFromIntent() {
        Intent intent = getIntent();
        edtTenSanPham.setText(intent.getStringExtra("sp_ten"));
        edtGiaSanPham.setText(intent.getStringExtra("sp_gia"));
        edtSoLuong.setText(intent.getStringExtra("sp_soluong"));
        edtMoTa.setText(intent.getStringExtra("sp_mota"));
        edtDoiTuong.setText(intent.getStringExtra("sp_doituong"));

        // Restore image from Base64 String
        String bitmapString = intent.getStringExtra("sp_hinhanh");
        if (bitmapString != null) {
            Bitmap bitmap = stringToBitmap(bitmapString);
            imgSanPham.setImageBitmap(bitmap);
        }

        setSpinnerSelection(intent);
    }

    private void setSpinnerSelection(Intent intent) {
        int lsp_ma = intent.getIntExtra("lsp_ma", -1);
        int nsx_ma = intent.getIntExtra("nsx_ma", -1);

        if (lsp_ma != -1) {
            for (int i = 0; i < listLoaiSanPham.size(); i++) {
                if (loaiSanPhamDao.getLoaiSanPhamMaByTen(listLoaiSanPham.get(i)) == lsp_ma) {
                    spinnerLoaiSanPham.setSelection(i);
                    break;
                }
            }
        }

        if (nsx_ma != -1) {
            for (int i = 0; i < listNhaSanXuat.size(); i++) {
                if (nhaSanXuatDao.getNhaSanXuatMaByTen(listNhaSanXuat.get(i)) == nsx_ma) {
                    spinnerNhaSanXuat.setSelection(i);
                    break;
                }
            }
        }
    }

    private void setUpImagePicker() {
        ActivityResultLauncher<Intent> imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Uri imageUri = result.getData().getData();
                        imgSanPham.setImageURI(imageUri); // Display selected image
                    }
                }
        );

        findViewById(R.id.imageButton).setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            imagePickerLauncher.launch(intent); // Open image picker
        });
    }

    private void setUpSaveButton() {
        findViewById(R.id.btnSave).setOnClickListener(v -> saveProduct());
    }

    private void saveProduct() {
        String tenSanPham = edtTenSanPham.getText().toString().trim();
        String giaSanPham = edtGiaSanPham.getText().toString().trim();
        String soLuong = edtSoLuong.getText().toString().trim();
        String moTa = edtMoTa.getText().toString().trim();
        String doiTuong = edtDoiTuong.getText().toString().trim();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); // Định dạng ngày
        String ngayCapNhat = formatter.format(date);

        // Validate inputs
        if (tenSanPham.isEmpty() || giaSanPham.isEmpty() || soLuong.isEmpty() ) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Convert image to Base64
        Bitmap bitmap = ((BitmapDrawable) imgSanPham.getDrawable()).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] hinhanh = byteArrayOutputStream.toByteArray();
        String bitmapString = Base64.encodeToString(hinhanh, Base64.DEFAULT);

        int lsp_ma = spinnerLoaiSanPham.getSelectedItemPosition() > 0 ? loaiSanPhamDao.getLoaiSanPhamMaByTen(listLoaiSanPham.get(spinnerLoaiSanPham.getSelectedItemPosition())) : -1;
        int nsx_ma = spinnerNhaSanXuat.getSelectedItemPosition() > 0 ? nhaSanXuatDao.getNhaSanXuatMaByTen(listNhaSanXuat.get(spinnerNhaSanXuat.getSelectedItemPosition())) : -1;
        int sp_ma = getIntent().getIntExtra("sp_ma", -1); // Get product ID from Intent

        // Check product ID
        if (sp_ma == -1) {
            Toast.makeText(this, "Mã sản phẩm không hợp lệ!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Save product to database
        if (sanPhamDao.updateSanPham(sp_ma, tenSanPham, Double.parseDouble(giaSanPham), ngayCapNhat, Integer.parseInt(soLuong), lsp_ma, nsx_ma, hinhanh, moTa, doiTuong)) {
            Toast.makeText(this, "Lưu sản phẩm thành công!", Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK);
            finish(); // Close Activity
        } else {
            Toast.makeText(this, "Lưu sản phẩm thất bại!", Toast.LENGTH_SHORT).show();
        }
    }

    public Bitmap stringToBitmap(String encodedString) {
        byte[] decodedByte = Base64.decode(encodedString, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }
}
