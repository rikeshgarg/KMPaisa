// Generated by view binder compiler. Do not edit!
package com.codunite.rechargeapp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.widget.NestedScrollView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.codunite.rechargeapp.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActBbpselectricitytollBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final Button btnElectricrecharge;

  @NonNull
  public final Button btnFetch;

  @NonNull
  public final AppCompatButton btnOtpauth;

  @NonNull
  public final AppCompatButton btnOtpcancel;

  @NonNull
  public final LinearLayout cardElectrical;

  @NonNull
  public final LinearLayout cardOtp;

  @NonNull
  public final EditText edtOtp;

  @NonNull
  public final EditText electricityAmount;

  @NonNull
  public final LinearLayout headlayout;

  @NonNull
  public final ImageView imgDrop1;

  @NonNull
  public final IncludeActionbarBinding layActionbar;

  @NonNull
  public final LinearLayout layBiller;

  @NonNull
  public final RelativeLayout layCircle;

  @NonNull
  public final LinearLayout layDynamicLay;

  @NonNull
  public final NestedScrollView nestedScrollView;

  @NonNull
  public final Spinner spinnerElectricoperatorlist;

  @NonNull
  public final TextView spinnerElectricoperatorlistTxt;

  @NonNull
  public final TextView tvHeading;

  @NonNull
  public final TextView txtCurrency;

  @NonNull
  public final TextView txtUsername;

  @NonNull
  public final TextView txtViewallplans;

  private ActBbpselectricitytollBinding(@NonNull LinearLayout rootView,
      @NonNull Button btnElectricrecharge, @NonNull Button btnFetch,
      @NonNull AppCompatButton btnOtpauth, @NonNull AppCompatButton btnOtpcancel,
      @NonNull LinearLayout cardElectrical, @NonNull LinearLayout cardOtp, @NonNull EditText edtOtp,
      @NonNull EditText electricityAmount, @NonNull LinearLayout headlayout,
      @NonNull ImageView imgDrop1, @NonNull IncludeActionbarBinding layActionbar,
      @NonNull LinearLayout layBiller, @NonNull RelativeLayout layCircle,
      @NonNull LinearLayout layDynamicLay, @NonNull NestedScrollView nestedScrollView,
      @NonNull Spinner spinnerElectricoperatorlist,
      @NonNull TextView spinnerElectricoperatorlistTxt, @NonNull TextView tvHeading,
      @NonNull TextView txtCurrency, @NonNull TextView txtUsername,
      @NonNull TextView txtViewallplans) {
    this.rootView = rootView;
    this.btnElectricrecharge = btnElectricrecharge;
    this.btnFetch = btnFetch;
    this.btnOtpauth = btnOtpauth;
    this.btnOtpcancel = btnOtpcancel;
    this.cardElectrical = cardElectrical;
    this.cardOtp = cardOtp;
    this.edtOtp = edtOtp;
    this.electricityAmount = electricityAmount;
    this.headlayout = headlayout;
    this.imgDrop1 = imgDrop1;
    this.layActionbar = layActionbar;
    this.layBiller = layBiller;
    this.layCircle = layCircle;
    this.layDynamicLay = layDynamicLay;
    this.nestedScrollView = nestedScrollView;
    this.spinnerElectricoperatorlist = spinnerElectricoperatorlist;
    this.spinnerElectricoperatorlistTxt = spinnerElectricoperatorlistTxt;
    this.tvHeading = tvHeading;
    this.txtCurrency = txtCurrency;
    this.txtUsername = txtUsername;
    this.txtViewallplans = txtViewallplans;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActBbpselectricitytollBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActBbpselectricitytollBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.act_bbpselectricitytoll, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActBbpselectricitytollBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btn_electricrecharge;
      Button btnElectricrecharge = ViewBindings.findChildViewById(rootView, id);
      if (btnElectricrecharge == null) {
        break missingId;
      }

      id = R.id.btn_fetch;
      Button btnFetch = ViewBindings.findChildViewById(rootView, id);
      if (btnFetch == null) {
        break missingId;
      }

      id = R.id.btn_otpauth;
      AppCompatButton btnOtpauth = ViewBindings.findChildViewById(rootView, id);
      if (btnOtpauth == null) {
        break missingId;
      }

      id = R.id.btn_otpcancel;
      AppCompatButton btnOtpcancel = ViewBindings.findChildViewById(rootView, id);
      if (btnOtpcancel == null) {
        break missingId;
      }

      id = R.id.card_electrical;
      LinearLayout cardElectrical = ViewBindings.findChildViewById(rootView, id);
      if (cardElectrical == null) {
        break missingId;
      }

      id = R.id.card_otp;
      LinearLayout cardOtp = ViewBindings.findChildViewById(rootView, id);
      if (cardOtp == null) {
        break missingId;
      }

      id = R.id.edt_otp;
      EditText edtOtp = ViewBindings.findChildViewById(rootView, id);
      if (edtOtp == null) {
        break missingId;
      }

      id = R.id.electricity_amount;
      EditText electricityAmount = ViewBindings.findChildViewById(rootView, id);
      if (electricityAmount == null) {
        break missingId;
      }

      LinearLayout headlayout = (LinearLayout) rootView;

      id = R.id.img_drop_1;
      ImageView imgDrop1 = ViewBindings.findChildViewById(rootView, id);
      if (imgDrop1 == null) {
        break missingId;
      }

      id = R.id.lay_actionbar;
      View layActionbar = ViewBindings.findChildViewById(rootView, id);
      if (layActionbar == null) {
        break missingId;
      }
      IncludeActionbarBinding binding_layActionbar = IncludeActionbarBinding.bind(layActionbar);

      id = R.id.lay_biller;
      LinearLayout layBiller = ViewBindings.findChildViewById(rootView, id);
      if (layBiller == null) {
        break missingId;
      }

      id = R.id.lay_circle;
      RelativeLayout layCircle = ViewBindings.findChildViewById(rootView, id);
      if (layCircle == null) {
        break missingId;
      }

      id = R.id.lay_dynamic_lay;
      LinearLayout layDynamicLay = ViewBindings.findChildViewById(rootView, id);
      if (layDynamicLay == null) {
        break missingId;
      }

      id = R.id.nested_scroll_view;
      NestedScrollView nestedScrollView = ViewBindings.findChildViewById(rootView, id);
      if (nestedScrollView == null) {
        break missingId;
      }

      id = R.id.spinner_electricoperatorlist;
      Spinner spinnerElectricoperatorlist = ViewBindings.findChildViewById(rootView, id);
      if (spinnerElectricoperatorlist == null) {
        break missingId;
      }

      id = R.id.spinner_electricoperatorlist_txt;
      TextView spinnerElectricoperatorlistTxt = ViewBindings.findChildViewById(rootView, id);
      if (spinnerElectricoperatorlistTxt == null) {
        break missingId;
      }

      id = R.id.tv_heading;
      TextView tvHeading = ViewBindings.findChildViewById(rootView, id);
      if (tvHeading == null) {
        break missingId;
      }

      id = R.id.txt_currency;
      TextView txtCurrency = ViewBindings.findChildViewById(rootView, id);
      if (txtCurrency == null) {
        break missingId;
      }

      id = R.id.txt_username;
      TextView txtUsername = ViewBindings.findChildViewById(rootView, id);
      if (txtUsername == null) {
        break missingId;
      }

      id = R.id.txt_viewallplans;
      TextView txtViewallplans = ViewBindings.findChildViewById(rootView, id);
      if (txtViewallplans == null) {
        break missingId;
      }

      return new ActBbpselectricitytollBinding((LinearLayout) rootView, btnElectricrecharge,
          btnFetch, btnOtpauth, btnOtpcancel, cardElectrical, cardOtp, edtOtp, electricityAmount,
          headlayout, imgDrop1, binding_layActionbar, layBiller, layCircle, layDynamicLay,
          nestedScrollView, spinnerElectricoperatorlist, spinnerElectricoperatorlistTxt, tvHeading,
          txtCurrency, txtUsername, txtViewallplans);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
