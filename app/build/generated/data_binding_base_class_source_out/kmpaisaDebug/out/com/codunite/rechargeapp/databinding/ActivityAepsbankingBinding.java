// Generated by data binding compiler. Do not edit!
package com.codunite.rechargeapp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.codunite.rechargeapp.R;
import java.lang.Deprecated;
import java.lang.Object;
import me.grantland.widget.AutofitTextView;

public abstract class ActivityAepsbankingBinding extends ViewDataBinding {
  @NonNull
  public final LinearLayout bankDetails;

  @NonNull
  public final AppCompatButton btnProcess;

  @NonNull
  public final AppCompatButton btnSubmit;

  @NonNull
  public final RadioGroup deviceType;

  @NonNull
  public final EditText edtAadharNumber;

  @NonNull
  public final EditText edtAmount;

  @NonNull
  public final EditText edtMobNumber;

  @NonNull
  public final EditText edtSearche;

  @NonNull
  public final AutofitTextView heading;

  @NonNull
  public final ImageView imgBack;

  @NonNull
  public final ImageView imgCross;

  @NonNull
  public final ImageView imgCrossWeb;

  @NonNull
  public final ImageView imgDrop1;

  @NonNull
  public final LinearLayout imgEwallet;

  @NonNull
  public final ImageView imgSearch;

  @NonNull
  public final View imgView3;

  @NonNull
  public final LinearLayout imgWallet;

  @NonNull
  public final RadioGroup inputType;

  @NonNull
  public final LinearLayout layBalance;

  @NonNull
  public final ConstraintLayout laySearch;

  @NonNull
  public final RelativeLayout laySearchview;

  @NonNull
  public final RelativeLayout layTop;

  @NonNull
  public final RelativeLayout layWeb;

  @NonNull
  public final RecyclerView mainRecycler;

  @NonNull
  public final LinearLayout prepaidAndSemilarLayout;

  @NonNull
  public final RadioButton radioAadharpay;

  @NonNull
  public final RadioButton radioBankEnq;

  @NonNull
  public final RadioButton radioFour;

  @NonNull
  public final RadioButton radioMantra;

  @NonNull
  public final RadioButton radioMiniStat;

  @NonNull
  public final RadioButton radioMorpho;

  @NonNull
  public final RadioButton radioOne;

  @NonNull
  public final RadioButton radioThree;

  @NonNull
  public final RadioButton radioTwo;

  @NonNull
  public final RadioButton radioWithdrawal;

  @NonNull
  public final RelativeLayout rvMain;

  @NonNull
  public final Spinner spinnerStatelist;

  @NonNull
  public final TextView toolbarTxtEwalletbal;

  @NonNull
  public final TextView toolbarTxtWalletbal;

  @NonNull
  public final TextView tvMachinetype;

  @NonNull
  public final TextView tvOutput;

  @NonNull
  public final RecyclerView tvOutputWebview;

  @NonNull
  public final TextView tvSelectmethod;

  @NonNull
  public final TextView txtBalance;

  @NonNull
  public final TextView txtSharereceipt;

  @NonNull
  public final TextView txtTobank;

  protected ActivityAepsbankingBinding(Object _bindingComponent, View _root, int _localFieldCount,
      LinearLayout bankDetails, AppCompatButton btnProcess, AppCompatButton btnSubmit,
      RadioGroup deviceType, EditText edtAadharNumber, EditText edtAmount, EditText edtMobNumber,
      EditText edtSearche, AutofitTextView heading, ImageView imgBack, ImageView imgCross,
      ImageView imgCrossWeb, ImageView imgDrop1, LinearLayout imgEwallet, ImageView imgSearch,
      View imgView3, LinearLayout imgWallet, RadioGroup inputType, LinearLayout layBalance,
      ConstraintLayout laySearch, RelativeLayout laySearchview, RelativeLayout layTop,
      RelativeLayout layWeb, RecyclerView mainRecycler, LinearLayout prepaidAndSemilarLayout,
      RadioButton radioAadharpay, RadioButton radioBankEnq, RadioButton radioFour,
      RadioButton radioMantra, RadioButton radioMiniStat, RadioButton radioMorpho,
      RadioButton radioOne, RadioButton radioThree, RadioButton radioTwo,
      RadioButton radioWithdrawal, RelativeLayout rvMain, Spinner spinnerStatelist,
      TextView toolbarTxtEwalletbal, TextView toolbarTxtWalletbal, TextView tvMachinetype,
      TextView tvOutput, RecyclerView tvOutputWebview, TextView tvSelectmethod, TextView txtBalance,
      TextView txtSharereceipt, TextView txtTobank) {
    super(_bindingComponent, _root, _localFieldCount);
    this.bankDetails = bankDetails;
    this.btnProcess = btnProcess;
    this.btnSubmit = btnSubmit;
    this.deviceType = deviceType;
    this.edtAadharNumber = edtAadharNumber;
    this.edtAmount = edtAmount;
    this.edtMobNumber = edtMobNumber;
    this.edtSearche = edtSearche;
    this.heading = heading;
    this.imgBack = imgBack;
    this.imgCross = imgCross;
    this.imgCrossWeb = imgCrossWeb;
    this.imgDrop1 = imgDrop1;
    this.imgEwallet = imgEwallet;
    this.imgSearch = imgSearch;
    this.imgView3 = imgView3;
    this.imgWallet = imgWallet;
    this.inputType = inputType;
    this.layBalance = layBalance;
    this.laySearch = laySearch;
    this.laySearchview = laySearchview;
    this.layTop = layTop;
    this.layWeb = layWeb;
    this.mainRecycler = mainRecycler;
    this.prepaidAndSemilarLayout = prepaidAndSemilarLayout;
    this.radioAadharpay = radioAadharpay;
    this.radioBankEnq = radioBankEnq;
    this.radioFour = radioFour;
    this.radioMantra = radioMantra;
    this.radioMiniStat = radioMiniStat;
    this.radioMorpho = radioMorpho;
    this.radioOne = radioOne;
    this.radioThree = radioThree;
    this.radioTwo = radioTwo;
    this.radioWithdrawal = radioWithdrawal;
    this.rvMain = rvMain;
    this.spinnerStatelist = spinnerStatelist;
    this.toolbarTxtEwalletbal = toolbarTxtEwalletbal;
    this.toolbarTxtWalletbal = toolbarTxtWalletbal;
    this.tvMachinetype = tvMachinetype;
    this.tvOutput = tvOutput;
    this.tvOutputWebview = tvOutputWebview;
    this.tvSelectmethod = tvSelectmethod;
    this.txtBalance = txtBalance;
    this.txtSharereceipt = txtSharereceipt;
    this.txtTobank = txtTobank;
  }

  @NonNull
  public static ActivityAepsbankingBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_aepsbanking, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static ActivityAepsbankingBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<ActivityAepsbankingBinding>inflateInternal(inflater, R.layout.activity_aepsbanking, root, attachToRoot, component);
  }

  @NonNull
  public static ActivityAepsbankingBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_aepsbanking, null, false, component)
   */
  @NonNull
  @Deprecated
  public static ActivityAepsbankingBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<ActivityAepsbankingBinding>inflateInternal(inflater, R.layout.activity_aepsbanking, null, false, component);
  }

  public static ActivityAepsbankingBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.bind(view, component)
   */
  @Deprecated
  public static ActivityAepsbankingBinding bind(@NonNull View view, @Nullable Object component) {
    return (ActivityAepsbankingBinding)bind(component, view, R.layout.activity_aepsbanking);
  }
}
