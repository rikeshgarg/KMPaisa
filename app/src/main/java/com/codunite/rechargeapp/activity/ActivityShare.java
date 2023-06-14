package com.codunite.rechargeapp.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.codunite.commonutility.GlobalVariables;
import com.codunite.commonutility.NoInternetScreen;
import com.codunite.commonutility.PreferenceConnector;
import com.codunite.commonutility.ShowCustomToast;
import com.codunite.commonutility.customfont.FontUtils;
import com.codunite.rechargeapp.R;

public class ActivityShare extends AppCompatActivity implements View.OnClickListener {
    private TextView copyandshare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_share);

        StartApp();
        resumeApp();
    }

    public void resumeApp() {
        copyandshare = (Button) findViewById(R.id.btn_copy_share);
        TextView referral = (TextView) findViewById(R.id.referallink);
        referral.setOnClickListener(v -> {
            ClipboardManager clipboard = (ClipboardManager) svContext.getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("EditText", referral.getText().toString());
            clipboard.setPrimaryClip(clip);
            customToast.showCustomToast(svContext, "Link Copied", customToast.ToastyNormal);
        });

        String deepLink;
        deepLink = PreferenceConnector.readString(svContext, PreferenceConnector.REFFERALLINK, "0");
        referral.setText(deepLink);

        String firebaseLink = "https://sonikapay.page.link/?";
        String webLink = "link=https://www.sonikapay.com/register/?referral_id%3D" +
                PreferenceConnector.readString(svContext, PreferenceConnector.LOGINMEMBERID, "0");
        String endLink = "&apn=com.codunite.sonikapay" + "&efr=1";
        String dynamicLink = firebaseLink + webLink + endLink;

        referral.setText(dynamicLink);

        copyandshare.setOnClickListener((View.OnClickListener) v -> {
            ClipboardManager clipboard = (ClipboardManager) svContext.getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("EditText", referral.getText().toString());
            clipboard.setPrimaryClip(clip);
            customToast.showCustomToast(svContext, "Link Copied", customToast.ToastyNormal);
            Intent intent = new Intent(Intent.ACTION_SEND);
            String shareBody = "Hi, I just invited you to use the sonikapay app!\n" +
                    "\n" +
                    "Step1: Use my link to download the app\n" +
                    "Step2: add money\n" +
                    "Step3: Start making 24x7 instant  payments for money transfers, recharges, bill payments & more.\n" +
                    "\n" +
                    "\n" +
                    "Most Indians use with SONIKAPAY It's 100% safe & secure.\n" +
                    "Download the app now. "
                    + referral.getText().toString();
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_SUBJECT, "Share Refer Link");
            intent.putExtra(Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(intent, "Share Via"));
        });

    }

    private Context svContext;
    private ShowCustomToast customToast;
    private void StartApp() {
        svContext = this;
        customToast = new ShowCustomToast(svContext);
        
        ViewGroup root = (ViewGroup) findViewById(R.id.headlayout);
        new NoInternetScreen(svContext, root, ActivityShare.this);
        if (!GlobalVariables.CUSTOMFONTNAME.equals("")) {
            Typeface font = Typeface.createFromAsset(getAssets(), GlobalVariables.CUSTOMFONTNAME);
            FontUtils.setFont(root, font);
        }
        hideKeyboard();
        loadToolBar();
    }

    private void loadToolBar() {
        ImageView imgToolBarBack = (ImageView) findViewById(R.id.img_back);
        imgToolBarBack.setOnClickListener(this);

        TextView txtHeading = (TextView) findViewById(R.id.heading);
        txtHeading.setText("Refer & Earn");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                onBackPressed();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}