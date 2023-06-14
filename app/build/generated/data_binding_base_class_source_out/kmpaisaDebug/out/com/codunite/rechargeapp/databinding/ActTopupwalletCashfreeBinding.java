// Generated by view binder compiler. Do not edit!
package com.codunite.rechargeapp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.codunite.rechargeapp.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActTopupwalletCashfreeBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final TextView btnAmount100;

  @NonNull
  public final TextView btnAmount1000;

  @NonNull
  public final TextView btnAmount1500;

  @NonNull
  public final TextView btnAmount2000;

  @NonNull
  public final TextView btnAmount500;

  @NonNull
  public final AppCompatButton btnproceedtopay;

  @NonNull
  public final TextView currentBal;

  @NonNull
  public final TextView debitCard;

  @NonNull
  public final TextView debitUpi;

  @NonNull
  public final EditText edtenteramount;

  @NonNull
  public final ConstraintLayout headlayout;

  @NonNull
  public final TextView historyLabel;

  @NonNull
  public final IncludeActionbarBinding layActionbar;

  @NonNull
  public final FrameLayout layTopwallet;

  @NonNull
  public final LinearLayout linearLayout;

  @NonNull
  public final RadioGroup rgTxttype;

  @NonNull
  public final RecyclerView wallethistoryRv;

  private ActTopupwalletCashfreeBinding(@NonNull ConstraintLayout rootView,
      @NonNull TextView btnAmount100, @NonNull TextView btnAmount1000,
      @NonNull TextView btnAmount1500, @NonNull TextView btnAmount2000,
      @NonNull TextView btnAmount500, @NonNull AppCompatButton btnproceedtopay,
      @NonNull TextView currentBal, @NonNull TextView debitCard, @NonNull TextView debitUpi,
      @NonNull EditText edtenteramount, @NonNull ConstraintLayout headlayout,
      @NonNull TextView historyLabel, @NonNull IncludeActionbarBinding layActionbar,
      @NonNull FrameLayout layTopwallet, @NonNull LinearLayout linearLayout,
      @NonNull RadioGroup rgTxttype, @NonNull RecyclerView wallethistoryRv) {
    this.rootView = rootView;
    this.btnAmount100 = btnAmount100;
    this.btnAmount1000 = btnAmount1000;
    this.btnAmount1500 = btnAmount1500;
    this.btnAmount2000 = btnAmount2000;
    this.btnAmount500 = btnAmount500;
    this.btnproceedtopay = btnproceedtopay;
    this.currentBal = currentBal;
    this.debitCard = debitCard;
    this.debitUpi = debitUpi;
    this.edtenteramount = edtenteramount;
    this.headlayout = headlayout;
    this.historyLabel = historyLabel;
    this.layActionbar = layActionbar;
    this.layTopwallet = layTopwallet;
    this.linearLayout = linearLayout;
    this.rgTxttype = rgTxttype;
    this.wallethistoryRv = wallethistoryRv;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActTopupwalletCashfreeBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActTopupwalletCashfreeBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.act_topupwallet_cashfree, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActTopupwalletCashfreeBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btn_amount100;
      TextView btnAmount100 = ViewBindings.findChildViewById(rootView, id);
      if (btnAmount100 == null) {
        break missingId;
      }

      id = R.id.btn_amount1000;
      TextView btnAmount1000 = ViewBindings.findChildViewById(rootView, id);
      if (btnAmount1000 == null) {
        break missingId;
      }

      id = R.id.btn_amount1500;
      TextView btnAmount1500 = ViewBindings.findChildViewById(rootView, id);
      if (btnAmount1500 == null) {
        break missingId;
      }

      id = R.id.btn_amount2000;
      TextView btnAmount2000 = ViewBindings.findChildViewById(rootView, id);
      if (btnAmount2000 == null) {
        break missingId;
      }

      id = R.id.btn_amount500;
      TextView btnAmount500 = ViewBindings.findChildViewById(rootView, id);
      if (btnAmount500 == null) {
        break missingId;
      }

      id = R.id.btnproceedtopay;
      AppCompatButton btnproceedtopay = ViewBindings.findChildViewById(rootView, id);
      if (btnproceedtopay == null) {
        break missingId;
      }

      id = R.id.current_bal;
      TextView currentBal = ViewBindings.findChildViewById(rootView, id);
      if (currentBal == null) {
        break missingId;
      }

      id = R.id.debit_card;
      TextView debitCard = ViewBindings.findChildViewById(rootView, id);
      if (debitCard == null) {
        break missingId;
      }

      id = R.id.debit_upi;
      TextView debitUpi = ViewBindings.findChildViewById(rootView, id);
      if (debitUpi == null) {
        break missingId;
      }

      id = R.id.edtenteramount;
      EditText edtenteramount = ViewBindings.findChildViewById(rootView, id);
      if (edtenteramount == null) {
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
      FrameLayout layTopwallet = ViewBindings.findChildViewById(rootView, id);
      if (layTopwallet == null) {
        break missingId;
      }

      id = R.id.linearLayout;
      LinearLayout linearLayout = ViewBindings.findChildViewById(rootView, id);
      if (linearLayout == null) {
        break missingId;
      }

      id = R.id.rg_txttype;
      RadioGroup rgTxttype = ViewBindings.findChildViewById(rootView, id);
      if (rgTxttype == null) {
        break missingId;
      }

      id = R.id.wallethistory_rv;
      RecyclerView wallethistoryRv = ViewBindings.findChildViewById(rootView, id);
      if (wallethistoryRv == null) {
        break missingId;
      }

      return new ActTopupwalletCashfreeBinding((ConstraintLayout) rootView, btnAmount100,
          btnAmount1000, btnAmount1500, btnAmount2000, btnAmount500, btnproceedtopay, currentBal,
          debitCard, debitUpi, edtenteramount, headlayout, historyLabel, binding_layActionbar,
          layTopwallet, linearLayout, rgTxttype, wallethistoryRv);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
