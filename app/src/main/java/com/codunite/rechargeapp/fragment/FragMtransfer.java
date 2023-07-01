package com.codunite.rechargeapp.fragment;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


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
import com.codunite.rechargeapp.R;
import com.codunite.rechargeapp.activity.ActivityRecharge;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;

public class FragMtransfer extends Fragment implements View.OnClickListener, WebServiceListener {
    private Context svContext;
    private ShowCustomToast customToast;
    private CheckInternet checkNetwork;
    private View aiView = null;
    private boolean mAlreadyLoaded = false;
    private EditText edBeneficiryName, edMobile, edTransferAmount, edBenefeciaryAccNumber, edReBenefeciaryAccNumber,
            benefeciaryIfsc;
    private Button btnProceed;

    private ImageView imgBack;
    private View[] allViewWithClick = {btnProceed};
    private int[] allViewWithClickId = {R.id.btn_proceed};

    private EditText[] edTexts = {edBeneficiryName, edTransferAmount, edBenefeciaryAccNumber, benefeciaryIfsc, edReBenefeciaryAccNumber, edMobile};
    private String[] edTextsError = {"Enter benefeciary name", "Enter  transfer name", "Enter  benefeciary account number",
            "Enter  benefeciary ifsc code", "Re-nter  benefeciary account number", "Enter mobile number"};
    private int[] editTextsClickId = {R.id.banefeciary_name, R.id.transferAmount, R.id.accountnumber,
            R.id.ifsccode, R.id.reaccountnumber, R.id.mobile_num};

    public FragMtransfer() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (aiView == null) {
            aiView = inflater.inflate(R.layout.frag_mtransfer, container, false);
        }
        return aiView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        super.onCreate(savedInstanceState);
        StartApp();
        if (savedInstanceState == null && !mAlreadyLoaded) {
            mAlreadyLoaded = true;
            aiView = getView();
        }
        resumeApp();
    }

    private void resumeApp() {
        OnClickCombineDeclare(allViewWithClick);
        EditTextDeclare(edTexts);
    }

    private void ProceedWalletTransfer() {
        int response = 0;
        response = CheckValidation.emptyEditTextError(edTexts, edTextsError);

        if (response == 0) {
            if (edTransferAmount.getText().toString().trim().length() == 1 ||
                    edTransferAmount.getText().toString().trim().length() == 0) {
                response++;
                edTransferAmount.setError("Amount must be greater than 10");
            } else {
                Integer enteredAmount = Integer.parseInt(edTransferAmount.getText().toString().trim());
                if (enteredAmount > 100) {
                    response++;
                    edTransferAmount.setError("Amount must be not more than 100");
                }
            }
        }

        if(! ((edBenefeciaryAccNumber.getText().toString()).equals(edReBenefeciaryAccNumber.getText().toString()))){
            response++;
            edReBenefeciaryAccNumber.setError("Account not matching");
        }

        String strMessage = "Transfer Money\nTransfer to: " + edMobile.getText().toString().trim() +
                "\nBenefeciary name: " + edBeneficiryName.getText().toString().trim() +
                "\nBenefeciary Acccount number: " + edBenefeciaryAccNumber.getText().toString().trim() +
                "\nBenefeciary IFSC: " + benefeciaryIfsc.getText().toString().trim() +
                "\nAmount: " + edTransferAmount.getText().toString().trim();
        if (response == 0) {
            ((ActivityRecharge)getActivity()).ShowConfirmDialog(svContext, strMessage, "transfer");
        }
    }

    public void ConfirmRecharge(){
        float amount = Float.parseFloat(edTransferAmount.getText().toString().trim());
        boolean isWalletLoading = ((ActivityRecharge)getActivity()).checkEWalletAndAddWallet(amount, "mtransfer");
        if (!isWalletLoading) {
            RechargeProcess();
        }
    }

    public void RechargeProcess() {
        lstUploadData = new LinkedList<>();
        lstUploadData.add(PreferenceConnector.readString(svContext, PreferenceConnector.LOGINEDUSERID, ""));
        lstUploadData.add(edMobile.getText().toString().trim());
        lstUploadData.add(edBeneficiryName.getText().toString().trim());
        lstUploadData.add(edBenefeciaryAccNumber.getText().toString().trim());
        lstUploadData.add(benefeciaryIfsc.getText().toString().trim());
        lstUploadData.add(edTransferAmount.getText().toString().trim());
        callWebService(ApiInterface.MOBILETRANSFER, lstUploadData);
    }

    private void OnClickCombineDeclare(View[] allViewWithClick) {
        for (int j = 0; j < allViewWithClick.length; j++) {
            allViewWithClick[j] = aiView.findViewById(allViewWithClickId[j]);
            allViewWithClick[j].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.btn_proceed:
                            ProceedWalletTransfer();
                            break;
                    }
                }
            });
        }
    }

    private void EditTextDeclare(EditText[] editTexts) {
        for (int j = 0; j < editTexts.length; j++) {
            editTexts[j] = aiView.findViewById(editTextsClickId[j]);
        }
        edBeneficiryName = (EditText) editTexts[0];
        edTransferAmount = (EditText) editTexts[1];
        edBenefeciaryAccNumber = (EditText) editTexts[2];
        benefeciaryIfsc = (EditText) editTexts[3];
        edReBenefeciaryAccNumber = (EditText) editTexts[4];
        edMobile = (EditText) editTexts[5];
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
        svContext = getActivity();
        customToast = new ShowCustomToast(svContext);
        checkNetwork = new CheckInternet(svContext);
        ViewGroup root = (ViewGroup) aiView.findViewById(R.id.mylayout);
        if (!GlobalVariables.CUSTOMFONTNAME.equals("")) {
            Typeface font = Typeface.createFromAsset(getActivity().getAssets(), GlobalVariables.CUSTOMFONTNAME);
            FontUtils.setFont(root, font);
        }
        if (PreferenceConnector.readBoolean(svContext, PreferenceConnector.ISDARKTHEME, false)) {
//            FontUtils.setThemeColor(root, svContext, true);
        } else {
//            FontUtils.setThemeColor(root, svContext, false);
        }

        GlobalData.SetLanguage(svContext);
        if (checkNetwork.isConnectingToInternet()) {
            NoInternetScreen.hideError();
        } else {
            NoInternetScreen.showInternetError();
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
        System.out.println(result + ".........jsonresponse....." + url);
        if (url.contains(ApiInterface.MOBILETRANSFER)) {
            try {
                JSONObject json = new JSONObject(result);
                String str_status = json.getString("status");
                String str_msg = json.getString("message");
                if (str_status.equalsIgnoreCase("0")) {
                    customToast.showCustomToast(svContext, str_msg, customToast.ToastyError);
                } else {
                    customToast.showCustomToast(svContext, str_msg, customToast.ToastySuccess);
                }
            } catch (JSONException e) {
                customToast.showCustomToast(svContext, "Some error occured", customToast.ToastyError);
                e.printStackTrace();
            }
        }
    }

    public void switchBack() {
        hideFragmentkeyboard(svContext, aiView);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
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

}