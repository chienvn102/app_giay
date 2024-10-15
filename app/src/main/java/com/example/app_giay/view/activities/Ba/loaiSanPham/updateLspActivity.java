package com.example.app_giay.view.activities.Ba.loaiSanPham;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.app_giay.R;
import com.example.app_giay.dao.loaiSanPhamDao;

public class updateLspActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_lsp);

        // Lấy ID của sản phẩm cần sửa từ Intent
        int loaiSanPhamId = getIntent().getIntExtra("loaiSanPhamId", -1);

        // Dựa vào loaiSanPhamId để lấy thông tin sản phẩm từ cơ sở dữ liệu hoặc nơi lưu trữ dữ liệu
        // Hiển thị thông tin sản phẩm cho người dùng có thể sửa
    }
    private void updateLoaiSanPham(int lspMa, String newTen, String newMoTa) {
        loaiSanPhamDao loaiSanPhamDao = new loaiSanPhamDao(this);
        loaiSanPhamDao.updateLoaiSanPham(lspMa, newTen, newMoTa);
    }
}