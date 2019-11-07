package com.example.e_restautant;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Fragment_Person extends Fragment {

    private DatabaseReference database;
    private List<User> userList;

    private TextView nameText;
    private String name;

    private TextView phoneText;
    private String phone;

    private TextView addressText;
    private String address;

    private Button track;
    private Button table;

    private  TextView thai;
    private  TextView english;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View temp = inflater.inflate(R.layout.fragment_person, null);

        nameText = temp.findViewById(R.id.person_name);
        phoneText = temp.findViewById(R.id.person_phone);
        addressText = temp.findViewById(R.id.person_address);
        track = temp.findViewById(R.id.person_tracking);
        table = temp.findViewById(R.id.person_booking);

        thai = temp.findViewById(R.id.thai5);
        english = temp.findViewById(R.id.english5);

        thai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Configuration configuration = new Configuration();
                configuration.locale = new Locale("th");
                getResources().updateConfiguration(configuration, null);

                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(getId(), new Fragment_Person());
                ft.commit();

            }
        });


        english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Configuration configuration = new Configuration();
                configuration.locale = new Locale("en");
                getResources().updateConfiguration(configuration, null);

                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(getId(), new Fragment_Person());
                ft.commit();

            }
        });


        track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), TrackingActivity.class);
                startActivity(intent);
            }
        });

        table.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), BookingDetail.class);
                startActivity(intent);
            }
        });

        database = FirebaseDatabase.getInstance().getReference("User").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        userList = new ArrayList<>();

        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                userList.add(user);
                for (User u: userList){
                    name = u.getName();
                    phone = u.getPhone();
                    address = u.getAddress();

                    nameText.setText(name);
                    phoneText.setText(phone);
                    addressText.setText(address);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return temp;
    }


}