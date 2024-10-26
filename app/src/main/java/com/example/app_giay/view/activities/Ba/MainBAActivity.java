package com.example.app_giay.view.activities.Ba;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.app_giay.R;
import com.example.app_giay.dao.DonDatHangDAO;
import com.example.app_giay.view.activities.Ba.donHang.DonHangActivity;
import com.example.app_giay.view.activities.Ba.loaiSanPham.lspActivity;
import com.example.app_giay.view.activities.Ba.nhaSanXuat.nsxActivity;
import com.example.app_giay.view.activities.Ba.sanPham.sanphamActivity;
import com.example.app_giay.view.activities.SigninActivity;

import java.text.DecimalFormat;

public class MainBAActivity extends AppCompatActivity {
    ImageButton imgbtnlsp, imgbtnnsx, imgbtnsp, imgbtnDonHang;
    TextView txtdonhuy, txtdontc, txttong;
    DonDatHangDAO donDatHangDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_baactivity);

        // Setup Window Insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize UI elements
        imgbtnlsp = findViewById(R.id.imgbtnlsp);
        imgbtnnsx = findViewById(R.id.imgbtnnsx);
        imgbtnsp = findViewById(R.id.imgbtnsp);
        imgbtnDonHang = findViewById(R.id.imgbtnDonHang);
        txtdonhuy = findViewById(R.id.txtdonhuy);
        txtdontc = findViewById(R.id.txtdontc);
        txttong = findViewById(R.id.txttong);

        // Set up button click listeners
        imgbtnlsp.setOnClickListener(v -> startActivityWithErrorHandling(lspActivity.class));
        imgbtnnsx.setOnClickListener(v -> startActivityWithErrorHandling(nsxActivity.class));
        imgbtnsp.setOnClickListener(v -> startActivityWithErrorHandling(sanphamActivity.class));
        imgbtnDonHang.setOnClickListener(v -> startActivityWithErrorHandling(DonHangActivity.class));

        Button btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(v -> showLogoutDialog());

        donDatHangDAO = new DonDatHangDAO(this); // Initialize DAO
    }

    private void startActivityWithErrorHandling(Class<?> activityClass) {
        try {
            Intent intent = new Intent(this, activityClass);
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(MainBAActivity.this, "Error navigating: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void showLogoutDialog() {
        new AlertDialog.Builder(MainBAActivity.this)
                .setTitle("Xác nhận đăng xuất")
                .setMessage("Bạn có chắc chắn muốn đăng xuất không?")
                .setPositiveButton("Có", (dialog, which) -> {
                    Toast.makeText(MainBAActivity.this, "Logged out", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainBAActivity.this, SigninActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish(); // Close current activity
                })
                .setNegativeButton("Không", (dialog, which) -> dialog.dismiss())
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Retrieve data from the database
        int donhuy = donDatHangDAO.getdonbihuuy();
        int dontc = donDatHangDAO.getdonthanhcong();

        // Calculate total for a specific order status, e.g., 4
        String totalAmount = donDatHangDAO.TongTien(4);

        // Update UI elements safely
        txtdonhuy.setText(String.valueOf(donhuy));
        txtdontc.setText(String.valueOf(dontc));
        txttong.setText(totalAmount); // Update total amount with currency formatting
    }
}
