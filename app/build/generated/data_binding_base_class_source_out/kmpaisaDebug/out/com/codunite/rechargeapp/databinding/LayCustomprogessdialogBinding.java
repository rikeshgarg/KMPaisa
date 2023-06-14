// Generated by view binder compiler. Do not edit!
package com.codunite.rechargeapp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.codunite.rechargeapp.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class LayCustomprogessdialogBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final TextView loaderShowtext;

  @NonNull
  public final ProgressBar progressbarLoader;

  private LayCustomprogessdialogBinding(@NonNull RelativeLayout rootView,
      @NonNull TextView loaderShowtext, @NonNull ProgressBar progressbarLoader) {
    this.rootView = rootView;
    this.loaderShowtext = loaderShowtext;
    this.progressbarLoader = progressbarLoader;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static LayCustomprogessdialogBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static LayCustomprogessdialogBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.lay_customprogessdialog, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static LayCustomprogessdialogBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.loader_showtext;
      TextView loaderShowtext = ViewBindings.findChildViewById(rootView, id);
      if (loaderShowtext == null) {
        break missingId;
      }

      id = R.id.progressbar_loader;
      ProgressBar progressbarLoader = ViewBindings.findChildViewById(rootView, id);
      if (progressbarLoader == null) {
        break missingId;
      }

      return new LayCustomprogessdialogBinding((RelativeLayout) rootView, loaderShowtext,
          progressbarLoader);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
