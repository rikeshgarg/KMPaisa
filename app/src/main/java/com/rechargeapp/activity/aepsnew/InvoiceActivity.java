package com.rechargeapp.activity.aepsnew;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.asksira.webviewsuite.WebViewSuite;
import com.codunite.rechargeapp.R;
import com.commonutility.PreferenceConnector;
import com.commonutility.ShowCustomToast;

public class InvoiceActivity extends AppCompatActivity implements View.OnClickListener{
    private WebViewSuite webViewSuite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_webview);

        webViewSuite    = findViewById(R.id.webViewSuite);
        TextView txtShareReceipt = findViewById(R.id.txt_sharereceipt);
        txtShareReceipt.setVisibility(View.VISIBLE);

        ProgressBar pDialog = new ProgressBar(this);

        webViewSuite.startLoading(PreferenceConnector.readString(this, PreferenceConnector.WEBURL, ""));
        webViewSuite.setCustomProgressBar(pDialog);

        webViewSuite.setOpenPDFCallback(new WebViewSuite.WebViewOpenPDFCallback() {
            @Override
            public void onOpenPDF() {
                onBackPressed();
            }
        });


        txtShareReceipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShareText();
            }
        });

        webViewSuite.customizeClient(new WebViewSuite.WebViewSuiteCallback() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                //Do your own stuffs. These will be executed after default onPageStarted().
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                //Do your own stuffs. These will be executed after default onPageFinished().
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //Override those URLs you need and return true.
                //Return false if you don't need to override that URL.
                if (url.contains("wa.me")) {
                    openWhatsApp();
                    onBackPressed();
                }else {
                    view.loadUrl(url);
                }
                return false;
            }
        });

        loadToolBar();
    }

    private void ShareText() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        String shareBody = "This is the receipt of your transcation\n\n";
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Receipt");
        intent.putExtra(Intent.EXTRA_TEXT, shareBody + "This is the receipt of your transcation" + "\n\n" +
                PreferenceConnector.readString(this, PreferenceConnector.WEBURL, ""));
        startActivity(Intent.createChooser(intent, "Refer and Earn"));
    }

    private void loadToolBar() {
        ImageView imgToolBarBack = (ImageView) findViewById(R.id.img_back);
        imgToolBarBack.setOnClickListener(this);

        TextView txtHeading = (TextView) findViewById(R.id.heading);
        txtHeading.setText(PreferenceConnector.readString(this, PreferenceConnector.WEBHEADING, ""));

        LinearLayout toolbar_txt_walletbal = (LinearLayout) findViewById(R.id.img_wallet);
        toolbar_txt_walletbal.setVisibility(View.INVISIBLE);

        LinearLayout toolbar_txt_ewalletbal = (LinearLayout) findViewById(R.id.img_ewallet);
        toolbar_txt_ewalletbal.setVisibility(View.INVISIBLE);
    }


    private void openWhatsApp() {
        String smsNumber = "919116669453"; // E164 format without '+' sign
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.setType("text/plain");
        sendIntent.putExtra(Intent.EXTRA_TEXT, "I want to know about your services.");
        sendIntent.putExtra("jid", smsNumber + "@s.whatsapp.net"); //phone number without "+" prefix
        sendIntent.setPackage("com.whatsapp");
        if (sendIntent.resolveActivity(getPackageManager()) == null) {
            new ShowCustomToast(InvoiceActivity.this).showToast("Whatsapp have not been installed.", InvoiceActivity.this);
            return;
        }
        startActivity(sendIntent);
    }

    @Override
    public void onClick(View view) {
        int response;
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
        }
    }

    private void onRefreshWebView() {
        webViewSuite.refresh();
    }

    private void LoadStaticData() {
//        webViewSuite.startLoadData(data, mimeType, encoding);
    }

    @Override
    public void onBackPressed() {
        if (!webViewSuite.goBackIfPossible()) super.onBackPressed();
    }
}
