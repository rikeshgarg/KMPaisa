package com.rechargeapp.activity.aepsnew;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.codunite.rechargeapp.R;
import com.commonutility.ItemAnimation;

import java.util.ArrayList;
import java.util.List;

public class AEPStHistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<FinoAEPSHistoryModel> items = new ArrayList<>();
    private Context ctx;
    private OnItemClickListener mOnItemClickListener;
    private int animation_type = 0;
    private final String[] strColors = {"#388E3C", "#D32F2F"};

    public interface OnItemClickListener {
        void onItemClick(View view, FinoAEPSHistoryModel obj, int position);

        void onInvoiceClick(View view, FinoAEPSHistoryModel obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public AEPStHistoryAdapter(Context context, List<FinoAEPSHistoryModel> items, int animation_type) {
        this.items = items;
        ctx = context;
        this.animation_type = animation_type;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        public TextView memberDeatil, service, txnID, aadhar_no, str_status, amount, mobile, datetime, bankName;
        public CardView cardView;
        public View lyt_parent, layBankName;

        public OriginalViewHolder(View v) {
            super(v);
            memberDeatil = (TextView) v.findViewById(R.id.member_detail);
            service = (TextView) v.findViewById(R.id.txt_service);
            txnID = (TextView) v.findViewById(R.id.txt_txtid);
            aadhar_no = (TextView) v.findViewById(R.id.txt_aadhdarnumber);
            str_status = (TextView) v.findViewById(R.id.txt_status);
            amount = (TextView) v.findViewById(R.id.txt_amount);
            mobile = (TextView) v.findViewById(R.id.txt_mobile);
            datetime = (TextView) v.findViewById(R.id.txt_datetime);
            layBankName = (View) v.findViewById(R.id.lay_bank_name);
            bankName = (TextView) v.findViewById(R.id.bank_name);

            cardView = (CardView) v.findViewById(R.id.cardview);
            lyt_parent = (View) v.findViewById(R.id.lyt_parent);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_aepshistory, parent, false);
        vh = new OriginalViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof OriginalViewHolder) {
            OriginalViewHolder view = (OriginalViewHolder) holder;

            view.memberDeatil.setText(items.get(position).getMemberDeatil());
            view.service.setText(items.get(position).getService());
            view.txnID.setText(items.get(position).getTxnID());
            view.aadhar_no.setText(items.get(position).getAadhar_no());
            view.str_status.setText(items.get(position).getStr_status());
            view.amount.setText(items.get(position).getAmount());
            view.mobile.setText(items.get(position).getMobile());
            view.datetime.setText(items.get(position).getDatetime());

            if (items.get(position).getBank_name().equalsIgnoreCase("")) {
                view.layBankName.setVisibility(View.GONE);
            }else {
                view.bankName.setText(items.get(position).getBank_name());
                view.layBankName.setVisibility(View.VISIBLE);
            }

            if (items.get(position).getStr_status().equalsIgnoreCase("Success")){
                view.amount.setTextColor(ctx.getResources().getColor(R.color.green));
                view.str_status.setTextColor(ctx.getResources().getColor(R.color.green));
            }else if (items.get(position).getStr_status().equalsIgnoreCase("Failed")){
                view.amount.setTextColor(ctx.getResources().getColor(R.color.red));
                view.str_status.setTextColor(ctx.getResources().getColor(R.color.red));
            }else {
                view.amount.setTextColor(ctx.getResources().getColor(R.color.orange_900));
                view.str_status.setTextColor(ctx.getResources().getColor(R.color.orange_900));
            }

            view.lyt_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(view, items.get(position), position);
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