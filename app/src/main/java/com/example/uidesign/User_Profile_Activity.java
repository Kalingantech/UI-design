package com.example.uidesign;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class User_Profile_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        String name = getIntent().getStringExtra("user1");
        String home = getIntent().getStringExtra("hometown");
        TextView  username = findViewById(R.id.xml_profile_id);
        TextView  userhome = findViewById(R.id.xml_profile_home);
        username.setText(name);
        userhome.setText(home);
    }
}