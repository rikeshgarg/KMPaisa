package com.codunite.rechargeapp.activity.mainwallet;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codunite.commonutility.CheckValidation;
import com.codunite.commonutility.GlobalVariables;
import com.codunite.commonutility.ItemAnimation;
import com.codunite.commonutility.NoInternetScreen;
import com.codunite.commonutility.PreferenceConnector;
import com.codunite.commonutility.ShowCustomToast;
import com.codunite.commonutility.WebService;
import com.codunite.commonutility.WebServiceListener;
import com.codunite.commonutility.customfont.FontUtils;
import com.codunite.commonutility.retrofit.ApiInterface;
import com.codunite.rechargeapp.R;
import com.codunite.rechargeapp.WebViewActivity;
import com.codunite.rechargeapp.adapter.WalletHistoryAdapter;
import com.codunite.rechargeapp.model.WalletHistoryModel;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ActivityTopupWalletRazorpay extends AppCompatActivity implements View.OnClickListener, WebServiceListener,
        PaymentResultListener {
    private static final String TAG = "okrecharge";
    private EditText edAmount;
    private TextView txtCurrentBal, txt_history_label;

    private RadioGroup rgPaymentTYpe;
    private ImageView imgBack;
    private TextView btnAmount100, btnAmount500, btnAmount1000, btnAmount2000, btnAmount1500;
    private AppCompatButton btnproceedtopay;
    private View[] allViewWithClick = {btnproceedtopay, imgBack, btnAmount100, btnAmount500, btnAmount1000, btnAmount2000, btnAmount1500};
    private int[] allViewWithClickId = {R.id.btnproceedtopay, R.id.img_back, R.id.btn_amount100, R.id.btn_amount500, R.id.btn_amount1000, R.id.btn_amount2000, R.id.btn_amount1500};

    private EditText[] edTexts = {edAmount};
    private String[] edTextsError = {"Enter amount"};
    private int[] editTextsClickId = {R.id.edtenteramount};

    private List<WalletHistoryModel> lstItems = new ArrayList<>();
    private RecyclerView wallethistoryrv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_topupwallet_cashfree);

        StartApp();
        OnClickCombineDeclare(allViewWithClick);
        EditTextDeclare(edTexts);

        Checkout.preload(getApplicationContext());

        txtCurrentBal = findViewById(R.id.current_bal);
        txt_history_label = findViewById(R.id.history_label);

        rgPaymentTYpe = findViewById(R.id.rg_txttype);
        wallethistoryrv = findViewById(R.id.wallethistory_rv);
        txtCurrentBal.setText(PreferenceConnector.readString(svContext, PreferenceConnector.WALLETBAL, ""));

        RadioGroup rGroup = (RadioGroup) findViewById(R.id.rg_txttype);
        RadioButton checkedRadioButton = (RadioButton) rGroup.findViewById(rGroup.getCheckedRadioButtonId());
        rGroup.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton checkedRadioButton1 = group.findViewById(checkedId);
            boolean isChecked = checkedRadioButton1.isChecked();
            if (isChecked) {
                Proceedtopay();
            }
        });

    }

    private String firstLetterInUpperCase(String str) {
        try {
            String firstLetStr = str.substring(0, 1);
            String remLetStr = str.substring(1).toLowerCase();
            firstLetStr = firstLetStr.toUpperCase();
            String firstLetterCapitalizedName = firstLetStr + remLetStr;
            return firstLetterCapitalizedName;
        } catch (Exception e) {
            e.printStackTrace();
            return str;
        }
    }

    private void Proceedtopay() {
        int response = 0;
        response = CheckValidation.emptyEditTextError(edTexts, edTextsError);

        if ((edAmount.getText().toString().trim()).length() == 1) {
            response++;
            customToast.showCustomToast(svContext, "Please enter at least 10 Rs", customToast.ToastyError);
        }

        double amount = Double.parseDouble(edAmount.getText().toString().trim());
        if (amount > 2000) {
            response++;
            customToast.showCustomToast(svContext, "Please enter at amount less than Rs 2000/- ", customToast.ToastyError);
        }

        if (response == 0) {
            lstUploadData = new LinkedList<>();
            lstUploadData.add(PreferenceConnector.readString(svContext, PreferenceConnector.LOGINEDUSERID, ""));
            lstUploadData.add(edAmount.getText().toString().trim());
            callWebService(ApiInterface.ORDER_TOKEN_AUTH, lstUploadData);
        }
    }

    @Override
    public void onPaymentSuccess(String razorpayPaymentID) {
        try {
            lstUploadData = new LinkedList<>();
            lstUploadData.add(PreferenceConnector.readString(svContext, PreferenceConnector.LOGINEDUSERID, ""));
            lstUploadData.add(strAMount);
            lstUploadData.add(razorpayPaymentID);
            callWebService(ApiInterface.ADDWALLETRAZORPAY, lstUploadData);
        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentSuccess", e);
        }
    }

    @Override
    public void onPaymentError(int code, String response) {
        Log.e(TAG,   code + response);
        try {
            Toast.makeText(this, "Payment failed: " + code + " " + response, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentError", e);
        }
    }

    String strAMount;

    public void startPayment(String strAmount) {
        strAMount = strAmount;
        final Activity activity = this;
        String strRazorPayId = PreferenceConnector.readString(svContext, PreferenceConnector.RAZORPAYID, "");
//        strRazorPayId = "rzp_test_ySSHvBEqxc6f7Q";
        final Checkout co = new Checkout();
        co.setKeyID(strRazorPayId);
        co.setImage(R.drawable.logo_color);
        if (strRazorPayId.length() == 0) {
            customToast.showCustomToast(svContext, "Razorpay Key Not Available", customToast.ToastyError);
        } else {
            try {
                JSONObject options = new JSONObject();
                options.put("name", getResources().getString(R.string.app_name));
                options.put("description", "Add amount to wallet");
                options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.jpg");
//                options.put("order_id", strOrderrderId);
                options.put("theme.color", "#F37254");
                options.put("currency", "INR");
                options.put("method", "upi");
                options.put("amount", (Integer.parseInt(strAmount) * 100) + "");
                options.put("prefill.email", PreferenceConnector.readString(svContext, PreferenceConnector.LOGINUSEREMAIL, ""));
                options.put("prefill.contact", PreferenceConnector.readString(svContext, PreferenceConnector.LOGINUSERPHONE, ""));
//                JSONObject retryObj = new JSONObject();
//                retryObj.put("enabled", true);
//                retryObj.put("max_count", 4);
//                options.put("retry", retryObj);
                co.open(activity, options);
            } catch (Exception e) {
                Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
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
                        case R.id.btnproceedtopay:
                            Proceedtopay();
                            break;
                        case R.id.btn_amount100:
                            edAmount.setText("100");
                            break;
                        case R.id.btn_amount500:
                            edAmount.setText("500");
                            break;
                        case R.id.btn_amount1000:
                            edAmount.setText("1000");
                            break;
                        case R.id.btn_amount1500:
                            edAmount.setText("1500");
                            break;
                        case R.id.btn_amount2000:
                            edAmount.setText("2000");
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
        edAmount = (EditText) editTexts[0];

        if (getIntent().hasExtra("strAmount")) {
            Float strAmount = getIntent().getFloatExtra("strAmount", 0);
            edAmount.setText(strAmount + "");
        } else {
            edAmount.setText("");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.termscondition:
//                break;
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
        new NoInternetScreen(svContext, root, ActivityTopupWalletRazorpay.this);
        if (!GlobalVariables.CUSTOMFONTNAME.equals("")) {
            Typeface font = Typeface.createFromAsset(getAssets(), GlobalVariables.CUSTOMFONTNAME);
            FontUtils.setFont(root, font);
        }
        hideKeyboard();
        loadToolBar();
    }

    private void loadToolBar() {
        TextView txtHeading = (TextView) findViewById(R.id.heading);
        txtHeading.setText("Add Fund");
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

    private void callWebServiceWithoutDialog(String postUrl, LinkedList<String> lstUploadData) {
        WebService webService = new WebService(svContext, postUrl, lstUploadData, this, false);
        webService.LoadDataRetrofit(webService.callReturn());
    }

    public static final String TAG_DATA = "data";
    public static final String TAG_AMOUNT = "amount";
    public static final String TAG_DATETIME = "datetime";
    public static final String TAG_DESCRIPTION = "description";
    public static final String TAG_TYPE = "type";
    public static final String TAG_MESSAGE = "message";
    public static final String TAG_STATUS = "status";

    private String strOrderrderId = "";
    @Override
    public void onWebServiceActionComplete(String result, String url) {
        if (url.contains(ApiInterface.ADDWALLETRAZORPAY)) {
            try {
                JSONObject json = new JSONObject(result);
                String str_status = json.getString("status");
                String str_msg = json.getString("message");
                if (str_status.equalsIgnoreCase("0")) {
                    customToast.showCustomToast(svContext, str_msg, customToast.ToastyError);
                } else {
                    customToast.showCustomToast(svContext, str_msg, customToast.ToastySuccess);
                    onBackPressed();
                }
            } catch (JSONException e) {
                customToast.showCustomToast(svContext, "Some error occured", customToast.ToastyError);
                e.printStackTrace();
            }
        } else if (url.contains(ApiInterface.ORDER_TOKEN_AUTH)) {
            try {
                JSONObject json = new JSONObject(result);
                String str_status = json.getString("status");
                String str_msg = json.getString("message");
                if (str_status.equalsIgnoreCase("0")) {
                    customToast.showCustomToast(svContext, str_msg, customToast.ToastyError);
                } else {
                    startPayment(edAmount.getText().toString().trim());
                }
            } catch (JSONException e) {
                customToast.showCustomToast(svContext, "Some error occured", customToast.ToastyError);
                e.printStackTrace();
            }
        } else if (url.contains(ApiInterface.WALLETHISTORY)) {
            try {
                lstItems = new ArrayList<>();

                JSONObject json = new JSONObject(result);
                String str_message = json.getString(TAG_MESSAGE);
                String str_status = json.getString(TAG_STATUS);
                if (str_status.equalsIgnoreCase("0")) {
                    customToast.showCustomToast(svContext, str_message, customToast.ToastyError);
                } else {
                    JSONArray data = json.getJSONArray(TAG_DATA);
                    for (int data_i = 0; data_i < ((JSONArray) data).length(); data_i++) {
                        JSONObject data_obj = data.getJSONObject(data_i);
                        String str_amount = data_obj.getString(TAG_AMOUNT);
                        String str_datetime = data_obj.getString(TAG_DATETIME);
                        String str_description = data_obj.getString(TAG_DESCRIPTION);
                        String str_type = data_obj.getString(TAG_TYPE);

                        lstItems.add(new WalletHistoryModel(str_amount, str_datetime, str_description, str_type));
                    }
                }
            } catch (JSONException e) {
                customToast.showCustomToast(svContext, "Some error occured", customToast.ToastyError);
                e.printStackTrace();
            }

            if (lstItems.size() > 0) {
                txt_history_label.setVisibility(View.VISIBLE);
            }
            txtCurrentBal.setVisibility(View.VISIBLE);

            LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            wallethistoryrv.setLayoutManager(layoutManager);
            wallethistoryrv.setHasFixedSize(true);
            int animation_type = ItemAnimation.LEFT_RIGHT;
            WalletHistoryAdapter mAdapter = new WalletHistoryAdapter(this, lstItems, animation_type);
            wallethistoryrv.setNestedScrollingEnabled(false);
            wallethistoryrv.setAdapter(mAdapter);
            mAdapter.setOnItemClickListener((view, obj, position) -> {

            });
        }
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

    private String strDemoServiceName = "", dtrDemoServiceUrl = "";

}