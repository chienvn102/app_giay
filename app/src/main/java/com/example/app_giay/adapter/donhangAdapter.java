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
        TextView txtsp_ten = convertView.findViewById(R.id.txtsp_ten);
        TextView txtsp_gia = convertView.findViewById(R.id.txtsp_gia);
        TextView txtsp_mota = convertView.findViewById(R.id.txtsp_mota);
        TextView txtdh_noigiao = convertView.findViewById(R.id.txtdh_noigiao);

        donhang currentOrder = data.get(position);

        // Hiển thị thông tin đơn hàng
        txtdh_ma.setText(String.valueOf(currentOrder.getDhMa()));
        txtsp_ten.setText("Tên sản phẩm: " + currentOrder.getSpTen());
        txtsp_gia.setText("Giá: " + currentOrder.getSpGia());
        txtsp_mota.setText("Mô tả: " + currentOrder.getSpMoTa());
        txtdh_noigiao.setText("Nơi giao: " + currentOrder.getDhNoiGiao());

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
