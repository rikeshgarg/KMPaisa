// Generated by view binder compiler. Do not edit!
package com.codunite.rechargeapp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.codunite.rechargeapp.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class DialogHeaderTwobuttonBinding implements ViewBinding {
  @NonNull
  private final CardView rootView;

  @NonNull
  public final Button btConfirm;

  @NonNull
  public final Button btDecline;

  @NonNull
  public final TextView dialogDesc;

  @NonNull
  public final TextView dialogHead;

  @NonNull
  public final TextView dialogTitle;

  private DialogHeaderTwobuttonBinding(@NonNull CardView rootView, @NonNull Button btConfirm,
      @NonNull Button btDecline, @NonNull TextView dialogDesc, @NonNull TextView dialogHead,
      @NonNull TextView dialogTitle) {
    this.rootView = rootView;
    this.btConfirm = btConfirm;
    this.btDecline = btDecline;
    this.dialogDesc = dialogDesc;
    this.dialogHead = dialogHead;
    this.dialogTitle = dialogTitle;
  }

  @Override
  @NonNull
  public CardView getRoot() {
    return rootView;
  }

  @NonNull
  public static DialogHeaderTwobuttonBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static DialogHeaderTwobuttonBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.dialog_header_twobutton, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static DialogHeaderTwobuttonBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.bt_confirm;
      Button btConfirm = ViewBindings.findChildViewById(rootView, id);
      if (btConfirm == null) {
        break missingId;
      }

      id = R.id.bt_decline;
      Button btDecline = ViewBindings.findChildViewById(rootView, id);
      if (btDecline == null) {
        break missingId;
      }

      id = R.id.dialog_desc;
      TextView dialogDesc = ViewBindings.findChildViewById(rootView, id);
      if (dialogDesc == null) {
        break missingId;
      }

      id = R.id.dialog_head;
      TextView dialogHead = ViewBindings.findChildViewById(rootView, id);
      if (dialogHead == null) {
        break missingId;
      }

      id = R.id.dialog_title;
      TextView dialogTitle = ViewBindings.findChildViewById(rootView, id);
      if (dialogTitle == null) {
        break missingId;
      }

      return new DialogHeaderTwobuttonBinding((CardView) rootView, btConfirm, btDecline, dialogDesc,
          dialogHead, dialogTitle);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
