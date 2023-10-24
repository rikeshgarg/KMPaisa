package com.rechargeapp.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.codunite.rechargeapp.R;
import com.rechargeapp.model.DashboardModel;
import com.commonutility.GlobalVariables;
import com.commonutility.ImageLoading;
import com.commonutility.ItemAnimation;
import com.commonutility.customfont.FontUtils;

import java.util.List;

public class BbpsDashboardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<DashboardModel> items;
    private Context ctx;
    private OnItemClickListener mOnItemClickListener;
    private int animation_type = 0;
    private int[] strColors = {R.color.bg_datacard, R.color.bg_fasttag, R.color.bg_metro, R.color.bg_metro,R.color.bg_postpaid,R.color.bg_postpaid, R.color.bg_fasttag,R.color.bg_fasttag,
            R.color.bg_metro,R.color.bg_datacard, R.color.bg_dth, R.color.bg_prepaid, R.color.bg_fasttag, R.color.bg_postpaid,R.color.bg_fasttag, R.color.bg_postpaid,R.color.bg_postpaid, R.color.bg_postpaid,R.color.bg_prepaid, R.color.bg_postpaid,R.color.bg_datacard, R.color.bg_prepaid,R.color.bg_prepaid};

    public interface OnItemClickListener {
        void onItemClick(View view, String obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public BbpsDashboardAdapter(Context context, List<DashboardModel> items, int animation_type) {
        this.items = items;
        ctx = context;
        this.animation_type = animation_type;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        CardView cardview_item;
        public ImageView imgDrawable;
        public RelativeLayout lyt_parent;

        public OriginalViewHolder(View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.name);
            imgDrawable = (ImageView) v.findViewById(R.id.imgdrawable);
            lyt_parent = (RelativeLayout) v.findViewById(R.id.cardview);
            cardview_item = (CardView)v.findViewById(R.id.cardview_item);
            if (!GlobalVariables.CUSTOMFONTNAME.equals("")) {
                Typeface font = Typeface.createFromAsset(ctx.getAssets(), GlobalVariables.CUSTOMFONTNAME);
                FontUtils.setFont(lyt_parent, font);
            }
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dashboard_bbps, parent, false);
        vh = new OriginalViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof OriginalViewHolder) {
            OriginalViewHolder view = (OriginalViewHolder) holder;

            view.name.setText(items.get(position).getName());
            ImageLoading.loadLocalImages(items.get(position).getDrawble(), view.imgDrawable);

//            if (position >= 12 ){
//                view.cardView.setCardBackgroundColor(Color.parseColor(strColors[position-12]));
//            }else {
//                view.cardView.setCardBackgroundColor(Color.parseColor(strColors[position]));
//            }
            //view.cardview_item.setBackgroundColor(strColors[position]);
            //if(items.get(position).getColorName()!=0) {
                view.cardview_item.setCardBackgroundColor(ResourcesCompat.getColor(ctx.getResources(), strColors[position], null));
            //}
            view.lyt_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(view, items.get(position).getName(), position);
                    }
                }
            });
            setAnimation(view.itemView, position);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                on_attach = false;
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    private int lastPosition = -1;
    private boolean on_attach = true;
    private void setAnimation(View view, int position) {
        if (position > lastPosition) {
            ItemAnimation.animate(view, on_attach ? position : -1, animation_type);
            lastPosition = position;
        }
    }
}