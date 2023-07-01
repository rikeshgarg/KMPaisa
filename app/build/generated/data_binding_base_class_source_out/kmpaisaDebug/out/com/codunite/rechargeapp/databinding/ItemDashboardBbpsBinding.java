// Generated by view binder compiler. Do not edit!
package com.codunite.rechargeapp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.codunite.rechargeapp.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;
import me.grantland.widget.AutofitTextView;

public final class ItemDashboardBbpsBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final RelativeLayout cardview;

  @NonNull
  public final CardView cardviewItem;

  @NonNull
  public final ImageView imgdrawable;

  @NonNull
  public final AutofitTextView name;

  private ItemDashboardBbpsBinding(@NonNull RelativeLayout rootView,
      @NonNull RelativeLayout cardview, @NonNull CardView cardviewItem,
      @NonNull ImageView imgdrawable, @NonNull AutofitTextView name) {
    this.rootView = rootView;
    this.cardview = cardview;
    this.cardviewItem = cardviewItem;
    this.imgdrawable = imgdrawable;
    this.name = name;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ItemDashboardBbpsBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ItemDashboardBbpsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.item_dashboard_bbps, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ItemDashboardBbpsBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      RelativeLayout cardview = (RelativeLayout) rootView;

      id = R.id.cardview_item;
      CardView cardviewItem = ViewBindings.findChildViewById(rootView, id);
      if (cardviewItem == null) {
        break missingId;
      }

      id = R.id.imgdrawable;
      ImageView imgdrawable = ViewBindings.findChildViewById(rootView, id);
      if (imgdrawable == null) {
        break missingId;
      }

      id = R.id.name;
      AutofitTextView name = ViewBindings.findChildViewById(rootView, id);
      if (name == null) {
        break missingId;
      }

      return new ItemDashboardBbpsBinding((RelativeLayout) rootView, cardview, cardviewItem,
          imgdrawable, name);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
