package com.codunite.rechargeapp.activity.aepsnew.finoaepspayout;

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
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.codunite.commonutility.CheckValidation;
import com.codunite.commonutility.GlobalVariables;

import com.codunite.commonutility.NoInternetScreen;
import com.codunite.commonutility.PreferenceConnector;
import com.codunite.commonutility.ShowCustomToast;
import com.codunite.commonutility.WebService;
import com.codunite.commonutility.WebServiceListener;
import com.codunite.commonutility.customfont.FontUtils;
import com.codunite.commonutility.retrofit.ApiInterface;
import com.codunite.rechargeapp.R;
import com.codunite.rechargeapp.activity.ActivityCompletion;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;

public class ActivityNewAepsWalletPayoutTransfer extends AppCompatActivity implements View.OnClickListener,
        WebServiceListener {
    private EditText edName, edAccountNo, edConfirmAccountno, edIfsc, edtxd, edAmount;
    private Button btnSubmitMem, btnCancel;
    private ImageView imgDrop, imgDropOne, imgDropTwo;
    private View[] allViewWithClick = {btnSubmitMem, btnCancel};
    private int[] allViewWithClickId = {R.id.btn_SubmitMem, R.id.btnCancel};

    private EditText[] edTexts = {edName, edAccountNo, edConfirmAccountno, edIfsc,edtxd, edAmount};
    private String[] edTextsError = {"Enter name", "Enter account no", "Enter confirm account no",
            "Enter ifsc code","Enter Transaction Password","Enter amount"};
    private int[] editTextsClickId = {R.id.edt__acc_name, R.id.edt_bank_name, R.id.edt_cnfrm_acc_no,
            R.id.edt_ifsc,R.id.edt_transaction_pass,R.id.edt_amount_transfer};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_aepswalletpayouttransfer);
        StartApp();

        //Start Coding from here
        OnClickCombineDeclare(allViewWithClick);
        EditTextDeclare(edTexts);

        resumeApp();

    }

    private RadioGroup rgTxtType;
    private Spinner spinnerBankList;
    private String str_benId;
    public void resumeApp() {
        rgTxtType = (RadioGroup) findViewById(R.id.rg_txttype);
        spinnerBankList = (Spinner) findViewById(R.id.spinner_bank_name);

        Intent intent =  getIntent();
        String str_name = intent.getStringExtra("str_name");
        String str_account_no = intent.getStringExtra("str_account_no");
        String str_ifsc = intent.getStringExtra("str_ifsc");
        String str_bank_name = intent.getStringExtra("str_bank_name");
        str_benId = intent.getStringExtra("str_benId");
        String str_date = intent.getStringExtra("str_date");

        edName.setText(str_name);
        edAccountNo.setText(str_bank_name);
        edConfirmAccountno.setText(str_account_no);
        edIfsc.setText(str_ifsc);
        edtxd.setText("");
        edAmount.setText("");

        //    spinnercountrylist = (Spinner) findViewById(R.id.spinner_countrylist);
        //    radioDistributor = (RadioButton) findViewById(R.id.radio_distributor);

        //    spinnerMemberPosition.setAdapter(LegAdapter);

        //    spinnerMemberPosition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        //        @Override
        //        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                strMemberPosition = listSpinner.get(position).split("#:#")[0];
        //         }
//
        //        @Override
        //        public void onNothingSelected(AdapterView<?> parent) {

        //        }
        //     });

    }

    private void callWebServiceWithoutLoader(String postUrl, LinkedList<String> lstUploadData) {
        WebService webService = new WebService(svContext, postUrl, lstUploadData, this, false);
        webService.LoadDataRetrofit(webService.callReturn());
    }

    private void removeFocus(EditText edTxt) {
        edTxt.clearFocus();
    }

    private void setFocus(EditText edTxt) {
        edTxt.setFocusable(true);
        edTxt.requestFocus();
    }

    private void SubmitSignUpForm() {
        int response = 0;
        response = CheckValidation.emptyEditTextError(edTexts, edTextsError);

        if (response == 0) {
            lstUploadData = new LinkedList<>();
            lstUploadData.add(PreferenceConnector.readString(svContext, PreferenceConnector.LOGINEDUSERID, ""));
            lstUploadData.add(str_benId);
            lstUploadData.add(getEditextValue(edtxd));
            lstUploadData.add(getEditextValue(edAmount));
            callWebService(ApiInterface.NEW_AEPS_PAYOUT_AUTH, lstUploadData);
        }
    }

    private void OnClickCombineDeclare(View[] allViewWithClick) {
        for (int j = 0; j < allViewWithClick.length; j++) {
            allViewWithClick[j] = findViewById(allViewWithClickId[j]);
            allViewWithClick[j].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.btn_SubmitMem:
                            SubmitSignUpForm();
                            break;
                        case R.id.btnCancel:
                            onBackPressed();
                            break;
                    }
                }
            });
        }
    }

    private void EditTextDeclare(EditText[] editTexts) {
        for (int j = 0; j < editTexts.length; j++) {
            editTexts[j] = findViewById(editTextsClickId[j]);
        }
        edName = (EditText) editTexts[0];
        edAccountNo = (EditText) editTexts[1];
        edConfirmAccountno = (EditText) editTexts[2];
        edIfsc = (EditText) editTexts[3];
        edtxd =(EditText) editTexts[4];
        edAmount = (EditText) editTexts[5];
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                onBackPressed();
                break;
            default:
                break;
        }
    }

    private Context svContext;
    private ShowCustomToast customToast;
    
    
    private void StartApp() {
        svContext = this;
        customToast = new ShowCustomToast(svContext);
        
        ViewGroup root = (ViewGroup) findViewById(R.id.headlayout);
        new NoInternetScreen(svContext, root, ActivityNewAepsWalletPayoutTransfer.this);
        if (!GlobalVariables.CUSTOMFONTNAME.equals("")) {
            Typeface font = Typeface.createFromAsset(getAssets(), GlobalVariables.CUSTOMFONTNAME);
            FontUtils.setFont(root, font);
        }
        hideKeyboard();
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

    LinkedList<String> lstUploadData = new LinkedList<>();

    private void callWebService(String postUrl, LinkedList<String> lstUploadData) {
        WebService webService = new WebService(svContext, postUrl, lstUploadData, this);
        webService.LoadDataRetrofit(webService.callReturn());
    }

    public static final String TAG_DATA = "data";
    public static final String TAG_COUNTRY_CODE = "country_code";
    public static final String TAG_COUNTRY_NAME = "country_name";
    public static final String TAG_COUNTRY_ID = "country_id";
    public static final String TAG_MESSAGE = "message";
    public static final String TAG_STATUS = "status";

    @Override
    public void onWebServiceActionComplete(String result, String url) {
        if (url.contains(ApiInterface.NEW_AEPS_PAYOUT_AUTH)) {
            try {
                JSONObject json = new JSONObject(result);

                String str_status = json.getString("status");
                String str_msg = json.getString("message");
                if (str_status.equalsIgnoreCase("0")) {
                    customToast.showCustomToast(svContext, str_msg, customToast.ToastyError);
                } else {
                    customToast.showCustomToast(svContext, str_msg, customToast.ToastySuccess);
                    Intent svIntent = new Intent(svContext, ActivityCompletion.class);
                    svIntent.putExtra("from_act", "newaepspayout");
                    startActivity(svIntent);
                    finish();
                }
            } catch (JSONException e) {
                customToast.showCustomToast(svContext, "Some error occured", customToast.ToastyError);
                e.printStackTrace();
            }
        }
    }

    private ImageView imgToolBarBack;

    private void loadToolBar() {
        imgToolBarBack = (ImageView) findViewById(R.id.img_back);
        imgToolBarBack.setOnClickListener(this);

        TextView txtHeading = (TextView) findViewById(R.id.heading);
        txtHeading.setText("Fino AEPS Settlement Transfer");
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
        super.onBackPressed();
    }
}