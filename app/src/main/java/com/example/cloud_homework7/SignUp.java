package com.example.cloud_homework7;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignUp extends AppCompatActivity {

    private FirebaseAuth mAuth;
    DatabaseReference myRef;

    EditText username;
    EditText email;
    EditText password;
    Button signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Sign Up");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mAuth = FirebaseAuth.getInstance();

        username=findViewById(R.id.signup_uesrname);
        email=findViewById(R.id.signup_email);
        password=findViewById(R.id.signup_password);
        signup=findViewById(R.id.signup);
        mAuth = FirebaseAuth.getInstance();
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text_username=username.getText().toString();
                String text_email=email.getText().toString();
                String text_password=password.getText().toString();
                if(TextUtils.isEmpty(text_username)||TextUtils.isEmpty(text_email)||TextUtils.isEmpty(text_password)){
                    Toast.makeText(getApplicationContext(),"all filed",Toast.LENGTH_LONG).show();
                }else if (text_password.length()<6){
                    Toast.makeText(SignUp.this, "password muset be < 6", Toast.LENGTH_SHORT).show();

                }else{
                    signup(text_username,text_email,text_password);
                }




            }
        });
    }
    private  void signup (final String username , String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseUser user = mAuth.getCurrentUser();
                            String userid = user.getUid();
                            myRef = FirebaseDatabase.getInstance().getReference("Users").child(userid);

                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("id", userid);
                            hashMap.put("username", username);
                            hashMap.put("imageURL", "default");
                            myRef.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Intent intent = new Intent(getApplicationContext(), Home.class);
                                        startActivity(intent);

                                    }
                                }
                            });


//
                        } else {
                            ;
                        }

                        // ...
                    }
                });
    }


}
