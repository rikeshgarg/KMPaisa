// Generated by view binder compiler. Do not edit!
package com.codunite.rechargeapp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.codunite.rechargeapp.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActUpdateSenderDetailsBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final Button btnSubmit;

  @NonNull
  public final EditText edAddress;

  @NonNull
  public final EditText edFirstName;

  @NonNull
  public final EditText edLastName;

  @NonNull
  public final EditText edPincode;

  @NonNull
  public final ConstraintLayout headlayout;

  @NonNull
  public final IncludeActionbarBinding layActionbar;

  @NonNull
  public final LinearLayout linearLayout;

  @NonNull
  public final LinearLayout linearLayoutReg;

  @NonNull
  public final NestedScrollView scrollView2;

  @NonNull
  public final Spinner spinnerMemberPosition;

  @NonNull
  public final TextView txtDob;

  @NonNull
  public final TextView txtLoginerror;

  private ActUpdateSenderDetailsBinding(@NonNull ConstraintLayout rootView,
      @NonNull Button btnSubmit, @NonNull EditText edAddress, @NonNull EditText edFirstName,
      @NonNull EditText edLastName, @NonNull EditText edPincode,
      @NonNull ConstraintLayout headlayout, @NonNull IncludeActionbarBinding layActionbar,
      @NonNull LinearLayout linearLayout, @NonNull LinearLayout linearLayoutReg,
      @NonNull NestedScrollView scrollView2, @NonNull Spinner spinnerMemberPosition,
      @NonNull TextView txtDob, @NonNull TextView txtLoginerror) {
    this.rootView = rootView;
    this.btnSubmit = btnSubmit;
    this.edAddress = edAddress;
    this.edFirstName = edFirstName;
    this.edLastName = edLastName;
    this.edPincode = edPincode;
    this.headlayout = headlayout;
    this.layActionbar = layActionbar;
    this.linearLayout = linearLayout;
    this.linearLayoutReg = linearLayoutReg;
    this.scrollView2 = scrollView2;
    this.spinnerMemberPosition = spinnerMemberPosition;
    this.txtDob = txtDob;
    this.txtLoginerror = txtLoginerror;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActUpdateSenderDetailsBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActUpdateSenderDetailsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.act_update_sender_details, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActUpdateSenderDetailsBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btnSubmit;
      Button btnSubmit = ViewBindings.findChildViewById(rootView, id);
      if (btnSubmit == null) {
        break missingId;
      }

      id = R.id.ed_address;
      EditText edAddress = ViewBindings.findChildViewById(rootView, id);
      if (edAddress == null) {
        break missingId;
      }

      id = R.id.ed_first_name;
      EditText edFirstName = ViewBindings.findChildViewById(rootView, id);
      if (edFirstName == null) {
        break missingId;
      }

      id = R.id.ed_last_name;
      EditText edLastName = ViewBindings.findChildViewById(rootView, id);
      if (edLastName == null) {
        break missingId;
      }

      id = R.id.ed_pincode;
      EditText edPincode = ViewBindings.findChildViewById(rootView, id);
      if (edPincode == null) {
        break missingId;
      }

      ConstraintLayout headlayout = (ConstraintLayout) rootView;

      id = R.id.lay_actionbar;
      View layActionbar = ViewBindings.findChildViewById(rootView, id);
      if (layActionbar == null) {
        break missingId;
      }
      IncludeActionbarBinding binding_layActionbar = IncludeActionbarBinding.bind(layActionbar);

      id = R.id.linearLayout;
      LinearLayout linearLayout = ViewBindings.findChildViewById(rootView, id);
      if (linearLayout == null) {
        break missingId;
      }

      id = R.id.linearLayoutReg;
      LinearLayout linearLayoutReg = ViewBindings.findChildViewById(rootView, id);
      if (linearLayoutReg == null) {
        break missingId;
      }

      id = R.id.scrollView2;
      NestedScrollView scrollView2 = ViewBindings.findChildViewById(rootView, id);
      if (scrollView2 == null) {
        break missingId;
      }

      id = R.id.spinner_member_position;
      Spinner spinnerMemberPosition = ViewBindings.findChildViewById(rootView, id);
      if (spinnerMemberPosition == null) {
        break missingId;
      }

      id = R.id.txtDob;
      TextView txtDob = ViewBindings.findChildViewById(rootView, id);
      if (txtDob == null) {
        break missingId;
      }

      id = R.id.txt_loginerror;
      TextView txtLoginerror = ViewBindings.findChildViewById(rootView, id);
      if (txtLoginerror == null) {
        break missingId;
      }

      return new ActUpdateSenderDetailsBinding((ConstraintLayout) rootView, btnSubmit, edAddress,
          edFirstName, edLastName, edPincode, headlayout, binding_layActionbar, linearLayout,
          linearLayoutReg, scrollView2, spinnerMemberPosition, txtDob, txtLoginerror);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}