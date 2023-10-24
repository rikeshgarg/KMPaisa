// Generated by view binder compiler. Do not edit!
package com.codunite.rechargeapp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
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

public final class ActContactBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final ImageView appImg;

  @NonNull
  public final Button btnSubmitcontact;

  @NonNull
  public final EditText edtEmail;

  @NonNull
  public final EditText edtFirstname;

  @NonNull
  public final EditText edtMessage;

  @NonNull
  public final EditText edtMobilenumber;

  @NonNull
  public final ConstraintLayout headlayout;

  @NonNull
  public final IncludeActionbarBinding layActionbar;

  @NonNull
  public final LinearLayout linearLayout;

  @NonNull
  public final ScrollView scrollView2;

  @NonNull
  public final TextView txtAddress;

  @NonNull
  public final TextView txtCustCin;

  @NonNull
  public final TextView txtCustEmail;

  @NonNull
  public final TextView txtCustGst;

  @NonNull
  public final TextView txtCustPhoneno;

  private ActContactBinding(@NonNull ConstraintLayout rootView, @NonNull ImageView appImg,
      @NonNull Button btnSubmitcontact, @NonNull EditText edtEmail, @NonNull EditText edtFirstname,
      @NonNull EditText edtMessage, @NonNull EditText edtMobilenumber,
      @NonNull ConstraintLayout headlayout, @NonNull IncludeActionbarBinding layActionbar,
      @NonNull LinearLayout linearLayout, @NonNull ScrollView scrollView2,
      @NonNull TextView txtAddress, @NonNull TextView txtCustCin, @NonNull TextView txtCustEmail,
      @NonNull TextView txtCustGst, @NonNull TextView txtCustPhoneno) {
    this.rootView = rootView;
    this.appImg = appImg;
    this.btnSubmitcontact = btnSubmitcontact;
    this.edtEmail = edtEmail;
    this.edtFirstname = edtFirstname;
    this.edtMessage = edtMessage;
    this.edtMobilenumber = edtMobilenumber;
    this.headlayout = headlayout;
    this.layActionbar = layActionbar;
    this.linearLayout = linearLayout;
    this.scrollView2 = scrollView2;
    this.txtAddress = txtAddress;
    this.txtCustCin = txtCustCin;
    this.txtCustEmail = txtCustEmail;
    this.txtCustGst = txtCustGst;
    this.txtCustPhoneno = txtCustPhoneno;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActContactBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActContactBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.act_contact, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActContactBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.app_img;
      ImageView appImg = ViewBindings.findChildViewById(rootView, id);
      if (appImg == null) {
        break missingId;
      }

      id = R.id.btn_submitcontact;
      Button btnSubmitcontact = ViewBindings.findChildViewById(rootView, id);
      if (btnSubmitcontact == null) {
        break missingId;
      }

      id = R.id.edt_email;
      EditText edtEmail = ViewBindings.findChildViewById(rootView, id);
      if (edtEmail == null) {
        break missingId;
      }

      id = R.id.edt_firstname;
      EditText edtFirstname = ViewBindings.findChildViewById(rootView, id);
      if (edtFirstname == null) {
        break missingId;
      }

      id = R.id.edt_message;
      EditText edtMessage = ViewBindings.findChildViewById(rootView, id);
      if (edtMessage == null) {
        break missingId;
      }

      id = R.id.edt_mobilenumber;
      EditText edtMobilenumber = ViewBindings.findChildViewById(rootView, id);
      if (edtMobilenumber == null) {
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

      id = R.id.scrollView2;
      ScrollView scrollView2 = ViewBindings.findChildViewById(rootView, id);
      if (scrollView2 == null) {
        break missingId;
      }

      id = R.id.txt_address;
      TextView txtAddress = ViewBindings.findChildViewById(rootView, id);
      if (txtAddress == null) {
        break missingId;
      }

      id = R.id.txt_cust_cin;
      TextView txtCustCin = ViewBindings.findChildViewById(rootView, id);
      if (txtCustCin == null) {
        break missingId;
      }

      id = R.id.txt_cust_email;
      TextView txtCustEmail = ViewBindings.findChildViewById(rootView, id);
      if (txtCustEmail == null) {
        break missingId;
      }

      id = R.id.txt_cust_gst;
      TextView txtCustGst = ViewBindings.findChildViewById(rootView, id);
      if (txtCustGst == null) {
        break missingId;
      }

      id = R.id.txt_cust_phoneno;
      TextView txtCustPhoneno = ViewBindings.findChildViewById(rootView, id);
      if (txtCustPhoneno == null) {
        break missingId;
      }

      return new ActContactBinding((ConstraintLayout) rootView, appImg, btnSubmitcontact, edtEmail,
          edtFirstname, edtMessage, edtMobilenumber, headlayout, binding_layActionbar, linearLayout,
          scrollView2, txtAddress, txtCustCin, txtCustEmail, txtCustGst, txtCustPhoneno);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}