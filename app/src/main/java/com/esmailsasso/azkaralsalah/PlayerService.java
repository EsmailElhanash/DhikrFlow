package com.esmailsasso.azkaralsalah;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.OptIn;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.media3.common.MediaItem;
import androidx.media3.common.Player;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.datasource.DataSource;
import androidx.media3.datasource.DefaultDataSource;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;


public class PlayerService extends Service {
    public PlayerService() {
    }

    String current_track_name = "";

    String channelID = "channelID";

    private ExoPlayer player;

    public ExoPlayer getPlayer() {
        return player;
    }

    public void initializePlayer(Context context, PlayerView playerView) {
        if (player == null) {
            player = new ExoPlayer.Builder(this).build();
        }
        playerView.setPlayer(player);
    }

    public void start(Context context, int i) {

        DataSource.Factory dataSourceFactory = new DefaultDataSource.Factory(context);

        Uri uri = null;

        if (i == 0) {
            uri = Uri.parse("asset:///azkar_5atm_salah.mp3");
            current_track_name = "أذكار ختم الصلاة";
        }

        if (uri != null) {
            MediaItem mediaItem = MediaItem.fromUri(uri);
            player.setMediaItem(mediaItem);
            player.prepare();
            player.play(); // Use play() instead of setPlayWhenReady(true)
        }

        player.addListener(new Player.Listener() {


            @Override
            public void onPlaybackStateChanged(int playbackState) {
                Player.Listener.super.onPlaybackStateChanged(playbackState);

                if (playbackState == Player.STATE_ENDED) { // Use Player.STATE_ENDED
                    releasePlayer();
                }
            }

            @Override
            public void onPlayWhenReadyChanged(boolean playWhenReady, int reason) {
                Player.Listener.super.onPlayWhenReadyChanged(playWhenReady, reason);

                if (!playWhenReady) {
                    stopForeground(true);
                }

            }
        });
    }


    public void playPause() {
        player.setPlayWhenReady(!player.getPlayWhenReady());
    }

    public void startForeground() {

        Intent pps = new Intent(getApplicationContext(), PlayPauseService.class);
        PendingIntent pppi = PendingIntent.getService(this, 0, pps, PendingIntent.FLAG_IMMUTABLE);

        Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);

        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0, resultIntent, PendingIntent.FLAG_IMMUTABLE);

        int nID = 1000;

        createNotificationChannel();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelID)
                .setSmallIcon(R.drawable.praying)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(current_track_name)
                .addAction(R.drawable.play_pause, getString(R.string.play_pause), pppi)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

// notificationId is a unique int for each notification that you must define
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        notificationManager.notify(nID, builder.build());

        /*if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.O) {

            NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            Notification.Action action = new Notification.Action.Builder(null,"ايقاف/تشغيل", pppi).build();

            NotificationChannel channel = new NotificationChannel("channel_id" , "channel_name" , NotificationManager.IMPORTANCE_DEFAULT);
            assert mNotificationManager != null;
            mNotificationManager.createNotificationChannel(channel);

            Notification.Builder builder = new Notification.Builder(this, channel.getId())
                    .setContentTitle()
                    .setContentText()
                    .setSmallIcon(R.drawable.praying)
                    .setContentIntent(resultPendingIntent)
                    .addAction(action);

            Notification notification = builder.build();
            startForeground(nID, notification);

        } else {
            NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationChannel channel = new NotificationChannel("channel_id" , "channel_name" , NotificationManager.IMPORTANCE_DEFAULT);
            mNotificationManager.createNotificationChannel(channel);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this,channel.getId())
                    .setContentTitle(getString(R.string.app_name))
                    .setContentText(current_track_name)
                    .setSmallIcon(R.drawable.praying)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(resultPendingIntent)
                    .addAction(0,"ايقاف/تشغيل",pppi);
            Notification notification = builder.build();

            startForeground(nID, notification);
        }*/
    }

    public void releasePlayer(){

        if (player!=null) {
            player.release();
            player = null;
        }

    }

    public boolean isPlaying(){
        return player.getPlayWhenReady();
    }

    private final IBinder mBinder = new LocalBinder();
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    class LocalBinder extends Binder {
        PlayerService getService() {
            return PlayerService.this;
        }
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(channelID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}