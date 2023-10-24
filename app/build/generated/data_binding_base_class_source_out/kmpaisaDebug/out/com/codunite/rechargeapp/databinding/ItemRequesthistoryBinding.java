// Generated by view binder compiler. Do not edit!
package com.codunite.rechargeapp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.codunite.rechargeapp.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ItemRequesthistoryBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final TextView amount;

  @NonNull
  public final CardView cvRecharge;

  @NonNull
  public final TextView datetime;

  @NonNull
  public final ImageView ivOperator;

  @NonNull
  public final LinearLayout layAmount;

  @NonNull
  public final LinearLayout llImg;

  @NonNull
  public final LinearLayout lytParent;

  @NonNull
  public final TextView tvRequestId;

  @NonNull
  public final TextView tvStatus;

  @NonNull
  public final TextView tvTxnId;

  private ItemRequesthistoryBinding(@NonNull LinearLayout rootView, @NonNull TextView amount,
      @NonNull CardView cvRecharge, @NonNull TextView datetime, @NonNull ImageView ivOperator,
      @NonNull LinearLayout layAmount, @NonNull LinearLayout llImg, @NonNull LinearLayout lytParent,
      @NonNull TextView tvRequestId, @NonNull TextView tvStatus, @NonNull TextView tvTxnId) {
    this.rootView = rootView;
    this.amount = amount;
    this.cvRecharge = cvRecharge;
    this.datetime = datetime;
    this.ivOperator = ivOperator;
    this.layAmount = layAmount;
    this.llImg = llImg;
    this.lytParent = lytParent;
    this.tvRequestId = tvRequestId;
    this.tvStatus = tvStatus;
    this.tvTxnId = tvTxnId;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ItemRequesthistoryBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ItemRequesthistoryBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.item_requesthistory, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ItemRequesthistoryBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.amount;
      TextView amount = ViewBindings.findChildViewById(rootView, id);
      if (amount == null) {
        break missingId;
      }

      id = R.id.cv_recharge;
      CardView cvRecharge = ViewBindings.findChildViewById(rootView, id);
      if (cvRecharge == null) {
        break missingId;
      }

      id = R.id.datetime;
      TextView datetime = ViewBindings.findChildViewById(rootView, id);
      if (datetime == null) {
        break missingId;
      }

      id = R.id.iv_operator;
      ImageView ivOperator = ViewBindings.findChildViewById(rootView, id);
      if (ivOperator == null) {
        break missingId;
      }

      id = R.id.lay_amount;
      LinearLayout layAmount = ViewBindings.findChildViewById(rootView, id);
      if (layAmount == null) {
        break missingId;
      }

      id = R.id.ll_img;
      LinearLayout llImg = ViewBindings.findChildViewById(rootView, id);
      if (llImg == null) {
        break missingId;
      }

      LinearLayout lytParent = (LinearLayout) rootView;

      id = R.id.tv_request_id;
      TextView tvRequestId = ViewBindings.findChildViewById(rootView, id);
      if (tvRequestId == null) {
        break missingId;
      }

      id = R.id.tv_status;
      TextView tvStatus = ViewBindings.findChildViewById(rootView, id);
      if (tvStatus == null) {
        break missingId;
      }

      id = R.id.tv_txn_id;
      TextView tvTxnId = ViewBindings.findChildViewById(rootView, id);
      if (tvTxnId == null) {
        break missingId;
      }

      return new ItemRequesthistoryBinding((LinearLayout) rootView, amount, cvRecharge, datetime,
          ivOperator, layAmount, llImg, lytParent, tvRequestId, tvStatus, tvTxnId);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}