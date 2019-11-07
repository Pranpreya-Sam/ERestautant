package com.example.e_restautant;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.database.sqlite.SQLiteStatement;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelperTable extends SQLiteOpenHelper{

    private static String DB_NAME = "RestaurantDB.db";
    private static String DB_PATH = "";

    private Context myContext = null;

    public DatabaseHelperTable(Context context){
        super(context,DB_NAME,null,1);
        if(Build.VERSION.SDK_INT >= 17){
            DB_PATH = context.getApplicationInfo().dataDir+"/databases/";
        }
        else{
            DB_PATH = "/data/data/"+context.getPackageName()+"/databases/";
        }
        myContext = context;
    }

    public List<Table> getTable(){
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();

        String[] sqlSelect = {"Amount", "Date", "Time", "Request"};
        String sqlTable = "Booking";

        builder.setTables(sqlTable);
        Cursor cursor = builder.query(db, sqlSelect, null, null, null, null, null);

        final List<Table> table = new ArrayList<>();
        if (cursor.moveToLast()){
//            do{
                table.add(new Table(cursor.getString(cursor.getColumnIndex("Amount")),
                        cursor.getString(cursor.getColumnIndex("Date")),
                        cursor.getString(cursor.getColumnIndex("Time")),
                        cursor.getString(cursor.getColumnIndex("Request"))));

//            }while (cursor.moveToNext());
        }
        return table;
    }


    public boolean addTable(Table table){
        try {
            SQLiteDatabase db = getWritableDatabase();

            String sql = String.format("Insert Into Booking(Amount, Date, Time, Request) VALUES ('%s', '%s', '%s', '%s')",
                    table.getAmount(),
                    table.getDate(),
                    table.getTime(),
                    table.getRequest());
            db.execSQL(sql);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public void deleteTable(Table table){
        SQLiteDatabase db = getWritableDatabase();
        String query = String.format("Delete From Booking ");
        db.execSQL(query);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}