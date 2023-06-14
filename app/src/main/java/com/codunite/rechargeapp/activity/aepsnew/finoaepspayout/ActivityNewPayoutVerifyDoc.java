package com.codunite.rechargeapp.activity.aepsnew.finoaepspayout;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.codunite.commonutility.retrofit.ApiInterface;
import com.codunite.rechargeapp.R;
import com.codunite.commonutility.GlobalVariables;
import com.codunite.commonutility.NoInternetScreen;
import com.codunite.commonutility.PreferenceConnector;
import com.codunite.commonutility.ShowCustomToast;
import com.codunite.commonutility.WebService;
import com.codunite.commonutility.WebServiceListener;
import com.codunite.commonutility.customfont.FontUtils;
import com.codunite.rechargeapp.activity.ActivityBrowseProfileImage;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ActivityNewPayoutVerifyDoc extends AppCompatActivity implements View.OnClickListener, WebServiceListener {
    private Button btnChooseAadhrFront, btnChooseAadharBack, btnChoosePanCard, btnChooseBankPass;
    private Button removeAadharcardFront, removeAadharcardBack, removePanCard, removeBankPass;

    private View[] allViewWithClick = {btnChooseAadhrFront, btnChooseAadharBack, btnChoosePanCard, btnChooseBankPass,
            removeAadharcardFront, removeAadharcardBack, removePanCard, removeBankPass};
    private int[] allViewWithClickId = {R.id.choose_aadharfront, R.id.choose_aadharback, R.id.choose_pancard, R.id.choose_bankpassbook,
            R.id.remove_aadharfront, R.id.remove_aadharback, R.id.remove_pancard, R.id.remove_bankpassbook};

    private EditText[] edTexts = {};
    private String[] edTextsError = {};
    private int[] editTextsClickId = {};
    private View parentView;

    private WebView webViewKyc;
    private String mCameraPhotoPath;
    private ValueCallback<Uri[]> mFilePathCallback;
    private final static int FILECHOOSER_RESULTCODE = 11;
    private final static String TAG = ActivityNewPayoutVerifyDoc.class.getSimpleName();

    private LinearLayout layVerifyDoc, layWebViewKyc;
    private LinearLayout layBankPassbook, layPancard, layAadharFornt, layAadharBack;
    private Button bt_save_input;
    private RadioGroup radioGroupDocType;
    private RadioButton radioPancard, radiuoAadharCard;

    private int selectedImageView = 0;
    private ImageView imgFrontAadharcard, imgBackAadharCard, imgPanCard, imgBankPass;
    private Uri imageFrontAadharCardUri = null, imageBackAadharCardUri = null, imagePanCardUri = null, imageBankPassbookUri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_new_aepspayout_kyc);
        StartApp();
        if (getIntent().hasExtra("str_benId")) {
            strBeneId = getIntent().getStringExtra("str_benId");
        }
        if (getIntent().hasExtra("redirect_url")) {
            strRedirectUrl = getIntent().getStringExtra("redirect_url");
        }

        OnClickCombineDeclare(allViewWithClick);
        EditTextDeclare(edTexts);

        resumeApp();
    }

    private void resumeApp() {
        parentView = findViewById(android.R.id.content);
        imgBankPass = (ImageView) findViewById(R.id.img_bankpassbook);
        imgFrontAadharcard = (ImageView) findViewById(R.id.img_frontaadharcard);
        imgBackAadharCard = (ImageView) findViewById(R.id.img_backaadharcard);
        imgPanCard = (ImageView) findViewById(R.id.img_pancard);

        radioGroupDocType = (RadioGroup) findViewById(R.id.doc_type);
        radioPancard = (RadioButton) findViewById(R.id.radio_pancard);
        radiuoAadharCard = (RadioButton) findViewById(R.id.radio_aadhar);

        layVerifyDoc = findViewById(R.id.lay_kyc_doc_verify);
        layWebViewKyc = findViewById(R.id.lay_web_payoutkyc);
        webViewKyc = findViewById(R.id.webview);

        layBankPassbook = findViewById(R.id.lay_bankpassbook);
        layPancard = findViewById(R.id.lay_pancard);
        layAadharFornt = findViewById(R.id.lay_aadharfront);
        layAadharBack = findViewById(R.id.lay_aadharback);

        bt_save_input = (Button) findViewById(R.id.bt_save_input);
        bt_save_input.setOnClickListener(view -> ProcessingDocument());

        radioGroupDocType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                RadioButton checkedRadioButton = radioGroup.findViewById(checkedId);
                String selectedText = checkedRadioButton.getText().toString();
                if (selectedText.equals(radioPancard.getText().toString())) {
                    selectPancard();
                } else if (selectedText.equals(radiuoAadharCard.getText().toString())) {
                    selecctAadhar();
                }
            }
        });

        selectPancard();

        if (strRedirectUrl.equals("")) {
            layVerifyDoc.setVisibility(View.VISIBLE);
            layWebViewKyc.setVisibility(View.GONE);
        } else {
            layWebViewKyc.setVisibility(View.VISIBLE);
            layVerifyDoc.setVisibility(View.GONE);

            Button btnGoBack = findViewById(R.id.btn_goback);
            btnGoBack.setOnClickListener(v -> {
                hideKeyboard();
                finish();
            });

            setWebViewSettings(webViewKyc);
            setWebViewListeners(webViewKyc);
        }
    }

    private String strBeneId = "";
    private String strRedirectUrl = "";
    private String documentType = "PAN";

    private void selectPancard() {
        documentType = "PAN";
        layBankPassbook.setVisibility(View.VISIBLE);
        layPancard.setVisibility(View.VISIBLE);
        layAadharFornt.setVisibility(View.GONE);
        layAadharBack.setVisibility(View.GONE);

        imgFrontAadharcard.setImageURI(null);
        imgFrontAadharcard.setVisibility(View.GONE);
        imageFrontAadharCardUri = null;

        imgBackAadharCard.setImageURI(null);
        imgBackAadharCard.setVisibility(View.GONE);
        imageBackAadharCardUri = null;

    }

    private void selecctAadhar() {
        documentType = "AADHAAR";
        layBankPassbook.setVisibility(View.VISIBLE);
        layAadharFornt.setVisibility(View.VISIBLE);
        layAadharBack.setVisibility(View.VISIBLE);
        layPancard.setVisibility(View.GONE);

        imgPanCard.setImageURI(null);
        imgPanCard.setVisibility(View.GONE);
        imagePanCardUri = null;

    }

    private void ProcessingDocument() {
        int response = 0;
        if (imageBankPassbookUri == null) {
            response++;
            customToast.showCustomToast(svContext, "Please select bank passbook", customToast.ToastyWarning);
        }

        if (documentType.equals("PAN")) {
            if (response == 0 && imagePanCardUri == null) {
                response++;
                customToast.showCustomToast(svContext, "Please select pan card", customToast.ToastyWarning);
            }

        } else {
            if (response == 0 && imageFrontAadharCardUri == null) {
                response++;
                customToast.showCustomToast(svContext, "Please select front aadhar card", customToast.ToastyWarning);
            }

            if (response == 0 && imageBackAadharCardUri == null) {
                response++;
                customToast.showCustomToast(svContext, "Please select back aadhar card", customToast.ToastyWarning);
            }
        }

        if (response == 0) {
            lstUploadData = new LinkedList<>();
            lstUploadData.add(PreferenceConnector.readString(svContext, PreferenceConnector.LOGINEDUSERID, ""));
            lstUploadData.add(strBeneId);
            lstUploadData.add(documentType);
            lstUploadData.add(encodeImage(imageBankPassbookUri));
            lstUploadData.add(encodeImage(imagePanCardUri));
            lstUploadData.add(encodeImage(imageFrontAadharCardUri));
            lstUploadData.add(encodeImage(imageBackAadharCardUri));
            callWebService(ApiInterface.NEW_PAYOUT_DOC_AUTH, lstUploadData);
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
                        case R.id.choose_bankpassbook:
                            selectedImageView = 3;
                            svIntent = new Intent(svContext, ActivityBrowseProfileImage.class);
                            startActivity(svIntent);
                            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                            break;
                        case R.id.remove_aadharfront:
                            imgFrontAadharcard.setImageURI(null);
                            imgFrontAadharcard.setVisibility(View.GONE);
                            break;
                        case R.id.remove_aadharback:
                            imgBackAadharCard.setImageURI(null);
                            imgBackAadharCard.setVisibility(View.GONE);
                            break;
                        case R.id.remove_pancard:
                            imgPanCard.setImageURI(null);
                            imgPanCard.setVisibility(View.GONE);
                        case R.id.remove_bankpassbook:
                            imgBankPass.setImageURI(null);
                            imgBankPass.setVisibility(View.GONE);
                            break;
                    }
                }
            });
        }
//        btnBack = (Button) allViewWithClick[0];
    }


    private void showInfoDialog(String title, int type) {
        new SweetAlertDialog(this, type)
                .setTitleText(title)
                .setConfirmText("OK")
                .setConfirmClickListener(Dialog::dismiss)
                .show();
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
                imageBackAadharCardUri = ActivityBrowseProfileImage.imageUri;
                imgBackAadharCard.setImageURI(null);
                imgBackAadharCard.setImageURI(ActivityBrowseProfileImage.imageUri);
                imgBackAadharCard.setVisibility(View.VISIBLE);
            } else if (selectedImageView == 2) {
                imagePanCardUri = ActivityBrowseProfileImage.imageUri;
                imgPanCard.setImageURI(null);
                imgPanCard.setImageURI(ActivityBrowseProfileImage.imageUri);
                imgPanCard.setVisibility(View.VISIBLE);
            } else if (selectedImageView == 3) {
                imageBankPassbookUri = ActivityBrowseProfileImage.imageUri;
                imgBankPass.setImageURI(null);
                imgBankPass.setImageURI(ActivityBrowseProfileImage.imageUri);
                imgBankPass.setVisibility(View.VISIBLE);
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
        new NoInternetScreen(svContext, root, ActivityNewPayoutVerifyDoc.this);
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
        
        

        loadToolBar();
    }

    private void loadToolBar() {
        ImageView imgToolBarBack = (ImageView) findViewById(R.id.img_back);
        imgToolBarBack.setOnClickListener(this);

        TextView txtHeading = (TextView) findViewById(R.id.heading);
        txtHeading.setText("Fino AEPS Settlement Document Verify");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                hideKeyboard();
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
        if (url.contains(ApiInterface.NEW_PAYOUT_DOC_AUTH)) {
            try {
                JSONObject json = new JSONObject(result);
                String str_message = json.getString("message");
                String str_status = json.getString("status");

                if (str_status.equalsIgnoreCase("0")) {
                    customToast.showCustomToast(svContext, str_message, customToast.ToastyError);
                } else {
//                    JSONArray data = json.getJSONArray("data");
//                    for (int data_i = 0; data_i < ((JSONArray) data).length(); data_i++) {
//                        JSONObject data_obj = data.getJSONObject(data_i);
//                    }
                    customToast.showCustomToast(svContext, str_message, customToast.ToastySuccess);
                    finish();
                }
            } catch (JSONException e) {
                customToast.showCustomToast(svContext, "Some error occured", customToast.ToastyError);
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onWebServiceError(String result, String url) {
        customToast.showCustomToast(svContext, result, customToast.ToastyError);
    }

    @Override
    public void onBackPressed() {
        if (webViewKyc != null && webViewKyc.canGoBack() == true) {
            webViewKyc.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode != FILECHOOSER_RESULTCODE || mFilePathCallback == null) {
            super.onActivityResult(requestCode, resultCode, data);
            return;
        }

        if (requestCode == FILECHOOSER_RESULTCODE) {
            Uri[] results = null;
            // Check that the response is a good one
            if (resultCode == Activity.RESULT_OK) {
                if (data == null) {
                    // If there is not data, then we may have taken a photo
                    if (mCameraPhotoPath != null) {
                        results = new Uri[]{Uri.parse(mCameraPhotoPath)};
                    }
                } else {
                    String dataString = data.getDataString();
                    if (dataString != null) {
                        results = new Uri[]{Uri.parse(dataString)};
                    }
                }
            }

            mFilePathCallback.onReceiveValue(results);
            mFilePathCallback = null;
        }
    }

    public void setWebViewSettings(WebView webView) {
        WebSettings settings = webView.getSettings();

        // Enable Javascript
        settings.setJavaScriptEnabled(true);

        // Use WideViewport and Zoom out if there is no viewport defined
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);

        settings.setAllowFileAccess(true);
    }

    public void setWebViewListeners(WebView webView) {
        webView.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                webView.loadUrl("about:blank");
                view.clearHistory();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {
            public boolean onShowFileChooser(
                    WebView webView, ValueCallback<Uri[]> filePathCallback,
                    FileChooserParams fileChooserParams) {
                if (mFilePathCallback != null) {
                    mFilePathCallback.onReceiveValue(null);
                }
                mFilePathCallback = filePathCallback;

                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    // Create the File where the photo should go
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                        takePictureIntent.putExtra("PhotoPath", mCameraPhotoPath);
                    } catch (IOException ex) {
                        // Error occurred while creating the File
                        Log.e(TAG, "Unable to create Image File", ex);
                    }

                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        mCameraPhotoPath = "file:" + photoFile.getAbsolutePath();
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                    } else {
                        takePictureIntent = null;
                    }
                }

                Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
                contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
                contentSelectionIntent.setType("image/*");

                Intent[] intentArray;
                if (takePictureIntent != null) {
                    intentArray = new Intent[]{takePictureIntent};
                } else {
                    intentArray = new Intent[0];
                }

                Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
                chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
                chooserIntent.putExtra(Intent.EXTRA_TITLE, "Image Chooser");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray);
                startActivityForResult(chooserIntent, FILECHOOSER_RESULTCODE);
                return true;
            }
        });

        webView.loadUrl(strRedirectUrl);

//        final MyJavaScriptInterface myJavaScriptInterface = new MyJavaScriptInterface(this);
//        webView.addJavascriptInterface(myJavaScriptInterface, "AndroidFunction");
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File imageFile = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        return imageFile;
    }


}