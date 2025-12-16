package com.esmailsasso.azkaralsalah;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.media3.ui.PlayerView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity implements  ImageAdapter.OnItemClickListener{
    private static final int MY_PERMISSIONS_REQUEST_POST_NOTIFICATIONS = 123;

    RecyclerView recyclerView;

    ImageAdapter adapter;
    TextView title;
    PlayerService playerService;
    boolean isBound = false;

    PlayerView playerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(findViewById(R.id.toolbar));
        setTitle(R.string.app_title);
        recyclerView = findViewById(R.id.recycler_view);



        adapter = new ImageAdapter();
        title = findViewById(R.id.song_title);
        recyclerView.setAdapter(adapter);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2); // Set the initial column count
        recyclerView.setLayoutManager(gridLayoutManager);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return (position - 2) % 3 == 0 ? 2 : 1; // Set the span size based on the pattern
            }
        });

        playerView = findViewById(R.id.player_view); // Assuming your layout uses PlayerView

        requestNotificationPermission();
        connectService();

        adapter.setOnItemClickListener(this);
    }


    private void connectService() {
        Intent playerServiceIntent = new Intent(this, PlayerService.class);
        getApplicationContext().bindService(playerServiceIntent, connection, Context.BIND_AUTO_CREATE);

    }

    private void requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED
            ) {
                if (!shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                    ActivityCompat.requestPermissions(
                            this,
                            new String[]{Manifest.permission.POST_NOTIFICATIONS},
                            MY_PERMISSIONS_REQUEST_POST_NOTIFICATIONS
                    );
                }
            }

        }

    }

    private final ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            PlayerService.LocalBinder binder = (PlayerService.LocalBinder) iBinder;
            playerService = binder.getService();
            playerService.initializePlayer(playerView);
            isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            isBound = false;
        }
    };


    @Override
    public void onItemClick(int position) {
        if (isBound) {
            if (playerService.getPlayer() == null) {
                playerService.initializePlayer(playerView);
            }
            playerService.start(position);
        }

        title.setText(Data.INSTANCE.getTitleFromNumber(position));
        title.setVisibility(View.VISIBLE);
    }
}
