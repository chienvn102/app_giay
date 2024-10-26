package com.example.app_giay.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.example.app_giay.R;
import com.example.app_giay.dao.DonDatHangDAO;
import com.example.app_giay.database.DatabaseHelper;
import com.example.app_giay.model.donhang;

import java.util.ArrayList;

public class donHanAdminAdapter extends ArrayAdapter<donhang> {
    private Context context;
    private int resource;
    private ArrayList<donhang> data;

    public donHanAdminAdapter(Context context, int resource, ArrayList<donhang> data) {
        super(context, resource, data);
        this.context = context;
        this.resource = resource;
        this.data = data;
    }
    // Các phương thức khác của adapter
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(resource, parent, false);
        }

        // Ánh xạ các thành phần trong giao diện
        DonDatHangDAO donDatHangDAO = new DonDatHangDAO(context);
        TextView txtdh_ma = convertView.findViewById(R.id.txtdh_ma);
        TextView txtdh_tt = convertView.findViewById(R.id.txtdh_tt);
        TextView txtDon = convertView.findViewById(R.id.txtDon);
        Button btnEdit = convertView.findViewById(R.id.btnEdit);

        donhang currentOrder = data.get(position);

        // Hiển thị mã đơn hàng và trạng thái
        txtdh_ma.setText(String.valueOf(currentOrder.getDhMa()));
        txtdh_tt.setText(String.valueOf(currentOrder.getTt_ma()));
        btnEdit.setOnClickListener(view -> {
            // Danh sách các trạng thái đơn hàng
            String[] statuses = {"Đã Hủy Đơn Hàng", "Đang Xác Nhận", "Đang Giao Hàng", "Giao Hàng Thành Công"};

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Chọn trạng thái đơn hàng");

            // Đặt Adapter cho danh sách trạng thái
            builder.setItems(statuses, (dialog, which) -> {
                String selectedStatus = statuses[which];

                // Chọn giá trị tt_ma tương ứng với trạng thái
                int tt_ma;
                switch (selectedStatus) {
                    case "Đã Hủy Đơn Hàng":
                        tt_ma = 1; // Ví dụ giá trị cho "Đã Hủy Đơn Hàng"
                        break;
                    case "Đang Xác Nhận":
                        tt_ma = 2; // Ví dụ giá trị cho "Đang Xác Nhận"
                        break;
                    case "Đang Giao Hàng":
                        tt_ma = 3; // Ví dụ giá trị cho "Đang Giao Hàng"
                        break;
                    case "Giao Hàng Thành Công":
                        tt_ma = 4; // Ví dụ giá trị cho "Giao Hàng Thành Công"
                        break;
                    default:
                        tt_ma = -1; // Giá trị mặc định nếu không có trạng thái nào khớp
                        break;
                }

                int dhMa = Integer.parseInt(currentOrder.getDhMa());
                donDatHangDAO.updateDonDatHang(dhMa, tt_ma);

                // Cập nhật trạng thái trong đối tượng currentOrder
                currentOrder.setTt_ma(tt_ma); // Giả sử bạn đã có phương thức này

                // Cập nhật lại giao diện
                notifyDataSetChanged();

            });

            builder.setNegativeButton("Hủy", (dialog, which) -> dialog.cancel());

            AlertDialog dialog = builder.create();
            dialog.show();
        });





        // Thiết lập sự kiện OnClick cho txtDon
        txtDon.setOnClickListener(view -> {
            // Tạo AlertDialog để hiển thị chi tiết đơn hàng
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Chi tiết đơn hàng");

            // Nội dung chi tiết đơn hàng
            String details = "Mã đơn hàng: " + currentOrder.getDhMa() + "\n" +
                    "Trạng thái: " + currentOrder.getTt_ma() + "\n" +
                    "Tên sản phẩm: " + currentOrder.getSpTen() + "\n" +
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
