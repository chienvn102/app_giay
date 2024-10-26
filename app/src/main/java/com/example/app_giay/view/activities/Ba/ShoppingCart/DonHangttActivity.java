package com.example.app_giay.view.activities.Ba.ShoppingCart;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.app_giay.R;
import com.example.app_giay.adapter.donhangAdapter;
import com.example.app_giay.dao.sp_dondathangDao;
import com.example.app_giay.model.donhang;

import java.util.ArrayList;

public class DonHangttActivity extends AppCompatActivity {
    sp_dondathangDao sp_dondathangDAO = new sp_dondathangDao(this);
    ListView listView;
    donhangAdapter adapter;
    ArrayList<donhang> data = new ArrayList<>();
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
        SharedPreferences prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
        int currentUserId = prefs.getInt("user_id", -1);
        listView = findViewById(R.id.lvDon);
        data = sp_dondathangDAO.ThongTinDonHang(currentUserId);
        adapter = new donhangAdapter(this,R.layout.layout_item_donhang, data);
        listView.setAdapter(adapter);
    }
}