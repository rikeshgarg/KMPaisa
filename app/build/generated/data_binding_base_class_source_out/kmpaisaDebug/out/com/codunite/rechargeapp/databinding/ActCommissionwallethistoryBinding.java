// Generated by view binder compiler. Do not edit!
package com.codunite.rechargeapp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;
import me.grantland.widget.AutofitTextView;

public final class ActCommissionwallethistoryBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final Button btnAddwallet;

  @NonNull
  public final CardView cardAddwallet;

  @NonNull
  public final CardView cardWallbal;

  @NonNull
  public final TextView datePickerFrom;

  @NonNull
  public final TextView datePickerTo;

  @NonNull
  public final AutofitTextView ewalletbal;

  @NonNull
  public final ImageView filterSearch;

  @NonNull
  public final ConstraintLayout headlayout;

  @NonNull
  public final IncludeActionbarBinding layActionbar;

  @NonNull
  public final LinearLayout layFilter;

  @NonNull
  public final NestedScrollView layNestedscroll;

  @NonNull
  public final LinearLayout linearLayout;

  @NonNull
  public final RecyclerView rvPagination;

  @NonNull
  public final AutofitTextView walletbal;

  @NonNull
  public final RecyclerView wallethistoryRv;

  private ActCommissionwallethistoryBinding(@NonNull ConstraintLayout rootView,
      @NonNull Button btnAddwallet, @NonNull CardView cardAddwallet, @NonNull CardView cardWallbal,
      @NonNull TextView datePickerFrom, @NonNull TextView datePickerTo,
      @NonNull AutofitTextView ewalletbal, @NonNull ImageView filterSearch,
      @NonNull ConstraintLayout headlayout, @NonNull IncludeActionbarBinding layActionbar,
      @NonNull LinearLayout layFilter, @NonNull NestedScrollView layNestedscroll,
      @NonNull LinearLayout linearLayout, @NonNull RecyclerView rvPagination,
      @NonNull AutofitTextView walletbal, @NonNull RecyclerView wallethistoryRv) {
    this.rootView = rootView;
    this.btnAddwallet = btnAddwallet;
    this.cardAddwallet = cardAddwallet;
    this.cardWallbal = cardWallbal;
    this.datePickerFrom = datePickerFrom;
    this.datePickerTo = datePickerTo;
    this.ewalletbal = ewalletbal;
    this.filterSearch = filterSearch;
    this.headlayout = headlayout;
    this.layActionbar = layActionbar;
    this.layFilter = layFilter;
    this.layNestedscroll = layNestedscroll;
    this.linearLayout = linearLayout;
    this.rvPagination = rvPagination;
    this.walletbal = walletbal;
    this.wallethistoryRv = wallethistoryRv;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActCommissionwallethistoryBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActCommissionwallethistoryBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.act_commissionwallethistory, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActCommissionwallethistoryBinding bind(@NonNull View rootView) {
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

      id = R.id.card_wallbal;
      CardView cardWallbal = ViewBindings.findChildViewById(rootView, id);
      if (cardWallbal == null) {
        break missingId;
      }

      id = R.id.datePicker_from;
      TextView datePickerFrom = ViewBindings.findChildViewById(rootView, id);
      if (datePickerFrom == null) {
        break missingId;
      }

      id = R.id.datePicker_to;
      TextView datePickerTo = ViewBindings.findChildViewById(rootView, id);
      if (datePickerTo == null) {
        break missingId;
      }

      id = R.id.ewalletbal;
      AutofitTextView ewalletbal = ViewBindings.findChildViewById(rootView, id);
      if (ewalletbal == null) {
        break missingId;
      }

      id = R.id.filter_search;
      ImageView filterSearch = ViewBindings.findChildViewById(rootView, id);
      if (filterSearch == null) {
        break missingId;
      }

      ConstraintLayout headlayout = (ConstraintLayout) rootView;

      id = R.id.lay_actionbar;
      View layActionbar = ViewBindings.findChildViewById(rootView, id);
      if (layActionbar == null) {
        break missingId;
      }
      IncludeActionbarBinding binding_layActionbar = IncludeActionbarBinding.bind(layActionbar);

      id = R.id.lay_filter;
      LinearLayout layFilter = ViewBindings.findChildViewById(rootView, id);
      if (layFilter == null) {
        break missingId;
      }

      id = R.id.lay_nestedscroll;
      NestedScrollView layNestedscroll = ViewBindings.findChildViewById(rootView, id);
      if (layNestedscroll == null) {
        break missingId;
      }

      id = R.id.linearLayout;
      LinearLayout linearLayout = ViewBindings.findChildViewById(rootView, id);
      if (linearLayout == null) {
        break missingId;
      }

      id = R.id.rv_pagination;
      RecyclerView rvPagination = ViewBindings.findChildViewById(rootView, id);
      if (rvPagination == null) {
        break missingId;
      }

      id = R.id.walletbal;
      AutofitTextView walletbal = ViewBindings.findChildViewById(rootView, id);
      if (walletbal == null) {
        break missingId;
      }

      id = R.id.wallethistory_rv;
      RecyclerView wallethistoryRv = ViewBindings.findChildViewById(rootView, id);
      if (wallethistoryRv == null) {
        break missingId;
      }

      return new ActCommissionwallethistoryBinding((ConstraintLayout) rootView, btnAddwallet,
          cardAddwallet, cardWallbal, datePickerFrom, datePickerTo, ewalletbal, filterSearch,
          headlayout, binding_layActionbar, layFilter, layNestedscroll, linearLayout, rvPagination,
          walletbal, wallethistoryRv);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
