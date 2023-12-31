package com.rechargeapp.activity.support;

import android.content.Context;
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

import com.commonutility.retrofit.ApiInterface;
import com.commonutility.CheckValidation;
import com.commonutility.GlobalData;
import com.commonutility.GlobalVariables;
import com.commonutility.NoInternetScreen;
import com.commonutility.PreferenceConnector;
import com.commonutility.ShowCustomToast;
import com.commonutility.WebService;
import com.commonutility.WebServiceListener;
import com.commonutility.customfont.FontUtils;
import com.codunite.rechargeapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;

public class ActivityContact extends AppCompatActivity implements View.OnClickListener, WebServiceListener {
    private Button btnSubmitContact;
    private View[] allViewWithClick = {btnSubmitContact};
    private int[] allViewWithClickId = {R.id.btn_submitcontact};

    private EditText edFirstname, edEmail, edMobileNumber, edMessage;
    private EditText[] edTexts = {edFirstname, edEmail, edMobileNumber, edMessage};
    private String[] edError = {"Enter full name", "Enter email", "Enter mobile number", "Enter message"};
    private int[] editTextsClickId = {R.id.edt_firstname, R.id.edt_email, R.id.edt_mobilenumber, R.id.edt_message};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_contact);
        StartApp();

        OnClickCombineDeclare(allViewWithClick);
        EditTextDeclare(edTexts);

        resumeApp();
    }

    public void resumeApp() {
        lstUploadData = new LinkedList<>();
        lstUploadData.add(PreferenceConnector.readString(svContext, PreferenceConnector.LOGINEDUSERID, ""));
        callWebService(ApiInterface.GET_CONTACT_CONTENT, lstUploadData);
    }

    private void EditTextDeclare(EditText[] editTexts) {
        for (int j = 0; j < editTexts.length; j++) {
            editTexts[j] = findViewById(editTextsClickId[j]);
        }
        edFirstname = (EditText) editTexts[0];
        edEmail = (EditText) editTexts[1];
        edMobileNumber = (EditText) editTexts[2];
        edMessage = (EditText) editTexts[3];
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
                            if (response == 0 && (edMobileNumber.getText().toString().trim()).length() != 10) {
                                response++;
                                edMobileNumber.setError("Invalid mobile number");
                            }
                            if (response == 0 && !GlobalData.validateEmail(edEmail.getText())) {
                                response++;
                                edEmail.setError("Invalid email address");
                            }

                            if (response == 0) {
                                lstUploadData = new LinkedList<>();
                                lstUploadData.add(edFirstname.getText().toString());
                                lstUploadData.add(edEmail.getText().toString());
                                lstUploadData.add(edMobileNumber.getText().toString());
                                lstUploadData.add(edMessage.getText().toString());
                                callWebService(ApiInterface.SUBMIT_CONTACT_CONTENT, lstUploadData);
                            }
                            break;
                    }
                }
            });
        }
    }


    private Context svContext;
    private ShowCustomToast customToast;
    
    

    private void StartApp() {
        svContext = this;
        customToast = new ShowCustomToast(svContext);
        
        ViewGroup root = (ViewGroup) findViewById(R.id.headlayout);
        new NoInternetScreen(svContext, root, ActivityContact.this);
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
        txtHeading.setText(getString(R.string.toolbar_contact));
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

    public static final String TAG_MESSAGE = "message";
    public static final String TAG_STATUS = "status";
    public static final String TAG_DATA = "data";

    LinkedList<String> lstUploadData = new LinkedList<>();

    private void callWebService(String postUrl, LinkedList<String> lstUploadData) {
        WebService webService = new WebService(svContext, postUrl, lstUploadData, this);
        webService.LoadDataRetrofit(webService.callReturn());
    }

    @Override
    public void onWebServiceActionComplete(String result, String url) {
        if (url.contains(ApiInterface.GET_CONTACT_CONTENT)) {
            try {
                JSONObject json = new JSONObject(result);
                String str_status = json.getString(TAG_STATUS);
                String str_msg = json.getString(TAG_MESSAGE);
                if (str_status.equalsIgnoreCase("1")) {
                    String strAddress = json.getString("address");
                    String strMobile = json.getString("contact");
                    String strMail = json.getString("mail");
                    String strGst = json.getString("gst");
                    String strCin = json.getString("cin");

                    ((TextView) findViewById(R.id.txt_address)).setText(strAddress);
                    ((TextView) findViewById(R.id.txt_cust_email)).setText(strMail);
                    ((TextView) findViewById(R.id.txt_cust_gst)).setText("GST: " + strGst);
                    ((TextView) findViewById(R.id.txt_cust_cin)).setText("CIN: " + strCin);

                    TextView tvMobile = findViewById(R.id.txt_cust_phoneno);
                    tvMobile.setText(strMobile);
                    tvMobile.setOnClickListener(v -> GlobalData.dialCall(svContext, tvMobile.getText().toString()));
                } else {
                    customToast.showCustomToast(svContext, str_msg, customToast.ToastyError);
                }
            } catch (JSONException e) {
                customToast.showCustomToast(svContext, "Some error occured", customToast.ToastyError);
                e.printStackTrace();
            }
        } else if (url.contains(ApiInterface.SUBMIT_CONTACT_CONTENT)) {
            try {
                JSONObject json = new JSONObject(result);
                String str_status = json.getString(TAG_STATUS);
                String str_msg = json.getString(TAG_MESSAGE);
                if (str_status.equalsIgnoreCase("1")) {
                    customToast.showCustomToast(svContext, str_msg, customToast.ToastySuccess);
                    finish();
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