package com.example.e_restautant;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BookingDetail extends AppCompatActivity {

    private List<Table> tableList = new ArrayList<>();
    private  TextView thai;
    private  TextView english;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_detail);

        thai = findViewById(R.id.thai6);
        english = findViewById(R.id.english6);

        thai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Configuration configuration = new Configuration();
                configuration.locale = new Locale("th");
                getResources().updateConfiguration(configuration, null);

                Intent intent = new Intent(BookingDetail.this, BookingDetail.class);
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

                Intent intent = new Intent(BookingDetail.this, BookingDetail.class);
                startActivity(intent);
                finish();
            }
        });

        tableList = new DatabaseHelperTable(this).getTable();


        TextView textAmount, textDate, textTime, textSpecial;

        textAmount = findViewById(R.id.table_amount);
        textDate = findViewById(R.id.table_date);
        textTime = findViewById(R.id.table_time);
        textSpecial = findViewById(R.id.table_special);

        for (Table t: tableList){
            textAmount.setText(t.getAmount());
            textDate.setText(t.getDate());
            textTime.setText(t.getTime());
            textSpecial.setText(t.getRequest());
        }

    }
}
