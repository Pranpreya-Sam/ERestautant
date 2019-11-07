package com.example.e_restautant;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.database.sqlite.SQLiteStatement;
import android.os.Build;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelperOrder extends SQLiteOpenHelper {
    private static String DB_NAME = "RestaurantDB.db";
    private static String DB_PATH = "";

    private SQLiteDatabase myDatabase;
    private Context myContext = null;


    public DatabaseHelperOrder(Context context) {
        super(context, DB_NAME, null, 1);
        if(Build.VERSION.SDK_INT >= 17){
            DB_PATH = context.getApplicationInfo().dataDir+"/databases/";
        }
        else{
            DB_PATH = "/data/data/"+context.getPackageName()+"/databases/";
        }
        myContext = context;
    }


    //select order that customer add from "OrderList"
    public List<Order> getCart(){
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();

        String[] sqlSelect = {"OrderID", "OrderName", "OrderPrice", "OrderType", "OrderQuality"};
        String sqlTable = "OrderList";

        builder.setTables(sqlTable);
        Cursor cursor = builder.query(db, sqlSelect, null, null, null, null, null);

        final List<Order> orders = new ArrayList<>();
        if (cursor.moveToFirst()){
            do{
                orders.add(new Order(cursor.getInt(cursor.getColumnIndex("OrderID")),
                        cursor.getString(cursor.getColumnIndex("OrderName")),
                        cursor.getInt(cursor.getColumnIndex("OrderPrice")),
                        cursor.getString(cursor.getColumnIndex("OrderType")),
                        cursor.getString(cursor.getColumnIndex("OrderQuality"))));
            }while (cursor.moveToNext());
        }
        return orders;
    }



    // insert menu that customer select to "OrderList Table"
    public boolean addCart(Order order){
        try {
            SQLiteDatabase db = getWritableDatabase();

            String strSQL = String.format("INSERT INTO OrderList(OrderID, OrderName, OrderPrice, OrderType, OrderQuality) VALUES('%d', '%s', '%d', '%s', '%s');",
                    order.getOrderID(),
                    order.getOrderName(),
                    order.getOrderPrice(),
                    order.getOrderType(),
                    order.getOrderQuality());
            db.execSQL(strSQL);
            return true;

        }catch (Exception e){
            return false;
        }
    }

    //delete menu that match with menuName that customer click
    public void deleteCart(Order order){
        SQLiteDatabase db = getWritableDatabase();
        String query = String.format("Delete From OrderList where OrderName = '%s' ",order.getOrderName());
        db.execSQL(query);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
