package com.example.app_giay.view.activities.Fe;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.app_giay.R;
import com.example.app_giay.adapter.feAdpter;
import com.example.app_giay.adapter.sanPhamAdapter;
import com.example.app_giay.dao.SanPhamDao;
import com.example.app_giay.model.SanPham;
import com.example.app_giay.view.activities.Ba.ShoppingCart.DonHangttActivity;
import com.example.app_giay.view.activities.Ba.ShoppingCart.shoppingCartActivity;
import com.example.app_giay.view.activities.SigninActivity;

import java.util.ArrayList;

public class MainFEActivity extends AppCompatActivity {
    GridView gridView;
    feAdpter adapter;
    SanPhamDao sanPhamDao = new SanPhamDao(this);
    ArrayList<SanPham> data = new ArrayList<>();
    ArrayList<SanPham> originalData = new ArrayList<>();
    ImageButton imgbtnHome, imgbtnGioHang,ImgbtnDonHang;
    SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_feactivity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        gridView = findViewById(R.id.gridView);
        data = sanPhamDao.getAllSanPham();
        adapter = new feAdpter(this, R.layout.layout_grid_viewfe, data);
        gridView.setAdapter(adapter);
        searchView = findViewById(R.id.searchView);

        imgbtnGioHang = findViewById(R.id.imgbtnGioHang);
        imgbtnGioHang.setOnClickListener(v -> {
            Intent intent = new Intent(MainFEActivity.this, shoppingCartActivity.class);
            startActivity(intent);
        });
        ImgbtnDonHang = findViewById(R.id.imgbtnDonHang);
        ImgbtnDonHang.setOnClickListener(v -> {
            Intent intent = new Intent(MainFEActivity.this, DonHangttActivity.class);
            startActivity(intent);
        });

        imgbtnHome = findViewById(R.id.imgbtnHome);
        imgbtnHome.setOnClickListener(v -> {
            Intent intent = new Intent(MainFEActivity.this, MainFEActivity.class);
            startActivity(intent);
        });
        LoadData();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Tìm kiếm khi người dùng nhấn "Enter"
                filterBySanPham(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Tìm kiếm khi người dùng nhập từ khóa
                filterBySanPham(newText);
                return false;
            }
        });
        gridView.setOnItemClickListener((parent, view, position, id) -> {
            // Lấy item được click từ danh sách
            SanPham sanPham = data.get(position);

            // Hiển thị thông báo khi click vào item
            Toast.makeText(this, "Đã click vào sản phẩm: " + sanPham.getSp_ten(), Toast.LENGTH_SHORT).show();
        });

        Button btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(v -> {
            // Hiển thị hộp thoại xác nhận
            new AlertDialog.Builder(MainFEActivity.this)
                    .setTitle("Xác nhận đăng xuất")
                    .setMessage("Bạn có chắc chắn muốn đăng xuất không?")
                    .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                // Thực hiện thao tác đăng xuất ở đây
                                Toast.makeText(MainFEActivity.this, "Logged out", Toast.LENGTH_SHORT).show();
                                // Chuyển người dùng về màn hình đăng nhập
                                Intent intent = new Intent(MainFEActivity.this, SigninActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish(); // Đóng màn hình hiện tại
                            } catch (Exception e) {
                                // Bắt lỗi và thông báo cho người dùng
                                Toast.makeText(MainFEActivity.this, "Error during logout: " + e.getMessage(), Toast.LENGTH_LONG).show();
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

    public void LoadData() {
        originalData = sanPhamDao.getAllSanPham();
        data = sanPhamDao.getAllSanPham();
        adapter = new feAdpter(this, R.layout.layout_grid_viewfe, data);
        gridView.setAdapter(adapter);
    }
    // Phương thức lọc dữ liệu theo mã sản phẩm
    public void filterBySanPham(String query) {
        query = query.toLowerCase().trim();
        data.clear();  // Xóa dữ liệu hiện tại trước khi lọc

        if (query.isEmpty()) {
            // Nếu không có từ khóa tìm kiếm, hiển thị lại toàn bộ dữ liệu gốc
            data.addAll(originalData);
        } else {
            // Lọc danh sách theo mã sản phẩm
            for (SanPham item : originalData) {
                if (String.valueOf(item.getSp_ten()).contains(query)) {
                    data.add(item);
                }
            }
        }

        // Cập nhật lại giao diện sau khi lọc dữ liệu
        adapter.notifyDataSetChanged();
    }
}
