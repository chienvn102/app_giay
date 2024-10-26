package com.example.app_giay.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.example.app_giay.R;
import com.example.app_giay.dao.DonDatHangDAO;
import com.example.app_giay.model.OrderWithProducts;
import com.example.app_giay.model.donhang;

import java.util.ArrayList;

public class OrderWithProductsAdapter extends ArrayAdapter<OrderWithProducts> {
    private Context context;
    private int resource;
    private ArrayList<OrderWithProducts> data;

    public OrderWithProductsAdapter(Context context, int resource, ArrayList<OrderWithProducts> data) {
        super(context, resource, data);
        this.context = context;
        this.resource = resource;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DonDatHangDAO donDatHangDAO = new DonDatHangDAO(context);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(resource, parent, false);
        }

        TextView txtdh_ma = convertView.findViewById(R.id.txtdh_ma);
        TextView txtdh_noigiao = convertView.findViewById(R.id.txtdh_noigiao);
        TextView txtTrangThai = convertView.findViewById(R.id.txtTrangThai);

        OrderWithProducts currentOrder = data.get(position);

        txtdh_ma.setText("Mã đơn hàng: " + currentOrder.getDhMa());
        txtdh_noigiao.setText("Nơi giao: " + currentOrder.getDhNoiGiao());
        int tt_ma = donDatHangDAO.gettt_ma(currentOrder.getDhMa());
        if (tt_ma == 1) {
            txtTrangThai.setText("Đã Hủy Đơn Hàng");
        } else if (tt_ma == 2) {
            txtTrangThai.setText("Đang Xác Nhận");
        } else if (tt_ma == 3) {
            txtTrangThai.setText("Đang Giao Hàng");
        } else if (tt_ma == 4) {
            txtTrangThai.setText("Giao Hàng Thành Công");
        }


        convertView.setOnClickListener(view -> {
            // Tạo AlertDialog để hiển thị tất cả sản phẩm
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Chi tiết đơn hàng " + currentOrder.getDhMa());

            // Xây dựng nội dung với tất cả sản phẩm
            StringBuilder details = new StringBuilder();
            for (donhang product : currentOrder.getProducts()) {
                details.append("Tên sản phẩm: ").append(product.getSpTen()).append("\n")
                        .append("Giá: ").append(product.getSpGia()).append("\n")
                        .append("Mô tả: ").append(product.getSpMoTa()).append("\n\n");
            }

            builder.setMessage(details.toString());

            // Nút đóng
            builder.setPositiveButton("Đóng", (dialog, which) -> dialog.dismiss());

            // Hiển thị dialog
            AlertDialog dialog = builder.create();
            dialog.show();
        });

        return convertView;
    }
}

