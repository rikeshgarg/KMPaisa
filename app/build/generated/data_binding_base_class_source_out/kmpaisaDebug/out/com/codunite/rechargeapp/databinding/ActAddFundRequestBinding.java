// Generated by view binder compiler. Do not edit!
package com.codunite.rechargeapp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.codunite.rechargeapp.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActAddFundRequestBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final AppCompatButton btSaveInput;

  @NonNull
  public final AppCompatButton btnTransactionPassword;

  @NonNull
  public final FrameLayout cardAccounteedetail;

  @NonNull
  public final AppCompatButton choosePicture;

  @NonNull
  public final EditText edAmount;

  @NonNull
  public final EditText edReferenceId;

  @NonNull
  public final EditText edRemark;

  @NonNull
  public final EditText edtTransactionpassword;

  @NonNull
  public final TextView goback;

  @NonNull
  public final ConstraintLayout headlayout;

  @NonNull
  public final ImageView imgDrop1;

  @NonNull
  public final ImageView imgDrop2;

  @NonNull
  public final ImageView imgUploadPicture;

  @NonNull
  public final IncludeActionbarBinding layActionbar;

  @NonNull
  public final LinearLayout layFormTwoButton;

  @NonNull
  public final LinearLayout layKycdetails;

  @NonNull
  public final LinearLayout layKycform;

  @NonNull
  public final RelativeLayout layPaymentTo;

  @NonNull
  public final RelativeLayout laySelectWallet;

  @NonNull
  public final RelativeLayout layoutTransactionpassword;

  @NonNull
  public final LinearLayout lytExpandInput;

  @NonNull
  public final LinearLayout lytExpandText;

  @NonNull
  public final NestedScrollView nestedScrollView;

  @NonNull
  public final AppCompatButton removePicture;

  @NonNull
  public final TextView resendotp;

  @NonNull
  public final TextView spinnerPaymentToTxt;

  @NonNull
  public final TextView spinnerSelectWalletTxt;

  @NonNull
  public final TextView txtDemo;

  @NonNull
  public final TextView txtKycStatus;

  private ActAddFundRequestBinding(@NonNull ConstraintLayout rootView,
      @NonNull AppCompatButton btSaveInput, @NonNull AppCompatButton btnTransactionPassword,
      @NonNull FrameLayout cardAccounteedetail, @NonNull AppCompatButton choosePicture,
      @NonNull EditText edAmount, @NonNull EditText edReferenceId, @NonNull EditText edRemark,
      @NonNull EditText edtTransactionpassword, @NonNull TextView goback,
      @NonNull ConstraintLayout headlayout, @NonNull ImageView imgDrop1,
      @NonNull ImageView imgDrop2, @NonNull ImageView imgUploadPicture,
      @NonNull IncludeActionbarBinding layActionbar, @NonNull LinearLayout layFormTwoButton,
      @NonNull LinearLayout layKycdetails, @NonNull LinearLayout layKycform,
      @NonNull RelativeLayout layPaymentTo, @NonNull RelativeLayout laySelectWallet,
      @NonNull RelativeLayout layoutTransactionpassword, @NonNull LinearLayout lytExpandInput,
      @NonNull LinearLayout lytExpandText, @NonNull NestedScrollView nestedScrollView,
      @NonNull AppCompatButton removePicture, @NonNull TextView resendotp,
      @NonNull TextView spinnerPaymentToTxt, @NonNull TextView spinnerSelectWalletTxt,
      @NonNull TextView txtDemo, @NonNull TextView txtKycStatus) {
    this.rootView = rootView;
    this.btSaveInput = btSaveInput;
    this.btnTransactionPassword = btnTransactionPassword;
    this.cardAccounteedetail = cardAccounteedetail;
    this.choosePicture = choosePicture;
    this.edAmount = edAmount;
    this.edReferenceId = edReferenceId;
    this.edRemark = edRemark;
    this.edtTransactionpassword = edtTransactionpassword;
    this.goback = goback;
    this.headlayout = headlayout;
    this.imgDrop1 = imgDrop1;
    this.imgDrop2 = imgDrop2;
    this.imgUploadPicture = imgUploadPicture;
    this.layActionbar = layActionbar;
    this.layFormTwoButton = layFormTwoButton;
    this.layKycdetails = layKycdetails;
    this.layKycform = layKycform;
    this.layPaymentTo = layPaymentTo;
    this.laySelectWallet = laySelectWallet;
    this.layoutTransactionpassword = layoutTransactionpassword;
    this.lytExpandInput = lytExpandInput;
    this.lytExpandText = lytExpandText;
    this.nestedScrollView = nestedScrollView;
    this.removePicture = removePicture;
    this.resendotp = resendotp;
    this.spinnerPaymentToTxt = spinnerPaymentToTxt;
    this.spinnerSelectWalletTxt = spinnerSelectWalletTxt;
    this.txtDemo = txtDemo;
    this.txtKycStatus = txtKycStatus;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActAddFundRequestBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActAddFundRequestBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.act_add_fund_request, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActAddFundRequestBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.bt_save_input;
      AppCompatButton btSaveInput = ViewBindings.findChildViewById(rootView, id);
      if (btSaveInput == null) {
        break missingId;
      }

      id = R.id.btn_transaction_password;
      AppCompatButton btnTransactionPassword = ViewBindings.findChildViewById(rootView, id);
      if (btnTransactionPassword == null) {
        break missingId;
      }

      id = R.id.card_accounteedetail;
      FrameLayout cardAccounteedetail = ViewBindings.findChildViewById(rootView, id);
      if (cardAccounteedetail == null) {
        break missingId;
      }

      id = R.id.choose_picture;
      AppCompatButton choosePicture = ViewBindings.findChildViewById(rootView, id);
      if (choosePicture == null) {
        break missingId;
      }

      id = R.id.ed_amount;
      EditText edAmount = ViewBindings.findChildViewById(rootView, id);
      if (edAmount == null) {
        break missingId;
      }

      id = R.id.ed_reference_id;
      EditText edReferenceId = ViewBindings.findChildViewById(rootView, id);
      if (edReferenceId == null) {
        break missingId;
      }

      id = R.id.ed_remark;
      EditText edRemark = ViewBindings.findChildViewById(rootView, id);
      if (edRemark == null) {
        break missingId;
      }

      id = R.id.edt_transactionpassword;
      EditText edtTransactionpassword = ViewBindings.findChildViewById(rootView, id);
      if (edtTransactionpassword == null) {
        break missingId;
      }

      id = R.id.goback;
      TextView goback = ViewBindings.findChildViewById(rootView, id);
      if (goback == null) {
        break missingId;
      }

      ConstraintLayout headlayout = (ConstraintLayout) rootView;

      id = R.id.img_drop_1;
      ImageView imgDrop1 = ViewBindings.findChildViewById(rootView, id);
      if (imgDrop1 == null) {
        break missingId;
      }

      id = R.id.img_drop_2;
      ImageView imgDrop2 = ViewBindings.findChildViewById(rootView, id);
      if (imgDrop2 == null) {
        break missingId;
      }

      id = R.id.img_upload_picture;
      ImageView imgUploadPicture = ViewBindings.findChildViewById(rootView, id);
      if (imgUploadPicture == null) {
        break missingId;
      }

      id = R.id.lay_actionbar;
      View layActionbar = ViewBindings.findChildViewById(rootView, id);
      if (layActionbar == null) {
        break missingId;
      }
      IncludeActionbarBinding binding_layActionbar = IncludeActionbarBinding.bind(layActionbar);

      id = R.id.lay_form_two_button;
      LinearLayout layFormTwoButton = ViewBindings.findChildViewById(rootView, id);
      if (layFormTwoButton == null) {
        break missingId;
      }

      id = R.id.lay_kycdetails;
      LinearLayout layKycdetails = ViewBindings.findChildViewById(rootView, id);
      if (layKycdetails == null) {
        break missingId;
      }

      id = R.id.lay_kycform;
      LinearLayout layKycform = ViewBindings.findChildViewById(rootView, id);
      if (layKycform == null) {
        break missingId;
      }

      id = R.id.lay_payment_to;
      RelativeLayout layPaymentTo = ViewBindings.findChildViewById(rootView, id);
      if (layPaymentTo == null) {
        break missingId;
      }

      id = R.id.lay_select_wallet;
      RelativeLayout laySelectWallet = ViewBindings.findChildViewById(rootView, id);
      if (laySelectWallet == null) {
        break missingId;
      }

      id = R.id.layout_transactionpassword;
      RelativeLayout layoutTransactionpassword = ViewBindings.findChildViewById(rootView, id);
      if (layoutTransactionpassword == null) {
        break missingId;
      }

      id = R.id.lyt_expand_input;
      LinearLayout lytExpandInput = ViewBindings.findChildViewById(rootView, id);
      if (lytExpandInput == null) {
        break missingId;
      }

      id = R.id.lyt_expand_text;
      LinearLayout lytExpandText = ViewBindings.findChildViewById(rootView, id);
      if (lytExpandText == null) {
        break missingId;
      }

      id = R.id.nested_scroll_view;
      NestedScrollView nestedScrollView = ViewBindings.findChildViewById(rootView, id);
      if (nestedScrollView == null) {
        break missingId;
      }

      id = R.id.remove_picture;
      AppCompatButton removePicture = ViewBindings.findChildViewById(rootView, id);
      if (removePicture == null) {
        break missingId;
      }

      id = R.id.resendotp;
      TextView resendotp = ViewBindings.findChildViewById(rootView, id);
      if (resendotp == null) {
        break missingId;
      }

      id = R.id.spinner_payment_to_txt;
      TextView spinnerPaymentToTxt = ViewBindings.findChildViewById(rootView, id);
      if (spinnerPaymentToTxt == null) {
        break missingId;
      }

      id = R.id.spinner_select_wallet_txt;
      TextView spinnerSelectWalletTxt = ViewBindings.findChildViewById(rootView, id);
      if (spinnerSelectWalletTxt == null) {
        break missingId;
      }

      id = R.id.txt_demo;
      TextView txtDemo = ViewBindings.findChildViewById(rootView, id);
      if (txtDemo == null) {
        break missingId;
      }

      id = R.id.txt_kyc_status;
      TextView txtKycStatus = ViewBindings.findChildViewById(rootView, id);
      if (txtKycStatus == null) {
        break missingId;
      }

      return new ActAddFundRequestBinding((ConstraintLayout) rootView, btSaveInput,
          btnTransactionPassword, cardAccounteedetail, choosePicture, edAmount, edReferenceId,
          edRemark, edtTransactionpassword, goback, headlayout, imgDrop1, imgDrop2,
          imgUploadPicture, binding_layActionbar, layFormTwoButton, layKycdetails, layKycform,
          layPaymentTo, laySelectWallet, layoutTransactionpassword, lytExpandInput, lytExpandText,
          nestedScrollView, removePicture, resendotp, spinnerPaymentToTxt, spinnerSelectWalletTxt,
          txtDemo, txtKycStatus);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
