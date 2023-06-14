// Generated by view binder compiler. Do not edit!
package com.codunite.rechargeapp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.codunite.rechargeapp.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActPrivacypolicyBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final ConstraintLayout headlayout;

  @NonNull
  public final IncludeActionbarBinding layActionbar;

  @NonNull
  public final RelativeLayout linearLayout;

  @NonNull
  public final TextView txtPrivacycontent;

  private ActPrivacypolicyBinding(@NonNull ConstraintLayout rootView,
      @NonNull ConstraintLayout headlayout, @NonNull IncludeActionbarBinding layActionbar,
      @NonNull RelativeLayout linearLayout, @NonNull TextView txtPrivacycontent) {
    this.rootView = rootView;
    this.headlayout = headlayout;
    this.layActionbar = layActionbar;
    this.linearLayout = linearLayout;
    this.txtPrivacycontent = txtPrivacycontent;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActPrivacypolicyBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActPrivacypolicyBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.act_privacypolicy, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActPrivacypolicyBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      ConstraintLayout headlayout = (ConstraintLayout) rootView;

      id = R.id.lay_actionbar;
      View layActionbar = ViewBindings.findChildViewById(rootView, id);
      if (layActionbar == null) {
        break missingId;
      }
      IncludeActionbarBinding binding_layActionbar = IncludeActionbarBinding.bind(layActionbar);

      id = R.id.linearLayout;
      RelativeLayout linearLayout = ViewBindings.findChildViewById(rootView, id);
      if (linearLayout == null) {
        break missingId;
      }

      id = R.id.txt_privacycontent;
      TextView txtPrivacycontent = ViewBindings.findChildViewById(rootView, id);
      if (txtPrivacycontent == null) {
        break missingId;
      }

      return new ActPrivacypolicyBinding((ConstraintLayout) rootView, headlayout,
          binding_layActionbar, linearLayout, txtPrivacycontent);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
