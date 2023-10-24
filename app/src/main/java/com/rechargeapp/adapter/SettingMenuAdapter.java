package com.rechargeapp.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.commonutility.GlobalVariables;
import com.commonutility.ImageLoading;
import com.commonutility.customfont.FontUtils;
import com.codunite.rechargeapp.R;
import com.rechargeapp.model.SettingMenu;

import java.util.ArrayList;
import java.util.List;

public class SettingMenuAdapter extends RecyclerView.Adapter<SettingMenuAdapter.SingleViewHolder> {
    private Context context;
    private List<SettingMenu> items;
    private int checkedPosition = 0;

    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, String obj);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public SettingMenuAdapter(Context context, List<SettingMenu> items) {
        this.context = context;
        this.items = items;
    }

    public void setItems(ArrayList<SettingMenu> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SingleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_setting, viewGroup, false);
        return new SingleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SingleViewHolder singleViewHolder, int position) {
        singleViewHolder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class SingleViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private ImageView icon;
        private CardView layHeadChange;

        SingleViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.icon);
            name = itemView.findViewById(R.id.name);
            layHeadChange = itemView.findViewById(R.id.lay_head_change);
        }

        void bind(final SettingMenu model) {
            if (!GlobalVariables.CUSTOMFONTNAME.equals("")) {
                Typeface font = Typeface.createFromAsset(context.getAssets(), GlobalVariables.CUSTOMFONTNAME);
                FontUtils.setFont(layHeadChange, font);
            }
            icon.setVisibility(View.GONE);
            ImageLoading.loadLocalImages(model.getDrawable(), icon);
            name.setText(model.getMenuName());
            itemView.setOnClickListener(view -> {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(view, model.getMenuName());
                }
            });
        }
    }
}