package com.example.e_restautant;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

public class Fragment_Menu extends AppCompatActivity {

//    private ListView listView;
    private GridView gridView;
    private DatabaseHelperPork databaseHelper;
    private List<Menu> menuList;
    private MenuAdapter menuAdapter;

    private DatabaseHelperOrder helperOrder;

    FloatingActionButton floatingActionButton;

    private TextView thai;
    private TextView english;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_view_menu);

        thai = findViewById(R.id.thai1);
        english = findViewById(R.id.english1);

        thai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Configuration configuration = new Configuration();
                configuration.locale = new Locale("th");
                getResources().updateConfiguration(configuration, null);

                Intent intent = new Intent(Fragment_Menu.this, Fragment_Menu.class);
                startActivity(intent);
                finish();
            }
        });


        english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Configuration configuration = new Configuration();
                configuration.locale = new Locale("en");
                getResources().updateConfiguration(configuration, null);

                Intent intent = new Intent(Fragment_Menu.this, Fragment_Menu.class);
                startActivity(intent);
                finish();
            }
        });

        gridView = (GridView) findViewById(R.id.gridview_menu);

        //use databaseHelper to open Restaurant.db
        databaseHelper = new DatabaseHelperPork(this);
        databaseHelper.copyDB();

        //select menu from SQLite Database
        menuList = databaseHelper.getListMenu();

        //Adapter of grid view -> show all menu in db to application
        //menuList = list of menu that read from db
        menuAdapter = new MenuAdapter(this, menuList);
        gridView.setAdapter(menuAdapter);

        helperOrder = new DatabaseHelperOrder(getApplicationContext());
        floatingActionButton = findViewById(R.id.floating_cart);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //go to cart activity
                Intent cart = new Intent(Fragment_Menu.this, CartActivity.class);
                startActivity(cart);
            }
        });


    }


}
