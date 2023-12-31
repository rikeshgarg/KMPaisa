// Generated by view binder compiler. Do not edit!
package com.codunite.rechargeapp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.codunite.rechargeapp.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActVirtualAccountBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final AppCompatButton btnActive;

  @NonNull
  public final LinearLayout headlayout;

  @NonNull
  public final IncludeActionbarBinding layActionbar;

  @NonNull
  public final TextView tvActiveStatus;

  @NonNull
  public final TextView tvIfsc;

  @NonNull
  public final TextView tvVirtualac;

  private ActVirtualAccountBinding(@NonNull LinearLayout rootView,
      @NonNull AppCompatButton btnActive, @NonNull LinearLayout headlayout,
      @NonNull IncludeActionbarBinding layActionbar, @NonNull TextView tvActiveStatus,
      @NonNull TextView tvIfsc, @NonNull TextView tvVirtualac) {
    this.rootView = rootView;
    this.btnActive = btnActive;
    this.headlayout = headlayout;
    this.layActionbar = layActionbar;
    this.tvActiveStatus = tvActiveStatus;
    this.tvIfsc = tvIfsc;
    this.tvVirtualac = tvVirtualac;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActVirtualAccountBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActVirtualAccountBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.act_virtual_account, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActVirtualAccountBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btn_active;
      AppCompatButton btnActive = ViewBindings.findChildViewById(rootView, id);
      if (btnActive == null) {
        break missingId;
      }

      LinearLayout headlayout = (LinearLayout) rootView;

      id = R.id.lay_actionbar;
      View layActionbar = ViewBindings.findChildViewById(rootView, id);
      if (layActionbar == null) {
        break missingId;
      }
      IncludeActionbarBinding binding_layActionbar = IncludeActionbarBinding.bind(layActionbar);

      id = R.id.tv_active_status;
      TextView tvActiveStatus = ViewBindings.findChildViewById(rootView, id);
      if (tvActiveStatus == null) {
        break missingId;
      }

      id = R.id.tv_ifsc;
      TextView tvIfsc = ViewBindings.findChildViewById(rootView, id);
      if (tvIfsc == null) {
        break missingId;
      }

      id = R.id.tv_virtualac;
      TextView tvVirtualac = ViewBindings.findChildViewById(rootView, id);
      if (tvVirtualac == null) {
        break missingId;
      }

      return new ActVirtualAccountBinding((LinearLayout) rootView, btnActive, headlayout,
          binding_layActionbar, tvActiveStatus, tvIfsc, tvVirtualac);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
