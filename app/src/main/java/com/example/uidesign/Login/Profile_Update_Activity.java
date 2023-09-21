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

import com.bumptech.glide.Glide;
import com.example.uidesign.HomeActivity;
import com.example.uidesign.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Transaction;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.ContentValues.TAG;

public class Profile_Update_Activity extends AppCompatActivity {

    private EditText profilename_ref,userage_ref,user_phoneno_ref;
    private CircleImageView profilepic_ref;
    private Button profile_save_btn,profile_pic_add_btn;
    private ProgressBar progressBar;
    private ProgressBar horizontal_progress_bar;
    private FirebaseAuth fauth;
    private FirebaseUser mcurrentuser;
    private Uri profileimageuri;
    private FirebaseFirestore db;
    private StorageReference storageReference;
    private String uid;
    private Uri downloaduri;
    private static final int PICK_IMAGE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_update);

        profilepic_ref = findViewById(R.id.xml_profile_pic);
        profilename_ref = findViewById(R.id.xml_profile_name);
        userage_ref = findViewById(R.id.xml_profile_age);
        user_phoneno_ref = findViewById(R.id.xml_profile_phoneno);
        profile_pic_add_btn =findViewById(R.id.xml_profile_pic_btn);
        profile_save_btn = findViewById(R.id.xml_profile_save);
        progressBar = findViewById(R.id.xml_progressBar);
        horizontal_progress_bar=findViewById(R.id.xml_p_create_progressbar);

        storageReference = FirebaseStorage.getInstance().getReference();
        db = FirebaseFirestore.getInstance();

        fauth = FirebaseAuth.getInstance();
        mcurrentuser = fauth.getCurrentUser();
        uid=fauth.getCurrentUser().getUid();

        progressBar.setVisibility(View.INVISIBLE);
        horizontal_progress_bar.setVisibility(View.INVISIBLE);



        StorageReference downloadpicref = storageReference.child("users/"+uid+"/profilepic.jpg");
        downloadpicref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            //load(uri) is brought from below line
            public void onSuccess(Uri uri) {

                /*below check is to use default pic if user not uploaded a pic*/
                if(uri != null){
                    Picasso.get().load(uri).into(profilepic_ref);
                }
                else
                {
                    profilepic_ref.setImageResource(R.drawable.profile_pic);
                }
                /*below check is to use default pic if user not uploaded a pic*/

                //downloaduri = uri;


            }
        });

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

                updateprofile();
            }
        });

    }


    private void updateprofile() {

        String profilename = profilename_ref.getText().toString();
        String age = userage_ref.getText().toString();
        String phoneno = user_phoneno_ref.getText().toString();
        String url = String.valueOf(downloaduri);


        final DocumentReference updatedoc_ref = db.collection("users").document(uid);
        db.runTransaction(new Transaction.Function<Void>() {
            @Override
            public Void apply(Transaction transaction) throws FirebaseFirestoreException {
                //DocumentSnapshot snapshot = transaction.get(updatedoc_ref);

                transaction.update(updatedoc_ref, "pic", url);
                transaction.update(updatedoc_ref, "username", profilename);
                transaction.update(updatedoc_ref, "age", age);
                transaction.update(updatedoc_ref, "phoneno", phoneno);


                // Success
                return null;
            }
        }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "Transaction success!");
                startActivity(new Intent(Profile_Update_Activity.this, HomeActivity.class));
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Transaction failure.", e);
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
            Toast.makeText(Profile_Update_Activity.this,"Error with picture" +e,Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadpicfstore(Uri profileimageuri) {

        StorageReference uploadpicref = storageReference.child("users/"+uid+"/profilepic.jpg");
        uploadpicref.putFile(profileimageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(Profile_Update_Activity.this,"Profile pic upload success",Toast.LENGTH_SHORT).show();
                uploadpicref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    //load(uri) is brought from below line,
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(profilepic_ref);
                        //below line will add the uri link to downloaduri string which  is used to upload to firestore DB
                        downloaduri = uri;
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });
            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Profile_Update_Activity.this,"Profile pic upload failed",Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }



    /*----To check login status start---*/
    @Override
    protected void onStart() {
        super.onStart();

            String uid = mcurrentuser.getUid();
            //This is will check if user has created the proile already
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
                        Glide.with(Profile_Update_Activity.this)
                                .load(url)
                                .placeholder(R.drawable.profile_pic)
                                .into(profilepic_ref);

                        profilename_ref.setText(name);
                        userage_ref.setText(age);
                        user_phoneno_ref.setText(phoneno);

                    }
                }
            });


    }
}