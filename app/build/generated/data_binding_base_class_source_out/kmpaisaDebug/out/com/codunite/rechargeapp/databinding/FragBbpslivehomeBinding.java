// Generated by view binder compiler. Do not edit!
package com.codunite.rechargeapp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.codunite.rechargeapp.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragBbpslivehomeBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final LinearLayout mylayout;

  @NonNull
  public final RecyclerView rvDashboard;

  private FragBbpslivehomeBinding(@NonNull LinearLayout rootView, @NonNull LinearLayout mylayout,
      @NonNull RecyclerView rvDashboard) {
    this.rootView = rootView;
    this.mylayout = mylayout;
    this.rvDashboard = rvDashboard;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragBbpslivehomeBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragBbpslivehomeBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.frag_bbpslivehome, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragBbpslivehomeBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      LinearLayout mylayout = (LinearLayout) rootView;

      id = R.id.rv_dashboard;
      RecyclerView rvDashboard = ViewBindings.findChildViewById(rootView, id);
      if (rvDashboard == null) {
        break missingId;
      }

      return new FragBbpslivehomeBinding((LinearLayout) rootView, mylayout, rvDashboard);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}