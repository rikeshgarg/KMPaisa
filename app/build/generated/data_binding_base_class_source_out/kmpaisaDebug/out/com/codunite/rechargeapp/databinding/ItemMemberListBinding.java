// Generated by view binder compiler. Do not edit!
package com.codunite.rechargeapp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.codunite.rechargeapp.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ItemMemberListBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final LinearLayout llBg;

  @NonNull
  public final LinearLayout llImage;

  @NonNull
  public final LinearLayout lytParent;

  @NonNull
  public final TextView vmMemberid;

  @NonNull
  public final TextView vmName;

  @NonNull
  public final TextView vmStatus;

  @NonNull
  public final TextView vmWallbal;

  @NonNull
  public final TextView walletBal;

  private ItemMemberListBinding(@NonNull LinearLayout rootView, @NonNull LinearLayout llBg,
      @NonNull LinearLayout llImage, @NonNull LinearLayout lytParent, @NonNull TextView vmMemberid,
      @NonNull TextView vmName, @NonNull TextView vmStatus, @NonNull TextView vmWallbal,
      @NonNull TextView walletBal) {
    this.rootView = rootView;
    this.llBg = llBg;
    this.llImage = llImage;
    this.lytParent = lytParent;
    this.vmMemberid = vmMemberid;
    this.vmName = vmName;
    this.vmStatus = vmStatus;
    this.vmWallbal = vmWallbal;
    this.walletBal = walletBal;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ItemMemberListBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ItemMemberListBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.item_member_list, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ItemMemberListBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.ll_bg;
      LinearLayout llBg = ViewBindings.findChildViewById(rootView, id);
      if (llBg == null) {
        break missingId;
      }

      id = R.id.ll_image;
      LinearLayout llImage = ViewBindings.findChildViewById(rootView, id);
      if (llImage == null) {
        break missingId;
      }

      LinearLayout lytParent = (LinearLayout) rootView;

      id = R.id.vm_memberid;
      TextView vmMemberid = ViewBindings.findChildViewById(rootView, id);
      if (vmMemberid == null) {
        break missingId;
      }

      id = R.id.vm_name;
      TextView vmName = ViewBindings.findChildViewById(rootView, id);
      if (vmName == null) {
        break missingId;
      }

      id = R.id.vm_status;
      TextView vmStatus = ViewBindings.findChildViewById(rootView, id);
      if (vmStatus == null) {
        break missingId;
      }

      id = R.id.vm_wallbal;
      TextView vmWallbal = ViewBindings.findChildViewById(rootView, id);
      if (vmWallbal == null) {
        break missingId;
      }

      id = R.id.wallet_bal;
      TextView walletBal = ViewBindings.findChildViewById(rootView, id);
      if (walletBal == null) {
        break missingId;
      }

      return new ItemMemberListBinding((LinearLayout) rootView, llBg, llImage, lytParent,
          vmMemberid, vmName, vmStatus, vmWallbal, walletBal);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
