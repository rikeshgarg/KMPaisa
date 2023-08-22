package com.codunite.rechargeapp.activity.bbps;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.codunite.commonutility.retrofit.ApiInterface;
import com.codunite.commonutility.spinner.ActivitySpinner;
import com.codunite.commonutility.spinner.SpinnerModel;
import com.codunite.rechargeapp.activity.ActivityMain;
import com.codunite.rechargeapp.model.ParamDataModel;
import com.codunite.rechargeapp.activity.ActivityCompletion;
import com.codunite.rechargeapp.R;
import com.codunite.rechargeapp.WebViewActivity;
import com.codunite.commonutility.CheckValidation;
import com.codunite.commonutility.GlobalVariables;
import com.codunite.commonutility.PreferenceConnector;
import com.codunite.commonutility.ShowCustomToast;
import com.codunite.commonutility.SpinnerPopulateAdapter;
import com.codunite.commonutility.WebService;
import com.codunite.commonutility.WebServiceListener;
import com.codunite.commonutility.customfont.FontUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ActivityBbpsAllServices extends AppCompatActivity implements OnClickListener, WebServiceListener {
    //private List<String> listSpinnerOperatorList = new ArrayList<>();
    private List<String> listSpinnerShowData = new ArrayList<>();
    //private List<String> listSpinnerServiceList = new ArrayList<>();
    LinkedList<String> lstUploadData = new LinkedList<>();
    public static final String TAG_AMOUNT = "amount";
    private String str_amount = "", str_customername = "";
    private Button btnElectricRecharge, btnOtpVerify, btnCancelOtp,btnfetch;
    private ImageView imgDropOperator,img_serviceoperator;
    private TextView txtUserName;
    private static EditText edRechargeAmount;
    private EditText[] edRecharge = {edRechargeAmount};
    private String[] edTextsError = {"Enter recharge amount"};
    private int[] editTextDthClickId = {R.id.electricity_amount};
    RelativeLayout laySelectService, laySelectOperator;;
    private View[] allViewWithClick = {btnElectricRecharge, btnfetch, imgDropOperator, btnOtpVerify, btnCancelOtp,laySelectService, laySelectOperator,img_serviceoperator};
    private int[] allViewWithClickId = {R.id.btn_electricrecharge, R.id.btn_fetch, R.id.img_drop_1, R.id.btn_otpauth,R.id.btn_otpcancel, R.id.lay_serviceoperator, R.id.lay_spinner_operator,R.id.img_serviceoperator};

    private String str_minLength;
    private LinearLayout layBiller;
    private LinearLayout item;

    public static final String TAG_DATA = "data";
    public static final String TAG_MESSAGE = "message";
    public static final String TAG_STATUS = "status";
    private EditText edtOtp;
    public static String strServiceId = "";
    TextView tv_heading;
    List<ParamDataModel> lstParam;
    static List<EditText> lstEditext;
    String strRechargeNumber = "";
    private String serviceId;

    private String strOperatorCode;

    private TextView txtSpinnerOperatorList,txtSpinnerServiceList;
    private SpinnerModel selectedOperatorSpinner,selectedServiceSpinner;
    private List<SpinnerModel> listSpinnerOperatorList = new ArrayList<>();
    private List<SpinnerModel> listSpinnerServiceList = new ArrayList<>();

    private static final int REQUEST_CODE_SERVICE = 434;
    private static final int REQUEST_CODE_OPERATOR = 435;
    private Context svContext;
    private ShowCustomToast customToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_bbpsallservices);
        StartApp();

        OnClickCombineDeclare(allViewWithClick);
        EditTextDeclare(edRecharge);

        resumeApp();
    }

    public ActivityBbpsAllServices() {
    }



    public void resumeApp() {
        edtOtp = findViewById(R.id.edt_otp);
        layBiller = (LinearLayout) findViewById(R.id.lay_biller);
        layBiller.setVisibility(View.INVISIBLE);

        item = (LinearLayout) findViewById(R.id.lay_dynamic_lay);
        txtUserName = (TextView) findViewById(R.id.txt_username);
        //spinnerOperatorList = (Spinner) findViewById(R.id.spinner_electricoperatorlist);
        //spinnerServiceList = (Spinner) findViewById(R.id.spinner_serviceoperator);
        txtSpinnerServiceList = findViewById(R.id.spinner_serviceoperator_txt);
        txtSpinnerOperatorList = findViewById(R.id.spinner_electricoperator_txt);
        //layServiceoperator = (RelativeLayout) findViewById(R.id.lay_serviceoperator);

        if (FragBBPSDashBoard.strServiceId.equals("")) {
            LoadServiceList();
            laySelectService.setVisibility(View.VISIBLE);
        } else {
            serviceId = FragBBPSDashBoard.strServiceId;
            LoadOperatorList(serviceId);
            laySelectService.setVisibility(View.GONE);
        }

        hideOtpLayout();
        //OpenDemoLink();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void callWebServiceWithoutLoader(String postUrl, LinkedList<String> lstUploadData) {
        WebService webService = new WebService(svContext, postUrl, lstUploadData, this, false);
        webService.LoadDataRetrofit(webService.callReturn());
    }

    private void LoadServiceList() {
        lstUploadData = new LinkedList<>();
        lstUploadData.add(PreferenceConnector.readString(svContext, PreferenceConnector.LOGINEDUSERID, ""));
        callWebService(ApiInterface.GETBBSPSERVICELIST, lstUploadData);
    }

    private void LoadOperatorList(String serviceId) {
        lstUploadData = new LinkedList<>();
        lstUploadData.add(PreferenceConnector.readString(svContext, PreferenceConnector.LOGINEDUSERID, ""));
        lstUploadData.add(serviceId);
        callWebService(ApiInterface.GETBBSPSERVICEOPERATOR, lstUploadData);
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
            allViewWithClick[j].setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.btn_electricrecharge:
                            EletricRecharge();
                            break;
                        case R.id.btn_fetch:
                            FetchandBill(lstEditext.get(lstEditext.size() - 1), lstParam.size() - 1);
                            break;
                        case R.id.lay_serviceoperator:
                            ActivitySpinner.showSpinner(svContext, listSpinnerServiceList, "Select Service", REQUEST_CODE_SERVICE);
                            break;
                        case R.id.lay_spinner_operator:
                            ActivitySpinner.showSpinner(svContext, listSpinnerOperatorList, "Select Operator", REQUEST_CODE_OPERATOR);
                            break;
                        case R.id.btn_otpcancel:
                            hideOtpLayout();
                            break;
                        case R.id.img_serviceoperator:
                            ActivitySpinner.showSpinner(svContext, listSpinnerServiceList, "Select Service", REQUEST_CODE_SERVICE);
                            break;
                        case R.id.img_drop_1:
                            ActivitySpinner.showSpinner(svContext, listSpinnerOperatorList, "Select Operator", REQUEST_CODE_OPERATOR);
                            break;
                        case R.id.btn_otpauth:
                            //RechargeProcess(svContext);
                            break;
                    }
                }
            });
        }

        btnElectricRecharge = (Button) allViewWithClick[0];
        btnfetch = (Button) allViewWithClick[1];
        laySelectService = (RelativeLayout) allViewWithClick[5];
        laySelectOperator = (RelativeLayout) allViewWithClick[6];
    }


    

    private void StartApp() {
        svContext = this;
        customToast = new ShowCustomToast(svContext);
        
        ViewGroup root = (ViewGroup) findViewById(R.id.headlayout);
        if (!GlobalVariables.CUSTOMFONTNAME.equals("")) {
            Typeface font = Typeface.createFromAsset(getAssets(), GlobalVariables.CUSTOMFONTNAME);
            FontUtils.setFont(root, font);
        }
        loadToolBar();
    }

    public static void hideFragmentkeyboard(Context meraContext, View meraView) {
        final InputMethodManager imm = (InputMethodManager) meraContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(meraView.getWindowToken(), 0);
    }


    private void callWebService(String postUrl, LinkedList<String> lstUploadData) {
        WebService webService = new WebService(svContext, postUrl, lstUploadData, this);
        webService.LoadDataRetrofit(webService.callReturn());
    }



    @Override
    public void onWebServiceActionComplete(String result, String url) {
        if (url.contains(ApiInterface.GETBBSPSERVICELIST)) {
            try {
                listSpinnerServiceList = new ArrayList<>();
                JSONObject json = new JSONObject(result);

                String str_message = json.getString(TAG_MESSAGE);
                String str_status = json.getString(TAG_STATUS);

                if (str_status.equalsIgnoreCase("1")) {
                    JSONArray data = json.getJSONArray(TAG_DATA);
                    for (int data_i = 0; data_i < data.length(); data_i++) {
                        JSONObject data_obj = data.getJSONObject(data_i);
                        String str_code = data_obj.getString("service_id");
                        String str_name = data_obj.getString("title");
                        String strIconUrl = null;
                        if (data_obj.has("icon")) {
                            strIconUrl = data_obj.getString("icon");
                        }
                        listSpinnerServiceList.add(new SpinnerModel(str_code, str_name, "", "", strIconUrl));
                    }
                } else {
                    customToast.showCustomToast(svContext, str_message, customToast.ToastyError);
                }

                //PopulateServiceList();
            } catch (JSONException e) {
                customToast.showCustomToast(svContext, "Some error occured", customToast.ToastyError);
                e.printStackTrace();
            }
        } else if (url.contains(ApiInterface.GETBBSPSERVICEOPERATOR)) {
            try {
                listSpinnerOperatorList = new ArrayList<>();
                JSONObject json = new JSONObject(result);

                String str_message = json.getString(TAG_MESSAGE);
                String str_status = json.getString(TAG_STATUS);

                if (str_status.equalsIgnoreCase("1")) {
                    JSONArray data = json.getJSONArray(TAG_DATA);
                    for (int data_i = 0; data_i < data.length(); data_i++) {
                        JSONObject data_obj = data.getJSONObject(data_i);
                        String str_code = data_obj.getString("biller_id");
                        String str_name = data_obj.getString("billerName");
                        String str_billerAliasName = data_obj.getString("billerAliasName");
                        String is_fetch = data_obj.getString("is_fetch");

                        String strIconUrl = null;
                        if (data_obj.has("icon")) {
                            strIconUrl = data_obj.getString("icon");
                        }
                        listSpinnerOperatorList.add(new SpinnerModel(str_code, str_name, is_fetch, str_billerAliasName, strIconUrl));
                    }
                } else {
                    customToast.showCustomToast(svContext, str_message, customToast.ToastyError);
                }
                //PopulateEltricOperatorList();
            } catch (JSONException e) {
                customToast.showCustomToast(svContext, "Some error occured", customToast.ToastyError);
                e.printStackTrace();
            }
        } else if (url.contains(ApiInterface.GETBBSPSERVICEBILLPAY)) {
            try {
                JSONObject json = new JSONObject(result);

                String str_message = json.getString(TAG_MESSAGE);
                String str_status = json.getString(TAG_STATUS);

                if (str_status.equalsIgnoreCase("0")) {
                    customToast.showCustomToast(svContext, str_message, customToast.ToastyError);
                } else {
                    txtUserName.setText("");
                    edRechargeAmount.setText("");
                    //spinnerOperatorList.setSelection(0);
                    customToast.showCustomToast(svContext, str_message, customToast.ToastySuccess);

                    Intent svIntent = new Intent(svContext, ActivityCompletion.class);
                    svIntent.putExtra("from_act", "bbps");
                    startActivity(svIntent);
                    finish();
                }
            } catch (JSONException e) {
                customToast.showCustomToast(svContext, "Some error occured", customToast.ToastyError);
                e.printStackTrace();
            }
        } else if (url.contains(ApiInterface.GETBBSPSERVICEFORM)) {
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

        } else if (url.contains(ApiInterface.GETBBSPSERVICEBILLFETCH)) {

            try {
                str_amount = "";
                str_customername = "";

                JSONObject json = new JSONObject(result);
                String str_message = json.getString("message");
                String str_json_status = json.getString(TAG_STATUS);

                if (str_json_status.equalsIgnoreCase("0")) {
                    str_amount = "";
                    txtUserName.setText("");


                    customToast.showCustomToast(svContext, str_message, customToast.ToastyError);
                } else {

                    txtUserName.setText("");
                    edRechargeAmount.setText("");
                    if (json.has("amount")) {
                        str_amount = json.getString("amount");
                    } else {
                        str_amount = "";
                    }

                    if (json.has("accountHolderName")) {
                        str_customername = json.getString("accountHolderName");
                    } else {
                        str_customername = "";
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


    private void AttachDynamicLay() {
        lstEditext = new ArrayList<>();
        for (int i = 0; i < lstParam.size(); i++) {
            View child = getLayoutInflater().inflate(R.layout.item_dynamiclay, null);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) getResources().getDimension(R.dimen.fortyfive));
            lp.setMargins(0, 0, 0, (int) getResources().getDimension(R.dimen.fifteen));

            EditText edItem = (child).findViewById(R.id.param_name);
            edItem.setHint(lstParam.get(i).getParamName());

            edItem.setId(i);
            edItem.setLayoutParams(lp);
            edItem.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
//                        if (((EditText) v).getText().toString().trim().length() != 0) {
//                            FetchandBill(v, v.getId());
//                        }
                    }
                }
            });
            lstEditext.add(edItem);
            item.addView(child);
        }
    }

    private void FetchandBill(EditText v, int pos) {
        int edtTextxId = v.getId();

        if (selectedOperatorSpinner != null &&
                pos == lstParam.size() - 1) {

            String paramOne = "", paramTwo = "", paramThree = "";
            String paramFour = "", paramFive = "", paramSix = "";
            String paramSeven = "", paramEight = "";

            if (lstEditext.size() > 0) {
                paramOne = lstEditext.get(0).getText().toString().trim();
            }

            if (lstEditext.size() > 1) {
                paramTwo = lstEditext.get(1).getText().toString().trim();
            }

            if (lstEditext.size() > 2) {
                paramThree = lstEditext.get(2).getText().toString().trim();
            }

            if (lstEditext.size() > 3) {
                paramFour = lstEditext.get(3).getText().toString().trim();
            }

            if (lstEditext.size() > 4) {
                paramFive = lstEditext.get(4).getText().toString().trim();
            }

            if (lstEditext.size() > 5) {
                paramSix = lstEditext.get(5).getText().toString().trim();
            }

            if (lstEditext.size() > 6) {
                paramSeven = lstEditext.get(6).getText().toString().trim();
            }

            if (lstEditext.size() > 7) {
                paramEight = lstEditext.get(7).getText().toString().trim();
            }

            if (edtTextxId == lstEditext.size() - 1) {
                strRechargeNumber = v.getText().toString().trim();

                lstUploadData = new LinkedList<>();
                lstUploadData.add(PreferenceConnector.readString(svContext, PreferenceConnector.LOGINEDUSERID, ""));
                lstUploadData.add(serviceId);
                lstUploadData.add(strOperatorCode);
                lstUploadData.add(paramOne);
                lstUploadData.add(paramTwo);
                lstUploadData.add(paramThree);
                lstUploadData.add(paramFour);
                lstUploadData.add(paramFive);
                lstUploadData.add(paramSix);
                lstUploadData.add(paramSeven);
                lstUploadData.add(paramEight);
                callWebService(ApiInterface.GETBBSPSERVICEBILLFETCH, lstUploadData);
            }
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

    private void EletricRecharge() {
        strRechargeNumber = lstEditext.get(0).getText().toString().trim();

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
            boolean isWalletLoading = act.checkEWalletAndAddWallet(amount, "Service", svContext,lstEditext,strOperatorCode,edRechargeAmount.getText().toString().trim(),serviceId);
            if (!isWalletLoading) {
                RechargeProcess(svContext,lstEditext,strOperatorCode,edRechargeAmount.getText().toString().trim(),serviceId);
            }
        }
    }

    public void RechargeProcess(Context svContext,List<EditText> lstEditext,String strOperatorCode,String amount,String serviceId) {
        String paramOne = "", paramTwo = "", paramThree = "";
        String paramFour = "", paramFive = "", paramSix = "";
        String paramSeven = "", paramEight = "";

        paramOne = lstEditext.get(0).getText().toString().trim();

        if (lstEditext.size() > 1) {
            paramTwo = lstEditext.get(1).getText().toString().trim();
        }

        if (lstEditext.size() > 2) {
            paramThree = lstEditext.get(2).getText().toString().trim();
        }

        if (lstEditext.size() > 3) {
            paramFour = lstEditext.get(3).getText().toString().trim();
        }

        if (lstEditext.size() > 4) {
            paramFive = lstEditext.get(4).getText().toString().trim();
        }

        if (lstEditext.size() > 5) {
            paramSix = lstEditext.get(5).getText().toString().trim();
        }

        if (lstEditext.size() > 6) {
            paramSeven = lstEditext.get(6).getText().toString().trim();
        }

        if (lstEditext.size() > 7) {
            paramEight = lstEditext.get(7).getText().toString().trim();
        }

        lstUploadData = new LinkedList<>();
        lstUploadData.add(PreferenceConnector.readString(svContext, PreferenceConnector.LOGINEDUSERID, ""));
        lstUploadData.add(serviceId);
        lstUploadData.add(strOperatorCode);
        lstUploadData.add(paramOne);
        lstUploadData.add(paramTwo);
        lstUploadData.add(paramThree);
        lstUploadData.add(paramFour);
        lstUploadData.add(paramFive);
        lstUploadData.add(paramSix);
        lstUploadData.add(paramSeven);
        lstUploadData.add(paramEight);
        lstUploadData.add(amount);
        //lstUploadData.add(edtOtp.getText().toString().trim());
        WebService webService = new WebService(svContext, ApiInterface.GETBBSPSERVICEBILLPAY, lstUploadData, this);
        webService.LoadDataRetrofit(webService.callReturn());
    }


    private ImageView imgToolBarBack;

    private void loadToolBar() {
        imgToolBarBack = (ImageView) findViewById(R.id.img_back);
        imgToolBarBack.setOnClickListener(this);
        tv_heading=(TextView)findViewById(R.id.tv_heading);
        Bundle extras = getIntent().getExtras();
        String servicetype = "";
        if (extras != null) {
            servicetype = extras.getString("servicetype");
        }

        TextView txtHeading = (TextView) findViewById(R.id.heading);
        //txtHeading.setText(servicetype);
        tv_heading.setText(servicetype);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void hideKeyboard() {
        InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        // check if no view has focus:
        View view = this.getCurrentFocus();
        if (view != null) {
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == RESULT_OK) {
            Bundle extras = intent.getExtras();
            if (extras == null) return;
            switch (requestCode) {
                case REQUEST_CODE_SERVICE:
                    int pos1 = intent.getIntExtra(ActivitySpinner.EXTRA_SPINNER_POSITION, 0);
                    selectedServiceSpinner = (SpinnerModel) intent.getSerializableExtra(ActivitySpinner.EXTRA_SPINNER_DATA);
                    if (selectedServiceSpinner == null) {
                        txtSpinnerServiceList.setText("Select Service");
                    } else {
                        txtSpinnerServiceList.setText(selectedServiceSpinner.getTitle());
                        serviceId = selectedServiceSpinner.getId();
                        LoadOperatorList(serviceId);
                    }
                    break;
                case REQUEST_CODE_OPERATOR:
                    int pos2 = intent.getIntExtra(ActivitySpinner.EXTRA_SPINNER_POSITION, 0);
                    selectedOperatorSpinner = (SpinnerModel) intent.getSerializableExtra(ActivitySpinner.EXTRA_SPINNER_DATA);
                    if (selectedOperatorSpinner == null) {
                        txtSpinnerOperatorList.setText("Select Operator");
                    } else {
                        txtSpinnerOperatorList.setText(selectedOperatorSpinner.getTitle());
                        strOperatorCode = selectedOperatorSpinner.getId();
                        item.removeAllViews();
                        if (!strOperatorCode.equalsIgnoreCase("-1")) {
                            lstUploadData = new LinkedList<>();
                            lstUploadData.add(PreferenceConnector.readString(svContext, PreferenceConnector.LOGINEDUSERID, ""));
                            lstUploadData.add(serviceId);
                            lstUploadData.add(strOperatorCode);
                            callWebService(ApiInterface.GETBBSPSERVICEFORM, lstUploadData);
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }
}