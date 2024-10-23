package com.example.app_giay.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app_giay.R;
import com.example.app_giay.dao.SanPhamDao;
import com.example.app_giay.dao.loaiSanPhamDao;
import com.example.app_giay.model.SanPham;
import com.example.app_giay.model.loaiSanPham;

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
        SanPhamDao SP = new SanPhamDao(context);
        // Get the data item for the current position
        SanPham sanPham = data.get(position);
        TextView txtTen = convertView.findViewById(R.id.txtsp_ten);
        ImageView imgSanPham = convertView.findViewById(R.id.imgSanPham);
        ImageButton btnDelete = convertView.findViewById(R.id.btnDeleteNsx);
        ImageButton btnUpdate = convertView.findViewById(R.id.btnUpdateNsx);

        btnDelete.setOnClickListener(v -> {
            // Hiển thị hộp thoại xác nhận trước khi xóa
            new AlertDialog.Builder(context)
                    .setTitle("Xác nhận xóa")
                    .setMessage("Bạn có chắc chắn muốn xóa " + sanPham.getSp_ten() + " không?")
                    .setPositiveButton("Xóa", (dialog, which) -> {
                        // Xác định sản phẩm cần xóa dựa vào vị trí position
                        SanPham SanPhamToRemove = data.get(position);
                        // Xóa sản phẩm khỏi cơ sở dữ liệu
                        SP.deleteSanPham(SanPhamToRemove.getSp_ma());
                        // Xóa sản phẩm khỏi danh sách
                        data.remove(SanPhamToRemove);
                        // Thông báo cho Adapter biết là dữ liệu đã thay đổi và cần cập nhật giao diện
                        notifyDataSetChanged();
                        // Hiển thị thông báo xóa sản phẩm
                        Toast.makeText(context, "Đã xóa " + SanPhamToRemove.getSp_ten(), Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Hủy", null)
                    .show();
        });
        btnUpdate.setOnClickListener(v -> {
            Toast.makeText(context, "Cập nhật " + sanPham.getSp_ten(), Toast.LENGTH_SHORT).show();
        });

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


