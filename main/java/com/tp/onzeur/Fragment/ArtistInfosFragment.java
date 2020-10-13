package com.tp.onzeur.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.tp.onzeur.Listener.MusicListener;
import com.tp.onzeur.R;
import com.tp.onzeur.databinding.ArtistInfosBinding;
import com.tp.onzeur.viewmodel.ArtistInfosViewModel;

public class ArtistInfosFragment extends Fragment {

    private ArtistInfosViewModel viewModel;
    MusicListener musicListener;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ArtistInfosBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.artist_infos, container, false);
        viewModel = new ArtistInfosViewModel();
        binding.setArtistInfosViewModel(viewModel);
        return binding.getRoot();
    }


public void UpdateArtistInfos(String artist,String summary){
    viewModel.setSummary(summary);
    viewModel.setName(artist);
}
}
