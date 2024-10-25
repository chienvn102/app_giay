package com.example.app_giay.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app_giay.R;
import com.example.app_giay.model.SanPham;
import com.example.app_giay.view.activities.Ba.detailSanPham.detailSanPhamActivity;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class feAdpter extends ArrayAdapter<SanPham> {
    private Context context;
    private int resource;
    private ArrayList<SanPham> data;

    public feAdpter(Context context, int resource, ArrayList<SanPham> data) {
        super(context, resource, data);
        this.context = context;
        this.resource = resource;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(resource, parent, false);
        }
        SanPham sanPham = data.get(position);
        Button btnBuy = convertView.findViewById(R.id.btnBuy);
        ImageView imgSanPham = convertView.findViewById(R.id.imgSanPham);
        TextView txtTen = convertView.findViewById(R.id.txtsp_ten);
        TextView txtGia = convertView.findViewById(R.id.txtsp_gia);

        txtTen.setText(sanPham.getSp_ten());
        txtGia.setText(String.format("%,.0f VND", sanPham.getSp_gia()));
        if (sanPham.getSp_hinhanh() != null) {
            imgSanPham.setImageBitmap(sanPham.getSp_hinhanh());
        } else {
            imgSanPham.setImageResource(R.drawable.ic_launcher_background); // Hình ảnh mặc định
        }
        btnBuy.setOnClickListener(v -> {
            Intent intent = new Intent(context, detailSanPhamActivity.class);
            intent.putExtra("sanPham", sanPham.getSp_ten());
            String gia = String.format("%,.0f VND", sanPham.getSp_gia());
            intent.putExtra("sanPham_gia", gia);
            intent.putExtra("sanPham_mota", sanPham.getSp_mota());

            // Chuyển đổi hình ảnh sang chuỗi Base64
            if (sanPham.getSp_hinhanh() != null) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                sanPham.getSp_hinhanh().compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] hinhanhBytes = byteArrayOutputStream.toByteArray();
                String hinhanhBase64 = Base64.encodeToString(hinhanhBytes, Base64.DEFAULT);
                intent.putExtra("sanPham_hinhanh", hinhanhBase64);
            }

            context.startActivity(intent);
        });


        return convertView;
    }


}
