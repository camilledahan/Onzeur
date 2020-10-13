package com.tp.onzeur.viewmodel;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.tp.onzeur.Model.ArtistInfos;

public class ArtistInfosViewModel extends BaseObservable {

    private ArtistInfos artistInfos = new ArtistInfos();

    public void setArtistInfos(ArtistInfos artistInfos) {
        artistInfos = artistInfos;
        //indique que  tout a été changé et qu'il faut maj tous les champs bindable
        notifyChange();
    }

    //Bindable = indique que ça peut etre mis a jour
    @Bindable
    public ArtistInfos getArtistInfos(){
        return artistInfos;
    }

    @Bindable
    public String getName() {
        return artistInfos.getName();
    }

    @Bindable
    public String getSummary() {
        return artistInfos.getSummary();
    }

    public void setSummary(String summary){
        artistInfos.setSummary(summary);
        //une seule propriété à changé donc on change que elle
        notifyPropertyChanged(com.tp.onzeur.BR.summary);
    }

    public void setName(String name){
        artistInfos.setName(name);
        //une seule propriété à changé donc on change que elle
        notifyPropertyChanged(com.tp.onzeur.BR.name);
    }


}
