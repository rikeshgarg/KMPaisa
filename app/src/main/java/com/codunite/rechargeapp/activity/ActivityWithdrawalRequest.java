package com.codunite.rechargeapp.activity;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codunite.commonutility.retrofit.ApiInterface;
import com.codunite.rechargeapp.R;
import com.codunite.rechargeapp.adapter.WalletHistoryAdapter;
import com.codunite.commonutility.CheckInternet;
import com.codunite.commonutility.CheckValidation;
import com.codunite.commonutility.GlobalVariables;
import com.codunite.commonutility.ItemAnimation;
import com.codunite.commonutility.NoInternetScreen;
import com.codunite.commonutility.PreferenceConnector;
import com.codunite.commonutility.ShowCustomToast;
import com.codunite.commonutility.WebService;
import com.codunite.commonutility.WebServiceListener;
import com.codunite.commonutility.customfont.FontUtils;
import com.codunite.rechargeapp.model.WalletHistoryModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ActivityWithdrawalRequest extends AppCompatActivity implements View.OnClickListener, WebServiceListener {
    private EditText edAmount, edTPin;
    private Button btnproceedtopay;
    private TextView txtCurrentBal;

    private ImageView imgBack;
    private View[] allViewWithClick = {btnproceedtopay, imgBack};
    private int[] allViewWithClickId = {R.id.btnproceedtopay, R.id.img_back};

    private EditText[] edTexts = {edAmount, edTPin};
    private String[] edTextsError = {"Enter amount", "Enter Transcation Password"};
    private int[] editTextsClickId = {R.id.edtenteramount, R.id.edttpwd};

    private List<WalletHistoryModel> lstItems = new ArrayList<>();
    private RecyclerView wallethistoryrv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_withdrawalrequest);
        StartApp();

        OnClickCombineDeclare(allViewWithClick);
        EditTextDeclare(edTexts);

        txtCurrentBal = (TextView) findViewById(R.id.current_bal);
        wallethistoryrv = (RecyclerView) findViewById(R.id.wallethistory_rv);
        txtCurrentBal.setText(PreferenceConnector.readString(svContext, PreferenceConnector.COMMISIONBALANCE, ""));
    }

    private void Proceedtopay() {
        int response = 0;
        response = CheckValidation.emptyEditTextError(edTexts, edTextsError);

        if (response == 0) {
            lstUploadData = new LinkedList<>();
            lstUploadData.add(PreferenceConnector.readString(svContext, PreferenceConnector.LOGINEDUSERID, ""));
            lstUploadData.add(edAmount.getText().toString().trim());
            lstUploadData.add(edTPin.getText().toString().trim());
            callWebService(ApiInterface.WITHDRAWALREQUESTAMOUNT, lstUploadData);
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
        edTPin = (EditText) editTexts[1];
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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
        new NoInternetScreen(svContext, root, ActivityWithdrawalRequest.this);
        if (!GlobalVariables.CUSTOMFONTNAME.equals("")) {
            Typeface font = Typeface.createFromAsset(getAssets(), GlobalVariables.CUSTOMFONTNAME);
            FontUtils.setFont(root, font);
        }
        hideKeyboard();
        loadToolBar();
    }

    private void loadToolBar() {
        TextView txtHeading = (TextView) findViewById(R.id.heading);
        txtHeading.setText("Withdarwal Request");
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

    @Override
    public void onWebServiceActionComplete(String result, String url) {
        if (url.contains(ApiInterface.WITHDRAWALREQUESTAMOUNT)) {
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

            txtCurrentBal.setVisibility(View.VISIBLE);

            LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            wallethistoryrv.setLayoutManager(layoutManager);
            wallethistoryrv.setHasFixedSize(true);
            int animation_type = ItemAnimation.LEFT_RIGHT;
            WalletHistoryAdapter mAdapter = new WalletHistoryAdapter(this, lstItems, animation_type);
            wallethistoryrv.setNestedScrollingEnabled(false);
            wallethistoryrv.setAdapter(mAdapter);
            mAdapter.setOnItemClickListener(new WalletHistoryAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, String obj, int position) {

                }
            });
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