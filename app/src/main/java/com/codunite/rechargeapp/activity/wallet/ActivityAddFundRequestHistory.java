package com.codunite.rechargeapp.activity.wallet;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codunite.commonutility.CheckInternet;
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
import com.codunite.rechargeapp.adapter.RequestHistoryAdapter;
import com.codunite.rechargeapp.model.RequestHistoryModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ActivityAddFundRequestHistory extends AppCompatActivity implements View.OnClickListener, WebServiceListener {
    private Context svContext;
    private ShowCustomToast customToast;
    private CheckInternet checkNetwork;
    private NoInternetScreen errrorScreen;
    private ImageView imgToolBarBack;
    private RecyclerView wallethistoryrv;
    private TextView txtWalletbal;
    private TextView btnAddRequest;
    private String actType = "";

    private RelativeLayout layConnection, progressbarInternet;
    private TextView textError;
    private ProgressBar progressBarLayconnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_requesthistory_wallet);
        actType = getIntent().getStringExtra("actType");
        StartApp();

        resumeApp();
    }

    public void resumeApp() {
        wallethistoryrv = (RecyclerView) findViewById(R.id.wallethistory_rv);
        txtWalletbal = (TextView) findViewById(R.id.walletbal);
        txtWalletbal.setVisibility(View.INVISIBLE);

        btnAddRequest = findViewById(R.id.add_request);
        btnAddRequest.setOnClickListener(this);

        lstUploadData = new LinkedList<>();
        lstUploadData.add(PreferenceConnector.readString(svContext, PreferenceConnector.LOGINEDUSERID, ""));

        if (actType.equals(ActivityMain.R_WALLET_MYFUNDREQUEST)) {
            txtWalletbal.setText(PreferenceConnector.readString(svContext, PreferenceConnector.WALLETBAL, ""));
            callWebService(ApiInterface.REQUESTHISTORY, lstUploadData);
        } else if (actType.equals(ActivityMain.E_WALLET_MYFUNDREQUEST)) {
            txtWalletbal.setText(PreferenceConnector.readString(svContext, PreferenceConnector.EWALLETBAL, ""));
            callWebService(ApiInterface.E_REQUESTHISTORY, lstUploadData);
        }

    }



    private void StartApp() {
        svContext = this;
        customToast = new ShowCustomToast(svContext);
        checkNetwork = new CheckInternet(svContext);
        ViewGroup root = (ViewGroup) findViewById(R.id.headlayout);
        errrorScreen = new NoInternetScreen(svContext, root, ActivityAddFundRequestHistory.this);
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
        txtHeading.setText(actType + " History");

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
            case R.id.add_request:
                Intent svIntent = new Intent(svContext, ActivityAddFundRequest.class);
                svIntent.putExtra("actType", actType);
                startActivity(svIntent);
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
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

    private List<RequestHistoryModel> lstItems = new ArrayList<>();

    @Override
    public void onWebServiceActionComplete(String result, String url) {
        System.out.println(result + ".........jsonresponse....." + url);
        if (url.contains(ApiInterface.REQUESTHISTORY)) {
            try {
                lstItems = new ArrayList<>();
//                {"request_id":"1613812020256","txnid":"123456",status":"Pending"]}
                JSONObject json = new JSONObject(result);
                String str_message = json.getString("message");
                String str_status = json.getString("status");
                if (str_status.equalsIgnoreCase("0")) {
                    customToast.showCustomToast(svContext, str_message, customToast.ToastyError);
                } else {
                    JSONArray data = json.getJSONArray("data");
                    for (int data_i = 0; data_i < ((JSONArray) data).length(); data_i++) {
                        JSONObject data_obj = data.getJSONObject(data_i);
                        String str_amount = data_obj.getString("request_amount");
                        String str_datetime = data_obj.getString("datetime");
                        String str_requestamount = data_obj.getString("request_amount");
                        String request_id = data_obj.getString("request_id");
                        String txnid = data_obj.getString("txnid");
                        String status = data_obj.getString("status");

                        lstItems.add(new RequestHistoryModel(str_amount, str_datetime, str_requestamount, request_id,
                                txnid, status));
                    }
                }
            } catch (JSONException e) {
                customToast.showCustomToast(svContext, "Some error occured", customToast.ToastyError);
                e.printStackTrace();
            }

            txtWalletbal.setVisibility(View.VISIBLE);

            LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            wallethistoryrv.setLayoutManager(layoutManager);
            wallethistoryrv.setHasFixedSize(true);
            int animation_type = ItemAnimation.LEFT_RIGHT;
            RequestHistoryAdapter mAdapter = new RequestHistoryAdapter(this, lstItems, animation_type);
            wallethistoryrv.setNestedScrollingEnabled(false);
            wallethistoryrv.setAdapter(mAdapter);
            mAdapter.setOnItemClickListener(new RequestHistoryAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, String obj, int position) {

                }
            });
        } else if (url.contains(ApiInterface.E_REQUESTHISTORY)) {
            try {
                lstItems = new ArrayList<>();
//                {"request_id":"1613812020256","txnid":"123456",status":"Pending"]}
                JSONObject json = new JSONObject(result);
                String str_message = json.getString("message");
                String str_status = json.getString("status");
                if (str_status.equalsIgnoreCase("0")) {
                    customToast.showCustomToast(svContext, str_message, customToast.ToastyError);
                } else {
                    JSONArray data = json.getJSONArray("data");
                    for (int data_i = 0; data_i < ((JSONArray) data).length(); data_i++) {
                        JSONObject data_obj = data.getJSONObject(data_i);
                        String str_amount = data_obj.getString("request_amount");
                        String str_datetime = data_obj.getString("datetime");
                        String str_requestamount = data_obj.getString("request_amount");
                        String request_id = data_obj.getString("request_id");
                        String txnid = data_obj.getString("txnid");
                        String status = data_obj.getString("status");

                        lstItems.add(new RequestHistoryModel(str_amount, str_datetime, str_requestamount, request_id,
                                txnid, status));
                    }
                }
            } catch (JSONException e) {
                customToast.showCustomToast(svContext, "Some error occured", customToast.ToastyError);
                e.printStackTrace();
            }

            txtWalletbal.setVisibility(View.VISIBLE);

            LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            wallethistoryrv.setLayoutManager(layoutManager);
            wallethistoryrv.setHasFixedSize(true);
            int animation_type = ItemAnimation.LEFT_RIGHT;
            RequestHistoryAdapter mAdapter = new RequestHistoryAdapter(this, lstItems, animation_type);
            wallethistoryrv.setNestedScrollingEnabled(false);
            wallethistoryrv.setAdapter(mAdapter);
            mAdapter.setOnItemClickListener(new RequestHistoryAdapter.OnItemClickListener() {
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