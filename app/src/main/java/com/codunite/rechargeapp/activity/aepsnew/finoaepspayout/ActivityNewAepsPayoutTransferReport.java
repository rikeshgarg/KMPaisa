package com.codunite.rechargeapp.activity.aepsnew.finoaepspayout;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codunite.commonutility.retrofit.ApiInterface;
import com.codunite.rechargeapp.R;
import com.codunite.commonutility.GetFormattedDateTime;
import com.codunite.commonutility.GlobalVariables;
import com.codunite.commonutility.ItemAnimation;
import com.codunite.commonutility.NoInternetScreen;
import com.codunite.commonutility.PreferenceConnector;
import com.codunite.commonutility.ShowCustomToast;
import com.codunite.commonutility.WebService;
import com.codunite.commonutility.WebServiceListener;
import com.codunite.commonutility.customfont.FontUtils;
import com.codunite.rechargeapp.model.AepsPayoutHistoryModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

public class ActivityNewAepsPayoutTransferReport extends AppCompatActivity implements View.OnClickListener, WebServiceListener {
    private ImageView imgToolBarBack;
    private RecyclerView wallethistoryrv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_payout_transfer_report);
        StartApp();
        resumeApp();
    }

    boolean isDateFrom = true;
    Calendar myCalendar;
    TextView txtFrom, txtTo;

    public void resumeApp() {
        wallethistoryrv = (RecyclerView) findViewById(R.id.wallethistory_rv);
        findViewById(R.id.lay_filter).setVisibility(View.GONE);

        myCalendar = Calendar.getInstance();
        txtFrom = (TextView) findViewById(R.id.datePicker_from);
        txtTo = (TextView) findViewById(R.id.datePicker_to);

        LoadMoneyTransferReport(GetFormattedDateTime.getOneMonthDate(), GetFormattedDateTime.getcurrentcalDate());
        txtFrom.setText(GetFormattedDateTime.getOneMonthDate());
        txtTo.setText(GetFormattedDateTime.getcurrentcalDate());

        DatePickerDialog.OnDateSetListener date = (view, year, monthOfYear, dayOfMonth) -> {
            int month = monthOfYear + 1;
            String selectedDate = year + "-" + (month >= 10 ? month : "0" + month)
                    + "-" + (dayOfMonth >= 10 ? dayOfMonth : "0" + dayOfMonth);

            if (isDateFrom) {
                txtFrom.setText(selectedDate);
            } else {
                txtTo.setText(selectedDate);
            }
        };

        txtTo.setOnClickListener(v -> {
            isDateFrom = false;
            String[] strDate = txtTo.getText().toString().trim().split("-");
            new DatePickerDialog(svContext, date,
                    Integer.parseInt(strDate[0]),
                    Integer.parseInt(strDate[1]) - 1,
                    Integer.parseInt(strDate[2])).show();
        });

        txtFrom.setOnClickListener(v -> {
            isDateFrom = true;
            String[] strDate = txtFrom.getText().toString().trim().split("-");
            new DatePickerDialog(svContext, date,
                    Integer.parseInt(strDate[0]),
                    Integer.parseInt(strDate[1]) - 1,
                    Integer.parseInt(strDate[2])).show();
        });

        ImageView imgSearch = (ImageView) findViewById(R.id.filter_search);
        imgSearch.setOnClickListener(v -> {
            if ((txtFrom.getText().toString().trim()).equalsIgnoreCase("Select from date")) {
                customToast.showCustomToast(svContext, "Please select from date first", customToast.ToastyInfo);
            } else if ((txtTo.getText().toString().trim()).equalsIgnoreCase("Select to date")) {
                customToast.showCustomToast(svContext, "Please select to date first", customToast.ToastyInfo);
            } else {
                LoadMoneyTransferReport(txtFrom.getText().toString().trim(), txtTo.getText().toString().trim());
            }
        });
    }


    private void LoadMoneyTransferReport(String fromDate, String toDate) {
        lstUploadData = new LinkedList<>();
        lstUploadData.add(PreferenceConnector.readString(svContext, PreferenceConnector.LOGINEDUSERID, ""));
//        lstUploadData.add(fromDate);
//        lstUploadData.add(toDate);
        callWebService(ApiInterface.NEW_AEPS_PAYOUT_LIST, lstUploadData);
    }

    private Context svContext;
    private ShowCustomToast customToast;
    
    

    private void StartApp() {
        svContext = this;
        customToast = new ShowCustomToast(svContext);
        
        ViewGroup root = (ViewGroup) findViewById(R.id.headlayout);
        new NoInternetScreen(svContext, root, ActivityNewAepsPayoutTransferReport.this);
        if (!GlobalVariables.CUSTOMFONTNAME.equals("")) {
            Typeface font = Typeface.createFromAsset(getAssets(), GlobalVariables.CUSTOMFONTNAME);
            FontUtils.setFont(root, font);
        }
        hideKeyboard();
        loadToolBar();
    }

    private void loadToolBar() {
        imgToolBarBack = (ImageView) findViewById(R.id.img_back);
        imgToolBarBack.setOnClickListener(this);

        TextView txtHeading = (TextView) findViewById(R.id.heading);
        txtHeading.setText("Fino Aeps Settlement Transfer Report");
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

    private List<AepsPayoutHistoryModel> lstItems = new ArrayList<>();

    public static final String TAG_DATA = "data";
    public static final String TAG_AMOUNT = "amount";
    public static final String TAG_DATETIME = "datetime";
    public static final String TAG_DESCRIPTION = "description";
    public static final String TAG_ORDER_ID = "order_id";
    public static final String TAG_STATUS = "status";
    public static final String TAG_MESSAGE = "message";
    public static final String TAG_JSON_STATUS = "status";

    @Override
    public void onWebServiceActionComplete(String result, String url) {
        if (url.contains(ApiInterface.NEW_AEPS_PAYOUT_LIST)) {
            try {
                lstItems = new ArrayList<>();

                JSONObject json = new JSONObject(result);
                String str_message = json.getString(TAG_MESSAGE);
                String str_json_status = json.getString(TAG_STATUS);
                if (str_json_status.equalsIgnoreCase("0")) {
                    customToast.showCustomToast(svContext, str_message, customToast.ToastyError);
                } else {
                    JSONArray data = json.getJSONArray(TAG_DATA);
                    for (int data_i = 0; data_i < (data).length(); data_i++) {
                        JSONObject data_obj = data.getJSONObject(data_i);
                        String str_memberID = data_obj.getString("beneficiary");
                        String str_account_holder_name = data_obj.getString("account_holder_name");
                        String str_date = data_obj.getString("date");
                        String str_transfer_amount = data_obj.getString("transfer_amount");
                        String str_trnx_id = data_obj.getString("refid");
                        String str_total_wallet_deduct = data_obj.getString("total_wallet_deduct");
                        String str_rrn = data_obj.getString("utr");
                        String Str_status = data_obj.getString("status");

                        String str_mobile = "", str_ifsc = "";
                        if (data_obj.has("mobile")) {
                            str_mobile = data_obj.getString("mobile");
                        }
                        if (data_obj.has("ifsc")) {
                            str_ifsc = data_obj.getString("ifsc");
                        }

                        lstItems.add(new AepsPayoutHistoryModel(str_memberID, str_account_holder_name, str_date, str_mobile, str_ifsc,
                                str_transfer_amount, str_trnx_id, str_rrn, Str_status));
                    }
                }
            } catch (JSONException e) {
                customToast.showCustomToast(svContext, "Some error occured", customToast.ToastyError);
                e.printStackTrace();
            }

            LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            wallethistoryrv.setLayoutManager(layoutManager);
            wallethistoryrv.setHasFixedSize(true);
            int animation_type = ItemAnimation.LEFT_RIGHT;


        } else if (url.contains(ApiInterface.AEPS_PAYOUT_CHECK_STATUSAUTH)) {
            try {
                JSONObject json = new JSONObject(result);

                String str_status = json.getString("status");
                String str_msg = json.getString("message");
                if (str_status.equalsIgnoreCase("0")) {
                    customToast.showCustomToast(svContext, str_msg, customToast.ToastyError);
                } else {
                    customToast.showCustomToast(svContext, str_msg, customToast.ToastySuccess);
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
        hideKeyboard();
        super.onBackPressed();
    }
}