package com.example.e_restautant;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;


public class MenuAdapter extends BaseAdapter {

    private Context context;
    private List<Menu> menuList;
    private DatabaseHelperOrder helperOrder;




    public MenuAdapter(Context context, List<Menu> menuList) {
        this.context = context;
        this.menuList = menuList;
        //this.helperOrder = helperOrder;
    }


    @Override
    public int getCount() {
        return menuList.size();
    }

    @Override
    public Object getItem(int position) {
        return menuList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return menuList.get(position).getID();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = View.inflate(context, R.layout.menu_adapter, null);
        TextView textViewName, textViewPrice, textViewType;
        Button addCart;
        final ElegantNumberButton numberButton;
        ImageView image;

        textViewName = view.findViewById(R.id.menu_name);
        textViewPrice = view.findViewById(R.id.menu_price);
        textViewType = view.findViewById(R.id.menu_type);
        addCart = view.findViewById(R.id.menu_addcart);
        numberButton = view.findViewById(R.id.number_button);
        image = view.findViewById(R.id.menu_image);

        textViewName.setText(menuList.get(position).getName());
        textViewPrice.setText(String.valueOf(menuList.get(position).getPrice())+' '+'฿');
        textViewType.setText(String.valueOf(menuList.get(position).getType()));

        //object of image is byte so we change it to BitMap
        Bitmap bmp = BitmapFactory.decodeByteArray(menuList.get(position).getPicture(),0, menuList.get(position).getPicture().length);
        image.setImageBitmap(bmp);

        Log.d("length", String.valueOf(menuList.get(position).getPicture().length));


        //click add to select menu and insert into "OrderList" Table
        helperOrder = new DatabaseHelperOrder(context);
        addCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helperOrder.addCart(new Order(menuList.get(position).getID(), menuList.get(position).getName(), menuList.get(position).getPrice() ,
                        menuList.get(position).getType(), numberButton.getNumber()));

            }
        });

        return view;
    }
}
