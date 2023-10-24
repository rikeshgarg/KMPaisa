package com.rechargeapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codunite.rechargeapp.R;
import com.rechargeapp.model.SearchMemberListModel;

import java.util.ArrayList;
import java.util.List;


public class SearchAdaper extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<SearchMemberListModel> items = new ArrayList<>();
    private Context ctx;
    private OnItemClickListener mOnItemClickListener;

    int wallettype;
    public SearchAdaper(Context context, List<SearchMemberListModel> users,int wallettype) {
        this.items=users;
        ctx = context;
        this.wallettype= wallettype;

    }

    public interface OnItemClickListener {
        void onItemClick(View view, String obj, String obj1, String obj2,int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

public class OriginalViewHolder extends RecyclerView.ViewHolder {
    public TextView  tv_member, tv_mobile;
    LinearLayout lyt_parent;

    public OriginalViewHolder(View v) {
        super(v);
        tv_member = (TextView) v.findViewById(R.id.tv_member);
        tv_mobile = (TextView) v.findViewById(R.id.tv_mobile);
        lyt_parent=(LinearLayout) v.findViewById(R.id.lyt_parent);

    }
}

//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        // Get the data item for this position
//        SearchMemberListModel searchMemberListModel = getItem(position);
//        // Check if an existing view is being reused, otherwise inflate the view
//        if (convertView == null) {
//            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_member_search_list, parent, false);
//        }
//        // Lookup view for data population
//        TextView tvName = (TextView) convertView.findViewById(R.id.tv_member);
//        TextView tv_mobile= (TextView) convertView.findViewById(R.id.tv_mobile);
//        tvName.setText(searchMemberListModel.getName() + " ("+ searchMemberListModel.getUser_code()+") ");
//        tv_mobile.setText(searchMemberListModel.getMobile());
//        return convertView;
//    }

    public void filterList(List<SearchMemberListModel> filterlist) {
        items = filterlist;
        notifyDataSetChanged();
    }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            RecyclerView.ViewHolder vh;
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_member_search_list, parent, false);
            vh = new OriginalViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof OriginalViewHolder) {
                OriginalViewHolder view = (OriginalViewHolder) holder;
                view.tv_member.setText(items.get(position).getName() + " ("+ items.get(position).getUser_code()+") ");
                view.tv_mobile.setText(items.get(position).getMobile());

                view.lyt_parent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mOnItemClickListener != null) {
                            if(wallettype==1){
                                mOnItemClickListener.onItemClick(view, items.get(position).getName(), items.get(position).getWallet_balance(), items.get(position).getUser_id(), position);
                            } else if(wallettype==2) {
                                mOnItemClickListener.onItemClick(view, items.get(position).getName(), items.get(position).getE_wallet_balance(), items.get(position).getUser_id(), position);
                            }
                        }
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return items.size();
        }



}
