package com.codunite.rechargeapp.activity.reports;

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
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codunite.commonutility.CheckInternet;
import com.codunite.commonutility.GlobalData;
import com.codunite.commonutility.retrofit.ApiInterface;
import com.codunite.rechargeapp.R;
import com.codunite.rechargeapp.activity.ActivityMain;
import com.codunite.rechargeapp.activity.wallet.ActivityAddFundRequest;
import com.codunite.rechargeapp.adapter.PaginationAdapter;
import com.codunite.rechargeapp.adapter.WalletHistoryAdapter;
import com.codunite.rechargeapp.model.WalletHistoryModel;
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

public class ActivityWalletHistory extends AppCompatActivity implements View.OnClickListener, WebServiceListener {
    public static final String TAG_DATA = "data";
    public static final String TAG_AMOUNT = "amount";
    public static final String TAG_DATETIME = "datetime";
    public static final String TAG_DESCRIPTION = "description";
    public static final String TAG_TYPE = "type";
    public static final String TAG_MESSAGE = "message";
    public static final String TAG_STATUS = "status";
    private ImageView imgToolBarBack;
    private RecyclerView wallethistoryrv;
    private TextView txtWalletbal;
    private Button btnAddWallet;
    private CardView cvAddWallet, cardShowBalance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_wallethistory);
        StartApp();

        resumeApp();
    }

    boolean isDateFrom = true;
    Calendar myCalendar;
    TextView txtFrom, txtTo;
    LinearLayout layFilter;

    public void resumeApp() {
        wallethistoryrv = (RecyclerView) findViewById(R.id.wallethistory_rv);
        txtWalletbal = (TextView) findViewById(R.id.walletbal);
        btnAddWallet = (Button) findViewById(R.id.btn_addwallet);
        txtWalletbal.setVisibility(View.INVISIBLE);
        cvAddWallet = (CardView) findViewById(R.id.card_addwallet);
        cardShowBalance = (CardView) findViewById(R.id.card_wallbal);
        cvAddWallet.setVisibility(View.INVISIBLE);
        cardShowBalance.setVisibility(View.INVISIBLE);
        layFilter = (LinearLayout) findViewById(R.id.lay_filter);
        layFilter.setVisibility(View.INVISIBLE);
        btnAddWallet.setOnClickListener(this);

        TextView txteWalletbal = (TextView) findViewById(R.id.ewalletbal);
        txtWalletbal.setText(PreferenceConnector.readString(svContext, PreferenceConnector.WALLETBAL, "0"));
        txteWalletbal.setText(PreferenceConnector.readString(svContext, PreferenceConnector.EWALLETBAL, "0"));

        myCalendar = Calendar.getInstance();
        txtFrom = (TextView) findViewById(R.id.datePicker_from);
        txtTo = (TextView) findViewById(R.id.datePicker_to);

        txtFrom.setText(GetFormattedDateTime.getcurrentcalDate());
        txtTo.setText(GetFormattedDateTime.getcurrentcalDate());
        LoadHistory(txtFrom.getText().toString(), txtTo.getText().toString());
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


        txtTo.setOnClickListener(new View.OnClickListener() {
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

        txtFrom.setOnClickListener(new View.OnClickListener() {
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
                    LoadHistory(txtFrom.getText().toString().trim(), txtTo.getText().toString().trim());
                }
            }
        });
    }

    private void LoadHistory(String fromDate, String toDate) {
        lstUploadData = new LinkedList<>();
        lstUploadData.add(PreferenceConnector.readString(svContext, PreferenceConnector.LOGINEDUSERID, ""));
        lstUploadData.add(fromDate);
        lstUploadData.add(toDate);
        lstUploadData.add(strSearchKey);
        callWebService(ApiInterface.WALLETHISTORY, lstUploadData);
    }

    private String strSearchKey = "";
    private SearchView searchView;

    private void setSearchView() {
        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) findViewById(R.id.searchview);
        searchView.setVisibility(View.INVISIBLE);
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

    private Context svContext;
    private ShowCustomToast customToast;
    private CheckInternet checkNetwork;
    private NoInternetScreen errrorScreen;

    private void StartApp() {
        svContext = this;
        customToast = new ShowCustomToast(svContext);
        checkNetwork = new CheckInternet(svContext);
        ViewGroup root = (ViewGroup) findViewById(R.id.headlayout);
        errrorScreen = new NoInternetScreen(svContext, root, ActivityWalletHistory.this);
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
        txtHeading.setText("R-Wallet History");

        TextView toolbar_txt_walletbal = (TextView) findViewById(R.id.toolbar_txt_walletbal);
        toolbar_txt_walletbal.setText(ActivityMain.ShowBalance(svContext));

        TextView toolbar_txt_ewalletbal = (TextView) findViewById(R.id.toolbar_txt_ewalletbal);
        toolbar_txt_ewalletbal.setText(ActivityMain.ShoweBalance(svContext));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                onBackPressed();
                break;
            case R.id.btn_addwallet:
                ActivityAddFundRequest.OpenAddFundRequest(svContext,"");
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

    private List<WalletHistoryModel> lstItems = new ArrayList<>();

    @Override
    public void onWebServiceActionComplete(String result, String url) {
        System.out.println(result + ".........jsonresponse....." + url);
        if (url.contains(ApiInterface.WALLETHISTORY)) {
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
                        String str_openbal = data_obj.getString("before_balance");
                        String str_closebal = data_obj.getString("after_balance");


                        lstItems.add(new WalletHistoryModel(str_amount, str_datetime, str_description, str_type, str_openbal, str_closebal));
                    }
                }
            } catch (JSONException e) {
                customToast.showCustomToast(svContext, "Some error occured", customToast.ToastyError);
                e.printStackTrace();
            }

            cvAddWallet.setVisibility(View.VISIBLE);
            cardShowBalance.setVisibility(View.VISIBLE);
            layFilter.setVisibility(View.VISIBLE);
            searchView.setVisibility(View.VISIBLE);

            txtWalletbal.setVisibility(View.VISIBLE);

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