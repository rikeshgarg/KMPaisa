// Generated by view binder compiler. Do not edit!
package com.codunite.rechargeapp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.codunite.rechargeapp.R;
import com.mikhaellopez.circularimageview.CircularImageView;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ItemContactBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final CircularImageView avatar;

  @NonNull
  public final ImageView ivSelected;

  @NonNull
  public final RelativeLayout layHeadChange;

  @NonNull
  public final RelativeLayout layProfileimg;

  @NonNull
  public final LinearLayout lytParent;

  @NonNull
  public final TextView name;

  @NonNull
  public final TextView number;

  private ItemContactBinding(@NonNull LinearLayout rootView, @NonNull CircularImageView avatar,
      @NonNull ImageView ivSelected, @NonNull RelativeLayout layHeadChange,
      @NonNull RelativeLayout layProfileimg, @NonNull LinearLayout lytParent,
      @NonNull TextView name, @NonNull TextView number) {
    this.rootView = rootView;
    this.avatar = avatar;
    this.ivSelected = ivSelected;
    this.layHeadChange = layHeadChange;
    this.layProfileimg = layProfileimg;
    this.lytParent = lytParent;
    this.name = name;
    this.number = number;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ItemContactBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ItemContactBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.item_contact, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ItemContactBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.avatar;
      CircularImageView avatar = ViewBindings.findChildViewById(rootView, id);
      if (avatar == null) {
        break missingId;
      }

      id = R.id.iv_selected;
      ImageView ivSelected = ViewBindings.findChildViewById(rootView, id);
      if (ivSelected == null) {
        break missingId;
      }

      id = R.id.lay_head_change;
      RelativeLayout layHeadChange = ViewBindings.findChildViewById(rootView, id);
      if (layHeadChange == null) {
        break missingId;
      }

      id = R.id.lay_profileimg;
      RelativeLayout layProfileimg = ViewBindings.findChildViewById(rootView, id);
      if (layProfileimg == null) {
        break missingId;
      }

      LinearLayout lytParent = (LinearLayout) rootView;

      id = R.id.name;
      TextView name = ViewBindings.findChildViewById(rootView, id);
      if (name == null) {
        break missingId;
      }

      id = R.id.number;
      TextView number = ViewBindings.findChildViewById(rootView, id);
      if (number == null) {
        break missingId;
      }

      return new ItemContactBinding((LinearLayout) rootView, avatar, ivSelected, layHeadChange,
          layProfileimg, lytParent, name, number);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
