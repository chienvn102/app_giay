package com.example.app_giay.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.app_giay.R;
import com.example.app_giay.dao.SanPhamDao;
import com.example.app_giay.dao.cartDao;
import com.example.app_giay.model.cart;

import java.util.ArrayList;

public class gioHangAdapter extends ArrayAdapter<cart> {
    private Context context;
    private ArrayList<cart> data;
    private int layoutResourceId;

    public gioHangAdapter(Context context, int layoutResourceId, ArrayList<cart> data) {
        super(context, layoutResourceId, data);
        this.context = context;
        this.data = data;
        this.layoutResourceId = layoutResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SanPhamDao sanPhamDao = new SanPhamDao(context);
        cartDao cartDao = new cartDao(context);
        int sp_ma;
        View row = convertView;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layoutResourceId, parent, false);
        }
        SharedPreferences prefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        int currentUserId = prefs.getInt("user_id", -1);
        // Tìm các view
        TextView txtTen = row.findViewById(R.id.txtsp_ten);
        TextView txtGia = row.findViewById(R.id.txtsp_gia);
        TextView txtSoLuong = row.findViewById(R.id.txtsp_soluong);
        ImageView imgHinh = row.findViewById(R.id.imgSanPham);
        ImageButton btnXoa = row.findViewById(R.id.btnDelete);
        ImageButton btnUpdate = row.findViewById(R.id.btnUpdateNsx);

        // Thiết lập dữ liệu
        sp_ma = data.get(position).getSp_ma();
        txtTen.setText(sanPhamDao.getSp_ten(sp_ma));
        txtGia.setText(String.format("%,.0f VND", sanPhamDao.getSp_gia(sp_ma)));
        txtSoLuong.setText(String.valueOf(data.get(position).getSp_soluong()));

        // Lấy ảnh sản phẩm từ CSDL và gán vào ImageView
        Bitmap bitmap = sanPhamDao.getsp_img(sp_ma);
        if (bitmap != null) {
            imgHinh.setImageBitmap(bitmap);
        } else {
            imgHinh.setImageResource(R.drawable.ic_launcher_background); // Ảnh mặc định nếu không có ảnh
        }
        // Xử lý sự kiện khi nút Xóa được nhấn
        btnXoa.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Xác nhận xóa")
                    .setMessage("Bạn có chắc muốn xóa sản phẩm này khỏi giỏ hàng không?")
                    .setPositiveButton("Xóa", (dialog, which) -> {
                        // Xóa sản phẩm nếu người dùng nhấn "Xóa"
                        cartDao.deleteCartItem(currentUserId, data.get(position).getSp_ma());
                        data.remove(position);
                        notifyDataSetChanged();
                        Toast.makeText(context, "Đã xóa sản phẩm", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss()) // Đóng dialog nếu người dùng nhấn "Hủy"
                    .show();
        });

        btnUpdate.setOnClickListener(v -> {
            // Tạo AlertDialog với một EditText để nhập số lượng mới
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Cập nhật số lượng");

            // Tạo EditText và đặt số lượng hiện tại làm giá trị mặc định
            final EditText input = new EditText(context);
            input.setInputType(InputType.TYPE_CLASS_NUMBER);
            input.setHint("Nhập số lượng mới");

            // Lấy số lượng hiện tại và thiết lập làm giá trị mặc định
            int currentQuantity = data.get(position).getSp_soluong();
            input.setText(String.valueOf(currentQuantity)); // Thiết lập số lượng hiện tại

            builder.setView(input);

            // Thiết lập nút "Cập nhật" để cập nhật số lượng
            builder.setPositiveButton("Cập nhật", (dialog, which) -> {
                String newQuantityStr = input.getText().toString().trim();

                if (!newQuantityStr.isEmpty()) {
                    int newQuantity = Integer.parseInt(newQuantityStr);

                    if (newQuantity > 0 && newQuantity <= 10) { // Kiểm tra giới hạn <= 10
                        // Cập nhật số lượng trong database
                        cartDao.updateCartItemQuantity(currentUserId, data.get(position).getSp_ma(), newQuantity);

                        // Cập nhật số lượng mới trong danh sách và cập nhật giao diện
                        data.get(position).setSp_soluong(newQuantity);
                        notifyDataSetChanged();
                        Toast.makeText(context, "Số lượng đã được cập nhật", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Số lượng phải lớn hơn 0 và không quá 10", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Vui lòng nhập số lượng", Toast.LENGTH_SHORT).show();
                }
            });

            // Thiết lập nút "Hủy"
            builder.setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss());

            // Hiển thị AlertDialog
            builder.show();
        });




        return row;
    }

}
