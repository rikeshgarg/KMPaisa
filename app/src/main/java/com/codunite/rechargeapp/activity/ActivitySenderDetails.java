package com.codunite.rechargeapp.activity;

import android.app.DatePickerDialog;
import android.content.Context;
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

import com.codunite.commonutility.CheckInternet;
import com.codunite.commonutility.CheckValidation;
import com.codunite.commonutility.GlobalData;
import com.codunite.commonutility.GlobalVariables;
import com.codunite.commonutility.MessageListener;
import com.codunite.commonutility.NoInternetScreen;
import com.codunite.commonutility.PreferenceConnector;
import com.codunite.commonutility.ShowCustomToast;
import com.codunite.commonutility.WebService;
import com.codunite.commonutility.WebServiceListener;
import com.codunite.commonutility.customfont.FontUtils;
import com.codunite.commonutility.retrofit.ApiInterface;
import com.codunite.rechargeapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.LinkedList;

public class ActivitySenderDetails extends AppCompatActivity implements View.OnClickListener, WebServiceListener, MessageListener {
    private EditText edFirstName, edLastname, edAddress, edPincode;
    private Button btnSubmit;
    private TextView txtDob;
    private String loadCurrentUrl;
    Calendar myCalendar;
    private View[] allViewWithClick = {btnSubmit};
    private int[] allViewWithClickId = {R.id.btnSubmit};

    private EditText[] edTexts = {edFirstName, edLastname, edAddress, edPincode};
    private String[] edTextsError = {"Enter first name", "Enter last anme", "Enter address", "Enter PinCode"};
    private int[] editTextsClickId = {R.id.ed_first_name, R.id.ed_last_name, R.id.ed_address, R.id.ed_pincode};
    private LinearLayout LayRegistertranfer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_update_sender_details);
        StartApp();
        //Start Coding from here
        OnClickCombineDeclare(allViewWithClick);
        EditTextDeclare(edTexts);
        resumeApp();
    }



    public void resumeApp() {
        loadCurrentUrl = getIntent().getExtras().get("logintransfer").toString();
        LayRegistertranfer = findViewById(R.id.linearLayoutReg);
        myCalendar = Calendar.getInstance();
        txtDob = findViewById(R.id.txtDob);
        txtDob.setHint("Select Dob");
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
        lstUploadData = new LinkedList<>();
        lstUploadData.add(PreferenceConnector.readString(svContext, PreferenceConnector.LOGINEDUSERID, ""));
        lstUploadData.add(loadCurrentUrl);
        callWebService(ApiInterface.GET_SENDER_DETAILS, lstUploadData);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void SubmitSignUpForm() {
        int response = 0;
        response = CheckValidation.emptyEditTextError(edTexts, edTextsError);

        if ((txtDob.getText().toString().trim()).equals("Select Dob") || (txtDob.getText().toString().trim()).equals("")) {
            response++;
            customToast.showCustomToast(svContext, "Please select dob", customToast.ToastyError);
        }

        if (response == 0) {
            RegisterUser();
        }
    }

    private void RegisterUser() {
        lstUploadData = new LinkedList<>();
        lstUploadData.add(PreferenceConnector.readString(svContext, PreferenceConnector.LOGINEDUSERID, ""));
        lstUploadData.add(loadCurrentUrl);
        lstUploadData.add(getEditextValue(edFirstName));
        lstUploadData.add(getEditextValue(edLastname));
        lstUploadData.add(txtDob.getText().toString().trim());
        lstUploadData.add(getEditextValue(edAddress));
        lstUploadData.add(getEditextValue(edPincode));
        callWebService(ApiInterface.UPDATE_SENDER_DETAILS, lstUploadData);
    }

    private void loadToolBar() {
        ImageView imgToolBarBack = (ImageView) findViewById(R.id.img_back);
        imgToolBarBack.setOnClickListener(this);

        TextView txtHeading = (TextView) findViewById(R.id.heading);
        txtHeading.setText("Update Personal Detail");

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
                        case R.id.btnSubmit:
                            SubmitSignUpForm();
                            break;
                    }
                }
            });
        }
        btnSubmit = (Button) allViewWithClick[0];
    }

    private void EditTextDeclare(EditText[] editTexts) {
        for (int j = 0; j < editTexts.length; j++) {
            editTexts[j] = findViewById(editTextsClickId[j]);
        }
        edFirstName = (EditText) editTexts[0];
        edLastname = (EditText) editTexts[1];
        edAddress = (EditText) editTexts[2];
        edPincode = (EditText) editTexts[3];
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

    private Context svContext;
    private ShowCustomToast customToast;
    private CheckInternet checkNetwork;
    private NoInternetScreen errrorScreen;

    private void StartApp() {
        svContext = this;
        customToast = new ShowCustomToast(svContext);
        checkNetwork = new CheckInternet(svContext);
        ViewGroup root = (ViewGroup) findViewById(R.id.headlayout);
        errrorScreen = new NoInternetScreen(svContext, root, ActivitySenderDetails.this);
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

    @Override
    public void onWebServiceActionComplete(String result, String url) {
        System.out.println(result + ".........jsonresponse....." + url);
        if (url.contains(ApiInterface.GET_SENDER_DETAILS)) {
            try {
                JSONObject json = new JSONObject(result);
                String str_status = json.getString("status");
                String str_message = json.getString("message");
                if (str_status.equalsIgnoreCase("1")) {
                    JSONObject data = json.getJSONObject("data");
                    edFirstName.setText(data.getString("first_name"));
                    edLastname.setText(data.getString("last_name"));
                    txtDob.setText(data.getString("dob"));
                    edAddress.setText(data.getString("address"));
                    edPincode.setText(data.getString("pin_code"));
                }
            } catch (JSONException e) {
                customToast.showCustomToast(svContext, "Some error occured", customToast.ToastyError);
                e.printStackTrace();
            }

        } else if (url.contains(ApiInterface.UPDATE_SENDER_DETAILS)) {
            try {
                JSONObject json = new JSONObject(result);
                String str_status = json.getString("status");
                String str_message = json.getString("message");
                if (str_status.equalsIgnoreCase("1")) {
                    customToast.showCustomToast(svContext, str_message, customToast.ToastySuccess);
                    finish();
                } else {
                    customToast.showCustomToast(svContext, str_message, customToast.ToastyError);
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