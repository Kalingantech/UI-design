package com.example.uidesign;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class PremiumActivity extends AppCompatActivity implements Recycler_interface {
    RecyclerView recyclerView;
LinearLayoutManager layoutManager;
List<User_recycle_model> userlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_premium);

        recyclerView =findViewById(R.id.xml_recyclervview);
        initData();
        initRecycleview();


        /*----Start of bottom nav*/
        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_btm);
        bottomNavigationView.setSelectedItemId(R.id.Premium);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                        overridePendingTransition(0,0);
                        break;

                    case R.id.Match:
                        startActivity(new Intent(getApplicationContext(),MatchActivity.class));
                        overridePendingTransition(0,0);
                        break;

                    case R.id.Mail:
                        startActivity(new Intent(getApplicationContext(),MailActivity.class));
                        overridePendingTransition(0,0);
                        break;

                    case R.id.Premium:
                        break;
                }
                return true;
            }
        });

        /*----End of bottom nav*/

    }

    private void initData() {
        userlist =new ArrayList<>();
        userlist.add(new User_recycle_model(R.drawable.ic_launcher_foreground,"user1","NKL","22","----------------------------"));
        userlist.add(new User_recycle_model(R.drawable.ic_launcher_foreground,"sathish","NKL","22","----------------------------"));
        userlist.add(new User_recycle_model(R.drawable.ic_launcher_foreground,"madhesh","Kpt","22","----------------------------"));
        userlist.add(new User_recycle_model(R.drawable.ic_launcher_foreground,"Kams","Erode","22","----------------------------"));
        userlist.add(new User_recycle_model(R.drawable.ic_launcher_foreground,"Ranjith","Madurai","22","----------------------------"));
        userlist.add(new User_recycle_model(R.drawable.ic_launcher_foreground,"Mother","NKL","22","----------------------------"));
        userlist.add(new User_recycle_model(R.drawable.ic_launcher_foreground,"DAD","Bedroom","22","----------------------------"));

    }

    private void initRecycleview() {
        recyclerView=findViewById(R.id.xml_recyclervview);
        layoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        Recycler_card_Adapter_old adapter_instance = new Recycler_card_Adapter_old(userlist,this);
        recyclerView.setAdapter(adapter_instance);
        adapter_instance.notifyDataSetChanged();
    }


    @Override
    public void usertemplateclick(int position) {
    Intent intent = new Intent(PremiumActivity.this,User_Profile_Activity.class);
    intent.putExtra("user1",userlist.get(position).getUsername());
    intent.putExtra("hometown",userlist.get(position).getHometown());
    startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent Premium_intent = new Intent(PremiumActivity.this,HomeActivity.class);
        startActivity(Premium_intent);
        overridePendingTransition(0,0);
    }
}