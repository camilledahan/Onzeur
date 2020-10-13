package com.tp.onzeur;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.MediaStore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.tp.onzeur.Fragment.ArtistInfosFragment;
import com.tp.onzeur.Fragment.AudioFileListFragment;
import com.tp.onzeur.Fragment.PlayControlFragment;
import com.tp.onzeur.Listener.MusicListener;
import com.tp.onzeur.Model.AudioFile;
import com.tp.onzeur.Service.PlayerService;
import com.tp.onzeur.WebService.WebService;
import com.tp.onzeur.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private static final String TAG ="Main activity" ;
    private static final int MY_PERMISSIONS_REQUEST_EXTERNAL_STORAGE = 2;
    private static final int MY_PERMISSIONS_REQUEST_INTERNET = 1;

    private ActivityMainBinding binding;
    private List<AudioFile> audioList = new ArrayList<>();
    private boolean mBound = false;
    final AudioFileListFragment audioFileListFragment = new AudioFileListFragment();






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        makeActionWithPermission();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(getApplicationContext(), PlayerService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);


        showStartup();
    }

    public void makeActionWithPermission() {

        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_EXTERNAL_STORAGE);
        } else {
            Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI; // La carte SD
            String[] projection = {MediaStore.Audio.Media.DATA, MediaStore.Audio.Media.TITLE, MediaStore.Audio.Media.ARTIST, MediaStore.Audio.Media.ALBUM, MediaStore.Audio.Media.YEAR, MediaStore.Audio.Media.DURATION}; //chemin du fichier,
            Cursor cursor = getApplicationContext().getContentResolver().query(uri, projection, null, null, null);

            if (cursor != null) {
                while (cursor.moveToNext()) {
                    int audioIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
                    AudioFile audioFile = new AudioFile(cursor.getString(audioIndex + 1), cursor.getString(audioIndex), cursor.getString(audioIndex + 2), cursor.getString(audioIndex + 3), cursor.getInt(audioIndex + 4), cursor.getInt(audioIndex + 5)/1000);
                    audioList.add(audioFile);
                }


            }
        }

// internet connection

        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.ACCESS_NETWORK_STATE},
                    MY_PERMISSIONS_REQUEST_INTERNET);
        } else {

        }
    }


    public void showStartup() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        final PlayControlFragment playcontrolfragment = new PlayControlFragment();
        final ArtistInfosFragment artistInfosFragment = new ArtistInfosFragment();

        MusicListener musicListener = new MusicListener() {
            @Override
            public void onClickTitle(AudioFile audioFile) {
               playcontrolfragment.UpdateMusic(audioFile);
                WebService webService = new WebService(audioFile.getArtist());
                webService.start();
               artistInfosFragment.UpdateArtistInfos(audioFile.getArtist(),webService.getSummary());

            }

            @Override
            public void onClickControlButtons(AudioFile audioFile, int buttonId ) {
                playcontrolfragment.UpdateMusic(audioFileListFragment.controlButtonMusic(audioFile, buttonId ));

            }
        };
        audioFileListFragment.SetMusicListener(musicListener);
        audioFileListFragment.SetAudioList(audioList);
        playcontrolfragment.SetMusicListener(musicListener);
        transaction.replace(R.id.fragment_container, audioFileListFragment);
        transaction.replace(R.id.fragment_container2,playcontrolfragment);
        transaction.replace(R.id.fragment_container3,artistInfosFragment);

        transaction.commit();

    }
private PlayerService mService;
    //listener qui permet d'acceder au service
    private ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            //binder permet d'acceder aux method public du service
            PlayerService.PlayerBinder binder = (PlayerService.PlayerBinder) service;
            mService = binder.getService();
            audioFileListFragment.SetPlayerService(mService);
            mBound = true;

        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };


}
