package com.example.e_restautant;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TrackingActivity extends AppCompatActivity {
    private TextView trackNo;
    private TextView trackPrice;
    private TextView trackName;
    private ImageView imgButton;

    private  TextView thai;
    private  TextView english;

    private DatabaseReference database;
    private DatabaseReference database2;

    private List<OrderRequest> orderRequestList;
    private List<User> userList;
    int num;
    String price;
    String name;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking);

        thai = findViewById(R.id.thai3);
        english = findViewById(R.id.english3);

        thai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Configuration configuration = new Configuration();
                configuration.locale = new Locale("th");
                getResources().updateConfiguration(configuration, null);

                Intent intent = new Intent(TrackingActivity.this, TrackingActivity.class);
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

                Intent intent = new Intent(TrackingActivity.this, TrackingActivity.class);
                startActivity(intent);
                finish();
            }
        });

        trackNo = findViewById(R.id.tracking_num);
        trackPrice = findViewById(R.id.tracking_price);
        trackName = findViewById(R.id.tracking_name);
        imgButton = findViewById(R.id.button_home);

        database = FirebaseDatabase.getInstance().getReference("Requests").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        orderRequestList = new ArrayList<>();

        imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrackingActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        //get orderRequest from Firebase to show tracking information -> total cost and tracking number
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                OrderRequest request = dataSnapshot.getValue(OrderRequest.class);
                orderRequestList.add(request);
                for(OrderRequest r: orderRequestList){
                        num = r.getTrackingNum();
                        price = r.getTotal();

                        //Log.d("TrackingNum", String.valueOf(num));
                        trackNo.setText('#'+String.valueOf(num));
                        trackPrice.setText(price+'฿');
                    }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("Database", "Can't Retrieve");
            }
        });

        database2 = FirebaseDatabase.getInstance().getReference("User").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        userList = new ArrayList<>();

        //get User information from Firebase ti show tracking information -> user's name
        database2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                userList.add(user);
                for (User u: userList){
                    name = u.getName();
                    trackName.setText(name);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//        database.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
//                    OrderRequest request = snapshot.getValue(OrderRequest.class);
//                    orderRequestList.add(request);
//                    Log.d("Retrieve", String.valueOf(request));
//                    for(OrderRequest r: orderRequestList){
//                        track = r.getTrackingNum();
//                        Log.d("TrackingNum", String.valueOf(track));
//                        textView.setText('#'+String.valueOf(track));
//                    }
//                }
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });


    }
}
