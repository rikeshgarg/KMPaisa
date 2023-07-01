package com.codunite.rechargeapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.codunite.commonutility.ItemAnimation;
import com.codunite.rechargeapp.R;
import com.codunite.rechargeapp.model.BeneficiaryListModel;

import java.util.ArrayList;
import java.util.List;

public class BeneficiaryListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<BeneficiaryListModel> items = new ArrayList<>();
    private Context ctx;
    private OnItemClickListener mOnItemClickListener;

    private OnComplaintItemClickListener mOnComplaintItemClickListener;
    private int animation_type = 0;
    private String[] strColors = {"#388E3C", "#D32F2F"};
    private boolean isComplaint;

    public interface OnItemClickListener {
        void onItemClick(View view, BeneficiaryListModel obj, int position);

        void onItemDelete(View view, BeneficiaryListModel obj, int position);
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

    public BeneficiaryListAdapter(Context context, List<BeneficiaryListModel> items, int animation_type, boolean isComplaint) {
        this.isComplaint = isComplaint;
        this.items = items;
        ctx = context;
        this.animation_type = animation_type;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        public TextView txtname, txtaccntno, txtifsc, txtbank, txtmob, txtstatus, txtotp;
        public CardView cardView;
        public View lyt_parent;
        public Button btntransfer;
        public ImageView btnDelete;

        public OriginalViewHolder(View v) {
            super(v);
            txtname = (TextView) v.findViewById(R.id.tv_name);
            txtaccntno = (TextView) v.findViewById(R.id.tv_accntno);
            txtifsc = (TextView) v.findViewById(R.id.tv_ifsc);
            txtbank = (TextView) v.findViewById(R.id.tv_bnkname);
            //   txttransasctionid = (TextView) v.findViewById(R.id.tv_trnsc_id);
            txtotp = (TextView) v.findViewById(R.id.tv_otp_verify);
            txtstatus = (TextView) v.findViewById(R.id.tv_acc_status);
            txtmob = (TextView) v.findViewById(R.id.tv_ben_mob);
            btnDelete = v.findViewById(R.id.img_delete);

            btntransfer = (Button) v.findViewById(R.id.btn_transfer_now);

            cardView = (CardView) v.findViewById(R.id.cardview);
            lyt_parent = (View) v.findViewById(R.id.lyt_parent);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_beneficiarydetails, parent, false);
        vh = new OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof OriginalViewHolder) {
            OriginalViewHolder view = (OriginalViewHolder) holder;

            //view.txtamount.setText(GlobalVariables.CURRENCYSYMBOL+items.get(position).getStr_amount());
            // view.txtdate.setText(items.get(position).getStr_datetime());
            // view.txtrrn.setText(items.get(position).getStr_rrn());
            //  view.txtMobile.setText(items.get(position).getMobile());
            // view.txttransasctionid.setText(items.get(position).getStr_txn_id());
            view.txtname.setText(items.get(position).getStr_name());
            view.txtaccntno.setText(items.get(position).getStr_account_no());
            view.txtifsc.setText(items.get(position).getStr_ifsc());
            view.txtbank.setText(items.get(position).getStr_bank_name());
            view.txtotp.setText(items.get(position).getStr_otp_verify());

            view.txtstatus.setText(items.get(position).getStr_status());
            //view.txtmob.setText(items.get(position).getStr_benMob());


            //   view.txtmember.setText(items.get(position).getStr_member_id());

            //    if (items.get(position).getStr_status().equalsIgnoreCase("1")){
            //         view.txtstatus.setText("Pending");
            //         view.txtstatus.setTextColor(ctx.getResources().getColor(R.color.orange_900));
            //    }else if (items.get(position).getStr_status().equalsIgnoreCase("2")){
            //        view.txtstatus.setText("Success");
            //        view.txtstatus.setTextColor(ctx.getResources().getColor(R.color.green));
            //   }else {
            //      view.txtstatus.setText("Fail");
            //       view.txtstatus.setTextColor(ctx.getResources().getColor(R.color.red));
            //    }


//            if ((items.get(position).getStr_status()).equalsIgnoreCase("1")) {
//                view.cardView.setCardBackgroundColor(Color.parseColor(strColors[0]));
//            } else {
//                view.cardView.setCardBackgroundColor(Color.parseColor(strColors[1]));
//            }

//            view.lyt_parent.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (mOnItemClickListener != null) {
//                        mOnItemClickListener.onItemClick(view, items.get(position).getStr_name(), position);
//                    }
//                }
//            });

            view.btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemDelete(view, items.get(position), position);
                    }
                }
            });

            view.btntransfer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(view, items.get(position), position);
                    }
                }
            });

            //        if (isComplaint) {
            //          view.btnComplain.setVisibility(View.VISIBLE);
            //       }else{
            //         view.btnComplain.setVisibility(View.GONE);
            //      }
            //       view.btnComplain.setOnClickListener(new View.OnClickListener() {
            //          @Override
            //          public void onClick(View view) {
            //            if (mOnComplaintItemClickListener != null) {
            //               mOnComplaintItemClickListener.onComplaintItemClick(view, items.get(position).getStr_datetime(), position);
            //            }
            //        }
            //     });
            //     setAnimation(view.itemView, position);
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