package com.rechargeapp.activity.support;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.commonutility.retrofit.ApiInterface;
import com.commonutility.CheckValidation;
import com.commonutility.GlobalVariables;
import com.commonutility.NoInternetScreen;
import com.commonutility.PreferenceConnector;
import com.commonutility.ShowCustomToast;
import com.commonutility.SpinnerPopulateAdapter;
import com.commonutility.WebService;
import com.commonutility.WebServiceListener;
import com.commonutility.customfont.FontUtils;
import com.codunite.rechargeapp.R;
import com.rechargeapp.activity.ActivityBrowseProfileImage;

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

public class ActivityHelpFeedback extends AppCompatActivity implements View.OnClickListener, WebServiceListener {
    private EditText edCompalintTitle, edCompalintDesc;
    private Button btnRemovePic, btnSelectPic;

    AppCompatButton btnsignUp;
    private ImageView imgProfilePic, imgDrop;
    private TextView txtNoFile;
    public Uri imageUri = null;
    private Spinner ticketType;

    private View[] allViewWithClick = {imgDrop};
    private int[] allViewWithClickId = {R.id.img_drop_1};

    private EditText[] edTexts = {};
    private String[] edTextsError = {"Enter phone number"};
    private int[] editTextsClickId = {};
    private List<String> listSpinner;

    private Context svContext;
    private ShowCustomToast customToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_help);
        StartApp();

        OnClickCombineDeclare(allViewWithClick);
        EditTextDeclare(edTexts);

        resumeApp();
    }

    public void resumeApp() {
        txtNoFile = (TextView) findViewById(R.id.nofile_aadharfront);
        edCompalintTitle = (EditText) findViewById(R.id.et_title);
        edCompalintDesc = (EditText) findViewById(R.id.et_desc);
        btnsignUp = (AppCompatButton) findViewById(R.id.submit_ticket);
        imgProfilePic = (ImageView) findViewById(R.id.imgae_dp);
        btnRemovePic = findViewById(R.id.btn_removepic);
        btnSelectPic = findViewById(R.id.choose_aadharfront);
        btnSelectPic.setOnClickListener(this);

        btnsignUp.setOnClickListener(this);
        imgProfilePic.setOnClickListener(this);
        btnRemovePic.setOnClickListener(this);

        ticketType = (Spinner) findViewById(R.id.spinner_countrylist);

        lstUploadData = new LinkedList<>();
        lstUploadData.add(PreferenceConnector.readString(svContext, PreferenceConnector.LOGINEDUSERID, ""));
        callWebService(ApiInterface.GETTICKETTYPELIST, lstUploadData);



    }

    private void PopulateSpinner(){
        SpinnerPopulateAdapter LegAdapter = new SpinnerPopulateAdapter(svContext, listSpinner, true);
        ticketType.setAdapter(LegAdapter);
        ticketType.setOnItemSelectedListener(onItemSelectedListener);
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
                    switch (v.getId()) {
                        case R.id.img_drop_1:
                            ticketType.performClick();
                            break;
                    }
                }
            });
        }

//        btnBack = (Button) allViewWithClick[0];
    }
    private void StartApp() {
        svContext = this;
        customToast = new ShowCustomToast(svContext);
        
        ViewGroup root = (ViewGroup) findViewById(R.id.headlayout);
        new NoInternetScreen(svContext, root, ActivityHelpFeedback.this);
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

    private ImageView imgToolBarBack;

    private void loadToolBar() {
        imgToolBarBack = (ImageView) findViewById(R.id.img_back);
        imgToolBarBack.setOnClickListener(this);

        TextView txtHeading = (TextView) findViewById(R.id.heading);
        txtHeading.setText("Create Ticket");
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


    public static final String TAG_MESSAGE = "message";
    public static final String TAG_STATUS = "status";

    @Override
    public void onWebServiceActionComplete(String result, String url) {
        if (url.contains(ApiInterface.SUBMITCOMPALINT)) {
            try {
                JSONObject json = new JSONObject(result);

                String str_message = json.getString(TAG_MESSAGE);
                String str_status = json.getString(TAG_STATUS);

                if (str_status.equalsIgnoreCase("0")) {
                    customToast.showCustomToast(svContext, str_message, customToast.ToastyError);
                } else {
                    customToast.showCustomToast(svContext, str_message, customToast.ToastySuccess);
                    onBackPressed();
                }
            } catch (JSONException e) {
                customToast.showCustomToast(svContext, "Some error occured", customToast.ToastyError);
                e.printStackTrace();
            }
        }else if (url.contains(ApiInterface.GETTICKETTYPELIST)) {
            try {
                JSONObject json = new JSONObject(result);

                String str_message = json.getString(TAG_MESSAGE);
                String str_status = json.getString(TAG_STATUS);

                if (str_status.equalsIgnoreCase("0")) {
                    customToast.showCustomToast(svContext, str_message, customToast.ToastyError);
                } else {
                    listSpinner = new ArrayList<>();
                    JSONArray data = json.getJSONArray("data");
                    for (int data_i = 0; data_i < data.length(); data_i++) {
                        JSONObject data_obj = data.getJSONObject(data_i);
                        String str_code = data_obj.getString("id");
                        String str_name = data_obj.getString("title");

                        listSpinner.add(str_code + "#:#" + str_name);
                    }
                    PopulateSpinner();
                }
            } catch (JSONException e) {
                customToast.showCustomToast(svContext, "Some error occured", customToast.ToastyError);
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.submit_ticket:
                SubmitForm();
                break;
            case R.id.img_back:
                onBackPressed();
                break;
            case R.id.imgae_dp:
            case R.id.choose_aadharfront:
                Intent svIntent = new Intent(svContext, ActivityBrowseProfileImage.class);
                startActivity(svIntent);
                ((Activity) svContext).overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                break;
            case R.id.btn_removepic:
                if (imageUri == null) {
                    customToast.showCustomToast(svContext, "No image to remove", customToast.ToastyError);
                } else {
                    imageUri = null;
                    imgProfilePic.setImageDrawable(null);
                    imgProfilePic.setBackgroundResource(R.drawable.users);
                    imgProfilePic.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    txtNoFile.setVisibility(View.VISIBLE);
                }
                break;
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        if (ActivityBrowseProfileImage.imageUri != null) {
            imageUri = ActivityBrowseProfileImage.imageUri;
            imgProfilePic.setImageURI(null);
            imgProfilePic.setBackgroundResource(0);
            imgProfilePic.setImageURI(imageUri);
            ActivityBrowseProfileImage.imageUri = null;
            txtNoFile.setVisibility(View.INVISIBLE);
        } else {
            imgProfilePic.setBackgroundResource(R.drawable.users);
            txtNoFile.setVisibility(View.VISIBLE);
        }
        imgProfilePic.setScaleType(ImageView.ScaleType.FIT_CENTER);
    }

    private void SubmitForm() {
        int response = 0;
        response = CheckValidation.emptyEditTextError(
                new EditText[]{edCompalintTitle, edCompalintDesc},
                new String[]{"enter ticket title", "enter ticket description"});

        if (ticketType.getSelectedItemPosition() == 0) {
            response++;
            customToast.showCustomToast(svContext, "Please select Ticket type", customToast.ToastyWarning);
        }

        if (response == 0) {
            if (imageUri == null) {
                lstUploadData = new LinkedList<>();
                lstUploadData.add(PreferenceConnector.readString(svContext, PreferenceConnector.LOGINEDUSERID, ""));
                lstUploadData.add(edCompalintTitle.getText().toString().trim());
                lstUploadData.add(listSpinner.get(ticketType.getSelectedItemPosition()).split("#:#")[0]);
                lstUploadData.add(edCompalintDesc.getText().toString().trim());
                lstUploadData.add("");
                callWebService(ApiInterface.SUBMITCOMPALINT, lstUploadData);
            } else {
                lstUploadData = new LinkedList<>();
                lstUploadData.add(PreferenceConnector.readString(svContext, PreferenceConnector.LOGINEDUSERID, ""));
                lstUploadData.add(edCompalintTitle.getText().toString().trim());
                lstUploadData.add(listSpinner.get(ticketType.getSelectedItemPosition()).split("#:#")[0]);
                lstUploadData.add(edCompalintDesc.getText().toString().trim());
                lstUploadData.add(encodeImage(imageUri));
                callWebService(ApiInterface.SUBMITCOMPALINT, lstUploadData);
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


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onWebServiceError(String result, String url) {
        customToast.showCustomToast(svContext, result, customToast.ToastyError);
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