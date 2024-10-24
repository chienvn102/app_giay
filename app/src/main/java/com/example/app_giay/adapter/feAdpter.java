package com.example.app_giay.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.app_giay.R;
import com.example.app_giay.model.SanPham;

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

        return convertView;
    }
    public String bitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

}
