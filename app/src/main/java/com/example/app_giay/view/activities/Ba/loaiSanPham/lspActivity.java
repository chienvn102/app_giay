package com.example.app_giay.view.activities.Ba.loaiSanPham;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.app_giay.R;
import com.example.app_giay.adapter.loaiSanPhamAdapter;
import com.example.app_giay.dao.loaiSanPhamDao;
import com.example.app_giay.model.loaiSanPham;

import java.util.ArrayList;

public class lspActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_UPDATE_LSP = 2;
    private static final int REQUEST_CODE_ADD_LSP = 1;  // Mã request để nhận kết quả từ addLspActivity
    private static final int REQUEST_CODE_EDIT_LSP = 2;  // Mã request để nhận kết quả từ editLspActivity
    ImageButton imgbtnBack, imgbtnAdd;
    ListView lvLsp;
    ArrayList<loaiSanPham> data = new ArrayList<>();
    ArrayList<loaiSanPham> originalData = new ArrayList<>();  // Dữ liệu gốc để tìm kiếm
    loaiSanPhamAdapter adapter;
    loaiSanPhamDao dao = new loaiSanPhamDao(this);
    SearchView searchView;  // SearchView để thực hiện tìm kiếm

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lsp);

        // Đặt lại padding khi có sự thay đổi về hệ thống điều hướng
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Ánh xạ các thành phần giao diện
        imgbtnBack = findViewById(R.id.imgbtnBack);
        imgbtnBack.setOnClickListener(v -> finish());

        lvLsp = findViewById(R.id.lvLsp);
        imgbtnAdd = findViewById(R.id.imgbtnAdd);
        searchView = findViewById(R.id.searchView);  // Ánh xạ SearchView

        // Xử lý sự kiện khi bấm nút thêm loại sản phẩm
        imgbtnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(lspActivity.this, addLspActivity.class);
            startActivityForResult(intent, REQUEST_CODE_ADD_LSP);  // Mở activity để thêm loại sản phẩm
        });

        // Tải dữ liệu lên ListView
        loadData();

        // Xử lý sự kiện tìm kiếm với SearchView
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Tìm kiếm khi người dùng nhấn "Enter"
                filterByMaSanPham(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Tìm kiếm khi người dùng nhập từ khóa
                filterByMaSanPham(newText);
                return false;
            }
        });
    }

    // Hàm để tải dữ liệu từ cơ sở dữ liệu và hiển thị trên ListView
    public void loadData() {
        originalData = dao.getAllLoaiSanPham();  // Lấy toàn bộ dữ liệu gốc từ database
        data = new ArrayList<>(originalData);  // Sao chép dữ liệu vào danh sách hiển thị
        adapter = new loaiSanPhamAdapter(this, R.layout.layout_list_lsp, data);
        lvLsp.setAdapter(adapter);
    }

    // Phương thức nhận kết quả từ addLspActivity hoặc editLspActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD_LSP || requestCode == REQUEST_CODE_UPDATE_LSP) {
            if (resultCode == RESULT_OK) {
                loadData();  // Cập nhật lại dữ liệu sau khi thêm hoặc chỉnh sửa
            }
        }
    }

    // Phương thức lọc dữ liệu theo mã sản phẩm
    public void filterByMaSanPham(String query) {
        data.clear();  // Xóa dữ liệu hiện tại trước khi lọc

        if (query.isEmpty()) {
            // Nếu không có từ khóa tìm kiếm, hiển thị lại toàn bộ dữ liệu gốc
            data.addAll(originalData);
        } else {
            // Lọc danh sách theo mã sản phẩm
            for (loaiSanPham item : originalData) {
                if (String.valueOf(item.getLsp_ten()).contains(query)) {
                    data.add(item);
                }
            }
        }

        // Cập nhật lại giao diện sau khi lọc dữ liệu
        adapter.notifyDataSetChanged();
    }
}
