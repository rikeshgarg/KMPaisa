// Generated by view binder compiler. Do not edit!
package com.codunite.rechargeapp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.codunite.rechargeapp.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragBottomsheetAllplansBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final ImageView btClosePlans;

  @NonNull
  public final RelativeLayout headlayoutPlans;

  @NonNull
  public final ImageView imgDrop1;

  @NonNull
  public final ImageView imgDrop2;

  @NonNull
  public final RelativeLayout layCircle;

  @NonNull
  public final RelativeLayout layOperator;

  @NonNull
  public final RecyclerView listViewBtmSheetPlans;

  @NonNull
  public final RecyclerView lstTabs;

  @NonNull
  public final Spinner spinnerCirclelist;

  @NonNull
  public final Spinner spinnerOperatorlist;

  private FragBottomsheetAllplansBinding(@NonNull RelativeLayout rootView,
      @NonNull ImageView btClosePlans, @NonNull RelativeLayout headlayoutPlans,
      @NonNull ImageView imgDrop1, @NonNull ImageView imgDrop2, @NonNull RelativeLayout layCircle,
      @NonNull RelativeLayout layOperator, @NonNull RecyclerView listViewBtmSheetPlans,
      @NonNull RecyclerView lstTabs, @NonNull Spinner spinnerCirclelist,
      @NonNull Spinner spinnerOperatorlist) {
    this.rootView = rootView;
    this.btClosePlans = btClosePlans;
    this.headlayoutPlans = headlayoutPlans;
    this.imgDrop1 = imgDrop1;
    this.imgDrop2 = imgDrop2;
    this.layCircle = layCircle;
    this.layOperator = layOperator;
    this.listViewBtmSheetPlans = listViewBtmSheetPlans;
    this.lstTabs = lstTabs;
    this.spinnerCirclelist = spinnerCirclelist;
    this.spinnerOperatorlist = spinnerOperatorlist;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragBottomsheetAllplansBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragBottomsheetAllplansBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.frag_bottomsheet_allplans, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragBottomsheetAllplansBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.bt_close_plans;
      ImageView btClosePlans = ViewBindings.findChildViewById(rootView, id);
      if (btClosePlans == null) {
        break missingId;
      }

      RelativeLayout headlayoutPlans = (RelativeLayout) rootView;

      id = R.id.img_drop_1;
      ImageView imgDrop1 = ViewBindings.findChildViewById(rootView, id);
      if (imgDrop1 == null) {
        break missingId;
      }

      id = R.id.img_drop_2;
      ImageView imgDrop2 = ViewBindings.findChildViewById(rootView, id);
      if (imgDrop2 == null) {
        break missingId;
      }

      id = R.id.lay_circle;
      RelativeLayout layCircle = ViewBindings.findChildViewById(rootView, id);
      if (layCircle == null) {
        break missingId;
      }

      id = R.id.lay_operator;
      RelativeLayout layOperator = ViewBindings.findChildViewById(rootView, id);
      if (layOperator == null) {
        break missingId;
      }

      id = R.id.listViewBtmSheet_plans;
      RecyclerView listViewBtmSheetPlans = ViewBindings.findChildViewById(rootView, id);
      if (listViewBtmSheetPlans == null) {
        break missingId;
      }

      id = R.id.lst_tabs;
      RecyclerView lstTabs = ViewBindings.findChildViewById(rootView, id);
      if (lstTabs == null) {
        break missingId;
      }

      id = R.id.spinner_circlelist;
      Spinner spinnerCirclelist = ViewBindings.findChildViewById(rootView, id);
      if (spinnerCirclelist == null) {
        break missingId;
      }

      id = R.id.spinner_operatorlist;
      Spinner spinnerOperatorlist = ViewBindings.findChildViewById(rootView, id);
      if (spinnerOperatorlist == null) {
        break missingId;
      }

      return new FragBottomsheetAllplansBinding((RelativeLayout) rootView, btClosePlans,
          headlayoutPlans, imgDrop1, imgDrop2, layCircle, layOperator, listViewBtmSheetPlans,
          lstTabs, spinnerCirclelist, spinnerOperatorlist);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
