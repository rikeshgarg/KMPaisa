package com.rechargeapp.activity.bbps;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.commonutility.retrofit.ApiInterface;
import com.commonutility.spinner.ActivitySpinner;
import com.commonutility.spinner.SpinnerModel;
import com.codunite.rechargeapp.R;
import com.rechargeapp.activity.ActivityMain;
import com.rechargeapp.model.ParamDataModel;
import com.commonutility.CheckValidation;
import com.commonutility.GlobalVariables;
import com.commonutility.PreferenceConnector;
import com.commonutility.ShowCustomToast;
import com.commonutility.WebService;
import com.commonutility.WebServiceListener;
import com.commonutility.customfont.FontUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ActivityBbpsElectricity extends AppCompatActivity implements OnClickListener, WebServiceListener {
    private View aiView = null;
    private boolean mAlreadyLoaded = false;

//    private List<String> listSpinnerOperatorList = new ArrayList<>();
//    private List<String> listSpinnerShowData = new ArrayList<>();

    private Button btnElectricRecharge,btnFetch, btnOtpVerify, btnCancelOtp;
    private ImageView imgDropOperator;
    private TextView txtUserName;

    RelativeLayout lay_circle;

    private EditText edRechargeAmount;
    private EditText[] edRecharge = {edRechargeAmount};
    private String[] edTextsError = {"Enter recharge amount"};
    private int[] editTextDthClickId = {R.id.electricity_amount};

    private View[] allViewWithClick = {btnElectricRecharge,btnFetch, imgDropOperator, btnOtpVerify, btnCancelOtp,lay_circle};
    private int[] allViewWithClickId = {R.id.btn_electricrecharge,R.id.btn_fetch, R.id.img_drop_1, R.id.btn_otpcancel, R.id.btn_otpauth,R.id.lay_circle};

    private String str_minLength;
    private LinearLayout layBiller;
    private LinearLayout item;

    public static final String TAG_DATA = "data";
    public static final String TAG_MESSAGE = "message";
    public static final String TAG_STATUS = "status";
    TextView tv_heading;
    private EditText edtOtp;
    private TextView txtSpinnerOperatorList;

    private SpinnerModel selectedOperatorSpinner;
    private List<SpinnerModel> listSpinnerOperatorList = new ArrayList<>();
    private static final int REQUEST_CODE_OPERATOR = 435;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_bbpselectricitytoll);
        StartApp();

        OnClickCombineDeclare(allViewWithClick);
        EditTextDeclare(edRecharge);

        resumeApp();
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

    public void resumeApp() {
        edtOtp = findViewById(R.id.edt_otp);
        layBiller = (LinearLayout) findViewById(R.id.lay_biller);
        layBiller.setVisibility(View.INVISIBLE);
        item = (LinearLayout) findViewById(R.id.lay_dynamic_lay);
        txtUserName = (TextView) findViewById(R.id.txt_username);
        txtSpinnerOperatorList = findViewById(R.id.spinner_electricoperatorlist_txt);
        spinnerOperatorList = (Spinner) findViewById(R.id.spinner_electricoperatorlist);
        LoadOperatorList();

        hideOtpLayout();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void callWebServiceWithoutLoader(String postUrl, LinkedList<String> lstUploadData) {
        WebService webService = new WebService(svContext, postUrl, lstUploadData, this, false);
        webService.LoadDataRetrofit(webService.callReturn());
    }

    private void LoadOperatorList() {
        lstUploadData = new LinkedList<>();
        lstUploadData.add(PreferenceConnector.readString(svContext, PreferenceConnector.LOGINEDUSERID, ""));
        callWebService(ApiInterface.GETBBSPELECTOPERATOR, lstUploadData);
    }

    private void EditTextDeclare(EditText[] editTexts) {
        for (int j = 0; j < editTexts.length; j++) {
            editTexts[j] = findViewById(editTextDthClickId[j]);
        }
        edRechargeAmount = (EditText) editTexts[0];
    }

    private void OnClickCombineDeclare(View[] allViewWithClick) {
        for (int j = 0; j < allViewWithClick.length; j++) {
            allViewWithClick[j] = findViewById(allViewWithClickId[j]);
            allViewWithClick[j].setOnClickListener(v -> {
                switch (v.getId()) {
                    case R.id.btn_fetch:
                        if (lstEditext != null && lstEditext.size() > 0) {
                            FetchandBill(lstEditext.get(0));
                        }
                        break;
                    case R.id.btn_electricrecharge:
                        EletricRecharge();
                        break;
                    case R.id.lay_circle:
                        ActivitySpinner.showSpinner(svContext, listSpinnerOperatorList, "Select Operator", REQUEST_CODE_OPERATOR);
                        break;
                    case R.id.img_drop_1:
                        ActivitySpinner.showSpinner(svContext, listSpinnerOperatorList, "Select Operator", REQUEST_CODE_OPERATOR);
                        break;
                    case R.id.btn_otpcancel:
                        hideOtpLayout();
                        break;
                    case R.id.btn_otpauth:
                        //RechargeProcess(svContext,lstEditext);
                        break;

                }
            });
        }
    }
    private void FetchandBill(View v) {
        int edtTextxId = v.getId();
        if (selectedOperatorSpinner != null &&
                ((EditText) v).getText().toString().trim().length() != 0) {

            String paramOne = "", paramTwo = "", paramThree = "";

            paramOne = lstEditext.get(0).getText().toString().trim();

            if (lstEditext.size() > 1) {
                paramTwo = lstEditext.get(1).getText().toString().trim();
            }

            if (lstEditext.size() > 2) {
                paramThree = lstEditext.get(2).getText().toString().trim();
            }


            if (edtTextxId == 0) {
                strRechargeNumber = ((EditText) v).getText().toString().trim();
                lstUploadData = new LinkedList<>();
                lstUploadData.add(PreferenceConnector.readString(svContext, PreferenceConnector.LOGINEDUSERID, ""));
                lstUploadData.add(strOperatorCode);
                lstUploadData.add(paramOne);
                lstUploadData.add(paramTwo);
                lstUploadData.add(paramThree);
                callWebService(ApiInterface.GETBBSPELECBILLFETCH, lstUploadData);
            }
        }
//        else {
//            customToast.showCustomToast(svContext, "Please Enter " + ((EditText) v).getHint().toString(), customToast.ToastyError);
//        }
    }


    private Context svContext;
    private ShowCustomToast customToast;
    

    private void StartApp() {
        svContext = this;
        customToast = new ShowCustomToast(svContext);
        
        ViewGroup root = (ViewGroup) findViewById(R.id.headlayout);
        if (!GlobalVariables.CUSTOMFONTNAME.equals("")) {
            Typeface font = Typeface.createFromAsset(getAssets(), GlobalVariables.CUSTOMFONTNAME);
            FontUtils.setFont(root, font);
        }
        if (PreferenceConnector.readBoolean(svContext, PreferenceConnector.ISDARKTHEME, false)) {
//            FontUtils.setThemeColor(root, svContext, true);
        } else {
//            FontUtils.setThemeColor(root, svContext, false);
        }

        
//        if (checkNetwork.isConnectingToInternet()) {
//            NoInternetScreen.hideError();
//        } else {
//            NoInternetScreen.showInternetError();
//        }

        loadToolBar();
    }

    private ImageView imgToolBarBack;
    private void loadToolBar(){
        imgToolBarBack = (ImageView)findViewById(R.id.img_back);
        imgToolBarBack.setOnClickListener(this);
        tv_heading = (TextView)findViewById(R.id.tv_heading);
        TextView txtHeading = (TextView)findViewById(R.id.heading);
        //txtHeading.setText("BBPS Electricity");
        tv_heading.setText("BBPS Electricity");
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

    public static final String TAG_FIELDNAME = "fieldName";
    public static final String TAG_FIELDOTHER = "fieldOther";
    public static final String TAG_MINLENGTH = "minLength";
    public static final String TAG_MAXLENGTH = "maxLength";
    public static final String TAG_AMOUNT = "amount";
    private String str_amount = "", str_customername = "";

    @Override
    public void onWebServiceActionComplete(String result, String url) {
        if (url.contains(ApiInterface.GETBBSPELECTOPERATOR)) {
            try {
                listSpinnerOperatorList = new ArrayList<>();
                //listSpinnerShowData = new ArrayList<>();
                JSONObject json = new JSONObject(result);

                String str_message = json.getString(TAG_MESSAGE);
                String str_status = json.getString(TAG_STATUS);

                //listSpinnerOperatorList.add("-1" + "#:#" + "Select Biller");

                if (str_status.equalsIgnoreCase("1")) {
                    JSONArray data = json.getJSONArray(TAG_DATA);
                    for (int data_i = 0; data_i < data.length(); data_i++) {
                        JSONObject data_obj = data.getJSONObject(data_i);
                        String str_code = data_obj.getString("biller_id");
                        String str_name = data_obj.getString("billerName");
                        String str_billerAliasName = data_obj.getString("billerAliasName");
                        String is_fetch = data_obj.getString("is_fetch");

//                        listSpinnerOperatorList.add(str_code + "#:#" + str_name);
//                        listSpinnerShowData.add(str_code);
                        String strIconUrl = null;
                        if (data_obj.has("icon")) {
                            strIconUrl = data_obj.getString("icon");
                        }

                        listSpinnerOperatorList.add(new SpinnerModel(str_code, str_name, "", str_billerAliasName, strIconUrl));

                    }
                } else {
                    customToast.showCustomToast(svContext, str_message, customToast.ToastyError);
                }

                PopulateEltricOperatorList();
            } catch (JSONException e) {
                customToast.showCustomToast(svContext, "Some error occured", customToast.ToastyError);
                e.printStackTrace();
            }
        } else if (url.contains(ApiInterface.GETBBSPBILLPAY)) {
            try {
                JSONObject json = new JSONObject(result);

                String str_message = json.getString(TAG_MESSAGE);
                String str_status = json.getString(TAG_STATUS);

                if (str_status.equalsIgnoreCase("0")) {
                    customToast.showCustomToast(svContext, str_message, customToast.ToastyError);
                } else {
                    //txtUserName.setText("");
                    //edRechargeAmount.setText("");
//                    edKnumber.setText("");
//                    edSecondValue.setText("");
                    //spinnerOperatorList.setSelection(0);
                    customToast.showCustomToast(svContext, str_message, customToast.ToastySuccess);
                    Intent svIntent = new Intent(svContext, ActivityBbpsHistory.class);
                    startActivity(svIntent);
                    ((Activity) svContext).finish();

                }
            } catch (JSONException e) {
                customToast.showCustomToast(svContext, "Some error occured", customToast.ToastyError);
                e.printStackTrace();
            }
        } else if (url.contains(ApiInterface.GETBBSPELECTFORM)) {
            try {
                JSONObject json = new JSONObject(result);

                String str_json_status = json.getString(TAG_STATUS);

                if (str_json_status.equalsIgnoreCase("0")) {
                    customToast.showCustomToast(svContext, json.getString("msg"), customToast.ToastyError);
                } else {
                    txtUserName.setText("");
                    edRechargeAmount.setText("");
                    String str_status = json.getString(TAG_STATUS);
                    if (str_status.equalsIgnoreCase("1")) {
                        JSONArray data_obj = json.getJSONArray(TAG_DATA);
                        if (data_obj.length() != 0) {
                            lstParam = new ArrayList<>();
                            for (int i = 0; i < data_obj.length(); i++) {
//                              {"fieldKey":"para1","paramName":"K Number","datatype":"NUMERIC","minlength":12,"maxlength":12,"optional":0}]}
                                JSONObject jsonObj = data_obj.getJSONObject(i);
                                lstParam.add(new ParamDataModel(jsonObj.getString("fieldKey"),
                                        jsonObj.getString("paramName"), jsonObj.getString("datatype"),
                                        jsonObj.getString("minlength"), jsonObj.getString("maxlength"),
                                        jsonObj.getString("optional")));

                                layBiller.setVisibility(View.VISIBLE);
                            }

                            AttachDynamicLay();
                        } else {
                            customToast.showCustomToast(svContext, "", customToast.ToastyError);
                        }
                    }
                }
            } catch (JSONException e) {
                customToast.showCustomToast(svContext, "Some error occured", customToast.ToastyError);
                e.printStackTrace();
            }

        } else if (url.contains(ApiInterface.GETBBSPELECBILLFETCH)) {

            try {
                JSONObject json = new JSONObject(result);

                String str_message = json.getString("message");
                String str_json_status = json.getString(TAG_STATUS);

                if (str_json_status.equalsIgnoreCase("0")) {
                    customToast.showCustomToast(svContext, str_message, customToast.ToastyError);
                } else {
                    if(json.has("amount")){
                        str_amount = json.getString("amount");}
                    else{
                        str_amount="";
                    }
                    if(json.has("accountHolderName")){
                        str_customername = json.getString("accountHolderName");
                    }
                    else{
                        str_customername="";
                    }
                }
            } catch (JSONException e) {
                customToast.showCustomToast(svContext, "Some error occured", customToast.ToastyError);
                e.printStackTrace();
            }

            txtUserName.setText(str_customername);
            edRechargeAmount.setText(str_amount);
        }
    }

    List<ParamDataModel> lstParam;
    List<EditText> lstEditext;
    String strRechargeNumber = "";

    private void AttachDynamicLay() {
        lstEditext = new ArrayList<>();
        for (int i = 0; i < lstParam.size(); i++) {
            View child = getLayoutInflater().inflate(R.layout.item_dynamiclay, null);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) getResources().getDimension(R.dimen.fortyfive));
            lp.setMargins(0, 0, 0, (int) getResources().getDimension(R.dimen.fifteen));
            EditText edItem = (child).findViewById(R.id.param_name);
            edItem.setHint(lstParam.get(i).getParamName());
//            edItem.setFilters(new InputFilter[]{new InputFilterMinMax(lstParam.get(i).getMinlength(),
//                    lstParam.get(i).getMaxlength())});


            edItem.setId(i);
            edItem.setLayoutParams(lp);
            edItem.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        int edtTextxId = v.getId();
                        if (spinnerOperatorList.getSelectedItemPosition() != 0 &&
                                ((EditText) v).getText().toString().trim().length() != 0) {

                            String paramOne = "", paramTwo = "", paramThree = "";

                            paramOne = lstEditext.get(0).getText().toString().trim();

                            if (lstEditext.size() > 1) {
                                paramTwo = lstEditext.get(1).getText().toString().trim();
                            }

                            if (lstEditext.size() > 2) {
                                paramThree = lstEditext.get(2).getText().toString().trim();
                            }


                            if (edtTextxId == 0) {
                                strRechargeNumber = ((EditText) v).getText().toString().trim();
                                lstUploadData = new LinkedList<>();
                                lstUploadData.add(PreferenceConnector.readString(svContext, PreferenceConnector.LOGINEDUSERID, ""));
                                lstUploadData.add(strOperatorCode);
                                lstUploadData.add(paramOne);
                                lstUploadData.add(paramTwo);
                                lstUploadData.add(paramThree);
                                callWebService(ApiInterface.GETBBSPELECBILLFETCH, lstUploadData);
                            }
                        }
                    }
                }
            });
            lstEditext.add(edItem);


            item.addView(child);
        }


    }

    @Override
    public void onWebServiceError(String result, String url) {
        customToast.showCustomToast(svContext, result, customToast.ToastyError);
    }

    private void ShowOtpLayout() {
        ((LinearLayout) findViewById(R.id.card_electrical)).setVisibility(View.GONE);
        ((LinearLayout) findViewById(R.id.card_otp)).setVisibility(View.VISIBLE);
    }

    private void hideOtpLayout() {
        ((LinearLayout) findViewById(R.id.card_electrical)).setVisibility(View.VISIBLE);
        ((LinearLayout) findViewById(R.id.card_otp)).setVisibility(View.GONE);
    }

    Spinner spinnerOperatorList;

    private void PopulateEltricOperatorList() {
//        SpinnerPopulateAdapter spindapter = new SpinnerPopulateAdapter(svContext, listSpinnerOperatorList, true);
//        spinnerOperatorList.setAdapter(spindapter);
//
//        spinnerOperatorList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                strOperatorCode = (listSpinnerOperatorList.get(i)).split("#:#")[0];
//                item.removeAllViews();
//                if (!strOperatorCode.equalsIgnoreCase("-1")) {
//                    lstUploadData = new LinkedList<>();
//                    lstUploadData.add(PreferenceConnector.readString(svContext, PreferenceConnector.LOGINEDUSERID, ""));
//                    lstUploadData.add(strOperatorCode);
//                    callWebService(ApiInterface.GETBBSPELECTFORM, lstUploadData);
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
    }

    private String strOperatorCode;

    private void EletricRecharge() {
        int response = 0;
        response = CheckValidation.emptyEditTextError(edRecharge, edTextsError);

        if (selectedOperatorSpinner == null) {
            response++;
            customToast.showCustomToast(svContext, "Please select operator", customToast.ToastyError);
        }

        if ((edRechargeAmount.getText().toString().trim()).length() == 1) {
            response++;
            customToast.showCustomToast(svContext, "Please enter at least 10 Rs", customToast.ToastyError);
        }

        if (response == 0) {
            if (strRechargeNumber.length() == 0) {
                response++;
                customToast.showCustomToast(svContext, "Please enter correct value", customToast.ToastyError);
            }
        }


//        if (response == 0) {
//            ShowOtpLayout();
//        }

        if (response == 0) {
            Float amount = Float.parseFloat(edRechargeAmount.getText().toString().trim());
            ActivityMain act = new ActivityMain();
            boolean isWalletLoading = act.checkEWalletAndAddWallet(amount, "electric", svContext,lstEditext,strOperatorCode,edRechargeAmount.getText().toString().trim(),"",customToast);
            if (!isWalletLoading) {
                RechargeProcess(svContext,lstEditext,strOperatorCode,edRechargeAmount.getText().toString().trim(),customToast);
            }
        }
    }

    public void RechargeProcess(Context svContext, List<EditText> lstEditext, String strOperatorCode, String amount, ShowCustomToast customToast) {
        String paramOne = "", paramTwo = "", paramThree = "";
        paramOne = lstEditext.get(0).getText().toString().trim();
        if (lstEditext.size() > 1) {
            paramTwo = lstEditext.get(1).getText().toString().trim();
        }
        if (lstEditext.size() > 2) {
            paramThree = lstEditext.get(2).getText().toString().trim();
        }

        lstUploadData = new LinkedList<>();
        lstUploadData.add(PreferenceConnector.readString(svContext, PreferenceConnector.LOGINEDUSERID, ""));
        lstUploadData.add(strOperatorCode);
        lstUploadData.add(paramOne);
        //lstUploadData.add(paramTwo);
        //lstUploadData.add(paramThree);
        lstUploadData.add(amount);
        //lstUploadData.add(edRechargeAmount.getText().toString().trim());
        //lstUploadData.add(edtOtp.getText().toString().trim());
        this.svContext=svContext;
        this.customToast=customToast;

        callWebService(ApiInterface.GETBBSPBILLPAY, lstUploadData);
    }

    private String strDemoServiceName = "", dtrDemoServiceUrl = "";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == RESULT_OK) {
            Bundle extras = intent.getExtras();
            if (extras == null) return;
            switch (requestCode) {
                case REQUEST_CODE_OPERATOR:
                    int pos2 = intent.getIntExtra(ActivitySpinner.EXTRA_SPINNER_POSITION, 0);
                    selectedOperatorSpinner = (SpinnerModel) intent.getSerializableExtra(ActivitySpinner.EXTRA_SPINNER_DATA);
                    if (selectedOperatorSpinner == null) {
                        txtSpinnerOperatorList.setText("Select Operator");
                    } else {
                        txtSpinnerOperatorList.setText(selectedOperatorSpinner.getTitle());
                        strOperatorCode = selectedOperatorSpinner.getId();
                        item.removeAllViews();
                        lstUploadData = new LinkedList<>();
                        lstUploadData.add(PreferenceConnector.readString(svContext, PreferenceConnector.LOGINEDUSERID, ""));
                        lstUploadData.add(strOperatorCode);
                        callWebService(ApiInterface.GETBBSPELECTFORM, lstUploadData);
                    }
                    break;
                default:
                    break;
            }
        }
    }
}