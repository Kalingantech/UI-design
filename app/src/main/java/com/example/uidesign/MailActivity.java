package com.example.uidesign;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TableLayout;

import com.example.uidesign.Tab_Layout.Tab_Adapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.tabs.TabLayout;

public class MailActivity extends AppCompatActivity {


    TabLayout tablayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail);

        tablayout = findViewById(R.id.tablayout);
        viewPager = findViewById(R.id.viewpgr);


        /*----Start of Tab layout */
        tablayout.addTab(tablayout.newTab().setText("First"));
        tablayout.addTab(tablayout.newTab().setText("Second"));
        tablayout.addTab(tablayout.newTab().setText("Third"));
        tablayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final Tab_Adapter tab_adapter = new Tab_Adapter(getSupportFragmentManager(), this, tablayout.getTabCount());
        viewPager.setAdapter(tab_adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tablayout));

        //below is 
        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        /*----end of Tab layout */


        /*----Start of bottom nav*/
        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_btm);


        bottomNavigationView.setSelectedItemId(R.id.Mail);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        overridePendingTransition(0, 0);
                        break;

                    case R.id.Match:
                        startActivity(new Intent(getApplicationContext(), MatchActivity.class));
                        overridePendingTransition(0, 0);
                        break;

                    case R.id.Mail:
                        break;

                    case R.id.Premium:
                        startActivity(new Intent(getApplicationContext(), PremiumActivity.class));
                        overridePendingTransition(0, 0);
                        break;
                }
                return true;
            }
        });

        /*----end of bottom nav*/
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent Premium_intent = new Intent(MailActivity.this, HomeActivity.class);
        startActivity(Premium_intent);
        overridePendingTransition(0, 0);
    }
}