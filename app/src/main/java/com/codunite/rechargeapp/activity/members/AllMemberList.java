package com.codunite.rechargeapp.activity.members;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codunite.commonutility.CheckInternet;
import com.codunite.commonutility.ItemAnimation;
import com.codunite.commonutility.NoInternetScreen;
import com.codunite.commonutility.PreferenceConnector;
import com.codunite.commonutility.ShowCustomToast;
import com.codunite.commonutility.WebService;
import com.codunite.commonutility.WebServiceListener;
import com.codunite.commonutility.retrofit.ApiInterface;
import com.codunite.rechargeapp.R;
import com.codunite.rechargeapp.adapter.MemberListAdapter;
import com.codunite.rechargeapp.model.MemberListModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class AllMemberList extends AppCompatActivity implements View.OnClickListener, WebServiceListener {
    private Context svContext;
    private ShowCustomToast customToast;
    private NoInternetScreen errrorScreen;

    private CheckInternet checkNetwork;
    RecyclerView rv_members;
    List<String> lstMember = new ArrayList<>();
    Button btn_addmember;
    LinkedList<String> lstUploadData = new LinkedList<>();

    private List<MemberListModel> lstItems = new ArrayList<>();
    private String strWalletBalance = "";

    public static final String TAG_USER_CODE = "user_code";
    public static final String TAG_USER_ID = "user_id";
    public static final String TAG_NAME = "name";
    public static final String TAG_MOBILE = "mobile";
    public static final String TAG_EMAIL = "email";
    public static final String TAG_MESSAGE = "message";
    public static final String TAG_STATUS = "status";
    public static final String TAG_DATA = "data";
    public static final String TAG_WALLET_BALANCE = "wallet_balance";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_member_list);
        StartApp();
    }


    private void StartApp() {
        svContext = this;
        customToast = new ShowCustomToast(svContext);
        checkNetwork = new CheckInternet(svContext);
        ViewGroup root = (ViewGroup) findViewById(R.id.headlayout);
        errrorScreen = new NoInternetScreen(svContext, root, AllMemberList.this);
        loadToolBar();
        resumeApp();
    }

    private void resumeApp() {
        lstUploadData = new LinkedList<>();
        lstUploadData.add(PreferenceConnector.readString(svContext, PreferenceConnector.LOGINEDUSERID, ""));
        callWebService(ApiInterface.GETMEMBERLIST, lstUploadData);

        rv_members = (RecyclerView) findViewById(R.id.rv_members);
        btn_addmember = (Button) findViewById(R.id.btn_addmember);
        btn_addmember.setOnClickListener(this);

//        memberListAdapter.setOnItemClickListener(new MemberListAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, String obj, int position) {
//                customToast.showCustomToast(svContext,String.valueOf(position),customToast.ToastySuccess);
//            }
//        });
    }

    private void loadToolBar() {
        ImageView imgToolBarBack = (ImageView) findViewById(R.id.img_back);
        imgToolBarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        TextView txtHeading = (TextView) findViewById(R.id.heading);
        txtHeading.setText("View All Member");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.btn_addmember:
                Intent svIntent = new Intent(svContext, AddEditMember.class);
                startActivity(svIntent);
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                break;
            default:
                break;
        }
    }

    private void callWebService(String postUrl, LinkedList<String> lstUploadData) {
        WebService webService = new WebService(svContext, postUrl, lstUploadData, this);
        webService.LoadDataRetrofit(webService.callReturn());
    }

    @Override
    public void onWebServiceActionComplete(String result, String url) {
        System.out.println(result + ".........jsonresponse....." + url);
        if (url.contains(ApiInterface.GETMEMBERLIST)) {
            try {
                lstItems = new ArrayList<>();

                JSONObject json = new JSONObject(result);
                String str_message = json.getString(TAG_MESSAGE);
                String str_status = json.getString(TAG_STATUS);
                if (str_status.equalsIgnoreCase("0")) {
                    customToast.showCustomToast(svContext, str_message, customToast.ToastyError);
                } else {
                    if (json.has(TAG_WALLET_BALANCE)) {
                        strWalletBalance = json.getString(TAG_WALLET_BALANCE);
                    }

                    JSONArray data = json.getJSONArray(TAG_DATA);
                    for (int data_i = 0; data_i < data.length(); data_i++) {
                        JSONObject data_obj = data.getJSONObject(data_i);
                        String str_user_code = data_obj.getString(TAG_USER_CODE);
                        String str_user_id = data_obj.getString(TAG_USER_ID);
                        String str_name = data_obj.getString(TAG_NAME);
                        String str_mobile = data_obj.getString(TAG_MOBILE);
                        String str_email = data_obj.getString(TAG_EMAIL);
                        String wall_bal = data_obj.getString("wallet_balance");
                        String e_wall_bal = data_obj.getString("e_wallet_balance");

                        lstItems.add(new MemberListModel(str_user_id, str_name, str_mobile, str_email, str_user_code,
                                wall_bal));
                    }
                }
            } catch (JSONException e) {
                customToast.showCustomToast(svContext, "Some error occured", customToast.ToastyError);
                e.printStackTrace();
            }

            PreferenceConnector.writeString(svContext, PreferenceConnector.WALLETBAL, strWalletBalance);

            LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            rv_members.setLayoutManager(layoutManager);
            rv_members.setHasFixedSize(true);
            int animation_type = ItemAnimation.LEFT_RIGHT;
            MemberListAdapter mAdapter = new MemberListAdapter(this, lstItems, animation_type);
            rv_members.setNestedScrollingEnabled(false);
            rv_members.setAdapter(mAdapter);
            mAdapter.setOnItemClickListener(new MemberListAdapter.OnItemClickListener() {
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

    private void hideKeyboard() {
        InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        View view = this.getCurrentFocus();
        if (view != null) {
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}