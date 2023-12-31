package com.rechargeapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.rechargeapp.model.RechargeCommisionModel;
import com.codunite.rechargeapp.R;
import com.commonutility.ItemAnimation;

import java.util.ArrayList;
import java.util.List;

public class RechargeCommisionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<RechargeCommisionModel> items = new ArrayList<>();
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

    public RechargeCommisionAdapter(Context context, List<RechargeCommisionModel> items, int animation_type) {
        this.items = items;
        ctx = context;
        this.animation_type = animation_type;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        public TextView txtOperator, txtcode, txtType, txtcommision,txtflat,txtsurcharge;
        public CardView cardView;
        public View lyt_parent;

        public OriginalViewHolder(View v) {
            super(v);
            txtOperator = (TextView) v.findViewById(R.id.operator_re_commision);
            txtcode = (TextView) v.findViewById(R.id.code_re_commision);
            txtType = (TextView) v.findViewById(R.id.type_re_commision);
            txtcommision = (TextView) v.findViewById(R.id.commision_re_commision);
            txtflat = (TextView) v.findViewById(R.id.flat_re_commision);
            txtsurcharge= (TextView) v.findViewById(R.id.surcharge_re_commision);
            cardView = (CardView) v.findViewById(R.id.cardview);
            lyt_parent = (View) v.findViewById(R.id.lyt_parent);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rechargecommision, parent, false);
        vh = new OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof OriginalViewHolder) {
            OriginalViewHolder view = (OriginalViewHolder) holder;

            view.txtOperator.setText(items.get(position).getStrOperator());
            view.txtcode.setText(items.get(position).getStrcode());
            view.txtType.setText(items.get(position).getStrType());
            view.txtcommision.setText(items.get(position).getStrcommision());
//            view.txtflat.setText(items.get(position).getStrflat());
//            view.txtsurcharge.setText(items.get(position).getStrsurcharge());

            if(items.get(position).getStrflat().equals("1")){
                view.txtflat.setText("Yes");
                view.txtflat.setTextColor(ctx.getResources().getColor(R.color.green));
            } else {
                view.txtflat.setText("No");
                view.txtflat.setTextColor(ctx.getResources().getColor(R.color.red));
            }

            if(items.get(position).getStrsurcharge().equals("1")){
                view.txtsurcharge.setText("Yes");
                view.txtsurcharge.setTextColor(ctx.getResources().getColor(R.color.green));
            } else {
                view.txtsurcharge.setText("No");
                view.txtsurcharge.setTextColor(ctx.getResources().getColor(R.color.red));
            }

            view.lyt_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(view, items.get(position).getStrType(), position);
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