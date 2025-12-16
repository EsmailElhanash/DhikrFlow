package com.esmailsasso.azkaralsalah;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.media3.common.MediaItem;
import androidx.media3.common.Player;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;


public class PlayerService extends Service {

    private String PLAYBACK_TOGGLE= "playback toggle";
    private String PLAYBACK_POSITION= "playback position";

    String current_track_name = "";
    private int current_track_number = -1;



    String channelID = "channelID";

    private ExoPlayer player;

    @Override
    public void onCreate() {
        super.onCreate();
        startForeground();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int i = intent.getIntExtra( PLAYBACK_TOGGLE , 0);
        current_track_number = intent.getIntExtra(PLAYBACK_POSITION, current_track_number);
        if (player != null){
            if (i == 1) {
                if (player.isPlaying()) {
                    player.pause();
                } else {
                    player.play();
                }
            }
        }
        return START_STICKY;
    }

    public ExoPlayer getPlayer() {
        return player;
    }

    public void initializePlayer(PlayerView playerView) {
        if (player == null) {
            player = new ExoPlayer.Builder(this).build();
        }
        playerView.setPlayer(player);
    }

    public void start(int i) {

        Uri uri = null;

        switch (i){
            case 0: uri = Uri.parse("asset:///azkar_5atm_salah.mp3");current_track_name = "أذكار ختم الصلاة";
                break;
            case 1: uri = Uri.parse("asset:///azkar_sabah.mp3");current_track_name = "أذكار الصباح";
                break;
            case 2: uri = Uri.parse("asset:///azkar_masa2.mp3");current_track_name = "أذكار المساء";
                break;
            case 3: uri = Uri.parse("asset:///duaa_alreeh.mp3");current_track_name = "دعاء الريح";
                break;
            case 4: uri = Uri.parse("asset:///duaa_istikhara.mp3");current_track_name = "دعاء الإستخارة";
                break;
            case 5: uri = Uri.parse("asset:///duaa_sa3b.mp3");current_track_name = "دعاء استصعاب أمر";
                break;
            case 6: uri = Uri.parse("asset:///duaa_safar.mp3");current_track_name = "دعاء السفر";
                break;
            case 7: uri = Uri.parse("asset:///rokya_shar3eya.mp3");current_track_name = "الرقية الشرعية";
                break;
            case 8: uri = Uri.parse("asset:///duaa_matar.mp3");current_track_name = "دعاء المطر";
                break;
            case 9: uri = Uri.parse("asset:///azkar_nawm.mp3");current_track_name = "أذكار النوم";
                break;
        }


        if (uri != null) {
            MediaItem mediaItem = MediaItem.fromUri(uri);
            player.setMediaItem(mediaItem);
            player.prepare();
            player.play();
            current_track_number = i;
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

    private PendingIntent createPlayPausePendingIntent() {
        // create pending Intent that launches PlayerService and toggles play/pause
        Intent intent = new Intent(this, PlayerService.class);
        intent.putExtra(PLAYBACK_TOGGLE, 1);
        return PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
    }

    public void startForeground() {
        int nID = 1000;
        Notification notification = createNotification();
        if (notification == null) return;
        startForeground(nID, notification);
    }

    private Notification createNotification() {
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.O) {
            Notification.Builder builder = new Notification.Builder(this)
                    .setContentTitle(getString(R.string.app_name))
                    .setContentText(current_track_name)
                    .setSmallIcon(R.drawable.praying)
                    .setPriority(Notification.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent)
                    .addAction(0, "ايقاف/تشغيل", createPlayPausePendingIntent());

            return builder.build();

        } else {
            createNotificationChannel();
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelID)
                    .setContentTitle(getString(R.string.app_name))
                    .setContentText(current_track_name)
                    .setSmallIcon(R.drawable.praying)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent)
                    .addAction(0, "ايقاف/تشغيل", createPlayPausePendingIntent());
            return builder.build();
        }
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