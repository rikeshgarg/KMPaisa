package com.rechargeapp.activity.virtualaccount;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.commonutility.ItemAnimation;
import com.codunite.rechargeapp.R;

import java.util.ArrayList;
import java.util.List;

public class VirtualAccountReportAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<VirtualTransactionReportModel> items = new ArrayList<>();
    private Context ctx;
    private OnItemClickListener mOnItemClickListener;

    private int animation_type = 0;
    private String[] strColors = {"#388E3C", "#D32F2F"};

    public interface OnItemClickListener {
        void onItemClick(View view, VirtualTransactionReportModel obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public VirtualAccountReportAdapter(Context context, List<VirtualTransactionReportModel> items, int animation_type) {
        this.items = items;
        this.ctx = context;
        this.animation_type = animation_type;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        public TextView tvVirtualAccount, tvMode, tvUtr, tvBankTxnID, tvAmount, tvPayerName, tvPayerAccount, tvRemark, tvDateTime;
        public CardView cardView;
        public View lyt_parent;

        public OriginalViewHolder(View v) {
            super(v);
            tvVirtualAccount = (TextView) v.findViewById(R.id.tv_virtualac);
            tvMode = (TextView) v.findViewById(R.id.tv_mode);
            tvUtr = (TextView) v.findViewById(R.id.tv_utr);
            tvBankTxnID = (TextView) v.findViewById(R.id.tv_banktxn_id);
            tvAmount = (TextView) v.findViewById(R.id.tv_amount);
            tvPayerName = (TextView) v.findViewById(R.id.tv_payer_name);
            tvPayerAccount = (TextView) v.findViewById(R.id.tv_payer_accountno);
            tvRemark = (TextView) v.findViewById(R.id.tv_remark);
            tvDateTime = (TextView) v.findViewById(R.id.tv_datetime);

            cardView = (CardView) v.findViewById(R.id.cardview);
            lyt_parent = (View) v.findViewById(R.id.lyt_parent);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_virtual_account_report, parent, false);
        vh = new OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof OriginalViewHolder) {
            OriginalViewHolder view = (OriginalViewHolder) holder;

            VirtualTransactionReportModel data = items.get(position);

            view.tvVirtualAccount.setText(data.getVirtualAccount());
            view.tvMode.setText(data.getMode());
            view.tvUtr.setText(data.getUtr());
            view.tvBankTxnID.setText(data.getBankTxnID());
            view.tvAmount.setText(data.getAmount());
            view.tvPayerName.setText(data.getPayerName());
            view.tvPayerAccount.setText(data.getPayerAccount());
            view.tvRemark.setText(data.getRemark());
            view.tvDateTime.setText(data.getDateTime());


            view.lyt_parent.setOnClickListener(view1 -> {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(view1, items.get(position), position);
                }
            });

            //setAnimation(view.itemView, position);
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