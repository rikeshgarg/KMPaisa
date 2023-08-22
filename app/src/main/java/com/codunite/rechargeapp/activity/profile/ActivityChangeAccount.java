package com.codunite.rechargeapp.activity.profile;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.codunite.commonutility.retrofit.ApiInterface;
import com.codunite.commonutility.CheckValidation;
import com.codunite.commonutility.GlobalVariables;
import com.codunite.commonutility.NoInternetScreen;
import com.codunite.commonutility.PreferenceConnector;
import com.codunite.commonutility.ShowCustomToast;
import com.codunite.commonutility.WebService;
import com.codunite.commonutility.WebServiceListener;
import com.codunite.commonutility.customfont.FontUtils;
import com.codunite.rechargeapp.R;
import com.codunite.rechargeapp.WebViewActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;

public class ActivityChangeAccount extends AppCompatActivity implements View.OnClickListener, WebServiceListener {
    private Button btnSubmitContact;
    private View[] allViewWithClick = {btnSubmitContact};
    private int[] allViewWithClickId = {R.id.btn_submitcontact};

    private EditText edHolderName, edAccountNo, edBankIfsc, edBankName;
    private EditText[] edTexts = {edHolderName, edAccountNo, edBankIfsc, edBankName};
    private String[] edError = {"Enter account holder name", "Enter account number", "Enter bank ifsc", "Enter bank name"};
    private int[] editTextsClickId = {R.id.ed_accountholder_name, R.id.ed_account_number, R.id.ed_bank_ifsc, R.id.ed_bank_name};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_change_account);
        StartApp();

        OnClickCombineDeclare(allViewWithClick);
        EditTextDeclare(edTexts);

        resumeApp();
    }

    public void resumeApp() {
        lstUploadData = new LinkedList<>();
        lstUploadData.add(PreferenceConnector.readString(svContext, PreferenceConnector.LOGINEDUSERID, ""));
        callWebService(ApiInterface.USERKYCDETAIL, lstUploadData);

    }

    private void EditTextDeclare(EditText[] editTexts) {
        for (int j = 0; j < editTexts.length; j++) {
            editTexts[j] = findViewById(editTextsClickId[j]);
        }
        edHolderName = (EditText) editTexts[0];
        edAccountNo = (EditText) editTexts[1];
        edBankIfsc = (EditText) editTexts[2];
        edBankName = (EditText) editTexts[3];
    }

    private void OnClickCombineDeclare(View[] allViewWithClick) {
        for (int j = 0; j < allViewWithClick.length; j++) {
            allViewWithClick[j] = findViewById(allViewWithClickId[j]);
            allViewWithClick[j].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.btn_submitcontact:
                            int response = 0;
                            response = CheckValidation.emptyEditTextError(edTexts, edError);

                            if (response == 0) {
                                lstUploadData = new LinkedList<>();
                                lstUploadData.add(PreferenceConnector.readString(svContext, PreferenceConnector.LOGINEDUSERID, ""));
                                lstUploadData.add(getEditextValue(edHolderName));
                                lstUploadData.add(getEditextValue(edAccountNo));
                                lstUploadData.add(getEditextValue(edBankIfsc));
                                lstUploadData.add(getEditextValue(edBankName));
                                callWebService(ApiInterface.USERKYC_CHANGE_ACCOUNT, lstUploadData);
                            }
                            break;
                    }
                }
            });
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


    private Context svContext;
    private ShowCustomToast customToast;
    
    

    private void StartApp() {
        svContext = this;
        customToast = new ShowCustomToast(svContext);
        
        ViewGroup root = (ViewGroup) findViewById(R.id.headlayout);
        new NoInternetScreen(svContext, root, ActivityChangeAccount.this);
        if (!GlobalVariables.CUSTOMFONTNAME.equals("")) {
            Typeface font = Typeface.createFromAsset(getAssets(), GlobalVariables.CUSTOMFONTNAME);
            FontUtils.setFont(root, font);
        }
        if (PreferenceConnector.readBoolean(svContext, PreferenceConnector.ISDARKTHEME, false)) {
//            FontUtils.setThemeColor(root, svContext, true);
        } else {
//            FontUtils.setThemeColor(root, svContext, false);
        }

        hideKeyboard();

        

        loadToolBar();
    }

    private ImageView imgToolBarBack;

    private void loadToolBar() {
        imgToolBarBack = (ImageView) findViewById(R.id.img_back);
        imgToolBarBack.setOnClickListener(this);

        TextView txtHeading = (TextView) findViewById(R.id.heading);
        txtHeading.setText("Change Account");
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
        if (url.contains(ApiInterface.USERKYC_CHANGE_ACCOUNT)) {
            try {
                JSONObject json = new JSONObject(result);
                String str_message = json.getString("message");
                String str_status = json.getString("status");
                if (str_status.equalsIgnoreCase("0")) {
                    customToast.showCustomToast(svContext, str_message, customToast.ToastyError);
                } else {
                    customToast.showCustomToast(svContext, str_message, customToast.ToastySuccess);
                    finish();
                }
            } catch (JSONException e) {
                customToast.showCustomToast(svContext, "Some error occured", customToast.ToastyError);
                e.printStackTrace();
            }
        } else if (url.contains(ApiInterface.USERKYCDETAIL)) {
            try {
                JSONObject json = new JSONObject(result);

                String str_message = json.getString("message");
                String str_status = json.getString("status");
                if (str_status.equalsIgnoreCase("0")) {
                    customToast.showCustomToast(svContext, str_message, customToast.ToastyError);
                } else {
                    if (json.has("data") && !json.get("data").toString().equals("[]")) {
                        JSONObject data = json.getJSONObject("data");
                        if (data.has("ac_holder_name")) {
                            edHolderName.setText(data.getString("ac_holder_name"));
                        }
                        if (data.has("ac_no")) {
                            edAccountNo.setText(data.getString("ac_no"));
                        }
                        if (data.has("ifsc")) {
                            edBankIfsc.setText(data.getString("ifsc"));
                        }
                        if (data.has("bank_name")) {
                            edBankName.setText(data.getString("bank_name"));
                        }
                    }
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