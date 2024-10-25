package com.example.app_giay.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.app_giay.R;
import com.example.app_giay.model.cartdetail;

import java.util.ArrayList;

public class cartAdapter extends ArrayAdapter<cartdetail> {
    private Context context;
    private int resource;
    private ArrayList<cartdetail> data;

    public cartAdapter(Context context, int resource, ArrayList<cartdetail> data) {
        super(context, resource, data);
        this.context = context;
        this.resource = resource;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(resource, null);
        }
        TextView txtsp_ten = convertView.findViewById(R.id.txtsp_ten);
        ImageView imgHinh = convertView.findViewById(R.id.imgHinh);

        cartdetail cart = data.get(position);
        txtsp_ten.setText(cart.getSp_ma());
        imgHinh.setImageResource(Integer.parseInt(cart.getSp_img()));
        return convertView;
    }

}
