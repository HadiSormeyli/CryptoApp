package com.example.cryptoapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.cryptoapp.R;
import com.example.cryptoapp.databinding.SliderImageItemBinding;

import java.util.ArrayList;

public class SliderImageAdapter extends RecyclerView.Adapter<SliderImageAdapter.ViewHolder> {

    private ArrayList<Integer> items;
    private LayoutInflater layoutInflater;

    public SliderImageAdapter(ArrayList<Integer> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        SliderImageItemBinding itemBinding = DataBindingUtil
                .inflate(layoutInflater, R.layout.slider_image_item, parent, false);
        return new ViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private SliderImageItemBinding itemBinding;

        public ViewHolder(@NonNull SliderImageItemBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }


        public void bind(Integer integer) {
            Glide.with(itemBinding.getRoot())
                    .load(integer)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(itemBinding.sliderImageView);
            itemBinding.executePendingBindings();
        }
    }
}
