// Generated by view binder compiler. Do not edit!
package com.codunite.rechargeapp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

public final class ActTicketlistBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final Button btnAddwallet;

  @NonNull
  public final CardView cardAddwallet;

  @NonNull
  public final ConstraintLayout headlayout;

  @NonNull
  public final IncludeActionbarBinding layActionbar;

  @NonNull
  public final RecyclerView wallethistoryRv;

  private ActTicketlistBinding(@NonNull ConstraintLayout rootView, @NonNull Button btnAddwallet,
      @NonNull CardView cardAddwallet, @NonNull ConstraintLayout headlayout,
      @NonNull IncludeActionbarBinding layActionbar, @NonNull RecyclerView wallethistoryRv) {
    this.rootView = rootView;
    this.btnAddwallet = btnAddwallet;
    this.cardAddwallet = cardAddwallet;
    this.headlayout = headlayout;
    this.layActionbar = layActionbar;
    this.wallethistoryRv = wallethistoryRv;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActTicketlistBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActTicketlistBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.act_ticketlist, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActTicketlistBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btn_addwallet;
      Button btnAddwallet = ViewBindings.findChildViewById(rootView, id);
      if (btnAddwallet == null) {
        break missingId;
      }

      id = R.id.card_addwallet;
      CardView cardAddwallet = ViewBindings.findChildViewById(rootView, id);
      if (cardAddwallet == null) {
        break missingId;
      }

      ConstraintLayout headlayout = (ConstraintLayout) rootView;

      id = R.id.lay_actionbar;
      View layActionbar = ViewBindings.findChildViewById(rootView, id);
      if (layActionbar == null) {
        break missingId;
      }
      IncludeActionbarBinding binding_layActionbar = IncludeActionbarBinding.bind(layActionbar);

      id = R.id.wallethistory_rv;
      RecyclerView wallethistoryRv = ViewBindings.findChildViewById(rootView, id);
      if (wallethistoryRv == null) {
        break missingId;
      }

      return new ActTicketlistBinding((ConstraintLayout) rootView, btnAddwallet, cardAddwallet,
          headlayout, binding_layActionbar, wallethistoryRv);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}