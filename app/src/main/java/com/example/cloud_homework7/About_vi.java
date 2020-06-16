package com.example.cloud_homework7;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class About_vi extends AppCompatActivity {
    private String videoURL ="https://firebasestorage.googleapis.com/v0/b/homework4-34847.appspot.com/o/!%D9%81%D9%8A%D8%B1%D9%88%D8%B3%20%D9%83%D9%88%D8%B1%D9%88%D9%86%D8%A7%20%D8%A7%D9%84%D9%85%D8%B3%D8%AA%D8%AC%D8%AF%20%D9%88%D8%A7%D9%84%D9%88%D8%A8%D8%A7%D8%A1%20%D8%A7%D9%84%D8%B9%D8%A7%D9%84%D9%85%D9%8A%D8%8C%20%D9%81%D9%8A%D8%AF%D9%8A%D9%88%20%D8%AA%D9%88%D8%B9%D9%88%D9%8A%20%D9%84%D9%84%D9%85%D8%AC%D8%AA%D9%85%D8%B9.mp4?alt=media&token=5aa14f40-71b2-4618-8eab-1d9b32d595ef";
    PlayerView playerView;
    private SimpleExoPlayer player;
    private boolean playWhenReady = true;
    private int currentWindow = 0;
    private long playbackPosition = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_vi);

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("حول الفايروس");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ActivityCompat.requestPermissions(About_vi.this,
                new String[]{Manifest.permission.INTERNET, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                100);

        playerView = findViewById(R.id.video_view);
        TextView textView=findViewById(R.id.textView);

// Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Static text");



        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                textView.setText(value);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Log.w(TAG, "Failed to read value.", databaseError.toException());

            }

        });
    }

    private void initializePlayer() {
        player = ExoPlayerFactory.newSimpleInstance(this);
        playerView.setPlayer(player);

        Uri uri = Uri.parse(videoURL);
        MediaSource mediaSource = buildMediaSource(uri);

        player.setPlayWhenReady(playWhenReady);
        player.seekTo(currentWindow, playbackPosition);
        player.prepare(mediaSource, false, false);
    }



    private void releasePlayer() {
        if (player != null) {
            playWhenReady = player.getPlayWhenReady();
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            player.release();
            player = null;
        }
    }


    private MediaSource buildMediaSource(Uri uri) {
        DataSource.Factory dataSourceFactory =
                new DefaultDataSourceFactory(this, "exoplayer-codelab");
        return new ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(uri);
    }




    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT >= 24) {
            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if ((Util.SDK_INT < 24 || player == null)) {
            initializePlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT < 24) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT >= 24) {
            releasePlayer();
        }
    }
}
