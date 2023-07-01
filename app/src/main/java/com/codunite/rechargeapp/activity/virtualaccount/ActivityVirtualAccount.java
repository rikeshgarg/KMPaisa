package com.codunite.rechargeapp.activity.virtualaccount;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.codunite.commonutility.CheckInternet;
import com.codunite.commonutility.GlobalData;
import com.codunite.commonutility.GlobalVariables;
import com.codunite.commonutility.NoInternetScreen;
import com.codunite.commonutility.PreferenceConnector;
import com.codunite.commonutility.ShowCustomToast;
import com.codunite.commonutility.WebService;
import com.codunite.commonutility.WebServiceListener;
import com.codunite.commonutility.customfont.FontUtils;
import com.codunite.commonutility.retrofit.ApiInterface;
import com.codunite.rechargeapp.R;
import com.codunite.rechargeapp.activity.ActivityMain;
import com.codunite.rechargeapp.activity.ActivitySplash;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.LinkedList;

public class ActivityVirtualAccount extends AppCompatActivity implements View.OnClickListener, WebServiceListener {
    private View[] allViewWithClick = {};
    private int[] allViewWithClickId = {};

    private EditText[] edTexts = {};
    private String[] edTextsError = {""};
    private int[] editTextsClickId = {};
    private Context svContext;
    private ShowCustomToast customToast;
    private CheckInternet checkNetwork;
    private NoInternetScreen errrorScreen;
    private TextView tvActiveStatus, tvVirtualAccount, tvIfsc;
    private Button btnActive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_virtual_account);
        StartApp();

        OnClickCombineDeclare(allViewWithClick);
        EditTextDeclare(edTexts);

        resumeApp();
    }

    public void resumeApp() {
        tvActiveStatus = findViewById(R.id.tv_active_status);
        tvVirtualAccount = findViewById(R.id.tv_virtualac);
        tvIfsc = findViewById(R.id.tv_ifsc);
        btnActive = findViewById(R.id.btn_active);

        LoadActiveButtonStatus();

        lstUploadData = new LinkedList<>();
        lstUploadData.add(PreferenceConnector.readString(svContext, PreferenceConnector.LOGINEDUSERID, ""));
        callWebService(ApiInterface.VIRTUAL_ACCOUNT_AUTH, lstUploadData);

    }

    public void LoadActiveButtonStatus() {
        if (PreferenceConnector.readBoolean(svContext, PreferenceConnector.VIRTUALACCOUNT_ACTIVE_STATUS, false)) {
            btnActive.setVisibility(View.GONE);
        } else {
            btnActive.setVisibility(View.VISIBLE);
            btnActive.setOnClickListener(v -> {
                lstUploadData = new LinkedList<>();
                lstUploadData.add(PreferenceConnector.readString(svContext, PreferenceConnector.LOGINEDUSERID, ""));
                callWebService(ApiInterface.VIRTUAL_ACTIVE_AUTH, lstUploadData);
            });
        }
    }

    private void EditTextDeclare(EditText[] editTexts) {
        for (int j = 0; j < editTexts.length; j++) {
            editTexts[j] = findViewById(editTextsClickId[j]);
        }
    }

    private void OnClickCombineDeclare(View[] allViewWithClick) {
        for (int j = 0; j < allViewWithClick.length; j++) {
            allViewWithClick[j] = findViewById(allViewWithClickId[j]);
            allViewWithClick[j].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {

                    }
                }
            });
        }

//        btnBack = (Button) allViewWithClick[0];
    }




    private void StartApp() {
        svContext = this;
        customToast = new ShowCustomToast(svContext);
        checkNetwork = new CheckInternet(svContext);
        ViewGroup root = (ViewGroup) findViewById(R.id.headlayout);
        errrorScreen = new NoInternetScreen(svContext, root, ActivityVirtualAccount.this);
        if (!GlobalVariables.CUSTOMFONTNAME.equals("")) {
            Typeface font = Typeface.createFromAsset(getAssets(), GlobalVariables.CUSTOMFONTNAME);
            FontUtils.setFont(root, font);
        }

        hideKeyboard();
        GlobalData.SetLanguage(svContext);
        if (checkNetwork.isConnectingToInternet()) {
            errrorScreen.hideError();
        } else {
            errrorScreen.showInternetError();
        }

        loadToolBar();
    }

    private ImageView imgToolBarBack;

    private void loadToolBar() {
        imgToolBarBack = (ImageView) findViewById(R.id.img_back);
        imgToolBarBack.setOnClickListener(this);

        TextView txtHeading = (TextView) findViewById(R.id.heading);
        txtHeading.setText("Virtual Account Details");

//        TextView toolbar_txt_walletbal = (TextView) findViewById(R.id.toolbar_txt_walletbal);
//        toolbar_txt_walletbal.setText(ActivityMain.ShowBalance(svContext));
//
//        TextView toolbar_txt_ewalletbal = (TextView) findViewById(R.id.toolbar_txt_ewalletbal);
//        toolbar_txt_ewalletbal.setText(ActivityMain.ShoweBalance(svContext));
//
//        LinearLayout imgToolBarWallet = (LinearLayout) findViewById(R.id.img_wallet);
//        imgToolBarWallet.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ActivitySplash.OpenWalletActivity(svContext);
//            }
//        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
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

    private void callWebService(String postUrl, LinkedList<String> lstUploadData) {
        WebService webService = new WebService(svContext, postUrl, lstUploadData, this);
        webService.LoadDataRetrofit(webService.callReturn());
    }

    @Override
    public void onWebServiceActionComplete(String result, String url) {
        System.out.println(result + ".........jsonresponse....." + url);
        if (url.contains(ApiInterface.VIRTUAL_ACCOUNT_AUTH)) {
            try {
                //{"status":1,"message":"Success","isActive":"Active","accountNo":"KMPA9872651555","ifsc":"ICIC0000104"}
                JSONObject json = new JSONObject(result);
                String strStatus = json.getString("status");
                String strMessage = json.getString("message");
                if (strStatus.equalsIgnoreCase("0")) {
                    customToast.showCustomToast(svContext, strMessage, customToast.ToastyError);
                }

                if (json.has("isActive")) {
                    String strIsActive = json.getString("isActive");
                    tvActiveStatus.setText(strIsActive);

                    if (strIsActive.equalsIgnoreCase("Active")) {
                        tvActiveStatus.setTextColor(getResources().getColor(R.color.green));
                    } else {
                        tvActiveStatus.setTextColor(getResources().getColor(R.color.red));
                    }
                }

                if (json.has("accountNo")) {
                    String strAccountNo = json.getString("accountNo");
                    tvVirtualAccount.setText(strAccountNo);
                }

                if (json.has("ifsc")) {
                    String strIfsc = json.getString("ifsc");
                    tvIfsc.setText(strIfsc);
                }

            } catch (JSONException e) {
                customToast.showCustomToast(svContext, "Some error occured", customToast.ToastyError);
                e.printStackTrace();
            }
        } else if (url.contains(ApiInterface.VIRTUAL_ACTIVE_AUTH)) {
            try {
                JSONObject json = new JSONObject(result);
                String strStatus = json.getString("status");
                String strMessage = json.getString("message");
                if (strStatus.equalsIgnoreCase("0")) {
                    customToast.showCustomToast(svContext, strMessage, customToast.ToastyError);
                } else {
                    customToast.showCustomToast(svContext, strMessage, customToast.ToastySuccess);

                    lstUploadData = new LinkedList<>();
                    lstUploadData.add(PreferenceConnector.readString(svContext, PreferenceConnector.LOGINEDUSERID, ""));
                    lstUploadData.add(PreferenceConnector.readString(svContext, PreferenceConnector.FCMID, ""));
                    lstUploadData.add(PreferenceConnector.readString(svContext, PreferenceConnector.DEVICE_ID, ""));
                    callWebService(ApiInterface.UPDATEFCM, lstUploadData);
                }

            } catch (JSONException e) {
                customToast.showCustomToast(svContext, "Some error occured", customToast.ToastyError);
                e.printStackTrace();
            }
        } else if (url.contains(ApiInterface.UPDATEFCM)) {
            ActivitySplash.LoadUserData(result, svContext);
            LoadActiveButtonStatus();
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