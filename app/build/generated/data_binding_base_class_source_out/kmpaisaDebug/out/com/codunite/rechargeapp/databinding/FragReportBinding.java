// Generated by view binder compiler. Do not edit!
package com.codunite.rechargeapp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.codunite.rechargeapp.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;
import me.grantland.widget.AutofitTextView;

public final class FragReportBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final CardView cardAddwallet;

  @NonNull
  public final LinearLayout mylayout;

  @NonNull
  public final NestedScrollView nestedScrollView;

  @NonNull
  public final RecyclerView rvDashIncome;

  @NonNull
  public final RecyclerView rvDashReports;

  @NonNull
  public final TextView todayBalTxt;

  @NonNull
  public final TextView totalBalTxt;

  @NonNull
  public final AutofitTextView totalFailed;

  @NonNull
  public final AutofitTextView totalPending;

  @NonNull
  public final AutofitTextView totalSuccess;

  private FragReportBinding(@NonNull LinearLayout rootView, @NonNull CardView cardAddwallet,
      @NonNull LinearLayout mylayout, @NonNull NestedScrollView nestedScrollView,
      @NonNull RecyclerView rvDashIncome, @NonNull RecyclerView rvDashReports,
      @NonNull TextView todayBalTxt, @NonNull TextView totalBalTxt,
      @NonNull AutofitTextView totalFailed, @NonNull AutofitTextView totalPending,
      @NonNull AutofitTextView totalSuccess) {
    this.rootView = rootView;
    this.cardAddwallet = cardAddwallet;
    this.mylayout = mylayout;
    this.nestedScrollView = nestedScrollView;
    this.rvDashIncome = rvDashIncome;
    this.rvDashReports = rvDashReports;
    this.todayBalTxt = todayBalTxt;
    this.totalBalTxt = totalBalTxt;
    this.totalFailed = totalFailed;
    this.totalPending = totalPending;
    this.totalSuccess = totalSuccess;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragReportBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragReportBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.frag_report, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragReportBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.card_addwallet;
      CardView cardAddwallet = ViewBindings.findChildViewById(rootView, id);
      if (cardAddwallet == null) {
        break missingId;
      }

      LinearLayout mylayout = (LinearLayout) rootView;

      id = R.id.nested_scroll_view;
      NestedScrollView nestedScrollView = ViewBindings.findChildViewById(rootView, id);
      if (nestedScrollView == null) {
        break missingId;
      }

      id = R.id.rv_dash_income;
      RecyclerView rvDashIncome = ViewBindings.findChildViewById(rootView, id);
      if (rvDashIncome == null) {
        break missingId;
      }

      id = R.id.rv_dash_reports;
      RecyclerView rvDashReports = ViewBindings.findChildViewById(rootView, id);
      if (rvDashReports == null) {
        break missingId;
      }

      id = R.id.today_bal_txt;
      TextView todayBalTxt = ViewBindings.findChildViewById(rootView, id);
      if (todayBalTxt == null) {
        break missingId;
      }

      id = R.id.total_bal_txt;
      TextView totalBalTxt = ViewBindings.findChildViewById(rootView, id);
      if (totalBalTxt == null) {
        break missingId;
      }

      id = R.id.total_failed;
      AutofitTextView totalFailed = ViewBindings.findChildViewById(rootView, id);
      if (totalFailed == null) {
        break missingId;
      }

      id = R.id.total_pending;
      AutofitTextView totalPending = ViewBindings.findChildViewById(rootView, id);
      if (totalPending == null) {
        break missingId;
      }

      id = R.id.total_success;
      AutofitTextView totalSuccess = ViewBindings.findChildViewById(rootView, id);
      if (totalSuccess == null) {
        break missingId;
      }

      return new FragReportBinding((LinearLayout) rootView, cardAddwallet, mylayout,
          nestedScrollView, rvDashIncome, rvDashReports, todayBalTxt, totalBalTxt, totalFailed,
          totalPending, totalSuccess);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
