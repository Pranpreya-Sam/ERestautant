package com.example.e_restautant;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;


import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;


public class CartAdapter extends BaseAdapter {

    private Context context;
    private  List<Order> orderList;
    private DatabaseHelperOrder helperOrder;


    public CartAdapter(Context context, List<Order> orderList) {
        this.context = context;
        this.orderList = orderList;
    }


    @Override
    public int getCount() {
        return orderList.size();
    }

    @Override
    public Object getItem(int position) {
        return orderList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return orderList.get(position).getOrderID();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = View.inflate(context, R.layout.cart_adapter, null);

        TextView textCartName, textCartPrice, textCartType, textCartAmount;
        Button deleteCart;

        textCartName = (TextView) view.findViewById(R.id.cart_name);
        textCartPrice = (TextView) view.findViewById(R.id.cart_price);
        textCartAmount = (TextView) view.findViewById(R.id.cart_quality);
        textCartType = (TextView) view.findViewById(R.id.cart_type);
        deleteCart = view.findViewById(R.id.button_deletecart);

        Locale locale = new Locale("en", "US");
        NumberFormat numberFormat = NumberFormat.getNumberInstance(locale);

        int price = (Integer.parseInt(orderList.get(position).getOrderQuality())) * orderList.get(position).getOrderPrice();
        textCartPrice.setText(numberFormat.format(price)+' '+'฿');

        textCartName.setText(orderList.get(position).getOrderName());
        textCartType.setText(orderList.get(position).getOrderType());
        textCartAmount.setText(orderList.get(position).getOrderQuality());

        helperOrder = new DatabaseHelperOrder(context);

        //delete menu from "OrderList" Table
        deleteCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helperOrder.deleteCart(new Order(orderList.get(position).getOrderID(), orderList.get(position).getOrderName(), orderList.get(position).getOrderPrice(),
                        orderList.get(position).getOrderType(), orderList.get(position).getOrderType()));
                orderList.remove(position);

                //delete menu from interface
                notifyDataSetChanged();
            }
        });

        return view;
    }
}

