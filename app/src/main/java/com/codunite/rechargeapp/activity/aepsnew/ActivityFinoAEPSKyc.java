package com.codunite.rechargeapp.activity.aepsnew;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;

import com.codunite.commonutility.retrofit.ApiInterface;
import com.codunite.rechargeapp.R;
import com.codunite.commonutility.CheckValidation;
import com.codunite.commonutility.GPSTracker;
import com.codunite.commonutility.GlobalVariables;
import com.codunite.commonutility.NoInternetScreen;
import com.codunite.commonutility.PreferenceConnector;
import com.codunite.commonutility.ShowCustomToast;
import com.codunite.commonutility.SpinnerPopulateAdapter;
import com.codunite.commonutility.WebService;
import com.codunite.commonutility.WebServiceListener;
import com.codunite.commonutility.customfont.FontUtils;
import com.codunite.rechargeapp.WebViewActivity;
import com.codunite.rechargeapp.activity.ActivityBrowseProfileImage;
import com.codunite.rechargeapp.activity.ActivityMain;
import com.codunite.rechargeapp.activity.ActivitySplash;
import com.paysprint.onboardinglib.activities.HostActivity;

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

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ActivityFinoAEPSKyc extends AppCompatActivity implements View.OnClickListener, WebServiceListener {
    private Button removeAadharcardFront/*, btnRemoveAadharcardBack*/, btnPanCard;
    private Button btnChooseAadhrFront, /*btnChooseAadharBack,*/
            btnChoosePanCard, btnOtpVerification;
    private ImageView imgDropOne, imgDropTwo;
    private TextView txtOpenMainScreen, txtResendOtp, fingerMachine, Kyc_Charge;

    private View[] allViewWithClick = {btnChooseAadhrFront, /*btnChooseAadharBack,*/ btnChoosePanCard,
            removeAadharcardFront, /*btnRemoveAadharcardBack,*/ btnPanCard, imgDropOne, imgDropTwo, btnOtpVerification,
            txtOpenMainScreen, txtResendOtp, fingerMachine};
    private int[] allViewWithClickId = {R.id.choose_aadharfront, /*R.id.choose_aadharback,*/ R.id.choose_pancard,
            R.id.remove_aadharfront/*, R.id.remove_aadharback*/, R.id.remove_pancard, R.id.img_drop_1,
            R.id.img_drop_2, R.id.btn_transaction_password, R.id.goback, R.id.resendotp, R.id.check_finger_machine};

    private EditText edFirstName, edLastName, edMobile, edShopName, edAddress, edPincode, edAadharCardNumber,
            edPancardNumber, edTranscationOtp;
    private EditText[] edTexts = {edFirstName, edLastName, edMobile, edShopName, edAddress, edPincode, edAadharCardNumber, edPancardNumber};
    private String[] edTextsError = {"Enter first name", "Enter last number", "Enter phone number", "Enter shop name",
            "Enter address", "Ente pincode", "Enter Aadhar card", "Enter pancard"};
    private int[] editTextsClickId = {R.id.ed_first_name, R.id.ed_last_name, R.id.ed_mobile, R.id.ed_shopname,
            R.id.ed_address, R.id.ed_pincode, R.id.ed_adhar_no, R.id.ed_pancard_no};
    private View parentView;
    private NestedScrollView nested_scroll_view;
    private Button bt_save_input;
    private ImageView imgFrontAadharcard, /*imgBackAadharCard,*/
            imgPanCard;
    private int selectedImageView = 0;
    private Spinner spinnercitylist, spinnerstatelist;
    private Uri imageFrontAadharCardUri = null, /*imageBackAadharCardUri = null,*/
            imagePanCardUri = null;
    private RelativeLayout layoutTransactionpassword;
    private String biometricData;
    private String encodeFPTxnId;
    private RadioGroup rgDeviceType;
    private RadioButton radioMantra, radioMorpo;

    private GPSTracker gpsTracker;
    private static final int PERMISSION_CALLBACK_CONSTANT = 100;
    private String[] permissionsRequired = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_aepskyc);

        StartApp();

        OnClickCombineDeclare(allViewWithClick);
        EditTextDeclare(edTexts);

        resumeApp();
    }

    private void resumeApp() {
        Kyc_Charge = findViewById(R.id.kyc_Charge);
        parentView = findViewById(android.R.id.content);
        imgFrontAadharcard = (ImageView) findViewById(R.id.img_frontaadharcard);
//        imgBackAadharCard = (ImageView) findViewById(R.id.img_backaadharcard);
        imgPanCard = (ImageView) findViewById(R.id.img_pancard);
        rgDeviceType = (RadioGroup) findViewById(R.id.device_type);
        radioMantra = (RadioButton) findViewById(R.id.radio_mantra);
        radioMorpo = (RadioButton) findViewById(R.id.radio_morpho);

        spinnerstatelist = (Spinner) findViewById(R.id.spinner_statelist);
        spinnercitylist = (Spinner) findViewById(R.id.spinner_citylist);
        layoutTransactionpassword = (RelativeLayout) findViewById(R.id.layout_transactionpassword);

        listSpinnerStateList = new ArrayList<>();
        listSpinnerCityList = new ArrayList<>();

        lstUploadData = new LinkedList<>();
        lstUploadData.add(PreferenceConnector.readString(svContext, PreferenceConnector.LOGINEDUSERID, ""));
        callWebService(ApiInterface.GETFINOAEPSPIPELINE, lstUploadData);

        listSpinnerStateList.add("-1#:#Loading State");
        listSpinnerCityList.add("-1#:#Select City");
        PopulateState();
        PopulateCity();
        LoadState();
        selectMorpho();

        Kyc_Charge.setText(PreferenceConnector.readString(svContext, PreferenceConnector.FINOKYC_CHARGE, "0"));
        bt_save_input = findViewById(R.id.bt_save_input);
        bt_save_input.setOnClickListener(view -> ProcessingKyc());

        TextView tvMachineType = findViewById(R.id.tv_machinetype);
        tvMachineType.setVisibility(View.GONE);
        rgDeviceType.setVisibility(View.GONE);

        rgDeviceType.setOnCheckedChangeListener((radioGroup, checkedId) -> {
            RadioButton checkedRadioButton = radioGroup.findViewById(checkedId);
            String selectedText = checkedRadioButton.getText().toString();
            if (selectedText.equals(radioMantra.getText().toString())) {
                selecctMyntra();
            } else if (selectedText.equals(radioMorpo.getText().toString())) {
                selectMorpho();
            }
        });

        nested_scroll_view = (NestedScrollView) findViewById(R.id.nested_scroll_view);

        gpsTracker = new GPSTracker(this);
        checkPermissions();

    }

    int deviceType = 0;

    private void selectMorpho() {
        deviceType = 0;
        Intent intent = new Intent("android.intent.action.SCL_RDSERVICE_OTP_RECIEVER");
        //        intent.putExtra("OTP", "SCL-9999-8888-777")
        intent.setPackage("com.scl.rdservice");
        sendBroadcast(intent);
    }

    private void selecctMyntra() {
        deviceType = 1;
    }


    private void LoadState() {
        lstUploadData = new LinkedList<>();
        lstUploadData.add(PreferenceConnector.readString(svContext, PreferenceConnector.LOGINEDUSERID, ""));
        callWebService(ApiInterface.AEPSSTATELIST, lstUploadData);
    }

    List<String> listSpinnerStateList;

    private void PopulateState() {
        SpinnerPopulateAdapter LegAdapter = new SpinnerPopulateAdapter(svContext, listSpinnerStateList, true);
        spinnerstatelist.setAdapter(LegAdapter);

        spinnerstatelist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    lstUploadData = new LinkedList<>();
                    lstUploadData.add(listSpinnerStateList.get(position).split("#:#")[0]);
                    callWebService(ApiInterface.AEPSCITYLIST, lstUploadData);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    List<String> listSpinnerCityList;

    private void PopulateCity() {
        SpinnerPopulateAdapter LegAdapter = new SpinnerPopulateAdapter(svContext, listSpinnerCityList, true);
        spinnercitylist.setAdapter(LegAdapter);
        spinnercitylist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }


    private void ProcessingKyc() {
        int response = 0;
        response = CheckValidation.emptyEditTextError(edTexts, edTextsError);

        if (imgFrontAadharcard == null) {
            response++;
            customToast.showCustomToast(svContext, "Please select front aadhar card", customToast.ToastyWarning);
        }

        if (imgPanCard == null) {
            response++;
            customToast.showCustomToast(svContext, "Please select pan card", customToast.ToastyWarning);
        }

        if (spinnercitylist.getSelectedItemPosition() == 0) {
            response++;
            customToast.showCustomToast(svContext, "Select city", customToast.ToastyError);
        }

        if (spinnerstatelist.getSelectedItemPosition() == 0) {
            response++;
            customToast.showCustomToast(svContext, "Select state", customToast.ToastyError);
        }

        if (response == 0 && (gpsTracker.getLatitude() == 0.0 || gpsTracker.getLongitude() == 0.0)) {
            customToast.showCustomToast(svContext, "locating..", customToast.ToastyInfo);
            if (!gpsTracker.getIsGPSTrackingEnabled()) {
                gpsTracker.showSettingsAlert();
            }
            response++;
        }

        if (response == 0) {
            lstUploadData = new LinkedList<>();
            lstUploadData.add(PreferenceConnector.readString(svContext, PreferenceConnector.LOGINEDUSERID, ""));
            lstUploadData.add(edFirstName.getText().toString().trim());
            lstUploadData.add(edLastName.getText().toString().trim());
            lstUploadData.add(edMobile.getText().toString().trim());
            lstUploadData.add(edShopName.getText().toString().trim());
            lstUploadData.add(listSpinnerStateList.get(spinnerstatelist.getSelectedItemPosition()).split("#:#")[0]);
            lstUploadData.add((listSpinnerCityList.get(spinnercitylist.getSelectedItemPosition()).split("#:#")[0]));
            lstUploadData.add(edAddress.getText().toString().trim());
            lstUploadData.add(edPincode.getText().toString().trim());
            lstUploadData.add(edAadharCardNumber.getText().toString().trim());
            lstUploadData.add(edPancardNumber.getText().toString().trim());
            lstUploadData.add(encodeImage(imageFrontAadharCardUri));
            lstUploadData.add(encodeImage(imagePanCardUri));
            callWebService(ApiInterface.NEW_AEPSACTIVEAUTH, lstUploadData);
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
        return encImage;
    }

    private void EditTextDeclare(EditText[] editTexts) {
        for (int j = 0; j < editTexts.length; j++) {
            editTexts[j] = findViewById(editTextsClickId[j]);
        }

        edFirstName = (EditText) editTexts[0];
        edLastName = (EditText) editTexts[1];
        edMobile = (EditText) editTexts[2];
        edShopName = (EditText) editTexts[3];
        edAddress = (EditText) editTexts[4];
        edPincode = (EditText) editTexts[5];
        edAadharCardNumber = (EditText) editTexts[6];
        edPancardNumber = (EditText) editTexts[7];

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
                        case R.id.choose_aadharfront:
                            selectedImageView = 0;
                            svIntent = new Intent(svContext, ActivityBrowseProfileImage.class);
                            startActivity(svIntent);
                            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                            break;
                        case R.id.choose_aadharback:
                            selectedImageView = 1;
                            svIntent = new Intent(svContext, ActivityBrowseProfileImage.class);
                            startActivity(svIntent);
                            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                            break;
                        case R.id.choose_pancard:
                            selectedImageView = 2;
                            svIntent = new Intent(svContext, ActivityBrowseProfileImage.class);
                            startActivity(svIntent);
                            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                            break;
                        case R.id.remove_aadharfront:
                            imgFrontAadharcard.setImageURI(null);
                            imgFrontAadharcard.setVisibility(View.GONE);
                            break;
                        case R.id.remove_pancard:
                            imgPanCard.setImageURI(null);
                            imgPanCard.setVisibility(View.GONE);
                            break;
                        case R.id.img_drop_1:
                            spinnerstatelist.performClick();
                            break;
                        case R.id.img_drop_2:
                            spinnercitylist.performClick();
                            break;
                        case R.id.goback:
                            ShowMainScreen();
                            break;
                        case R.id.resendotp:
                            ResendOtp();
                            break;
                        case R.id.check_finger_machine:
                            GetDeviceInfo();
                            break;
                        case R.id.btn_transaction_password:
                            VerifyOtp();
                            break;
                    }
                }
            });
        }
    }

    int DEVICE_INFO = 119;
    int AUTHENTICATION_REQUEST = 120;
    private void GetDeviceInfo() {
        Intent intent = new Intent("in.gov.uidai.rdservice.fp.INFO");
        if (deviceType == 0) {
            intent.setPackage("com.scl.rdservice");
        } else {
            intent.setPackage("com.mantra.rdservice");
        }
        startActivityForResult(intent, DEVICE_INFO);
    }

    private void SubmitAutheticateData() {
        String inputPidData = "";

        if (deviceType == 0) {
            inputPidData =
                    "<?xml version=\"1.0\"?><PidOptions ver=\"1.0\"><Opts fCount=\"1\" fType=\"2\" iCount=\"0\" iType=\"0\" pCount=\"0\" pType=\"0\" format=\"0\" pidVer=\"2.0\" timeout=\"15000\" env=\"P\" otp=\"\" wadh=\"E0jzJ/P8UopUHAieZn8CKqS4WPMi5ZSYXgfnlfkWjrc=\" posh=\"UNKNOWN\"/></PidOptions>";
        } else {
            inputPidData =
                    "<?xml version=\"1.0\"?><PidOptions ver=\"1.0\"><Opts fCount=\"1\" fType=\"2\" iCount=\"0\" iType=\"0\" pCount=\"0\" pType=\"0\" format=\"0\" pidVer=\"2.0\" timeout=\"15000\" env=\"P\" otp=\"\" wadh=\"E0jzJ/P8UopUHAieZn8CKqS4WPMi5ZSYXgfnlfkWjrc=\" posh=\"UNKNOWN\"/></PidOptions>";
        }

        try {
            Intent intentCapture = new Intent();
            intentCapture.setAction("in.gov.uidai.rdservice.fp.CAPTURE");
            if (deviceType == 0) {
                intentCapture.setPackage("com.scl.rdservice");
            } else {
                intentCapture.setPackage("com.mantra.rdservice");
            }
            intentCapture.putExtra("PID_OPTIONS", inputPidData);
            startActivityForResult(intentCapture, AUTHENTICATION_REQUEST);
        } catch (Exception ex) {
            ex.printStackTrace();
            showInfoDialog("RD Services App not Installed or Machine not Attached.", SweetAlertDialog.ERROR_TYPE);
        }

    }

    private void showInfoDialog(String title, int type) {
        new SweetAlertDialog(this, type)
                .setTitleText(title)
                .setConfirmText("OK")
                .setConfirmClickListener(Dialog::dismiss)
                .show();
    }


    private List<String> getUngrantedPermissions() {
        List<String> permissions = new ArrayList<>();
        for (String s : permissionsRequired) {
            if (ContextCompat.checkSelfPermission(svContext, s) != PackageManager.PERMISSION_GRANTED)
                permissions.add(s);
        }
        return permissions;
    }

    private void checkPermissions() {
        List<String> permissions = getUngrantedPermissions();
        if (!permissions.isEmpty()) {
            ActivityCompat.requestPermissions(this,
                    permissions.toArray(new String[permissions.size()]),
                    PERMISSION_CALLBACK_CONSTANT);

            SharedPreferences permissionStatus = getSharedPreferences(getString(R.string.app_name) + "_sample", 0);
            permissionStatus.edit().putBoolean(permissionsRequired[0], true).apply();
        } else {
            // check GPS Permission
            if (gpsTracker.getIsGPSTrackingEnabled()) {
                Toast.makeText(svContext, "locating..", Toast.LENGTH_SHORT).show();
            } else {
                gpsTracker.showSettingsAlert();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_CALLBACK_CONSTANT:
                boolean allgranted = false;
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        allgranted = true;
                    } else {
                        allgranted = false;
                        break;
                    }
                }

                if (allgranted) {
                    if (gpsTracker.getIsGPSTrackingEnabled()) {
                        Toast.makeText(svContext, "locating..", Toast.LENGTH_SHORT).show();
                    } else {
                        gpsTracker.showSettingsAlert();
                    }
                } else if (getUngrantedPermissions().size() > 0) {
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(svContext);
                    builder.setTitle("Need some Permissions");
                    builder.setMessage("This app needs permission to get Device Information");
                    builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            ActivityCompat.requestPermissions(ActivityFinoAEPSKyc.this, permissionsRequired, PERMISSION_CALLBACK_CONSTANT);
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.show();
                } else {
                    Toast.makeText(getBaseContext(), "Unable to get Permission", Toast.LENGTH_LONG).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == DEVICE_INFO && resultCode == RESULT_OK) {
            try {
                if (data != null) {
                    String deviceInfo = data.getStringExtra("DEVICE_INFO");
                    String rdServiceInfo = data.getStringExtra("RD_SERVICE_INFO");
                    String dnc = data.getStringExtra("DNC");
                    String dnr = data.getStringExtra("DNR");

                    String display = "";
                    if (dnc != null || dnr != null) {
                        if (dnc != null) {
                            customToast.showCustomToast(svContext, "Device not connected", customToast.ToastyError);
                        } else {
                            customToast.showCustomToast(svContext, "Device not registered", customToast.ToastyError);
                        }
                    } else {
                        display += "DNC Info :\n$dnc\n\n";
                        display += "DNR Info :\n$dnr\n\n";
                        display += "Device Info :\n$deviceInfo\n\n";
                        if (rdServiceInfo != null) {
                            display += "Rd Service Info :\n$rdServiceInfo\n\n";
                        }
                    }
                }
            } catch (Exception e) {
                Log.e("Error", "Error while deserialze device info", e);
            }
        } else if (requestCode == 999) {
            if (resultCode == RESULT_OK) {
                String TAG = "finishOnBoarding";
                boolean status = data.getBooleanExtra("status", false);
                int response = data.getIntExtra("response", 0);
                String message = data.getStringExtra("message");
                String detailedResponse = "Status: " + status +
                        "\nResponse: " + response +
                        "\nMessage: " + message;

                Log.e(TAG, detailedResponse);
                if (status && response == 1 && message.equals("Account is activated")) {
                    lstUploadData = new LinkedList<>();
                    lstUploadData.add(PreferenceConnector.readString(svContext, PreferenceConnector.LOGINEDUSERID, ""));
                    callWebService(ApiInterface.NEW_AEPS_STATUS_ACTIVE, lstUploadData);
                } else {
                    new SweetAlertDialog(svContext, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                            .setTitleText("Info")
                            .setContentText(detailedResponse)
                            .setContentTextSize(17)
                            .setCustomImage(R.drawable.logo_color)
                            .setConfirmButton("OK", sweetAlertDialog -> {
                                sweetAlertDialog.dismiss();
                                if (status && response != 0) {
                                    finish();
                                }
                            })
                            .show();
                }
            }
        }
    }

    public String RemoveSpace(String input) {
        return input.replaceAll("(?!>\\s+</)(>\\s+<)", "><");
    }

    private void VerifyOtp() {
        lstUploadData = new LinkedList<>();
        lstUploadData.add(PreferenceConnector.readString(svContext, PreferenceConnector.LOGINEDUSERID, ""));
        lstUploadData.add(encodeFPTxnId);
        lstUploadData.add(edTranscationOtp.getText().toString().trim());
        callWebService(ApiInterface.AEPSICICOTOAUTH, lstUploadData);
    }

    private void FinalUpload(String biometData) {
        lstUploadData = new LinkedList<>();
        lstUploadData.add(PreferenceConnector.readString(svContext, PreferenceConnector.LOGINEDUSERID, ""));
        lstUploadData.add(encodeFPTxnId);
        lstUploadData.add(biometData);
        callWebService(ApiInterface.AEPSICICUPLOADKYC, lstUploadData);
    }

    private void ResendOtp() {
        lstUploadData = new LinkedList<>();
        lstUploadData.add(PreferenceConnector.readString(svContext, PreferenceConnector.LOGINEDUSERID, ""));
        lstUploadData.add(encodeFPTxnId);
        callWebService(ApiInterface.AEPSICICRESENDOTP, lstUploadData);
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
                imgFrontAadharcard.setImageURI(null);
                imgFrontAadharcard.setImageURI(ActivityBrowseProfileImage.imageUri);
                imgFrontAadharcard.setVisibility(View.VISIBLE);
            } else if (selectedImageView == 1) {
//                imageBackAadharCardUri = ActivityBrowseProfileImage.imageUri;
//                imgBackAadharCard.setImageURI(null);
//                imgBackAadharCard.setImageURI(ActivityBrowseProfileImage.imageUri);
//                imgBackAadharCard.setVisibility(View.VISIBLE);
            } else if (selectedImageView == 2) {
                imagePanCardUri = ActivityBrowseProfileImage.imageUri;
                imgPanCard.setImageURI(null);
                imgPanCard.setImageURI(ActivityBrowseProfileImage.imageUri);
                imgPanCard.setVisibility(View.VISIBLE);
            }
            ActivityBrowseProfileImage.imageUri = null;
        }
    }


    private Context svContext;
    private ShowCustomToast customToast;
    
    

    private void StartApp() {
        svContext = this;
        customToast = new ShowCustomToast(svContext);

        ViewGroup root = (ViewGroup) findViewById(R.id.headlayout);
        new NoInternetScreen(svContext, root, ActivityFinoAEPSKyc.this);
        if (!GlobalVariables.CUSTOMFONTNAME.equals("")) {
            Typeface font = Typeface.createFromAsset(getAssets(), GlobalVariables.CUSTOMFONTNAME);
            FontUtils.setFont(root, font);
        }

        hideKeyboard();

        loadToolBar();
    }

    private ImageView imgToolBarBack;

    private void loadToolBar() {
        imgToolBarBack = (ImageView) findViewById(R.id.img_back);
        imgToolBarBack.setOnClickListener(this);

        TextView txtHeading = (TextView) findViewById(R.id.heading);
        txtHeading.setText("Fino AEPS Kyc");
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
            listSpinnerStateList = new ArrayList<>();
            listSpinnerStateList.add("-1#:#Select State");
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

                        listSpinnerStateList.add(data_obj.getString("state_id") + "#:#" + data_obj.getString("state_name"));
                    }

                    PopulateState();
                }
            } catch (JSONException e) {
                customToast.showCustomToast(svContext, "Some error occured", customToast.ToastyError);
                e.printStackTrace();
            }
        } else if (url.contains(ApiInterface.GETFINOAEPSPIPELINE)) {
            try {
                JSONObject json = new JSONObject(result);
                String str_message = json.getString("msg");
                String str_status = json.getString("status");

                if (str_status.equalsIgnoreCase("0")) {
                    if (json.has("is_approved") && json.getString("is_approved").equalsIgnoreCase("4")) {
                        customToast.showCustomToast(svContext, str_message, customToast.ToastyError);
                    }
                } else {
                    if (json.has("is_approved") && json.getString("is_approved").equalsIgnoreCase("1")) {
                        Intent svIntent = new Intent(svContext, ActivityFinoAEPSBankingService.class);
                        svIntent.putExtra("selecteditem", "AEPS");
                        startActivity(svIntent);
                        finish();
                    } else {
                        customToast.showCustomToast(svContext, str_message, customToast.ToastyInfo);
                    }
                }
            } catch (JSONException e) {
                customToast.showCustomToast(svContext, "Some error occured", customToast.ToastyError);
                e.printStackTrace();
            }
        } else if (url.contains(ApiInterface.AEPSCITYLIST)) {
            listSpinnerCityList = new ArrayList<>();
            listSpinnerCityList.add("-1#:#Select city");
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
                        listSpinnerCityList.add(data_obj.getString("city_id") + "#:#"
                                + data_obj.getString("city_name"));
                    }

                    PopulateCity();
                }
            } catch (JSONException e) {
                customToast.showCustomToast(svContext, "Some error occured", customToast.ToastyError);
                e.printStackTrace();
            }
        } else if (url.contains(ApiInterface.AEPSICICOTOAUTH)) {
            try {
                JSONObject json = new JSONObject(result);

                String str_message = json.getString("message");
                String str_status = json.getString("status");

                if (str_status.equalsIgnoreCase("0")) {
                    customToast.showCustomToast(svContext, str_message, customToast.ToastyError);
                } else {
                    customToast.showCustomToast(svContext, str_message, customToast.ToastySuccess);
                    SubmitAutheticateData();
                }
            } catch (JSONException e) {
                customToast.showCustomToast(svContext, "Some error occured", customToast.ToastyError);
                e.printStackTrace();
            }
        } else if (url.contains(ApiInterface.AEPSICICRESENDOTP)) {
            try {
                JSONObject json = new JSONObject(result);

                String str_message = json.getString("message");
                String str_status = json.getString("status");

                if (str_status.equalsIgnoreCase("0")) {
                    customToast.showCustomToast(svContext, str_message, customToast.ToastyError);
                } else if (str_status.equalsIgnoreCase("2")) {
                    PreferenceConnector.writeBoolean(svContext, PreferenceConnector.ISICICIAEPSKYCDONE, true);
                    //ActivityMain.OpenNewAeps("Fino AEPS", svContext, customToast);
                    finish();
                } else {
                    customToast.showCustomToast(svContext, str_message, customToast.ToastySuccess);
                }
            } catch (JSONException e) {
                customToast.showCustomToast(svContext, "Some error occured", customToast.ToastyError);
                e.printStackTrace();
            }
        } else if (url.contains(ApiInterface.NEW_AEPSACTIVEAUTH)) {
            try {
                JSONObject json = new JSONObject(result);
                String str_message = json.getString("message");
                String str_status = json.getString("status");

                if (str_status.equalsIgnoreCase("0")) {
                    customToast.showCustomToast(svContext, str_message, customToast.ToastyError);
                } else {
                    //String kycUrl = json.getString("redirecturl");
                    //ActivityMain.openWebPage(svContext, kycUrl);
                    //finish();

                    startOnBoardingPage();

//                    encodeFPTxnId = json.getString("encodeFPTxnId");
//                    ShowOtpScreen();
//                    customToast.showCustomToast(svContext, str_message, customToast.ToastySuccess);
                }
            } catch (JSONException e) {
                customToast.showCustomToast(svContext, "Some error occured", customToast.ToastyError);
                e.printStackTrace();
            }
        } else if (url.contains(ApiInterface.NEW_AEPS_STATUS_ACTIVE)) {
            try {
                JSONObject json = new JSONObject(result);
                String str_message = json.getString("message");
                String str_status = json.getString("status");
                if (str_status.equalsIgnoreCase("0")) {
                    customToast.showCustomToast(svContext, str_message, customToast.ToastyError);
                } else {
                    customToast.showCustomToast(svContext, str_message, customToast.ToastySuccess);
                }
                finish();
            } catch (JSONException e) {
                customToast.showCustomToast(svContext, "Some error occured", customToast.ToastyError);
                e.printStackTrace();
            }
        } else if (url.contains(ApiInterface.AEPSICICUPLOADKYC)) {
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
        callWebService(ApiInterface.UPDATEFCM, lstUploadData);
    }

    @Override
    public void onWebServiceError(String result, String url) {
        customToast.showCustomToast(svContext, result, customToast.ToastyError);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void startOnBoardingPage() {
        String strMemberMobile = edMobile.getText().toString().trim();
        String strShopName = edShopName.getText().toString().trim();

        String strPartnerId = PreferenceConnector.readString(svContext, PreferenceConnector.PARTNER_ID, "");
        String strPartnerKey = PreferenceConnector.readString(svContext, PreferenceConnector.PARTNER_KEY, "");
        String strMemberId = PreferenceConnector.readString(svContext, PreferenceConnector.LOGINMEMBERID, "");
        String strMemberEmail = PreferenceConnector.readString(svContext, PreferenceConnector.LOGINUSEREMAIL, "");

        Intent intent = new Intent(svContext, HostActivity.class);
        intent.putExtra("pId", strPartnerId);
        intent.putExtra("pApiKey", strPartnerKey);
        intent.putExtra("mCode", strMemberId);  //Merchant Code
        intent.putExtra("mobile", strMemberMobile); // merchant mobile number
        intent.putExtra("lat", "" + gpsTracker.getLatitude());
        intent.putExtra("lng", "" + gpsTracker.getLongitude());
        intent.putExtra("firm", strShopName);
        intent.putExtra("email", strMemberEmail);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivityForResult(intent, 999);
    }

    private String strDemoServiceName = "", dtrDemoServiceUrl = "";


}