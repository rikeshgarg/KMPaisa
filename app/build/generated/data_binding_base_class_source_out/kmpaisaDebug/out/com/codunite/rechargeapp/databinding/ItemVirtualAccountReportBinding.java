// Generated by view binder compiler. Do not edit!
package com.codunite.rechargeapp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public final class ItemVirtualAccountReportBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final CardView cardview;

  @NonNull
  public final LinearLayout layHeadChange;

  @NonNull
  public final LinearLayout lytParent;

  @NonNull
  public final TextView tvAmount;

  @NonNull
  public final TextView tvBanktxnId;

  @NonNull
  public final TextView tvDatetime;

  @NonNull
  public final TextView tvMode;

  @NonNull
  public final TextView tvPayerAccountno;

  @NonNull
  public final TextView tvPayerName;

  @NonNull
  public final TextView tvRemark;

  @NonNull
  public final TextView tvUtr;

  @NonNull
  public final TextView tvVirtualac;

  private ItemVirtualAccountReportBinding(@NonNull LinearLayout rootView,
      @NonNull CardView cardview, @NonNull LinearLayout layHeadChange,
      @NonNull LinearLayout lytParent, @NonNull TextView tvAmount, @NonNull TextView tvBanktxnId,
      @NonNull TextView tvDatetime, @NonNull TextView tvMode, @NonNull TextView tvPayerAccountno,
      @NonNull TextView tvPayerName, @NonNull TextView tvRemark, @NonNull TextView tvUtr,
      @NonNull TextView tvVirtualac) {
    this.rootView = rootView;
    this.cardview = cardview;
    this.layHeadChange = layHeadChange;
    this.lytParent = lytParent;
    this.tvAmount = tvAmount;
    this.tvBanktxnId = tvBanktxnId;
    this.tvDatetime = tvDatetime;
    this.tvMode = tvMode;
    this.tvPayerAccountno = tvPayerAccountno;
    this.tvPayerName = tvPayerName;
    this.tvRemark = tvRemark;
    this.tvUtr = tvUtr;
    this.tvVirtualac = tvVirtualac;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ItemVirtualAccountReportBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ItemVirtualAccountReportBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.item_virtual_account_report, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ItemVirtualAccountReportBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.cardview;
      CardView cardview = ViewBindings.findChildViewById(rootView, id);
      if (cardview == null) {
        break missingId;
      }

      id = R.id.lay_head_change;
      LinearLayout layHeadChange = ViewBindings.findChildViewById(rootView, id);
      if (layHeadChange == null) {
        break missingId;
      }

      LinearLayout lytParent = (LinearLayout) rootView;

      id = R.id.tv_amount;
      TextView tvAmount = ViewBindings.findChildViewById(rootView, id);
      if (tvAmount == null) {
        break missingId;
      }

      id = R.id.tv_banktxn_id;
      TextView tvBanktxnId = ViewBindings.findChildViewById(rootView, id);
      if (tvBanktxnId == null) {
        break missingId;
      }

      id = R.id.tv_datetime;
      TextView tvDatetime = ViewBindings.findChildViewById(rootView, id);
      if (tvDatetime == null) {
        break missingId;
      }

      id = R.id.tv_mode;
      TextView tvMode = ViewBindings.findChildViewById(rootView, id);
      if (tvMode == null) {
        break missingId;
      }

      id = R.id.tv_payer_accountno;
      TextView tvPayerAccountno = ViewBindings.findChildViewById(rootView, id);
      if (tvPayerAccountno == null) {
        break missingId;
      }

      id = R.id.tv_payer_name;
      TextView tvPayerName = ViewBindings.findChildViewById(rootView, id);
      if (tvPayerName == null) {
        break missingId;
      }

      id = R.id.tv_remark;
      TextView tvRemark = ViewBindings.findChildViewById(rootView, id);
      if (tvRemark == null) {
        break missingId;
      }

      id = R.id.tv_utr;
      TextView tvUtr = ViewBindings.findChildViewById(rootView, id);
      if (tvUtr == null) {
        break missingId;
      }

      id = R.id.tv_virtualac;
      TextView tvVirtualac = ViewBindings.findChildViewById(rootView, id);
      if (tvVirtualac == null) {
        break missingId;
      }

      return new ItemVirtualAccountReportBinding((LinearLayout) rootView, cardview, layHeadChange,
          lytParent, tvAmount, tvBanktxnId, tvDatetime, tvMode, tvPayerAccountno, tvPayerName,
          tvRemark, tvUtr, tvVirtualac);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
