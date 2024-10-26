package com.example.app_giay.view.activities.Ba.ShoppingCart;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.app_giay.R;
import com.example.app_giay.dao.DonDatHangDAO;
import com.example.app_giay.dao.khachhangDao;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class informationActivity extends AppCompatActivity {
    EditText txtName, txtEmail, txtPhone, txtAddress;
    khachhangDao khachhangDao= new khachhangDao(this);
    DonDatHangDAO donDatHangDAO = new DonDatHangDAO(this);
    Button btnSave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_information);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        txtName = findViewById(R.id.edtkh_hoten);
        txtEmail = findViewById(R.id.edtkh_email);
        txtPhone = findViewById(R.id.edtkh_sdt);
        txtAddress = findViewById(R.id.edtkh_diachi);
        btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(v -> save());


    }

    public void save() {
        String name = txtName.getText().toString();
        String email = txtEmail.getText().toString();
        String phone = txtPhone.getText().toString();
        String address = txtAddress.getText().toString();

        // Định dạng ngày hiện tại
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = dateFormat.format(Calendar.getInstance().getTime());

        // Thêm khách hàng và đơn đặt hàng vào cơ sở dữ liệu
        khachhangDao.addKhachHang(name, email, phone, address);
        int khachHangMa = khachhangDao.getKhachHangMaByTen(name);
        int dhMa = donDatHangDAO.addDonDatHang(date, address, 2, khachHangMa); // Lấy mã đơn hàng

        // Trả mã đơn hàng về Activity trước đó
        Intent intent = new Intent();
        intent.putExtra("dh_ma", dhMa);
        setResult(RESULT_OK, intent);
        finish();
    }

}