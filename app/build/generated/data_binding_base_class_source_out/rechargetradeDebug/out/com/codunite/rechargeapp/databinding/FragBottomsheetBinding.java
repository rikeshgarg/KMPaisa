// Generated by view binder compiler. Do not edit!
package com.codunite.rechargeapp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.codunite.rechargeapp.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragBottomsheetBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final ImageView btClose;

  @NonNull
  public final RelativeLayout headlayoutOffers;

  @NonNull
  public final RecyclerView listViewBtmSheetOffers;

  private FragBottomsheetBinding(@NonNull RelativeLayout rootView, @NonNull ImageView btClose,
      @NonNull RelativeLayout headlayoutOffers, @NonNull RecyclerView listViewBtmSheetOffers) {
    this.rootView = rootView;
    this.btClose = btClose;
    this.headlayoutOffers = headlayoutOffers;
    this.listViewBtmSheetOffers = listViewBtmSheetOffers;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragBottomsheetBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragBottomsheetBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.frag_bottomsheet, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragBottomsheetBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.bt_close;
      ImageView btClose = ViewBindings.findChildViewById(rootView, id);
      if (btClose == null) {
        break missingId;
      }

      RelativeLayout headlayoutOffers = (RelativeLayout) rootView;

      id = R.id.listViewBtmSheet_offers;
      RecyclerView listViewBtmSheetOffers = ViewBindings.findChildViewById(rootView, id);
      if (listViewBtmSheetOffers == null) {
        break missingId;
      }

      return new FragBottomsheetBinding((RelativeLayout) rootView, btClose, headlayoutOffers,
          listViewBtmSheetOffers);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}