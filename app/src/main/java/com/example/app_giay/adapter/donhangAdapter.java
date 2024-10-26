package com.example.app_giay.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.example.app_giay.R;
import com.example.app_giay.model.donhang;

import java.util.ArrayList;

public class donhangAdapter extends ArrayAdapter<donhang> {
    private Context context;
    private int resource;
    private ArrayList<donhang> data;

    public donhangAdapter(Context context, int resource, ArrayList<donhang> data) {
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

        // Ánh xạ các thành phần trong giao diện
        TextView txtdh_ma = convertView.findViewById(R.id.txtdh_ma);
        donhang currentOrder = data.get(position);

        // Hiển thị mã đơn hàng
        txtdh_ma.setText(String.valueOf(currentOrder.getDhMa()));

        convertView.setOnClickListener(view -> {
            // Tạo AlertDialog để hiển thị chi tiết đơn hàng
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Chi tiết đơn hàng");

            // Nội dung chi tiết đơn hàng
            String details = "Tên sản phẩm: " + currentOrder.getSpTen() + "\n" +
                    "Giá: " + currentOrder.getSpGia() + "\n" +
                    "Mô tả: " + currentOrder.getSpMoTa() + "\n" +
                    "Nơi giao: " + currentOrder.getDhNoiGiao();

            builder.setMessage(details);

            // Nút đóng dialog
            builder.setPositiveButton("Đóng", (dialog, which) -> dialog.dismiss());

            // Hiển thị dialog
            AlertDialog dialog = builder.create();
            dialog.show();
        });

        return convertView;
    }

}
