package com.example.e_restautant;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;


public class RegisterPage extends AppCompatActivity {

    private EditText Name;
    private EditText Email;
    private EditText Password;
    private EditText ConfirmPass;
    private EditText PhoneNum;
    private EditText Address;
    private Button Regist_Btn;

    private FirebaseAuth Auth;

    private  TextView thai;
    private  TextView english;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        thai = findViewById(R.id.thai5);
        english = findViewById(R.id.english5);

        thai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Configuration configuration = new Configuration();
                configuration.locale = new Locale("th");
                getResources().updateConfiguration(configuration, null);

                Intent intent = new Intent(RegisterPage.this, RegisterPage.class);
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

                Intent intent = new Intent(RegisterPage.this, RegisterPage.class);
                startActivity(intent);
                finish();
            }
        });

        // find value that user input
        Name = findViewById(R.id.regName);
        Email = findViewById(R.id.regEmail);
        Password = findViewById(R.id.regPassword);
        ConfirmPass = findViewById(R.id.regConfimPass);
        PhoneNum = findViewById(R.id.regPhoneNum);
        Address = findViewById(R.id.regAddress);
        Regist_Btn = findViewById(R.id.regButtonRegist);

        Auth = FirebaseAuth.getInstance();

        TextView btnRegist = (TextView) findViewById(R.id.forLogin);

        btnRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterPage.this, Login.class);
                startActivity(intent);
            }
        });


        Regist_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //keep the info. into string
                String name = Name.getText().toString();
                String email = Email.getText().toString();
                String password = Password.getText().toString();
                String confirmPass = ConfirmPass.getText().toString();
                String phone = PhoneNum.getText().toString();
                String address = Address.getText().toString();
                // Check wrong input from user
                if(name.isEmpty()){
                    showMessage("Please input your name");
                }
                else if(email.isEmpty()){
                    showMessage("Please input email");
                }
                else if(password.isEmpty()){
                    showMessage("Please input password");
                }
                else if(!password.equals(confirmPass)){
                    showMessage("Please Make sure that you input correct password");
                }
                else if(phone.length() != 10){
                    showMessage("Please input your phone number");
                }
                    // User input every information
                else{
                    CreateUserAccount(name, email, password, phone, address);

                }
            }
        });
    }

    //create account with specific email and password
    private void CreateUserAccount(final String name, final String email, final String password, final String Phone, final String address) {
        //create account with email and password on firebase Authentication
        Auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(RegisterPage.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //Account is created
                if (task.isSuccessful()) {
                    User user = new User(name, email, Phone, address);
                    //User user = new User(name, email, password);

                    //up user information to firebase realtime
                    FirebaseDatabase.getInstance().getReference("User")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        showMessage("Your account is created");
                                        changePage();
                                    }
                                }
                            });

                }

                else{
                    showMessage("Cannot create your account"+task.getException().getMessage());
                    Regist_Btn.setVisibility(View.VISIBLE);
                }
            }

                });
    }


    private void changePage() {
        Intent Menu = new Intent(RegisterPage.this, Login.class);
        startActivity(Menu);
        finish();
    }

    // make text to show user
    private void showMessage(String show){
        Toast.makeText(getApplicationContext(),show,Toast.LENGTH_LONG).show();

    }
}
