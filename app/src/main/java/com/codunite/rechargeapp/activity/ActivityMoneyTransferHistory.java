package com.codunite.rechargeapp.activity;

import android.app.DatePickerDialog;
import android.app.SearchManager;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import com.codunite.commonutility.CheckInternet;
import com.codunite.commonutility.GetFormattedDateTime;
import com.codunite.commonutility.GlobalData;
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
import com.codunite.rechargeapp.adapter.PayoutTransferReportAdapter;
import com.codunite.rechargeapp.model.PayoutTransferReportModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

public class ActivityMoneyTransferHistory extends AppCompatActivity implements View.OnClickListener, WebServiceListener {
    private ImageView imgToolBarBack, iv_from, iv_to;
    private RecyclerView wallethistoryrv;
    private Button btntransfer;
    boolean isDateFrom = true;
    Calendar myCalendar;
    TextView txtFrom, txtTo;
    private TextView txtWalletbal;
    private String strSearchKey = "";
    private Context svContext;
    private ShowCustomToast customToast;
    private CheckInternet checkNetwork;
    private NoInternetScreen errrorScreen;
    private List<PayoutTransferReportModel> lstItems = new ArrayList<>();

    private String strFromDate = "", strToDate = "";
    private SwipeRefreshLayout layrefrsh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_money_transfer);
        StartApp();
        resumeApp();
    }

    public void resumeApp() {
        txtWalletbal = (TextView) findViewById(R.id.walletbal);
        iv_from = (ImageView) findViewById(R.id.iv_from);
        iv_to = (ImageView) findViewById(R.id.iv_to);
        wallethistoryrv = (RecyclerView) findViewById(R.id.wallethistory_rv);
        TextView txteWalletbal = (TextView) findViewById(R.id.ewalletbal);
        txtWalletbal.setText(PreferenceConnector.readString(svContext, PreferenceConnector.WALLETBAL, "0"));
        txteWalletbal.setText(PreferenceConnector.readString(svContext, PreferenceConnector.EWALLETBAL, "0"));

        myCalendar = Calendar.getInstance();
        layrefrsh = (SwipeRefreshLayout) findViewById(R.id.layrefrsh);
        txtFrom = (TextView) findViewById(R.id.datePicker_from);
        txtTo = (TextView) findViewById(R.id.datePicker_to);

        txtFrom.setText(GetFormattedDateTime.getcurrentcalDate());
        txtTo.setText(GetFormattedDateTime.getcurrentcalDate());
        LoadRechargeHistory(txtFrom.getText().toString(), txtTo.getText().toString());
        setSearchView();

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                int month = monthOfYear + 1;
                String selectedDate = year + "-" + (month >= 10 ? month : "0" + month)
                        + "-" + (dayOfMonth >= 10 ? dayOfMonth : "0" + dayOfMonth);
                if (isDateFrom) {
                    txtFrom.setText(selectedDate);
                } else {
                    txtTo.setText(selectedDate);
                }
            }
        };

        iv_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isDateFrom = false;
                String[] strDate = txtTo.getText().toString().trim().split("-");
                new DatePickerDialog(svContext, date,
                        Integer.parseInt(strDate[0]),
                        Integer.parseInt(strDate[1]) - 1,
                        Integer.parseInt(strDate[2])).show();
            }
        });
        iv_from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isDateFrom = true;
                String[] strDate = txtFrom.getText().toString().trim().split("-");
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
                    LoadRechargeHistory(txtFrom.getText().toString().trim(), txtTo.getText().toString().trim());
                }
            }
        });

        layrefrsh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadUserDataBackground();
                layrefrsh.setRefreshing(false);
            }
        });

        layrefrsh.setColorSchemeColors(
                getResources().getColor(R.color.colorAccent),
                getResources().getColor(R.color.colorAccent),
                getResources().getColor(R.color.colorAccent),
                getResources().getColor(R.color.colorAccent)
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
        lstUploadData.add(strSearchKey);
        callWebService(ApiInterface.MTRANSFERHISTORY, lstUploadData);
    }


    private void setSearchView() {
        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) findViewById(R.id.searchview);
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

    private void StartApp() {
        svContext = this;
        customToast = new ShowCustomToast(svContext);
        checkNetwork = new CheckInternet(svContext);
        ViewGroup root = (ViewGroup) findViewById(R.id.headlayout);
        errrorScreen = new NoInternetScreen(svContext, root, ActivityMoneyTransferHistory.this);
        if (!GlobalVariables.CUSTOMFONTNAME.equals("")) {
            Typeface font = Typeface.createFromAsset(getAssets(), GlobalVariables.CUSTOMFONTNAME);
            FontUtils.setFont(root, font);
        }
        if (PreferenceConnector.readBoolean(svContext, PreferenceConnector.ISDARKTHEME, false)) {
// FontUtils.setThemeColor(root, svContext, true);
        } else {
// FontUtils.setThemeColor(root, svContext, false);
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

    private void loadToolBar() {
        imgToolBarBack = (ImageView) findViewById(R.id.img_back);
        imgToolBarBack.setOnClickListener(this);

        TextView txtHeading = (TextView) findViewById(R.id.heading);
        txtHeading.setText("Money Transfer History");

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.btn_transfer:
                ActivityMain.onDrawerItemClick("Transfer", svContext,"");
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
        System.out.println(result + ".........jsonresponse....." + url);
        if (url.contains(ApiInterface.MTRANSFERHISTORY)) {
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
                        String str_amount = data_obj.getString("transfer_amount");
                        String str_datetime = data_obj.getString("datetime");
                        String str_status = data_obj.getString("status");
                        String str_member_id = data_obj.getString("member_id");

                        String str_ifsc = data_obj.getString("ifsc");
                        String mobile = data_obj.getString("mobile");
                        String str_txn_id = data_obj.getString("transaction_id");

                        String str_rrn = data_obj.getString("rrn");
                        String str_name = data_obj.getString("account_holder_name");
                        String str_account_no = "";
                        if (data_obj.has("account_no")) {
                            str_account_no = data_obj.getString("account_no");
                        }

                        lstItems.add(new PayoutTransferReportModel(str_member_id, str_txn_id, str_amount,
                                str_datetime, str_status, str_rrn, mobile, str_name, str_ifsc, str_account_no));
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
            PayoutTransferReportAdapter mAdapter = new PayoutTransferReportAdapter(this, lstItems, animation_type, true);
            wallethistoryrv.setAdapter(mAdapter);
            mAdapter.setOnItemClickListener(new PayoutTransferReportAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, String obj, int position) {

                }
            });

            //      mAdapter.setOnComplaintItemClickListener(new PayoutTransferReportAdapter.OnComplaintItemClickListener() {
            //         @Override
            //       public void onComplaintItemClick(View view, String obj, int position) {
            //             Intent intent = new Intent(svContext, ActivityRaiseComplaint.class);
            //              intent.putExtra("comp_from", false);
            //             intent.putExtra("rechg_id", lstItems.get(position).getStr_recharge_id());
            //             intent.putExtra("rechg_amount", lstItems.get(position).getStr_amount());
            //           startActivity(intent);
            //       }
            //    });
        } else if (url.contains(ApiInterface.UPDATEFCM)) {
            ActivitySplash.LoadUserData(result, svContext);
            loadToolBar();
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