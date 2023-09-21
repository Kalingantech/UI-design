package com.example.uidesign.Login;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.uidesign.R;
import com.example.uidesign.HomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;

public class LoginActivity extends AppCompatActivity {

    private Button signup_btn,signin_btn;
    private ProgressBar mloginprogress;
    private FirebaseAuth fauth;
    private FirebaseUser fcurrentuser;
    private EditText email_txt, password_txt ;
    private CheckBox show_pswd_checkbox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signup_btn = findViewById(R.id.xml_Register);
        email_txt = findViewById(R.id.xml_mailID);
        password_txt = findViewById(R.id.xml_password);
        signin_btn = findViewById(R.id.xml_signin);
        mloginprogress = findViewById(R.id.xml_login_progressbar);
        show_pswd_checkbox =findViewById(R.id.xml_showpassword);
        fauth = FirebaseAuth.getInstance();

        //this method to enable show pssword
        show_pswd_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    password_txt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else{
                    password_txt.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });


        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, com.example.uidesign.Login.SignupActivity.class));
            }
        });

        signin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailID = email_txt.getText().toString();
                String password = password_txt.getText().toString();
                mloginprogress.setVisibility(View.VISIBLE);

                if(!emailID.isEmpty() && !password.isEmpty() ){
                    fauth.signInWithEmailAndPassword(emailID,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(LoginActivity.this,"Login successful",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                                finish();
                                mloginprogress.setVisibility(View.INVISIBLE);
                            }else{
                                Toast.makeText(LoginActivity.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                mloginprogress.setVisibility(View.INVISIBLE);
                            }
                        }
                    });

                }

                else {
                    Toast.makeText(LoginActivity.this,"Please enter Email & Password",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}