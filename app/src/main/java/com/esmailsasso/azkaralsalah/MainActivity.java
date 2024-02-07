package com.esmailsasso.azkaralsalah;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.exoplayer.SimpleExoPlayer;
import androidx.media3.ui.PlayerView;


public class MainActivity extends AppCompatActivity {
    GridView mainGrid;
    PlayerService playerService;
    boolean isBound = false;

    PlayerView playerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(findViewById(R.id.toolbar));

        setTitle(R.string.app_title);
        mainGrid = findViewById(R.id.grid);
        mainGrid.setAdapter(new ImageAdapter(this));
        Intent playerServiceIntent = new Intent(this, PlayerService.class);
        getApplicationContext().bindService(playerServiceIntent, connection, Context.BIND_AUTO_CREATE);

        playerView = findViewById(R.id.player_control); // Assuming your layout uses PlayerView

        mainGrid.setOnItemClickListener((adapterView, view, i, l) -> {
            playerService.releasePlayer();
            if (playerService.getPlayer() == null) {
                playerService.initializePlayer(getApplicationContext(), playerView);
            }
            playerService.start(getApplicationContext(), i);
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (playerService.isPlaying())
            playerService.startForeground();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (playerService.isPlaying())
            playerService.startForeground();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(isBound&&playerService.isPlaying())
            playerService.stopForeground(true);
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            PlayerService.LocalBinder binder = (PlayerService.LocalBinder) iBinder;
            playerService = binder.getService();
            playerService.initializePlayer(getApplicationContext(), playerView);
            playerService.stopForeground(true);
            isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            isBound = false;
        }
    };

}
