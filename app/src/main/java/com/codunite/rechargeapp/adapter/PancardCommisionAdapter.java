package com.codunite.rechargeapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.codunite.commonutility.ItemAnimation;
import com.codunite.rechargeapp.R;
import com.codunite.rechargeapp.model.AadharKYCCommisionModel;
import com.codunite.rechargeapp.model.BBPSCommisionModel;

import java.util.ArrayList;
import java.util.List;

public class PancardCommisionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<BBPSCommisionModel> items = new ArrayList<>();
    private Context ctx;
    private int animation_type = 0;
    private String[] strColors = {"#388E3C", "#D32F2F"};


    public PancardCommisionAdapter(Context context, List<BBPSCommisionModel> items, int animation_type) {
        this.items = items;
        ctx = context;
        this.animation_type = animation_type;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        public TextView txtservice, txtcommision,txtflat,txtsurcharge,tv_heading_commission;
        public CardView cardView;
        public View lyt_parent;
        LinearLayout ll_surcharge,ll_flat;

        public OriginalViewHolder(View v) {
            super(v);
            txtservice = (TextView) v.findViewById(R.id.tv_service);
            txtcommision = (TextView) v.findViewById(R.id.tv_commission);
            txtflat = (TextView) v.findViewById(R.id.flat_re_commision);
            txtsurcharge= (TextView) v.findViewById(R.id.surcharge_re_commision);
            tv_heading_commission= (TextView) v.findViewById(R.id.tv_heading_commission);
            cardView = (CardView) v.findViewById(R.id.cardview);
            lyt_parent = (View) v.findViewById(R.id.lyt_parent);

            ll_flat=(LinearLayout) v.findViewById(R.id.ll_flat);
            ll_surcharge=(LinearLayout) v.findViewById(R.id.ll_surcharge);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bbpsfixcommision, parent, false);
        vh = new OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof OriginalViewHolder) {
            OriginalViewHolder view = (OriginalViewHolder) holder;

            view.txtservice.setText(items.get(position).getStrType());
            view.txtcommision.setText(items.get(position).getStrcommision());
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

            //view.txtsurcharge.setText(items.get(position).getStrsurcharge());
            view.tv_heading_commission.setText("Charge");

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