package com.codunite.rechargeapp.activity.profile;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.codunite.commonutility.ImageLoading;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;

public class ActivityChangePassword extends AppCompatActivity implements WebServiceListener {
    private EditText edExistingPasword, edNewPassword, edConfirmPasword;
    private int[] allViewWithClickId = {R.id.btn_CP_update, R.id.img_back};

    private EditText[] edTexts = {edExistingPasword, edNewPassword, edConfirmPasword};
    private String[] edTextsError = {"Enter existing password", "Enter new password", "Enter confirm password"};
    private int[] editTextsClickId = {R.id.edt_CP_existingpass, R.id.edt_CP_newpass, R.id.edt_CP_confirmPass};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_changepassword);
        StartApp();
        OnClickCombineDeclare(allViewWithClickId);
        EditTextDeclare(edTexts);
    }

    private void CPUpdate() {
        int response = 0;
        response = CheckValidation.emptyEditTextError(edTexts, edTextsError);

        if (!getEditextValue(edNewPassword).equals(getEditextValue(edConfirmPasword))) {
            response++;
            customToast.showCustomToast(svContext, "Password not matching", customToast.ToastyError);
        }

        if (response == 0) {
            lstUploadData = new LinkedList<>();
            lstUploadData.add(PreferenceConnector.readString(svContext, PreferenceConnector.LOGINEDUSERID, ""));
            lstUploadData.add(getEditextValue(edExistingPasword));
            lstUploadData.add(getEditextValue(edNewPassword));
            lstUploadData.add(getEditextValue(edConfirmPasword));
            callWebService(ApiInterface.CHANGEPASSWORD, lstUploadData);
        }
    }



    private void OnClickCombineDeclare(int[] allViewWithClick) {
        for (int j = 0; j < allViewWithClick.length; j++) {
            (findViewById(allViewWithClickId[j])).setOnClickListener(v -> {
                switch (v.getId()) {
                    case R.id.img_back:
                        onBackPressed();
                        break;
                    case R.id.btn_CP_update:
                        CPUpdate();
                        break;
                }
            });
        }
    }

    private void EditTextDeclare(EditText[] editTexts) {
        for (int j = 0; j < editTexts.length; j++) {
            editTexts[j] = findViewById(editTextsClickId[j]);
        }
        edExistingPasword = (EditText) editTexts[0];
        edNewPassword = (EditText) editTexts[1];
        edConfirmPasword = (EditText) editTexts[2];
    }

    private Context svContext;
    private ShowCustomToast customToast;
    private void StartApp() {
        svContext = this;
        customToast = new ShowCustomToast(svContext);
        ViewGroup root = (ViewGroup) findViewById(R.id.headlayout);
        TextView tvName = (TextView)findViewById(R.id.menuheader_name);
        TextView tvMemberId = (TextView)findViewById(R.id.menuheader_memberid);
        ImageView imgAvatar =(ImageView)findViewById(R.id.avatar);
        tvName.setText(PreferenceConnector.readString(svContext, PreferenceConnector.LOGINUSERNAME, ""));
        tvMemberId.setText(PreferenceConnector.readString(svContext, PreferenceConnector.LOGINMEMBERID, "memberid"));
        String imageUrl = PreferenceConnector.readString(svContext, PreferenceConnector.LOGINUSERPROFILEPIC, "");
        if(imageUrl!="") {
            try {
                ImageLoading.loadImages(imageUrl, imgAvatar, R.drawable.users);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        new NoInternetScreen(svContext, root, ActivityChangePassword.this);
        if (!GlobalVariables.CUSTOMFONTNAME.equals("")) {
            Typeface font = Typeface.createFromAsset(getAssets(), GlobalVariables.CUSTOMFONTNAME);
            FontUtils.setFont(root, font);
        }
        hideKeyboard();
        loadToolBar();
    }

    private void loadToolBar() {
        ImageView imgToolBarBack = (ImageView) findViewById(R.id.img_back);
        imgToolBarBack.setOnClickListener(view -> onBackPressed());
        TextView txtHeading = (TextView) findViewById(R.id.heading);
        txtHeading.setText("Change Password");
    }

    private void hideKeyboard() {
        InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
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

    public static final String TAG_MESSAGE = "message";
    public static final String TAG_STATUS = "status";
    @Override
    public void onWebServiceActionComplete(String result, String url) {
        if (url.contains(ApiInterface.CHANGEPASSWORD)) {
            try {
                JSONObject json = new JSONObject(result);

                String str_message = json.getString(TAG_MESSAGE);
                String str_status = json.getString(TAG_STATUS);

                if (str_status.equalsIgnoreCase("0")) {
                    customToast.showCustomToast(svContext, str_message, customToast.ToastyError);
                } else {
                    onBackPressed();
                    customToast.showCustomToast(svContext, str_message, customToast.ToastySuccess);
                }
            } catch (JSONException e) {
                customToast.showCustomToast(svContext, "Some error occured", customToast.ToastyError);
                e.printStackTrace();
            }
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

    @Override
    public void onWebServiceError(String result, String url) {
        customToast.showCustomToast(svContext, result, customToast.ToastyError);
    }

    @Override
    public void onBackPressed() {
        hideKeyboard();
        super.onBackPressed();
    }
}