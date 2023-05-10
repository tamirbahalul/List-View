package com.example.myapplicationsemester2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<Product> {


    public CustomAdapter(@NonNull Context context, ArrayList databaseAdapter) {
        super(context, R.layout.custom_row,databaseAdapter);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater li = LayoutInflater.from(getContext());
        View customView = li.inflate(R.layout.custom_row, parent, false);
        //String singleItem = getItem(position);

        String prodName = getItem(position).getName();
        String prodPrice = String.valueOf(getItem(position).getPrice());
        String prodQty = String.valueOf(getItem(position).getQty());
        String prodInfo = getItem(position).getInfo();

        TextView tvItem = (TextView)customView.findViewById(R.id.tvText);
        TextView tvInfo = (TextView)customView.findViewById(R.id.info);
        TextView tvPrice = (TextView)customView.findViewById(R.id.price);
        TextView tvQty = (TextView)customView.findViewById(R.id.qty);

        tvItem.setText(prodName);
        tvInfo.setText(prodInfo);
        tvPrice.setText(prodPrice);
        tvQty.setText(prodQty);
        return customView;
    }
}
