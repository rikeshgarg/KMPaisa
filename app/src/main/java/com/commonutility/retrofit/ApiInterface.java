package com.commonutility.retrofit;

import com.google.gson.JsonObject;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiInterface {
    String[] strUrlName = {"", "", ""};
    String[] strUrlText = {"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
            "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""};

    String NSDLPAYCARD = "nsdlPanAuth";
    String UPDATEFCM = "userDetail";

    String LOGNUSER = "loginAuth"; //ok
    String LOGINOTPVERIFY = "loginOtpAuth"; //ok

    String LOGIN = "userAuth"; //ok

    String SIGNUP = "registerAuth"; //unused
    String OPERATORLIST = "getOperatorList"; //ok
    String CIRCLELIST = "getCircleList"; //ok
    String FORGOTPWDAUTH = "forgotAuth";
    String FORGOTPWDOTPVERIFY = "forgotOTPAuth";
    String FORGOTUPDATEPWD = "updatePasswordAuth";
    String CHANGEPASSWORD = "changePassword";
    String CHANGETRANCATIONPASSWORD = "changeTransactionPassword";
    String GETPACKAGELIST = "getPackage";//unused
    String GETDIRECTDOWNLINE = "getUserDirectDownline";
    String GETTOTALDOWNLINE = "getUserTotalDownline";
    String RECHARGEOTPAUTH = "rechargeOTPAuth";//unused
    String GETDIRECTACTIVEDOWNLINE = "getUserDirectActiveDownline";
    String GETDIRECTDEACTIVEDOWNLINE = "getUserDirectDeactiveDownline";
    String GETTOTALACTIVEDOWNLINE = "getUserTotalActiveDownline";
    String GETTOTALDEACTIVEDOWNLINE = "getUserTotalDeactiveDownline";
    String GETDIRECTINCOME = "getDirectIncome";



    String GETMATCHINGBINARY = "getMatchingBinaryIncome";//unused
    String WALLETHISTORY = "getWalletList";
    String EWALLETHISTORY = "getEWalletList";
    String REQUESTHISTORY = "getRequestHistory";

    String E_REQUESTHISTORY = "getEwalletRequestHistory";
    String RECHARGEHISTORY = "getRechargeHistory";
    String RECENTRECHARGEHISTORY = "getRecentRechargeHistory";
    String RECHARGECOMMISIONHISTORY = "getRechargeCommisionHistory";

    String RECHARGECOMMISIONREPORTLIST = "getRechargeCommissionReportList";

    String AEPSCOMMISIONREPORTLIST = "getAepsCommissionReportList";

    String FUNDTRANSFERCOMMISIONREPORTLIST = "getFundTransferCommissionReportList";

    //

    String GETELECTRICITYBILLERDETAIL = "getElectricityBillerDetail";//unused
    String ADDWALLET = "addWallet";//unused
    String WALLETTRANSFER = "walletAuth";//unused
    String WALLETOTPAUTH = "walletsTransferOtpAuth";//unused
    String MTRANSFERHISTORY = "getFundTransferList";
    String MOBILETRANSFER = "fundTransferAuth";//unused
    String MOBILETRANSFERVERIFY = "transferOTPAuth";//unused
    String UPGRADE = "upgrade";//unused
    String GETDASHBOARD = "dashboardAuth";//unused

    String GETCOUNTRYLIST = "getCountryList";//unused
    String GetSTATELIST = "getStateList";
    String GetCITYLIST = "getCityList";
    String GETMEMBERLIST = "getMemberList";//unused

    String GETFUNDMEMBERLIST="getFundMemberList";
    String RECHARGEAUTH = "rechargeAuth";//unused
    String ELECTRICRECHARGEAUTH = "electricityAuth";
    String UPADATEUSERDATA = "updateuserdata";
    String SUBMITCOMPALINT = "ticketAuth";
    String GETTICKETLIST = "getTicketList";
    String GETLOAN = "getLoan";
    String GETLOANINSTALLMENT = "getLoanInstallment";
    String EMIPAYAUTH = "emiPayAuth";
    String ADDMEMBERAUTH = "addMemberAuth";
    String GETCREDITCARDHISTORY = "getCreditCardHistory";
    String SENDSMS = "";
    String GETCCTRANSFERHISTORY = "getCreditTransferHistory";
    String USERKYC = "kycAuth";
    String USERKYCDETAIL = "getUserKycDetails";


    String GETTICKETTYPELIST = "getTicketTypeList";
    String REQUESTAMOUNT = "requestwalletamount";

    String VIRTUAL_ACCOUNT_AUTH = "virtualAccountAuth";
    String VIRTUAL_ACTIVE_AUTH = "activeVirtualAuth";

    String VIRTUAL_ACCOUNT_REPORT = "virtualAccountList";

    String GET_CONTACT_CONTENT = "getContactContent";
    String SUBMIT_CONTACT_CONTENT = "submitContactUsForm";
    String GET_PRIVACY_CONTENT = "getPrivacyContent";
    String GET_WEBSITE_DISCLAIMER = "getWebsiteDisclaimer";
    String GET_PRODUCT_DESIGN = "getProductDesign";
    String GET_TERMS_AND_CONDITIONS = "getTermsAndConditions";

    String ROFFERS = "getROfferList";
    String RDTHOFFERS = "getDTHROfferList";
    String VIEWALLPLANS = "getPlanList";
    String VIEWALLDTHPLANS = "getDTHPlanList";
    String GETOPERATORID = "getOperatorId";
    //String GETRECHARGECOMMISIONLIST = "getRechargeCommission";
    String GETRECHARGECOMMISIONLIST = "getRechargeCommisionList";
    String GETBBPSCOMMISIONLIST = "getBBPSCommisionList";
    //String GETBBPSLIVECOMMISIONLIST = "getBbpsCommission";

    String GETBBPSFIXCOMMISIONLIST = "getBbpsCommission";

    String GETAADHARKYCCOMMISIONLIST = "getAdharEKycCharge";

    String GETACCOUNTVERIFYCOMMISIONLIST = "getAccountVerifyCharge";

    String GETUTIPANCARDCOMMISIONLIST = "getUtiPancardCharge";

    String GETMONEYTRANSFERCOMMISIONLIST = "getMoneyTransferCommisionList";
    String GETAEPSCOMMISIONCHARGE = "getAepsCommissionCharge";
    String GETBBSPHISTORY = "getBBSPHistory";
    String GETBBSPLIVEHISTORY = "getBBPSLiveHistory";

    String GETBBSPELECTOPERATOR = "getBbpsElectricityOperator";
    String GETBBSPELECTFORM = "getBbpsElectricityFormParams";//?biller_id={biller_id}
    String GETBBSPELECBILLFETCH = "electricityBillFetchAuth";
    String GETBBSPBILLPAY = "electricityBillPayAuth";
    String GETBBSPFASTTAGOPERATOR = "getBbpsFastagOperator";
    String GETBBSPFASTAGFORM = "getBbpsFastagFormParams";//?biller_id={biller_id}
    String GETBBSPFASTTAGBILLFETCH = "fastagBillFetchAuth";
    String GETBBSPFASTTAGBILLPAY = "fastagBillPayAuth";
    String RAISERECHGCOMPALINT = "complainAuth";
    String RAISEBBPSCOMPALINT = "bbpsComplainAuth";
    String GETCOMPLAINTLIST = "getComplainHistory";
    String GETMEMBERBYMOBILE = "getMemberByMobile";
    String GETBBSPSERVICELIST = "getBbpsServiceList";
    String GETBBSPSERVICEOPERATOR = "getServiceOperator";
    String GETBBSPSERVICEFORM = "getServiceFormParams";
    String GETBBSPSERVICEBILLFETCH = "serviceBillFetchAuth";
    String GETBBSPSERVICEBILLPAY = "serviceBillPayAuth";
    String REGISTERTRANSFER = "moneyTransferRegister";
    String MONEYTRANSFERREGISTEROTP = "moneyTransferOtpRegister";
    String PAYOUTAUTH = "payoutAuth";
    String GETPAYOUTTRANSFERREPORT = "fundTransferHistory";
    String MONEYTRANSFERlOGIN = "moneyTransferLogin";
    String MONEYTRANSFERAUTH = "moneyTransferAuth";

    String GETMONEYTRANSFERHISTORY = "getMoneyTransferHistory";
    String GETBBPSOPERATORLIST = "getBbpsOperatorList";
    String GETBBPSCIRCLELIST = "getBbpsCircleList";

    // Add Fund Apis
    String GET_FUNDREQUEST_MANUUAL_QR = "getManualQr";



    // Common AEPS Apis
    String AEPSBANKLIST = "getAepsBankList";
    String AEPSSTATELIST = "getAepsStateList";
    String AEPSCITYLIST = "getAepsCityList";

    // Main AEPS Apis
    String AEPSACTIVEAUTH = "aepsActiveAuth";
    String AEPSOTOAUTH = "aepsOtpAuth";
    String AEPSRESENDOTP = "aepsResendOtpAuth";
    String AEPSUPLOADKYC = "aepsKycBioAuth";
    String AEPSHISTORY = "getAepsHistory";
    String AEPSAPIAUTH = "aepsApiAuth";

    // ICIC AEPS Apis
    String AEPSICICACTIVEAUTH = "iciciAepsActiveAuth";
    String AEPSICICOTOAUTH = "iciciAepsOtpAuth";
    String AEPSICICRESENDOTP = "iciciAepsResendOtpAuth";
    String AEPSICICUPLOADKYC = "iciciAepsKycBioAuth";

    String AEPSICICAPIAUTH = "iciciAepsApiAuth";

    // New AEPS Service Apis

    String NEW_AEPSACTIVEAUTH = "newAepsActiveAuth";
    String NEW_AEPSAPIAUTH = "newAepsApiAuth";



    String EWALLETPAYOUTREPORT = "getEwalletBankTransferHistory";



    String REQUESTTOKENAUTH = "requestPinAuth";

    String GETTENURELIST = "getTenureList";
    String APPLYLOANAUTH = "applyLoanAuth";
    String GETLOANCHARGES = "getLoanCharges";
    String USERACTIVATEUPI = "activateUpi";



    String ADDTRANSFERBENEFICERY = "addBeneficiary";
    String GETBENEFICIARYLIST = "getBeneficiaryList";
    String BENEFICIARYOTPAUTH = "beneficiaryOtpAuth";
    String GETAEPSWALLETHISTORY = "getAepsWalletList";
    String GETCOMMISIONWALLETHISTORY = "getCommissionWalletList";
    String GETPOINTWALLETHISTORY = "getPointWalletList";


    String GET_DOCUMENT = "getDocument";
    String GET_DOCUMENT_CATEGORY = "documentCategory";

    String PRODUCTLIST = "getProductList";
    String SEARCHPRODUCT = "searchProduct";
    String PRODUCTDETAIL = "getProductDetail";
    String CATEGORYID = "getCategoryList";
    String PRODUCTSECTIONLIST = "productSectionList";
    String GETSUBCATEGORY = "getSubCategoryList";



    String MAINWALLETPAYOUTREPORT = "mainWalletfundTransferHistory";
    String AEPSWALLETPAYOUTREPORT = "aepsWalletfundTransferHistory";
    String COMMISSIONWALLETPAYOUTREPORT = "commissionWalletfundTransferHistory";




    String GET_AFFILIATE_LIST = "affiliateList"; //unused

    String GET_NOMINEE_DATA = "getUserNomineeDetails";
    String GETZOOMDETAILS = "zoom_detail";
    String GET_NOTIFICATION_LIST = "notificationList";

    String GET_VENDOR_DETAILS = "getVendorRequestDetails";
    String VENDOR_REGISTER_AUTH = "vendorRegisterAuth";
    String GENERATE_VENDOR_BILL_AUTH = "generateVendorBillAuth";
    String VENDOR_BILLER_LIST = "vendorBillList";
    String SEARCH_STORE_AUTH = "searchStoreAuth";
    String SEARCH_DOWNLINE = "searchDownlineUser";
    String GETVENDOR_PACKAGELIST = "getVendorPackageList";
    String VENDOR_PACKAGE_PURCHASEAUTH = "vendorPackagePurchaseAuth";
    String UPLOAD_VENDOR_BANNER = "uploadVendorBanner";
    String DELETE_VENDOR_BANNER_AUTH = "deleteVendorBannerAuth";
    String GET_VENDOR_BANNERLIST = "getVendorBannerList";
    String GET_AREA_BANNERLIST = "getAreaBannerList"; //unused

    // New AEPS Payout Apis
    String NEWAEPSBANKLIST = "getNewAepsPayoutBankList";


    String GETOTTPLANS = "getOttPlan";
    String GetFranchiseStateList = "getFranchiseStateList";
    String GetFranchiseDistrictList = "getFranchiseDistrictList";
    String GetFranchiseBlockList = "getFranchiseBlockList";
    String VERIFYSIGNUPOTP = "registerOtpVerifyAuth";
    String GETUSERNAME = "getUserName";
    String GETDEMOLINK = "getDemoLink";

    String ADDWALLETRAZORPAY = "paymentCallback";


    String GET_SENDER_DETAILS = "getSenderDetail";
    String UPDATE_SENDER_DETAILS = "updateSenderDetailAuth";
    String DELETE_DMT_BENEFICERY = "deleteDmtBen";
    String DMT_BANK_LIST = "dmtBankList";

    @FormUrlEncoded
    @POST(ADDWALLETRAZORPAY)
    Call<String> AddWalletCallback(@Field("user_id") String user_id,
                                   @Field("amount") String amount,
                                   @Field("razorpay_response_id") String razorpay_response_id);

    String GETFINOAEPSPIPELINE = "checkFinoPipeStatus";
    @FormUrlEncoded
    @POST(GETFINOAEPSPIPELINE)
    Call<String> GetFinoPipeline(@Field("user_id") String service_id);

    @FormUrlEncoded
    @POST(NSDLPAYCARD)
    Call<String> GetNsdlPan(@Field("user_id") String user_id,
                            @Field("gender") String gender,
                            @Field("first_name") String first_name,
                            @Field("email") String email,
                            @Field("middle_name") String middle_name,
                            @Field("last_name") String last_name,
                            @Field("title") String title,
                            @Field("mode") String mode);


    @FormUrlEncoded
    @POST(GETDEMOLINK)
    Call<String> GetDemoLink(@Field("service_id") String service_id);

    @FormUrlEncoded
    @POST(VERIFYSIGNUPOTP)
    Call<String> VerifySignupOtp(@Field("otp_code") String otp_code,
                                 @Field("encrypt_otp_code") String encrypt_otp_code);

    @FormUrlEncoded
    @POST(GetFranchiseStateList)
    Call<String> getFrancStateList(@Field("operator_id") String operator_id);

    @FormUrlEncoded
    @POST(GetFranchiseDistrictList)
    Call<String> getDistrictList(@Field("state_id") String operator_id);

    @FormUrlEncoded
    @POST(GetFranchiseBlockList)
    Call<String> getBlockList(@Field("district_id") String operator_id);


    @FormUrlEncoded
    @POST(GETOTTPLANS)
    Call<String> getOttPlans(@Field("operator_id") String operator_id);



    @GET(NEWAEPSBANKLIST)
    Call<String> getNewAepsBankList();











    @GET(GET_AFFILIATE_LIST)
    Call<String> getAffiliateList();

    @GET(CATEGORYID)
    Call<String> GetcategoryId();

    @GET(PRODUCTSECTIONLIST)
    Call<String> Getproductlist();

    @FormUrlEncoded
    @POST(SEARCHPRODUCT)
    Call<String> GetSearch(@Field("keyword") String prdkey);











    @FormUrlEncoded
    @POST(GET_DOCUMENT)
    Call<String> getDocument(@Field("cat_id") String cat_id);

    @POST(GET_DOCUMENT_CATEGORY)
    Call<String> getDocumentCategory();



    @FormUrlEncoded
    @POST(PAYOUTAUTH)
    Call<String> payoutAuth(@Field("userID") String user_id,
                            @Field("mobile") String mobile,
                            @Field("account_holder_name") String accholdername,
                            @Field("account_no") String accno,
                            @Field("confirm_account_no") String confirmaccno,
                            @Field("ifsc") String ifsc,
                            @Field("amount") String amount,
                            @Field("bankID") String bankID,
                            @Field("txnType") String txnType);








    @FormUrlEncoded
    @POST(USERACTIVATEUPI)
    Call<String> ActivateUpi(@Field("user_id") String userId,
                             @Field("name") String password,
                             @Field("mobile") String cpassword);







    @GET(GET_FUNDREQUEST_MANUUAL_QR)
    Call<String> getFundRequestManualQr();





    @FormUrlEncoded
    @POST(AEPSAPIAUTH)
    Call<JsonObject> AddAepsRequest(@Field("user_data") String user_data);

    @POST(AEPSBANKLIST)
    Call<JsonObject> getAllbankNamedetails();

    @POST(AEPSBANKLIST)
    Call<String> getAllbankName();

    @GET(AEPSSTATELIST)
    Call<String> getAepsState();

    @FormUrlEncoded
    @POST(AEPSCITYLIST)
    Call<String> getAepsCity(@Field("state_id") String state_id);

    @FormUrlEncoded
    @POST(AEPSACTIVEAUTH)
    Call<String> getAepsActiveAuth(@Field("user_id") String user_id,
                                   @Field("first_name") String first_name,
                                   @Field("last_name") String last_name,
                                   @Field("mobile") String mobile,
                                   @Field("shop_name") String shop_name,
                                   @Field("state_id") String state_id,
                                   @Field("city_id") String city_id,
                                   @Field("address") String address,
                                   @Field("pin_code") String pin_code,
                                   @Field("aadhar_no") String aadhar_no,
                                   @Field("pancard_no") String pancard_no,
                                   @Field("aadhar_photo") String aadhar_photo,
                                   @Field("pancard_photo") String pancard_photo);

    //    @FormUrlEncoded
//    @POST(AEPSHISTORY)
//    Call<String> GetAepsHistory(@Field("userID") String userId,
//                                @Field("fromDate") String from,
//                                @Field("toDate") String to,
//                                @Field("keyword") String keyword);
//
    @FormUrlEncoded
    @POST(AEPSOTOAUTH)
    Call<String> getAepOtp(@Field("user_id") String user_id,
                           @Field("encodeFPTxnId") String encodeFPTxnId,
                           @Field("otp_code") String otp_code);

    @FormUrlEncoded
    @POST(AEPSRESENDOTP)
    Call<String> getAepsResendOtp(@Field("user_id") String user_id,
                                  @Field("encodeFPTxnId") String encodeFPTxnId);

    @FormUrlEncoded
    @POST(AEPSUPLOADKYC)
    Call<String> getAepsUpload(@Field("user_id") String user_id,
                               @Field("encodeFPTxnId") String encodeFPTxnId,
                               @Field("BiometricData") String otp_code);


    /* ICIC Aeps Apis */
    @FormUrlEncoded
    @POST(AEPSICICACTIVEAUTH)
    Call<String> getIcicAepsActiveAuth(@Field("user_id") String user_id,
                                       @Field("first_name") String first_name,
                                       @Field("last_name") String last_name,
                                       @Field("mobile") String mobile,
                                       @Field("shop_name") String shop_name,
                                       @Field("state_id") String state_id,
                                       @Field("city_id") String city_id,
                                       @Field("address") String address,
                                       @Field("pin_code") String pin_code,
                                       @Field("aadhar_no") String aadhar_no,
                                       @Field("pancard_no") String pancard_no,
                                       @Field("aadhar_photo") String aadhar_photo,
                                       @Field("pancard_photo") String pancard_photo);

    @FormUrlEncoded
    @POST(AEPSICICOTOAUTH)
    Call<String> getIcicAepOtp(@Field("user_id") String user_id,
                               @Field("encodeFPTxnId") String encodeFPTxnId,
                               @Field("otp_code") String otp_code);

    @FormUrlEncoded
    @POST(AEPSICICRESENDOTP)
    Call<String> getIcicAepsResendOtp(@Field("user_id") String user_id,
                                      @Field("encodeFPTxnId") String encodeFPTxnId);

    @FormUrlEncoded
    @POST(AEPSICICUPLOADKYC)
    Call<String> getIcicAepsUpload(@Field("user_id") String user_id,
                                   @Field("encodeFPTxnId") String encodeFPTxnId,
                                   @Field("BiometricData") String otp_code);




    @FormUrlEncoded
    @POST(AEPSICICAPIAUTH)
    Call<JsonObject> AddIcicAepsRequest(@Field("user_data") String user_data);
    /* ICIC Aeps Apis */


    /* New Aeps Apis */


    @FormUrlEncoded
    @POST(NEW_AEPSACTIVEAUTH)
    Call<String> newAepsActiveAuth(@Field("user_id") String user_id,
                                   @Field("first_name") String first_name,
                                   @Field("last_name") String last_name,
                                   @Field("mobile") String mobile,
                                   @Field("shop_name") String shop_name,
                                   @Field("state_id") String state_id,
                                   @Field("city_id") String city_id,
                                   @Field("address") String address,
                                   @Field("pin_code") String pin_code,
                                   @Field("aadhar_no") String aadhar_no,
                                   @Field("pancard_no") String pancard_no,
                                   @Field("aadhar_photo") String aadhar_photo,
                                   @Field("pancard_photo") String pancard_photo);

    @FormUrlEncoded
    @POST(NEW_AEPSAPIAUTH)
    Call<JsonObject> newAepsApiAuth(@Field("user_data") String user_data);



    /* New Aeps Apis */


    @GET(GETBBPSOPERATORLIST)
    Call<String> getBbpsOperator();

    @GET(GETBBPSCIRCLELIST)
    Call<String> getBbpsCircle();

    @FormUrlEncoded
    @POST(GETMONEYTRANSFERHISTORY)
    Call<String> getMoneyTransferHistory(@Field("user_id") String user_id,@Field("startDate") String fromDate,
                                         @Field("endDate") String toDate,
                                         @Field("keyword") String keyword);

    @FormUrlEncoded
    @POST(MONEYTRANSFERAUTH)
    Call<String> moneyTransferAuth(@Field("user_id") String user_id,
                                   @Field("beneId") String ben_id,
                                   @Field("amount") String amount);



    @FormUrlEncoded
    @POST(ADDTRANSFERBENEFICERY)
    Call<String> addBeneficiary(@Field("user_id") String user_id,
                                @Field("mobile") String mobile,
                                @Field("account_holder_name") String accholdername,
                                @Field("ben_mobile") String benmob,
                                @Field("account_no") String accno,
                                @Field("ifsc") String ifsc,
                                @Field("bankID") String bankID,
                                @Field("is_verify") String is_verify);

    @FormUrlEncoded
    @POST(BENEFICIARYOTPAUTH)
    Call<String> beneficiaryOtpAuth(@Field("user_id") String user_id,
                                    @Field("beneId") String ben_id,
                                    @Field("mobile") String mobile,
                                    @Field("otp_code") String otp_code);

    @FormUrlEncoded
    @POST(GETBENEFICIARYLIST)
    Call<String> getBeneficiaryList(@Field("user_id") String user_id,
                                    @Field("mobile") String mobile);

    @FormUrlEncoded
    @POST(MONEYTRANSFERlOGIN)
    Call<String> moneyTransferLogin(@Field("user_id") String user_id,
                                    @Field("mobile") String mobile);


    @FormUrlEncoded
    @POST(GETPAYOUTTRANSFERREPORT)
    Call<String> getPayoutTransferHistory(@Field("user_id") String user_id,
                                          @Field("fromDate") String from,
                                          @Field("toDate") String to);


    @FormUrlEncoded
    @POST(MAINWALLETPAYOUTREPORT)
    Call<String> getMainPayout(@Field("user_id") String user_id,
                               @Field("fromDate") String from,
                               @Field("toDate") String to,
                               @Field("page_no") String page_no);

    @FormUrlEncoded
    @POST(COMMISSIONWALLETPAYOUTREPORT)
    Call<String> getCommsionPayout(@Field("user_id") String user_id,
                                   @Field("fromDate") String from,
                                   @Field("toDate") String to,
                                   @Field("page_no") String page);

    @FormUrlEncoded
    @POST(AEPSWALLETPAYOUTREPORT)
    Call<String> getAepsPayout(@Field("user_id") String user_id,
                               @Field("fromDate") String from,
                               @Field("toDate") String to,
                               @Field("page_no") String page_no);


    @FormUrlEncoded
    @POST(EWALLETPAYOUTREPORT)
    Call<String> getEWalletPayoutHistory(@Field("user_id") String user_id,
                                         @Field("fromDate") String from,
                                         @Field("toDate") String to);





    @FormUrlEncoded
    @POST(MONEYTRANSFERREGISTEROTP)
    Call<String> moneyTransferOtpRegister(@Field("user_id") String user_id,
                                          @Field("token") String token,
                                          @Field("otp_code") String otp);


    @FormUrlEncoded
    @POST(GETBBSPSERVICELIST)
    Call<String> getBbpsServiceList(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST(GETBBSPSERVICEOPERATOR)
    Call<String> GetServiceOperatorList(@Field("user_id") String user_id,
                                        @Field("service_id") String service_id);

    @FormUrlEncoded
    @POST(GETBBSPSERVICEFORM)
    Call<String> GetBBPSFORm(@Field("user_id") String user_id,
                             @Field("service_id") String service_id,
                             @Field("biller_id") String biller_id);


    @FormUrlEncoded
    @POST(REGISTERTRANSFER)
    Call<String> moneyTransferRegister(@Field("user_id") String user_id,
                                       @Field("first_name") String fname,
                                       @Field("last_name") String lname,
                                       @Field("mobile") String mobile,
                                       @Field("dob") String dob,
                                       @Field("address") String address,
                                       @Field("pin_code") String pincode);


    @FormUrlEncoded
    @POST(GETBBSPSERVICEBILLFETCH)
    Call<String> getServicePayBillHistory(@Field("user_id") String user_id,
                                          @Field("service_id") String service_id,
                                          @Field("biller_id") String biller_id,
                                          @Field("para1") String para1,
                                          @Field("para2") String para2,
                                          @Field("para3") String para3,
                                          @Field("para4") String para4,
                                          @Field("para5") String para5,
                                          @Field("para6") String para6,
                                          @Field("para7") String para7,
                                          @Field("para8") String para8);


    @FormUrlEncoded
    @POST(GETBBSPSERVICEBILLPAY)
    Call<String> ServiceBillPayAuth(@Field("user_id") String user_id,
                                    @Field("service_id") String service_id,
                                    @Field("biller_id") String biller_id,
                                    @Field("para1") String para1,
                                    @Field("para2") String para2,
                                    @Field("para3") String para3,
                                    @Field("para4") String para4,
                                    @Field("para5") String para5,
                                    @Field("para6") String para6,
                                    @Field("para7") String para7,
                                    @Field("para8") String para8,
                                    @Field("amount") String amount);

    @FormUrlEncoded
    @POST(GETBBSPSERVICEBILLPAY)
    Call<String> ServiceBillPayAuthOTT(@Field("user_id") String user_id,
                                       @Field("service_id") String service_id,
                                       @Field("biller_id") String biller_id,
                                       @Field("para1") String para1,
                                       @Field("para2") String para2,
                                       @Field("para3") String para3,
                                       @Field("para4") String para4,
                                       @Field("para5") String para5,
                                       @Field("para6") String para6,
                                       @Field("para7") String para7,
                                       @Field("para8") String para8,
                                       @Field("amount") String amount,
                                       @Field("planrefid") String planrefid,
                                       @Field("planid") String planid,
                                       @Field("txn_pass") String txn_pass);

    @FormUrlEncoded
    @POST(GETCOMPLAINTLIST)
    Call<String> GetComplaintList(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST(GETMEMBERBYMOBILE)
    Call<String> GetMemberByMobile(@Field("user_id") String user_id,@Field("mobile") String mobile);

    @FormUrlEncoded
    @POST(RAISERECHGCOMPALINT)
    Call<String> RaiseComplaintAUth(@Field("user_id") String user_id,
                                    @Field("recharge_id") String biller_id,
                                    @Field("description") String para1);

    @FormUrlEncoded
    @POST(RAISEBBPSCOMPALINT)
    Call<String> RaiseBBPPSComplaintAUth(@Field("user_id") String user_id,
                                         @Field("recharge_id") String biller_id,
                                         @Field("description") String para1);

    @FormUrlEncoded
    @POST(GETBBSPFASTTAGOPERATOR)
    Call<String> getBbpsFAstagOperator(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST(GETBBSPFASTAGFORM)
    Call<String> FAstagBillFetchAuth(@Field("user_id") String user_id,
                                     @Field("biller_id") String billerId);

    @FormUrlEncoded
    @POST(GETBBSPFASTTAGBILLFETCH)
    Call<String> getFAstagPayBillHistory(@Field("user_id") String user_id,
                                         @Field("biller_id") String biller_id,
                                         @Field("para1") String para1,
                                         @Field("para2") String para2,
                                         @Field("para3") String para3);

    @FormUrlEncoded
    @POST(GETBBSPFASTTAGBILLPAY)
    Call<String> FAstagBillPayAuth(@Field("user_id") String user_id,
                                   @Field("biller_id") String biller_id,
                                   @Field("para1") String para1,
                                   @Field("para2") String para2,
                                   @Field("para3") String para3,
                                   @Field("amount") String amount);

    @FormUrlEncoded
    @POST(GETBBSPELECTOPERATOR)
    Call<String> getBbpsElectricityOperator(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST(GETBBSPELECTFORM)
    Call<String> electricityBillFetchAuth(@Field("user_id") String user_id,
            @Field("biller_id") String billerId);

    @FormUrlEncoded
    @POST(GETBBSPELECBILLFETCH)
    Call<String> getPayBillHistory(@Field("user_id") String user_id,
                                   @Field("biller_id") String biller_id,
                                   @Field("para1") String para1,
                                   @Field("para2") String para2,
                                   @Field("para3") String para3);

    @FormUrlEncoded
    @POST(GETBBSPBILLPAY)
    Call<String> electricityBillPayAuth(@Field("user_id") String user_id,
                                        @Field("biller_id") String biller_id,
                                        @Field("para1") String para1,
                                        @Field("amount") String amount);

    @FormUrlEncoded
    @POST(GETBBSPHISTORY)
    Call<String> getPayBillHistorys(@Field("user_id") String user_id,
                                    @Field("startDate") String from,
                                    @Field("endDate") String to,
                                    @Field("keyword") String keyword);

    @FormUrlEncoded
    @POST(GETBBSPLIVEHISTORY)
    Call<String> getBBPSLiveHistory(@Field("user_id") String user_id,
                                    @Field("startDate") String from,
                                    @Field("endDate") String to,
                                    @Field("keyword") String keyword);

    @FormUrlEncoded
    @POST(GETRECHARGECOMMISIONLIST)
    Call<String> getRechargeCommisionList(@Field("user_id") String user_id);



    @FormUrlEncoded
    @POST(GETBBPSCOMMISIONLIST)
    Call<String> getBBPSCommisionList(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST(GETBBPSFIXCOMMISIONLIST)
    Call<String> getBBPSFixCommisionList(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST(GETAADHARKYCCOMMISIONLIST)
    Call<String> getAadharKycCommisionList(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST(GETACCOUNTVERIFYCOMMISIONLIST)
    Call<String> getAccountVerifyCommisionList(@Field("user_id") String user_id);


    @FormUrlEncoded
    @POST(GETUTIPANCARDCOMMISIONLIST)
    Call<String> getUTIPancardCommisionList(@Field("user_id") String user_id);


//    @FormUrlEncoded
//    @POST(GETBBPSLIVECOMMISIONLIST)
//    Call<String> getBBPSLiveCommisionList(@Field("userID") String code);

    @FormUrlEncoded
    @POST(GETMONEYTRANSFERCOMMISIONLIST)
    Call<String> getMoneyTransferCommisionList(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST(GETAEPSCOMMISIONCHARGE)
    Call<String> getAEPSCommisionCharge(@Field("user_id") String user_id);

    @GET(GET_CONTACT_CONTENT)
    Call<String> getContactContent();

    @FormUrlEncoded
    @POST(SUBMIT_CONTACT_CONTENT)
    Call<String> submitContactUsForm(@Field("name") String name,
                                     @Field("email") String email,
                                     @Field("mobile") String mobile,
                                     @Field("message") String message);

    @GET(GET_PRIVACY_CONTENT)
    Call<String> getPrivacyContent();

    @GET(GET_WEBSITE_DISCLAIMER)
    Call<String> getStringSecond();

    @GET(GET_PRODUCT_DESIGN)
    Call<String> getStringThiord();

    @GET(GET_TERMS_AND_CONDITIONS)
    Call<String> getStringForth();

    @GET(GETCOUNTRYLIST)
    Call<String> GETCOUNTRYLIST();

    @FormUrlEncoded
    @POST(GETTICKETTYPELIST)
    Call<String> GetTicketTypelist(@Field("user_id") String userId);

    @FormUrlEncoded
    @POST(GETOPERATORID)
    Call<String> GetOperatorList(@Field("user_id") String user_id,@Field("mobile") String mob);

    @FormUrlEncoded
    @POST(GetSTATELIST)
    Call<String> getStateList(@Field("user_id") String userId);

    @GET(GetCITYLIST)
    Call<String> getCityList();

    @FormUrlEncoded
    @POST(GETMEMBERLIST)
    Call<String> GETMEMBERLIST(@Field("user_id") String code,@Field("role_id") String role_id);

    @FormUrlEncoded
    @POST(GETFUNDMEMBERLIST)
    Call<String> GETFUNDMEMBERLIST(@Field("user_id") String code,@Field("role_id") String role_id);

    @FormUrlEncoded
    @POST(GETTICKETLIST)
    Call<String> GetTicketKList(@Field("user_id") String code);

    @FormUrlEncoded
    @POST(OPERATORLIST)
    Call<String> GetOperator(@Field("type") String operatorType,@Field("user_id") String userId);

    @GET(CIRCLELIST)
    Call<String> CIRCLELIST();

    @FormUrlEncoded
    @POST(RECHARGEAUTH)
    Call<String> RECHARGEAUTH(@Field("user_id") String user_id,
                              @Field("mobile") String mobile,
                              @Field("operator") String operator,
                              @Field("circle") String circle,
                              @Field("amount") String amount);



    @FormUrlEncoded
    @POST(GETUSERNAME)
    Call<String> GetUsername(@Field("user_code") String userId);


    @FormUrlEncoded
    @POST(REQUESTAMOUNT)
    Call<String> RequestWalletBalance(@Field("user_id") String userid,
                                      @Field("amount") String amount,
                                      @Field("transid") String transid,
                                      @Field("walletType") String walletType,
                                      @Field("remark") String remark,
                                      @Field("photo") String photo);

    @FormUrlEncoded
    @POST(ELECTRICRECHARGEAUTH)
    Call<String> ElectricRechargeAuth(@Field("user_id") String userid,
                                      @Field("number") String mob,
                                      @Field("operator") String oprator,
                                      @Field("customer_name") String custname,
                                      @Field("reference_id") String refId,
                                      @Field("amount") String city);

    @FormUrlEncoded
    @POST(ADDMEMBERAUTH)
    Call<String> ADDMEMBERAUTH(@Field("user_id") String userid,
                               @Field("role_id") String roleid,
                               @Field("name") String name,
                               @Field("email") String email,
                               @Field("mobile") String mob,
                               @Field("password") String pass,
                               @Field("transaction_password") String transpas,
                               @Field("country_id") String coun,
                               @Field("state_id") String state,
                               @Field("city") String city);

    @FormUrlEncoded
    @POST(UPDATEFCM)
    Call<String> UpdateFcm(@Field("user_id") String userid,
                           @Field("fcm_id") String fcmid,
                           @Field("device_id") String device_id);


    @FormUrlEncoded
    @POST(LOGNUSER)
    Call<String> LoginAttempt(@Field("transaction_password") String username,
                              @Field("encode_login_id") String password);

    @FormUrlEncoded
    @POST(LOGINOTPVERIFY)
    Call<String> LoginOTPVerify(@Field("otp_code") String otp,
                                @Field("encode_otp_code") String encode_otp_code);

    @FormUrlEncoded
    @POST(LOGNUSER)
    Call<String> Login(@Field("username") String username,
                       @Field("password") String password);

    @FormUrlEncoded
    @POST(SIGNUP)
    Call<String> Register(@Field("name") String name,
                          @Field("mobile") String phone,
                          @Field("email") String email,
                          @Field("password") String password,
                          @Field("transaction_password") String tpassword);

    @FormUrlEncoded
    @POST(CIRCLELIST)
    Call<String> GetCircleList(@Field("user_id") String userId);

    @FormUrlEncoded
    @POST(FORGOTPWDAUTH)
    Call<String> AuthForgotPwd(@Field("mobile") String operatorType);

    @FormUrlEncoded
    @POST(FORGOTPWDOTPVERIFY)
    Call<String> ForgotOtpverify(@Field("otp") String operatorType);

    @FormUrlEncoded
    @POST(FORGOTUPDATEPWD)
    Call<String> ForgotUpdatePwd(@Field("userID") String userId,
                                 @Field("password") String password,
                                 @Field("cpassword") String cpassword);

    @FormUrlEncoded
    @POST(UPADATEUSERDATA)
    Call<String> UpdateUserData(@Field("user_id") String userId,
                                @Field("name") String password,
                                @Field("photo") String photo);

    @FormUrlEncoded
    @POST(SUBMITCOMPALINT)
    Call<String> SubMitComplain(@Field("user_id") String title,
                                @Field("subject") String desc,
                                @Field("type_id") String type,
                                @Field("message") String custId,
                                @Field("photo") String photo);


    @FormUrlEncoded
    @POST(CHANGEPASSWORD)
    Call<String> ChangePassword(@Field("user_id") String userId,
                                @Field("opw") String oldpassword,
                                @Field("npw") String newpassword,
                                @Field("cpw") String confirmpassword);

    @FormUrlEncoded
    @POST(CHANGETRANCATIONPASSWORD)
    Call<String> ChangeTranscationPassword(@Field("userID") String userId,
                                           @Field("otpw") String oldpassword,
                                           @Field("ntpw") String newpassword,
                                           @Field("ctpw") String confirmpassword);

    @FormUrlEncoded
    @POST(RECHARGEOTPAUTH)
    Call<String> RechargeOtpConfirm(@Field("userID") String userId,
                                    @Field("otp") String otp);

    @FormUrlEncoded
    @POST(WALLETHISTORY)
    Call<String> GetWalletHistory(@Field("user_id") String userId,
                                  @Field("startDate") String fromDate,
                                  @Field("endDate") String toDate,
                                  @Field("keyword") String keyword);

    @FormUrlEncoded
    @POST(EWALLETHISTORY)
    Call<String> GetEWalletHistory(@Field("user_id") String userId,
                                   @Field("startDate") String fromDate,
                                   @Field("endDate") String toDate,
                                   @Field("keyword") String keyword);

    @FormUrlEncoded
    @POST(GETAEPSWALLETHISTORY)
    Call<String> GetAepsWalletHistory(@Field("user_id") String userId,
                                      @Field("fromDate") String from,
                                      @Field("toDate") String to,
                                      @Field("page_no") String page_no);

    @FormUrlEncoded
    @POST(GETCOMMISIONWALLETHISTORY)
    Call<String> GetCommissionWalletHistory(@Field("user_id") String userId,
                                            @Field("fromDate") String from,
                                            @Field("toDate") String to,
                                            @Field("page_no") String page_no);

    @FormUrlEncoded
    @POST(GETPOINTWALLETHISTORY)
    Call<String> GetPointWalletHistory(@Field("user_id") String userId,
                                       @Field("fromDate") String from,
                                       @Field("toDate") String to,
                                       @Field("page_no") String page_no);

    @FormUrlEncoded
    @POST(REQUESTHISTORY)
    Call<String> GetRequestHistory(@Field("user_id") String userId);

    @FormUrlEncoded
    @POST(E_REQUESTHISTORY)
    Call<String> GetERequestHistory(@Field("user_id") String userId);

    @FormUrlEncoded
    @POST(ADDWALLET)
    Call<String> AddWallet(@Field("userID") String operatorType,
                           @Field("amount") String amount);

    @FormUrlEncoded
    @POST(WALLETTRANSFER)
    Call<String> WalletTransfer(@Field("user_id") String userid,
                                @Field("member_id") String memberid,
                                @Field("type") String type,
                                @Field("amount") String amount,
                                @Field("description") String desc);

    @FormUrlEncoded
    @POST(WALLETOTPAUTH)
    Call<String> WalletTransferOtpAuth(@Field("userID") String userid,
                                       @Field("otp_code") String otpcode);

    @FormUrlEncoded
    @POST(MTRANSFERHISTORY)
    Call<String> MtransferHistory(@Field("user_id") String userId,
                                  @Field("startDate") String fromDate,
                                  @Field("endDate") String toDate,
                                  @Field("keyword") String keyword);

    @FormUrlEncoded
    @POST(MOBILETRANSFER)
    Call<String> TransferMwalletAmount(@Field("user_id") String userid,
                                       @Field("mobile") String mobilenumber,
                                       @Field("account_holder_name") String holderName,
                                       @Field("account_no") String accountNumber,
                                       @Field("ifsc") String ifsc,
                                       @Field("amount") String amount);

    @FormUrlEncoded
    @POST(MOBILETRANSFERVERIFY)
    Call<String> VerifyMwalletAmount(@Field("userID") String userid,
                                     @Field("otp_code") String holderName,
                                     @Field("encode_transaction_id") String transId);

    @FormUrlEncoded
    @POST(UPGRADE)
    Call<String> UpgradeAccount(@Field("userID") String userid,
                                @Field("package_id") String pkgId,
                                @Field("token") String pin,
                                @Field("memberID") String membeId,
                                @Field("upgrade_by") String upgrade_by);


    @GET(GETPACKAGELIST)
    Call<String> GetPackageList();

    @FormUrlEncoded
    @POST(GETDIRECTDOWNLINE)
    Call<String> GetDirectDownLine(@Field("userID") String userId);

    @FormUrlEncoded
    @POST(GETTOTALDOWNLINE)
    Call<String> GetTotalDownLine(@Field("userID") String userId);

    @FormUrlEncoded
    @POST(GETTOTALACTIVEDOWNLINE)
    Call<String> GetTotalActiveDownLine(@Field("userID") String userId);

    @FormUrlEncoded
    @POST(GETTOTALDEACTIVEDOWNLINE)
    Call<String> GetTotalDeactiveDownLine(@Field("userID") String userId);

    @FormUrlEncoded
    @POST(GETDIRECTACTIVEDOWNLINE)
    Call<String> GetDirectActiveDownLine(@Field("userID") String userId);

    @FormUrlEncoded
    @POST(GETDIRECTDEACTIVEDOWNLINE)
    Call<String> GetDirectDeActiveDownLine(@Field("userID") String userId);

    @FormUrlEncoded
    @POST(GETMATCHINGBINARY)
    Call<String> GetMatchingBinary(@Field("userID") String userId);

    @FormUrlEncoded
    @POST(GETDIRECTINCOME)
    Call<String> GetDirectIncome(@Field("userID") String userId);

    @GET(GETDASHBOARD)
    Call<String> GetDashBoard();

    @FormUrlEncoded
    @POST(GETSUBCATEGORY)
    Call<String> GetSUBCATEGORY(@Field("catID") String userId);

    @FormUrlEncoded
    @POST(PRODUCTLIST)
    Call<String> GetProductList(@Field("catID") String userId);

    @FormUrlEncoded
    @POST(PRODUCTDETAIL)
    Call<String> GetProductDetail(@Field("proID") String prdtId);

    @FormUrlEncoded
    @POST(RECHARGEHISTORY)
    Call<String> GetRechargeHistory(@Field("user_id") String user_id,
                                    @Field("startDate") String startDate,
                                    @Field("endDate") String endDate,
                                    @Field("keyword") String keyword);


    @FormUrlEncoded
    @POST(RECHARGECOMMISIONREPORTLIST)
    Call<String> GetRechargeCommissionReportList(@Field("user_id") String user_id,
                                    @Field("startDate") String startDate,
                                    @Field("endDate") String endDate,
                                    @Field("keyword") String keyword);

    @FormUrlEncoded
    @POST(AEPSCOMMISIONREPORTLIST)
    Call<String> GetAEPSCommissionReportList(@Field("user_id") String user_id,
                                                 @Field("startDate") String startDate,
                                                 @Field("endDate") String endDate,
                                                 @Field("keyword") String keyword);

    @FormUrlEncoded
    @POST(FUNDTRANSFERCOMMISIONREPORTLIST)
    Call<String> GetFundTransCommissionReportList(@Field("user_id") String user_id,
                                             @Field("startDate") String startDate,
                                             @Field("endDate") String endDate,
                                             @Field("keyword") String keyword);

    @FormUrlEncoded
    @POST(RECHARGECOMMISIONHISTORY)
    Call<String> GetRechargeCommisionHistory(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST(GETCREDITCARDHISTORY)
    Call<String> GetCreditCardHistory(@Field("userID") String code);

    @FormUrlEncoded
    @POST(GETLOAN)
    Call<String> GetLoan(@Field("userID") String code,
                         @Field("loan_id") String loanid);

    @FormUrlEncoded
    @POST(GETLOANINSTALLMENT)
    Call<String> GetLoanInstallment(@Field("userID") String code,
                                    @Field("loan_display_id") String loanid);

    @FormUrlEncoded
    @POST(EMIPAYAUTH)
    Call<String> PayEmi(@Field("userID") String code,
                        @Field("loan_display_id") String loanid,
                        @Field("emi_id") String emi_id);

    @FormUrlEncoded
    @POST(ROFFERS)
    Call<String> GetROffers(@Field("user_id") String user_id,
                            @Field("mobile") String code,
                            @Field("operator") String emi_id);

    @FormUrlEncoded
    @POST(RDTHOFFERS)
    Call<String> GetRDthOffers(@Field("user_id") String user_id,
                               @Field("mobile") String code,
                               @Field("operator") String emi_id);

    @FormUrlEncoded
    @POST(VIEWALLPLANS)
    Call<String> GetAllPlans(@Field("user_id") String user_id,
                             @Field("circle") String code,
                             @Field("operator") String emi_id);

    @FormUrlEncoded
    @POST(VIEWALLDTHPLANS)
    Call<String> GetAllDthPlans(@Field("user_id") String user_id,
                                @Field("number") String card_num,
                                @Field("operator") String emi_id);




    @FormUrlEncoded
    @POST(GETLOANCHARGES)
    Call<String> getLoanCharges(@Field("tenureID") String tenure_id,
                                @Field("loan_amount") String loan_amnt);

    @GET(GETTENURELIST)
    Call<String> getTenureList();

//    @FormUrlEncoded
//    @POST(AEPSHISTORY)
//    Call<String> getAepsHistory(@Field("userID") String userID,
//                                @Field("fromDate") String fromDate,
//                                @Field("toDate") String toDate);

    @FormUrlEncoded
    @POST(AEPSHISTORY)
    Call<String> GetAepsHistory(@Field("userID") String userId,
                                @Field("fromDate") String from,
                                @Field("toDate") String to,
                                @Field("page_no") String page);



    @FormUrlEncoded
    @POST(SIGNUP)
    Call<String> Register(@Field("name") String name,
                          @Field("mobile") String phone,
                          @Field("email") String email,
                          @Field("password") String password,
                          @Field("transaction_password") String tpassword,
                          @Field("member_position") String mposition,
                          @Field("referral_id") String refid,
                          @Field("pan_card") String pancard);

    @FormUrlEncoded
    @POST(GETCCTRANSFERHISTORY)
    Call<String> CCTransferHistory(@Field("userID") String userid);

    @FormUrlEncoded
    @POST(USERKYC)
    Call<String> UserKYC(@Field("userID") String userid,
                         @Field("accountName") String acc_name,
                         @Field("accountNumber") String acc_num,
                         @Field("mobile_no") String mobile_no,
                         @Field("ifsc") String ifsc,
                         @Field("bankName") String bankname,
                         @Field("aadhar_no") String aadhar_no,
                         @Field("dob") String dob,
                         @Field("adharFront") String aadharFront,
                         @Field("adharBack") String aadharBack,
                         @Field("pancard") String photo);

    @FormUrlEncoded
    @POST(USERKYCDETAIL)
    Call<String> USERKYCDETAIL(@Field("userID") String userid);




    @FormUrlEncoded
    @POST(GET_NOMINEE_DATA)
    Call<String> getNomineeData(@Field("userID") String userid);

    String GET_AVAILABLE_PIN = "getAvailablePin";
    String TRANSFER_PIN_AUTH = "transferPinAuth";
    String GET_RANK_LIST = "getRankList";
    String GET_RANKWISE_TEAMLIST = "getRankWiseTeamList";
    String GET_LEVEL_LIST = "getLevelList";
    String GET_LEVELWISE_TEAMLIST = "getLevelWiseTeamList";

    @FormUrlEncoded
    @POST(TRANSFER_PIN_AUTH)
    Call<String> transferPinAuth(@Field("userID") String userID,
                                 @Field("package_id") String package_id,
                                 @Field("token_number") String token_number,
                                 @Field("transfer_to_user") String transfer_to_user);

    @FormUrlEncoded
    @POST(GET_AVAILABLE_PIN)
    Call<String> getAvailablePin(@Field("userID") String userID,
                                 @Field("package_id") String package_id);

    @FormUrlEncoded
    @POST(GET_RANK_LIST)
    Call<String> getRankList(@Field("userID") String userID);

    @FormUrlEncoded
    @POST(GET_RANKWISE_TEAMLIST)
    Call<String> getRankWiseTeamList(@Field("userID") String userID,
                                     @Field("rank_id") String rank_id,
                                     @Field("page_no") String page_no);

    @FormUrlEncoded
    @POST(GET_LEVEL_LIST)
    Call<String> getLevelList(@Field("userID") String userID);

    @FormUrlEncoded
    @POST(GET_LEVELWISE_TEAMLIST)
    Call<String> getLevelWiseTeamList(@Field("userID") String userID,
                                      @Field("level_id") String level_id,
                                      @Field("page_no") String page_no);

    @GET(GETZOOMDETAILS)
    Call<String> getZoomDetails();

    @FormUrlEncoded
    @POST(GET_NOTIFICATION_LIST)
    Call<String> notificationList(@Field("userID") String userID);


    @FormUrlEncoded
    @POST(GET_VENDOR_DETAILS)
    Call<String> getVendorRequestDetails(@Field("userID") String userID);

    @FormUrlEncoded
    @POST(VENDOR_REGISTER_AUTH)
    Call<String> vendorRegisterAuth(@Field("userID") String userID,
                                    @Field("business_name") String business_name,
                                    @Field("mobile") String mobile,
                                    @Field("alternate_mobile") String alternate_mobile,
                                    @Field("gst_no") String gst_no,
                                    @Field("commission") String commission,
                                    @Field("address") String address,
                                    @Field("description") String description,
                                    @Field("pincode") String pincode,
                                    @Field("city_id") String city_id,
                                    @Field("state_id") String state_id,
                                    @Field("latitude") String latitude,
                                    @Field("longitude") String longitude,
                                    @Field("profile") String profile);

    @FormUrlEncoded
    @POST(GENERATE_VENDOR_BILL_AUTH)
    Call<String> generateVendorBillAuth(@Field("userID") String userID,
                                        @Field("member_id") String member_id,
                                        @Field("amount") String amount);

    @FormUrlEncoded
    @POST(VENDOR_BILLER_LIST)
    Call<String> vendorBillList(@Field("userID") String userID);

    @FormUrlEncoded
    @POST(SEARCH_STORE_AUTH)
    Call<String> searchStoreAuth(@Field("userID") String userID,
                                 @Field("state_id") String state_id,
                                 @Field("city_id") String city_id,
                                 @Field("pincode") String pincode);

    @FormUrlEncoded
    @POST(SEARCH_DOWNLINE)
    Call<String> searchDownline(@Field("userID") String userID,
                                 @Field("user_code") String pincode);


    @FormUrlEncoded
    @POST(GETVENDOR_PACKAGELIST)
    Call<String> getVendorPackageList(@Field("userID") String userID);

    @FormUrlEncoded
    @POST(VENDOR_PACKAGE_PURCHASEAUTH)
    Call<String> vendorPackagePurchaseAuth(@Field("userID") String userID,
                                           @Field("package_id") String package_id);

    @FormUrlEncoded
    @POST(UPLOAD_VENDOR_BANNER)
    Call<String> uploadVendorBanner(@Field("userID") String userID,
                                    @Field("banner") String banner);

    @FormUrlEncoded
    @POST(DELETE_VENDOR_BANNER_AUTH)
    Call<String> deleteVendorBannerAuth(@Field("userID") String userID,
                                        @Field("banner_id") String banner_id);

    @FormUrlEncoded
    @POST(GET_VENDOR_BANNERLIST)
    Call<String> getVendorBannerList(@Field("userID") String userID);

    @FormUrlEncoded
    @POST(GET_AREA_BANNERLIST)
    Call<String> getAreaBannerList(@Field("userID") String userID);

    String ORDER_TOKEN_AUTH = "topupPgAuth";
    String ADD_TOPUP_WALLET = "PaymentReceiveFreecash";

    @FormUrlEncoded
    @POST(ORDER_TOKEN_AUTH)
    Call<String> OrderTokenAuth(@Field("user_id") String user_id,
                                @Field("order_id") String order_id,
                                @Field("amount") String amount);

    @FormUrlEncoded
    @POST(ADD_TOPUP_WALLET)
    Call<String> PaymentReceiveFreecash(@Field("user_id") String MemberID,
                                        @Field("order_id") String order_id,
                                        @Field("amount") String Amount,
                                        @Field("payment_status") String PaymentStatus);

    @FormUrlEncoded
    @POST(VIRTUAL_ACCOUNT_AUTH)
    Call<String> virtualAccountAuth(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST(VIRTUAL_ACTIVE_AUTH)
    Call<String> activeVirtualAuth(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST(VIRTUAL_ACCOUNT_REPORT)
    Call<String> virtualAccountList(@Field("user_id") String user_id,
                                    @Field("startDate") String fromDate,
                                    @Field("endDate") String toDate,
                                    @Field("keyword") String keyword);

    @FormUrlEncoded
    @POST(DMT_BANK_LIST)
    Call<String> dmtBankList(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST(GET_SENDER_DETAILS)
    Call<String> getSenderDetail(@Field("user_id") String user_id,
                                 @Field("mobile") String mobile);

    @FormUrlEncoded
    @POST(UPDATE_SENDER_DETAILS)
    Call<String> updateSenderDetailAuth(@Field("user_id") String user_id,
                                        @Field("accountMobile") String mobile,
                                        @Field("first_name") String first_name,
                                        @Field("last_name") String last_name,
                                        @Field("dob") String dob,
                                        @Field("address") String address,
                                        @Field("pin_code") String pin_code);


    @FormUrlEncoded
    @POST(RECENTRECHARGEHISTORY)
    Call<String> getRecentRechargeHistory(@Field("user_id") String user_id,
                                          @Field("service_id") String service_id,
                                          @Field("is_bbps") String is_bbps);


    @FormUrlEncoded
    @POST(DELETE_DMT_BENEFICERY)
    Call<String> deleteDmtBen(@Field("user_id") String user_id,
                              @Field("mobile") String mobile,
                              @Field("beneId") String beneId);

}