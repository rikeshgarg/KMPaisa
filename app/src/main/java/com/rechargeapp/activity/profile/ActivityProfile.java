package com.rechargeapp.activity.profile;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import com.commonutility.retrofit.ApiInterface;
import com.codunite.rechargeapp.R;
import com.commonutility.spinner.SpinnerModel;
import com.commonutility.CheckValidation;
import com.commonutility.GlobalData;
import com.commonutility.GlobalVariables;
import com.commonutility.ImageLoading;

import com.commonutility.NoInternetScreen;
import com.commonutility.PreferenceConnector;
import com.commonutility.ShowCustomToast;
import com.commonutility.WebService;
import com.commonutility.WebServiceListener;
import com.commonutility.customfont.FontUtils;
import com.rechargeapp.activity.ActivityBrowseProfileImage;
import com.rechargeapp.activity.ActivityMain;
import com.rechargeapp.activity.ActivitySplash;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ActivityProfile extends AppCompatActivity implements View.OnClickListener, WebServiceListener {
    private EditText edName, edEmail, edPhone, edAddress, edPincode, edBlockName;
    private TextView tvSelectDistrict, tvSelectState;
    private ImageView imgAvatar;
    private int[] allViewWithClickId = {R.id.btn_PI_Update, R.id.avatar};

    private EditText[] edTexts = {edName, edEmail, edPhone};
    private String[] edTextsError = {"Enter name", "Enter email", "Enter mobile"};
    private int[] editTextsClickId = {R.id.edt_PI_name, R.id.edt_PI_mail, R.id.edt_PI_mobnumber};

    private SpinnerModel selectedFranchSpinnerDistrict, selectedFranchSpinnerState;
    private List<SpinnerModel> listFranchSpinnerDistrict, listFranchSpinnerState;

    private static final int REQUEST_CODE_SELECT_DISTRICT = 434;
    private static final int REQUEST_CODE_SELECT_STATE = 435;
    private static final int REQUEST_CODE_SELECT_BLOCK = 436;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_profile);
        StartApp();

        //Start Coding from here
        OnClickCombineDeclare(allViewWithClickId);
        EditTextDeclare(edTexts);

//        RelativeLayout laySelectDistrict = findViewById(R.id.lay_district);
//        laySelectDistrict.setOnClickListener(v -> {
//            if (listFranchSpinnerDistrict!=null) {
//                ActivitySpinner.showSpinner(svContext, listFranchSpinnerDistrict, "Select District", REQUEST_CODE_SELECT_DISTRICT);
//            }
//        });
//        RelativeLayout laySelectState = findViewById(R.id.lay_state);
//        laySelectState.setOnClickListener(v -> {
//            if (listFranchSpinnerState!=null) {
//                ActivitySpinner.showSpinner(svContext, listFranchSpinnerState, "Select State", REQUEST_CODE_SELECT_STATE);
//            }
//        });
//
//        tvSelectState = findViewById(R.id.txt_state);
//        tvSelectDistrict = findViewById(R.id.txt_district);
//
//        lstUploadData = new LinkedList<>();
//        lstUploadData.add("IN");
//        callWebService(ApiInterface.GetFranchiseStateList, lstUploadData);

        NestedScrollView layMainLay = findViewById(R.id.scrollView2);
        if(PreferenceConnector.readBoolean(svContext, PreferenceConnector.ISPROFILEUPDATED, false)){
            DiableAllViews(layMainLay);
        }

    }

    private void DiableAllViews(ViewGroup parentView){
        for (int i = 0; i < parentView.getChildCount(); i++) {
            View child = parentView.getChildAt(i);
            if (child instanceof EditText) {
                child.setEnabled(false);
                child.setFocusable(false);
                child.setFocusableInTouchMode(false);
            }else if (child instanceof ImageView) {
                child.setClickable(false);
            }else if (child instanceof TextView) {
                child.setClickable(false);
            }else if (child instanceof Button) {
                child.setEnabled(false);
            }else if (child instanceof RadioButton) {
                child.setEnabled(false);
            }else if (child instanceof ViewGroup) {
                DiableAllViews((ViewGroup) child);
            }
        }
    }

    private void UpdatePI() {
        int response = 0;
        response = CheckValidation.emptyEditTextError(new EditText[]{edName, edEmail, edPhone},
                new String[]{"Enter name", "Enter email", "Enter mobile"});

//        if (response == 0 && selectedFranchSpinnerDistrict == null) {
//            response++;
//            customToast.showCustomToast(svContext, "Please select district", customToast.ToastyError);
//        }
//
//        if (response == 0 && selectedFranchSpinnerState == null) {
//            response++;
//            customToast.showCustomToast(svContext, "Please select state", customToast.ToastyError);
//        }

        if (response == 0) {
            if (imageDpUri == null) {
                lstUploadData = new LinkedList<>();
                lstUploadData.add(PreferenceConnector.readString(svContext, PreferenceConnector.LOGINEDUSERID, ""));
                lstUploadData.add(edName.getText().toString().trim());
                lstUploadData.add("");
                callWebService(ApiInterface.UPADATEUSERDATA, lstUploadData);
            } else {
                lstUploadData = new LinkedList<>();
                lstUploadData.add(PreferenceConnector.readString(svContext, PreferenceConnector.LOGINEDUSERID, ""));
                lstUploadData.add(edName.getText().toString().trim());
                lstUploadData.add(encodeImage(imageDpUri));
                callWebService(ApiInterface.UPADATEUSERDATA, lstUploadData);
            }
        }
    }

    private String encodeImage(Uri imgUri) {
        File imagefile = new File(imgUri.getPath());
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(imagefile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Bitmap bm = BitmapFactory.decodeStream(fis);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);
        //Base64.de
        return encImage;

    }

    private void OnClickCombineDeclare(int[] allViewWithClick) {
        for (int j = 0; j < allViewWithClick.length; j++) {
            (findViewById(allViewWithClickId[j])).setOnClickListener(v -> {
                switch (v.getId()) {
                    case R.id.btn_PI_Update:
                        UpdatePI();
                        break;
                    case R.id.avatar:
                        Intent svIntent = new Intent(svContext, ActivityBrowseProfileImage.class);
                        startActivity(svIntent);
                        break;
                }
            });
        }
        imgAvatar = findViewById(allViewWithClick[1]);
        String imageUrl = PreferenceConnector.readString(svContext, PreferenceConnector.LOGINUSERPROFILEPIC, "");
        if(imageUrl!="") {
            try {
                ImageLoading.loadImages(imageUrl, imgAvatar, R.drawable.users);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private Uri imageDpUri = null;

    @Override
    public void onResume() {
        super.onResume();
        if (ActivityBrowseProfileImage.imageUri != null) {
            imageDpUri = ActivityBrowseProfileImage.imageUri;
            imgAvatar.setImageURI(null);
            imgAvatar.setImageURI(ActivityBrowseProfileImage.imageUri);
            imgAvatar.setVisibility(View.VISIBLE);
        }
    }

    private void EditTextDeclare(EditText[] editTexts) {
        for (int j = 0; j < editTexts.length; j++) {
            editTexts[j] = findViewById(editTextsClickId[j]);
        }
        edName = (EditText) editTexts[0];
        edEmail = (EditText) editTexts[1];
        edPhone = (EditText) editTexts[2];
//        edAddress = (EditText) editTexts[3];
//        edPincode = (EditText) editTexts[4];
//        edBlockName = (EditText) editTexts[5];

        edName.setText(PreferenceConnector.readString(svContext, PreferenceConnector.LOGINUSERNAME, ""));
        edPhone.setText(PreferenceConnector.readString(svContext, PreferenceConnector.LOGINUSERPHONE, ""));
        edEmail.setText(PreferenceConnector.readString(svContext, PreferenceConnector.LOGINUSEREMAIL, ""));
//        edAddress.setText(PreferenceConnector.readString(svContext, PreferenceConnector.LOGINADDRESS, ""));
//        edPincode.setText(PreferenceConnector.readString(svContext, PreferenceConnector.LOGINPINCODE, ""));
//        edBlockName.setText(PreferenceConnector.readString(svContext, PreferenceConnector.LOGINBLOCKID, ""));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
        }
    }

    private Context svContext;
    private ShowCustomToast customToast;
    
    

    private void StartApp() {
        svContext = this;
        customToast = new ShowCustomToast(svContext);
        
        ViewGroup root = (ViewGroup) findViewById(R.id.headlayout);
        new NoInternetScreen(svContext, root, ActivityProfile.this);
        if (!GlobalVariables.CUSTOMFONTNAME.equals("")) {
            Typeface font = Typeface.createFromAsset(getAssets(), GlobalVariables.CUSTOMFONTNAME);
            FontUtils.setFont(root, font);
        }
//        if (PreferenceConnector.readBoolean(svContext, PreferenceConnector.ISDARKTHEME, false)) {
//            FontUtils.setThemeColor(root, svContext, true);
//        } else {
//            FontUtils.setThemeColor(root, svContext, false);
//        }
        hideKeyboard();

        
        loadToolBar();
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
        txtHeading.setText("Profile");
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

    public static final String TAG_MESSAGE = "message";
    public static final String TAG_STATUS = "status";
    public static final String TAG_DATA = "data";

    @Override
    public void onWebServiceActionComplete(String result, String url) {
        if (url.contains(ApiInterface.GetFranchiseStateList)) {
            listFranchSpinnerState = new ArrayList<>();
            try {
                JSONObject json = new JSONObject(result);
                String str_status = json.getString(TAG_STATUS);
                String str_msg = json.getString(TAG_MESSAGE);
                if (str_status.equalsIgnoreCase("1")) {
                    JSONArray data = json.getJSONArray(TAG_DATA);
                    for (int data_i = 0; data_i < data.length(); data_i++) {
                        JSONObject data_obj = data.getJSONObject(data_i);
                        String strName = data_obj.getString("state_title");
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
        }else if (url.contains(ApiInterface.GetFranchiseDistrictList)) {
            listFranchSpinnerDistrict = new ArrayList<>();
            try {
                JSONObject json = new JSONObject(result);
                String str_status = json.getString(TAG_STATUS);
                String str_msg = json.getString(TAG_MESSAGE);
                if (str_status.equalsIgnoreCase("1")) {
                    JSONArray data = json.getJSONArray(TAG_DATA);
                    for (int data_i = 0; data_i < data.length(); data_i++) {
                        JSONObject data_obj = data.getJSONObject(data_i);
                        String str_country_id = data_obj.getString("districtid");
                        String str_country_name = data_obj.getString("district_title");

                        if (PreferenceConnector.readString(svContext, PreferenceConnector.LOGINCITYID, "").equalsIgnoreCase(str_country_id)) {
                            tvSelectDistrict.setText(str_country_name);
                        }
                        listFranchSpinnerDistrict.add(new SpinnerModel(str_country_id, str_country_name, ""));
                    }

                    String strtId = PreferenceConnector.readString(svContext, PreferenceConnector.LOGINCITYID, "");
                } else {
                    customToast.showCustomToast(svContext, str_msg, customToast.ToastyError);
                }
            } catch (JSONException e) {
                customToast.showCustomToast(svContext, "Some error occured", customToast.ToastyError);
                e.printStackTrace();
            }
        } else if (url.contains(ApiInterface.UPADATEUSERDATA)) {
            try {
                JSONObject json = new JSONObject(result);

                String str_message = json.getString(TAG_MESSAGE);
                String str_status = json.getString(TAG_STATUS);

                if (str_status.equalsIgnoreCase("0")) {
                    customToast.showCustomToast(svContext, str_message, customToast.ToastyError);
                } else {
                    customToast.showCustomToast(svContext, str_message, customToast.ToastySuccess);
                    PreferenceConnector.writeString(svContext, PreferenceConnector.LOGINUSERNAME, edName.getText().toString().trim());
                    //PreferenceConnector.writeString(svContext, PreferenceConnector.LOGINUSERPROFILEPIC, "");
                    Intent svIntent = new Intent(svContext, ActivityMain.class);
                    svContext.startActivity(svIntent);
                    finishAffinity();
                }
            } catch (JSONException e) {
                customToast.showCustomToast(svContext, "Some error occured", customToast.ToastyError);
                e.printStackTrace();
            }
        } else if (url.contains(ApiInterface.UPDATEFCM)) {
            ActivitySplash.LoadUserData(result, svContext);

            edName.setText(PreferenceConnector.readString(svContext, PreferenceConnector.LOGINUSERNAME, ""));
            edPhone.setText(PreferenceConnector.readString(svContext, PreferenceConnector.LOGINUSERPHONE, ""));
            edEmail.setText(PreferenceConnector.readString(svContext, PreferenceConnector.LOGINUSEREMAIL, ""));
            edAddress.setText(PreferenceConnector.readString(svContext, PreferenceConnector.LOGINADDRESS, ""));
            edPincode.setText(PreferenceConnector.readString(svContext, PreferenceConnector.LOGINPINCODE, ""));

            String strCityId = PreferenceConnector.readString(svContext, PreferenceConnector.LOGINCITYID, "");
            selectedFranchSpinnerDistrict = GlobalData.getSpinnerById(listFranchSpinnerDistrict, strCityId);
            if (selectedFranchSpinnerDistrict == null) {
                tvSelectDistrict.setText("Select City");
            } else {
                tvSelectDistrict.setText(selectedFranchSpinnerDistrict.getTitle());
            }

            String strStateId = PreferenceConnector.readString(svContext, PreferenceConnector.LOGINSTATEID, "");
            selectedFranchSpinnerState = GlobalData.getSpinnerById(listFranchSpinnerState, strStateId);
            if (selectedFranchSpinnerState == null) {
                tvSelectState.setText("Select State");
            } else {
                tvSelectState.setText(selectedFranchSpinnerState.getTitle());
            }

            ImageLoading.loadImages(PreferenceConnector.readString(svContext,
                    PreferenceConnector.LOGINUSERPROFILEPIC, ""),
                    imgAvatar, R.drawable.users);
        }
    }

    @Override
    public void onWebServiceError(String result, String url) {
        customToast.showCustomToast(svContext, result, customToast.ToastyError);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
//        super.onActivityResult(requestCode, resultCode, intent);
//        if (resultCode == RESULT_OK) {
//            Bundle extras = intent.getExtras();
//            if (extras == null) return;
//            switch (requestCode) {
//                case REQUEST_CODE_SELECT_DISTRICT:
//                    int pos1 = intent.getIntExtra(ActivitySpinner.EXTRA_SPINNER_POSITION, 0);
//                    selectedFranchSpinnerDistrict = (SpinnerModel) intent.getSerializableExtra(ActivitySpinner.EXTRA_SPINNER_DATA);
//
//                    if (selectedFranchSpinnerDistrict == null) {
//                        tvSelectDistrict.setText("Select City");
//                    } else {
//                        tvSelectDistrict.setText(selectedFranchSpinnerDistrict.getTitle());
//                    }
//                    break;
//                case REQUEST_CODE_SELECT_STATE:
//                    selectedFranchSpinnerState = (SpinnerModel) intent.getSerializableExtra(ActivitySpinner.EXTRA_SPINNER_DATA);
//                    if (selectedFranchSpinnerState == null) {
//                        tvSelectState.setText("Select State");
//                    } else {
//                        tvSelectState.setText(selectedFranchSpinnerState.getTitle());
//
//                        lstUploadData = new LinkedList<>();
//                        lstUploadData.add(selectedFranchSpinnerState.getId());
//                        callWebService(ApiInterface.GetFranchiseDistrictList, lstUploadData);
//                    }
//                    break;
//                default:
//                    break;
//            }
//        }
//    }


}