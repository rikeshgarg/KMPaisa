package com.codunite.rechargeapp.activity.reports;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.codunite.commonutility.retrofit.ApiInterface;
import com.codunite.rechargeapp.R;
import com.codunite.rechargeapp.activity.support.ActivityRaiseComplaint;
import com.codunite.rechargeapp.activity.ActivitySplash;
import com.codunite.rechargeapp.adapter.PaginationAdapter;
import com.codunite.rechargeapp.model.RechargeHistoryModel;
import com.codunite.commonutility.GetFormattedDateTime;
import com.codunite.commonutility.GlobalVariables;
import com.codunite.commonutility.ItemAnimation;
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
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

public class ActivityRechargeHistory extends AppCompatActivity implements View.OnClickListener, WebServiceListener {
    private ImageView imgToolBarBack;
    private TextView txtWalletbal;

    private RecyclerView wallethistoryrv, rvPagination;
    private boolean isFirstLoad = true;
    private NestedScrollView layNestedScroll;
    private int pageNumber = 1;
    private String strFromDate = "", strToDate = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_rechargehistory);
        StartApp();
        resumeApp();
    }

    private SwipeRefreshLayout layrefrsh;
    boolean isDateFrom = true;
    Calendar myCalendar;
    TextView txtFrom, txtTo;

    public void resumeApp() {
//        btnAddWallet = (Button) findViewById(R.id.btn_addwallet);
        txtWalletbal = (TextView) findViewById(R.id.walletbal);

//        btnAddWallet.setOnClickListener(this);

        layNestedScroll = (NestedScrollView) findViewById(R.id.lay_nestedscroll);
        wallethistoryrv = (RecyclerView) findViewById(R.id.history_rv);
        rvPagination = (RecyclerView) findViewById(R.id.rv_pagination);
        rvPagination.setVisibility(View.VISIBLE);

        TextView txteWalletbal = (TextView) findViewById(R.id.ewalletbal);
        txtWalletbal.setText(PreferenceConnector.readString(svContext, PreferenceConnector.WALLETBAL, "0"));
        txteWalletbal.setText(PreferenceConnector.readString(svContext, PreferenceConnector.EWALLETBAL, "0"));

        myCalendar = Calendar.getInstance();
        txtFrom = (TextView) findViewById(R.id.datePicker_from);
        txtTo = (TextView) findViewById(R.id.datePicker_to);
        layrefrsh = (SwipeRefreshLayout) findViewById(R.id.layrefrsh);

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

        txtTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtDate = txtTo.getText().toString().trim();
                if (txtDate.equalsIgnoreCase("Select to date")) {
                    txtDate = GetFormattedDateTime.getcurrentcalDate();
                }
                isDateFrom = false;
                String[] strDate = txtDate.split("-");
                new DatePickerDialog(svContext, date,
                        Integer.parseInt(strDate[0]),
                        Integer.parseInt(strDate[1]) - 1,
                        Integer.parseInt(strDate[2])).show();
            }
        });

        txtFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtDate = txtFrom.getText().toString().trim();
                if (txtDate.equalsIgnoreCase("Select from date")) {
                    txtDate = GetFormattedDateTime.getcurrentcalDate();
                }
                isDateFrom = true;
                String[] strDate = txtDate.split("-");
                new DatePickerDialog(svContext, date,
                        Integer.parseInt(strDate[0]),
                        Integer.parseInt(strDate[1]) - 1,
                        Integer.parseInt(strDate[2])).show();
            }
        });

        ImageView imgSearch = (ImageView) findViewById(R.id.filter_search);
        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((txtFrom.getText().toString().trim()).equalsIgnoreCase("Select from date")) {
                    customToast.showCustomToast(svContext, "Please select from date first", customToast.ToastyInfo);
                } else if ((txtTo.getText().toString().trim()).equalsIgnoreCase("Select to date")) {
                    customToast.showCustomToast(svContext, "Please select to date first", customToast.ToastyInfo);
                } else {
                    isFirstLoad = true;
                    pageNumber = 1;
                    strFromDate = txtFrom.getText().toString().trim();
                    strToDate = txtTo.getText().toString().trim();
                    LoadRechargeHistory(strFromDate, strToDate);
                }
            }
        });

        isFirstLoad = true;
        pageNumber = 1;
        LoadRechargeHistory(strFromDate, strToDate);

        layrefrsh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadUserDataBackground();
                layrefrsh.setRefreshing(false);
            }
        });

        layrefrsh.setColorSchemeColors(
                getResources().getColor(android.R.color.holo_blue_bright),
                getResources().getColor(android.R.color.holo_green_light),
                getResources().getColor(android.R.color.holo_orange_light),
                getResources().getColor(android.R.color.holo_red_light)
        );

    }

    private void loadUserDataBackground() {
        lstUploadData = new LinkedList<>();
        lstUploadData.add(PreferenceConnector.readString(svContext, PreferenceConnector.LOGINEDUSERID, ""));
        lstUploadData.add(PreferenceConnector.readString(svContext, PreferenceConnector.FCMID, ""));
        lstUploadData.add(PreferenceConnector.readString(svContext, PreferenceConnector.DEVICE_ID, ""));
        callWebServiceWithoutLoader(ApiInterface.UPDATEFCM, lstUploadData);
    }

    private void callWebServiceWithoutLoader(String postUrl, LinkedList<String> lstUploadData) {
        WebService webService = new WebService(svContext, postUrl, lstUploadData, this, false);
        webService.LoadDataRetrofit(webService.callReturn());
    }

    private void LoadRechargeHistory(String fromDate, String toDate) {
        lstUploadData = new LinkedList<>();
        lstUploadData.add(PreferenceConnector.readString(svContext, PreferenceConnector.LOGINEDUSERID, ""));
        lstUploadData.add(fromDate);
        lstUploadData.add(toDate);
        lstUploadData.add("" + pageNumber);
        callWebService(ApiInterface.RECHARGEHISTORY, lstUploadData);
    }

    private Context svContext;
    private ShowCustomToast customToast;



    private void StartApp() {
        svContext = this;
        customToast = new ShowCustomToast(svContext);

        ViewGroup root = (ViewGroup) findViewById(R.id.headlayout);
        new NoInternetScreen(svContext, root, ActivityRechargeHistory.this);
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
        txtHeading.setText("Recharge History");
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

    private List<RechargeHistoryModel> lstItems = new ArrayList<>();

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
        if (url.contains(ApiInterface.RECHARGEHISTORY)) {
            int strPageCount = 0;
            try {
                lstItems = new ArrayList<>();

                JSONObject json = new JSONObject(result);
                String str_message = json.getString(TAG_MESSAGE);
                String str_json_status = json.getString(TAG_STATUS);

                try {
                    strPageCount = Integer.parseInt(json.getString("pages"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (str_json_status.equalsIgnoreCase("0")) {
                    customToast.showCustomToast(svContext, str_message, customToast.ToastyError);
                } else {
                    JSONArray data = json.getJSONArray(TAG_DATA);
                    for (int data_i = 0; data_i < (data).length(); data_i++) {
                        JSONObject data_obj = data.getJSONObject(data_i);
                        String str_amount = data_obj.getString(TAG_AMOUNT);
                        String str_datetime = data_obj.getString(TAG_DATETIME);
                        String str_order_id = data_obj.getString(TAG_ORDER_ID);
                        String str_status = data_obj.getString(TAG_STATUS);
                        String str_recharge_id = data_obj.getString("recharge_id");

                        String operator = data_obj.getString("operator");
                        String mobile = data_obj.getString("mobile");
                        String type = data_obj.getString("type");
                        String txtId = data_obj.getString("txid");

                        String beforeBalance = data_obj.getString("before_balance");
                        String afterBalance = data_obj.getString("after_balance");

                        String operatorTranId = "";
                        if (data_obj.has("operator_transcation_id")) {
                            operatorTranId = data_obj.getString("operator_transcation_id");
                        }

                        String memberDeatil = data_obj.getString("member_name") + " (" +
                                data_obj.getString("member_code") + ")";

                        lstItems.add(new RechargeHistoryModel(str_recharge_id, memberDeatil, str_order_id, str_amount, str_datetime, str_status,
                                operator, mobile, type, beforeBalance, afterBalance, txtId, operatorTranId));
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

            layNestedScroll.scrollTo(0, 0);
            if (isFirstLoad) {
                isFirstLoad = false;
                LoadPaginationView(strPageCount);
            }
        } else if (url.contains(ApiInterface.UPDATEFCM)) {
            ActivitySplash.LoadUserData(result, svContext);
            loadToolBar();
        }
    }


    void LoadPaginationView(int paginationSize) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(svContext, LinearLayoutManager.HORIZONTAL, false);
        rvPagination.setLayoutManager(layoutManager);
        rvPagination.setHasFixedSize(true);
        int animation_type = ItemAnimation.LEFT_RIGHT;
        PaginationAdapter mAdapter = new PaginationAdapter(svContext, paginationSize, animation_type);
        rvPagination.setNestedScrollingEnabled(false);
        rvPagination.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new PaginationAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, String obj, int position) {
                pageNumber = Integer.parseInt(obj);
                LoadRechargeHistory(strFromDate, strToDate);
            }
        });
        if (paginationSize <= 1) {
            rvPagination.setVisibility(View.GONE);
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