package com.rechargeapp.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.commonutility.CheckInternet;
import com.commonutility.GlobalData;
import com.commonutility.GlobalVariables;
import com.commonutility.NoInternetScreen;
import com.commonutility.PreferenceConnector;
import com.commonutility.ShowCustomToast;
import com.commonutility.WebService;
import com.commonutility.WebServiceListener;
import com.commonutility.customfont.FontUtils;
import com.commonutility.retrofit.ApiInterface;
import com.codunite.rechargeapp.R;
import com.rechargeapp.activity.reports.ActivityComisionWalletHistory;
import com.rechargeapp.activity.reports.ActivityWalletHistory;
import com.rechargeapp.fragment.FragHomeDashBoard;
import com.rechargeapp.model.SliderModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;

public class ActivitySplash extends AppCompatActivity implements View.OnClickListener, WebServiceListener {
    private Context svContext;
    private ShowCustomToast customToast;
    private NoInternetScreen errrorScreen;
    private CheckInternet checkNetwork;

    public static final String TAG_USER_TYPE = "user_type";
    public static final String TAG_USER_DETAIL = "user_detail";
    public static final String TAG_USER_CODE = "user_code";
    public static final String TAG_PROFILE_PHOTO = "profile_photo";
    public static final String TAG_IS_RECHARGE_ACTIVE = "is_recharge_active";
    public static final String TAG_NAME = "name";
    public static final String TAG_MOBILE = "mobile";
    public static final String TAG_USERID = "user_id";
    public static final String TAG_EMAIL = "email";
    public static final String TAG_MESSAGE = "message";
    public static final String TAG_STATUS = "status";

    public static final String TAG_IS_MONEY_TRANSFER_ACTIVE = "is_money_transfer_active";
    public static final String TAG_IS_MAIN_TRANSFER_ACTIVE = "is_main_wallet_transfer_active";
    public static final String TAG_IS_AEPS_TRANSFER_ACTIVE = "is_aeps_wallet_transfer_active";
    public static final String TAG_IS_COMMISSION_TRANSFER_ACTIVE = "is_commission_wallet_transfer_active";
    public static final String TAG_IS_TRANSFER_ACTIVE = "is_transfer_active";
    public static final String TAG_WALLET_BALANCE = "wallet_balance";
    public static final String TAG_EWALLET_BALANCE = "e_wallet_balance";
    private ProgressBar progreesBar;

    public static void OpenWalletActivity(Context svContext) {
        Intent svIntent = new Intent(svContext, ActivityWalletHistory.class);
        svContext.startActivity(svIntent);
    }

    public static void OpeneWalletActivity(Context svContext) {
        Intent svIntent = new Intent(svContext, ActivityComisionWalletHistory.class);
        svContext.startActivity(svIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GlobalData.Fullscreen(ActivitySplash.this);
        setContentView(R.layout.act_splash);

        progreesBar = (ProgressBar) findViewById(R.id.progressbar);
        progreesBar.setVisibility(View.VISIBLE);

        //Toast.makeText(getApplicationContext(),getApplicationContext().getPackageName().toString(), Toast.LENGTH_LONG).show();

        StartApp();
        PreferenceConnector.writeBoolean(svContext, PreferenceConnector.ISDARKTHEME, false);
        resumeApp();

    }

    public void resumeApp() {
        LoadSplash();
    }

    private void LoadSplash() {
        if (checkNetwork.isConnectingToInternet()) {
            errrorScreen.hideError();
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            Intent svIntent;
            if (PreferenceConnector.readBoolean(svContext, PreferenceConnector.AUTOLOGIN, false)) {
                lstUploadData = new LinkedList<>();
                lstUploadData.add(PreferenceConnector.readString(svContext, PreferenceConnector.LOGINEDUSERID, ""));
                lstUploadData.add(PreferenceConnector.readString(svContext, PreferenceConnector.FCMID, ""));
                lstUploadData.add(PreferenceConnector.readString(svContext, PreferenceConnector.DEVICE_ID, ""));
                callWebServiceWithoutLoader(ApiInterface.UPDATEFCM, lstUploadData);
            } else {
                //svIntent = new Intent(svContext, ActivityLogin.class);
                svIntent = new Intent(svContext, ActivityIntro.class);
                startActivity(svIntent);
                progreesBar.setVisibility(View.INVISIBLE);
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                finish();
            }
        }, 2000);
        } else {
            errrorScreen.showInternetError();
        }
    }

    private void StartApp() {
        svContext = this;
        customToast = new ShowCustomToast(svContext);
        checkNetwork = new CheckInternet(svContext);
        ViewGroup root = (ViewGroup) findViewById(R.id.headlayout);
        errrorScreen= new NoInternetScreen(svContext, root, ActivitySplash.this);
        if (!(GlobalVariables.CUSTOMFONTNAME).equals("")) {
            Typeface font = Typeface.createFromAsset(getAssets(), GlobalVariables.CUSTOMFONTNAME);
            FontUtils.setFont(root, font);
        }
        //change app heme from
        if (PreferenceConnector.readBoolean(svContext, PreferenceConnector.ISDARKTHEME, false)) {
//            FontUtils.setThemeColor(root, svContext, true);
        } else {
//            FontUtils.setThemeColor(root, svContext, false);
        }
        hideKeyboard();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
        }
    }

    private void hideKeyboard() {
        InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        // check if no view has focus:
        View view = this.getCurrentFocus();
        if (view != null) {
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public static void hideFragmentkeyboard(Context meraContext, View meraView) {
        final InputMethodManager imm = (InputMethodManager) meraContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(meraView.getWindowToken(), 0);
    }

    LinkedList<String> lstUploadData = new LinkedList<>();

    private void callWebServiceWithoutLoader(String postUrl, LinkedList<String> lstUploadData) {
        WebService webService = new WebService(svContext, postUrl, lstUploadData, this, false);
        webService.LoadDataRetrofit(webService.callReturn());
    }

    @Override
    public void onWebServiceActionComplete(String result, String url) {
        if (url.contains(ApiInterface.UPDATEFCM)) {
            LoadUserData(result, svContext, true);
            progreesBar.setVisibility(View.INVISIBLE);
        }
    }

    public static void LoadUserData(String result, Context svContext) {
        LoadUserData(result, svContext, false);
    }

    public static void LoadUserData(String result, Context svContext, boolean isLogin) {
        try {
            JSONObject json = new JSONObject(result);
            String str_status = json.getString(TAG_STATUS);
            String str_message = json.getString(TAG_MESSAGE);

            if (GlobalVariables.ISTESTING) {
                Log.e("result>>>>", result);
            }
            if (str_status.equalsIgnoreCase("1")) {
                PreferenceConnector.writeBoolean(svContext, PreferenceConnector.AUTOLOGIN, true);

                JSONObject user_detail_obj = json.getJSONObject(TAG_USER_DETAIL);
                String str_is_transfer_active = user_detail_obj.getString(TAG_IS_TRANSFER_ACTIVE);
                String str_is_recharge_active = user_detail_obj.getString(TAG_IS_RECHARGE_ACTIVE);
                String str_user_code = user_detail_obj.getString(TAG_USER_CODE);
                String str_profile_photo = user_detail_obj.getString(TAG_PROFILE_PHOTO);
                String str_name = user_detail_obj.getString(TAG_NAME);
                String str_mobile = user_detail_obj.getString(TAG_MOBILE);
                String str_wallet_balance = user_detail_obj.getString(TAG_WALLET_BALANCE);
                String str_ewallet_balance = user_detail_obj.getString(TAG_EWALLET_BALANCE);
                String str_userID = user_detail_obj.getString(TAG_USERID);
                String str_email = user_detail_obj.getString(TAG_EMAIL);
                String str_user_type = user_detail_obj.getString(TAG_USER_TYPE);


                PreferenceConnector.writeString(svContext, PreferenceConnector.LOGINUSERTYPE, str_user_type);
                PreferenceConnector.writeString(svContext, PreferenceConnector.LOGINMEMBERID, str_user_code);
                PreferenceConnector.writeString(svContext, PreferenceConnector.LOGINUSERNAME, str_name);
                PreferenceConnector.writeString(svContext, PreferenceConnector.LOGINEDUSERID, str_userID);


                PreferenceConnector.writeString(svContext, PreferenceConnector.LOGINUSERPHONE, str_mobile);
                PreferenceConnector.writeString(svContext, PreferenceConnector.LOGINUSEREMAIL, str_email);

                PreferenceConnector.writeString(svContext, PreferenceConnector.WALLETBAL, str_wallet_balance);
                PreferenceConnector.writeString(svContext, PreferenceConnector.EWALLETBAL, str_ewallet_balance);

                if (str_is_transfer_active.equalsIgnoreCase("1")) {
                    PreferenceConnector.writeBoolean(svContext, PreferenceConnector.ISTRANSFERACTIVE, true);
                } else {
                    PreferenceConnector.writeBoolean(svContext, PreferenceConnector.ISTRANSFERACTIVE, false);
                }

                if (str_is_recharge_active.equalsIgnoreCase("1")) {
                    PreferenceConnector.writeBoolean(svContext, PreferenceConnector.ISRECHARGEACTIVE, true);
                } else {
                    PreferenceConnector.writeBoolean(svContext, PreferenceConnector.ISRECHARGEACTIVE, false);
                }
                if (str_is_recharge_active.equalsIgnoreCase("1")) {
                    PreferenceConnector.writeBoolean(svContext, PreferenceConnector.ISRECHARGEACTIVE, true);
                } else {
                    PreferenceConnector.writeBoolean(svContext, PreferenceConnector.ISRECHARGEACTIVE, false);
                }
                //PreferenceConnector.writeString(svContext, PreferenceConnector.LOGINUSERPROFILEPIC, str_profile_photo);


                String str_is_razorpay_active = "";
                if (user_detail_obj.has("is_razorypay_active")) {
                    str_is_razorpay_active = user_detail_obj.getString("is_razorypay_active");
                }

                if (user_detail_obj.has("news")) {
                    String str_news = user_detail_obj.getString("news");
                    PreferenceConnector.writeString(svContext, PreferenceConnector.DASHNEWS, str_news);
                }

                if (user_detail_obj.has("is_bbps_live_active")) {
                    String str_is_bbps_live_active = user_detail_obj.getString("is_bbps_live_active");
                    PreferenceConnector.writeBoolean(svContext, PreferenceConnector.ISBBPSACTIVE, str_is_bbps_live_active.equalsIgnoreCase("1"));
                }

                if (user_detail_obj.has("is_xpress_payout")) {
                    String str_is_xpress_payout = user_detail_obj.getString("is_xpress_payout");
                    PreferenceConnector.writeBoolean(svContext, PreferenceConnector.ISXPRESSPAYOUTACTIVE, str_is_xpress_payout.equalsIgnoreCase("1"));
                }

                if (user_detail_obj.has("is_aeps_active")) {
                    String str_is_apes_active = user_detail_obj.getString("is_aeps_active");
                    PreferenceConnector.writeBoolean(svContext, PreferenceConnector.ISAEPSACTIVE, str_is_apes_active.equalsIgnoreCase("1"));
                }

                if (user_detail_obj.has("user_aeps_status")) {
                    String str_user_aeps_status = user_detail_obj.getString("user_aeps_status");
                    PreferenceConnector.writeBoolean(svContext, PreferenceConnector.ISAEPSKYCDONE, str_user_aeps_status.equalsIgnoreCase("1"));
                }

                if (user_detail_obj.has("is_dmt_active")) {
                    String str_is_dmt_active = user_detail_obj.getString("is_dmt_active");
                    PreferenceConnector.writeBoolean(svContext, PreferenceConnector.ISDMTACTIVE, str_is_dmt_active.equalsIgnoreCase("1"));
                }

                if (user_detail_obj.has("virtual_account_status")) {
                    String str_virtual_account_status = user_detail_obj.getString("virtual_account_status");
                    PreferenceConnector.writeBoolean(svContext, PreferenceConnector.VIRTUALACCOUNT_ACTIVE_STATUS, str_virtual_account_status.equalsIgnoreCase("1"));
                }

                if (user_detail_obj.has("is_virtual_account")) {
                    String str_is_virtual_account = user_detail_obj.getString("is_virtual_account");
                    PreferenceConnector.writeBoolean(svContext, PreferenceConnector.ISVIRTUALACCOUNT, str_is_virtual_account.equalsIgnoreCase("1"));
                }

                if (user_detail_obj.has("sliderData")) {
                    FragHomeDashBoard.lstSlider = new ArrayList<>();
                    JSONArray slider_data = user_detail_obj.getJSONArray("sliderData");
                    for (int i = 0; i < slider_data.length(); i++) {
                        JSONObject slider_img = slider_data.getJSONObject(i);
                        FragHomeDashBoard.lstSlider.add(new SliderModel(i + "",
                                slider_img.getString("link"),
                                GlobalVariables.PRE_URL_MAIN + slider_img.getString("imageUrl")));

                    }
                }

                if (str_is_razorpay_active.equalsIgnoreCase("1")) {
                    PreferenceConnector.writeBoolean(svContext, PreferenceConnector.ISRAZORPAYACTIVE, true);
                    PreferenceConnector.writeString(svContext, PreferenceConnector.RAZORPAYID,
                            user_detail_obj.getString("razor_pay_key"));
                } else {
                    PreferenceConnector.writeBoolean(svContext, PreferenceConnector.ISRAZORPAYACTIVE, false);
                    PreferenceConnector.writeString(svContext, PreferenceConnector.RAZORPAYID, "");
                }

                if (user_detail_obj.has("token")) {
                    String str_token = user_detail_obj.getString("token");
                    PreferenceConnector.writeString(svContext, PreferenceConnector.TOKEN, str_token);
                }

                String str_success = "", str_failed = "", str_pending = "";
                if (user_detail_obj.has("successAmount")) {
                    str_success = user_detail_obj.getString("successAmount");
                }
                if (user_detail_obj.has("failedAmount")) {
                    str_failed = user_detail_obj.getString("failedAmount");
                }
                if (user_detail_obj.has("pendingAmount")) {
                    str_pending = user_detail_obj.getString("pendingAmount");
                }
                PreferenceConnector.writeString(svContext, PreferenceConnector.TOTALSUCCESS, str_success);
                PreferenceConnector.writeString(svContext, PreferenceConnector.TOTALFAILED, str_failed);
                PreferenceConnector.writeString(svContext, PreferenceConnector.TOTALPENDING, str_pending);
                PreferenceConnector.writeString(svContext, PreferenceConnector.LOGINUSERPROFILEPIC, str_profile_photo);


                if (isLogin) {

                    Intent svIntent = new Intent(svContext, ActivityMain.class);
                    svContext.startActivity(svIntent);
                    ((Activity) svContext).finishAffinity();
                }

            } else {
                PreferenceConnector.cleanPrefrences(svContext);
                Intent svIntent = new Intent(svContext, ActivityLogin.class);
                svContext.startActivity(svIntent);
                ((Activity) svContext).finish();

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onWebServiceError(String result, String url) {
        customToast.showCustomToast(svContext, result, customToast.ToastyError);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private String strReferCode;
}