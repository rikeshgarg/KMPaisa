// Generated by view binder compiler. Do not edit!
package com.codunite.rechargeapp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import androidx.viewpager2.widget.ViewPager2;
import com.codunite.rechargeapp.R;
import com.denzcoskun.imageslider.ImageSlider;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;
import me.grantland.widget.AutofitTextView;

public final class FragPersonprofileBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final CardView cardSlider;

  @NonNull
  public final CardView cvRecharge1;

  @NonNull
  public final CardView cvRecharge2;

  @NonNull
  public final CardView cvRecharge3;

  @NonNull
  public final ImageSlider imageSlider;

  @NonNull
  public final ImageView imgdrawable1;

  @NonNull
  public final ImageView imgdrawable2;

  @NonNull
  public final ImageView imgdrawable3;

  @NonNull
  public final LinearLayout mylayout;

  @NonNull
  public final NestedScrollView nestedScrollView;

  @NonNull
  public final RecyclerView rvDashGeneology;

  @NonNull
  public final RecyclerView rvDashboard;

  @NonNull
  public final RecyclerView rvDashboardWallet;

  @NonNull
  public final AutofitTextView totalFailed;

  @NonNull
  public final AutofitTextView totalFailed1;

  @NonNull
  public final AutofitTextView totalPending;

  @NonNull
  public final AutofitTextView totalPending1;

  @NonNull
  public final AutofitTextView totalSuccess;

  @NonNull
  public final AutofitTextView totalSuccess1;

  @NonNull
  public final ViewPager2 viewPagerImageSlider;

  private FragPersonprofileBinding(@NonNull LinearLayout rootView, @NonNull CardView cardSlider,
      @NonNull CardView cvRecharge1, @NonNull CardView cvRecharge2, @NonNull CardView cvRecharge3,
      @NonNull ImageSlider imageSlider, @NonNull ImageView imgdrawable1,
      @NonNull ImageView imgdrawable2, @NonNull ImageView imgdrawable3,
      @NonNull LinearLayout mylayout, @NonNull NestedScrollView nestedScrollView,
      @NonNull RecyclerView rvDashGeneology, @NonNull RecyclerView rvDashboard,
      @NonNull RecyclerView rvDashboardWallet, @NonNull AutofitTextView totalFailed,
      @NonNull AutofitTextView totalFailed1, @NonNull AutofitTextView totalPending,
      @NonNull AutofitTextView totalPending1, @NonNull AutofitTextView totalSuccess,
      @NonNull AutofitTextView totalSuccess1, @NonNull ViewPager2 viewPagerImageSlider) {
    this.rootView = rootView;
    this.cardSlider = cardSlider;
    this.cvRecharge1 = cvRecharge1;
    this.cvRecharge2 = cvRecharge2;
    this.cvRecharge3 = cvRecharge3;
    this.imageSlider = imageSlider;
    this.imgdrawable1 = imgdrawable1;
    this.imgdrawable2 = imgdrawable2;
    this.imgdrawable3 = imgdrawable3;
    this.mylayout = mylayout;
    this.nestedScrollView = nestedScrollView;
    this.rvDashGeneology = rvDashGeneology;
    this.rvDashboard = rvDashboard;
    this.rvDashboardWallet = rvDashboardWallet;
    this.totalFailed = totalFailed;
    this.totalFailed1 = totalFailed1;
    this.totalPending = totalPending;
    this.totalPending1 = totalPending1;
    this.totalSuccess = totalSuccess;
    this.totalSuccess1 = totalSuccess1;
    this.viewPagerImageSlider = viewPagerImageSlider;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragPersonprofileBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragPersonprofileBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.frag_personprofile, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragPersonprofileBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.card_slider;
      CardView cardSlider = ViewBindings.findChildViewById(rootView, id);
      if (cardSlider == null) {
        break missingId;
      }

      id = R.id.cv_recharge1;
      CardView cvRecharge1 = ViewBindings.findChildViewById(rootView, id);
      if (cvRecharge1 == null) {
        break missingId;
      }

      id = R.id.cv_recharge2;
      CardView cvRecharge2 = ViewBindings.findChildViewById(rootView, id);
      if (cvRecharge2 == null) {
        break missingId;
      }

      id = R.id.cv_recharge3;
      CardView cvRecharge3 = ViewBindings.findChildViewById(rootView, id);
      if (cvRecharge3 == null) {
        break missingId;
      }

      id = R.id.image_slider;
      ImageSlider imageSlider = ViewBindings.findChildViewById(rootView, id);
      if (imageSlider == null) {
        break missingId;
      }

      id = R.id.imgdrawable1;
      ImageView imgdrawable1 = ViewBindings.findChildViewById(rootView, id);
      if (imgdrawable1 == null) {
        break missingId;
      }

      id = R.id.imgdrawable2;
      ImageView imgdrawable2 = ViewBindings.findChildViewById(rootView, id);
      if (imgdrawable2 == null) {
        break missingId;
      }

      id = R.id.imgdrawable3;
      ImageView imgdrawable3 = ViewBindings.findChildViewById(rootView, id);
      if (imgdrawable3 == null) {
        break missingId;
      }

      LinearLayout mylayout = (LinearLayout) rootView;

      id = R.id.nested_scroll_view;
      NestedScrollView nestedScrollView = ViewBindings.findChildViewById(rootView, id);
      if (nestedScrollView == null) {
        break missingId;
      }

      id = R.id.rv_dash_geneology;
      RecyclerView rvDashGeneology = ViewBindings.findChildViewById(rootView, id);
      if (rvDashGeneology == null) {
        break missingId;
      }

      id = R.id.rv_dashboard;
      RecyclerView rvDashboard = ViewBindings.findChildViewById(rootView, id);
      if (rvDashboard == null) {
        break missingId;
      }

      id = R.id.rv_dashboard_wallet;
      RecyclerView rvDashboardWallet = ViewBindings.findChildViewById(rootView, id);
      if (rvDashboardWallet == null) {
        break missingId;
      }

      id = R.id.total_failed;
      AutofitTextView totalFailed = ViewBindings.findChildViewById(rootView, id);
      if (totalFailed == null) {
        break missingId;
      }

      id = R.id.total_failed1;
      AutofitTextView totalFailed1 = ViewBindings.findChildViewById(rootView, id);
      if (totalFailed1 == null) {
        break missingId;
      }

      id = R.id.total_pending;
      AutofitTextView totalPending = ViewBindings.findChildViewById(rootView, id);
      if (totalPending == null) {
        break missingId;
      }

      id = R.id.total_pending1;
      AutofitTextView totalPending1 = ViewBindings.findChildViewById(rootView, id);
      if (totalPending1 == null) {
        break missingId;
      }

      id = R.id.total_success;
      AutofitTextView totalSuccess = ViewBindings.findChildViewById(rootView, id);
      if (totalSuccess == null) {
        break missingId;
      }

      id = R.id.total_success1;
      AutofitTextView totalSuccess1 = ViewBindings.findChildViewById(rootView, id);
      if (totalSuccess1 == null) {
        break missingId;
      }

      id = R.id.viewPagerImageSlider;
      ViewPager2 viewPagerImageSlider = ViewBindings.findChildViewById(rootView, id);
      if (viewPagerImageSlider == null) {
        break missingId;
      }

      return new FragPersonprofileBinding((LinearLayout) rootView, cardSlider, cvRecharge1,
          cvRecharge2, cvRecharge3, imageSlider, imgdrawable1, imgdrawable2, imgdrawable3, mylayout,
          nestedScrollView, rvDashGeneology, rvDashboard, rvDashboardWallet, totalFailed,
          totalFailed1, totalPending, totalPending1, totalSuccess, totalSuccess1,
          viewPagerImageSlider);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
