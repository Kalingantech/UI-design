package com.example.uidesign;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.uidesign.Login.LoginActivity;
import com.example.uidesign.Login.Profile_Create_Activity;
import com.example.uidesign.Login.Profile_View_Activity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import de.hdodenhof.circleimageview.CircleImageView;


public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private CircleImageView profilepic_ref;
    private TextView name_ref;
    private FirebaseAuth newauth;
    private FirebaseUser mcurrentuser;
    private Button logoutbtn, viewprofilebtn;
    private FirebaseFirestore db;
    private String uid;
    private ImageButton imageButton;

    DrawerLayout drawerLayout;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        profilepic_ref = findViewById(R.id.xml_profile_pic);
        name_ref = findViewById(R.id.xml_profile_name);
        viewprofilebtn=findViewById(R.id.xml_view_profile_btn);

        newauth = FirebaseAuth.getInstance();
        mcurrentuser = newauth.getCurrentUser();
        logoutbtn =findViewById(R.id.logout_btn);
        db = FirebaseFirestore.getInstance();
        drawerLayout = findViewById(R.id.drawer_nav_layout);
        navigationView = findViewById(R.id.nav_view);
        imageButton =findViewById(R.id.xml_drawer);

        load_nav_drawer();

        viewprofilebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, Profile_View_Activity.class));
            }
        });

        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newauth.signOut();
                sendusertologin();
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_btm);

        /*----Start of bottom nav*/
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.home:
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
                        startActivity(new Intent(getApplicationContext(),PremiumActivity.class));
                        overridePendingTransition(0,0);
                        break;
                }
                return true;
            }
        });
        /*----End of bottom nav*/
    }

    private void load_nav_drawer() {

        /*--Tool bar end--*/
        /*--Draw navigation start--*/
        /*-- Hooks--*/
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerVisible(GravityCompat.START)){
                    drawerLayout.closeDrawer(GravityCompat.START);
                }else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });

    }

    /*----To check login status start---*/
    @Override
    protected void onStart() {
        super.onStart();
        //check if this is new user
        if(mcurrentuser == null){
            sendusertologin();
        }
        else
        {
            String uid = mcurrentuser.getUid();
            //This is will check if user has created the proile already
            DocumentReference documentReference = db.collection("users").document(uid);
            documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                    if(task.getResult().exists()){
                        String url = task.getResult().getString("pic");
                        String name = task.getResult().getString("username");

                        //Picasso.get().load(url).into(profilepic_ref);
                        //Glide helps us to provide a default pic if there is no pic uploaded to firestore
                        Glide.with(HomeActivity.this)
                                .load(url)
                                .placeholder(R.drawable.profile_pic)
                                .into(profilepic_ref);

                        name_ref.setText(name);
                    }
                    else
                    {
                        startActivity(new Intent(HomeActivity.this, Profile_Create_Activity.class));
                        finish();
                    }
                }
            });
        }

    }

    private void sendusertologin(){
        Intent loginintent = new Intent(HomeActivity.this, LoginActivity.class);
        loginintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        loginintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginintent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
        finish();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.rate_draw_nav:
                Toast.makeText(getApplicationContext(),"Rate",Toast.LENGTH_SHORT).show();
                break;             /*--because we are already in home--*/

            case R.id.share_draw_nav:
                /*Intent sharingintent = new Intent(Intent.ACTION_SEND);;
                sharingintent.setType("text/plain");
                String shareBody="https://play.google.com/store/apps/details?id=com.kalingantech.tnpsctopper";
                sharingintent.putExtra(Intent.EXTRA_TEXT,shareBody);
                startActivity(Intent.createChooser(sharingintent,"Share using"));*/
                Toast.makeText(getApplicationContext(),"Share",Toast.LENGTH_SHORT).show();
                break;

            case R.id.other_app:
                //startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://play.google.com/store/apps/developer?id=Kalingan+Tech")));
                Toast.makeText(getApplicationContext(),"Other Apps",Toast.LENGTH_SHORT).show();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

}