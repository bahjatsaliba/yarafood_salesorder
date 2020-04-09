package com.yarafood.shop.yarafoodmodul;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import entities.SalesOrder;

public class OrderListAdapter extends ArrayAdapter<SalesOrder> {

    private int groupid;
    private ArrayList<SalesOrder> salesOrders;
    private Context context;

    public OrderListAdapter(Context context, ArrayList<SalesOrder> salesOrders) {

        super(context, 0, salesOrders);
        this.context = context;
        this.salesOrders = salesOrders;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        SalesOrder salesOrder = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item,
                    parent,
                    false);
        }
        convertView.setTag(salesOrder);
        TextView txtVID = (TextView) convertView.findViewById(R.id.txtVID);
        TextView txtVCustomer = (TextView) convertView.findViewById(R.id.txtVCustomer);
        TextView txtVStatus = (TextView) convertView.findViewById(R.id.txtVStatus);

        txtVID.setText(String.valueOf(salesOrder.getId()));
        txtVCustomer.setText(salesOrder.getCustomer());
        txtVStatus.setText(salesOrder.getSalesOrderStatus().toString());

        return convertView;
    }

    public void clear(){
        clear();
    }
}

