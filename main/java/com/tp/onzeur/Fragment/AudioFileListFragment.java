package com.tp.onzeur.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.tp.onzeur.Adapter.AudioFileListAdapter;
import com.tp.onzeur.Listener.MusicListener;
import com.tp.onzeur.Model.AudioFile;
import com.tp.onzeur.R;
import com.tp.onzeur.Service.PlayerService;
import com.tp.onzeur.databinding.AudioFileListFragmentBinding;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AudioFileListFragment extends Fragment {

    private static final String TAG ="AudioFileListFragment" ;
    private List<AudioFile> audioList = new ArrayList<>();
    private  MusicListener musicListener;
    private PlayerService playerService;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
      	//la fonction getRoot() de AudioFilebinding nous renvoie la view Java après avoir été créé à partir du fichier xml 
      	//par la fonction inflate
        final AudioFileListFragmentBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.audio_file_list_fragment,container,false);
        binding.audioFileList.setLayoutManager(new LinearLayoutManager(
                binding.getRoot().getContext()));
        binding.audioFileList.setAdapter(new AudioFileListAdapter(audioList,musicListener));

        return binding.getRoot();
    }
    public void SetMusicListener(MusicListener musicListener){
        this.musicListener=musicListener;


    }
    public void SetPlayerService(PlayerService playerService){
        this.playerService= playerService;
    }

    @Override
    public void onStart() {
        super.onStart();


    }

    public void SetAudioList(List<AudioFile> audioList){
        this.audioList=audioList;
    }

    public AudioFile controlButtonMusic(AudioFile audioFile, int buttonId ){
            int indexTitle = audioList.indexOf(audioFile);
            switch (buttonId){
                case R.id.next:
                    AudioFile nextAudiofile;
                    if(indexTitle== audioList.size()-1){
                        nextAudiofile= audioList.get(0);
                    }else {
                        nextAudiofile= audioList.get(indexTitle+1);
                    }
                    try {
                        playerService.play(nextAudiofile.getFilePath());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    return nextAudiofile;
                case R.id.previous:
                    AudioFile previousAudioFile;

                    if(indexTitle==0) {
                        previousAudioFile = audioList.get(audioList.size() - 1);
                    }else {
                        previousAudioFile = audioList.get(indexTitle-1);
                    }

                    try {
                      playerService.play(previousAudioFile.getFilePath());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                        return previousAudioFile;

                case R.id.play:

                    if (audioFile.getFilePath()==null){
                          audioFile = audioList.get(0);
                    }
                    try {
                        playerService.play(audioFile.getFilePath());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return audioFile;
                case R.id.pause:
                    playerService.pause();
                default:
                      return audioList.get(indexTitle);
            }
    }

}