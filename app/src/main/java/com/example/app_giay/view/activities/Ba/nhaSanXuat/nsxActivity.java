package com.example.app_giay.view.activities.Ba.nhaSanXuat; // Update the package path

import android.annotation.SuppressLint;
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
import com.example.app_giay.adapter.nhaSanXuatAdapter; // Update adapter import
import com.example.app_giay.dao.nhaSanXuatDao; // Update DAO import
import com.example.app_giay.model.nhaSanXuat; // Update model import

import java.util.ArrayList;

public class nsxActivity extends AppCompatActivity { // Rename class to nsxActivity
    ImageButton imgbtnBack, imgbtnAdd;
    ListView lvNsx; // Rename ListView variable
    ArrayList<nhaSanXuat> data = new ArrayList<>(); // Update ArrayList type
    nhaSanXuatAdapter adapter; // Update adapter variable
    nhaSanXuatDao dao = new nhaSanXuatDao(this); // Declare DAO variable

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
        imgbtnAdd = findViewById(R.id.imgbtnAdd);
        imgbtnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(nsxActivity.this, addNsxActivity.class); // Update intent target
            startActivity(intent);
        });

        data = dao.getAllNhaSanXuat();
        adapter = new nhaSanXuatAdapter(this, R.layout.layout_list_nsx, data);
        lvNsx.setAdapter(adapter);
    }
}
