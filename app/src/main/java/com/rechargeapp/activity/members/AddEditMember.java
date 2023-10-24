package com.rechargeapp.activity.members;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.commonutility.CheckValidation;
import com.commonutility.NoInternetScreen;
import com.commonutility.PreferenceConnector;
import com.commonutility.ShowCustomToast;
import com.commonutility.SpinnerPopulateAdapter;
import com.commonutility.WebService;
import com.commonutility.WebServiceListener;
import com.commonutility.retrofit.ApiInterface;
import com.commonutility.spinner.ActivitySpinner;
import com.commonutility.spinner.SpinnerModel;
import com.codunite.rechargeapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class AddEditMember extends AppCompatActivity implements WebServiceListener {
    private Context svContext;
    private ShowCustomToast customToast;
    private List<String> lstMemberType;
    private List<String> lststatus;

    private List<String> lstCountry;
    private Spinner spinner_member_type,spinner_status,spinner_country,spinner_state;
    private int[] allViewWithClickId = {R.id.img_member_type,R.id.img_country,R.id.img_status,R.id.btn_submit,R.id.img_state};

    private EditText et_name, et_email, et_mobile, et_password, et_trans_password, et_city;

    private int[] editTextAddMemberClickId = {R.id.et_name, R.id.et_email,R.id.et_mobile,R.id.et_password,R.id.et_trans_password,R.id.et_city};

    private EditText[] edAddMember = {et_name, et_email,et_mobile,et_password,et_trans_password,et_city};

    private String[] edAddMemberTextsError = {"Enter Name", "Enter Email","Enter Mobile No.","Enter Password","Enter Transaction Password","Enter City"};

    private SpinnerModel selectedFranchSpinnerDistrict, selectedFranchSpinnerState;
    private List<SpinnerModel> listFranchSpinnerDistrict, listFranchSpinnerState;
    LinkedList<String> lstUploadData;
    RelativeLayout laySelectState;
    private TextView tvSelectState;
    private static final int REQUEST_CODE_SELECT_STATE = 435;
    public static final String TAG_MESSAGE = "message";
    public static final String TAG_STATUS = "status";
    public static final String TAG_DATA = "data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_add_edit_member);
        StartApp();
    }
    private void StartApp() {
        svContext = this;
        customToast = new ShowCustomToast(svContext);
        ViewGroup root = (ViewGroup) findViewById(R.id.headlayout);
        new NoInternetScreen(svContext, root, AddEditMember.this);
        hideKeyboard();
        loadToolBar();
        resumeApp();
    }

    private void resumeApp(){
        OnClickCombineDeclare(allViewWithClickId);
        EditTextDeclare(edAddMember);
        lstMemberType = new ArrayList<>();
        lststatus = new ArrayList<>();
        lstCountry=new ArrayList<>();
        if(PreferenceConnector.readString(svContext, PreferenceConnector.LOGINUSERTYPE, "").equals("3")){
            lstMemberType.add("0" + "#:#" + "Member Type");
            lstMemberType.add("1" + "#:#" + "Distributor");
            lstMemberType.add("2" + "#:#" + "Retailer");
        } else if(PreferenceConnector.readString(svContext, PreferenceConnector.LOGINUSERTYPE, "").equals("4")){
            lstMemberType.add("0" + "#:#" + "Member Type");
            //lstMemberType.add("1" + "#:#" + "Distributor");
            lstMemberType.add("2" + "#:#" + "Retailer");
        }


        lststatus.add("1" + "#:#" + "Active");
        lststatus.add("2" + "#:#" + "Deactive");

        lstCountry.add("101" + "#:#" + "India");

        spinner_member_type=(Spinner)findViewById(R.id.spinner_member_type);
        spinner_status=(Spinner)findViewById(R.id.spinner_status);
        spinner_country=(Spinner)findViewById(R.id.spinner_country);
        laySelectState = findViewById(R.id.lay_state);
        tvSelectState = findViewById(R.id.txt_state);
        laySelectState.setOnClickListener(v -> {
            if (listFranchSpinnerState!=null) {
                ActivitySpinner.showSpinner(svContext, listFranchSpinnerState, "Select State", REQUEST_CODE_SELECT_STATE);
            }
        });

        PopulateMemberTypeSpinner();
        PopulateStatusSpinner();
        PopulateCountrySpinner();
        lstUploadData  = new LinkedList<>();
        lstUploadData.add(PreferenceConnector.readString(svContext, PreferenceConnector.LOGINEDUSERID, ""));
        //lstUploadData.add(PreferenceConnector.readString(svContext, PreferenceConnector.DEVICE_ID, ""));
        //lstUploadData.add(PreferenceConnector.readString(svContext, PreferenceConnector.TOKEN, ""));
        //lstUploadData.add("IN");
        callWebService(ApiInterface.GetSTATELIST, lstUploadData);
    }

    private void EditTextDeclare(EditText[] editTexts) {
        for (int j = 0; j < editTexts.length; j++) {
            editTexts[j] = findViewById(editTextAddMemberClickId[j]);
        }
        et_name = (EditText) editTexts[0];
        et_email = (EditText) editTexts[1];
        et_mobile = (EditText) editTexts[2];
        et_password = (EditText) editTexts[3];
        et_trans_password = (EditText) editTexts[4];
        et_city = (EditText) editTexts[5];

    }

    private void PopulateMemberTypeSpinner(){
        SpinnerPopulateAdapter LegAdapter = new SpinnerPopulateAdapter(svContext, lstMemberType, true);
        spinner_member_type.setAdapter(LegAdapter);
        spinner_member_type.setOnItemSelectedListener(onItemSelectedListener);
        spinner_member_type.setSelection(0, true);
    }

    private void PopulateStatusSpinner(){
        SpinnerPopulateAdapter LegAdapter = new SpinnerPopulateAdapter(svContext, lststatus, true);
        spinner_status.setAdapter(LegAdapter);
        spinner_status.setOnItemSelectedListener(onItemSelectedListener);
    }
    private void PopulateCountrySpinner(){
        SpinnerPopulateAdapter LegAdapter = new SpinnerPopulateAdapter(svContext, lstCountry, true);
        spinner_country.setAdapter(LegAdapter);
        spinner_country.setOnItemSelectedListener(onItemSelectedListener);
    }

    AdapterView.OnItemSelectedListener onItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            TextView textView = view.findViewById(R.id.txtitem);
            if(textView.getText().toString().equals("Member Type")){
                textView.setTextColor(getResources().getColor(R.color.c_gray2));
            } else {
                textView.setTextColor(getResources().getColor(R.color.fontcoloreditext));
            }
            //textView.setTextColor(getResources().getColor(R.color.fontcoloreditext));
        }
        @Override public void onNothingSelected(AdapterView<?> parent) { }
    };

    private void OnClickCombineDeclare(int[] allViewWithClick) {
        for (int j = 0; j < allViewWithClick.length; j++) {
            (findViewById(allViewWithClickId[j])).setOnClickListener(v -> {
                switch (v.getId()) {
                    case R.id.img_member_type:
                        spinner_member_type.performClick();
                        break;
                    case R.id.btn_submit:
                        SubmitData();
                        break;
                    case R.id.img_country:
                        spinner_country.performClick();
                        break;
                    case R.id.img_status:
                        spinner_status.performClick();
                        break;
                    case R.id.img_state:
                        laySelectState.performClick();
                }
            });
        }
    }

    private void SubmitData() {
        int response = 0;
        response = CheckValidation.emptyEditTextError(edAddMember,edAddMemberTextsError);
        if (!CheckValidation.isEmailValid(et_email.getText().toString().trim())) {
            response++;
            et_email.setError("Invalid email id");
        }
        if (spinner_member_type.getSelectedItemPosition() == 0) {
            response++;
            customToast.showCustomToast(svContext, "Please Select Member Type", customToast.ToastyError);
        }
        if (et_trans_password.length() < 4) {
            response++;
            customToast.showCustomToast(svContext, "Please Enter Transaction Password Min 4 Characters", customToast.ToastyError);
        }
        if (et_mobile.length() < 10) {
            response++;
            customToast.showCustomToast(svContext, "Please Enter Valid Mobile No.", customToast.ToastyError);
        }
        if (response == 0) {
            String roleId = "";
            if (spinner_member_type.getSelectedItemPosition() == 1){
                roleId = "4";
            } else {
                roleId = "5";
            }
            lstUploadData = new LinkedList<>();
            lstUploadData.add(PreferenceConnector.readString(svContext, PreferenceConnector.LOGINEDUSERID, ""));
            lstUploadData.add(roleId);
            lstUploadData.add(getEditextValue(et_name));
            lstUploadData.add(getEditextValue(et_email));
            lstUploadData.add(getEditextValue(et_mobile));
            lstUploadData.add(getEditextValue(et_password));
            lstUploadData.add(getEditextValue(et_trans_password));
            lstUploadData.add(lstCountry.get(spinner_country.getSelectedItemPosition()).split("#:#")[0]);
            lstUploadData.add(selectedFranchSpinnerState.getId());
            lstUploadData.add(getEditextValue(et_city));
            callWebService(ApiInterface.ADDMEMBERAUTH, lstUploadData);
        }
    }

    private String getEditextValue(EditText editText) {
        for (int i = 0; i < edAddMember.length; i++) {
            if (editText == edAddMember[i]) {
                return (edAddMember[i]).getText().toString().trim();
            }
        }
        return "";
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
        txtHeading.setText("Add Member");
    }


    private void hideKeyboard() {
        InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        View view = this.getCurrentFocus();
        if (view != null) {
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public void onWebServiceActionComplete(String result, String url) {
        if (url.contains(ApiInterface.GetSTATELIST)) {
            listFranchSpinnerState = new ArrayList<>();
            try {
                JSONObject json = new JSONObject(result);
                String str_status = json.getString(TAG_STATUS);
                String str_msg = json.getString(TAG_MESSAGE);
                if (str_status.equalsIgnoreCase("1")) {
                    JSONArray data = json.getJSONArray(TAG_DATA);
                    for (int data_i = 0; data_i < data.length(); data_i++) {
                        JSONObject data_obj = data.getJSONObject(data_i);
                        String strName = data_obj.getString("state_name");
                        String strStateId = data_obj.getString("state_id");

                        if (PreferenceConnector.readString(svContext, PreferenceConnector.LOGINSTATEID, "").equalsIgnoreCase(strStateId)) {
                            tvSelectState.setText(strName);
                        }
                        listFranchSpinnerState.add(new SpinnerModel(strStateId, strName, ""));
                    }
                } else {
                    customToast.showCustomToast(svContext, str_msg, customToast.ToastyError);
                }

                String strtId = PreferenceConnector.readString(svContext, PreferenceConnector.LOGINSTATEID, "");
                if (!strtId.equalsIgnoreCase("")) {
                    lstUploadData = new LinkedList<>();
                    lstUploadData.add(strtId);
                    callWebService(ApiInterface.GetFranchiseDistrictList, lstUploadData);
                }
            } catch (JSONException e) {
                customToast.showCustomToast(svContext, "Some error occured", customToast.ToastyError);
                e.printStackTrace();
            }
        } else if (url.contains(ApiInterface.ADDMEMBERAUTH)) {
            try {
                JSONObject json = new JSONObject(result);

                String str_status = json.getString("status");
                String str_msg = json.getString("message");
                if (str_status.equalsIgnoreCase("0")) {
                    customToast.showCustomToast(svContext, str_msg, customToast.ToastyError);
                } else {
                    customToast.showCustomToast(svContext, str_msg, customToast.ToastySuccess);
                    onBackPressed();
                }
            } catch (JSONException e) {
                customToast.showCustomToast(svContext, "Some error occured", customToast.ToastyError);
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onWebServiceError(String result, String url) {

    }
    private void callWebService(String postUrl, LinkedList<String> lstUploadData) {
        WebService webService = new WebService(svContext, postUrl, lstUploadData, this);
        webService.LoadDataRetrofit(webService.callReturn());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == RESULT_OK) {
            Bundle extras = intent.getExtras();
            if (extras == null) return;
            switch (requestCode) {
                case REQUEST_CODE_SELECT_STATE:
                    selectedFranchSpinnerState = (SpinnerModel) intent.getSerializableExtra(ActivitySpinner.EXTRA_SPINNER_DATA);
                    if (selectedFranchSpinnerState == null) {
                        tvSelectState.setText("Select State");
                    } else {
                        tvSelectState.setText(selectedFranchSpinnerState.getTitle());
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
