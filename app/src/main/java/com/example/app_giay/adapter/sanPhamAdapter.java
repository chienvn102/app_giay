package com.example.app_giay.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.app_giay.R;
import com.example.app_giay.model.SanPham;

import java.util.ArrayList;

public class sanPhamAdapter extends ArrayAdapter<SanPham> {
    private Context context;
    private int resource;
    private ArrayList<SanPham> data;

    public sanPhamAdapter(Context context, int resource, ArrayList<SanPham> data) {
        super(context, resource, data);
        this.context = context;
        this.resource = resource;
        this.data = data;
    }
    // Implement the necessary methods here
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(resource, parent, false);
        }

        // Get the data item for the current position
        SanPham sanPham = data.get(position);
        TextView txtTen = convertView.findViewById(R.id.txtsp_ten);
        ImageView imgSanPham = convertView.findViewById(R.id.imgSanPham);

        txtTen.setText(sanPham.getSp_ten());

        // Sử dụng setImageBitmap để hiển thị hình ảnh
        if (sanPham.getSp_hinhanh() != null) {
            imgSanPham.setImageBitmap(sanPham.getSp_hinhanh());
        } else {
            // Xử lý trường hợp không có hình ảnh (có thể đặt hình ảnh mặc định hoặc ẩn ImageView)
            imgSanPham.setImageResource(R.drawable.ic_launcher_background); // Thay default_image bằng hình ảnh mặc định bạn muốn
        }

        return convertView;
    }

}
