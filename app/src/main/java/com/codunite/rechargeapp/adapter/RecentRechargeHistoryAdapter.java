package com.codunite.rechargeapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.codunite.commonutility.GlobalVariables;
import com.codunite.commonutility.ImageLoading;
import com.codunite.commonutility.ItemAnimation;
import com.codunite.rechargeapp.R;
import com.codunite.rechargeapp.model.RecentRechargeHistoryModel;
import com.codunite.rechargeapp.model.RechargeHistoryModel;

import java.util.ArrayList;
import java.util.List;

public class RecentRechargeHistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<RecentRechargeHistoryModel> items = new ArrayList<>();
    private Context ctx;
    private OnItemClickListener mOnItemClickListener;

    private OnComplaintItemClickListener mOnComplaintItemClickListener;
    private int animation_type = 0;
    private String[] strColors = {"#388E3C", "#D32F2F"};
    private boolean isComplaint;

    public interface OnItemClickListener {
        void onItemClick(View view, String obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public interface OnComplaintItemClickListener {
        void onComplaintItemClick(View view, String obj, int position);
    }

    public void setOnComplaintItemClickListener(final OnComplaintItemClickListener mOnComplaintItemClickListener) {
        this.mOnComplaintItemClickListener = mOnComplaintItemClickListener;
    }

    public RecentRechargeHistoryAdapter(Context context, List<RecentRechargeHistoryModel> items, int animation_type, boolean isComplaint) {
        this.isComplaint = isComplaint;
        this.items = items;
        ctx = context;
        this.animation_type = animation_type;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_recharge_type, tv_mobile, tv_date, tv_amount,tv_status;
        ImageView iv_operator;

        public OriginalViewHolder(View v) {
            super(v);
            tv_recharge_type = (TextView) v.findViewById(R.id.tv_recharge_type);
            tv_mobile = (TextView) v.findViewById(R.id.tv_mobile);
            tv_date = (TextView) v.findViewById(R.id.tv_date);
            tv_amount = (TextView) v.findViewById(R.id.tv_amount);
            tv_status = (TextView) v.findViewById(R.id.tv_status);
            iv_operator = (ImageView) v.findViewById(R.id.iv_operator);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recentrechargehistory, parent, false);
        vh = new OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof OriginalViewHolder) {
            OriginalViewHolder view = (OriginalViewHolder) holder;

            view.tv_recharge_type.setText(items.get(position).getType() + " Recharge");
            view.tv_mobile.setText(items.get(position).getMobile());
            view.tv_date.setText(items.get(position).getStr_datetime());
            view.tv_amount.setText(items.get(position).getStr_amount());
            //view.tv_status.setText(items.get(position).getStr_status());

            if(items.get(position).getStrIcon()!=""){
                ImageLoading.loadImages(items.get(position).getStrIcon(), view.iv_operator, 0);
            }

            if (items.get(position).getStr_status().equalsIgnoreCase("Pending")){
                view.tv_status.setText("Pending");
                view.tv_status.setTextColor(ctx.getResources().getColor(R.color.orange_900));
            }else if (items.get(position).getStr_status().equalsIgnoreCase("Success")){
                view.tv_status.setText("Success");
                view.tv_status.setTextColor(ctx.getResources().getColor(R.color.green));
            }else {
                view.tv_status.setText("Fail");
                view.tv_status.setTextColor(ctx.getResources().getColor(R.color.red));
            }
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