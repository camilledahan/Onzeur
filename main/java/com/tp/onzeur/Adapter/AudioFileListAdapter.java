package com.tp.onzeur.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.tp.onzeur.Listener.MusicListener;
import com.tp.onzeur.Model.AudioFile;
import com.tp.onzeur.R;
import com.tp.onzeur.databinding.AudioFileItemBinding;
import com.tp.onzeur.viewmodel.AudioFileViewModel;

import java.util.List;

//RecyclerView --> dynamic contents
public class AudioFileListAdapter extends RecyclerView.Adapter<AudioFileListAdapter.ViewHolder> {
    List<AudioFile> audioFileList;
    MusicListener musicListener;

    public AudioFileListAdapter(List<AudioFile> fileList, MusicListener musicListener) {
        assert fileList != null;
        audioFileList = fileList;
        this.musicListener = musicListener;
    }

  	//appelé pour chaque vue à créer
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AudioFileItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.audio_file_item, parent, false);
        return new ViewHolder(binding);
    }

  	//appelé pour remplir la vue associée au ViewHolder
    @Override

    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        AudioFile file = audioFileList.get(position);

      	//permets de donner au databinding le ViewModel afin qu'il puisse l'utiliser avec la vue
        holder.viewModel.setAudioFile(file);
        holder.bind(file, musicListener);


    }

  	//permet de donner l'info à la RecyclerView sur le nb max d'éléments pour savoir quand on est à la fin de la liste ou non
    @Override
    public int getItemCount() {
        return audioFileList.size();

    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        private AudioFileItemBinding binding;
        private AudioFileViewModel viewModel = new AudioFileViewModel();

        ViewHolder(AudioFileItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.binding.setAudioFileViewModel(viewModel);
        }

        public void bind(final AudioFile item, final MusicListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClickTitle(item);
                }
            });
        }


    }
}