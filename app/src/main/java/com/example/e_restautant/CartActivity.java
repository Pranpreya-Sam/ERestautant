package com.example.e_restautant;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class CartActivity extends AppCompatActivity {

    TextView totalCost;
    Button buttonCost;
    private ListView listView;
    private List<Order> orderList = new ArrayList<>();
    private CartAdapter cartAdapter;
    private int total;

    private  TextView thai;
    private  TextView english;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        thai = findViewById(R.id.thai2);
        english = findViewById(R.id.english2);

        thai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Configuration configuration = new Configuration();
                configuration.locale = new Locale("th");
                getResources().updateConfiguration(configuration, null);

                Intent intent = new Intent(CartActivity.this, CartActivity.class);
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

                Intent intent = new Intent(CartActivity.this, CartActivity.class);
                startActivity(intent);
                finish();
            }
        });

        listView = (ListView) findViewById(R.id.listview_cart);

        buttonCost = findViewById(R.id.confirm);


        //click confirm to up request order to firebase real time and change to payment page
        buttonCost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random rand = new Random();
                final int value = rand.nextInt(99999999);
                Log.d("RandNum", String.valueOf(value));

                //keep Order Request to Firebase Realtime
                OrderRequest request = new OrderRequest(value ,String.valueOf(total), orderList);

                FirebaseDatabase.getInstance().getReference("Requests")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(request)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Intent cart = new Intent(CartActivity.this, PaymentActivity.class);
                                    startActivity(cart);
                                }
                            }
                        });
            }
        });

        showOrder();
    }

    private void showOrder(){

        //get order that customer add from "OrderList" table
        orderList = new DatabaseHelperOrder(this).getCart();

        //use adapter to show menu that customer add by using listView
        cartAdapter = new CartAdapter(getApplicationContext(), orderList);

        listView.setAdapter(cartAdapter);

        //calculate total price
        total = 0;
        for (Order o: orderList){
            total += (Integer.parseInt(o.getOrderQuality())) * o.getOrderPrice();

        }

    }
}
