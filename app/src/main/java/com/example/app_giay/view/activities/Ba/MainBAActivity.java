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

public class MainBAActivity extends AppCompatActivity {
    ImageButton imgbtnlsp, imgbtnnsx, imgbtnsp, imgbtnDonHang;
    TextView txtdonhuy,txtdontc,txttong;
    DonDatHangDAO donDatHangDAO = new DonDatHangDAO(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_baactivity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        imgbtnlsp = findViewById(R.id.imgbtnlsp);
        imgbtnnsx = findViewById(R.id.imgbtnnsx);
        imgbtnsp = findViewById(R.id.imgbtnsp);
        imgbtnDonHang = findViewById(R.id.imgbtnDonHang);
        txtdonhuy = findViewById(R.id.txtdonhuy);
        txtdontc = findViewById(R.id.txtdontc);
        txttong = findViewById(R.id.txttong);
        txtdonhuy.setText(String.valueOf(donDatHangDAO.getdonbihuuy()));
        txtdontc.setText(String.valueOf(donDatHangDAO.getdonthanhcong()));

        imgbtnlsp.setOnClickListener(v -> {
            try {
                Intent intent = new Intent(this, lspActivity.class);
                startActivity(intent);
            } catch (Exception e) {
                Toast.makeText(MainBAActivity.this, "Error navigating to LSP: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        imgbtnnsx.setOnClickListener(v -> {
            try {
                Intent intent = new Intent(this, nsxActivity.class);
                startActivity(intent);
            } catch (Exception e) {
                Toast.makeText(MainBAActivity.this, "Error navigating to NSX: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        imgbtnsp.setOnClickListener(v -> {
            try {
                Intent intent = new Intent(this, sanphamActivity.class);
                startActivity(intent);
            } catch (Exception e) {
                Toast.makeText(MainBAActivity.this, "Error navigating to SanPham: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        imgbtnDonHang.setOnClickListener(v -> {
            try {
                Intent intent = new Intent(this, DonHangActivity.class);
                startActivity(intent);
            } catch (Exception e) {
                Toast.makeText(MainBAActivity.this, "Error navigating to DonHang: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        Button btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(v -> {
            // Hiển thị hộp thoại xác nhận
            new AlertDialog.Builder(MainBAActivity.this)
                    .setTitle("Xác nhận đăng xuất")
                    .setMessage("Bạn có chắc chắn muốn đăng xuất không?")
                    .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                // Thực hiện thao tác đăng xuất ở đây
                                Toast.makeText(MainBAActivity.this, "Logged out", Toast.LENGTH_SHORT).show();
                                // Chuyển người dùng về màn hình đăng nhập
                                Intent intent = new Intent(MainBAActivity.this, SigninActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish(); // Đóng màn hình hiện tại
                            } catch (Exception e) {
                                // Bắt lỗi và thông báo cho người dùng
                                Toast.makeText(MainBAActivity.this, "Error during logout: " + e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    })
                    .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss(); // Đóng hộp thoại
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert) // Biểu tượng cho hộp thoại
                    .show();
        });
    }
}
