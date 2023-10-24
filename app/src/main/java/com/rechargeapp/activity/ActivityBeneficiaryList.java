package com.rechargeapp.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.commonutility.CheckInternet;
import com.commonutility.GlobalData;
import com.commonutility.GlobalVariables;
import com.commonutility.ItemAnimation;
import com.commonutility.NoInternetScreen;
import com.commonutility.PreferenceConnector;
import com.commonutility.ShowCustomToast;
import com.commonutility.WebService;
import com.commonutility.WebServiceListener;
import com.commonutility.customfont.FontUtils;
import com.commonutility.retrofit.ApiInterface;
import com.codunite.rechargeapp.R;
import com.rechargeapp.adapter.BeneficiaryListAdapter;
import com.rechargeapp.model.BeneficiaryListModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ActivityBeneficiaryList extends AppCompatActivity implements View.OnClickListener, WebServiceListener {
    private ImageView imgToolBarBack;
    private RecyclerView wallethistoryrv;
    private Button btnAddBeneficiary;
    public Button btnUpdateSender;
    private String loadCurrentUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_beneficiary_list);
        StartApp();
        resumeApp();
    }

    public static void OpenBeneficirayList(Context svContext, String remiiterNo) {
        Intent svIntent = new Intent(svContext, ActivityBeneficiaryList.class);
        svIntent.putExtra("logintransfer", remiiterNo);
        svContext.startActivity(svIntent);
    }

    public void resumeApp() {
        loadCurrentUrl = getIntent().getStringExtra("logintransfer").toString();

        wallethistoryrv = (RecyclerView) findViewById(R.id.wallethistory_rv);
        btnAddBeneficiary = (Button) findViewById(R.id.btn_add_beneficiary);
        btnUpdateSender = (Button) findViewById(R.id.btn_remitter);
        btnAddBeneficiary.setOnClickListener(this);
        btnUpdateSender.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        LoadBeneficiaryList();
    }

    private void LoadBeneficiaryList() {
        lstUploadData = new LinkedList<>();
        lstUploadData.add(PreferenceConnector.readString(svContext, PreferenceConnector.LOGINEDUSERID, ""));
        lstUploadData.add(loadCurrentUrl);
        callWebService(ApiInterface.GETBENEFICIARYLIST, lstUploadData);
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
        errrorScreen = new NoInternetScreen(svContext, root, ActivityBeneficiaryList.this);
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
        txtHeading.setText("Beneficiary List");

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
            case R.id.btn_add_beneficiary:
                Intent svIntent = new Intent(svContext, ActivityAddBeneficiary.class);
                svIntent.putExtra("logintransfer", loadCurrentUrl);
                startActivity(svIntent);
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                break;
            case R.id.btn_remitter:
                //svIntent = new Intent(svContext, ActivityLoginTransfer.class);
                svIntent = new Intent(svContext, ActivitySenderDetails.class);
                svIntent.putExtra("logintransfer", loadCurrentUrl);
                startActivity(svIntent);
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
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

    private List<BeneficiaryListModel> lstItems = new ArrayList<>();

    public static final String TAG_DATA = "data";
    public static final String TAG_AMOUNT = "amount";
    public static final String TAG_DATETIME = "datetime";
    public static final String TAG_DESCRIPTION = "description";
    public static final String TAG_STATUS = "status";
    public static final String TAG_MESSAGE = "message";
    public static final String TAG_JSON_STATUS = "status";

    @Override
    public void onWebServiceActionComplete(String result, String url) {
        System.out.println(result + ".........jsonresponse....." + url);
        if (url.contains(ApiInterface.GETBENEFICIARYLIST)) {
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
                        String str_account_no = data_obj.getString("account_no");
                        String str_bank_name = data_obj.getString("bank_name");
                        String str_ifsc = data_obj.getString("ifsc");
                        String str_name = data_obj.getString("account_holder_name");
                        String str_benId = data_obj.getString("beneId");
                        String str_benMob = data_obj.getString("ben_mobile");
                        String str_status = data_obj.getString("status");
                        String str_otp_verify = data_obj.getString("is_otp_verify");
                        String str_date = data_obj.getString("date");

                        lstItems.add(new BeneficiaryListModel(str_name, str_account_no, str_ifsc, str_bank_name, str_benId, str_benMob, str_status, str_otp_verify, str_date));
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
            BeneficiaryListAdapter mAdapter = new BeneficiaryListAdapter(this, lstItems, animation_type, true);
            wallethistoryrv.setAdapter(mAdapter);
            mAdapter.setOnItemClickListener(new BeneficiaryListAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, BeneficiaryListModel obj, int position) {
                    Showamountdialog(svContext, obj.getStr_benId());
                }

                @Override
                public void onItemDelete(View view, BeneficiaryListModel obj, int position) {
                    ShowConfirmDelete("Delete Beneficiary", "Are you want to delete beneficiary", obj.getStr_benId());
                }
            });

            //      mAdapter.setOnComplaintItemClickListener(new BeneficiaryListAdapter.OnComplaintItemClickListener() {
            //         @Override
            //       public void onComplaintItemClick(View view, String obj, int position) {
            //             Intent intent = new Intent(svContext, ActivityRaiseComplaint.class);
            //              intent.putExtra("comp_from", false);
            //             intent.putExtra("rechg_id", lstItems.get(position).getStr_recharge_id());
            //             intent.putExtra("rechg_amount", lstItems.get(position).getStr_amount());
            //           startActivity(intent);
            //       }
            //    });
        } else if (url.contains(ApiInterface.DELETE_DMT_BENEFICERY)) {
            try {
                JSONObject json = new JSONObject(result);
                String str_status = json.getString("status");
                String str_msg = json.getString("message");
                if (str_status.equalsIgnoreCase("0")) {
                    customToast.showCustomToast(svContext, str_msg, customToast.ToastyError);
                } else {
                    customToast.showCustomToast(svContext, str_msg, customToast.ToastySuccess);
                    LoadBeneficiaryList();
                }
            } catch (JSONException e) {
                customToast.showCustomToast(svContext, "Some error occured", customToast.ToastyError);
                e.printStackTrace();
            }
        } else if (url.contains(ApiInterface.MONEYTRANSFERAUTH)) {
            try {
                JSONObject json = new JSONObject(result);
                String str_status = json.getString("status");
                String str_msg = json.getString("message");
                if (str_status.equalsIgnoreCase("0")) {
                    customToast.showCustomToast(svContext, str_msg, customToast.ToastyError);
                } else {
                    customToast.showCustomToast(svContext, "Success", customToast.ToastySuccess);

                    Intent svIntent = new Intent(svContext, ActivityCompletion.class);
                    svIntent.putExtra("from_act", "moneytransfer");
                    startActivity(svIntent);
                    finish();
                }
            } catch (JSONException e) {
                customToast.showCustomToast(svContext, "Some error occured", customToast.ToastyError);
                e.printStackTrace();
            }
        }
    }

    private void Showamountdialog(Context svContext, String benId) {
        final Dialog dialog = new Dialog(svContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_header_transferamount);

        TextView textTitle = (TextView) dialog.findViewById(R.id.dialog_title);
        EditText textDesc = (EditText) dialog.findViewById(R.id.dialog_desc);
        TextView textHead = (TextView) dialog.findViewById(R.id.dialog_head);
        textHead.setText("");

        Button declineDialogButton = (Button) dialog.findViewById(R.id.bt_decline);
        declineDialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Button confirmDialogButton = (Button) dialog.findViewById(R.id.bt_confirm);
        confirmDialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Float Str_amnt = Float.parseFloat(textDesc.getText().toString().trim());
                if (Str_amnt <= 100) {
                    customToast.showCustomToast(svContext, "please enter amount greater than 100", customToast.ToastyInfo);
                } else {
                    lstUploadData = new LinkedList<>();
                    lstUploadData.add(PreferenceConnector.readString(svContext, PreferenceConnector.LOGINEDUSERID, ""));
                    lstUploadData.add(benId);
                    lstUploadData.add(textDesc.getText().toString().trim());
                    callWebService(ApiInterface.MONEYTRANSFERAUTH, lstUploadData);
                }
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void ShowConfirmDelete(String strTitle, String strDesc, String strBenzId) {
        final Dialog dialog = new Dialog(svContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_header_twobutton);

        TextView textTitle = (TextView) dialog.findViewById(R.id.dialog_title);
        textTitle.setText(strTitle);
        TextView textDesc = (TextView) dialog.findViewById(R.id.dialog_desc);
        textDesc.setText(strDesc);

        Button declineDialogButton = (Button) dialog.findViewById(R.id.bt_decline);
        declineDialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Button confirmDialogButton = (Button) dialog.findViewById(R.id.bt_confirm);
        confirmDialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                lstUploadData = new LinkedList<>();
                lstUploadData.add(PreferenceConnector.readString(svContext, PreferenceConnector.LOGINEDUSERID, ""));
                lstUploadData.add(loadCurrentUrl);
                lstUploadData.add(strBenzId);
                callWebService(ApiInterface.DELETE_DMT_BENEFICERY, lstUploadData);
            }
        });

        dialog.show();
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