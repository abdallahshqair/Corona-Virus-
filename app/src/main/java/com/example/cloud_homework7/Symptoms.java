package com.example.cloud_homework7;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class Symptoms extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptoms);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("أعراض الإصابات بكورونا");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView textView=findViewById(R.id.click);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Diabetes.class));
            }
        });


        ImageView imageView=findViewById(R.id.imageView);
        String URl="https://firebasestorage.googleapis.com/v0/b/homework4-34847.appspot.com/o/SYMPTOMS.png?alt=media&token=3bc6e614-7a32-4fb2-a280-198b6cb474f2";

        Glide.with(getApplicationContext()).load(URl).into(imageView);

    }
}
