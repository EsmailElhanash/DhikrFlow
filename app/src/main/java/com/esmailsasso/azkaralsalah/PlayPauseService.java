package com.esmailsasso.azkaralsalah;

import android.app.IntentService;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.IBinder;
import androidx.annotation.Nullable;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class PlayPauseService extends Service {

    PlayerService playerService;
    boolean isBound;

    public PlayPauseService() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        Intent playerServiceIntent = new Intent(this, PlayerService.class);
        getApplicationContext().bindService(playerServiceIntent, connection, Context.BIND_AUTO_CREATE);


    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            PlayerService.LocalBinder binder = (PlayerService.LocalBinder) iBinder;
            playerService = binder.getService();
            isBound = true;

            playerService.playPause();
            stopSelf();

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            isBound = false;
        }
    };

}
