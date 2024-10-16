package com.example.app_giay.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app_giay.R;
import com.example.app_giay.dao.nhaSanXuatDao;
import com.example.app_giay.model.loaiSanPham;
import com.example.app_giay.model.nhaSanXuat; // Import your nhaSanXuat model
import com.example.app_giay.view.activities.Ba.nhaSanXuat.editNsxActivity;
import com.example.app_giay.view.activities.Ba.nhaSanXuat.nsxActivity;
import java.util.ArrayList;

public class nhaSanXuatAdapter extends ArrayAdapter<nhaSanXuat> {
    private Context context;
    private int resource;
    private ArrayList<nhaSanXuat> data;

    public nhaSanXuatAdapter(Context context, int resource, ArrayList<nhaSanXuat> data) {
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

        TextView txtTen = convertView.findViewById(R.id.txtnsx_ten); // Update the ID as necessary
        ImageButton btnDelete = convertView.findViewById(R.id.btnDeleteNsx); // Update the ID as necessary
        ImageButton btnUpdate = convertView.findViewById(R.id.btnUpdateNsx); // Update the ID as necessary

        nhaSanXuat nhaSanXuat = data.get(position);
        txtTen.setText(nhaSanXuat.getNsx_ten());

        btnDelete.setOnClickListener(v -> {
            // Xác định nhà sản xuất cần xóa dựa vào vị trí position
            nhaSanXuat nhaSanXuatToRemove = data.get(position);
            // Xóa nhà sản xuất khỏi cơ sở dữ liệu
            deleteNhaSanXuat(nhaSanXuatToRemove.getNsx_ma());
            // Xóa nhà sản xuất khỏi danh sách
            data.remove(nhaSanXuatToRemove);
            // Thông báo cho Adapter biết là dữ liệu đã thay đổi và cần cập nhật giao diện
            notifyDataSetChanged();
            // Hiển thị thông báo xóa nhà sản xuất
            Toast.makeText(context, "Đã xóa " + nhaSanXuat.getNsx_ten(), Toast.LENGTH_SHORT).show();
            // Add deletion logic here
        });

        btnUpdate.setOnClickListener(v -> {
            // Hiển thị thông báo Toast
            Toast.makeText(context, "Cập nhật " + nhaSanXuat.getNsx_ten(), Toast.LENGTH_SHORT).show();
            // Tạo Intent để mở editNsxActivity và truyền dữ liệu sản phẩm
            Intent intent = new Intent(context, editNsxActivity.class);
            intent.putExtra("nsx_ma", nhaSanXuat.getNsx_ma());
            intent.putExtra("nsx_ten", nhaSanXuat.getNsx_ten());
            // Gọi startActivityForResult để nhận kết quả khi người dùng cập nhật nhà sản xuất
            ((nsxActivity) context).startActivityForResult(intent, nsxActivity.REQUEST_CODE_UPDATE_NSX);
        });

        return convertView;
    }
    private void deleteNhaSanXuat(int NsxMa) {
        nhaSanXuatDao nhaSanXuatDao = new nhaSanXuatDao(context);
        nhaSanXuatDao.deleteNhaSanXuat(NsxMa);
    }

    public void filterByMaNhaSanXuat(String query) {
        ArrayList<nhaSanXuat> filteredData = new ArrayList<>();
        if (query.isEmpty()) {
            filteredData.addAll(data); // Hiển thị tất cả sản phẩm nếu không có tìm kiếm
        } else {
            for (nhaSanXuat item : data) {
                if (String.valueOf(item.getNsx_ten()).contains(query)) {
                    filteredData.add(item);
                }
            }
        }
        // Cập nhật danh sách hiển thị
        data.clear();
        data.addAll(filteredData);
        notifyDataSetChanged();
    }
}
