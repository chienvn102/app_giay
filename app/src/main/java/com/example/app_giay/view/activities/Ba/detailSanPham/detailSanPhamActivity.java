package com.example.app_giay.view.activities.Ba.detailSanPham;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.app_giay.R;
import com.example.app_giay.dao.SanPhamDao;
import com.example.app_giay.view.activities.Ba.ShoppingCart.shoppingCartActivity;
import com.example.app_giay.dao.cartDao;
import com.example.app_giay.view.activities.Fe.MainFEActivity;

public class detailSanPhamActivity extends AppCompatActivity {
    ImageButton imgBtnBack, imgbtnHome;
    ImageView imgSanPham;
    TextView tvTenSanPham, tvMoTaSanPham, tvGiaSanPham, txtBuy;
    cartDao cartDao;
    SanPhamDao sanPhamDao = new SanPhamDao(this);

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail_san_pham);

        // Lấy user_id từ SharedPreferences
        SharedPreferences prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
        int currentUserId = prefs.getInt("user_id", -1); // -1 là giá trị mặc định nếu không tìm thấy user_id
        if (currentUserId == -1) {
            Toast.makeText(this, "User ID không tồn tại. Vui lòng đăng nhập lại.", Toast.LENGTH_SHORT).show();
            finish(); // Đóng activity nếu không có user_id
            return;
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        imgBtnBack = findViewById(R.id.imgbtnBack);
        imgBtnBack.setOnClickListener(v -> finish());

        imgSanPham = findViewById(R.id.imgHinhSanPham);
        tvTenSanPham = findViewById(R.id.txtsp_ten);
        tvMoTaSanPham = findViewById(R.id.txtlsp_mota);
        tvGiaSanPham = findViewById(R.id.txtsp_gia);
        txtBuy = findViewById(R.id.txtBuy);

        imgbtnHome = findViewById(R.id.imgbtnHome);
        imgbtnHome.setOnClickListener(v -> {
            Intent intent = new Intent(detailSanPhamActivity.this, MainFEActivity.class);
            startActivity(intent);
        });

        Intent intent = getIntent();
        tvTenSanPham.setText(intent.getStringExtra("sanPham"));
        tvMoTaSanPham.setText(intent.getStringExtra("sanPham_mota"));
        tvGiaSanPham.setText(intent.getStringExtra("sanPham_gia"));

        String img = intent.getStringExtra("sanPham_hinhanh");
        if (img != null) {
            Bitmap bitmap = stringToBitmap(img);
            imgSanPham.setImageBitmap(bitmap);
        }

        imgBtnBack.setOnClickListener(v -> finish());

        txtBuy.setOnClickListener(v -> {
            // Inflate layout từ dialog_number_picker.xml
            LayoutInflater inflater = getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.dialog_number_picker, null);

            // Tham chiếu đến NumberPicker
            NumberPicker numberPicker = dialogView.findViewById(R.id.numberPicker);
            numberPicker.setMinValue(1); // Số lượng tối thiểu
            numberPicker.setMaxValue(10); // Số lượng tối đa

            // Tạo AlertDialog với bố cục tùy chỉnh
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setView(dialogView);
            builder.setPositiveButton("Xác nhận", (dialog, which) -> {
                int soLuong = numberPicker.getValue(); // Lấy số lượng được chọn
                int sp_ma = sanPhamDao.getSp_ma(tvTenSanPham.getText().toString());
                Toast.makeText(this, "Số lượng bạn chọn là: " + soLuong, Toast.LENGTH_SHORT).show();
                cartDao = new cartDao(this);
                cartDao.addToCart(currentUserId,    sp_ma, soLuong); // Sử dụng currentUserId đã lấy
                Intent shoppingCartIntent = new Intent(this, shoppingCartActivity.class);
                startActivity(shoppingCartIntent);
            });
            builder.setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss());

            builder.show();
        });
    }

    public Bitmap stringToBitmap(String encodedString) {
        byte[] decodedByte = Base64.decode(encodedString, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }
}
