package com.rechargeapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.commonutility.GlobalVariables;
import com.commonutility.ItemAnimation;
import com.codunite.rechargeapp.R;
import com.rechargeapp.model.MemberListModel;

import java.util.List;

public class MemberListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<MemberListModel> items;
    private Context ctx;
    private OnItemClickListener mOnItemClickListener;
    private int animation_type = 0;
    private String[] strColors = {"#e91e63", "#9c27b0","#673ab7", "#E53935", "#5677fc", "#689F38", "#03a9f4", "#00bcd4",
            "#009688", "#259b24", "#ff5722", "#795548", "#607d8b", "#ff9800"};

    public interface OnItemClickListener {
        void onItemClick(View view, String obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public MemberListAdapter(Context context, List<MemberListModel> items, int animation_type) {
        this.items = items;
        ctx = context;
        this.animation_type = animation_type;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        public TextView vnMemberId, vmName, vmWallbal, vmStatus, usercode, walletBal;
        public CardView cardView;
        public View lyt_parent;
        LinearLayout ll_bg;

        public OriginalViewHolder(View v) {
            super(v);
            vnMemberId = (TextView) v.findViewById(R.id.vm_memberid);
            vmName = (TextView) v.findViewById(R.id.vm_name);
            vmWallbal = (TextView) v.findViewById(R.id.vm_wallbal);
            vmStatus = (TextView) v.findViewById(R.id.vm_status);
            walletBal = (TextView) v.findViewById(R.id.wallet_bal);

            ll_bg = (LinearLayout) v.findViewById(R.id.ll_bg);
            lyt_parent = (View) v.findViewById(R.id.lyt_parent);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_member_list, parent, false);
        vh = new OriginalViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof OriginalViewHolder) {
            OriginalViewHolder view = (OriginalViewHolder) holder;
            if(position %2 == 1)
            {
                view.ll_bg.setBackgroundResource(R.drawable.img_bg_mamber_card);
            }
            else
            {
                view.ll_bg.setBackgroundResource(R.drawable.bg_dashboard_blue);
            }
            view.vnMemberId.setText(items.get(position).getUserCode());
            view.vmName.setText(items.get(position).getName());
            view.vmWallbal.setText(items.get(position).getWalBal());
            view.vmStatus.setText(items.get(position).getStatus());
            view.walletBal.setText(GlobalVariables.CURRENCYSYMBOL+items.get(position).getWall_bal());

            view.lyt_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(view, items.get(position).getMemberId(), position);
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