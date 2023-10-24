package com.rechargeapp.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.commonutility.CheckInternet;
import com.commonutility.CheckValidation;
import com.commonutility.GlobalData;
import com.commonutility.GlobalVariables;
import com.commonutility.MessageListener;
import com.commonutility.NoInternetScreen;
import com.commonutility.PreferenceConnector;
import com.commonutility.ShowCustomToast;
import com.commonutility.WebService;
import com.commonutility.WebServiceListener;
import com.commonutility.customfont.FontUtils;
import com.commonutility.retrofit.ApiInterface;
import com.commonutility.spinner.ActivitySpinner;
import com.commonutility.spinner.SpinnerModel;
import com.codunite.rechargeapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ActivityAddBeneficiary extends AppCompatActivity implements View.OnClickListener, WebServiceListener, MessageListener {
    private Context svContext;
    private ShowCustomToast customToast;
    private CheckInternet checkNetwork;
    private NoInternetScreen errrorScreen;
    private EditText edName, edPhone, edAccountNumber, edIfsc;
    private Button btnVerifySubmit, btnSubmit, btnsucessfulregister;
    private String strDefaultIfsc = "";
    private CheckBox checkboxDefaultIfsc;
    private TextView goback;
    private RelativeLayout layBankname;

    private View[] allViewWithClick = {btnVerifySubmit, btnSubmit, btnsucessfulregister, goback, layBankname};
    private int[] allViewWithClickId = {R.id.btn_verifysubmit, R.id.btnSubmit, R.id.btn_transaction_password, R.id.goback, R.id.lay_bankname};

    private EditText[] edTexts = {edName, edPhone, edAccountNumber, edIfsc};
    private String[] edTextsError = {"Enter account holder name", "Enter mobile number", "Enter account number", "Enter Ifsc Code"};
    private int[] editTextsClickId = {R.id.edt_name, R.id.edt_mobile, R.id.edt_account_number, R.id.edt_ifsc};
    private String loadCurrentUrl;
    private CardView layoutTransactionpassword;
    private LinearLayout LayRegistertranfer;
    private ImageView imgToolBarBack;
    private EditText edTranPwd;
    private static final int REQUEST_CODE_BANK = 435;
    private int isVerify = 0;
    private TextView txtbankName;
    private SpinnerModel selectedBankSpinner;
    private List<SpinnerModel> listSpinnerBankList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_addbeneficery_transfer);
        StartApp();

        //Start Coding from here
        OnClickCombineDeclare(allViewWithClick);
        EditTextDeclare(edTexts);

        resumeApp();
    }

    public void resumeApp() {
        loadCurrentUrl = getIntent().getExtras().get("logintransfer").toString();
        layoutTransactionpassword = (CardView) findViewById(R.id.layout_transactionpassword);
        LayRegistertranfer = findViewById(R.id.linearLayoutReg);
        ShowRegisterScreen();

        checkboxDefaultIfsc = findViewById(R.id.checkbox_default_ifsc);
        checkboxDefaultIfsc.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                edIfsc.setText(strDefaultIfsc);
                edIfsc.setEnabled(false);
            } else {
                edIfsc.setText("");
                edIfsc.setEnabled(true);
            }
        });

        lstUploadData = new LinkedList<>();
        lstUploadData.add(PreferenceConnector.readString(svContext, PreferenceConnector.LOGINEDUSERID, ""));
        callWebService(ApiInterface.DMT_BANK_LIST, lstUploadData);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == RESULT_OK) {
            Bundle extras = intent.getExtras();
            if (extras == null) return;
            switch (requestCode) {
                case REQUEST_CODE_BANK:
                    int pos2 = intent.getIntExtra(ActivitySpinner.EXTRA_SPINNER_POSITION, 0);
                    selectedBankSpinner = (SpinnerModel) intent.getSerializableExtra(ActivitySpinner.EXTRA_SPINNER_DATA);
                    if (selectedBankSpinner == null) {
                        txtbankName.setText("Select Bank");
                    } else {
                        txtbankName.setText(selectedBankSpinner.getTitle());
                        strDefaultIfsc = selectedBankSpinner.getStrBillerAliasName();
                        edIfsc.setText(strDefaultIfsc);
                        edIfsc.setEnabled(false);
                        checkboxDefaultIfsc.setChecked(true);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    private void ShowOTPScreen() {
        LayRegistertranfer.setVisibility(View.GONE);
        btnSubmit.setVisibility(View.GONE);
        btnVerifySubmit.setVisibility(View.GONE);
        layoutTransactionpassword.setVisibility(View.VISIBLE);
    }

    private void ShowRegisterScreen() {
        LayRegistertranfer.setVisibility(View.VISIBLE);
        btnSubmit.setVisibility(View.VISIBLE);
        btnVerifySubmit.setVisibility(View.VISIBLE);
        layoutTransactionpassword.setVisibility(View.GONE);
    }

    private void SubmitSignUpForm() {
        int response = 0;
        response = CheckValidation.emptyEditTextError(edTexts, edTextsError);

        if (selectedBankSpinner == null) {
            response++;
            customToast.showCustomToast(svContext, "Please select bank", customToast.ToastyError);
        }

        if ((edPhone.getText().toString().trim()).length() != 10) {
            response++;
            edPhone.setError("Invalid mobile number");
        }

        if (response == 0) {
            RegisterUser();
        }
    }

    private void RegisterUser() {
        lstUploadData = new LinkedList<>();
        lstUploadData.add(PreferenceConnector.readString(svContext, PreferenceConnector.LOGINEDUSERID, ""));
        lstUploadData.add(loadCurrentUrl);
        lstUploadData.add(getEditextValue(edName));
        lstUploadData.add(getEditextValue(edPhone));
        lstUploadData.add(getEditextValue(edAccountNumber));
        lstUploadData.add(getEditextValue(edIfsc));
        lstUploadData.add(selectedBankSpinner.getId());
        lstUploadData.add("" + isVerify);
        callWebService(ApiInterface.ADDTRANSFERBENEFICERY, lstUploadData);
    }

    private void loadToolBar() {
        imgToolBarBack = (ImageView) findViewById(R.id.img_back);
        imgToolBarBack.setOnClickListener(this);

        TextView txtHeading = (TextView) findViewById(R.id.heading);
        txtHeading.setText("Beneficiary Details");

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


    private void OnClickCombineDeclare(View[] allViewWithClick) {
        for (int j = 0; j < allViewWithClick.length; j++) {
            allViewWithClick[j] = findViewById(allViewWithClickId[j]);
            allViewWithClick[j].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.img_back:
                            onBackPressed();
                            break;
                        case R.id.btn_verifysubmit:
                            hideKeyboard();
                            isVerify = 1;
                            SubmitSignUpForm();
                            break;
                        case R.id.btnSubmit:
                            hideKeyboard();
                            isVerify = 0;
                            SubmitSignUpForm();
                            break;
                        case R.id.goback:
                            ActivityBeneficiaryList.OpenBeneficirayList(svContext, loadCurrentUrl);
                            break;
                        case R.id.btn_transaction_password:
                            FinalStart();
                            break;
                        case R.id.lay_bankname:
                            ActivitySpinner.showSpinner(svContext, listSpinnerBankList, "Select Bank", REQUEST_CODE_BANK);
                            break;
                    }
                }
            });
        }
        btnSubmit = findViewById(R.id.btnSubmit);
        btnVerifySubmit = findViewById(R.id.btn_verifysubmit);
    }

    private void FinalStart() {
        edTranPwd = (EditText) findViewById(R.id.edt_transactionpassword);
        int response = 0;
        response = CheckValidation.emptyEditTextError(
                new EditText[]{edTranPwd},
                new String[]{"enter transcation password"});

        if (response == 0) {
            lstUploadData = new LinkedList<>();
            lstUploadData.add(PreferenceConnector.readString(svContext, PreferenceConnector.LOGINEDUSERID, ""));
            lstUploadData.add(strEncodeLoginCode);
            lstUploadData.add(loadCurrentUrl);
            lstUploadData.add(edTranPwd.getText().toString().trim());
            callWebService(ApiInterface.BENEFICIARYOTPAUTH, lstUploadData);
        }
    }

    private void EditTextDeclare(EditText[] editTexts) {
        for (int j = 0; j < editTexts.length; j++) {
            editTexts[j] = findViewById(editTextsClickId[j]);
        }
        edName = (EditText) editTexts[0];
        edPhone = (EditText) editTexts[1];
        edAccountNumber = (EditText) editTexts[2];
        edIfsc = (EditText) editTexts[3];
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.termscondition:
//                break;
            case R.id.img_back:
                onBackPressed();
                break;
            default:
                break;
        }
    }

    private void StartApp() {
        svContext = this;
        customToast = new ShowCustomToast(svContext);
        checkNetwork = new CheckInternet(svContext);
        ViewGroup root = (ViewGroup) findViewById(R.id.headlayout);
        errrorScreen = new NoInternetScreen(svContext, root, ActivityAddBeneficiary.this);
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

        txtbankName = findViewById(R.id.txt_bankname);
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

    @Override
    public void messageReceived(String message) {
        Toast.makeText(this, "New Message Received: " + message, Toast.LENGTH_SHORT).show();
    }

    LinkedList<String> lstUploadData = new LinkedList<>();

    private void callWebService(String postUrl, LinkedList<String> lstUploadData) {
        WebService webService = new WebService(svContext, postUrl, lstUploadData, this);
        webService.LoadDataRetrofit(webService.callReturn());
    }

    String strEncodeLoginCode = "";
    public static final String TAG_USER_DATA = "user_data";
    public static final String TAG_USER_ID = "user_id";

    @Override
    public void onWebServiceActionComplete(String result, String url) {
        System.out.println(result + ".........jsonresponse....." + url);
        if (url.contains(ApiInterface.DMT_BANK_LIST)) {
            try {
                listSpinnerBankList = new ArrayList<>();
                JSONObject json = new JSONObject(result);

                String str_status = json.getString("status");
                String str_message = json.getString("message");

                if (str_status.equalsIgnoreCase("1")) {
                    JSONArray data = json.getJSONArray("data");
                    for (int data_i = 0; data_i < data.length(); data_i++) {
                        JSONObject data_obj = data.getJSONObject(data_i);
                        String str_code = data_obj.getString("bankId");
                        String str_name = data_obj.getString("bankName");
                        String default_ifsc = data_obj.getString("default_ifsc");

                        listSpinnerBankList.add(new SpinnerModel(str_code, str_name, default_ifsc, ""));
                    }
                } else {
                    customToast.showCustomToast(svContext, str_message, customToast.ToastyError);
                }
            } catch (JSONException e) {
                customToast.showCustomToast(svContext, "Some error occured", customToast.ToastyError);
                e.printStackTrace();
            }

        } else if (url.contains(ApiInterface.ADDTRANSFERBENEFICERY)) {
            try {
                JSONObject json = new JSONObject(result);

                String str_status = json.getString("status");
                String str_msg = json.getString("message");
                if (str_status.equalsIgnoreCase("0")) {
                    customToast.showCustomToast(svContext, str_msg, customToast.ToastyError);
                } else {
                    customToast.showCustomToast(svContext, str_msg, customToast.ToastySuccess);
                    TextView txtMemberId = (TextView) findViewById(R.id.txt_loginerror);
                    txtMemberId.setText(str_msg);
                    EmptyData(edTexts);
                    strEncodeLoginCode = json.getString("mobile");
                    //ShowOTPScreen();
                    finish();
                }
            } catch (JSONException e) {
                customToast.showCustomToast(svContext, "Some error occured", customToast.ToastyError);
                e.printStackTrace();
            }
        } else if (url.contains(ApiInterface.BENEFICIARYOTPAUTH)) {
            try {
                JSONObject json = new JSONObject(result);
                String str_status = json.getString("status");
                String str_msg = json.getString("message");
                if (str_status.equalsIgnoreCase("0")) {
                    customToast.showCustomToast(svContext, str_msg, customToast.ToastyError);
                } else {

                    customToast.showCustomToast(svContext, "Beneficiary Successfully Added", customToast.ToastySuccess);
                    ActivityBeneficiaryList.OpenBeneficirayList(svContext, loadCurrentUrl);
                    finish();

                }
            } catch (JSONException e) {
                customToast.showCustomToast(svContext, "Some error occured", customToast.ToastyError);
                e.printStackTrace();
            }
        }

    }

    private void EmptyData(EditText[] edTexts) {
        for (int i = 0; i < edTexts.length; i++) {
            edTexts[i].setText("");
        }
    }

    private EditText getEditext(EditText editText) {
        for (int i = 0; i < edTexts.length; i++) {
            if (editText == edTexts[i]) {
                return edTexts[i];
            }
        }
        return new EditText(svContext);
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