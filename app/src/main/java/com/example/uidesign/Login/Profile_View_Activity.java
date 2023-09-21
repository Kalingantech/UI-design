package com.example.uidesign.Login;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.uidesign.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.NotNull;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile_View_Activity extends AppCompatActivity {
    private CircleImageView profilepic_ref;
    private Button profile_update_btn;
    private TextView name_ref,age_ref,phoneno_ref;
    private FirebaseAuth fauth;
    private String uid;
    private Uri profileimageuri;
    private FirebaseUser mcurrentuser;
    private FirebaseFirestore db;
    private StorageReference storageReference;
    private Uri downloaduri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_view);
        profilepic_ref = findViewById(R.id.xml_profile_pic);
        name_ref = findViewById(R.id.xml_profile_name);
        age_ref = findViewById(R.id.xml_profile_age);
        phoneno_ref = findViewById(R.id.xml_profile_phoneno);
        profile_update_btn = findViewById(R.id.xml_profile_update);
        fauth = FirebaseAuth.getInstance();
        mcurrentuser = fauth.getCurrentUser();
        storageReference = FirebaseStorage.getInstance().getReference();
        db = FirebaseFirestore.getInstance();
        String uid = mcurrentuser.getUid();


        //This download the firestore user details
        DocumentReference documentReference = db.collection("users").document(uid);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                if(task.getResult().exists()){
                    String url = task.getResult().getString("pic");
                    String name = task.getResult().getString("username");
                    String age = task.getResult().getString("age");
                    String phoneno = task.getResult().getString("phoneno");

                    //Picasso.get().load(url).into(profilepic_ref);
                    //Glide helps us to provide a default pic if there is no pic uploaded to firestore
                    Glide.with(Profile_View_Activity.this)
                            .load(url)
                            .placeholder(R.drawable.profile_pic)
                            .into(profilepic_ref);

                    name_ref.setText(name);
                    age_ref.setText(age);
                    phoneno_ref.setText(phoneno);

                }
            }
        });

        profile_update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Profile_View_Activity.this, Profile_Update_Activity.class));
            }
        });

    }


}