// Generated by view binder compiler. Do not edit!
package com.codunite.rechargeapp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.codunite.rechargeapp.R;
import com.codunite.rechargeapp.utility.CustomViewPager;
import com.google.android.material.tabs.TabLayout;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActRechargeBinding implements ViewBinding {
  @NonNull
  private final CoordinatorLayout rootView;

  @NonNull
  public final FrameLayout bottomSheet;

  @NonNull
  public final CoordinatorLayout headlayout;

  @NonNull
  public final RecyclerView historyRv;

  @NonNull
  public final ImageView imgBack;

  @NonNull
  public final LinearLayout imgDashLogo;

  @NonNull
  public final LinearLayout imgEwallet;

  @NonNull
  public final LinearLayout imgWallet;

  @NonNull
  public final RelativeLayout layActionbar;

  @NonNull
  public final LinearLayout prepaidAndSemilarLayout;

  @NonNull
  public final TabLayout tabs;

  @NonNull
  public final TextView toolbarTxtEwalletbal;

  @NonNull
  public final TextView toolbarTxtWalletbal;

  @NonNull
  public final TextView tvHeading;

  @NonNull
  public final CustomViewPager viewPager;

  private ActRechargeBinding(@NonNull CoordinatorLayout rootView, @NonNull FrameLayout bottomSheet,
      @NonNull CoordinatorLayout headlayout, @NonNull RecyclerView historyRv,
      @NonNull ImageView imgBack, @NonNull LinearLayout imgDashLogo,
      @NonNull LinearLayout imgEwallet, @NonNull LinearLayout imgWallet,
      @NonNull RelativeLayout layActionbar, @NonNull LinearLayout prepaidAndSemilarLayout,
      @NonNull TabLayout tabs, @NonNull TextView toolbarTxtEwalletbal,
      @NonNull TextView toolbarTxtWalletbal, @NonNull TextView tvHeading,
      @NonNull CustomViewPager viewPager) {
    this.rootView = rootView;
    this.bottomSheet = bottomSheet;
    this.headlayout = headlayout;
    this.historyRv = historyRv;
    this.imgBack = imgBack;
    this.imgDashLogo = imgDashLogo;
    this.imgEwallet = imgEwallet;
    this.imgWallet = imgWallet;
    this.layActionbar = layActionbar;
    this.prepaidAndSemilarLayout = prepaidAndSemilarLayout;
    this.tabs = tabs;
    this.toolbarTxtEwalletbal = toolbarTxtEwalletbal;
    this.toolbarTxtWalletbal = toolbarTxtWalletbal;
    this.tvHeading = tvHeading;
    this.viewPager = viewPager;
  }

  @Override
  @NonNull
  public CoordinatorLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActRechargeBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActRechargeBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.act_recharge, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActRechargeBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.bottom_sheet;
      FrameLayout bottomSheet = ViewBindings.findChildViewById(rootView, id);
      if (bottomSheet == null) {
        break missingId;
      }

      CoordinatorLayout headlayout = (CoordinatorLayout) rootView;

      id = R.id.history_rv;
      RecyclerView historyRv = ViewBindings.findChildViewById(rootView, id);
      if (historyRv == null) {
        break missingId;
      }

      id = R.id.img_back;
      ImageView imgBack = ViewBindings.findChildViewById(rootView, id);
      if (imgBack == null) {
        break missingId;
      }

      id = R.id.img_dash_logo;
      LinearLayout imgDashLogo = ViewBindings.findChildViewById(rootView, id);
      if (imgDashLogo == null) {
        break missingId;
      }

      id = R.id.img_ewallet;
      LinearLayout imgEwallet = ViewBindings.findChildViewById(rootView, id);
      if (imgEwallet == null) {
        break missingId;
      }

      id = R.id.img_wallet;
      LinearLayout imgWallet = ViewBindings.findChildViewById(rootView, id);
      if (imgWallet == null) {
        break missingId;
      }

      id = R.id.lay_actionbar;
      RelativeLayout layActionbar = ViewBindings.findChildViewById(rootView, id);
      if (layActionbar == null) {
        break missingId;
      }

      id = R.id.prepaid_and_semilar_layout;
      LinearLayout prepaidAndSemilarLayout = ViewBindings.findChildViewById(rootView, id);
      if (prepaidAndSemilarLayout == null) {
        break missingId;
      }

      id = R.id.tabs;
      TabLayout tabs = ViewBindings.findChildViewById(rootView, id);
      if (tabs == null) {
        break missingId;
      }

      id = R.id.toolbar_txt_ewalletbal;
      TextView toolbarTxtEwalletbal = ViewBindings.findChildViewById(rootView, id);
      if (toolbarTxtEwalletbal == null) {
        break missingId;
      }

      id = R.id.toolbar_txt_walletbal;
      TextView toolbarTxtWalletbal = ViewBindings.findChildViewById(rootView, id);
      if (toolbarTxtWalletbal == null) {
        break missingId;
      }

      id = R.id.tv_heading;
      TextView tvHeading = ViewBindings.findChildViewById(rootView, id);
      if (tvHeading == null) {
        break missingId;
      }

      id = R.id.viewPager;
      CustomViewPager viewPager = ViewBindings.findChildViewById(rootView, id);
      if (viewPager == null) {
        break missingId;
      }

      return new ActRechargeBinding((CoordinatorLayout) rootView, bottomSheet, headlayout,
          historyRv, imgBack, imgDashLogo, imgEwallet, imgWallet, layActionbar,
          prepaidAndSemilarLayout, tabs, toolbarTxtEwalletbal, toolbarTxtWalletbal, tvHeading,
          viewPager);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}