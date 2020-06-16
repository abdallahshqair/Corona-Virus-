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

public class Login extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText email,password;
    Button login, register;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
//        updateUI(currentUser);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Login");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mAuth = FirebaseAuth.getInstance();

        email = findViewById(R.id.login_email);
        password = findViewById(R.id.login_password);
        login = findViewById(R.id.login);
        register = findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),SignUp.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text_email    = email.   getText().toString();
                String text_password = password.getText().toString();
                if (TextUtils.isEmpty(text_email)||TextUtils.isEmpty(text_password)){
                    Toast.makeText(Login.this, "all filed are required", Toast.LENGTH_SHORT).show();
                }else{

                    mAuth.signInWithEmailAndPassword(text_email, text_password)
                            .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Intent intent = new Intent(Login.this, Home.class);
                                        startActivity(intent);

                                    }else {
                                        Toast.makeText(Login.this, "login falild", Toast.LENGTH_SHORT).show();
                                    }

                                    // ...

                                }});


                }


            }
        });



    }
}
