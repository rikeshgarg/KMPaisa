// Generated by view binder compiler. Do not edit!
package com.codunite.rechargeapp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.codunite.rechargeapp.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActBrowsecropimageBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final RelativeLayout mylayout;

  @NonNull
  public final LayUploadimageBinding uploadimage;

  private ActBrowsecropimageBinding(@NonNull RelativeLayout rootView,
      @NonNull RelativeLayout mylayout, @NonNull LayUploadimageBinding uploadimage) {
    this.rootView = rootView;
    this.mylayout = mylayout;
    this.uploadimage = uploadimage;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActBrowsecropimageBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActBrowsecropimageBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.act_browsecropimage, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActBrowsecropimageBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      RelativeLayout mylayout = (RelativeLayout) rootView;

      id = R.id.uploadimage;
      View uploadimage = ViewBindings.findChildViewById(rootView, id);
      if (uploadimage == null) {
        break missingId;
      }
      LayUploadimageBinding binding_uploadimage = LayUploadimageBinding.bind(uploadimage);

      return new ActBrowsecropimageBinding((RelativeLayout) rootView, mylayout,
          binding_uploadimage);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
