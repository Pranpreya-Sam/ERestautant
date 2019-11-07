package com.example.e_restautant;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //getting bottom navigation view and attaching the listener
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);

        if(savedInstanceState == null){
            //loading the default fragment
            loadFragment(new Fragment_Home());
        }


    }


    private boolean loadFragment(Fragment load_Fragment){
        if(load_Fragment != null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_main, load_Fragment)
                    .commit();
            return  true;
        }
        return false;
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Fragment fragment = null;

        switch (item.getItemId()){
            case R.id.navigation_table:
                fragment = new Fragment_Table();
                break;

            case R.id.navigation_home:
                fragment = new Fragment_Home();
                break;

            case R.id.navigation_menu:
                Intent Menu = new Intent(MainActivity.this, Fragment_Menu.class);
                startActivity(Menu);
                break;

            case R.id.navigation_person:
                fragment = new Fragment_Person();
                break;
        }
        return loadFragment(fragment);
    }
}
