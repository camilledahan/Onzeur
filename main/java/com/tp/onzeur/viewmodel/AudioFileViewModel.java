package com.tp.onzeur.viewmodel;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.tp.onzeur.Model.AudioFile;

//ViewModel communique à la fois avec la vue (audio_file_item.xml) et avec le model (AudioFile)
//étend BaseObservable pour pouvoir rafraichier la vue quand les données sont modifiées
public class AudioFileViewModel extends BaseObservable {


    private AudioFile audioFile = new AudioFile();

    public void setAudioFile(AudioFile file) {
        audioFile = file;
      	//indique que  tout a été changé et qu'il faut maj tous les champs bindable
        notifyChange();
    }
	
  	//Bindable = indique que ça peut etre mis a jour
    @Bindable
    public AudioFile getAudioFile(){
        return audioFile;
    }

    @Bindable
    public String getArtist() {
        return audioFile.getArtist();
    }

    @Bindable
    public String getTitle() {
        return audioFile.getTitle();
    }

    public void setTitle(String title){
        audioFile.setTitle(title);
      	//une seule propriété à changé donc on change que elle
        notifyPropertyChanged(com.tp.onzeur.BR.title);
    }

    @Bindable
    public String getAlbum() {

        return audioFile.getAlbum();
    }

    @Bindable
    public String getDuration() {
        return audioFile.getDurationText();
    }
}
