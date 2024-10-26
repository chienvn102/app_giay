package com.example.app_giay.view.activities.Ba.ShoppingCart;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.app_giay.R;
import com.example.app_giay.dao.SanPhamDao;
import com.example.app_giay.dao.cartDao;
import com.example.app_giay.model.cart;
import com.example.app_giay.model.cartdetail;

import java.util.ArrayList;

import com.example.app_giay.adapter.cartAdapter;


public class shoppingCartActivity extends AppCompatActivity {
    cartDao cartDao = new cartDao(this);
    SanPhamDao sanPhamDao = new SanPhamDao(this);
    ArrayList<cart> cartList;
    cartdetail  cartdetail = new cartdetail();
    ArrayList<cartdetail> data = new ArrayList<>();
    cartAdapter cartAdapter ;
    ListView lvCart;

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

        // Lấy tất cả các mục trong giỏ hàng
        cartList = cartDao.getCartItems(currentUserId);
        // Hiển thị danh sách các mục trong giỏ hàng
        for (cart cartItem : cartList) {
            int sp_ma = cartItem.getSp_ma();
            String sp_img = sanPhamDao.getImagePath(sp_ma);
            int soluong = cartItem.getSp_soluong();
            int user_id = cartItem.getUsers_id();
            cartdetail.setSp_ma(sp_ma);
            cartdetail.setSp_soluong(soluong);
            cartdetail.setUsers_id(user_id);
            cartdetail.setSp_img(sp_img);
            data.add(cartdetail);
            Log.d("ShoppingCartActivity", "uuu: " + user_id);
            Log.d("ShoppingCartActivity", "Product ID: " + sp_ma);
            Log.d("ShoppingCartActivity", "Quantity: " + soluong);
            Log.d("ShoppingCartActivity", "img: " + sp_img);
        }
        cartAdapter = new cartAdapter(this, R.layout.cart, data);
        lvCart = findViewById(R.id.lvGioHang);
        lvCart.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();

    }
}
