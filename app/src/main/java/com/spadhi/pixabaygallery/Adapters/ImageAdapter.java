package com.spadhi.pixabaygallery.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.spadhi.pixabaygallery.Activities.ImageDetailsActivity;
import com.spadhi.pixabaygallery.Models.Image;
import com.spadhi.pixabaygallery.R;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    List<Image> listOfImages;
    Context context;

    public ImageAdapter(List<Image> listOfImages, Context context) {
        this.listOfImages = listOfImages;
        this.context = context;
    }

    @NonNull
    @Override
    public ImageAdapter.ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item,parent,false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageAdapter.ImageViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.likesTextView.setText(String.valueOf(listOfImages.get(position).getLikes()));
        holder.downloadsTextView.setText(String.valueOf(listOfImages.get(position).getDownloads()));
        Glide.with(context).load(listOfImages.get(position).getImageURL()).into(holder.previewImageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, ImageDetailsActivity.class);
                intent.putExtra("image",listOfImages.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listOfImages.size();
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView previewImageView;
        TextView downloadsTextView,likesTextView;
        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            previewImageView=itemView.findViewById(R.id.previewImageView);
            downloadsTextView=itemView.findViewById(R.id.totalDownloadsTextView);
            likesTextView=itemView.findViewById(R.id.totalLikesTextView);
        }
    }
}
