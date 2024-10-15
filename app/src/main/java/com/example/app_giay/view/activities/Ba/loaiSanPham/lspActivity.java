package com.example.app_giay.view.activities.Ba.loaiSanPham;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ListView;

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
    ImageButton imgbtnBack, imgbtnAdd;
    ListView lvLsp;
    ArrayList<loaiSanPham> data = new ArrayList<>();
    loaiSanPhamAdapter adapter;
    loaiSanPhamDao dao = new loaiSanPhamDao(this);

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

        // Xử lý sự kiện khi bấm nút thêm loại sản phẩm
        imgbtnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(lspActivity.this, addLspActivity.class);
            startActivityForResult(intent, REQUEST_CODE_ADD_LSP);  // Mở activity để thêm loại sản phẩm
        });

        // Tải dữ liệu lên ListView
        loadData();
    }

    // Hàm để tải dữ liệu từ cơ sở dữ liệu và hiển thị trên ListView
    public void loadData() {
        data = dao.getAllLoaiSanPham();
        adapter = new loaiSanPhamAdapter(this, R.layout.layout_list_lsp, data);
        lvLsp.setAdapter(adapter);

    }

    // Phương thức nhận kết quả từ addLspActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD_LSP || requestCode == REQUEST_CODE_UPDATE_LSP) {
            if (resultCode == RESULT_OK) {
                loadData();
            }
        }
    }
}
