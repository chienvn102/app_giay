package com.example.app_giay.view.activities.Ba.ShoppingCart;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.app_giay.R;

import com.example.app_giay.adapter.gioHangAdapter;
import com.example.app_giay.dao.cartDao;
import com.example.app_giay.model.cart;
import com.example.app_giay.dao.sp_dondathangDao;
import com.example.app_giay.view.activities.Fe.MainFEActivity;

import java.util.ArrayList;

public class shoppingCartActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_BUY_CART = 1;
    ListView listView;
    cartDao cartDao;
    ArrayList<cart> data = new ArrayList<>();
    gioHangAdapter adapter;
    TextView txtBuy;
    sp_dondathangDao sp_dondathangDAO = new sp_dondathangDao(this);
    ImageButton imgbtnBack, imgbtnHome, imgbtnDonHang;
    TextView txtEmptyCart;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_shopping_cart);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        SharedPreferences prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
        int currentUserId = prefs.getInt("user_id", -1);

        listView = findViewById(R.id.lvDon);
        cartDao = new cartDao(this);
        data = cartDao.getCartItems(currentUserId);
        txtEmptyCart = findViewById(R.id.txtEmptyCart);
        data = cartDao.getCartItems(currentUserId);

        // Kiểm tra xem giỏ hàng có sản phẩm không
        if (data.isEmpty()) {
            txtEmptyCart.setVisibility(View.VISIBLE); // Hiện thông báo
            listView.setVisibility(View.GONE); // Ẩn ListView
        } else {
            txtEmptyCart.setVisibility(View.GONE); // Ẩn thông báo
            listView.setVisibility(View.VISIBLE); // Hiện ListView
        }

        txtBuy = findViewById(R.id.txtBuy);
        txtBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Kiểm tra nếu giỏ hàng rỗng
                if (data.isEmpty()) {
                    Toast.makeText(shoppingCartActivity.this, "Giỏ hàng hiện tại không có sản phẩm!", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(shoppingCartActivity.this, informationActivity.class);
                    intent.putExtra("user_id", currentUserId);
                    startActivityForResult(intent, REQUEST_CODE_BUY_CART);
                }
            }
        });


        adapter = new gioHangAdapter(this, R.layout.layout_itemcart, data);
        listView.setAdapter(adapter);

        imgbtnBack = findViewById(R.id.imgbtnBack);
        imgbtnBack.setOnClickListener(v -> finish());

        imgbtnHome = findViewById(R.id.imgbtnHome);
        imgbtnHome.setOnClickListener(v -> {
            Intent intent = new Intent(shoppingCartActivity.this, MainFEActivity.class);
            startActivity(intent);
        });

        imgbtnDonHang = findViewById(R.id.imgbtnDonHang);
        imgbtnDonHang.setOnClickListener(v -> {
            Intent intent = new Intent(shoppingCartActivity.this, DonHangttActivity.class);
            startActivity(intent);
        });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_BUY_CART && resultCode == RESULT_OK) {
            int dhMa = data.getIntExtra("dh_ma", -1);
            SharedPreferences prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
            int userId = prefs.getInt("user_id", -1);
            Toast.makeText(this, "Đặt hàng thành công!"+ userId, Toast.LENGTH_SHORT).show();
            if (userId != -1) {
                ArrayList<cart> cartItems = cartDao.getCartItemsByUserId(userId);
                for (cart item : cartItems) {
                    int spMa = item.getSp_ma();
                    int spSoLuong = item.getSp_soluong();
                    sp_dondathangDAO.addsp_dondathang(spMa,dhMa, spSoLuong, userId);
                    cartDao.deleteCartItem(userId, spMa);
                    Intent intent = new Intent(shoppingCartActivity.this, DonHangttActivity.class);
                    startActivity(intent);
                }
            }
        }
    }



}
