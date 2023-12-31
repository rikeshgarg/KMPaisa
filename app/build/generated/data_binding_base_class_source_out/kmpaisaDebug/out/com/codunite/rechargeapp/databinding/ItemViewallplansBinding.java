// Generated by view binder compiler. Do not edit!
package com.codunite.rechargeapp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public final class ItemViewallplansBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final CardView cardview;

  @NonNull
  public final ImageView imgRuppeSymbol;

  @NonNull
  public final LinearLayout lytParent;

  @NonNull
  public final TextView offerPrice;

  @NonNull
  public final TextView offerdesc;

  @NonNull
  public final TextView offervalidity;

  private ItemViewallplansBinding(@NonNull LinearLayout rootView, @NonNull CardView cardview,
      @NonNull ImageView imgRuppeSymbol, @NonNull LinearLayout lytParent,
      @NonNull TextView offerPrice, @NonNull TextView offerdesc, @NonNull TextView offervalidity) {
    this.rootView = rootView;
    this.cardview = cardview;
    this.imgRuppeSymbol = imgRuppeSymbol;
    this.lytParent = lytParent;
    this.offerPrice = offerPrice;
    this.offerdesc = offerdesc;
    this.offervalidity = offervalidity;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ItemViewallplansBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ItemViewallplansBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.item_viewallplans, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ItemViewallplansBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.cardview;
      CardView cardview = ViewBindings.findChildViewById(rootView, id);
      if (cardview == null) {
        break missingId;
      }

      id = R.id.img_ruppe_symbol;
      ImageView imgRuppeSymbol = ViewBindings.findChildViewById(rootView, id);
      if (imgRuppeSymbol == null) {
        break missingId;
      }

      LinearLayout lytParent = (LinearLayout) rootView;

      id = R.id.offerPrice;
      TextView offerPrice = ViewBindings.findChildViewById(rootView, id);
      if (offerPrice == null) {
        break missingId;
      }

      id = R.id.offerdesc;
      TextView offerdesc = ViewBindings.findChildViewById(rootView, id);
      if (offerdesc == null) {
        break missingId;
      }

      id = R.id.offervalidity;
      TextView offervalidity = ViewBindings.findChildViewById(rootView, id);
      if (offervalidity == null) {
        break missingId;
      }

      return new ItemViewallplansBinding((LinearLayout) rootView, cardview, imgRuppeSymbol,
          lytParent, offerPrice, offerdesc, offervalidity);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
