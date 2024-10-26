package com.example.app_giay.view.activities.Ba.ShoppingCart;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.app_giay.R;
import com.example.app_giay.adapter.OrderWithProductsAdapter; // Cập nhật dòng import này
import com.example.app_giay.dao.sp_dondathangDao; // Nếu bạn đã đổi tên DAO, cập nhật lại
import com.example.app_giay.model.OrderWithProducts; // Cập nhật dòng import này
import com.example.app_giay.view.activities.Fe.MainFEActivity;

import java.util.ArrayList;

public class DonHangttActivity extends AppCompatActivity {
    sp_dondathangDao sp_dondathangDAO;
    ListView listView;
    OrderWithProductsAdapter adapter; // Cập nhật adapter
    ArrayList<OrderWithProducts> data = new ArrayList<>();
    ImageButton imgbtnBack, imgbtnHome;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_don_hangtt);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Lấy user_id từ SharedPreferences
        SharedPreferences prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
        int currentUserId = prefs.getInt("user_id", -1);

        listView = findViewById(R.id.lvDon);

        // Khởi tạo DAO
        sp_dondathangDAO = new sp_dondathangDao(this);

        // Lấy dữ liệu đơn hàng theo user_id
        data = sp_dondathangDAO.ThongTinAllDonHang(currentUserId);

        // Khởi tạo adapter và gán vào ListView
        adapter = new OrderWithProductsAdapter(this, R.layout.layout_item_donhang, data);
        listView.setAdapter(adapter);

        imgbtnBack = findViewById(R.id.imgbtnBack);
        imgbtnBack.setOnClickListener(v -> finish());

        imgbtnHome = findViewById(R.id.imgbtnHome);
        imgbtnHome.setOnClickListener(v -> {
            Intent intent = new Intent(DonHangttActivity.this, MainFEActivity.class);
            startActivity(intent);
        });
    }
}
