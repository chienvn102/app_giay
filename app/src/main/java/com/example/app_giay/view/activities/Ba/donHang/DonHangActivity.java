package com.example.app_giay.view.activities.Ba.donHang;

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
import com.example.app_giay.adapter.donHanAdminAdapter;
import com.example.app_giay.dao.sp_dondathangDao;
import com.example.app_giay.model.donhang;
import com.example.app_giay.view.activities.Ba.MainBAActivity;

import java.util.ArrayList;

public class DonHangActivity extends AppCompatActivity {
    sp_dondathangDao sp_dondathangDAO = new sp_dondathangDao(this);
    ListView listView;
    donHanAdminAdapter adapter;
    ArrayList<donhang> data = new ArrayList<>();
    ImageButton imgbtnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_don_hang);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        data = sp_dondathangDAO.ThongTinAllDonHang();
        listView = findViewById(R.id.lvDon);
        adapter = new donHanAdminAdapter(this, R.layout.layout_item_donhang2, data);
        listView.setAdapter(adapter);

        imgbtnBack = findViewById(R.id.imgbtnBack);
        imgbtnBack.setOnClickListener(v -> {
            Intent intent = new Intent(DonHangActivity.this, MainBAActivity.class);
            startActivity(intent);
        });
    }
}