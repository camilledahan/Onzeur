package com.tp.onzeur.Service;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

import java.io.IOException;

public class PlayerService extends Service implements MediaPlayer.OnErrorListener, MediaPlayer.OnPreparedListener {

    private final Binder binder = new PlayerBinder();
    private MediaPlayer mediaPlayer = null;
    private static final String ACTION_PLAY = "com.tp.onzeur.PLAY";


    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = new MediaPlayer();

    }


    public void play(String path) throws IOException {
        mediaPlayer.reset();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setDataSource(path);
        mediaPlayer.prepareAsync();
        mediaPlayer.start();
    }


    public void pause() {
        mediaPlayer.pause();

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }
    public void onPrepared(MediaPlayer player) {
        player.start();
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }

    public class PlayerBinder extends Binder {
        public PlayerService getService() {

            return PlayerService.this;
        }
    }
}
