package com.example.uidesign;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MatchActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);

        recyclerView =findViewById(R.id.xml_recyclervview);
        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_btm);



        /*----Start of bottom nav*/
        bottomNavigationView.setSelectedItemId(R.id.Match);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                        overridePendingTransition(0,0);
                        break;

                    case R.id.Match:
                        break;

                    case R.id.Mail:
                        startActivity(new Intent(getApplicationContext(),MailActivity.class));
                        overridePendingTransition(0,0);
                        break;

                    case R.id.Premium:
                        startActivity(new Intent(getApplicationContext(),PremiumActivity.class));
                        overridePendingTransition(0,0);
                        break;
                }
                return true;
            }
        });
        /*----End of bottom nav*/
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent Match_intent = new Intent(MatchActivity.this,HomeActivity.class);
        startActivity(Match_intent);
        overridePendingTransition(0,0);
    }
}