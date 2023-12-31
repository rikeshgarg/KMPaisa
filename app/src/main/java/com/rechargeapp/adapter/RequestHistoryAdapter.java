package com.rechargeapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.rechargeapp.model.RequestHistoryModel;
import com.codunite.rechargeapp.R;
import com.commonutility.ItemAnimation;

import java.util.ArrayList;
import java.util.List;

public class RequestHistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<RequestHistoryModel> items = new ArrayList<>();
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

    public RequestHistoryAdapter(Context context, List<RequestHistoryModel> items, int animation_type) {
        this.items = items;
        ctx = context;
        this.animation_type = animation_type;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        public TextView amount, datetime, desc,tv_request_id,tv_txn_id,tv_status;
        public View lyt_parent;

        public OriginalViewHolder(View v) {
            super(v);
            amount = (TextView) v.findViewById(R.id.amount);
            datetime = (TextView) v.findViewById(R.id.datetime);
            lyt_parent = (View) v.findViewById(R.id.lyt_parent);
            tv_request_id= (TextView) v.findViewById(R.id.tv_request_id);
            tv_txn_id= (TextView) v.findViewById(R.id.tv_txn_id);
            tv_status= (TextView) v.findViewById(R.id.tv_status);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_requesthistory, parent, false);
        vh = new OriginalViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof OriginalViewHolder) {
            OriginalViewHolder view = (OriginalViewHolder) holder;

            view.amount.setText(items.get(position).getStr_amount());
            view.datetime.setText(items.get(position).getStr_datetime());
            view.tv_request_id.setText("#" + items.get(position).getRequest_id());
            view.tv_txn_id.setText("#" + items.get(position).getTxnid());
            view.tv_status.setText(items.get(position).getStatus());

//            view.desc.setText("#" + items.get(position).getRequest_id() + "\n#" +
//                    items.get(position).getTxnid() + "\n#" +
//                    items.get(position).getStatus());


            if (items.get(position).getStatus().equalsIgnoreCase("Approved")){
                 view.tv_status.setTextColor(ctx.getResources().getColor(R.color.green));
            }else if (items.get(position).getStatus().equalsIgnoreCase("Rejected")){
                view.tv_status.setTextColor(ctx.getResources().getColor(R.color.red));
            }else {
                view.tv_status.setTextColor(ctx.getResources().getColor(R.color.orange_900));
            }



            view.lyt_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(view, items.get(position).getRequest_id(), position);
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