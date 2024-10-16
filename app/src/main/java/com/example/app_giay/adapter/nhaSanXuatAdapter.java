package com.example.app_giay.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app_giay.R;
import com.example.app_giay.model.nhaSanXuat; // Import your nhaSanXuat model

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
            Toast.makeText(context, "Xóa " + nhaSanXuat.getNsx_ten(), Toast.LENGTH_SHORT).show();
            // Add deletion logic here
        });

        btnUpdate.setOnClickListener(v -> {
            Toast.makeText(context, "Cập nhật " + nhaSanXuat.getNsx_ten(), Toast.LENGTH_SHORT).show();
            // Add update logic here
        });

        return convertView;
    }
}
