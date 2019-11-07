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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PaymentActivity extends AppCompatActivity {
    RadioGroup radioGroup;
    RadioButton radioButton;
    TextView button;
    EditText cardNo;
    EditText cardCCV;
    EditText cardExpire;
    Button btn_credit;

    String number;
    String cCV;
    String expire;

    TextView totalCost;
    private int total;
    private List<Order> orderList;

    private  TextView thai;
    private  TextView english;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        radioGroup = findViewById(R.id.radioGroup);
        cardNo = findViewById(R.id.creditcard_no);
        cardCCV = findViewById(R.id.creditcard_ccv);
        cardExpire = findViewById(R.id.creditcard_date);
        btn_credit =findViewById(R.id.button_confirm_credit);
        button = findViewById(R.id.button_payment);


        cardNo.setVisibility(View.INVISIBLE);
        cardCCV.setVisibility(View.INVISIBLE);
        cardExpire.setVisibility(View.INVISIBLE);
        btn_credit.setVisibility(View.INVISIBLE);

        thai = findViewById(R.id.thai4);
        english = findViewById(R.id.english4);

        thai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Configuration configuration = new Configuration();
                configuration.locale = new Locale("th");
                getResources().updateConfiguration(configuration, null);

                Intent intent = new Intent(PaymentActivity.this, PaymentActivity.class);
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

                Intent intent = new Intent(PaymentActivity.this, PaymentActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //after finish payment go to tracking
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pay = new Intent(PaymentActivity.this, TrackingActivity.class);
                startActivity(pay);
            }
        });

        totalCost = findViewById(R.id.total_cost);

        //calculate total price
        total = 0;
        orderList = new DatabaseHelperOrder(this).getCart();
        for (Order o: orderList){
            total += (Integer.parseInt(o.getOrderQuality())) * o.getOrderPrice();

            Locale locale = new Locale("en", "US");
            NumberFormat numberFormat = NumberFormat.getNumberInstance(locale);

            totalCost.setText(numberFormat.format(total)+' '+'฿');

        }
        Log.d("Total", String.valueOf(total));
    }

    public void checkButton(View v){
        int radioID = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioID);

        if(radioButton == findViewById(R.id.pay_home)){
            Toast.makeText(getApplicationContext(),"Pay at home",Toast.LENGTH_LONG).show();

            //set credit card information to be invisible
            cardNo.setVisibility(View.INVISIBLE);
            cardCCV.setVisibility(View.INVISIBLE);
            cardExpire.setVisibility(View.INVISIBLE);
            btn_credit.setVisibility(View.INVISIBLE);
        }

        if(radioButton == findViewById(R.id.pay_creditcard)){
            cardNo.setVisibility(View.VISIBLE);
            cardCCV.setVisibility(View.VISIBLE);
            cardExpire.setVisibility(View.VISIBLE);
            btn_credit.setVisibility(View.VISIBLE);


            //up information of credit card to Firebase real time
            btn_credit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    number = cardNo.getText().toString();
                    cCV = cardCCV.getText().toString();
                    expire = cardExpire.getText().toString();
                    CreditCard creditCard = new CreditCard(number, cCV, expire);
                    FirebaseDatabase.getInstance().getReference("CreditCard")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(creditCard)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getApplicationContext(),"Pay by Credit Card",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
            });


        }
    }
}
