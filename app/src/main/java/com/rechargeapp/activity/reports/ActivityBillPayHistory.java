package com.rechargeapp.activity.reports;

import android.app.DatePickerDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.commonutility.retrofit.ApiInterface;
import com.codunite.rechargeapp.R;
import com.rechargeapp.activity.support.ActivityRaiseComplaint;
import com.rechargeapp.adapter.PaginationAdapter;
import com.rechargeapp.adapter.RechargeHistoryAdapter;
import com.rechargeapp.model.RechargeHistoryModel;
import com.commonutility.GetFormattedDateTime;
import com.commonutility.GlobalVariables;
import com.commonutility.ItemAnimation;
import com.commonutility.NoInternetScreen;
import com.commonutility.PreferenceConnector;
import com.commonutility.ShowCustomToast;
import com.commonutility.WebService;
import com.commonutility.WebServiceListener;
import com.commonutility.customfont.FontUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

public class ActivityBillPayHistory extends AppCompatActivity implements View.OnClickListener, WebServiceListener {
    private ImageView imgToolBarBack,iv_from,iv_to;
    private TextView txtWalletbal;
    private Context svContext;
    private ShowCustomToast customToast;

    private RecyclerView wallethistoryrv, rvPagination;
    private boolean isFirstLoad = true;
    private NestedScrollView layNestedScroll;
    private int pageNumber = 1;
    private String strFromDate = "", strToDate = "";
    private String strSearchKey = "";
    boolean isDateFrom = true;
    Calendar myCalendar;
    TextView txtFrom, txtTo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.act_billpayhistory);
        setContentView(R.layout.act_rechargehistory);
        StartApp();

        resumeApp();
    }


    public void resumeApp() {
        txtWalletbal = (TextView) findViewById(R.id.walletbal);
        iv_from=(ImageView)findViewById(R.id.iv_from);
        iv_to=(ImageView)findViewById(R.id.iv_to);
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
        //LoadRechargeHistory(txtFrom.getText().toString(), txtTo.getText().toString());

        txtFrom.setText(GetFormattedDateTime.getcurrentcalDate());
        txtTo.setText(GetFormattedDateTime.getcurrentcalDate());


        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                int month = monthOfYear + 1;
                String selectedDate = year + "-" + (month >= 10 ? month : "0" + month)
                        + "-" + (dayOfMonth >= 10 ? dayOfMonth : "0" + dayOfMonth);

                if(isDateFrom){
                    txtFrom.setText(selectedDate);
                }else {
                    txtTo.setText(selectedDate);
                }
            }

        };

        iv_to.setOnClickListener(new View.OnClickListener() {
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

        iv_from.setOnClickListener(new View.OnClickListener() {
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
                if ((txtFrom.getText().toString().trim()).equalsIgnoreCase("Select from date")){
                    customToast.showCustomToast(svContext, "Please select from date first", customToast.ToastyInfo);
                }else if((txtTo.getText().toString().trim()).equalsIgnoreCase("Select to date")){
                    customToast.showCustomToast(svContext, "Please select to date first", customToast.ToastyInfo);
                }else {
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
        setSearchView();

    }

    private void LoadRechargeHistory(String fromDate, String toDate){
        lstUploadData = new LinkedList<>();
        lstUploadData.add(PreferenceConnector.readString(svContext, PreferenceConnector.LOGINEDUSERID, ""));
        lstUploadData.add(fromDate);
        lstUploadData.add(toDate);
        lstUploadData.add(strSearchKey);
        //lstUploadData.add("" + pageNumber);
        callWebService(ApiInterface.GETBBSPHISTORY, lstUploadData);
    }




    private void StartApp() {
        svContext = this;
        customToast = new ShowCustomToast(svContext);
        ViewGroup root = (ViewGroup) findViewById(R.id.headlayout);
        new NoInternetScreen(svContext, root, ActivityBillPayHistory.this);
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
        txtHeading.setText("Billpay History");
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
        if (url.contains(ApiInterface.GETBBSPHISTORY)) {
            int strPageCount = 0;
            try {
                lstItems = new ArrayList<>();

                JSONObject json = new JSONObject(result);
                String str_message = json.getString(TAG_MESSAGE);
                String str_json_status = json.getString(TAG_STATUS);


//                try {
//                    strPageCount = Integer.parseInt(json.getString("pages"));
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }

                if (str_json_status.equalsIgnoreCase("0")) {
                    customToast.showCustomToast(svContext, str_message, customToast.ToastyError);
                } else {
                    JSONArray data = json.getJSONArray(TAG_DATA);
                    for (int data_i = 0; data_i < (data).length(); data_i++) {
                        JSONObject data_obj = data.getJSONObject(data_i);
                        String str_amount = data_obj.getString("amount");
                        String str_datetime = data_obj.getString("datetime");
                        String str_order_id = data_obj.getString("order_id");
                        String str_status = data_obj.getString("status");

                        String operator = data_obj.getString("operator");
                        String mobile = data_obj.getString("mobile");
                        String type = data_obj.getString("type");

                        String beforeBalance = data_obj.getString("before_balance");
                        String afterBalance = data_obj.getString("after_balance");
                        String str_account_number = data_obj.getString("account_number");

                        String memberDetail = data_obj.getString("member_name") +" ("+
                                data_obj.getString("member_code") +")";
                        String txtId = data_obj.getString("txid");

                        String operatorTranId = "";
                        if (data_obj.has("operator_transcation_id")) {
                            operatorTranId = data_obj.getString("operator_transcation_id");
                        }

//                        String str_recharge_id = data_obj.getString("recharge_id");
                        lstItems.add(new RechargeHistoryModel("", memberDetail,str_order_id, str_amount, str_datetime,
                                str_status, operator,mobile, type, beforeBalance, afterBalance, txtId,str_account_number,
                                operatorTranId,""));
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
            RechargeHistoryAdapter mAdapter = new RechargeHistoryAdapter(this, lstItems, animation_type, false);
            wallethistoryrv.setAdapter(mAdapter);
            mAdapter.setOnItemClickListener(new RechargeHistoryAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, String obj, int position) {

                }
            });

            mAdapter.setOnComplaintItemClickListener(new RechargeHistoryAdapter.OnComplaintItemClickListener() {
                @Override
                public void onComplaintItemClick(View view, String obj, int position) {
                    Intent intent = new Intent(svContext, ActivityRaiseComplaint.class);
                    intent.putExtra("comp_from", true);
                    intent.putExtra("rechg_id", lstItems.get(position).getStr_order_id());
                    intent.putExtra("rechg_amount", lstItems.get(position).getStr_amount());
                    startActivity(intent);
                }
            });

//            layNestedScroll.scrollTo(0, 0);
//            if (isFirstLoad) {
//                isFirstLoad = false;
//                LoadPaginationView(strPageCount);
//            }
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

    private void setSearchView() {
        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) findViewById(R.id.searchview);
        searchView.setVisibility(View.VISIBLE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                strSearchKey = query;
                //mAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                strSearchKey = query;
                //mAdapter.getFilter().filter(query);
                return false;
            }
        });
    }
}