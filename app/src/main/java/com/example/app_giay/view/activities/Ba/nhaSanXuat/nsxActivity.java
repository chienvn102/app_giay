package com.example.app_giay.view.activities.Ba.nhaSanXuat; // Update the package path

import android.annotation.SuppressLint;
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
import com.example.app_giay.adapter.nhaSanXuatAdapter; // Update adapter import
import com.example.app_giay.dao.nhaSanXuatDao; // Update DAO import
import com.example.app_giay.model.loaiSanPham;
import com.example.app_giay.model.nhaSanXuat; // Update model import

import java.util.ArrayList;

public class nsxActivity extends AppCompatActivity { // Rename class to nsxActivity
    ImageButton imgbtnBack, imgbtnAdd;
    public static final int REQUEST_CODE_UPDATE_NSX = 2;
    private static final int REQUEST_CODE_ADD_NSX = 1;  // Mã request để nhận kết quả từ addLspActivity
    private static final int REQUEST_CODE_EDIT_NSX = 2;  // Mã request để nhận kết quả từ editLspActivity    ImageButton imgbtnBack, imgbtnAdd;
    ListView lvNsx; // Rename ListView variable
    ArrayList<nhaSanXuat> data = new ArrayList<nhaSanXuat>(); // Update ArrayList type
    ArrayList<nhaSanXuat> originalData = new ArrayList<nhaSanXuat>();  // Dữ liệu gốc để tìm kiếm
    nhaSanXuatAdapter adapter; // Update adapter variable
    nhaSanXuatDao dao = new nhaSanXuatDao(this); // Declare DAO variable
    SearchView searchView;  // SearchView để thực hiện tìm kiếm

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_nsx); // Update layout resource if necessary
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        imgbtnBack = findViewById(R.id.imgbtnBack);
        imgbtnBack.setOnClickListener(v -> finish());
        lvNsx = findViewById(R.id.lvNsx); // Update ListView ID
        searchView = findViewById(R.id.searchView);  // Ánh xạ SearchView
        imgbtnAdd = findViewById(R.id.imgbtnAdd);
        // Xử lý sự kiện khi bấm nút thêm nhà sản xuất
        imgbtnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(nsxActivity.this, addNsxActivity.class);
            startActivityForResult(intent, REQUEST_CODE_ADD_NSX);  // Mở activity để thêm nhà sản xuất
        });

        // Tải dữ liệu lên ListView
        loadData();

        // Xử lý sự kiện tìm kiếm với SearchView
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Tìm kiếm khi người dùng nhấn "Enter"
                filterByMaNhaSanXuat(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Tìm kiếm khi người dùng nhập từ khóa
                filterByMaNhaSanXuat(newText);
                return false;
            }
        });
    }

    // Hàm để tải dữ liệu từ cơ sở dữ liệu và hiển thị trên ListView
    public void loadData() {
        originalData = dao.getAllNhaSanXuat();  // Lấy toàn bộ dữ liệu gốc từ database
        data = new ArrayList<nhaSanXuat>(originalData);  // Sao chép dữ liệu vào danh sách hiển thị
        adapter = new nhaSanXuatAdapter(this, R.layout.layout_list_nsx, data);
        lvNsx.setAdapter(adapter);
    }

    // Phương thức nhận kết quả từ addLspActivity hoặc editLspActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD_NSX || requestCode == REQUEST_CODE_UPDATE_NSX) {
            if (resultCode == RESULT_OK) {
                loadData();  // Cập nhật lại dữ liệu sau khi thêm hoặc chỉnh sửa
            }
        }
    }

    // Phương thức lọc dữ liệu theo mã sản phẩm
    public void filterByMaNhaSanXuat(String query) {
        data.clear();  // Xóa dữ liệu hiện tại trước khi lọc

        if (query.isEmpty()) {
            // Nếu không có từ khóa tìm kiếm, hiển thị lại toàn bộ dữ liệu gốc
            data.addAll(originalData);
        } else {
            // Lọc danh sách theo mã sản phẩm
            for (nhaSanXuat item : originalData) {
                if (String.valueOf(item.getNsx_ten()).contains(query)) {
                    data.add(item);
                }
            }
        }

        // Cập nhật lại giao diện sau khi lọc dữ liệu
        adapter.notifyDataSetChanged();
    }
    }
