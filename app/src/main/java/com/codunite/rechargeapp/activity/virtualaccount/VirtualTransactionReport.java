package com.codunite.rechargeapp.activity.virtualaccount;

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
import com.codunite.rechargeapp.activity.ActivityMain;
import com.codunite.rechargeapp.activity.ActivitySplash;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

public class VirtualTransactionReport extends AppCompatActivity implements View.OnClickListener, WebServiceListener {
    private ImageView imgToolBarBack,iv_from,iv_to;
    private RecyclerView wallethistoryrv;
    private Button btntransfer;

    private Context svContext;
    private ShowCustomToast customToast;
    private CheckInternet checkNetwork;
    private NoInternetScreen errrorScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_virtual_account_report);
        StartApp();
        resumeApp();
    }

    private SwipeRefreshLayout layrefrsh;
    boolean isDateFrom = true;
    Calendar myCalendar;
    TextView txtFrom, txtTo;

    public void resumeApp() {
        btntransfer = (Button) findViewById(R.id.btn_transfer);

        btntransfer.setOnClickListener(this);
        wallethistoryrv = (RecyclerView) findViewById(R.id.wallethistory_rv);

        //       TextView txteWalletbal = (TextView) findViewById(R.id.ewalletbal);
        //      txteWalletbal.setText(PreferenceConnector.readString(svContext, PreferenceConnector.EWALLETBAL, "0"));

        myCalendar = Calendar.getInstance();
        iv_from=(ImageView)findViewById(R.id.iv_from);
        iv_to=(ImageView)findViewById(R.id.iv_to);
        txtFrom = (TextView) findViewById(R.id.datePicker_from);
        txtTo = (TextView) findViewById(R.id.datePicker_to);
        layrefrsh = (SwipeRefreshLayout) findViewById(R.id.layrefrsh);

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


    }

    private void LoadRechargeHistory(String fromDate, String toDate) {
        lstUploadData = new LinkedList<>();
        lstUploadData.add(PreferenceConnector.readString(svContext, PreferenceConnector.LOGINEDUSERID, ""));
        lstUploadData.add(fromDate);
        lstUploadData.add(toDate);
        lstUploadData.add(strSearchKey);
        callWebService(ApiInterface.VIRTUAL_ACCOUNT_REPORT, lstUploadData);
    }

    private String strSearchKey = "";

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
        errrorScreen = new NoInternetScreen(svContext, root, VirtualTransactionReport.this);
        if (!GlobalVariables.CUSTOMFONTNAME.equals("")) {
            Typeface font = Typeface.createFromAsset(getAssets(), GlobalVariables.CUSTOMFONTNAME);
            FontUtils.setFont(root, font);
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
        txtHeading.setText("Virtual Transaction Report");

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
                ActivityMain.onDrawerItemClick("My Virtual Account", svContext,"");
                break;
            default:
                break;
        }
    }

    private RelativeLayout layConnection, progressbarInternet;
    private TextView textError;
    private ProgressBar progressBarLayconnection;

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

    private List<VirtualTransactionReportModel> lstItems = new ArrayList<>();

    public static final String TAG_DATA = "data";
    public static final String TAG_STATUS = "status";
    public static final String TAG_MESSAGE = "message";

    @Override
    public void onWebServiceActionComplete(String result, String url) {
        System.out.println(result + ".........jsonresponse....." + url);
        if (url.contains(ApiInterface.VIRTUAL_ACCOUNT_REPORT)) {
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
                        String virtualAccountNo = data_obj.getString("virtual_account_no");
                        String mode = data_obj.getString("mode");
                        String utr = data_obj.getString("utr");
                        String bankTxnNo = data_obj.getString("bank_txn_no");
                        String amount = data_obj.getString("amount");
                        String payerName = data_obj.getString("payer_name");
                        String payerAccountNo = data_obj.getString("payer_account_no");
                        String remark = data_obj.getString("remark");
                        String datetime = data_obj.getString("datetime");

                        lstItems.add(new VirtualTransactionReportModel(virtualAccountNo, mode, utr, bankTxnNo, amount, payerName, payerAccountNo, remark, datetime));
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
            VirtualAccountReportAdapter mAdapter = new VirtualAccountReportAdapter(this, lstItems, animation_type);
            wallethistoryrv.setAdapter(mAdapter);
            mAdapter.setOnItemClickListener(new VirtualAccountReportAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, VirtualTransactionReportModel obj, int position) {
                }
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
}