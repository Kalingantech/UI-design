package com.example.uidesign.Login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.uidesign.HomeActivity;
import com.example.uidesign.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {
    private FirebaseAuth newauth;
    private FirebaseUser mcurrentuser;
    private TextView splashtxt;

    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        newauth = FirebaseAuth.getInstance();
        mcurrentuser = newauth.getCurrentUser();
        splashtxt = findViewById(R.id.splashtext);
        handler = new Handler();

        //remove action bar
        //getSupportActionBar().hide();

        //remote top bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

//to start handler
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent h_intent = new Intent(SplashActivity.this,HomeActivity.class);
                startActivity(h_intent);
                finish();
            }
        },3000);
    }

}