package com.codunite.rechargeapp.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.codunite.rechargeapp.R;
import com.codunite.commonutility.retrofit.ApiInterface;
import com.codunite.commonutility.CheckValidation;
import com.codunite.commonutility.NoInternetScreen;
import com.codunite.commonutility.PreferenceConnector;
import com.codunite.commonutility.WebService;
import com.codunite.commonutility.WebServiceListener;
import com.codunite.commonutility.customfont.FontUtils;
import com.codunite.commonutility.GlobalData;
import com.codunite.commonutility.GlobalVariables;

import com.codunite.commonutility.ShowCustomToast;
import com.codunite.rechargeapp.activity.profile.ActivityForgotPassword;
import com.codunite.rechargeapp.fragment.FragHomeDashBoard;
import com.codunite.rechargeapp.model.SliderModel;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ActivityLogin extends AppCompatActivity implements WebServiceListener {
    public static final String TAG_USER_DETAIL = "user_detail";
    public static final String TAG_PROFILE_PHOTO = "profile_photo";
    public static final String TAG_NAME = "name";
    public static final String TAG_MOBILE = "mobile";
    public static final String TAG_CM_POINTS = "cm_points";
    public static final String TAG_USERID = "user_id";

    public static final String TAG_EMAIL = "email";
    public static final String TAG_FCM_ID = "fcm_id";
    public static final String TAG_MESSAGE = "message";
    public static final String TAG_STATUS = "status";
    public static final String TAG_USER_TYPE = "user_type";
    public static final String TAG_USER_CODE = "user_code";
    public static final String TAG_IS_TRANSFER_ACTIVE = "is_transfer_active";
    public static final String TAG_IS_RECHARGE_ACTIVE = "is_recharge_active";
    public static final String TAG_WALLET_BALANCE = "wallet_balance";
    public static final String TAG_EWALLET_BALANCE = "e_wallet_balance";
    private Context svContext;
    private ShowCustomToast customToast;
    private EditText edUsername, edPassword;

    private TextView btnRegister;
    private EditText[] edTexts = {edUsername, edPassword};
    private int[] editTextsClickId = {R.id.edt_loginID, R.id.edt_password};
    private int[] allViewWithClickId = {R.id.btnSignIn, R.id.btnSignUp, R.id.btn_forgetpassword, R.id.btn_transaction_password, R.id.goback, R.id.img_back,R.id.iv_eye};
    private CardView layoutLogin, layoutTransactionpassword;

    RelativeLayout layoutLoginBg;


    private boolean flagPassword = false;
    ImageView iv_eye;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_login);
        //Start Coding from here
        OnClickCombineDeclare(allViewWithClickId);
        EditTextDeclare(edTexts);
        requestPermissionForApp();
        StartApp();
    }
    private void OnClickCombineDeclare(int[] allViewWithClick) {
        for (int j = 0; j < allViewWithClick.length; j++) {
            (findViewById(allViewWithClickId[j])).setOnClickListener(v -> {
                Intent svIntent = null;
                switch (v.getId()) {
                    case R.id.btnSignIn:
                        LoginStart();
                        break;
                    case R.id.btnSignUp:
                        svIntent = new Intent(svContext, ActivitySignUp.class);
                        startActivity(svIntent);
                        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                        break;
                    case R.id.btn_forgetpassword:
                        svIntent = new Intent(svContext, ActivityForgotPassword.class);
                        startActivity(svIntent);
                        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                        break;
                    case R.id.btn_transaction_password:
                        FinalStart();
                        break;
                    case R.id.goback:
                        ShowLoginScreen();
                        break;
                    case R.id.img_back:
                        finish();
                        break;
                    case R.id.iv_eye:
                        if (!flagPassword) {
                            edPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                            iv_eye.setImageResource(R.drawable.ic_eye_vision);
                            flagPassword = true;
                        } else {
                            edPassword.setInputType( InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD );
                            iv_eye.setImageResource(R.drawable.ic_eye_invision);
                            flagPassword = false;
                        }
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + v.getId());
                }
            });
        }
        btnRegister = (TextView) findViewById(allViewWithClick[1]);
    }
    private void LoginStart() {
        int response = 0;
        response = CheckValidation.emptyEditTextError(
                edTexts,
                new String[]{"Enter username", "Enter password"});

        if (response == 0) {
            lstUploadData = new LinkedList<>();
            lstUploadData.add(getEditextValue(edUsername));
            lstUploadData.add(getEditextValue(edPassword));
            //lstUploadData.add(PreferenceConnector.readString(svContext, PreferenceConnector.DEVICE_ID, ""));
            callWebService(ApiInterface.LOGNUSER, lstUploadData);
        }
    }

    private EditText edTranPwd;
    private void FinalStart() {
        edTranPwd = (EditText) findViewById(R.id.edt_transactionpassword);
        int response = 0;
        response = CheckValidation.emptyEditTextError(
                new EditText[]{edTranPwd},
                new String[]{"Enter OTP"});

        if (response == 0) {
            lstUploadData = new LinkedList<>();
            lstUploadData.add(edTranPwd.getText().toString().trim());
            lstUploadData.add(strEncodeLoginCode);
            //lstUploadData.add(PreferenceConnector.readString(svContext, PreferenceConnector.DEVICE_ID, ""));
            callWebService(ApiInterface.LOGINOTPVERIFY, lstUploadData);
        }
    }
    private String getEditextValue(EditText editText) {
        for (int i = 0; i < edTexts.length; i++) {
            if (editText == edTexts[i]) {
                return (edTexts[i]).getText().toString().trim();
            }
        }
        return "";
    }
    private void requestPermissionForApp() {
        Dexter.withContext(this)
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                ).withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {/* ... */}

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {/* ... */}
                }).check();
    }

    private void EditTextDeclare(EditText[] editTexts) {
        for (int j = 0; j < editTexts.length; j++) {
            editTexts[j] = findViewById(editTextsClickId[j]);
        }
        edUsername = (EditText) editTexts[0];
        edPassword = (EditText) editTexts[1];
    }


    private void StartApp() {
        svContext = this;
        customToast = new ShowCustomToast(svContext);

        ViewGroup root = (ViewGroup) findViewById(R.id.headlayout);
        new NoInternetScreen(svContext, root, ActivityLogin.this);
        if (!GlobalVariables.CUSTOMFONTNAME.equals("")) {
            Typeface font = Typeface.createFromAsset(getAssets(), GlobalVariables.CUSTOMFONTNAME);
            FontUtils.setFont(root, font);
        }
        hideKeyboard();
        resumeApp();
    }

    public void resumeApp() {
        iv_eye = (ImageView)findViewById(R.id.iv_eye);
        layoutLogin = (CardView) findViewById(R.id.layoutLogin);
        layoutLoginBg= (RelativeLayout) findViewById(R.id.layoutLoginBg);
        layoutTransactionpassword = (CardView) findViewById(R.id.layout_transactionpassword);
        PreferenceConnector.writeString(this, PreferenceConnector.DEVICE_ID, GlobalData.getDeviceId(this));
        ShowLoginScreen();

    }

    private void ShowTranscationScreen() {
        layoutLogin.setVisibility(View.GONE);
        btnRegister.setVisibility(View.GONE);
        layoutTransactionpassword.setVisibility(View.VISIBLE);
    }
    private void ShowLoginScreen() {
        layoutLogin.setVisibility(View.VISIBLE);
        btnRegister.setVisibility(View.VISIBLE);
        layoutTransactionpassword.setVisibility(View.GONE);
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

    private void callWebService(String postUrl, LinkedList<String> lstUploadData) {
        WebService webService = new WebService(svContext, postUrl, lstUploadData, this);
        webService.LoadDataRetrofit(webService.callReturn());
    }

    private String strEncodeLoginCode = "";
    public static final String TAG_USER_DATA = "user_data";
    public static final String TAG_USER_ID = "user_id";

    @Override
    public void onWebServiceActionComplete(String result, String url) {
        if (url.contains(ApiInterface.LOGNUSER)) {
            try {
                JSONObject json = new JSONObject(result);
                String str_status = json.getString("status");
                String str_msg = json.getString("message");
                if (str_status.equalsIgnoreCase("0")) {
                    customToast.showCustomToast(svContext, str_msg, customToast.ToastyError);
                } else {
                    customToast.showCustomToast(svContext, str_msg, customToast.ToastySuccess);
                    String str_isOtp = "";
                    if (json.has("is_otp")) {
                        str_isOtp = json.getString("is_otp");
                    }

                    if (str_isOtp.equalsIgnoreCase("1")) {
                        strEncodeLoginCode = json.getString("encode_otp_code");
                        ShowTranscationScreen();
                    } else {
                        ActivitySplash.LoadUserData(result, svContext, true);
                    }

                }
            } catch (JSONException e) {
                customToast.showCustomToast(svContext, "Some error occured", customToast.ToastyError);
                e.printStackTrace();
            }
        } else if (url.contains(ApiInterface.LOGINOTPVERIFY)) {
            try {
                JSONObject json = new JSONObject(result);
                String str_status = json.getString("status");
                String str_msg = json.getString("message");
                if (str_status.equalsIgnoreCase("1")) {
                    ActivitySplash.LoadUserData(result, svContext, true);
                } else {
                    customToast.showCustomToast(svContext, str_msg, customToast.ToastyError);
                }
            } catch (JSONException e) {
                customToast.showCustomToast(svContext, "Some error occured", customToast.ToastyError);
                e.printStackTrace();
            }
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

}