// Generated by view binder compiler. Do not edit!
package com.codunite.rechargeapp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

public final class ActKycBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final Button btSaveInput;

  @NonNull
  public final Button chooseAadharback;

  @NonNull
  public final Button chooseAadharfront;

  @NonNull
  public final Button choosePancard;

  @NonNull
  public final EditText edAccountNumber;

  @NonNull
  public final EditText edAccountholderName;

  @NonNull
  public final EditText edAdharNo;

  @NonNull
  public final EditText edBankIfsc;

  @NonNull
  public final EditText edBankName;

  @NonNull
  public final TextView edDob;

  @NonNull
  public final EditText edMobNumber;

  @NonNull
  public final ConstraintLayout headlayout;

  @NonNull
  public final ImageView imgBackaadharcard;

  @NonNull
  public final ImageView imgFrontaadharcard;

  @NonNull
  public final ImageView imgPancard;

  @NonNull
  public final LinearLayout layAadharback;

  @NonNull
  public final LinearLayout layAadharfront;

  @NonNull
  public final IncludeActionbarBinding layActionbar;

  @NonNull
  public final IncludeDemourlBinding layDemourl;

  @NonNull
  public final LinearLayout layPancard;

  @NonNull
  public final NestedScrollView nestedScrollView;

  @NonNull
  public final TextView nofileAadharfront;

  @NonNull
  public final Button removeAadharback;

  @NonNull
  public final Button removeAadharfront;

  @NonNull
  public final Button removePancard;

  @NonNull
  public final TextView txtKycStatus;

  private ActKycBinding(@NonNull ConstraintLayout rootView, @NonNull Button btSaveInput,
      @NonNull Button chooseAadharback, @NonNull Button chooseAadharfront,
      @NonNull Button choosePancard, @NonNull EditText edAccountNumber,
      @NonNull EditText edAccountholderName, @NonNull EditText edAdharNo,
      @NonNull EditText edBankIfsc, @NonNull EditText edBankName, @NonNull TextView edDob,
      @NonNull EditText edMobNumber, @NonNull ConstraintLayout headlayout,
      @NonNull ImageView imgBackaadharcard, @NonNull ImageView imgFrontaadharcard,
      @NonNull ImageView imgPancard, @NonNull LinearLayout layAadharback,
      @NonNull LinearLayout layAadharfront, @NonNull IncludeActionbarBinding layActionbar,
      @NonNull IncludeDemourlBinding layDemourl, @NonNull LinearLayout layPancard,
      @NonNull NestedScrollView nestedScrollView, @NonNull TextView nofileAadharfront,
      @NonNull Button removeAadharback, @NonNull Button removeAadharfront,
      @NonNull Button removePancard, @NonNull TextView txtKycStatus) {
    this.rootView = rootView;
    this.btSaveInput = btSaveInput;
    this.chooseAadharback = chooseAadharback;
    this.chooseAadharfront = chooseAadharfront;
    this.choosePancard = choosePancard;
    this.edAccountNumber = edAccountNumber;
    this.edAccountholderName = edAccountholderName;
    this.edAdharNo = edAdharNo;
    this.edBankIfsc = edBankIfsc;
    this.edBankName = edBankName;
    this.edDob = edDob;
    this.edMobNumber = edMobNumber;
    this.headlayout = headlayout;
    this.imgBackaadharcard = imgBackaadharcard;
    this.imgFrontaadharcard = imgFrontaadharcard;
    this.imgPancard = imgPancard;
    this.layAadharback = layAadharback;
    this.layAadharfront = layAadharfront;
    this.layActionbar = layActionbar;
    this.layDemourl = layDemourl;
    this.layPancard = layPancard;
    this.nestedScrollView = nestedScrollView;
    this.nofileAadharfront = nofileAadharfront;
    this.removeAadharback = removeAadharback;
    this.removeAadharfront = removeAadharfront;
    this.removePancard = removePancard;
    this.txtKycStatus = txtKycStatus;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActKycBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActKycBinding inflate(@NonNull LayoutInflater inflater, @Nullable ViewGroup parent,
      boolean attachToParent) {
    View root = inflater.inflate(R.layout.act_kyc, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActKycBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.bt_save_input;
      Button btSaveInput = ViewBindings.findChildViewById(rootView, id);
      if (btSaveInput == null) {
        break missingId;
      }

      id = R.id.choose_aadharback;
      Button chooseAadharback = ViewBindings.findChildViewById(rootView, id);
      if (chooseAadharback == null) {
        break missingId;
      }

      id = R.id.choose_aadharfront;
      Button chooseAadharfront = ViewBindings.findChildViewById(rootView, id);
      if (chooseAadharfront == null) {
        break missingId;
      }

      id = R.id.choose_pancard;
      Button choosePancard = ViewBindings.findChildViewById(rootView, id);
      if (choosePancard == null) {
        break missingId;
      }

      id = R.id.ed_account_number;
      EditText edAccountNumber = ViewBindings.findChildViewById(rootView, id);
      if (edAccountNumber == null) {
        break missingId;
      }

      id = R.id.ed_accountholder_name;
      EditText edAccountholderName = ViewBindings.findChildViewById(rootView, id);
      if (edAccountholderName == null) {
        break missingId;
      }

      id = R.id.ed_adhar_no;
      EditText edAdharNo = ViewBindings.findChildViewById(rootView, id);
      if (edAdharNo == null) {
        break missingId;
      }

      id = R.id.ed_bank_ifsc;
      EditText edBankIfsc = ViewBindings.findChildViewById(rootView, id);
      if (edBankIfsc == null) {
        break missingId;
      }

      id = R.id.ed_bank_name;
      EditText edBankName = ViewBindings.findChildViewById(rootView, id);
      if (edBankName == null) {
        break missingId;
      }

      id = R.id.ed_dob;
      TextView edDob = ViewBindings.findChildViewById(rootView, id);
      if (edDob == null) {
        break missingId;
      }

      id = R.id.ed_mob_number;
      EditText edMobNumber = ViewBindings.findChildViewById(rootView, id);
      if (edMobNumber == null) {
        break missingId;
      }

      ConstraintLayout headlayout = (ConstraintLayout) rootView;

      id = R.id.img_backaadharcard;
      ImageView imgBackaadharcard = ViewBindings.findChildViewById(rootView, id);
      if (imgBackaadharcard == null) {
        break missingId;
      }

      id = R.id.img_frontaadharcard;
      ImageView imgFrontaadharcard = ViewBindings.findChildViewById(rootView, id);
      if (imgFrontaadharcard == null) {
        break missingId;
      }

      id = R.id.img_pancard;
      ImageView imgPancard = ViewBindings.findChildViewById(rootView, id);
      if (imgPancard == null) {
        break missingId;
      }

      id = R.id.lay_aadharback;
      LinearLayout layAadharback = ViewBindings.findChildViewById(rootView, id);
      if (layAadharback == null) {
        break missingId;
      }

      id = R.id.lay_aadharfront;
      LinearLayout layAadharfront = ViewBindings.findChildViewById(rootView, id);
      if (layAadharfront == null) {
        break missingId;
      }

      id = R.id.lay_actionbar;
      View layActionbar = ViewBindings.findChildViewById(rootView, id);
      if (layActionbar == null) {
        break missingId;
      }
      IncludeActionbarBinding binding_layActionbar = IncludeActionbarBinding.bind(layActionbar);

      id = R.id.lay_demourl;
      View layDemourl = ViewBindings.findChildViewById(rootView, id);
      if (layDemourl == null) {
        break missingId;
      }
      IncludeDemourlBinding binding_layDemourl = IncludeDemourlBinding.bind(layDemourl);

      id = R.id.lay_pancard;
      LinearLayout layPancard = ViewBindings.findChildViewById(rootView, id);
      if (layPancard == null) {
        break missingId;
      }

      id = R.id.nested_scroll_view;
      NestedScrollView nestedScrollView = ViewBindings.findChildViewById(rootView, id);
      if (nestedScrollView == null) {
        break missingId;
      }

      id = R.id.nofile_aadharfront;
      TextView nofileAadharfront = ViewBindings.findChildViewById(rootView, id);
      if (nofileAadharfront == null) {
        break missingId;
      }

      id = R.id.remove_aadharback;
      Button removeAadharback = ViewBindings.findChildViewById(rootView, id);
      if (removeAadharback == null) {
        break missingId;
      }

      id = R.id.remove_aadharfront;
      Button removeAadharfront = ViewBindings.findChildViewById(rootView, id);
      if (removeAadharfront == null) {
        break missingId;
      }

      id = R.id.remove_pancard;
      Button removePancard = ViewBindings.findChildViewById(rootView, id);
      if (removePancard == null) {
        break missingId;
      }

      id = R.id.txt_kyc_status;
      TextView txtKycStatus = ViewBindings.findChildViewById(rootView, id);
      if (txtKycStatus == null) {
        break missingId;
      }

      return new ActKycBinding((ConstraintLayout) rootView, btSaveInput, chooseAadharback,
          chooseAadharfront, choosePancard, edAccountNumber, edAccountholderName, edAdharNo,
          edBankIfsc, edBankName, edDob, edMobNumber, headlayout, imgBackaadharcard,
          imgFrontaadharcard, imgPancard, layAadharback, layAadharfront, binding_layActionbar,
          binding_layDemourl, layPancard, nestedScrollView, nofileAadharfront, removeAadharback,
          removeAadharfront, removePancard, txtKycStatus);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
