package com.rechargeapp.activity.wallet;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chaos.view.PinView;
import com.commonutility.CheckInternet;
import com.commonutility.CheckValidation;
import com.commonutility.GlobalData;
import com.commonutility.GlobalVariables;
import com.commonutility.MessageListener;
import com.commonutility.NoInternetScreen;
import com.commonutility.PreferenceConnector;
import com.commonutility.ShowCustomToast;
import com.commonutility.SpinnerPopulateAdapter;
import com.commonutility.WebService;
import com.commonutility.WebServiceListener;
import com.commonutility.customfont.FontUtils;
import com.commonutility.retrofit.ApiInterface;
import com.codunite.rechargeapp.R;
import com.rechargeapp.adapter.SearchAdaper;
import com.rechargeapp.model.SearchMemberListModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ActivityEWalletTransfer extends AppCompatActivity implements View.OnClickListener, WebServiceListener, MessageListener {
    private Context svContext;
    private ShowCustomToast customToast;
    private CheckInternet checkNetwork;
    private NoInternetScreen errrorScreen;

    private EditText edCurrentBal, edAmount, edDesc, edPhoneNumber,edt_avl_balance;
    private Button btnOtpAuth;

    AppCompatButton btnProceed;
    private ImageView imgDrop, imgDropOne,imgDropTwo;

    private ImageView imgBack;
    private View[] allViewWithClick = {btnProceed, imgBack, btnOtpAuth, imgDrop, imgDropOne,imgDropTwo};
    private int[] allViewWithClickId = {R.id.btn_proceed, R.id.img_back, R.id.btn_otpauth,
            R.id.img_drop_1, R.id.img_drop_2,R.id.img_drop_3};

    private EditText[] edTexts = {edCurrentBal, edAmount, edDesc};
    private String[] edTextsError = {"Enter current wallet Balance", "Enter Amount", "Enter Description"};
    private int[] editTextsClickId = {R.id.edtcurrentwalletbal, R.id.edt_enteramount, R.id.edt_desc};
    private Spinner spinnerCrDr, spinnerMemberList;
    private List<String> listSpinnerCrDr, listSpinnerMemberListy,listSpinnerWalletBal,NewlistSpinnerMemberListy;

    private List<SearchMemberListModel> searchMemberListModels;
    TextView tv_select_member;
    Dialog dialog;

    SearchAdaper searchAdaper;
    RecyclerView listView;
    String struserId="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_wallettransfer);
        StartApp();
        loadToolBar();

        //Start Coding from here
        OnClickCombineDeclare(allViewWithClick);
        EditTextDeclare(edTexts);

        spinnerCrDr = (Spinner) findViewById(R.id.spinner_crdr);
        spinnerMemberList = (Spinner) findViewById(R.id.spinner_memberlist);
        tv_select_member=(TextView)findViewById(R.id.tv_select_member);

        LoadAllData();

        listSpinnerCrDr = new ArrayList<>();
        //listSpinnerCrDr.add("-1#:#Add Credit");
        listSpinnerCrDr.add("1#:#CR");
        //listSpinnerCrDr.add("2#:#DR");
        SpinnerPopulateAdapter LegAdapter = new SpinnerPopulateAdapter(svContext, listSpinnerCrDr, true);
        spinnerCrDr.setAdapter(LegAdapter);
        spinnerCrDr.setOnItemSelectedListener(onItemSelectedListener);
        ((EditText) findViewById(R.id.edtcurrentwalletbal)).setText(PreferenceConnector.readString(svContext, PreferenceConnector.EWALLETBAL, ""));

//        spinnerMemberList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
//                // your code here
//                if(position!=0) {
//                    edt_avl_balance.setText(String.valueOf(listSpinnerWalletBal.get(position)));
//                    } else {
//                    edt_avl_balance.setText("");
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parentView) {
//                // your code here
//            }
//
//        });

        tv_select_member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog=new Dialog(ActivityEWalletTransfer.this);
                dialog.setContentView(R.layout.layout_searchable_spinner);
                dialog.getWindow().setLayout(600, 800);

                //set transparent background
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                //show dialog
                dialog.show();

                EditText editText=dialog.findViewById(R.id.editText_of_searchableSpinner);
                listView=dialog.findViewById(R.id.listView_of_searchableSpinner);
                LinearLayoutManager layoutManager = new LinearLayoutManager(ActivityEWalletTransfer.this, LinearLayoutManager.VERTICAL, false);
                listView.setLayoutManager(layoutManager);
                listView.setHasFixedSize(true);
                searchAdaper = new SearchAdaper(ActivityEWalletTransfer.this,searchMemberListModels,2);
                listView.setAdapter(searchAdaper);
//                ArrayAdapter<String> arrayAdapter=new ArrayAdapter<>(ActivityEWalletTransfer.this,
//                        androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,NewlistSpinnerMemberListy);
//                listView.setAdapter(arrayAdapter);

                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        filter(charSequence.toString());
                    }
                    @Override
                    public void afterTextChanged(Editable editable) {
                    }
                });

                searchAdaper.setOnItemClickListener((view, obj,obj1,obj2, position) -> {
                    tv_select_member.setText(obj.toString());
                    edt_avl_balance.setText(obj1);
                    struserId = obj2;
                    if(dialog!=null){
                        dialog.dismiss();
                    }
                });

//                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                        tv_select_member.setText(arrayAdapter.getItem(i));
//                        edt_avl_balance.setText(String.valueOf(listSpinnerWalletBal.get(i+1)));
//                        dialog.dismiss();
//                    }
//                });
            }
        });
    }




    private void LoadAllData(){
        lstUploadData = new LinkedList<>();
        lstUploadData.add(PreferenceConnector.readString(svContext, PreferenceConnector.LOGINEDUSERID, ""));
        if(PreferenceConnector.readString(svContext, PreferenceConnector.LOGINUSERTYPE, "").equals("3")) {
            lstUploadData.add("3");
        } else if(PreferenceConnector.readString(svContext, PreferenceConnector.LOGINUSERTYPE, "").equals("4")) {
            lstUploadData.add("4");
        }else if(PreferenceConnector.readString(svContext, PreferenceConnector.LOGINUSERTYPE, "").equals("5")) {
            lstUploadData.add("5");
        }else if(PreferenceConnector.readString(svContext, PreferenceConnector.LOGINUSERTYPE, "").equals("8")) {
            lstUploadData.add("8");
        }

        //lstUploadData.add("3");
        callWebService(ApiInterface.GETFUNDMEMBERLIST, lstUploadData);
    }

    private void ProceedWalletTransfer() {
        int response = 0;
        response = CheckValidation.emptyEditTextError(edTexts, edTextsError);

//        if (spinnerCrDr.getSelectedItemPosition() == 0) {
//            response++;
//            customToast.showCustomToast(svContext, "Select credit", customToast.ToastyError);
//        }

//        if (spinnerMemberList.getSelectedItemPosition() == 0) {
//            response++;
//            customToast.showCustomToast(svContext, "Select member", customToast.ToastyError);
//        }
        if (struserId.equals("")) {
            response++;
            customToast.showCustomToast(svContext, "Select member", customToast.ToastyError);
        }

        if (response == 0) {
            lstUploadData = new LinkedList<>();
            lstUploadData.add(PreferenceConnector.readString(svContext, PreferenceConnector.LOGINEDUSERID, ""));

            //lstUploadData.add((listSpinnerMemberListy.get(spinnerMemberList.getSelectedItemPosition()).split("#:#")[0]));
//            lstUploadData.add((listSpinnerCrDr.get(spinnerCrDr.getSelectedItemPosition()).split("#:#")[0]));
            lstUploadData.add(struserId);
            lstUploadData.add("2");
            lstUploadData.add(edAmount.getText().toString().trim());
            lstUploadData.add(edDesc.getText().toString().trim());
            callWebService(ApiInterface.WALLETTRANSFER, lstUploadData);
        }
    }

    private void OnClickCombineDeclare(View[] allViewWithClick) {
        for (int j = 0; j < allViewWithClick.length; j++) {
            allViewWithClick[j] = findViewById(allViewWithClickId[j]);
            allViewWithClick[j].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.btn_proceed:
                            ProceedWalletTransfer();
                            break;
                        case R.id.img_back:
                            onBackPressed();
                            break;
                        case R.id.btn_otpauth:
                            OtpVerify();
                            break;
                        case R.id.img_drop_1:
                            spinnerCrDr.performClick();
                            break;
                        case R.id.img_drop_2:
                            spinnerMemberList.performClick();
                            break;
                        case R.id.img_drop_3:
                            tv_select_member.performClick();
                            break;
                    }
                }

            });
        }
    }

    private void loadToolBar() {
        TextView txtHeading = (TextView) findViewById(R.id.heading);
        txtHeading.setText("Credit E-Wallet");
    }

    private void EditTextDeclare(EditText[] editTexts) {
        for (int j = 0; j < editTexts.length; j++) {
            editTexts[j] = findViewById(editTextsClickId[j]);
        }
        edCurrentBal = (EditText) editTexts[0];
        edAmount = (EditText) editTexts[1];
        edDesc = (EditText) editTexts[2];
        edPhoneNumber = (EditText) findViewById(R.id.edt_entermobile);
        edt_avl_balance= (EditText) findViewById(R.id.edt_avl_balance);
        edPhoneNumber.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {}
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if ((edPhoneNumber.getText().toString().trim()).length() == 10) {
                    lstUploadData = new LinkedList<>();
                    lstUploadData.add(PreferenceConnector.readString(svContext, PreferenceConnector.LOGINEDUSERID, ""));
                    lstUploadData.add(edPhoneNumber.getText().toString().trim());
                    callWebService(ApiInterface.GETMEMBERBYMOBILE, lstUploadData);
                }else if((edPhoneNumber.getText().toString().trim()).length() == 0){
                    LoadAllData();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.termscondition:
//                break;
            default:
                break;
        }
    }


    private void StartApp() {
        svContext = this;
        customToast = new ShowCustomToast(svContext);
        checkNetwork = new CheckInternet(svContext);
        ViewGroup root = (ViewGroup) findViewById(R.id.headlayout);
        errrorScreen = new NoInternetScreen(svContext, root, ActivityEWalletTransfer.this);
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

    @Override
    public void messageReceived(String message) {
        Toast.makeText(this, "New Message Received: " + message, Toast.LENGTH_SHORT).show();
    }

    LinkedList<String> lstUploadData = new LinkedList<>();

    private void callWebService(String postUrl, LinkedList<String> lstUploadData) {
        WebService webService = new WebService(svContext, postUrl, lstUploadData, this);
        webService.LoadDataRetrofit(webService.callReturn());
    }

    public static final String TAG_DATA = "data";
    public static final String TAG_MESSAGE = "message";
    public static final String TAG_STATUS = "status";
    public static final String TAG_USER_ID = "user_id";
    public static final String TAG_NAME = "name";
    @Override
    public void onWebServiceActionComplete(String result, String url) {
        System.out.println(result + ".........jsonresponse....." + url);
        if (url.contains(ApiInterface.WALLETTRANSFER)) {
            try {
                JSONObject json = new JSONObject(result);

                String str_status = json.getString("status");
                String str_msg = json.getString("message");
                if (str_status.equalsIgnoreCase("0")) {
                    customToast.showCustomToast(svContext, str_msg, customToast.ToastyError);
                } else {
                    PreferenceConnector.writeString(svContext, PreferenceConnector.WALLETBAL, json.getString("balance"));
                    customToast.showCustomToast(svContext, str_msg, customToast.ToastySuccess);
                    onBackPressed();
                }
            } catch (JSONException e) {
                customToast.showCustomToast(svContext, "Some error occured", customToast.ToastyError);
                e.printStackTrace();
            }
        } else if (url.contains(ApiInterface.WALLETOTPAUTH)) {
            try {
                JSONObject json = new JSONObject(result);

                String str_status = json.getString("status");
                String str_message = json.getString("message");

                if (str_status.equalsIgnoreCase("0")) {
                    customToast.showCustomToast(svContext, str_message, customToast.ToastyError);
                } else {
                    hideOtpLayout();
                    onBackPressed();
                }
            } catch (JSONException e) {
                customToast.showCustomToast(svContext, "Some error occured", customToast.ToastyError);
                e.printStackTrace();
            }
        }else if (url.contains(ApiInterface.GETMEMBERBYMOBILE)) {
            listSpinnerMemberListy = new ArrayList<>();

            listSpinnerMemberListy.add("-1#:#Select member");

            try {
                JSONObject json = new JSONObject(result);
                String str_message = json.getString(TAG_MESSAGE);
                String str_status = json.getString(TAG_STATUS);
                if (str_status.equalsIgnoreCase("0")) {
                    customToast.showCustomToast(svContext, str_message, customToast.ToastyError);
                } else {
                    JSONArray data = json.getJSONArray(TAG_DATA);
                    for (int data_i = 0; data_i < data.length(); data_i++) {
                        JSONObject data_obj = data.getJSONObject(data_i);
                        String str_user_id = data_obj.getString("member_id");
                        String str_name = data_obj.getString("member_name")+" ("+data_obj.getString("member_code")+") ";

                        listSpinnerMemberListy.add(str_user_id+"#:#"+str_name);

                    }
                }
            } catch (JSONException e) {
                customToast.showCustomToast(svContext, "Some error occured", customToast.ToastyError);
                e.printStackTrace();
            }
            SpinnerPopulateAdapter LegAdapter = new SpinnerPopulateAdapter(svContext, listSpinnerMemberListy, true);
            spinnerMemberList.setAdapter(LegAdapter);

        }else if (url.contains(ApiInterface.GETFUNDMEMBERLIST)) {
            //listSpinnerMemberListy = new ArrayList<>();
            //listSpinnerWalletBal = new ArrayList<>();
            //NewlistSpinnerMemberListy=new ArrayList<>();
            searchMemberListModels=new ArrayList<>();
            //listSpinnerMemberListy.add("-1#:#Select member");
            //listSpinnerWalletBal.add("0.00");
            try {
                JSONObject json = new JSONObject(result);
                String str_message = json.getString(TAG_MESSAGE);
                String str_status = json.getString(TAG_STATUS);
                if (str_status.equalsIgnoreCase("0")) {
                    customToast.showCustomToast(svContext, str_message, customToast.ToastyError);
                } else {
                    JSONArray data = json.getJSONArray(TAG_DATA);
                    for (int data_i = 0; data_i < data.length(); data_i++) {
                        JSONObject data_obj = data.getJSONObject(data_i);
                        String str_user_id = data_obj.getString("user_id");
                        String str_name = data_obj.getString("name")+" ("+data_obj.getString("user_code")+") ";
                        String str_name1 = data_obj.getString("name")+" - "+data_obj.getString("user_code")+"";
                        String str_wallet_balance = data_obj.getString("e_wallet_balance");
                        //listSpinnerMemberListy.add(str_user_id+"#:#"+str_name);
                        //listSpinnerWalletBal.add(str_wallet_balance);
                        //NewlistSpinnerMemberListy.add(str_name1);
                        searchMemberListModels.add(new SearchMemberListModel(str_user_id,data_obj.getString("user_code"),data_obj.getString("name"),data_obj.getString("email"),data_obj.getString("mobile"),data_obj.getString("wallet_balance"),data_obj.getString("e_wallet_balance")));
                    }
                }
            } catch (JSONException e) {
                customToast.showCustomToast(svContext, "Some error occured", customToast.ToastyError);
                e.printStackTrace();
            }
//            SpinnerPopulateAdapter LegAdapter = new SpinnerPopulateAdapter(svContext, listSpinnerMemberListy, true);
//            spinnerMemberList.setAdapter(LegAdapter);
        }
    }

    private void ShowOtpLayout() {
        ((CardView) findViewById(R.id.layoutLogin)).setVisibility(View.GONE);
        ((CardView) findViewById(R.id.card_otp)).setVisibility(View.VISIBLE);
    }

    private void hideOtpLayout() {
        ((CardView) findViewById(R.id.layoutLogin)).setVisibility(View.VISIBLE);
        ((CardView) findViewById(R.id.card_otp)).setVisibility(View.GONE);
    }

    private void OtpVerify() {
        PinView edtOtp = (PinView) findViewById(R.id.edt_otp);
        if (edtOtp.length() == 0) {
            customToast.showCustomToast(svContext, "Please enter otp", customToast.ToastyError);
        } else {
            lstUploadData = new LinkedList<>();
            lstUploadData.add(PreferenceConnector.readString(svContext, PreferenceConnector.LOGINEDUSERID, ""));
            lstUploadData.add(edtOtp.getText().toString().trim());
            callWebService(ApiInterface.WALLETOTPAUTH, lstUploadData);
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

    @Override
    public void onWebServiceError(String result, String url) {
        customToast.showCustomToast(svContext, result, customToast.ToastyError);
    }

    @Override
    public void onBackPressed() {
        hideKeyboard();
        super.onBackPressed();
    }

    private void filter(String text) {
        List<SearchMemberListModel> filteredlist = new ArrayList<SearchMemberListModel>();
        for (SearchMemberListModel item : searchMemberListModels) {
            if (item.getName().toLowerCase().contains(text.toLowerCase()) || item.getUser_code().toLowerCase().contains(text.toLowerCase()) || item.getMobile().toLowerCase().contains(text.toLowerCase())) {
                filteredlist.add(item);
            }
        }
        if (filteredlist.isEmpty()) {
            listView.setVisibility(View.GONE);
        } else {
            listView.setVisibility(View.VISIBLE);
            searchAdaper.filterList(filteredlist);
        }
    }

    AdapterView.OnItemSelectedListener onItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            TextView textView = view.findViewById(R.id.txtitem);
            textView.setTextColor(getResources().getColor(R.color.et_textcolor));
        }
        @Override public void onNothingSelected(AdapterView<?> parent) { }
    };
}