package com.example.music_player;

import android.content.Context;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.MyVieHolder> {

   private Context nContext;
   private ArrayList<MusicFiles> nFiles;

   MusicAdapter(Context nContext, ArrayList<MusicFiles> nFiles){
       this.nFiles = nFiles;
       this.nContext = nContext;
   }

    @NonNull
    @Override
    public MyVieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(nContext).inflate(R.layout.music_items, parent, false);
        return new MyVieHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyVieHolder holder, int position) {
       MusicFiles model = nFiles.get(position);
        holder.file_name.setText(nFiles.get(position).getTitle());
        byte[] image = getAlbumArt(model.getPath());
        Log.d("File_Path", "onBindViewHolder: "+image);

        if (image != null){
            Glide.with(nContext).asBitmap().load(image).into(holder.album_art);
        }
        else{
            Glide.with(nContext).load(R.drawable.music).into(holder.album_art);
        }
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(nContext, PlayerActivity.class);
            intent.putExtra("position", position);
            nContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return nFiles.size();
    }


    public class MyVieHolder extends RecyclerView.ViewHolder{
        TextView file_name;
        ImageView album_art;
        public MyVieHolder(@NonNull View itemView) {
            super(itemView);
            file_name = itemView.findViewById(R.id.music_file_name);
            album_art = itemView.findViewById(R.id.music_img);
        }
    }

    private byte[] getAlbumArt(String uri){
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(uri);
        byte[] art = retriever.getEmbeddedPicture();
        retriever.release();
        return art;
    }
}
