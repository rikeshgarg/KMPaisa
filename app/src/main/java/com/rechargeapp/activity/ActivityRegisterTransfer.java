package com.rechargeapp.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
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
import com.codunite.rechargeapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.LinkedList;


public class ActivityRegisterTransfer extends AppCompatActivity implements View.OnClickListener, WebServiceListener, MessageListener {

    private EditText edFName, edLName, edPhone, edAddress, edPincode;
    private AppCompatButton btnBack, btnRegister;
            Button btnsucessfulregister;
    private TextView goback, tvlogin, txtDob;

    private View[] allViewWithClick = {btnBack, btnRegister, btnsucessfulregister, goback, tvlogin};
    private int[] allViewWithClickId = {R.id.btn_loginIn, R.id.btnSignUp, R.id.btn_transaction_password, R.id.goback, R.id.tv_login};

    private EditText[] edTexts = {edFName, edLName, edPhone, edAddress, edPincode};
    private String[] edTextsError = {"Enter first name", "Enter last name", "Enter mobile number", "Enter address", "Enter pincode"};
    private int[] editTextsClickId = {R.id.edt_fname, R.id.edt_lname, R.id.edt_mobile, R.id.edt_address, R.id.edt_pincode};
    private CardView layoutTransactionpassword;
    private LinearLayout LayRegistertranfer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_register_transfer);
        StartApp();

        //Start Coding from here
        OnClickCombineDeclare(allViewWithClick);
        EditTextDeclare(edTexts);

        resumeApp();
    }

    Calendar myCalendar;

    public void resumeApp() {
        myCalendar = Calendar.getInstance();

        txtDob = findViewById(R.id.txtDob);
        txtDob.setHint("Select DOB");

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                int month = monthOfYear + 1;
                String selectedDate = year + "-" + (month >= 10 ? month : "0" + month)
                        + "-" + (dayOfMonth >= 10 ? dayOfMonth : "0" + dayOfMonth);
                txtDob.setText(selectedDate);
            }
        };

        txtDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (TextUtils.isEmpty(txtDob.getText().toString()) || txtDob.getText().toString().trim().equals("Select Dob")) {
                        new DatePickerDialog(svContext, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DATE)).show();
                    } else {
                        String[] strDate = txtDob.getText().toString().trim().split("-");
                        new DatePickerDialog(svContext, date,
                                Integer.parseInt(strDate[0]),
                                Integer.parseInt(strDate[1]) - 1,
                                Integer.parseInt(strDate[2])).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    new DatePickerDialog(svContext, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DATE)).show();
                }
            }
        });

        layoutTransactionpassword = (CardView) findViewById(R.id.layout_transactionpassword);
        LayRegistertranfer = findViewById(R.id.linearLayoutReg);
        ShowRegisterScreen();
    }

    private void ShowRegisterScreen() {
        LayRegistertranfer.setVisibility(View.VISIBLE);
        btnRegister.setVisibility(View.VISIBLE);
        btnBack.setVisibility(View.VISIBLE);
        layoutTransactionpassword.setVisibility(View.GONE);
    }

    private void ShowOTPScreen() {
        LayRegistertranfer.setVisibility(View.GONE);
        btnRegister.setVisibility(View.GONE);
        btnBack.setVisibility(View.GONE);
        layoutTransactionpassword.setVisibility(View.VISIBLE);
    }

    private void SubmitSignUpForm() {
        int response = 0;
        response = CheckValidation.emptyEditTextError(edTexts, edTextsError);

        if ((txtDob.getText().toString().trim()).equals("Select Dob") || (txtDob.getText().toString().trim()).equals("")) {
            response++;
            customToast.showCustomToast(svContext, "Please select dob", customToast.ToastyError);
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
        lstUploadData.add(getEditextValue(edFName));
        lstUploadData.add(getEditextValue(edLName));
        lstUploadData.add(getEditextValue(edPhone));
        lstUploadData.add(txtDob.getText().toString().trim());
        lstUploadData.add(getEditextValue(edAddress));
        lstUploadData.add(getEditextValue(edPincode));
        callWebService(ApiInterface.REGISTERTRANSFER, lstUploadData);
    }

    private ImageView imgToolBarBack;

    private void loadToolBar() {
        imgToolBarBack = (ImageView) findViewById(R.id.img_back);
        imgToolBarBack.setOnClickListener(this);

        TextView txtHeading = (TextView) findViewById(R.id.heading);
        txtHeading.setText("Register User");

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
                        case R.id.btn_loginIn:
                            onBackPressed();
                            break;
                        case R.id.btnSignUp:
                            SubmitSignUpForm();
                            break;
                        case R.id.goback:
                            Intent svIntent = new Intent(svContext, ActivityRegisterTransfer.class);
                            startActivity(svIntent);
                            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                            break;
                        case R.id.tv_login:
                            openLoginActivity();
                            break;
                        case R.id.btn_transaction_password:
                            FinalStart();
                            break;
                    }
                }
            });
        }
        btnRegister = findViewById(R.id.btnSignUp);
        btnBack = findViewById(R.id.btn_loginIn);
    }

    private EditText edTranPwd;

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
            lstUploadData.add(edTranPwd.getText().toString().trim());
            callWebService(ApiInterface.MONEYTRANSFERREGISTEROTP, lstUploadData);
        }
    }

    private void EditTextDeclare(EditText[] editTexts) {
        for (int j = 0; j < editTexts.length; j++) {
            editTexts[j] = findViewById(editTextsClickId[j]);
        }
        edFName = (EditText) editTexts[0];
        edLName = (EditText) editTexts[1];
        edPhone = (EditText) editTexts[2];
        edAddress = (EditText) editTexts[3];
        edPincode = (EditText) editTexts[4];
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {


            case R.id.img_back:
                finish();
                break;

//            case R.id.termscondition:
//                break;
            default:
                break;
        }
    }

    private Context svContext;
    private ShowCustomToast customToast;
    private CheckInternet checkNetwork;
    private NoInternetScreen errrorScreen;

    private void StartApp() {
        svContext = this;
        customToast = new ShowCustomToast(svContext);
        checkNetwork = new CheckInternet(svContext);
        ViewGroup root = (ViewGroup) findViewById(R.id.headlayout);
        errrorScreen = new NoInternetScreen(svContext, root, ActivityRegisterTransfer.this);
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
        if (url.contains(ApiInterface.REGISTERTRANSFER)) {
            try {
                JSONObject json = new JSONObject(result);
                String str_status = json.getString("status");
                String str_msg = json.getString("message");
                if (str_status.equalsIgnoreCase("1")) {
                    customToast.showCustomToast(svContext, str_msg, customToast.ToastySuccess);
                    ((TextView) findViewById(R.id.txt_loginerror)).setText(str_msg);
                    EmptyData(edTexts);
                    strEncodeLoginCode = json.getString("token");
                    ShowOTPScreen();

                } else if (str_status.equalsIgnoreCase("2")) {
                    customToast.showCustomToast(svContext, str_msg, customToast.ToastySuccess);
                    openLoginActivity();
                } else {
                    customToast.showCustomToast(svContext, str_msg, customToast.ToastyError);
                }
            } catch (JSONException e) {
                customToast.showCustomToast(svContext, "Some error occured", customToast.ToastyError);
                e.printStackTrace();
            }
        } else if (url.contains(ApiInterface.MONEYTRANSFERREGISTEROTP)) {
            try {
                JSONObject json = new JSONObject(result);

                String str_status = json.getString("status");
                String str_msg = json.getString("message");
                if (str_status.equalsIgnoreCase("0")) {
                    customToast.showCustomToast(svContext, str_msg, customToast.ToastyError);
                } else {
                    customToast.showCustomToast(svContext, str_msg, customToast.ToastySuccess);
                    openLoginActivity();
                }
            } catch (JSONException e) {
                customToast.showCustomToast(svContext, "Some error occured", customToast.ToastyError);
                e.printStackTrace();
            }
        }
    }

    public void openLoginActivity() {
        Intent svIntent = new Intent(svContext, ActivityLoginTransfer.class);
        startActivity(svIntent);
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
        finish();
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