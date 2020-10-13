package com.tp.onzeur.Listener;

import com.tp.onzeur.Model.AudioFile;

public interface MusicListener {

     void onClickTitle(AudioFile audioFile);

     void onClickControlButtons(AudioFile audioFile, int buttonId );
}
