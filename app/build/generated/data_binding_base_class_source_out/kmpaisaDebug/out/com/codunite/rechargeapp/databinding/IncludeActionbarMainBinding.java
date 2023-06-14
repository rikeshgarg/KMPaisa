// Generated by view binder compiler. Do not edit!
package com.codunite.rechargeapp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.codunite.rechargeapp.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class IncludeActionbarMainBinding implements ViewBinding {
  @NonNull
  private final FrameLayout rootView;

  @NonNull
  public final ImageView imgClose;

  @NonNull
  public final LinearLayout imgDashLogo;

  @NonNull
  public final ImageView imgMenu;

  @NonNull
  public final LinearLayout imgScancode;

  @NonNull
  public final LinearLayout layActions;

  @NonNull
  public final RelativeLayout layTop;

  @NonNull
  public final LinearLayout llMenu;

  private IncludeActionbarMainBinding(@NonNull FrameLayout rootView, @NonNull ImageView imgClose,
      @NonNull LinearLayout imgDashLogo, @NonNull ImageView imgMenu,
      @NonNull LinearLayout imgScancode, @NonNull LinearLayout layActions,
      @NonNull RelativeLayout layTop, @NonNull LinearLayout llMenu) {
    this.rootView = rootView;
    this.imgClose = imgClose;
    this.imgDashLogo = imgDashLogo;
    this.imgMenu = imgMenu;
    this.imgScancode = imgScancode;
    this.layActions = layActions;
    this.layTop = layTop;
    this.llMenu = llMenu;
  }

  @Override
  @NonNull
  public FrameLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static IncludeActionbarMainBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static IncludeActionbarMainBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.include_actionbar_main, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static IncludeActionbarMainBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.img_close;
      ImageView imgClose = ViewBindings.findChildViewById(rootView, id);
      if (imgClose == null) {
        break missingId;
      }

      id = R.id.img_dash_logo;
      LinearLayout imgDashLogo = ViewBindings.findChildViewById(rootView, id);
      if (imgDashLogo == null) {
        break missingId;
      }

      id = R.id.img_menu;
      ImageView imgMenu = ViewBindings.findChildViewById(rootView, id);
      if (imgMenu == null) {
        break missingId;
      }

      id = R.id.img_scancode;
      LinearLayout imgScancode = ViewBindings.findChildViewById(rootView, id);
      if (imgScancode == null) {
        break missingId;
      }

      id = R.id.lay_actions;
      LinearLayout layActions = ViewBindings.findChildViewById(rootView, id);
      if (layActions == null) {
        break missingId;
      }

      id = R.id.lay_top;
      RelativeLayout layTop = ViewBindings.findChildViewById(rootView, id);
      if (layTop == null) {
        break missingId;
      }

      id = R.id.ll_menu;
      LinearLayout llMenu = ViewBindings.findChildViewById(rootView, id);
      if (llMenu == null) {
        break missingId;
      }

      return new IncludeActionbarMainBinding((FrameLayout) rootView, imgClose, imgDashLogo, imgMenu,
          imgScancode, layActions, layTop, llMenu);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
