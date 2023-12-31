package com.rechargeapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.commonutility.ItemAnimation;
import com.codunite.rechargeapp.R;
import com.rechargeapp.model.RechargeCommisionReportHistoryModel;

import java.util.ArrayList;
import java.util.List;

public class RechargeCommisionReportHistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<RechargeCommisionReportHistoryModel> items = new ArrayList<>();
    private Context ctx;
    private OnItemClickListener mOnItemClickListener;
    private int animation_type = 0;
    private String[] strColors = {"#388E3C", "#D32F2F"};

    public interface OnItemClickListener {
        void onItemClick(View view, String obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public RechargeCommisionReportHistoryAdapter(Context context, List<RechargeCommisionReportHistoryModel> items, int animation_type) {
        this.items = items;
        ctx = context;
        this.animation_type = animation_type;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_member_id, tv_name,tv_amount,tv_trnsc_id,tv_datetime;
        public CardView cardView;
        public View lyt_parent;

        public OriginalViewHolder(View v) {
            super(v);
            tv_member_id = (TextView) v.findViewById(R.id.tv_member_id);
            tv_name = (TextView) v.findViewById(R.id.tv_name);
            tv_amount = (TextView) v.findViewById(R.id.tv_amount);
            tv_trnsc_id = (TextView) v.findViewById(R.id.tv_trnsc_id);
            tv_datetime = (TextView) v.findViewById(R.id.tv_datetime);
            lyt_parent = (View) v.findViewById(R.id.lyt_parent);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recharge_commission_history_report, parent, false);
        vh = new OriginalViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof OriginalViewHolder) {
            OriginalViewHolder view = (OriginalViewHolder) holder;

            view.tv_member_id.setText(items.get(position).getStr_user_code());
            view.tv_name.setText(items.get(position).getStr_member_name());
            view.tv_amount.setText(items.get(position).getStr_commision_amount());
            view.tv_trnsc_id.setText(items.get(position).getStr_txn_id());
            view.tv_datetime.setText(items.get(position).getStr_datetime());

            view.lyt_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(view, items.get(position).getStr_datetime(), position);
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