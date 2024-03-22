package com.rechargeapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;


import com.commonutility.ItemAnimation;
import com.codunite.rechargeapp.R;
import com.rechargeapp.model.RechargeHistoryModel;

import java.util.ArrayList;
import java.util.List;

public class RechargeHistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<RechargeHistoryModel> items = new ArrayList<>();
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

    public RechargeHistoryAdapter(Context context, List<RechargeHistoryModel> items, int animation_type, boolean isComplaint) {
        this.isComplaint = isComplaint;
        this.items = items;
        ctx = context;
        this.animation_type = animation_type;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        public TextView amountRecharge, recharegDateTime, status, txtID,txtaccno;
        public TextView txtOperator, txtMobile, txtType, memberDeatil, txtAfterBal, txtBeforeBal,tv_recharge_type;
        public CardView cardView;
        public View lyt_parent;
        public View layaccountno,laymobile;
        public AppCompatButton btnComplain;
        ImageView iv_operator;

        public OriginalViewHolder(View v) {
            super(v);
            amountRecharge = (TextView) v.findViewById(R.id.vm_memberid);
            recharegDateTime = (TextView) v.findViewById(R.id.vm_name);
            //txtaccno = (TextView) v.findViewById(R.id.vm_accountno);
            status = (TextView) v.findViewById(R.id.vm_wallbal);
            //txtOperator = (TextView) v.findViewById(R.id.operator);
            txtMobile = (TextView) v.findViewById(R.id.mobile);
            //txtType= (TextView) v.findViewById(R.id.type);
            txtAfterBal= (TextView) v.findViewById(R.id.after_balance);
            txtBeforeBal= (TextView) v.findViewById(R.id.before_balance);
            txtID= (TextView) v.findViewById(R.id.txtId);
            btnComplain= (AppCompatButton) v.findViewById(R.id.btn_complain);
            laymobile=(LinearLayout)v.findViewById(R.id.lay_mob);
            //layaccountno=(LinearLayout)v.findViewById(R.id.accountno);
            //memberDeatil= (TextView) v.findViewById(R.id.member_detail);
            //cardView = (CardView) v.findViewById(R.id.cardview);
            lyt_parent = (View) v.findViewById(R.id.lyt_parent);
            tv_recharge_type=(TextView) v.findViewById(R.id.tv_recharge_type);
            iv_operator=(ImageView)v.findViewById(R.id.iv_operator);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        //View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rechargehistory, parent, false);
        //View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_top_rechargehistory, parent, false);
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_top_rechargehistory_new, parent, false);
        vh = new OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof OriginalViewHolder) {
            OriginalViewHolder view = (OriginalViewHolder) holder;

            view.amountRecharge.setText(items.get(position).getStr_amount());
            view.recharegDateTime.setText(items.get(position).getStr_datetime());
            //view.txtOperator.setText(items.get(position).getOperator());
            //view.txtMobile.setText("Mobile: "+items.get(position).getMobile());
            view.txtMobile.setText(items.get(position).getMobile());
            //view.txtType.setText(items.get(position).getType());
            view.tv_recharge_type.setText(items.get(position).getType() + " Recharge");
            //view.memberDeatil.setText(items.get(position).getMemberDetail());
            view.txtAfterBal.setText("Closing Bal: "+items.get(position).getAfterBalance());
            view.txtBeforeBal.setText("Opening Bal: "+items.get(position).getBeforeBalance());
            //view.txtaccno.setText(items.get(position).getStr_account_number());


            view.txtID.setText("Txn Id: "+items.get(position).getTxtId());
            getImage(items.get(position).getType().trim(),view);
            //view.iv_operator.setColorFilter(R.color.colorAccent);
            view.iv_operator.setColorFilter(ContextCompat.getColor(ctx,
                    R.color.colorAccent));
            //ImageLoading.loadImages(items.get(position).getStrIcon(), view.iv_operator, 0);
            if (items.get(position).getStr_status().equalsIgnoreCase("1")){
                view.status.setText("Pending");
                view.status.setTextColor(ctx.getResources().getColor(R.color.orange_900));
            }else if (items.get(position).getStr_status().equalsIgnoreCase("2")){
                view.status.setText("Success");
                view.status.setTextColor(ctx.getResources().getColor(R.color.green));
            }else {
                view.status.setText("Fail");
                view.status.setTextColor(ctx.getResources().getColor(R.color.red));
            }

//            if (items.get(position).getStr_account_number().equalsIgnoreCase("")){
//                view.layaccountno.setVisibility(View.GONE);
//            }else {
//                view.layaccountno.setVisibility(View.VISIBLE);
//
//            }
//            if (items.get(position).getMobile().equalsIgnoreCase("")){
//                view.laymobile.setVisibility(View.GONE);
//            }else {
//                view.laymobile.setVisibility(View.VISIBLE);
//
//            }

            if (items.get(position).getMobile().equalsIgnoreCase("")){
                view.txtMobile.setVisibility(View.GONE);
            }else {
                view.txtMobile.setVisibility(View.VISIBLE);

            }

            view.lyt_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(view, items.get(position).getStr_datetime(), position);
                    }
                }
            });

            if (isComplaint) {
                view.btnComplain.setVisibility(View.VISIBLE);
            }else{
                view.btnComplain.setVisibility(View.GONE);
            }
            view.btnComplain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnComplaintItemClickListener != null) {
                        mOnComplaintItemClickListener.onComplaintItemClick(view, items.get(position).getStr_datetime(), position);
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

    private void getImage(String strType,OriginalViewHolder view){
        switch(strType) {
            case "BROADBAND POSTPAID":
                view.iv_operator.setImageResource(R.drawable.bbps_boardband);
                break;
            case "CABLE":
                view.iv_operator.setImageResource(R.drawable.bbps_cable_tv);
                break;
            case "CLUBS AND ASSOCIATIONS":
                view.iv_operator.setImageResource(R.drawable.bbps_club_association);
                break;
            case "CREDIT CARD":
                view.iv_operator.setImageResource(R.drawable.bbps_cradit_card);
                break;
            case "EDUCATION":
                view.iv_operator.setImageResource(R.drawable.bbps_education);
                break;
            case "ELECTRICITY":
                view.iv_operator.setImageResource(R.drawable.bbps_electricity);
                break;
            case "ENTERTAINMENT":
                view.iv_operator.setImageResource(R.drawable.bbps_entertainment);
                break;
            case "FASTAG":
                view.iv_operator.setImageResource(R.drawable.fastag);
                break;
            case "GAS":
                view.iv_operator.setImageResource(R.drawable.bbps_gas);
                break;
            case "HOSPITAL":
                view.iv_operator.setImageResource(R.drawable.bbps_hospital);
                break;
            case "HOUSING SOCIETY":
                view.iv_operator.setImageResource(R.drawable.bbps_housing);
                break;
            case "INSURANCE":
                view.iv_operator.setImageResource(R.drawable.bbps_insurance);
                break;
            case "LANDLINE POSTPAID":
                view.iv_operator.setImageResource(R.drawable.bbps_landline);
                break;
            case "LOAN":
                view.iv_operator.setImageResource(R.drawable.bbps_loan);
                break;
            case "LPG GAS":
                view.iv_operator.setImageResource(R.drawable.bbps_lpg_gas);
                break;
            case "MOBILE POSTPAID":
                view.iv_operator.setImageResource(R.drawable.postpaid);
                break;
            case "MUNICIPAL SERVICES":
                view.iv_operator.setImageResource(R.drawable.bbps_muncipal_tax);
                break;
            case "MUNICIPAL TAXES":
                view.iv_operator.setImageResource(R.drawable.bbps_muncipal_tax);
                break;
            case "SUBSCRIPTION":
                view.iv_operator.setImageResource(R.drawable.bbps_subscription);
                break;
            case "TRANSIT CARD":
                view.iv_operator.setImageResource(R.drawable.bbps_transit_card);
                break;
            case "TRAVEL-SUB":
                view.iv_operator.setImageResource(R.drawable.bbps_travels);
                break;
            case "WATER":
                view.iv_operator.setImageResource(R.drawable.bbps_water);
                break;
            case "OTT":
                view.iv_operator.setImageResource(R.drawable.bbps_subscription);
                break;
            case "MOBILE PREPAID":
                view.iv_operator.setImageResource(R.drawable.postpaid);
                break;
            case "DTH":
                view.iv_operator.setImageResource(R.drawable.dth);
                break;
            default:
                view.iv_operator.setImageResource(R.drawable.bbps_muncipal_tax);
                break;
        }
    }
}