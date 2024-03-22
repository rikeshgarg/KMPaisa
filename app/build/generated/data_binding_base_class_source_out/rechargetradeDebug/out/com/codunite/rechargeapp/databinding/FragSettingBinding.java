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
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.codunite.rechargeapp.R;
import com.mikhaellopez.circularimageview.CircularImageView;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragSettingBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final CardView cardview;

  @NonNull
  public final ImageView iconCallcompany;

  @NonNull
  public final ImageView iconCallsponser;

  @NonNull
  public final ImageView imgPlay;

  @NonNull
  public final ImageView imgPlay1;

  @NonNull
  public final RelativeLayout layCallcompany;

  @NonNull
  public final RelativeLayout layCallsponsor;

  @NonNull
  public final CircularImageView menuheaderDp;

  @NonNull
  public final TextView menuheaderMemberid;

  @NonNull
  public final TextView menuheaderName;

  @NonNull
  public final ConstraintLayout mylayout;

  @NonNull
  public final NestedScrollView nestedScrollView;

  @NonNull
  public final RecyclerView rvSettingMenu;

  private FragSettingBinding(@NonNull ConstraintLayout rootView, @NonNull CardView cardview,
      @NonNull ImageView iconCallcompany, @NonNull ImageView iconCallsponser,
      @NonNull ImageView imgPlay, @NonNull ImageView imgPlay1,
      @NonNull RelativeLayout layCallcompany, @NonNull RelativeLayout layCallsponsor,
      @NonNull CircularImageView menuheaderDp, @NonNull TextView menuheaderMemberid,
      @NonNull TextView menuheaderName, @NonNull ConstraintLayout mylayout,
      @NonNull NestedScrollView nestedScrollView, @NonNull RecyclerView rvSettingMenu) {
    this.rootView = rootView;
    this.cardview = cardview;
    this.iconCallcompany = iconCallcompany;
    this.iconCallsponser = iconCallsponser;
    this.imgPlay = imgPlay;
    this.imgPlay1 = imgPlay1;
    this.layCallcompany = layCallcompany;
    this.layCallsponsor = layCallsponsor;
    this.menuheaderDp = menuheaderDp;
    this.menuheaderMemberid = menuheaderMemberid;
    this.menuheaderName = menuheaderName;
    this.mylayout = mylayout;
    this.nestedScrollView = nestedScrollView;
    this.rvSettingMenu = rvSettingMenu;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragSettingBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragSettingBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.frag_setting, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragSettingBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.cardview;
      CardView cardview = ViewBindings.findChildViewById(rootView, id);
      if (cardview == null) {
        break missingId;
      }

      id = R.id.icon_callcompany;
      ImageView iconCallcompany = ViewBindings.findChildViewById(rootView, id);
      if (iconCallcompany == null) {
        break missingId;
      }

      id = R.id.icon_callsponser;
      ImageView iconCallsponser = ViewBindings.findChildViewById(rootView, id);
      if (iconCallsponser == null) {
        break missingId;
      }

      id = R.id.img_play;
      ImageView imgPlay = ViewBindings.findChildViewById(rootView, id);
      if (imgPlay == null) {
        break missingId;
      }

      id = R.id.img_play1;
      ImageView imgPlay1 = ViewBindings.findChildViewById(rootView, id);
      if (imgPlay1 == null) {
        break missingId;
      }

      id = R.id.lay_callcompany;
      RelativeLayout layCallcompany = ViewBindings.findChildViewById(rootView, id);
      if (layCallcompany == null) {
        break missingId;
      }

      id = R.id.lay_callsponsor;
      RelativeLayout layCallsponsor = ViewBindings.findChildViewById(rootView, id);
      if (layCallsponsor == null) {
        break missingId;
      }

      id = R.id.menuheader_dp;
      CircularImageView menuheaderDp = ViewBindings.findChildViewById(rootView, id);
      if (menuheaderDp == null) {
        break missingId;
      }

      id = R.id.menuheader_memberid;
      TextView menuheaderMemberid = ViewBindings.findChildViewById(rootView, id);
      if (menuheaderMemberid == null) {
        break missingId;
      }

      id = R.id.menuheader_name;
      TextView menuheaderName = ViewBindings.findChildViewById(rootView, id);
      if (menuheaderName == null) {
        break missingId;
      }

      ConstraintLayout mylayout = (ConstraintLayout) rootView;

      id = R.id.nested_scroll_view;
      NestedScrollView nestedScrollView = ViewBindings.findChildViewById(rootView, id);
      if (nestedScrollView == null) {
        break missingId;
      }

      id = R.id.rv_setting_menu;
      RecyclerView rvSettingMenu = ViewBindings.findChildViewById(rootView, id);
      if (rvSettingMenu == null) {
        break missingId;
      }

      return new FragSettingBinding((ConstraintLayout) rootView, cardview, iconCallcompany,
          iconCallsponser, imgPlay, imgPlay1, layCallcompany, layCallsponsor, menuheaderDp,
          menuheaderMemberid, menuheaderName, mylayout, nestedScrollView, rvSettingMenu);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}