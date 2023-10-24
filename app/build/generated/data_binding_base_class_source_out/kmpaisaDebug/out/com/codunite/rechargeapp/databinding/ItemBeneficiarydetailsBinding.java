// Generated by view binder compiler. Do not edit!
package com.codunite.rechargeapp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

public final class ItemBeneficiarydetailsBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final Button btnTransferNow;

  @NonNull
  public final CardView cardview;

  @NonNull
  public final ImageView imgDelete;

  @NonNull
  public final LinearLayout layHeadChange;

  @NonNull
  public final LinearLayout lytParent;

  @NonNull
  public final TextView tvAccStatus;

  @NonNull
  public final TextView tvAccntno;

  @NonNull
  public final TextView tvBenMob;

  @NonNull
  public final TextView tvBnkname;

  @NonNull
  public final TextView tvIfsc;

  @NonNull
  public final TextView tvName;

  @NonNull
  public final TextView tvOtpVerify;

  private ItemBeneficiarydetailsBinding(@NonNull LinearLayout rootView,
      @NonNull Button btnTransferNow, @NonNull CardView cardview, @NonNull ImageView imgDelete,
      @NonNull LinearLayout layHeadChange, @NonNull LinearLayout lytParent,
      @NonNull TextView tvAccStatus, @NonNull TextView tvAccntno, @NonNull TextView tvBenMob,
      @NonNull TextView tvBnkname, @NonNull TextView tvIfsc, @NonNull TextView tvName,
      @NonNull TextView tvOtpVerify) {
    this.rootView = rootView;
    this.btnTransferNow = btnTransferNow;
    this.cardview = cardview;
    this.imgDelete = imgDelete;
    this.layHeadChange = layHeadChange;
    this.lytParent = lytParent;
    this.tvAccStatus = tvAccStatus;
    this.tvAccntno = tvAccntno;
    this.tvBenMob = tvBenMob;
    this.tvBnkname = tvBnkname;
    this.tvIfsc = tvIfsc;
    this.tvName = tvName;
    this.tvOtpVerify = tvOtpVerify;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ItemBeneficiarydetailsBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ItemBeneficiarydetailsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.item_beneficiarydetails, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ItemBeneficiarydetailsBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btn_transfer_now;
      Button btnTransferNow = ViewBindings.findChildViewById(rootView, id);
      if (btnTransferNow == null) {
        break missingId;
      }

      id = R.id.cardview;
      CardView cardview = ViewBindings.findChildViewById(rootView, id);
      if (cardview == null) {
        break missingId;
      }

      id = R.id.img_delete;
      ImageView imgDelete = ViewBindings.findChildViewById(rootView, id);
      if (imgDelete == null) {
        break missingId;
      }

      id = R.id.lay_head_change;
      LinearLayout layHeadChange = ViewBindings.findChildViewById(rootView, id);
      if (layHeadChange == null) {
        break missingId;
      }

      LinearLayout lytParent = (LinearLayout) rootView;

      id = R.id.tv_acc_status;
      TextView tvAccStatus = ViewBindings.findChildViewById(rootView, id);
      if (tvAccStatus == null) {
        break missingId;
      }

      id = R.id.tv_accntno;
      TextView tvAccntno = ViewBindings.findChildViewById(rootView, id);
      if (tvAccntno == null) {
        break missingId;
      }

      id = R.id.tv_ben_mob;
      TextView tvBenMob = ViewBindings.findChildViewById(rootView, id);
      if (tvBenMob == null) {
        break missingId;
      }

      id = R.id.tv_bnkname;
      TextView tvBnkname = ViewBindings.findChildViewById(rootView, id);
      if (tvBnkname == null) {
        break missingId;
      }

      id = R.id.tv_ifsc;
      TextView tvIfsc = ViewBindings.findChildViewById(rootView, id);
      if (tvIfsc == null) {
        break missingId;
      }

      id = R.id.tv_name;
      TextView tvName = ViewBindings.findChildViewById(rootView, id);
      if (tvName == null) {
        break missingId;
      }

      id = R.id.tv_otp_verify;
      TextView tvOtpVerify = ViewBindings.findChildViewById(rootView, id);
      if (tvOtpVerify == null) {
        break missingId;
      }

      return new ItemBeneficiarydetailsBinding((LinearLayout) rootView, btnTransferNow, cardview,
          imgDelete, layHeadChange, lytParent, tvAccStatus, tvAccntno, tvBenMob, tvBnkname, tvIfsc,
          tvName, tvOtpVerify);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}