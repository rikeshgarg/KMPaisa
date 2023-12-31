package com.commonutility;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.commonutility.retrofit.ApiClient;
import com.commonutility.retrofit.ApiInterface;
import com.codunite.rechargeapp.R;
import com.commonutility.dialogandpicker.CustomeProgressDialog;

import java.util.LinkedList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WebService {
    private Context context;
    private String postUrl;
    private String[] imagePath;
    private WebServiceListener listener;
    private CustomeProgressDialog customeProgressDialog;

    private LinkedList<String> lstUploadData = new LinkedList<>();
    private boolean isShowText = false;
    private boolean isDialogShow = true;
    private String bodyString;

    public WebService(Context context, WebServiceListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public WebService(Context context, String postUrl, LinkedList<String> lstUploadData, WebServiceListener listener) {
        this.context = context;
        this.postUrl = postUrl;
        this.lstUploadData = lstUploadData;
        this.listener = listener;
    }

    public WebService(Context context, String postUrl, LinkedList<String> lstUploadData, WebServiceListener listener, boolean isDialogShow) {
        this.context = context;
        this.postUrl = postUrl;
        this.lstUploadData = lstUploadData;
        this.listener = listener;
        this.isDialogShow = isDialogShow;
    }

    public WebService(Context context, String postUrl, LinkedList<String> lstUploadData,
                      WebServiceListener listener, String[] imagePath) {
        this.imagePath = new String[imagePath.length];
        this.context = context;
        this.postUrl = postUrl;
        this.lstUploadData = lstUploadData;
        this.imagePath = imagePath;
        this.listener = listener;
    }

    public void LoadDataRetrofit(Call<String> call) {
        if (GlobalVariables.ISTESTING) {
            for (int i = 0; i < lstUploadData.size(); i++) {
                Log.d("response???", "......" + lstUploadData.get(i) + "......." + i + "...");
            }
        }

        if (isDialogShow) {
            customeProgressDialog = new CustomeProgressDialog(context, R.layout.lay_customprogessdialog);
            TextView textView = (TextView) customeProgressDialog.findViewById(R.id.loader_showtext);
            if (isShowText) {
                textView.setVisibility(View.VISIBLE);
                textView.setText(getDialogText(postUrl));
            } else {
                textView.setVisibility(View.GONE);
            }

            customeProgressDialog.setCancelable(false);
            customeProgressDialog.show();
        }

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {

                    int code = response.code();
//                    if (response.isSuccessful()) {
                    if (code == 200 || code == 300) {
                        bodyString = response.body();

                        if (null != customeProgressDialog && customeProgressDialog.isShowing()) {
                            customeProgressDialog.dismiss();
                        }
                    } else {
                        bodyString = response.message();
                        if (null != customeProgressDialog && customeProgressDialog.isShowing()) {
                            customeProgressDialog.dismiss();
                        }
                        listener.onWebServiceError(bodyString, postUrl);
                        return;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (GlobalVariables.ISTESTING) {
                    Log.d("response???", bodyString + "......resuilt.........." + postUrl);
                }
                listener.onWebServiceActionComplete(bodyString, postUrl);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if (null != customeProgressDialog && customeProgressDialog.isShowing()) {
                    customeProgressDialog.dismiss();
                }
                if (GlobalVariables.ISTESTING)
                    Log.e(FragmentTAG.APPNAME, t.toString());
            }
        });
    }

    public void LoadImageRetrofit(Call<String> call) {
        if (GlobalVariables.ISTESTING) {
            for (int i = 0; i < lstUploadData.size(); i++) {
                System.out.println(lstUploadData.get(i) + "........." + i + "..........");
            }
        }

        if (isDialogShow) {
            customeProgressDialog = new CustomeProgressDialog(context, R.layout.lay_customprogessdialog);
            TextView textView = (TextView) customeProgressDialog.findViewById(R.id.loader_showtext);
            if (isShowText) {
                textView.setVisibility(View.VISIBLE);
                textView.setText(getDialogText(postUrl));
            } else {
                textView.setVisibility(View.GONE);
            }

            customeProgressDialog.setCancelable(false);
            customeProgressDialog.show();
        }

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    int code = response.code();
                    if (code == 200 || code == 300) {
                        bodyString = response.body().toString();
                        if (null != customeProgressDialog && customeProgressDialog.isShowing()) {
                            customeProgressDialog.dismiss();
                        }
                    } else {
                        bodyString = response.message();
                        if (null != customeProgressDialog && customeProgressDialog.isShowing()) {
                            customeProgressDialog.dismiss();
                        }
                        listener.onWebServiceError(postUrl + " " + bodyString, postUrl);
                        return;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                listener.onWebServiceActionComplete(postUrl + " " + bodyString, postUrl);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if (null != customeProgressDialog && customeProgressDialog.isShowing()) {
                    customeProgressDialog.dismiss();
                }
                listener.onWebServiceError(t.toString(), postUrl);
            }
        });
    }

    public Call<String> callReturn() {
        GlobalVariables.DEVICEID = PreferenceConnector.readString(context, PreferenceConnector.DEVICE_ID, "");
        GlobalVariables.TOKEN = PreferenceConnector.readString(context, PreferenceConnector.TOKEN, "");
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        switch (postUrl) {
            case ApiInterface.UPDATEFCM:
                return apiService.UpdateFcm(lstUploadData.get(0), lstUploadData.get(1), lstUploadData.get(2));
            case ApiInterface.LOGNUSER:
                return apiService.Login(lstUploadData.get(0), lstUploadData.get(1));
            case ApiInterface.LOGINOTPVERIFY:
                return apiService.LoginOTPVerify(lstUploadData.get(0), lstUploadData.get(1));
            case ApiInterface.SIGNUP:
                return apiService.Register(lstUploadData.get(0), lstUploadData.get(1),
                        lstUploadData.get(2), lstUploadData.get(3),
                        lstUploadData.get(4));
//            case ApiInterface.LOGNUSER:
//                return apiService.LoginAttempt(lstUploadData.get(0), lstUploadData.get(1));
            case ApiInterface.GETCOMPLAINTLIST:
                return apiService.GetComplaintList(lstUploadData.get(0));
            case ApiInterface.GETBBSPSERVICEOPERATOR:
                return apiService.GetServiceOperatorList(lstUploadData.get(0),lstUploadData.get(1));
            case ApiInterface.GETBBSPSERVICEFORM:
                return apiService.GetBBPSFORm(lstUploadData.get(0), lstUploadData.get(1), lstUploadData.get(2));
            case ApiInterface.GETBBSPSERVICEBILLFETCH:
                return apiService.getServicePayBillHistory(lstUploadData.get(0), lstUploadData.get(1), lstUploadData.get(2),
                        lstUploadData.get(3), lstUploadData.get(4), lstUploadData.get(5),
                        lstUploadData.get(6), lstUploadData.get(7),
                        lstUploadData.get(8), lstUploadData.get(9), lstUploadData.get(10));

            case ApiInterface.REGISTERTRANSFER:
                return apiService.moneyTransferRegister(lstUploadData.get(0), lstUploadData.get(1), lstUploadData.get(2),
                        lstUploadData.get(3), lstUploadData.get(4), lstUploadData.get(5), lstUploadData.get(6));
            case ApiInterface.PAYOUTAUTH:
                return apiService.payoutAuth(lstUploadData.get(0), lstUploadData.get(1), lstUploadData.get(2),
                        lstUploadData.get(3), lstUploadData.get(4), lstUploadData.get(5), lstUploadData.get(6),
                        lstUploadData.get(7), lstUploadData.get(8));


            case ApiInterface.GETPAYOUTTRANSFERREPORT:
                return apiService.getPayoutTransferHistory(lstUploadData.get(0), lstUploadData.get(1), lstUploadData.get(2));

            case ApiInterface.SEARCHPRODUCT:
                return apiService.GetSearch(lstUploadData.get(0));





            case ApiInterface.ADDWALLETRAZORPAY:
                return apiService.AddWalletCallback(lstUploadData.get(0), lstUploadData.get(1),
                        lstUploadData.get(2));
            case ApiInterface.GETFINOAEPSPIPELINE:
                return apiService.GetFinoPipeline(lstUploadData.get(0));
            case ApiInterface.NSDLPAYCARD:
                return apiService.GetNsdlPan(lstUploadData.get(0), lstUploadData.get(1),
                        lstUploadData.get(2), lstUploadData.get(3), lstUploadData.get(4), lstUploadData.get(5),
                        lstUploadData.get(6), lstUploadData.get(7));

            case ApiInterface.GETDEMOLINK:
                return apiService.GetDemoLink(lstUploadData.get(0));
            case ApiInterface.VERIFYSIGNUPOTP:
                return apiService.VerifySignupOtp(lstUploadData.get(0), lstUploadData.get(1));
            case ApiInterface.GETOTTPLANS:
                return apiService.getOttPlans(lstUploadData.get(0));
            case ApiInterface.GetFranchiseStateList:
                return apiService.getFrancStateList(lstUploadData.get(0));
            case ApiInterface.GetFranchiseDistrictList:
                return apiService.getDistrictList(lstUploadData.get(0));
            case ApiInterface.GetFranchiseBlockList:
                return apiService.getBlockList(lstUploadData.get(0));

            case ApiInterface.NEWAEPSBANKLIST:
                return apiService.getNewAepsBankList();






            case ApiInterface.ADDTRANSFERBENEFICERY:
                return apiService.addBeneficiary(lstUploadData.get(0), lstUploadData.get(1), lstUploadData.get(2),
                        lstUploadData.get(3), lstUploadData.get(4), lstUploadData.get(5), lstUploadData.get(6), lstUploadData.get(7));
            case ApiInterface.GETMONEYTRANSFERHISTORY:
                return apiService.getMoneyTransferHistory(lstUploadData.get(0), lstUploadData.get(1), lstUploadData.get(2), lstUploadData.get(3));
            case ApiInterface.GETBBPSOPERATORLIST:
                return apiService.getBbpsOperator();
            case ApiInterface.GETBBPSCIRCLELIST:
                return apiService.getBbpsCircle();
            case ApiInterface.MONEYTRANSFERAUTH:
                return apiService.moneyTransferAuth(lstUploadData.get(0), lstUploadData.get(1), lstUploadData.get(2));

            case ApiInterface.GETBBSPSERVICEBILLPAY:
                if (lstUploadData.size() > 13 && lstUploadData.get(14).equalsIgnoreCase("ott")) {
                    return apiService.ServiceBillPayAuthOTT(lstUploadData.get(0), lstUploadData.get(1), lstUploadData.get(2),
                            lstUploadData.get(3), lstUploadData.get(4), lstUploadData.get(5), lstUploadData.get(6),
                            lstUploadData.get(7), lstUploadData.get(8),
                            lstUploadData.get(9), lstUploadData.get(10), lstUploadData.get(11),
                            lstUploadData.get(12), lstUploadData.get(13), lstUploadData.get(15));
                } else {
                    return apiService.ServiceBillPayAuth(lstUploadData.get(0), lstUploadData.get(1), lstUploadData.get(2),
                            lstUploadData.get(3), lstUploadData.get(4), lstUploadData.get(5), lstUploadData.get(6),
                            lstUploadData.get(7), lstUploadData.get(8),
                            lstUploadData.get(9), lstUploadData.get(10), lstUploadData.get(11));
                }
            case ApiInterface.GETMEMBERBYMOBILE:
                return apiService.GetMemberByMobile(lstUploadData.get(0),lstUploadData.get(1));
            case ApiInterface.OPERATORLIST:
                return apiService.GetOperator(lstUploadData.get(0),lstUploadData.get(1));
            case ApiInterface.GETCOUNTRYLIST:
                return apiService.GETCOUNTRYLIST();

            case ApiInterface.GET_CONTACT_CONTENT:
                return apiService.getContactContent();
            case ApiInterface.SUBMIT_CONTACT_CONTENT:
                return apiService.submitContactUsForm(lstUploadData.get(0), lstUploadData.get(1), lstUploadData.get(2), lstUploadData.get(3));
            case ApiInterface.GET_PRIVACY_CONTENT:
                return apiService.getPrivacyContent();
            case ApiInterface.GET_WEBSITE_DISCLAIMER:
                return apiService.getStringSecond();
            case ApiInterface.GET_PRODUCT_DESIGN:
                return apiService.getStringThiord();
            case ApiInterface.GET_TERMS_AND_CONDITIONS:
                return apiService.getStringForth();

            case ApiInterface.GETBBSPFASTTAGOPERATOR:
                return apiService.getBbpsFAstagOperator(lstUploadData.get(0));
            case ApiInterface.GETBBSPFASTAGFORM:
                return apiService.FAstagBillFetchAuth(lstUploadData.get(0),lstUploadData.get(1));
            case ApiInterface.GETBBSPFASTTAGBILLFETCH:
                return apiService.getFAstagPayBillHistory(lstUploadData.get(0), lstUploadData.get(1), lstUploadData.get(2),
                        lstUploadData.get(3), lstUploadData.get(4));
            case ApiInterface.GETBBSPFASTTAGBILLPAY:
                return apiService.FAstagBillPayAuth(lstUploadData.get(0), lstUploadData.get(1), lstUploadData.get(2),
                        lstUploadData.get(3), lstUploadData.get(4), lstUploadData.get(5));
            case ApiInterface.RAISERECHGCOMPALINT:
                return apiService.RaiseComplaintAUth(lstUploadData.get(0), lstUploadData.get(1), lstUploadData.get(2));
            case ApiInterface.RAISEBBPSCOMPALINT:
                return apiService.RaiseBBPPSComplaintAUth(lstUploadData.get(0), lstUploadData.get(1), lstUploadData.get(2));
            case ApiInterface.GETBBSPSERVICELIST:
                return apiService.getBbpsServiceList(lstUploadData.get(0));
            case ApiInterface.GETBBSPELECTOPERATOR:
                return apiService.getBbpsElectricityOperator(lstUploadData.get(0));
            case ApiInterface.GETBBSPELECTFORM:
                return apiService.electricityBillFetchAuth(lstUploadData.get(0),lstUploadData.get(1));
            case ApiInterface.MAINWALLETPAYOUTREPORT:
                return apiService.getMainPayout(lstUploadData.get(0), lstUploadData.get(1), lstUploadData.get(2), lstUploadData.get(3));
            case ApiInterface.AEPSWALLETPAYOUTREPORT:
                return apiService.getAepsPayout(lstUploadData.get(0), lstUploadData.get(1), lstUploadData.get(2), lstUploadData.get(3));
            case ApiInterface.COMMISSIONWALLETPAYOUTREPORT:
                return apiService.getCommsionPayout(lstUploadData.get(0), lstUploadData.get(1), lstUploadData.get(2), lstUploadData.get(3));
            case ApiInterface.EWALLETPAYOUTREPORT:
                return apiService.getEWalletPayoutHistory(lstUploadData.get(0), lstUploadData.get(1), lstUploadData.get(2));
            case ApiInterface.MONEYTRANSFERlOGIN:
                return apiService.moneyTransferLogin(lstUploadData.get(0), lstUploadData.get(1));

            case ApiInterface.GETBENEFICIARYLIST:
                return apiService.getBeneficiaryList(lstUploadData.get(0),lstUploadData.get(1));
            case ApiInterface.BENEFICIARYOTPAUTH:
                return apiService.beneficiaryOtpAuth(lstUploadData.get(0), lstUploadData.get(1), lstUploadData.get(2), lstUploadData.get(3));



            case ApiInterface.MONEYTRANSFERREGISTEROTP:
                return apiService.moneyTransferOtpRegister(lstUploadData.get(0), lstUploadData.get(1), lstUploadData.get(2));

            case ApiInterface.GETBBSPELECBILLFETCH:
                return apiService.getPayBillHistory(lstUploadData.get(0), lstUploadData.get(1), lstUploadData.get(2),
                        lstUploadData.get(3), lstUploadData.get(4));
            case ApiInterface.GETBBSPBILLPAY:
                return apiService.electricityBillPayAuth(lstUploadData.get(0), lstUploadData.get(1), lstUploadData.get(2),
                        lstUploadData.get(3));
            case ApiInterface.GETTICKETTYPELIST:
                return apiService.GetTicketTypelist(lstUploadData.get(0));
            case ApiInterface.CIRCLELIST:
                return apiService.GetCircleList(lstUploadData.get(0));
            case ApiInterface.GetSTATELIST:
                return apiService.getStateList(lstUploadData.get(0));
            case ApiInterface.GetCITYLIST:
                return apiService.getCityList();
            case ApiInterface.GETOPERATORID:
                return apiService.GetOperatorList(lstUploadData.get(0),lstUploadData.get(1));
            case ApiInterface.GETMEMBERLIST:
                return apiService.GETMEMBERLIST(lstUploadData.get(0),lstUploadData.get(1));
            case ApiInterface.GETFUNDMEMBERLIST:
                return apiService.GETFUNDMEMBERLIST(lstUploadData.get(0),lstUploadData.get(1));

            case ApiInterface.GETRECHARGECOMMISIONLIST:
                return apiService.getRechargeCommisionList(lstUploadData.get(0));


            case ApiInterface.GETBBSPHISTORY:
                return apiService.getPayBillHistorys(lstUploadData.get(0), lstUploadData.get(1), lstUploadData.get(2), lstUploadData.get(3));
            case ApiInterface.GETBBSPLIVEHISTORY:
                return apiService.getBBPSLiveHistory(lstUploadData.get(0), lstUploadData.get(1), lstUploadData.get(2), lstUploadData.get(3));
            case ApiInterface.GETBBPSCOMMISIONLIST:
                return apiService.getBBPSCommisionList(lstUploadData.get(0));
            case ApiInterface.GETBBPSFIXCOMMISIONLIST:
                return apiService.getBBPSFixCommisionList(lstUploadData.get(0));
            case ApiInterface.GETAADHARKYCCOMMISIONLIST:
                return apiService.getAadharKycCommisionList(lstUploadData.get(0));
            case ApiInterface.GETACCOUNTVERIFYCOMMISIONLIST:
                return apiService.getAccountVerifyCommisionList(lstUploadData.get(0));
            case ApiInterface.GETUTIPANCARDCOMMISIONLIST:
                return apiService.getUTIPancardCommisionList(lstUploadData.get(0));
//            case ApiInterface.GETBBPSLIVECOMMISIONLIST:
//                return apiService.getBBPSLiveCommisionList(lstUploadData.get(0));
            case ApiInterface.GETMONEYTRANSFERCOMMISIONLIST:
                return apiService.getMoneyTransferCommisionList(lstUploadData.get(0));
            case ApiInterface.GETAEPSCOMMISIONCHARGE:
                return apiService.getAEPSCommisionCharge(lstUploadData.get(0));
            case ApiInterface.GETTICKETLIST:
                return apiService.GetTicketKList(lstUploadData.get(0));
            case ApiInterface.SUBMITCOMPALINT:
                return apiService.SubMitComplain(lstUploadData.get(0), lstUploadData.get(1), lstUploadData.get(2),
                        lstUploadData.get(3), lstUploadData.get(4));
            case ApiInterface.RECHARGEAUTH:
                return apiService.RECHARGEAUTH(lstUploadData.get(0), lstUploadData.get(1),
                        lstUploadData.get(2), lstUploadData.get(3),lstUploadData.get(4));
            case ApiInterface.GETUSERNAME:
                return apiService.GetUsername(lstUploadData.get(0));
            case ApiInterface.REQUESTAMOUNT:
                return apiService.RequestWalletBalance(lstUploadData.get(0), lstUploadData.get(1),
                        lstUploadData.get(2), lstUploadData.get(3), lstUploadData.get(4), lstUploadData.get(5));

            case ApiInterface.ELECTRICRECHARGEAUTH:
                return apiService.ElectricRechargeAuth(lstUploadData.get(0), lstUploadData.get(1),
                        lstUploadData.get(2), lstUploadData.get(3),
                        lstUploadData.get(4), lstUploadData.get(5));
            case ApiInterface.ADDMEMBERAUTH:
                return apiService.ADDMEMBERAUTH(lstUploadData.get(0), lstUploadData.get(1), lstUploadData.get(2),
                        lstUploadData.get(3), lstUploadData.get(4), lstUploadData.get(5), lstUploadData.get(6),
                        lstUploadData.get(7), lstUploadData.get(8), lstUploadData.get(9));
            case ApiInterface.FORGOTPWDAUTH:
                return apiService.AuthForgotPwd(lstUploadData.get(0));
            case ApiInterface.FORGOTPWDOTPVERIFY:
                return apiService.ForgotOtpverify(lstUploadData.get(0));
            case ApiInterface.FORGOTUPDATEPWD:
                return apiService.ForgotUpdatePwd(lstUploadData.get(0), lstUploadData.get(1), lstUploadData.get(2));
            case ApiInterface.CHANGEPASSWORD:
                return apiService.ChangePassword(lstUploadData.get(0), lstUploadData.get(1), lstUploadData.get(2),
                        lstUploadData.get(3));
            case ApiInterface.CHANGETRANCATIONPASSWORD:
                return apiService.ChangeTranscationPassword(lstUploadData.get(0), lstUploadData.get(1), lstUploadData.get(2),
                        lstUploadData.get(3));
            case ApiInterface.UPADATEUSERDATA:
                return apiService.UpdateUserData(lstUploadData.get(0), lstUploadData.get(1), lstUploadData.get(2));

            case ApiInterface.RECHARGEOTPAUTH:
                return apiService.RechargeOtpConfirm(lstUploadData.get(0), lstUploadData.get(1));
            case ApiInterface.WALLETHISTORY:
                return apiService.GetWalletHistory(lstUploadData.get(0), lstUploadData.get(1), lstUploadData.get(2), lstUploadData.get(3));
            case ApiInterface.EWALLETHISTORY:
                return apiService.GetEWalletHistory(lstUploadData.get(0), lstUploadData.get(1), lstUploadData.get(2), lstUploadData.get(3));
            case ApiInterface.REQUESTHISTORY:
                return apiService.GetRequestHistory(lstUploadData.get(0));
            case ApiInterface.E_REQUESTHISTORY:
                return apiService.GetERequestHistory(lstUploadData.get(0));
            case ApiInterface.GETPOINTWALLETHISTORY:
                return apiService.GetPointWalletHistory(lstUploadData.get(0), lstUploadData.get(1), lstUploadData.get(2), lstUploadData.get(3));

            case ApiInterface.GETCOMMISIONWALLETHISTORY:
                return apiService.GetCommissionWalletHistory(lstUploadData.get(0), lstUploadData.get(1), lstUploadData.get(2), lstUploadData.get(3));
            case ApiInterface.GETAEPSWALLETHISTORY:
                return apiService.GetAepsWalletHistory(lstUploadData.get(0), lstUploadData.get(1), lstUploadData.get(2), lstUploadData.get(3));


            case ApiInterface.WALLETTRANSFER:
                return apiService.WalletTransfer(lstUploadData.get(0), lstUploadData.get(1), lstUploadData.get(2),
                        lstUploadData.get(3), lstUploadData.get(4));
            case ApiInterface.ADDWALLET:
                return apiService.AddWallet(lstUploadData.get(0), lstUploadData.get(1));
            case ApiInterface.WALLETOTPAUTH:
                return apiService.WalletTransferOtpAuth(lstUploadData.get(0), lstUploadData.get(1));
            case ApiInterface.MTRANSFERHISTORY:
                return apiService.MtransferHistory(lstUploadData.get(0),lstUploadData.get(1),lstUploadData.get(2),lstUploadData.get(3));
            case ApiInterface.MOBILETRANSFER:
                return apiService.TransferMwalletAmount(lstUploadData.get(0), lstUploadData.get(1), lstUploadData.get(2),
                        lstUploadData.get(3),lstUploadData.get(4), lstUploadData.get(5));
            case ApiInterface.MOBILETRANSFERVERIFY:
                return apiService.VerifyMwalletAmount(lstUploadData.get(0), lstUploadData.get(1), lstUploadData.get(2));
            case ApiInterface.UPGRADE:
                return apiService.UpgradeAccount(lstUploadData.get(0), lstUploadData.get(1), lstUploadData.get(2), lstUploadData.get(3), lstUploadData.get(4));
            case ApiInterface.GETDIRECTDOWNLINE:
                return apiService.GetDirectDownLine(lstUploadData.get(0));
            case ApiInterface.GETTOTALDOWNLINE:
                return apiService.GetTotalDownLine(lstUploadData.get(0));
            case ApiInterface.GETPACKAGELIST:
                return apiService.GetPackageList();
            case ApiInterface.GETTOTALACTIVEDOWNLINE:
                return apiService.GetTotalActiveDownLine(lstUploadData.get(0));
            case ApiInterface.GETTOTALDEACTIVEDOWNLINE:
                return apiService.GetTotalDeactiveDownLine(lstUploadData.get(0));
            case ApiInterface.GETDIRECTACTIVEDOWNLINE:
                return apiService.GetDirectActiveDownLine(lstUploadData.get(0));
            case ApiInterface.GETDIRECTDEACTIVEDOWNLINE:
                return apiService.GetDirectDeActiveDownLine(lstUploadData.get(0));
            case ApiInterface.GETDIRECTINCOME:
                return apiService.GetDirectIncome(lstUploadData.get(0));
            case ApiInterface.GETMATCHINGBINARY:
                return apiService.GetMatchingBinary(lstUploadData.get(0));
            case ApiInterface.GETDASHBOARD:
                return apiService.GetDashBoard();
            case ApiInterface.PRODUCTLIST:
                return apiService.GetProductList(lstUploadData.get(0));
            case ApiInterface.PRODUCTDETAIL:
                return apiService.GetProductDetail(lstUploadData.get(0));
            case ApiInterface.GETSUBCATEGORY:
                return apiService.GetSUBCATEGORY(lstUploadData.get(0));
            case ApiInterface.RECHARGEHISTORY:
                return apiService.GetRechargeHistory(lstUploadData.get(0), lstUploadData.get(1), lstUploadData.get(2), lstUploadData.get(3));
            case ApiInterface.RECHARGECOMMISIONREPORTLIST:
                return apiService.GetRechargeCommissionReportList(lstUploadData.get(0), lstUploadData.get(1), lstUploadData.get(2), lstUploadData.get(3));
            case ApiInterface.AEPSCOMMISIONREPORTLIST:
                return apiService.GetAEPSCommissionReportList(lstUploadData.get(0), lstUploadData.get(1), lstUploadData.get(2), lstUploadData.get(3));
            case ApiInterface.FUNDTRANSFERCOMMISIONREPORTLIST:
                return apiService.GetFundTransCommissionReportList(lstUploadData.get(0), lstUploadData.get(1), lstUploadData.get(2), lstUploadData.get(3));
            case ApiInterface.RECHARGECOMMISIONHISTORY:
                return apiService.GetRechargeCommisionHistory(lstUploadData.get(0));
            case ApiInterface.GETCREDITCARDHISTORY:
                return apiService.GetCreditCardHistory(lstUploadData.get(0));
            case ApiInterface.GETLOAN:
                return apiService.GetLoan(lstUploadData.get(0), lstUploadData.get(1));
            case ApiInterface.GETLOANINSTALLMENT:
                return apiService.GetLoanInstallment(lstUploadData.get(0), lstUploadData.get(1));
            case ApiInterface.EMIPAYAUTH:
                return apiService.PayEmi(lstUploadData.get(0), lstUploadData.get(1),
                        lstUploadData.get(2));
            case ApiInterface.ROFFERS:
                return apiService.GetROffers(lstUploadData.get(0), lstUploadData.get(1),lstUploadData.get(2));
            case ApiInterface.RDTHOFFERS:
                return apiService.GetRDthOffers(lstUploadData.get(0), lstUploadData.get(1),lstUploadData.get(2));
            case ApiInterface.VIEWALLPLANS:
                return apiService.GetAllPlans(lstUploadData.get(0), lstUploadData.get(1), lstUploadData.get(2));
            case ApiInterface.VIEWALLDTHPLANS:
                return apiService.GetAllDthPlans(lstUploadData.get(0), lstUploadData.get(1),lstUploadData.get(2));
            case ApiInterface.GETCCTRANSFERHISTORY:
                return apiService.CCTransferHistory(lstUploadData.get(0));
            case ApiInterface.USERKYC:
                return apiService.UserKYC(lstUploadData.get(0), lstUploadData.get(1),
                        lstUploadData.get(2), lstUploadData.get(3),
                        lstUploadData.get(4), lstUploadData.get(5),
                        lstUploadData.get(6), lstUploadData.get(7),
                        lstUploadData.get(8), lstUploadData.get(9), lstUploadData.get(10));
            case ApiInterface.USERKYCDETAIL:
                return apiService.USERKYCDETAIL(lstUploadData.get(0));


            case ApiInterface.GET_NOMINEE_DATA:
                return apiService.getNomineeData(lstUploadData.get(0));
            case ApiInterface.GET_AVAILABLE_PIN:
                return apiService.getAvailablePin(lstUploadData.get(0), lstUploadData.get(1));
            case ApiInterface.GET_RANK_LIST:
                return apiService.getRankList(lstUploadData.get(0));
            case ApiInterface.GET_LEVEL_LIST:
                return apiService.getLevelList(lstUploadData.get(0));
            case ApiInterface.GET_RANKWISE_TEAMLIST:
                return apiService.getRankWiseTeamList(lstUploadData.get(0), lstUploadData.get(1), lstUploadData.get(2));
            case ApiInterface.GET_LEVELWISE_TEAMLIST:
                return apiService.getLevelWiseTeamList(lstUploadData.get(0), lstUploadData.get(1), lstUploadData.get(2));
            case ApiInterface.TRANSFER_PIN_AUTH:
                return apiService.transferPinAuth(lstUploadData.get(0), lstUploadData.get(1), lstUploadData.get(2), lstUploadData.get(3));
            case ApiInterface.AEPSSTATELIST:
                return apiService.getAepsState();
            case ApiInterface.AEPSBANKLIST:
                return apiService.getAllbankName();
            case ApiInterface.AEPSCITYLIST:
                return apiService.getAepsCity(lstUploadData.get(0));
            case ApiInterface.AEPSOTOAUTH:
                return apiService.getAepOtp(lstUploadData.get(0), lstUploadData.get(1), lstUploadData.get(2));
            case ApiInterface.AEPSRESENDOTP:
                return apiService.getAepsResendOtp(lstUploadData.get(0), lstUploadData.get(1));
            case ApiInterface.AEPSUPLOADKYC:
                return apiService.getAepsUpload(lstUploadData.get(0), lstUploadData.get(1), lstUploadData.get(2));
            case ApiInterface.AEPSACTIVEAUTH:
                return apiService.getAepsActiveAuth(lstUploadData.get(0), lstUploadData.get(1),
                        lstUploadData.get(2), lstUploadData.get(3), lstUploadData.get(4), lstUploadData.get(5),
                        lstUploadData.get(6), lstUploadData.get(7), lstUploadData.get(8), lstUploadData.get(9),
                        lstUploadData.get(10), lstUploadData.get(11), lstUploadData.get(12));
            case ApiInterface.AEPSHISTORY:
                return apiService.GetAepsHistory(lstUploadData.get(0), lstUploadData.get(1), lstUploadData.get(2), lstUploadData.get(3));

            case ApiInterface.AEPSICICOTOAUTH:
                return apiService.getIcicAepOtp(lstUploadData.get(0), lstUploadData.get(1), lstUploadData.get(2));
            case ApiInterface.AEPSICICRESENDOTP:
                return apiService.getIcicAepsResendOtp(lstUploadData.get(0), lstUploadData.get(1));
            case ApiInterface.AEPSICICUPLOADKYC:
                return apiService.getIcicAepsUpload(lstUploadData.get(0), lstUploadData.get(1), lstUploadData.get(2));
            case ApiInterface.AEPSICICACTIVEAUTH:
                return apiService.getIcicAepsActiveAuth(lstUploadData.get(0), lstUploadData.get(1),
                        lstUploadData.get(2), lstUploadData.get(3), lstUploadData.get(4), lstUploadData.get(5),
                        lstUploadData.get(6), lstUploadData.get(7), lstUploadData.get(8), lstUploadData.get(9),
                        lstUploadData.get(10), lstUploadData.get(11), lstUploadData.get(12));



            case ApiInterface.NEW_AEPSACTIVEAUTH:
                return apiService.newAepsActiveAuth(lstUploadData.get(0), lstUploadData.get(1),
                        lstUploadData.get(2), lstUploadData.get(3), lstUploadData.get(4), lstUploadData.get(5),
                        lstUploadData.get(6), lstUploadData.get(7), lstUploadData.get(8), lstUploadData.get(9),
                        lstUploadData.get(10), lstUploadData.get(11), lstUploadData.get(12));





            case ApiInterface.GET_FUNDREQUEST_MANUUAL_QR:
                return apiService.getFundRequestManualQr();


            case ApiInterface.GETTENURELIST:
                return apiService.getTenureList();
            case ApiInterface.GETLOANCHARGES:
                return apiService.getLoanCharges(lstUploadData.get(0), lstUploadData.get(1));

            case ApiInterface.USERACTIVATEUPI:
                return apiService.ActivateUpi(lstUploadData.get(0), lstUploadData.get(1), lstUploadData.get(2));




            case ApiInterface.GET_DOCUMENT:
                return apiService.getDocument(lstUploadData.get(0));
            case ApiInterface.GET_DOCUMENT_CATEGORY:
                return apiService.getDocumentCategory();



            case ApiInterface.GET_AFFILIATE_LIST:
                return apiService.getAffiliateList();
            case ApiInterface.CATEGORYID:
                return apiService.GetcategoryId();
            case ApiInterface.PRODUCTSECTIONLIST:
                return apiService.Getproductlist();



            case ApiInterface.GETZOOMDETAILS:
                return apiService.getZoomDetails();
            case ApiInterface.GET_NOTIFICATION_LIST:
                return apiService.notificationList(lstUploadData.get(0));

            case ApiInterface.GET_VENDOR_DETAILS:
                return apiService.getVendorRequestDetails(lstUploadData.get(0));
            case ApiInterface.VENDOR_REGISTER_AUTH:
                return apiService.vendorRegisterAuth(lstUploadData.get(0), lstUploadData.get(1), lstUploadData.get(2), lstUploadData.get(3), lstUploadData.get(4),
                        lstUploadData.get(5), lstUploadData.get(6), lstUploadData.get(7), lstUploadData.get(8), lstUploadData.get(9),
                        lstUploadData.get(10), lstUploadData.get(11), lstUploadData.get(12), lstUploadData.get(13));
            case ApiInterface.GENERATE_VENDOR_BILL_AUTH:
                return apiService.generateVendorBillAuth(lstUploadData.get(0), lstUploadData.get(1), lstUploadData.get(2));
            case ApiInterface.VENDOR_BILLER_LIST:
                return apiService.vendorBillList(lstUploadData.get(0));
            case ApiInterface.SEARCH_STORE_AUTH:
                return apiService.searchStoreAuth(lstUploadData.get(0), lstUploadData.get(1), lstUploadData.get(2), lstUploadData.get(3));
            case ApiInterface.SEARCH_DOWNLINE:
                return apiService.searchDownline(lstUploadData.get(0), lstUploadData.get(1));
            case ApiInterface.GETVENDOR_PACKAGELIST:
                return apiService.getVendorPackageList(lstUploadData.get(0));
            case ApiInterface.VENDOR_PACKAGE_PURCHASEAUTH:
                return apiService.vendorPackagePurchaseAuth(lstUploadData.get(0), lstUploadData.get(1));
            case ApiInterface.UPLOAD_VENDOR_BANNER:
                return apiService.uploadVendorBanner(lstUploadData.get(0), lstUploadData.get(1));
            case ApiInterface.DELETE_VENDOR_BANNER_AUTH:
                return apiService.deleteVendorBannerAuth(lstUploadData.get(0), lstUploadData.get(1));
            case ApiInterface.GET_VENDOR_BANNERLIST:
                return apiService.getVendorBannerList(lstUploadData.get(0));
            case ApiInterface.GET_AREA_BANNERLIST:
                return apiService.getAreaBannerList(lstUploadData.get(0));

            case ApiInterface.ORDER_TOKEN_AUTH:
                return apiService.OrderTokenAuth(lstUploadData.get(0), lstUploadData.get(1), lstUploadData.get(2));
            case ApiInterface.ADD_TOPUP_WALLET:
                return apiService.PaymentReceiveFreecash(lstUploadData.get(0), lstUploadData.get(1), lstUploadData.get(2), lstUploadData.get(3));
            case ApiInterface.VIRTUAL_ACCOUNT_AUTH:
                return apiService.virtualAccountAuth(lstUploadData.get(0));
            case ApiInterface.VIRTUAL_ACTIVE_AUTH:
                return apiService.activeVirtualAuth(lstUploadData.get(0));
            case ApiInterface.VIRTUAL_ACCOUNT_REPORT:
                return apiService.virtualAccountList(lstUploadData.get(0), lstUploadData.get(1), lstUploadData.get(2), lstUploadData.get(3));
            case ApiInterface.DMT_BANK_LIST:
                return apiService.dmtBankList(lstUploadData.get(0));
            case ApiInterface.GET_SENDER_DETAILS:
                return apiService.getSenderDetail(lstUploadData.get(0), lstUploadData.get(1));
            case ApiInterface.DELETE_DMT_BENEFICERY:
                return apiService.deleteDmtBen(lstUploadData.get(0), lstUploadData.get(1), lstUploadData.get(2));
            case ApiInterface.UPDATE_SENDER_DETAILS:
                return apiService.updateSenderDetailAuth(lstUploadData.get(0), lstUploadData.get(1), lstUploadData.get(2),
                        lstUploadData.get(3), lstUploadData.get(4), lstUploadData.get(5), lstUploadData.get(6));
            case ApiInterface.RECENTRECHARGEHISTORY:
                return apiService.getRecentRechargeHistory(lstUploadData.get(0), lstUploadData.get(1), lstUploadData.get(2));
                default:
                new ShowCustomToast(context).showToast("Please decalre Url in webservice file", context);
                break;
        }
        return null;
    }

    public String getDialogText(String strPostUrl) {
        for (int i = 0; i < (ApiInterface.strUrlName).length; i++) {
            if ((ApiInterface.strUrlName)[i].equals(strPostUrl)) {
                return (ApiInterface.strUrlText)[i];
            }
        }
        return "";
    }
}
