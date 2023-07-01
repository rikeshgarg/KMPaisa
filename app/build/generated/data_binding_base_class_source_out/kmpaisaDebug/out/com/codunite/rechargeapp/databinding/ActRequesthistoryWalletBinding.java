// Generated by view binder compiler. Do not edit!
package com.codunite.rechargeapp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.codunite.rechargeapp.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActRequesthistoryWalletBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final Button addRequest;

  @NonNull
  public final CardView cardWallbal;

  @NonNull
  public final ConstraintLayout headlayout;

  @NonNull
  public final IncludeActionbarBinding layActionbar;

  @NonNull
  public final LinearLayout layBtn;

  @NonNull
  public final LinearLayout linearLayout;

  @NonNull
  public final TextView walletbal;

  @NonNull
  public final RecyclerView wallethistoryRv;

  private ActRequesthistoryWalletBinding(@NonNull ConstraintLayout rootView,
      @NonNull Button addRequest, @NonNull CardView cardWallbal,
      @NonNull ConstraintLayout headlayout, @NonNull IncludeActionbarBinding layActionbar,
      @NonNull LinearLayout layBtn, @NonNull LinearLayout linearLayout, @NonNull TextView walletbal,
      @NonNull RecyclerView wallethistoryRv) {
    this.rootView = rootView;
    this.addRequest = addRequest;
    this.cardWallbal = cardWallbal;
    this.headlayout = headlayout;
    this.layActionbar = layActionbar;
    this.layBtn = layBtn;
    this.linearLayout = linearLayout;
    this.walletbal = walletbal;
    this.wallethistoryRv = wallethistoryRv;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActRequesthistoryWalletBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActRequesthistoryWalletBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.act_requesthistory_wallet, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActRequesthistoryWalletBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.add_request;
      Button addRequest = ViewBindings.findChildViewById(rootView, id);
      if (addRequest == null) {
        break missingId;
      }

      id = R.id.card_wallbal;
      CardView cardWallbal = ViewBindings.findChildViewById(rootView, id);
      if (cardWallbal == null) {
        break missingId;
      }

      ConstraintLayout headlayout = (ConstraintLayout) rootView;

      id = R.id.lay_actionbar;
      View layActionbar = ViewBindings.findChildViewById(rootView, id);
      if (layActionbar == null) {
        break missingId;
      }
      IncludeActionbarBinding binding_layActionbar = IncludeActionbarBinding.bind(layActionbar);

      id = R.id.lay_btn;
      LinearLayout layBtn = ViewBindings.findChildViewById(rootView, id);
      if (layBtn == null) {
        break missingId;
      }

      id = R.id.linearLayout;
      LinearLayout linearLayout = ViewBindings.findChildViewById(rootView, id);
      if (linearLayout == null) {
        break missingId;
      }

      id = R.id.walletbal;
      TextView walletbal = ViewBindings.findChildViewById(rootView, id);
      if (walletbal == null) {
        break missingId;
      }

      id = R.id.wallethistory_rv;
      RecyclerView wallethistoryRv = ViewBindings.findChildViewById(rootView, id);
      if (wallethistoryRv == null) {
        break missingId;
      }

      return new ActRequesthistoryWalletBinding((ConstraintLayout) rootView, addRequest,
          cardWallbal, headlayout, binding_layActionbar, layBtn, linearLayout, walletbal,
          wallethistoryRv);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}