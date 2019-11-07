package com.example.e_restautant;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.os.Build;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelperPork extends SQLiteOpenHelper {

    private static String DB_NAME = "RestaurantDB.db";
    private static String DB_PATH = "";


    private SQLiteDatabase myDatabase;
    private Context myContext = null;

    public DatabaseHelperPork(Context context) {
        super(context, DB_NAME, null, 1);
        if(Build.VERSION.SDK_INT >= 17){
            DB_PATH = context.getApplicationInfo().dataDir+"/databases/";
        }
        else{
            DB_PATH = "/data/data/"+context.getPackageName()+"/databases/";
        }
        myContext = context;
    }

//    @Override
//    public synchronized void close() {
//        if(myDatabase != null){
//            myDatabase.close();
//        }
//        super.close();
//    }
//
//    private boolean checkDB(){
//        SQLiteDatabase tempDB = null;
//        try{
//            String path = DB_PATH+DB_NAME;
//            tempDB = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
//        }catch (Exception e) {
//
//        }
//
//        if (tempDB != null){
//            tempDB.close();
//        }
//        return tempDB!=null?true:false;
//    }

    public void copyDB(){
        try{
            InputStream myInput = myContext.getAssets().open(DB_NAME);
            String outputFileName = DB_PATH+DB_NAME;
            OutputStream myOutput = new FileOutputStream(outputFileName);

            byte[] buffer = new byte[1024];
            int length;

            while ((length = myInput.read(buffer)) > 0){
                myOutput.write(buffer, 0, length);
            }
            myOutput.flush();
            myOutput.close();
            myInput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public void openDB(){
//        String path = DB_PATH+DB_NAME;
//        myDatabase = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
//    }
//
//    public void creaeDB(){
//        boolean isDBExist = checkDB();
//        if (isDBExist){
//
//        }
//        else{
//            this.getReadableDatabase();
//            try{
//                copyDB();
//            }
//            catch (Exception e){
//
//            }
//        }
//    }

    public List<Menu> getListMenu(){
        List<Menu> temp = new ArrayList<Menu>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor;
        try{
            //read from table "Menu" from Restaurant databse
            cursor = db.rawQuery("SELECT * FROM Menu", null);
            if (cursor == null){
                return null;
            }

            //begin with first row of table
            cursor.moveToFirst();

            //read to next row until it doesn't have
            do{
                Menu menu = new Menu(cursor.getInt(cursor.getColumnIndex("ID")), cursor.getString(cursor.getColumnIndex("Name")),
                        cursor.getInt(cursor.getColumnIndex("Price")), cursor.getString(cursor.getColumnIndex("Type")),
                        cursor.getBlob(cursor.getColumnIndex("Picture")));
                temp.add(menu);
            }while (cursor.moveToNext());
            cursor.close();
        } catch (Exception e){

        }
        db.close();
        return temp;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
