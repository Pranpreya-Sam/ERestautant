package com.example.e_restautant;

import android.app.Fragment;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Locale;

public class Login extends AppCompatActivity {

    private EditText Email;
    private EditText Password;
    private Button Btn;
    private  TextView thai;
    private  TextView english;

    private FirebaseAuth Auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_login);

        Email = findViewById(R.id.logEmail);
        Password = findViewById(R.id.logPassword);
        Btn = findViewById(R.id.logButton);
        Auth = FirebaseAuth.getInstance();

        thai = findViewById(R.id.thai);
        english = findViewById(R.id.englishL);

        //change language
        thai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Configuration configuration = new Configuration();
                configuration.locale = new Locale("th");
                getResources().updateConfiguration(configuration, null);

                Intent intent = new Intent(Login.this, Login.class);
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

                Intent intent = new Intent(Login.this, Login.class);
                startActivity(intent);
                finish();
            }
        });

        TextView btnLogin = (TextView) findViewById(R.id.forRegist);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, RegisterPage.class);
                startActivity(intent);
            }
        });

        Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //keep string that input in EditText to keep in string
                String email = Email.getText().toString();
                String password = Password.getText().toString();

                if(email.isEmpty() ){
                    showMessage("Please input email");
                }
                else if(password.isEmpty()) {
                    showMessage("Please input password");
                }
                else{
                    logIn(email,password);
                }
            }
        });

    }

    private void logIn(String email, String password) {
        //log in with email and password that have already register to Firebase Authentication
        Auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //change activity if input correct email and password
                    changeUI();
                }
                else{
                    showMessage("Log In failed");
                }
            }
        });
    }

    private void changeUI() {
        //change page login to MainActivity
        Intent Menu = new Intent(Login.this, MainActivity.class);
        startActivity(Menu);
        finish();
    }

    private void showMessage(String show){
        //show message on application
        Toast.makeText(getApplicationContext(),show,Toast.LENGTH_LONG).show();
    }


}
