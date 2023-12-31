package com.rechargeapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.commonutility.ImageLoading;
import com.codunite.rechargeapp.R;
import com.rechargeapp.model.SliderModel;

import java.util.List;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.SliderViewHolder> {
    private List<SliderModel> sliderItems;
    private ViewPager2 viewPager2;

    public SliderAdapter(List<SliderModel> sliderItems, ViewPager2 viewPager2) {
        this.sliderItems = sliderItems;
        this.viewPager2 = viewPager2;
    }
    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SliderViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.slide_item_container, parent, false
                ) );
    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {
        holder.setImage(sliderItems.get(position));
        if (position == sliderItems.size()- 2){
            viewPager2.post(runnable);
        }
    }

    @Override
    public int getItemCount() {
        return sliderItems.size();
    }
    class SliderViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;

        SliderViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageSlide);
        }

        void setImage(SliderModel sliderItems) {
            ImageLoading.loadImages(sliderItems.getBanner_img(), imageView, 0);
        //use glide or picasso in case you get image from internet
//            Glide.with(imageView.getContext())
//                    .load(Uri.parse(sliderItems.getBanner_img()))
//                    .placeholder(R.drawable.loader)
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .dontAnimate()
//                    .into(imageView);
            //imageView.setImageURI(Uri.parse(sliderItems.getBanner_img()));
        }
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            sliderItems.addAll(sliderItems);
            notifyDataSetChanged();
        }
    };
}
