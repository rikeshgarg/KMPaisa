// Generated by view binder compiler. Do not edit!
package com.codunite.rechargeapp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.codunite.rechargeapp.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActWithdrawalrequestBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final Button btnproceedtopay;

  @NonNull
  public final TextView currentBal;

  @NonNull
  public final EditText edtenteramount;

  @NonNull
  public final EditText edttpwd;

  @NonNull
  public final ConstraintLayout headlayout;

  @NonNull
  public final TextView historyLabel;

  @NonNull
  public final IncludeActionbarBinding layActionbar;

  @NonNull
  public final CardView layTopwallet;

  @NonNull
  public final LinearLayout linearLayout;

  @NonNull
  public final RecyclerView wallethistoryRv;

  private ActWithdrawalrequestBinding(@NonNull ConstraintLayout rootView,
      @NonNull Button btnproceedtopay, @NonNull TextView currentBal,
      @NonNull EditText edtenteramount, @NonNull EditText edttpwd,
      @NonNull ConstraintLayout headlayout, @NonNull TextView historyLabel,
      @NonNull IncludeActionbarBinding layActionbar, @NonNull CardView layTopwallet,
      @NonNull LinearLayout linearLayout, @NonNull RecyclerView wallethistoryRv) {
    this.rootView = rootView;
    this.btnproceedtopay = btnproceedtopay;
    this.currentBal = currentBal;
    this.edtenteramount = edtenteramount;
    this.edttpwd = edttpwd;
    this.headlayout = headlayout;
    this.historyLabel = historyLabel;
    this.layActionbar = layActionbar;
    this.layTopwallet = layTopwallet;
    this.linearLayout = linearLayout;
    this.wallethistoryRv = wallethistoryRv;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActWithdrawalrequestBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActWithdrawalrequestBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.act_withdrawalrequest, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActWithdrawalrequestBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btnproceedtopay;
      Button btnproceedtopay = ViewBindings.findChildViewById(rootView, id);
      if (btnproceedtopay == null) {
        break missingId;
      }

      id = R.id.current_bal;
      TextView currentBal = ViewBindings.findChildViewById(rootView, id);
      if (currentBal == null) {
        break missingId;
      }

      id = R.id.edtenteramount;
      EditText edtenteramount = ViewBindings.findChildViewById(rootView, id);
      if (edtenteramount == null) {
        break missingId;
      }

      id = R.id.edttpwd;
      EditText edttpwd = ViewBindings.findChildViewById(rootView, id);
      if (edttpwd == null) {
        break missingId;
      }

      ConstraintLayout headlayout = (ConstraintLayout) rootView;

      id = R.id.history_label;
      TextView historyLabel = ViewBindings.findChildViewById(rootView, id);
      if (historyLabel == null) {
        break missingId;
      }

      id = R.id.lay_actionbar;
      View layActionbar = ViewBindings.findChildViewById(rootView, id);
      if (layActionbar == null) {
        break missingId;
      }
      IncludeActionbarBinding binding_layActionbar = IncludeActionbarBinding.bind(layActionbar);

      id = R.id.lay_topwallet;
      CardView layTopwallet = ViewBindings.findChildViewById(rootView, id);
      if (layTopwallet == null) {
        break missingId;
      }

      id = R.id.linearLayout;
      LinearLayout linearLayout = ViewBindings.findChildViewById(rootView, id);
      if (linearLayout == null) {
        break missingId;
      }

      id = R.id.wallethistory_rv;
      RecyclerView wallethistoryRv = ViewBindings.findChildViewById(rootView, id);
      if (wallethistoryRv == null) {
        break missingId;
      }

      return new ActWithdrawalrequestBinding((ConstraintLayout) rootView, btnproceedtopay,
          currentBal, edtenteramount, edttpwd, headlayout, historyLabel, binding_layActionbar,
          layTopwallet, linearLayout, wallethistoryRv);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
