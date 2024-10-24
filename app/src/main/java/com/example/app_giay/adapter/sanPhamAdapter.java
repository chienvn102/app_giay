package com.example.app_giay.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Base64;
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
import com.example.app_giay.model.SanPham;
import com.example.app_giay.view.activities.Ba.sanPham.editSanPhamActivity;
import com.example.app_giay.view.activities.Ba.sanPham.sanphamActivity;

import java.io.ByteArrayOutputStream;
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(resource, parent, false);
        }

        SanPhamDao SP = new SanPhamDao(context);
        SanPham sanPham = data.get(position);

        TextView txtTen = convertView.findViewById(R.id.txtsp_ten);
        ImageView imgSanPham = convertView.findViewById(R.id.imgSanPham);
        ImageButton btnDelete = convertView.findViewById(R.id.btnDeleteNsx);
        ImageButton btnUpdate = convertView.findViewById(R.id.btnUpdateNsx);

        txtTen.setOnClickListener(v -> {
            // Hiển thị AlertDialog khi click vào tên sản phẩm
            new AlertDialog.Builder(context)
                    .setTitle("Thông tin sản phẩm")
                    .setMessage("Tên sản phẩm: " + sanPham.getSp_ten() + "\n" +
                            "Giá: " + sanPham.getSp_gia() + "\n" +
                            "Số lượng: " + sanPham.getSp_soluong() + "\n" +
                            "Ngày cập nhật: " + sanPham.getSp_ngaycapnhat() + "\n" +
                            "Mô tả: " + sanPham.getSp_mota())
                    .setPositiveButton("OK", null) // Nút OK đóng dialog
                    .setNegativeButton("Hủy", null) // Nút Hủy, tùy chọn có thể không cần
                    .show();
        });



        btnDelete.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Xác nhận xóa")
                    .setMessage("Bạn có chắc chắn muốn xóa " + sanPham.getSp_ten() + " không?")
                    .setPositiveButton("Xóa", (dialog, which) -> {
                        SP.deleteSanPham(sanPham.getSp_ma());
                        data.remove(position);
                        notifyDataSetChanged();
                        Toast.makeText(context, "Đã xóa " + sanPham.getSp_ten(), Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Hủy", null)
                    .show();
        });


        btnUpdate.setOnClickListener(v -> {
            Intent intent = new Intent(context, editSanPhamActivity.class);
            intent.putExtra("sp_ma", sanPham.getSp_ma());
            intent.putExtra("sp_ten", sanPham.getSp_ten());
            intent.putExtra("sp_gia", String.valueOf(sanPham.getSp_gia()));
            intent.putExtra("sp_ngaycapnhat", sanPham.getSp_ngaycapnhat());
            intent.putExtra("sp_soluong", String.valueOf(sanPham.getSp_soluong()));
            intent.putExtra("lsp_ma", sanPham.getLsp_ma());
            intent.putExtra("nsx_ma", sanPham.getNsx_ma());
            intent.putExtra("sp_mota", sanPham.getSp_mota());
            intent.putExtra("sp_doituong", sanPham.getSp_doituong());

            // Chuyển đổi hình ảnh
            String bitmapString = bitmapToString(sanPham.getSp_hinhanh());
            intent.putExtra("sp_hinhanh", bitmapString);

            ((sanphamActivity) context).startActivityForResult(intent, sanphamActivity.REQUEST_CODE_EDIT_SAN_PHAM);
        });

        txtTen.setText(sanPham.getSp_ten());

        // Sử dụng setImageBitmap để hiển thị hình ảnh
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
