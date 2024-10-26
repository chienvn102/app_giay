package com.example.app_giay.view.activities.Ba.sanPham;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.app_giay.R;
import com.example.app_giay.adapter.sanPhamAdapter;
import com.example.app_giay.dao.SanPhamDao;
import com.example.app_giay.model.SanPham;
import com.example.app_giay.model.loaiSanPham;

import java.util.ArrayList;

public class sanphamActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_ADD_SAN_PHAM = 1;
    public static final int REQUEST_CODE_EDIT_SAN_PHAM = 2;
    ImageButton btnAdd, btnBack;

    ListView lvSanPham;
    sanPhamAdapter adapter;
    ArrayList<SanPham> data = new ArrayList<>();
    ArrayList<SanPham> originalData = new ArrayList<>();
    SanPhamDao dao = new SanPhamDao(this);
    SearchView searchView;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sanpham);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btnBack = findViewById(R.id.imgbtnBack);
        btnBack.setOnClickListener(v -> finish());


        lvSanPham = findViewById(R.id.lvSanPham);
        searchView = findViewById(R.id.searchView);



        btnAdd = findViewById(R.id.imgbtnAdd);
        btnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(this, addSanPhamActivity.class);
            startActivityForResult(intent, REQUEST_CODE_ADD_SAN_PHAM);
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
        lvSanPham.setOnItemClickListener((parent, view, position, id) -> {
            // Lấy item được click từ danh sách
            SanPham sanPham = data.get(position);

            // Hiển thị thông báo khi click vào item
            Toast.makeText(this, "Đã click vào sản phẩm: " + sanPham.getSp_ten(), Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD_SAN_PHAM || requestCode == REQUEST_CODE_EDIT_SAN_PHAM) {
            if (resultCode == RESULT_OK) {
                LoadData();
            }
        }
    }
    public void LoadData() {
        originalData = dao.getAllSanPham();
        data = dao.getAllSanPham();
        adapter = new sanPhamAdapter(this, R.layout.layout_list_sp_lv, data);
        lvSanPham.setAdapter(adapter);
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
    // Hàm xóa tất cả sản phẩm
    private void deleteAllSanPham() {
        new AlertDialog.Builder(this)
                .setTitle("Xác nhận xóa")
                .setMessage("Bạn có chắc chắn muốn xóa tất cả sản phẩm không?")
                .setPositiveButton("Xóa", (dialog, which) -> {
                    dao.deleteAllSanPham(); // Gọi phương thức xóa tất cả sản phẩm
                    LoadData(); // Tải lại dữ liệu
                })
                .setNegativeButton("Hủy", null)
                .show();
    }
}