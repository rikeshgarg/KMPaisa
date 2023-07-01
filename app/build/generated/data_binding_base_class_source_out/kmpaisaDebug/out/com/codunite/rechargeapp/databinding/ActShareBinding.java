// Generated by view binder compiler. Do not edit!
package com.codunite.rechargeapp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.codunite.rechargeapp.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActShareBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final AppCompatButton btnSubmit;

  @NonNull
  public final CardView cardText;

  @NonNull
  public final ConstraintLayout headlayout;

  @NonNull
  public final ImageView imgTop;

  @NonNull
  public final IncludeActionbarBinding layActionbar;

  @NonNull
  public final RelativeLayout linearLayout;

  @NonNull
  public final TextView referCode;

  @NonNull
  public final ImageView referWallet;

  @NonNull
  public final TextView txtRefreealcode;

  private ActShareBinding(@NonNull ConstraintLayout rootView, @NonNull AppCompatButton btnSubmit,
      @NonNull CardView cardText, @NonNull ConstraintLayout headlayout, @NonNull ImageView imgTop,
      @NonNull IncludeActionbarBinding layActionbar, @NonNull RelativeLayout linearLayout,
      @NonNull TextView referCode, @NonNull ImageView referWallet,
      @NonNull TextView txtRefreealcode) {
    this.rootView = rootView;
    this.btnSubmit = btnSubmit;
    this.cardText = cardText;
    this.headlayout = headlayout;
    this.imgTop = imgTop;
    this.layActionbar = layActionbar;
    this.linearLayout = linearLayout;
    this.referCode = referCode;
    this.referWallet = referWallet;
    this.txtRefreealcode = txtRefreealcode;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActShareBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActShareBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.act_share, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActShareBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btn_submit;
      AppCompatButton btnSubmit = ViewBindings.findChildViewById(rootView, id);
      if (btnSubmit == null) {
        break missingId;
      }

      id = R.id.card_text;
      CardView cardText = ViewBindings.findChildViewById(rootView, id);
      if (cardText == null) {
        break missingId;
      }

      ConstraintLayout headlayout = (ConstraintLayout) rootView;

      id = R.id.img_top;
      ImageView imgTop = ViewBindings.findChildViewById(rootView, id);
      if (imgTop == null) {
        break missingId;
      }

      id = R.id.lay_actionbar;
      View layActionbar = ViewBindings.findChildViewById(rootView, id);
      if (layActionbar == null) {
        break missingId;
      }
      IncludeActionbarBinding binding_layActionbar = IncludeActionbarBinding.bind(layActionbar);

      id = R.id.linearLayout;
      RelativeLayout linearLayout = ViewBindings.findChildViewById(rootView, id);
      if (linearLayout == null) {
        break missingId;
      }

      id = R.id.refer_code;
      TextView referCode = ViewBindings.findChildViewById(rootView, id);
      if (referCode == null) {
        break missingId;
      }

      id = R.id.refer_wallet;
      ImageView referWallet = ViewBindings.findChildViewById(rootView, id);
      if (referWallet == null) {
        break missingId;
      }

      id = R.id.txt_refreealcode;
      TextView txtRefreealcode = ViewBindings.findChildViewById(rootView, id);
      if (txtRefreealcode == null) {
        break missingId;
      }

      return new ActShareBinding((ConstraintLayout) rootView, btnSubmit, cardText, headlayout,
          imgTop, binding_layActionbar, linearLayout, referCode, referWallet, txtRefreealcode);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
