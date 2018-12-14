package com.example.nurulislam.gallery.ADAPTER;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nurulislam.gallery.Activity.Slide;
import com.example.nurulislam.gallery.POJO.Hit;
import com.example.nurulislam.gallery.R;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * This is Created by Nurul Islam Tipu on 12/13/2018
 */
public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {

    private Context context;
    private List<Hit> hits;

    public GalleryAdapter(Context context, List<Hit> hits) {
        this.context = context;
        this.hits = hits;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.gallery_row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {

        try {
            Hit hit = hits.get(i);

            Picasso.get().load(hit.getPreviewURL()).placeholder(R.drawable.picture)
                    .error(R.drawable.picture)
                    .into(viewHolder.galleryImage);
            viewHolder.imageText.setText(hit.getUser());

        }catch (Exception e){
            e.printStackTrace();
        }
        viewHolder.galleryImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Slide.class);
                intent.putExtra("position", i);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return hits.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView galleryImage;
        TextView imageText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            galleryImage = itemView.findViewById(R.id.galleryImage);
            imageText = itemView.findViewById(R.id.imageText);
        }
    }

}
