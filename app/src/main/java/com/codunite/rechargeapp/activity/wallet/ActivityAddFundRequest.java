package com.codunite.rechargeapp.activity.wallet;

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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import com.codunite.commonutility.CheckInternet;
import com.codunite.commonutility.CheckValidation;
import com.codunite.commonutility.GlobalData;
import com.codunite.commonutility.GlobalVariables;
import com.codunite.commonutility.NoInternetScreen;
import com.codunite.commonutility.PreferenceConnector;
import com.codunite.commonutility.ShowCustomToast;
import com.codunite.commonutility.WebService;
import com.codunite.commonutility.WebServiceListener;
import com.codunite.commonutility.customfont.FontUtils;
import com.codunite.commonutility.retrofit.ApiInterface;
import com.codunite.commonutility.spinner.ActivitySpinner;
import com.codunite.commonutility.spinner.SpinnerModel;
import com.codunite.rechargeapp.R;
import com.codunite.rechargeapp.activity.ActivityBrowseProfileImage;
import com.codunite.rechargeapp.activity.ActivityMain;
import com.codunite.rechargeapp.activity.ActivitySplash;

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

public class ActivityAddFundRequest extends AppCompatActivity implements View.OnClickListener, WebServiceListener {

    private Context svContext;
    private ShowCustomToast customToast;
    private CheckInternet checkNetwork;
    private NoInternetScreen errrorScreen;
    private RelativeLayout spinnerPaymentTo, spinnerSelectWallet;
    private TextView txt_spinner_selectwallet, txt_spinner_paymentto;
    private SpinnerModel spinnerDataWallet, spinnerDataPaymentTo;
    private List<SpinnerModel> listSpinnerSelectWallet;
    private List<SpinnerModel> listSpinnerPaymentTo;

    private ImageView imgUploadPicture;
    private Button btnChoosePicture, btnRemovePicture, btnOtpVerification;
    private TextView txtOpenMainScreen, txtResendOtp;

    private View[] allViewWithClick = {imgUploadPicture, btnChoosePicture, btnRemovePicture,
            spinnerPaymentTo, spinnerSelectWallet, btnOtpVerification, txtOpenMainScreen, txtResendOtp};
    private int[] allViewWithClickId = {R.id.img_upload_picture, R.id.choose_picture, R.id.remove_picture,
            R.id.lay_select_wallet, R.id.lay_payment_to, R.id.btn_transaction_password, R.id.goback, R.id.resendotp};

    private EditText edAmount, edTransactionId, edRemark, edTranscationOtp;
    private EditText[] edTexts = {edAmount, edTransactionId, edRemark};
    private String[] edTextsError = {"Enter amount", "Enter transaction id", "Enter remark"};
    private int[] editTextsClickId = {R.id.ed_amount, R.id.ed_reference_id, R.id.ed_remark};
    private View parentView;
    private NestedScrollView nested_scroll_view;
    private Button btnSubmitRequest;

    private int selectedImageView = 0;
    private Uri imageFrontAadharCardUri = null;

    private RelativeLayout layoutTransactionpassword;
    private String encodeFPTxnId = "";
    private String actType = "";

    public static void OpenAddFundRequest(Context context) {
        Intent svIntent = new Intent(context, ActivityAddFundRequest.class);
        svIntent.putExtra("actType", "");
        context.startActivity(svIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_add_fund_request);
        actType = getIntent().getStringExtra("actType");
        StartApp();

        OnClickCombineDeclare(allViewWithClick);
        EditTextDeclare(edTexts);

        resumeApp();
    }

    private void resumeApp() {
        parentView = findViewById(android.R.id.content);

        txt_spinner_selectwallet = findViewById(R.id.spinner_select_wallet_txt);
        txt_spinner_paymentto = findViewById(R.id.spinner_payment_to_txt);

        layoutTransactionpassword = (RelativeLayout) findViewById(R.id.layout_transactionpassword);
        nested_scroll_view = (NestedScrollView) findViewById(R.id.nested_scroll_view);

        LoadSelectWallet();
        //LoadPaymentTo();

        btnSubmitRequest = (Button) findViewById(R.id.bt_save_input);
        btnSubmitRequest.setOnClickListener(view -> ProcessingKyc());
    }

    private void LoadSelectWallet() {
        String rWalletBal = PreferenceConnector.readString(svContext, PreferenceConnector.WALLETBAL, "0");
        String eWalletBal = PreferenceConnector.readString(svContext, PreferenceConnector.EWALLETBAL, "0");
//        lstUploadData = new LinkedList<>();
//        lstUploadData.add(PreferenceConnector.readString(svContext, PreferenceConnector.LOGINEDUSERID, ""));
//        callWebService(ApiInterface.AEPSSTATELIST, lstUploadData);
        listSpinnerSelectWallet = new ArrayList<>();
        listSpinnerSelectWallet.add(new SpinnerModel("1", "My Wallet - " + rWalletBal + " RS"));
        listSpinnerSelectWallet.add(new SpinnerModel("2", "E Wallet - " + eWalletBal + " RS"));

    }

    private void LoadPaymentTo() {
        listSpinnerPaymentTo = new ArrayList<>();

        lstUploadData = new LinkedList<>();
        lstUploadData.add(PreferenceConnector.readString(svContext, PreferenceConnector.LOGINEDUSERID, ""));
        callWebService(ApiInterface.AEPSCITYLIST, lstUploadData);
    }

    private void ProcessingKyc() {
        int response = 0;
        response = CheckValidation.emptyEditTextError(new EditText[]{edAmount, edTransactionId}, new String[]{"Enter amount", "Enter transaction id"});

        if (response == 0 && spinnerDataWallet == null) {
            response++;
            customToast.showCustomToast(svContext, "Select wallet first", customToast.ToastyError);
        }

//        if (response == 0 && spinnerDataPaymentTo == null) {
//            response++;
//            customToast.showCustomToast(svContext, "Select payment transferred to", customToast.ToastyError);
//        }

//        if (response == 0 && imageFrontAadharCardUri == null) {
//            response++;
//            customToast.showCustomToast(svContext, "Please select picture or screenshot", customToast.ToastyWarning);
//        }

        if (response == 0) {
            lstUploadData = new LinkedList<>();
            lstUploadData.add(PreferenceConnector.readString(svContext, PreferenceConnector.LOGINEDUSERID, ""));
            lstUploadData.add(edAmount.getText().toString().trim());
            lstUploadData.add(edTransactionId.getText().toString().trim());
            lstUploadData.add(spinnerDataWallet.getId());
            //lstUploadData.add(spinnerDataPaymentTo.getId());
            lstUploadData.add(edRemark.getText().toString().trim());
            lstUploadData.add(encodeImage(imageFrontAadharCardUri));
            callWebService(ApiInterface.REQUESTAMOUNT, lstUploadData);
        }
    }

    private String encodeImage(Uri imgUri) {
        try {
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
            return encImage;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private void EditTextDeclare(EditText[] editTexts) {
        for (int j = 0; j < editTexts.length; j++) {
            editTexts[j] = findViewById(editTextsClickId[j]);
        }

        edAmount = (EditText) editTexts[0];
        edTransactionId = (EditText) editTexts[1];
        edRemark = (EditText) editTexts[2];
        edTranscationOtp = (EditText) findViewById(R.id.edt_transactionpassword);
    }

    private void OnClickCombineDeclare(View[] allViewWithClick) {
        for (int j = 0; j < allViewWithClick.length; j++) {
            allViewWithClick[j] = findViewById(allViewWithClickId[j]);
            allViewWithClick[j].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent svIntent;
                    switch (v.getId()) {
                        case R.id.img_upload_picture:
                        case R.id.choose_picture:
                            hideKeyboard();
                            selectedImageView = 0;
                            svIntent = new Intent(svContext, ActivityBrowseProfileImage.class);
                            startActivity(svIntent);
                            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                            break;
                        case R.id.remove_picture:
                            imageFrontAadharCardUri = null;
                            imgUploadPicture.setImageURI(null);
                            imgUploadPicture.setImageResource(R.drawable.ic_upload_photo);
                            break;
                        case R.id.lay_select_wallet:
                            ActivitySpinner.showSpinner(svContext, listSpinnerSelectWallet, "Select Wallet", REQUEST_CODE_WALLET);
                            break;
                        case R.id.lay_payment_to:
                            ActivitySpinner.showSpinner(svContext, listSpinnerPaymentTo, "Select Payment to", REQUEST_CODE_PAYMENT);
                            break;
                        case R.id.goback:
                            ShowMainScreen();
                            break;
                        case R.id.resendotp:
                            ResendOtp();
                            break;
                        case R.id.btn_transaction_password:
                            VerifyOtp();
                            break;
                    }
                }
            });
        }
        imgUploadPicture = (ImageView) allViewWithClick[0];
        btnChoosePicture = (Button) allViewWithClick[1];
        btnRemovePicture = (Button) allViewWithClick[2];
        spinnerPaymentTo = (RelativeLayout) allViewWithClick[3];
        spinnerSelectWallet = (RelativeLayout) allViewWithClick[4];

        imgUploadPicture.setImageResource(R.drawable.ic_upload_photo);
    }

    private void VerifyOtp() {
        lstUploadData = new LinkedList<>();
        lstUploadData.add(PreferenceConnector.readString(svContext, PreferenceConnector.LOGINEDUSERID, ""));
        lstUploadData.add(encodeFPTxnId);
        lstUploadData.add(edTranscationOtp.getText().toString().trim());
        callWebService(ApiInterface.AEPSOTOAUTH, lstUploadData);
    }

    private void ResendOtp() {
        lstUploadData = new LinkedList<>();
        lstUploadData.add(PreferenceConnector.readString(svContext, PreferenceConnector.LOGINEDUSERID, ""));
        lstUploadData.add(encodeFPTxnId);
        callWebService(ApiInterface.AEPSRESENDOTP, lstUploadData);
    }

    private void ShowOtpScreen() {
        nested_scroll_view.setVisibility(View.GONE);
        layoutTransactionpassword.setVisibility(View.VISIBLE);
    }

    private void ShowMainScreen() {
        nested_scroll_view.setVisibility(View.VISIBLE);
        layoutTransactionpassword.setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ActivityBrowseProfileImage.imageUri != null) {
            if (selectedImageView == 0) {
                imageFrontAadharCardUri = ActivityBrowseProfileImage.imageUri;
                imgUploadPicture.setImageURI(null);
                imgUploadPicture.setImageURI(ActivityBrowseProfileImage.imageUri);
            }
            ActivityBrowseProfileImage.imageUri = null;
        }
    }


    private static final int REQUEST_CODE_WALLET = 435;
    private static final int REQUEST_CODE_PAYMENT = 436;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == RESULT_OK) {
            Bundle extras = intent.getExtras();
            if (extras == null) return;
            switch (requestCode) {
                case REQUEST_CODE_WALLET:
                    int pos1 = intent.getIntExtra(ActivitySpinner.EXTRA_SPINNER_POSITION, 0);
                    spinnerDataWallet = (SpinnerModel) intent.getSerializableExtra(ActivitySpinner.EXTRA_SPINNER_DATA);
                    if (spinnerDataWallet == null) {
                        txt_spinner_selectwallet.setText("Select Wallet");
                    } else {
                        txt_spinner_selectwallet.setText(spinnerDataWallet.getTitle());
                    }
                    break;
                case REQUEST_CODE_PAYMENT:
                    int pos2 = intent.getIntExtra(ActivitySpinner.EXTRA_SPINNER_POSITION, 0);
                    spinnerDataPaymentTo = (SpinnerModel) intent.getSerializableExtra(ActivitySpinner.EXTRA_SPINNER_DATA);
                    if (spinnerDataPaymentTo == null) {
                        txt_spinner_paymentto.setText("Select Payment to");
                    } else {
                        txt_spinner_paymentto.setText(spinnerDataPaymentTo.getTitle());
                    }
                    break;
                default:
                    break;
            }
        }
    }


    private void StartApp() {
        svContext = this;
        customToast = new ShowCustomToast(svContext);
        checkNetwork = new CheckInternet(svContext);
        ViewGroup root = (ViewGroup) findViewById(R.id.headlayout);
        errrorScreen = new NoInternetScreen(svContext, root, ActivityAddFundRequest.this);
        if (!GlobalVariables.CUSTOMFONTNAME.equals("")) {
            Typeface font = Typeface.createFromAsset(getAssets(), GlobalVariables.CUSTOMFONTNAME);
            FontUtils.setFont(root, font);
        }
        if (PreferenceConnector.readBoolean(svContext, PreferenceConnector.ISDARKTHEME, false)) {
//            FontUtils.setThemeColor(root, svContext, true);
        } else {
//            FontUtils.setThemeColor(root, svContext, false);
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
        ImageView imgToolBarBack = (ImageView) findViewById(R.id.img_back);
        imgToolBarBack.setOnClickListener(this);

        TextView txtHeading = (TextView) findViewById(R.id.heading);
        txtHeading.setText("Fund Request");
//
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
//
//        LinearLayout imgToolBareWallet = (LinearLayout) findViewById(R.id.img_ewallet);
//        imgToolBareWallet.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ActivitySplash.OpeneWalletActivity(svContext);
//            }
//        });
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

    @Override
    public void onWebServiceActionComplete(String result, String url) {
        if (url.contains(ApiInterface.AEPSSTATELIST)) {
            listSpinnerSelectWallet = new ArrayList<>();
            try {
                JSONObject json = new JSONObject(result);
                String str_message = json.getString("message");
                String str_status = json.getString("status");

                if (str_status.equalsIgnoreCase("0")) {
                    customToast.showCustomToast(svContext, str_message, customToast.ToastyError);
                } else {
                    JSONArray data = json.getJSONArray("data");
                    for (int data_i = 0; data_i < ((JSONArray) data).length(); data_i++) {
                        JSONObject data_obj = data.getJSONObject(data_i);
                        String id = data_obj.getString("state_id");
                        String name = data_obj.getString("state_name");

                        listSpinnerSelectWallet.add(new SpinnerModel(id, name));
                    }
                }
            } catch (JSONException e) {
                customToast.showCustomToast(svContext, "Some error occured", customToast.ToastyError);
                e.printStackTrace();
            }
        } else if (url.contains(ApiInterface.AEPSCITYLIST)) {
            listSpinnerPaymentTo = new ArrayList<>();
            try {
                JSONObject json = new JSONObject(result);
                String str_message = json.getString("message");
                String str_status = json.getString("status");
                if (str_status.equalsIgnoreCase("0")) {
                    customToast.showCustomToast(svContext, str_message, customToast.ToastyError);
                } else {
                    JSONArray data = json.getJSONArray("data");
                    for (int data_i = 0; data_i < ((JSONArray) data).length(); data_i++) {
                        JSONObject data_obj = data.getJSONObject(data_i);
                        String id = data_obj.getString("city_id");
                        String name = data_obj.getString("city_name");

                        listSpinnerPaymentTo.add(new SpinnerModel(id, name));
                    }
                }
            } catch (JSONException e) {
                customToast.showCustomToast(svContext, "Some error occured", customToast.ToastyError);
                e.printStackTrace();
            }
        } else if (url.contains(ApiInterface.AEPSOTOAUTH)) {
            try {
                JSONObject json = new JSONObject(result);
                String str_message = json.getString("message");
                String str_status = json.getString("status");

                if (str_status.equalsIgnoreCase("0")) {
                    customToast.showCustomToast(svContext, str_message, customToast.ToastyError);
                } else {
                    customToast.showCustomToast(svContext, str_message, customToast.ToastySuccess);
                }
            } catch (JSONException e) {
                customToast.showCustomToast(svContext, "Some error occured", customToast.ToastyError);
                e.printStackTrace();
            }
        } else if (url.contains(ApiInterface.AEPSRESENDOTP)) {
            try {
                JSONObject json = new JSONObject(result);
                String str_message = json.getString("message");
                String str_status = json.getString("status");

                if (str_status.equalsIgnoreCase("0")) {
                    customToast.showCustomToast(svContext, str_message, customToast.ToastyError);
                } else {
                    customToast.showCustomToast(svContext, str_message, customToast.ToastySuccess);
                }
            } catch (JSONException e) {
                customToast.showCustomToast(svContext, "Some error occured", customToast.ToastyError);
                e.printStackTrace();
            }
        } else if (url.contains(ApiInterface.REQUESTAMOUNT)) {
            try {
                JSONObject json = new JSONObject(result);
                String str_message = json.getString("message");
                String str_status = json.getString("status");

                if (str_status.equalsIgnoreCase("0")) {
                    customToast.showCustomToast(svContext, str_message, customToast.ToastyError);
                } else {
//                    encodeFPTxnId = json.getString("encodeFPTxnId");
//                    ShowOtpScreen();
                    customToast.showCustomToast(svContext, str_message, customToast.ToastySuccess);
                    onBackPressed();
                }
            } catch (JSONException e) {
                customToast.showCustomToast(svContext, "Some error occured", customToast.ToastyError);
                e.printStackTrace();
            }
        } else if (url.contains(ApiInterface.AEPSUPLOADKYC)) {
            try {
                JSONObject json = new JSONObject(result);
                String str_message = json.getString("message");
                String str_status = json.getString("status");

                if (str_status.equalsIgnoreCase("0")) {
                    customToast.showCustomToast(svContext, str_message, customToast.ToastyError);
                } else {
                    customToast.showCustomToast(svContext, str_message, customToast.ToastySuccess);
                    UpdateData();
                }
            } catch (JSONException e) {
                customToast.showCustomToast(svContext, "Some error occured", customToast.ToastyError);
                e.printStackTrace();
            }
        } else if (url.contains(ApiInterface.UPDATEFCM)) {
            ActivitySplash.LoadUserData(result, svContext);
            onBackPressed();
        }
    }

    private void UpdateData() {
        lstUploadData = new LinkedList<>();
        lstUploadData.add(PreferenceConnector.readString(svContext, PreferenceConnector.LOGINEDUSERID, ""));
        lstUploadData.add(PreferenceConnector.readString(svContext, PreferenceConnector.FCMID, ""));
        ;
        lstUploadData.add(PreferenceConnector.readString(svContext, PreferenceConnector.DEVICE_ID, ""));
        callWebService(ApiInterface.UPDATEFCM, lstUploadData);
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