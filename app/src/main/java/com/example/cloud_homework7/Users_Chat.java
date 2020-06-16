package com.example.cloud_homework7;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.cloud_homework7.adapter.UserAdapter;
import com.example.cloud_homework7.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Users_Chat extends AppCompatActivity {
    CircleImageView profile_image;
    TextView username;
    private RecyclerView recyclerView;

    private UserAdapter userAdapter;
    private List<User> mUsers;

    EditText search_users;
    Button chat ,users;

    FirebaseUser firebaseUser;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_chat);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        chat=findViewById(R.id.chat);
        users=findViewById(R.id.users);



        profile_image = findViewById(R.id.circleImageView);
        username = findViewById(R.id.username);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                username.setText(user.getUsername());
                if (user.getImageURL().equals("default")){
                    profile_image.setImageResource(R.mipmap.ic_launcher);
                } else {

                    //change this
                    Glide.with(getApplicationContext()).load(user.getImageURL()).into(profile_image);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        mUsers = new ArrayList<>();


        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(Users_Chat.this));
        recyclerView.setAdapter(userAdapter);
        users.setEnabled(false);

        users.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark));
        readUsers();

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Users_Chat.this,Chats.class));
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case  R.id.logout:
                FirebaseAuth.getInstance().signOut();
                // change this code beacuse your app will crash
                startActivity(new Intent(Users_Chat.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                return true;
        }

        return false;


    }

    private void readUsers() {

        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if (search_users.getText().toString().equals("")) {
                mUsers.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);

                    if (!user.getId().equals(firebaseUser.getUid())) {
                        mUsers.add(user);
                    }

                }
                userAdapter = new UserAdapter(Users_Chat.this, mUsers, false);

                recyclerView.setAdapter(userAdapter);

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
