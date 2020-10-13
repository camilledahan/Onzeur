package com.tp.onzeur.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.tp.onzeur.Listener.MusicListener;
import com.tp.onzeur.Model.AudioFile;
import com.tp.onzeur.R;
import com.tp.onzeur.databinding.PlayControlBinding;
import com.tp.onzeur.viewmodel.AudioFileViewModel;

public class PlayControlFragment extends Fragment {
    private  AudioFileViewModel viewModel;
    MusicListener musicListener;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        PlayControlBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.play_control,container,false);
        viewModel = new AudioFileViewModel();
        binding.setAudioFileViewModel(viewModel);

        Button buttonNext = binding.next;
        Button buttonPrevious=binding.previous;
        Button buttonPlay= binding.play;
        Button buttonPause=binding.pause;


        final View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                        musicListener.onClickControlButtons(viewModel.getAudioFile(), view.getId());
            }
        };
        buttonNext.setOnClickListener(listener);
        buttonPrevious.setOnClickListener(listener);
        buttonPlay.setOnClickListener(listener);
        buttonPause.setOnClickListener(listener);

        return binding.getRoot();
    }
    public void SetMusicListener(MusicListener musicListener){
        this.musicListener=musicListener;

    }
    public void UpdateMusic(AudioFile audioFile){
    viewModel.setAudioFile(audioFile);
}


    }
