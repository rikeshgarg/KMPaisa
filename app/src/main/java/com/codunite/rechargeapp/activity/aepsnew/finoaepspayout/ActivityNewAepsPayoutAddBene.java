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
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.codunite.commonutility.retrofit.ApiInterface;
import com.codunite.commonutility.spinner.ActivitySpinner;
import com.codunite.commonutility.spinner.SpinnerModel;
import com.codunite.rechargeapp.R;
import com.codunite.commonutility.CheckValidation;
import com.codunite.commonutility.GlobalVariables;
import com.codunite.commonutility.NoInternetScreen;
import com.codunite.commonutility.PreferenceConnector;
import com.codunite.commonutility.ShowCustomToast;
import com.codunite.commonutility.WebService;
import com.codunite.commonutility.WebServiceListener;
import com.codunite.commonutility.customfont.FontUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ActivityNewAepsPayoutAddBene extends AppCompatActivity implements View.OnClickListener, WebServiceListener {
    private EditText edbal, edAccountHolderName, edAccountNo, edIfsc;
    private Button btnSubmitMem, btnCancel;
    private ImageView imgDrop, imgDropOne, imgDropTwo;
    private View[] allViewWithClick = {btnSubmitMem, btnCancel};
    private int[] allViewWithClickId = {R.id.btn_SubmitMem, R.id.btnCancel};

    private EditText[] edTexts = {edbal, edAccountHolderName, edAccountNo, edIfsc};
    private String[] edTextsError = {"Enter Current Bal", "Enter account holder name", /*"Enter transaction password",*/ "Enter account no", "Enter ifsc"};
    private int[] editTextsClickId = {R.id.edtcurrentwalletbal, R.id.edt_amount_transfer, /*R.id.edt_transaction_pass,*/ R.id.edt_acc_no, R.id.edt_ifsc};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_new_aepspayout_addbene);
        StartApp();

        OnClickCombineDeclare(allViewWithClick);
        EditTextDeclare(edTexts);
        ((EditText) findViewById(R.id.edtcurrentwalletbal)).setText(PreferenceConnector.readString(svContext, PreferenceConnector.EWALLETBAL, ""));
        resumeApp();
    }

    private RadioGroup rgTxtType;

    private TextView tvSelectBank;
    private SpinnerModel selectedSpinnerBank;
    private List<SpinnerModel> listBankList;
    private static final int REQUEST_CODE_SELECT_BANK = 435;

    public void resumeApp() {
        rgTxtType = (RadioGroup) findViewById(R.id.rg_txttype);
        tvSelectBank = findViewById(R.id.txt_bank);

        RelativeLayout layBankSpinner = findViewById(R.id.lay_bankspinner);
        layBankSpinner.setOnClickListener(v -> {
            ActivitySpinner.showSpinner(svContext, listBankList, "Select Bank", REQUEST_CODE_SELECT_BANK);
        });

        lstUploadData = new LinkedList<>();
        lstUploadData.add(PreferenceConnector.readString(svContext, PreferenceConnector.LOGINEDUSERID, ""));
        callWebService(ApiInterface.NEWAEPSBANKLIST, lstUploadData);
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
        if (response == 0 && selectedSpinnerBank == null) {
            response++;
            customToast.showCustomToast(svContext, "Please select bank", customToast.ToastyError);
        }

        if (response == 0) {
            lstUploadData = new LinkedList<>();
            lstUploadData.add(PreferenceConnector.readString(svContext, PreferenceConnector.LOGINEDUSERID, ""));
            lstUploadData.add(edAccountHolderName.getText().toString().trim());
            lstUploadData.add(selectedSpinnerBank.getId());
            lstUploadData.add(edAccountNo.getText().toString().trim());
            lstUploadData.add(edIfsc.getText().toString().trim());
            callWebService(ApiInterface.NEWAEPS_BENEAUTH, lstUploadData);
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
                        //    case R.id.img_drop:
                        //        spinnerMemberPosition.performClick();
                        //        break;
                        //    case R.id.img_drop_1:
                        //      spinnercountrylist.performClick();
                        //       break;
                        //    case R.id.img_drop_2:
                        //      spinnerstatelist.performClick();
                        //        break;
                    }
                }
            });
        }
    }

    private void EditTextDeclare(EditText[] editTexts) {
        for (int j = 0; j < editTexts.length; j++) {
            editTexts[j] = findViewById(editTextsClickId[j]);
        }
        edbal = (EditText) editTexts[0];
        edAccountHolderName = (EditText) editTexts[1];
        //edPassword = (EditText) editTexts[2];
        edAccountNo = (EditText) editTexts[2];
        edIfsc = (EditText) editTexts[3];
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                hideKeyboard();
                finish();
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
        new NoInternetScreen(svContext, root, ActivityNewAepsPayoutAddBene.this);
        if (!GlobalVariables.CUSTOMFONTNAME.equals("")) {
            Typeface font = Typeface.createFromAsset(getAssets(), GlobalVariables.CUSTOMFONTNAME);
            FontUtils.setFont(root, font);
        }
//        if (PreferenceConnector.readBoolean(svContext, PreferenceConnector.ISDARKTHEME, false)) {
//            FontUtils.setThemeColor(root, svContext, true);
//        } else {
//            FontUtils.setThemeColor(root, svContext, false);
//        }
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
    public static final String TAG_MESSAGE = "message";
    public static final String TAG_STATUS = "status";
    private List<String> listcountry = new ArrayList<>();

    @Override
    public void onWebServiceActionComplete(String result, String url) {
        if (url.contains(ApiInterface.GETCOUNTRYLIST)) {
            try {
                JSONObject json = new JSONObject(result);
                listcountry = new ArrayList<>();
                listcountry.add("-1" + "#:#" + "Select Country");

                String str_status = json.getString(TAG_STATUS);
                String str_msg = json.getString(TAG_MESSAGE);
                if (str_status.equalsIgnoreCase("1")) {
                    JSONArray data = json.getJSONArray(TAG_DATA);
                    for (int data_i = 0; data_i < data.length(); data_i++) {
                        JSONObject data_obj = data.getJSONObject(data_i);
                        String str_country_code = data_obj.getString(TAG_COUNTRY_CODE);
                        String str_country_name = data_obj.getString(TAG_COUNTRY_NAME);

                        listcountry.add(str_country_code + "#:#" + str_country_name);
                    }

                    //PopulateCountry(listcountry);
                } else {
                    customToast.showCustomToast(svContext, str_msg, customToast.ToastyError);
                }
            } catch (JSONException e) {
                customToast.showCustomToast(svContext, "Some error occured", customToast.ToastyError);
                e.printStackTrace();
            }
        } else if (url.contains(ApiInterface.NEWAEPS_BENEAUTH)) {
            try {
                JSONObject json = new JSONObject(result);

                String str_status = json.getString("status");
                String str_msg = json.getString("message");
                if (str_status.equalsIgnoreCase("0")) {
                    customToast.showCustomToast(svContext, str_msg, customToast.ToastyError);
                } else if (str_status.equalsIgnoreCase("2")) {
                    customToast.showCustomToast(svContext, str_msg, customToast.ToastySuccess);
                    String strBene = json.getString("bene_id");
                    String strRedirectUrl = json.getString("redirect_url");

                    Intent intent = new Intent(svContext, ActivityNewPayoutVerifyDoc.class);
                    intent.putExtra("str_benId", strBene);
                    intent.putExtra("redirect_url", strRedirectUrl);
                    svContext.startActivity(intent);
                    finish();
                } else {
                    customToast.showCustomToast(svContext, str_msg, customToast.ToastySuccess);
                    finish();
                }
            } catch (JSONException e) {
                customToast.showCustomToast(svContext, "Some error occured", customToast.ToastyError);
                e.printStackTrace();
            }
        }
        if (url.contains(ApiInterface.NEWAEPSBANKLIST)) {
            try {
                JSONObject json = new JSONObject(result);
                listBankList = new ArrayList<>();

                String str_status = json.getString("status");
                String str_msg = json.getString(TAG_MESSAGE);
                if (str_status.equalsIgnoreCase("1")) {
                    JSONArray data = json.getJSONArray("data");
                    for (int data_i = 0; data_i < data.length(); data_i++) {
                        JSONObject data_obj = data.getJSONObject(data_i);
                        String str_country_code = data_obj.getString("id");
                        String str_country_name = data_obj.getString("bank_name");

                        listBankList.add(new SpinnerModel(str_country_code, str_country_name, ""));
                    }
                } else {
                    customToast.showCustomToast(svContext, str_msg, customToast.ToastyError);
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
        txtHeading.setText("Add Fino AEPS Settlement Beneficiary");
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == RESULT_OK) {
            Bundle extras = intent.getExtras();
            if (extras == null) return;
            switch (requestCode) {
                case REQUEST_CODE_SELECT_BANK:
                    selectedSpinnerBank = (SpinnerModel) intent.getSerializableExtra(ActivitySpinner.EXTRA_SPINNER_DATA);
                    if (selectedSpinnerBank == null) {
                        tvSelectBank.setText("Select Bank");
                    } else {
                        tvSelectBank.setText(selectedSpinnerBank.getTitle());
                    }
                default:
                    break;
            }
        }
    }

}