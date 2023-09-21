package com.example.uidesign.Login;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.uidesign.HomeActivity;
import com.example.uidesign.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.ContentValues.TAG;

public class Profile_Create_Activity extends AppCompatActivity {

    private EditText profilename_ref,userage_ref,user_phoneno_ref;
    private CircleImageView profilepic_ref;
    private Button profile_save_btn,profile_pic_add_btn;
    private ProgressBar progressBar;
    private ProgressBar horizontal_progress_bar;
    private FirebaseAuth fauth;
    private Uri profileimageuri;
    private FirebaseFirestore db;
    private StorageReference storageReference;
    private String uid;
    private Uri downloaduri;
    private static final int PICK_IMAGE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_create);

        profilepic_ref = findViewById(R.id.xml_profile_pic);
        profilename_ref = findViewById(R.id.xml_profile_name);
        userage_ref = findViewById(R.id.xml_profile_age);
        user_phoneno_ref = findViewById(R.id.xml_profile_phoneno);
        profile_pic_add_btn =findViewById(R.id.xml_profile_pic_btn);
        profile_save_btn = findViewById(R.id.xml_profile_save);
        progressBar = findViewById(R.id.xml_progressBar);
        horizontal_progress_bar=findViewById(R.id.xml_p_create_progressbar);


        fauth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        db = FirebaseFirestore.getInstance();
        uid=fauth.getCurrentUser().getUid();



        progressBar.setVisibility(View.INVISIBLE);
        horizontal_progress_bar.setVisibility(View.INVISIBLE);


        /*StorageReference downloadpicref = storageReference.child("users/"+uid+"/profilepic.jpg");
        downloadpicref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            //load(uri) is brought from below line
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profilepic_ref);
                downloaduri = uri;
//below line will add the uri link to downloaduri string which  is used to upload to firestore DB

            }
        });*/


        profile_pic_add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,PICK_IMAGE);

            }
        });

        profile_save_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String profilename = profilename_ref.getText().toString();
                String age = userage_ref.getText().toString();
                String phoneno = user_phoneno_ref.getText().toString();
                horizontal_progress_bar.setVisibility(View.VISIBLE);

                DocumentReference documentReference = db.collection("users").document(uid);
                /*HashMap<String , Object> profile = new HashMap<>();
                profile.put("username",profilename);
                profile.put("age",age);
                profile.put("phoneno",phoneno);
                profile.put("pic",String.valueOf(downloaduri));*/

                Profile_Template profile_template = new Profile_Template(profilename,age,phoneno,String.valueOf(downloaduri));

                documentReference.set(profile_template).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(Profile_Create_Activity.this,"Profile  upload success",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Profile_Create_Activity.this, HomeActivity.class));
                        finish();
                        horizontal_progress_bar.setVisibility(View.INVISIBLE);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        Toast.makeText(Profile_Create_Activity.this,"Profile upload failed",Toast.LENGTH_SHORT).show();
                        horizontal_progress_bar.setVisibility(View.INVISIBLE);
                        Log.d(TAG,""+e.toString());
                    }
                });

            }
        });

    }



    //Below code will allow user to select photo from local storage with crop option
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try{
            if(requestCode == PICK_IMAGE || resultCode == RESULT_OK || data != null || data.getData() != null){

                Uri profileimageuri = data.getData();
                profilepic_ref.setImageURI(profileimageuri);
                uploadpicfstore(profileimageuri);
                progressBar.setVisibility(View.VISIBLE);

            }
        }
        catch (Exception e){
            Toast.makeText(Profile_Create_Activity.this,"Error with picture" +e,Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadpicfstore(Uri profileimageuri) {

        StorageReference uploadpicref = storageReference.child("users/"+uid+"/profilepic.jpg");
        uploadpicref.putFile(profileimageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(Profile_Create_Activity.this,"Profile pic upload success",Toast.LENGTH_SHORT).show();
                uploadpicref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    //load(uri) is brought from below line,
                    public void onSuccess(Uri uri) {
                       // Picasso.get().load(uri).into(profilepic_ref);
                        downloaduri = uri;
                        //below line will add the uri link to downloaduri string which  is used to upload to firestore DB
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });
            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Profile_Create_Activity.this,"Profile pic upload failed",Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }


}