package com.rechargeapp.activity;

import static com.rechargeapp.BaseApp.TAG;

import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.codunite.rechargeapp.R;
import com.commonutility.retrofit.ApiInterface;
import com.rechargeapp.activity.aepsnew.ActivityFinoAEPSHistory;
import com.rechargeapp.activity.bbps.ActivityAadharKYCCommision;
import com.rechargeapp.activity.bbps.ActivityAccountVerifyCommision;
import com.rechargeapp.activity.bbps.ActivityBBPSCommision;
import com.rechargeapp.activity.bbps.ActivityBbpsAllServices;
import com.rechargeapp.activity.bbps.ActivityBbpsElectricity;
import com.rechargeapp.activity.bbps.ActivityBbpsHistory;
import com.rechargeapp.activity.bbps.ActivityBbpsTollTax;
import com.rechargeapp.activity.bbps.ActivityBillPayCommision;
import com.rechargeapp.activity.bbps.ActivityPanCardCommision;
import com.rechargeapp.activity.commissionincome.ActivityAEPSCommision;
import com.rechargeapp.activity.commissionincome.ActivityDMRCommision;
import com.rechargeapp.activity.mainwallet.ActivityTopupWalletRazorpay;
import com.rechargeapp.activity.members.AddEditMember;
import com.rechargeapp.activity.members.AllMemberList;
import com.rechargeapp.activity.profile.ActivityChangePassword;
import com.rechargeapp.activity.profile.ActivityChangeTPassword;
import com.rechargeapp.activity.profile.ActivityKyc;
import com.rechargeapp.activity.profile.ActivityProfile;
import com.rechargeapp.activity.reports.ActivityAEPSCommisionHistory;
import com.rechargeapp.activity.reports.ActivityAepsWalletHistory;
import com.rechargeapp.activity.reports.ActivityBillPayHistory;
import com.rechargeapp.activity.reports.ActivityComisionWalletHistory;
import com.rechargeapp.activity.reports.ActivityFundTransferCommisionHistory;
import com.rechargeapp.activity.reports.ActivityRechargeCommisionHistory;
import com.rechargeapp.activity.reports.ActivityRechargeHistory;
import com.rechargeapp.activity.reports.ActivityWalletHistory;
import com.rechargeapp.activity.support.ActivityComplaintList;
import com.rechargeapp.activity.support.ActivityContact;
import com.rechargeapp.activity.support.ActivityHelpFeedback;
import com.rechargeapp.activity.support.ActivityTicketList;
import com.rechargeapp.activity.virtualaccount.ActivityVirtualAccount;
import com.rechargeapp.activity.virtualaccount.VirtualTransactionReport;
import com.rechargeapp.activity.wallet.ActivityAddFundRequest;
import com.rechargeapp.activity.wallet.ActivityAddFundRequestHistory;
import com.rechargeapp.activity.wallet.ActivityEWalletHistory;
import com.rechargeapp.activity.wallet.ActivityEWalletTransfer;
import com.rechargeapp.activity.wallet.ActivityTopupWallet;
import com.rechargeapp.activity.wallet.ActivityWallet;
import com.rechargeapp.activity.wallet.ActivityWalletTransfer;
import com.rechargeapp.adapter.ExpandableListAdapter;
import com.commonutility.firebase.FirebaseMessageReceiver;
import com.rechargeapp.fragment.FragHomeDashBoard;
import com.rechargeapp.fragment.FragProfile;
import com.rechargeapp.fragment.FragReport;
import com.rechargeapp.fragment.FragSetting;
import com.rechargeapp.model.MenuModel;
import com.commonutility.spinner.SpinnerModel;
import com.commonutility.GlobalVariables;
import com.commonutility.ImageLoading;
import com.commonutility.LocaleHelper;
import com.commonutility.PreferenceConnector;
import com.commonutility.ShowCustomToast;
import com.commonutility.WebService;
import com.commonutility.WebServiceListener;
import com.commonutility.customfont.FontUtils;
import com.rechargeapp.activity.aepsnew.ActivityFinoAEPSBankingService;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.appupdate.AppUpdateOptions;
import com.google.android.play.core.install.InstallState;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.razorpay.Checkout;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ActivityMain extends AppCompatActivity implements View.OnClickListener, WebServiceListener,
        NavigationView.OnNavigationItemSelectedListener, BottomNavigationView.OnItemSelectedListener {
    private Context svContext;
    private ShowCustomToast customToast;
    Intent svIntent;
    boolean loadApiInBackground = false;
    LinkedList<String> lstUploadData = new LinkedList<>();
    private View[] allViewWithClick = {};
    private int[] allViewWithClickId = {};
    public NavigationView navigationView;
    private ImageView imgMenu;
    private RelativeLayout layBottomCircle;
    private ImageView btnCircleScan, btnCircleTrophy, btnCircleNotification;
    private boolean isBottomFabClicked = false;
    BottomNavigationView bottomNav;
    public static final String UPI_ADD_MONEY = "Upi Add Money";
    public static String R_WALLET_MYFUNDREQUEST = "My Fund Request";
    public static String E_WALLET_MYFUNDREQUEST = "E Fund Request";
    public static List<SpinnerModel> lstCategoryData = new ArrayList<>();
    ExpandableListAdapter expandableListAdapter;
    ExpandableListView expandableListView;
    List<MenuModel> headerList = new ArrayList<>();
    HashMap<MenuModel, List<MenuModel>> childList = new HashMap<>();
    private String[] strMenuItemMaster;
    private String[][] strMenuChildItemMaster;
    private String[] strMenuItemDistributor;
    private String[][] strMenuChildItemDistributor;
    private String[] strMenuItemRetailor;
    private String[][] strMenuChildItemRetailor;

    private int[] arr_ic_menus;
    private String[] strMenuItem;
    private String[][] strMenuChildItem;
    private DrawerLayout drawer;
    private View viewUpdate;
    public AppUpdateManager appUpdateManager;
    private int UPDATE_TYPE = AppUpdateType.IMMEDIATE;
    private final int APP_UPDATE_REQUEST_CODE = 1222;

    String actReferTo = "";
    private String addAmountToWallet;

    public static String ShowBalance(Context svContext) {
        return GlobalVariables.CURRENCYSYMBOL +
                PreferenceConnector.readString(svContext, PreferenceConnector.WALLETBAL, "0");
    }

    public static String ShoweBalance(Context svContext) {
        return GlobalVariables.CURRENCYSYMBOL +
                PreferenceConnector.readString(svContext, PreferenceConnector.EWALLETBAL, "0");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);
        StartApp();
        OnClickCombineDeclare(allViewWithClick);
        resumeApp();
        initFirebaseNotifyToken();
        checkInAppUpdate();
    }

    public void resumeApp() {
        headerList = new ArrayList<>();
        childList = new HashMap<>();
        initToolbar();

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        expandableListView = findViewById(R.id.expandableListView);

        layBottomCircle = findViewById(R.id.lay_bottom_navigation_circle);
        btnCircleScan = findViewById(R.id.iv_circle_scan);
        btnCircleTrophy = findViewById(R.id.iv_circle_trophy);
        btnCircleNotification = findViewById(R.id.iv_circle_notification);
        btnCircleScan.setOnClickListener(this);
        btnCircleTrophy.setOnClickListener(this);
        btnCircleNotification.setOnClickListener(this);

        initMenuData();
        prepareMenuData(PreferenceConnector.readString(svContext, PreferenceConnector.LOGINUSERTYPE, "5"));
        populateExpandableList(PreferenceConnector.readString(svContext, PreferenceConnector.LOGINUSERTYPE, "5"));
        initBottomMenu();
        ImageView img_close = findViewById(R.id.img_close);
        RelativeLayout rl_main = findViewById(R.id.rl_main);
        LinearLayout ll_logout = findViewById(R.id.ll_logout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        initNavigationMenu(
                PreferenceConnector.readString(svContext, PreferenceConnector.LOGINUSERNAME, ""),
                PreferenceConnector.readString(svContext, PreferenceConnector.LOGINUSERTYPE, ""),
                PreferenceConnector.readString(svContext, PreferenceConnector.LOGINUSERPROFILEPIC, ""),
                PreferenceConnector.readString(svContext, PreferenceConnector.LOGINMEMBERID, "memberid"),
                navigationView);
        switchContent(new FragHomeDashBoard());
        final float END_SCALE = 0.7f;
        drawer.setScrimColor(Color.TRANSPARENT);
        ll_logout.setOnClickListener(v -> ShowConfirmLogout(svContext, "Logout", "Are you confirm?",
                "Are you are ready to end your current session. You have to enter login detail again"));
    }

    private void StartApp() {
        svContext = this;
        customToast = new ShowCustomToast(svContext);
        FirebaseMessageReceiver.CreateNotificationChannel(svContext);
        ViewGroup root = (ViewGroup) findViewById(R.id.headlayout);
        if (!GlobalVariables.CUSTOMFONTNAME.equals("")) {
            Typeface font = Typeface.createFromAsset(getAssets(), GlobalVariables.CUSTOMFONTNAME);
            FontUtils.setFont(root, font);
        }
        hideKeyboard();
        SetLanguage("en");
    }


    private void OnClickCombineDeclare(View[] allViewWithClick) {
        for (int j = 0; j < allViewWithClick.length; j++) {
            allViewWithClick[j] = findViewById(allViewWithClickId[j]);
            allViewWithClick[j].setOnClickListener(v -> {
                switch (v.getId()) {
//                        case R.id.img_notification:
//                            break;
                }
            });
        }
    }

    private void SetLanguage(String languageCode) {
        LocaleHelper.setLocale(svContext, languageCode);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_circle_notification:
                svIntent = new Intent(svContext, ActivityNotification.class);
                startActivity(svIntent);
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

    public void loadUserData(boolean inBackground) {
        lstUploadData = new LinkedList<>();
        lstUploadData.add(PreferenceConnector.readString(svContext, PreferenceConnector.LOGINEDUSERID, ""));
        lstUploadData.add(PreferenceConnector.readString(svContext, PreferenceConnector.FCMID, ""));
        lstUploadData.add(PreferenceConnector.readString(svContext, PreferenceConnector.DEVICE_ID, ""));
        //callWebService(ApiInterface.UPDATEFCM, lstUploadData);
        if (inBackground) {
            callWebServiceWithoutLoader(ApiInterface.UPDATEFCM, lstUploadData);
        } else {
            callWebService(ApiInterface.UPDATEFCM, lstUploadData);
        }
    }

    private void loadUserDataBackground() {
        lstUploadData = new LinkedList<>();
        lstUploadData.add(PreferenceConnector.readString(svContext, PreferenceConnector.LOGINEDUSERID, ""));
        lstUploadData.add(PreferenceConnector.readString(svContext, PreferenceConnector.FCMID, ""));
        lstUploadData.add(PreferenceConnector.readString(svContext, PreferenceConnector.DEVICE_ID, ""));
        callWebServiceWithoutLoader(ApiInterface.UPDATEFCM, lstUploadData);
    }

    private void callWebServiceWithoutLoader(String postUrl, LinkedList<String> lstUploadData) {
        loadApiInBackground = true;
        WebService webService = new WebService(svContext, postUrl, lstUploadData, this, false);
        webService.LoadDataRetrofit(webService.callReturn());
    }

    private void callWebService(String postUrl, LinkedList<String> lstUploadData) {
        loadApiInBackground = false;
        WebService webService = new WebService(svContext, postUrl, lstUploadData, this);
        webService.LoadDataRetrofit(webService.callReturn());
    }

    public static final String TAG_MESSAGE = "message";
    public static final String TAG_STATUS = "status";

    @Override
    public void onWebServiceActionComplete(String result, String url) {
        if (url.contains(ApiInterface.UPDATEFCM)) {
            ActivitySplash.LoadUserData(result, svContext);
            initMenuData();
            prepareMenuData(PreferenceConnector.readString(svContext, PreferenceConnector.LOGINUSERTYPE, "5"));
            populateExpandableList(PreferenceConnector.readString(svContext, PreferenceConnector.LOGINUSERTYPE, "5"));

            initNavigationMenu(
                    PreferenceConnector.readString(svContext, PreferenceConnector.LOGINUSERNAME, ""),
                    PreferenceConnector.readString(svContext, PreferenceConnector.LOGINUSERTYPE, ""),
                    PreferenceConnector.readString(svContext, PreferenceConnector.LOGINUSERPROFILEPIC, ""),
                    PreferenceConnector.readString(svContext, PreferenceConnector.LOGINMEMBERID, "memberid"),
                    navigationView);
            initToolbar();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
//        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container);
//        if (fragment instanceof FragHomeDashBoard) {
//            loadUserData(true);
//        }
        loadUserDataBackground();
        hideKeyboard();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void initToolbar() {
        imgMenu = (ImageView) findViewById(R.id.img_menu);
        imgMenu.setOnClickListener(view -> OpenDrawer());
        LinearLayout img_notification = (LinearLayout) findViewById(R.id.img_notification);
        img_notification.setVisibility(View.VISIBLE);
        img_notification.setOnClickListener(view -> {
            //svIntent = new Intent(svContext, ActivityContact.class);
            //svContext.startActivity(svIntent);
        });
    }


    private void initBottomMenu() {
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnItemSelectedListener(item -> {

            Fragment selectFragment = null;
            switch (item.getItemId()) {
                case R.id.nav_home:
                    switchContent(new FragHomeDashBoard());
                    break;
                case R.id.nav_report:
                    switchContent(new FragReport());
                    break;
                case R.id.nav_profile:
                    switchContent(new FragProfile());
                    break;
                case R.id.nav_setting:
                    switchContent(new FragSetting());
                    break;
            }
            return true;
        });

        FloatingActionButton bottomNavMore = findViewById(R.id.bottom_nav_more);
        bottomNavMore.setOnClickListener(v -> {
            SetBottomViewAnimation(!isBottomFabClicked, bottomNavMore);
        });
    }

    private void SetBottomViewAnimation(boolean isShow, FloatingActionButton fab) {
        int animId = isShow ? R.anim.rotate_open_animation : R.anim.rotate_close_animation;
        Animation animation = AnimationUtils.loadAnimation(svContext, animId);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                fab.setClickable(false);
                setFabItemClickable();
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                isBottomFabClicked = isShow;
                fab.setClickable(true);
                setFabItemClickable();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        layBottomCircle.startAnimation(animation);
        layBottomCircle.setVisibility(isShow ? View.VISIBLE : View.GONE);
        if (isShow) {
            fab.setImageDrawable(ContextCompat.getDrawable(svContext, R.drawable.ic_round_close_24));
        } else {
            fab.setImageDrawable(ContextCompat.getDrawable(svContext, R.drawable.ic_scan));
        }
    }

    private void setFabItemClickable() {
        layBottomCircle.setClickable(isBottomFabClicked);
        layBottomCircle.setFocusable(isBottomFabClicked);

        btnCircleScan.setClickable(isBottomFabClicked);
        btnCircleTrophy.setClickable(isBottomFabClicked);
        btnCircleNotification.setClickable(isBottomFabClicked);
    }

    public void initNavigationMenu(String strName, String usertype, String imageUrl, String memberId, NavigationView navigationView) {
        View navigation_header = navigationView.getHeaderView(0);
        TextView tvName = navigation_header.findViewById(R.id.menuheader_name);
        TextView tvMemberId = navigation_header.findViewById(R.id.menuheader_memberid);
        TextView tv_user_type = (TextView) navigation_header.findViewById(R.id.user_type);

        CircularImageView avatar = (CircularImageView) navigation_header.findViewById(R.id.menuheader_dp);
        LinearLayout ll_close = navigation_header.findViewById(R.id.ll_close);

        tvName.setText(strName);
        tvMemberId.setText(memberId);
        if (usertype.equalsIgnoreCase("3")) {
            tv_user_type.setText("Master");
        } else if (usertype.equalsIgnoreCase("4")) {
            tv_user_type.setText("Distributor");
        } else if (usertype.equalsIgnoreCase("5")) {
            tv_user_type.setText("Retailor");
        }

        Typeface fontFamily = Typeface.createFromAsset(getAssets(), GlobalVariables.CUSTOMFONTNAMEBOLD);
        tvName.setTypeface(fontFamily);
        tvMemberId.setTypeface(fontFamily);

        try {
            ImageLoading.loadImages(imageUrl, avatar, R.drawable.users);
        } catch (Exception e) {
            e.printStackTrace();
        }
        avatar.setOnClickListener(view -> {
            svIntent = new Intent(svContext, ActivityProfile.class);
            startActivity(svIntent);
            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
        });

        ll_close.setOnClickListener(v -> {
            drawer.closeDrawer(Gravity.LEFT);
        });
    }

    public static boolean toggleArrow(boolean show, View view, boolean delay) {
        if (show) {
            view.animate().setDuration(delay ? 200 : 0).rotation(180);
            return true;
        } else {
            view.animate().setDuration(delay ? 200 : 0).rotation(0);
            return false;
        }
    }

    public void switchContent(Fragment fragment, String tag) {
        hideKeyboard();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(tag)
                .commit();
    }

    public void switchContent(Fragment fragment) {
        hideKeyboard();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

    public void switchBack() {
        hideKeyboard();
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
        }
    }

    private static void LogOut(Context svContext) {
        PreferenceConnector.cleanPrefrences(svContext);
        Intent svIntent = new Intent(svContext, ActivityLogin.class);
        svContext.startActivity(svIntent);
        ((Activity) svContext).finish();
    }

    private String getStringResponse(int colorDraw) {
        return getResources().getString(colorDraw);
    }

    @Override
    public void onWebServiceError(String result, String url) {
        customToast.showCustomToast(svContext, result, customToast.ToastyError);
    }


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            CloseDrawer();
        } else {
            super.onBackPressed();
        }
    }

    private void initMenuData() {
        String[] documentsMenuItems = new String[lstCategoryData.size()];
        for (int i = 0; i < lstCategoryData.size(); i++) {
            documentsMenuItems[i] = lstCategoryData.get(i).getTitle();
        }
        strMenuItemMaster = new String[]{
                "AEPS",
                "DMT",
                "Members",
                "Profile",
                "Wallet",
                "Reports",
                "Commission",
                "Xpress Payout",
                "Virtual Account",
                "Support Ticket",
                "View Complaint",
                "Refer and Earn",
                "Privacy Policy",
        };

        strMenuChildItemMaster = new String[][]{
                {"Balance Enquiry", "Mini Statement", "Withdrawal", "Aadhar Pay", "AEPS History"},
                {"Register", "Login", "Transfer Report"},
                {"Add Member", "View All Member", "Distributor", "Retailer"},
                {"Personal Profile", "Change Password"},
                //{UPI_ADD_MONEY, "R-Wallet Transfer", "E-Wallet Transfer", "R-Wallet History", "E-Wallet History", R_WALLET_MYFUNDREQUEST, E_WALLET_MYFUNDREQUEST},
                new String[0],
                {"Recharge History", "Bill Pay History", "BBPS History", "Money Transfer History", "Recharge Commission history","Fund Transfer Commission","AEPS Commission Report"},
                {"Recharge Commission", "Bill Pay Commission", "BBPS Commission" ,"DMR Commission","Account Verify Charge","Aadhar eKyc Charge","UTI Pancard Charge", "AEPS Commission"},
                {"Transfer", "Payout Report"},
                {"My Virtual Account", "Transaction Report"},
                {"Create Ticket", "View Ticket"},
                new String[0],
                new String[0],
                new String[0]};


        strMenuItemDistributor = new String[]{
                "AEPS",
                "DMT",
                "Members",
                "Profile",
                "Wallet",
                "Reports",
                "Commission",
                "Xpress Payout",
                "Virtual Account",
                "Support Ticket",
                "View Complaint",
                "Refer and Earn",
                "Privacy Policy",
        };

        strMenuChildItemDistributor = new String[][]{
                {"Balance Enquiry", "Mini Statement", "Withdrawal", "Aadhar Pay", "AEPS History"},
                {"Register", "Login", "Transfer Report"},
                //{"Add Member", "View All Member", "Distributor", "Retailer"},
                {"Add Member", "View All Member", "Retailer"},
                {"Personal Profile", "Change Password"},
                //{UPI_ADD_MONEY, "R-Wallet Transfer", "E-Wallet Transfer", "R-Wallet History", "E-Wallet History", R_WALLET_MYFUNDREQUEST, E_WALLET_MYFUNDREQUEST},
                new String[0],
                {"Recharge History", "Bill Pay History", "BBPS History", "Money Transfer History", "Recharge Commission history"},
                {"Recharge Commission", "Bill Pay Commission", "DMR Commission", "AEPS Commission"},
                {"Transfer", "Payout Report"},
                {"My Virtual Account", "Transaction Report"},
                {"Create Ticket", "View Ticket"},
                new String[0],
                new String[0],
                new String[0]
        };

        strMenuItemRetailor = new String[]{
                "AEPS",
                "Profile",
                "Wallet",
                "Reports",
                "Commission",
                "Xpress Payout",
                "Virtual Account",
                "Support Ticket",
                "View Complaint",
                "Refer and Earn",
                "Privacy Policy",
        };

        strMenuChildItemRetailor = new String[][]{
                {"Balance Enquiry", "Mini Statement", "Withdrawal", "Aadhar Pay", "AEPS History"},
                {"Personal Profile", "Change Password"},
                //{UPI_ADD_MONEY, "R-Wallet Transfer", "E-Wallet Transfer", "R-Wallet History", "E-Wallet History", R_WALLET_MYFUNDREQUEST, E_WALLET_MYFUNDREQUEST},
                new String[0],
                {"Recharge History", "Bill Pay History", "BBPS History", "Money Transfer History", "Recharge Commission history"},
                {"Recharge Commission", "Bill Pay Commission", "DMR Commission", "AEPS Commission"},
                {"Transfer", "Payout Report"},
                {"My Virtual Account", "Transaction Report"},
                {"Create Ticket", "View Ticket"},
                new String[0],
                new String[0],
                new String[0]
        };
    }

    private void prepareMenuData(String usertype) {
        String strVendorStatus = PreferenceConnector.readString(svContext, PreferenceConnector.VENDOR_STATUS, "0");
        LinkedHashMap<String, String[]> HashMap = new LinkedHashMap<String, String[]>();
        // Master Distributor
        if (usertype.equalsIgnoreCase("3")) {
            strMenuItem = new String[strMenuItemMaster.length];
            strMenuChildItem = new String[strMenuChildItemMaster.length][];
            for (int j = 0; j < strMenuItemMaster.length; j++) {
                switch (strMenuItemMaster[j]) {
                    case "AEPS":
                        if (PreferenceConnector.readBoolean(svContext, PreferenceConnector.ISAEPSACTIVE, false)) {
                            HashMap.put(strMenuItemMaster[j], strMenuChildItemMaster[j]);
                            strMenuItem[j] = strMenuItemMaster[j];
                            strMenuChildItem[j] = strMenuChildItemMaster[j];
                        }
                        Log.e(TAG, strMenuItemMaster[j] + " : " + PreferenceConnector.readBoolean(svContext, PreferenceConnector.ISAEPSACTIVE, false));
                        break;
                    case "Xpress Payout":
                        if (PreferenceConnector.readBoolean(svContext, PreferenceConnector.ISXPRESSPAYOUTACTIVE, false)) {
                            HashMap.put(strMenuItemMaster[j], strMenuChildItemMaster[j]);
                            strMenuItem[j] = strMenuItemMaster[j];
                            strMenuChildItem[j] = strMenuChildItemMaster[j];
                        }
                        Log.e(TAG, strMenuItemMaster[j] + " : " + PreferenceConnector.readBoolean(svContext, PreferenceConnector.ISXPRESSPAYOUTACTIVE, false));
                        break;
                    case "DMT":
                        if (PreferenceConnector.readBoolean(svContext, PreferenceConnector.ISDMTACTIVE, false)) {
                            HashMap.put(strMenuItemMaster[j], strMenuChildItemMaster[j]);
                            strMenuItem[j] = strMenuItemMaster[j];
                            strMenuChildItem[j] = strMenuChildItemMaster[j];
                        }
                        break;
                    case "Virtual Account":
                        if (PreferenceConnector.readBoolean(svContext, PreferenceConnector.ISVIRTUALACCOUNT, false)) {
                            HashMap.put(strMenuItemMaster[j], strMenuChildItemMaster[j]);
                            strMenuItem[j] = strMenuItemMaster[j];
                            strMenuChildItem[j] = strMenuChildItemMaster[j];
                        }
                        break;
                    default:
                        HashMap.put(strMenuItemMaster[j], strMenuChildItemMaster[j]);
                        strMenuItem[j] = strMenuItemMaster[j];
                        strMenuChildItem[j] = strMenuChildItemMaster[j];
                        break;
                }
//                if (strMenuItemMaster[j].equals("Recharge")) {
//                    if (PreferenceConnector.readBoolean(svContext, PreferenceConnector.ISRECHARGEACTIVE, false)) {
//                        HashMap.put(strMenuItemMaster[j], strMenuChildItemMaster[j]);
//                        strMenuItem[j] = strMenuItemMaster[j];
//                        strMenuChildItem[j] = strMenuChildItemMaster[j];
//                    }
//                } else {
//                    HashMap.put(strMenuItemMaster[j], strMenuChildItemMaster[j]);
//                    strMenuItem[j] = strMenuItemMaster[j];
//                    strMenuChildItem[j] = strMenuChildItemMaster[j];
//                }
            }

        }
        // Distributor
        else if (usertype.equalsIgnoreCase("4")) {
            strMenuItem = new String[strMenuItemDistributor.length];
            strMenuChildItem = new String[strMenuChildItemDistributor.length][];
            for (int j = 0; j < strMenuItemDistributor.length; j++) {
                switch (strMenuItemDistributor[j]) {
                    case "AEPS":
                        if (PreferenceConnector.readBoolean(svContext, PreferenceConnector.ISAEPSACTIVE, false)) {
                            HashMap.put(strMenuItemDistributor[j], strMenuChildItemDistributor[j]);
                            strMenuItem[j] = strMenuItemDistributor[j];
                            strMenuChildItem[j] = strMenuChildItemDistributor[j];
                        }
                        Log.e(TAG, strMenuItemDistributor[j] + " : " + PreferenceConnector.readBoolean(svContext, PreferenceConnector.ISAEPSACTIVE, false));
                        break;
                    case "Xpress Payout":
                        if (PreferenceConnector.readBoolean(svContext, PreferenceConnector.ISXPRESSPAYOUTACTIVE, false)) {
                            HashMap.put(strMenuItemDistributor[j], strMenuChildItemDistributor[j]);
                            strMenuItem[j] = strMenuItemDistributor[j];
                            strMenuChildItem[j] = strMenuChildItemDistributor[j];
                        }
                        Log.e(TAG, strMenuItemDistributor[j] + " : " + PreferenceConnector.readBoolean(svContext, PreferenceConnector.ISXPRESSPAYOUTACTIVE, false));
                        break;
                    case "DMT":
                        if (PreferenceConnector.readBoolean(svContext, PreferenceConnector.ISDMTACTIVE, false)) {
                            HashMap.put(strMenuItemDistributor[j], strMenuChildItemDistributor[j]);
                            strMenuItem[j] = strMenuItemDistributor[j];
                            strMenuChildItem[j] = strMenuChildItemDistributor[j];
                        }
                        break;
                    case "Virtual Account":
                        if (PreferenceConnector.readBoolean(svContext, PreferenceConnector.ISVIRTUALACCOUNT, false)) {
                            HashMap.put(strMenuItemDistributor[j], strMenuChildItemDistributor[j]);
                            strMenuItem[j] = strMenuItemDistributor[j];
                            strMenuChildItem[j] = strMenuChildItemDistributor[j];
                        }
                        break;
                    default:
                        HashMap.put(strMenuItemDistributor[j], strMenuChildItemDistributor[j]);
                        strMenuItem[j] = strMenuItemDistributor[j];
                        strMenuChildItem[j] = strMenuChildItemDistributor[j];
                        break;
                }
            }
        }
        // Retailer or user
        else if (usertype.equalsIgnoreCase("5") || usertype.equalsIgnoreCase("8")) {
            strMenuItem = new String[strMenuItemRetailor.length];
            strMenuChildItem = new String[strMenuChildItemRetailor.length][];
            for (int j = 0; j < strMenuItemRetailor.length; j++) {
                switch (strMenuItemRetailor[j]) {
                    case "AEPS":
                        if (PreferenceConnector.readBoolean(svContext, PreferenceConnector.ISAEPSACTIVE, false)) {
                            HashMap.put(strMenuItemRetailor[j], strMenuChildItemRetailor[j]);
                            strMenuItem[j] = strMenuItemRetailor[j];
                            strMenuChildItem[j] = strMenuChildItemRetailor[j];
                        }
                        Log.e(TAG, strMenuItemRetailor[j] + " : " + PreferenceConnector.readBoolean(svContext, PreferenceConnector.ISAEPSACTIVE, false));
                        break;
                    case "Xpress Payout":
                        if (PreferenceConnector.readBoolean(svContext, PreferenceConnector.ISXPRESSPAYOUTACTIVE, false)) {
                            HashMap.put(strMenuItemRetailor[j], strMenuChildItemRetailor[j]);
                            strMenuItem[j] = strMenuItemRetailor[j];
                            strMenuChildItem[j] = strMenuChildItemRetailor[j];
                        }
                        Log.e(TAG, strMenuItemRetailor[j] + " : " + PreferenceConnector.readBoolean(svContext, PreferenceConnector.ISXPRESSPAYOUTACTIVE, false));
                        break;
                    case "DMT":
                        if (PreferenceConnector.readBoolean(svContext, PreferenceConnector.ISDMTACTIVE, false)) {
                            HashMap.put(strMenuItemRetailor[j], strMenuChildItemRetailor[j]);
                            strMenuItem[j] = strMenuItemRetailor[j];
                            strMenuChildItem[j] = strMenuChildItemRetailor[j];
                        }
                        break;
                    case "Virtual Account":
                        if (PreferenceConnector.readBoolean(svContext, PreferenceConnector.ISVIRTUALACCOUNT, false)) {
                            HashMap.put(strMenuItemRetailor[j], strMenuChildItemRetailor[j]);
                            strMenuItem[j] = strMenuItemRetailor[j];
                            strMenuChildItem[j] = strMenuChildItemRetailor[j];
                        }
                        break;
                    default:
                        HashMap.put(strMenuItemRetailor[j], strMenuChildItemRetailor[j]);
                        strMenuItem[j] = strMenuItemRetailor[j];
                        strMenuChildItem[j] = strMenuChildItemRetailor[j];
                        break;
                }
            }
        }

        headerList = new ArrayList<>();
        childList = new HashMap<>();
        for (Map.Entry map : HashMap.entrySet()) {
            System.out.println(map.getKey() + " " + map.getValue());
            String[] strChild = (String[]) map.getValue();

            MenuModel menuModel = new MenuModel(map.getKey().toString(), true, true, strChild);
            headerList.add(menuModel);

            List<MenuModel> childModelsList = new ArrayList<>();
            for (int j = 0; j < strChild.length; j++) {
                MenuModel childModel = new MenuModel(strChild[j], false, false, strMenuItem);
                childModelsList.add(childModel);
                childList.put(menuModel, childModelsList);
            }
        }
    }

    private void populateExpandableList(String usertype) {
        int[] textureArrayWin = {
                R.drawable.ic_menu_aeps,
                R.drawable.ic_menu_money_transfer,
                R.drawable.ic_menu_members,
                R.drawable.ic_menu_profile,
                R.drawable.ic_menu_wallet,
                R.drawable.ic_menu_report,
                R.drawable.ic_menu_commission,
                R.drawable.ic_menu_virtual_account,
                R.drawable.ic_menu_ticket,
                R.drawable.ic_menu_complaint,
                R.drawable.ic_menu_refer_earn,
                R.drawable.ic_menu_aeps,
        };

        int[] textureArrayxpress = {
                R.drawable.ic_menu_aeps,
                R.drawable.ic_menu_money_transfer,
                R.drawable.ic_menu_members,
                R.drawable.ic_menu_profile,
                R.drawable.ic_menu_wallet,
                R.drawable.ic_menu_report,
                R.drawable.ic_menu_commission,
                R.drawable.ic_menu_virtual_account,
                R.drawable.ic_menu_virtual_account,
                R.drawable.ic_menu_ticket,
                R.drawable.ic_menu_complaint,
                R.drawable.ic_menu_refer_earn,
                R.drawable.ic_menu_aeps,
        };

        int[] textureArrayretailers = {
                R.drawable.ic_menu_aeps,
                R.drawable.ic_menu_profile,
                R.drawable.ic_menu_wallet,
                R.drawable.ic_menu_report,
                R.drawable.ic_menu_commission,
                R.drawable.ic_menu_virtual_account,
                R.drawable.ic_menu_virtual_account,
                R.drawable.ic_menu_ticket,
                R.drawable.ic_menu_complaint,
                R.drawable.ic_menu_refer_earn,
                R.drawable.ic_menu_aeps,
        };


        if (usertype.equals("3") || usertype.equals("4")) {
            if (PreferenceConnector.readBoolean(svContext, PreferenceConnector.ISXPRESSPAYOUTACTIVE, false)) {
                expandableListAdapter = new ExpandableListAdapter(this, headerList, childList, textureArrayxpress);
            } else {
                expandableListAdapter = new ExpandableListAdapter(this, headerList, childList, textureArrayWin);
            }
        } else if (usertype.equals("5") || usertype.equals("8")) {
            expandableListAdapter = new ExpandableListAdapter(this, headerList, childList, textureArrayretailers);
        }

        expandableListView.setAdapter(expandableListAdapter);

        expandableListView.setOnGroupClickListener((parent, v, groupPosition, id) -> {
            if ((headerList.get(groupPosition).strMenuChildItem).length < 1) {
                onDrawerItemClick(headerList.get(groupPosition).menuName, svContext,"");
                CloseDrawer();
            }
            return false;
        });

        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int previousGroup = -1;
            boolean isExpanded = false;

            @Override
            public void onGroupExpand(int groupPosition) {
                if (groupPosition != previousGroup && isExpanded) {
                    expandableListView.collapseGroup(previousGroup);
                }
                previousGroup = groupPosition;
                isExpanded = true;
            }
        });

        expandableListView.setOnChildClickListener((parent, v, groupPosition, childPosition, id) -> {
            MenuModel model = childList.get(headerList.get(groupPosition)).get(childPosition);
            onDrawerItemClick(model.menuName, svContext,"");
            CloseDrawer();
            return false;
        });
    }

    public Fragment getFragment() {
        return getSupportFragmentManager().findFragmentById(R.id.container);
    }

    public static void onDrawerItemClick(String title, Context svContext,String type) {
        ShowCustomToast customToast = new ShowCustomToast(svContext);
        Intent svIntent;

        if (("Prepaid").equalsIgnoreCase(title)) {
            svIntent = new Intent(svContext, ActivityRecharge.class);
            svIntent.putExtra("selecteditem", 0);
            svContext.startActivity(svIntent);
        } else if (("Postpaid").equalsIgnoreCase(title)) {
            svIntent = new Intent(svContext, ActivityRecharge.class);
            svIntent.putExtra("selecteditem", 1);
            svContext.startActivity(svIntent);
        } else if (("DTH").equalsIgnoreCase(title)) {
            svIntent = new Intent(svContext, ActivityRecharge.class);
            svIntent.putExtra("selecteditem", 2);
            svContext.startActivity(svIntent);
        } else if (("Balance Enquiry").equalsIgnoreCase(title) || ("Mini Statement").equalsIgnoreCase(title) ||
                ("Withdrawal").equalsIgnoreCase(title) || ("Aadhar Pay").equalsIgnoreCase(title)) {
            ActivityMain.OpenAeps(title, svContext, customToast);
        } else if (("AEPS History").equalsIgnoreCase(title)) {
            svIntent = new Intent(svContext, ActivityFinoAEPSHistory.class);
            svContext.startActivity(svIntent);
        } else if (("Register").equalsIgnoreCase(title)) {
            svIntent = new Intent(svContext, ActivityRegisterTransfer.class);
            svIntent.putExtra("selecteditem", 1);
            svContext.startActivity(svIntent);
        } else if (("Login").equalsIgnoreCase(title)) {
            svIntent = new Intent(svContext, ActivityLoginTransfer.class);
            svIntent.putExtra("selecteditem", 1);
            svContext.startActivity(svIntent);
        } else if (("Transfer Report").equalsIgnoreCase(title)) {
            svIntent = new Intent(svContext, ActivityMoneyTransferHistory.class);
            svIntent.putExtra("selecteditem", 1);
            svContext.startActivity(svIntent);
        } else if (("Add Member").equalsIgnoreCase(title)) {
            svIntent = new Intent(svContext, AddEditMember.class);
            svContext.startActivity(svIntent);
        } else if (("View All Member").equalsIgnoreCase(title)) {
            svIntent = new Intent(svContext, AllMemberList.class);
            if(PreferenceConnector.readString(svContext, PreferenceConnector.LOGINUSERTYPE, "").equals("3")) {
                svIntent.putExtra("role_id", 3);
            } else if(PreferenceConnector.readString(svContext, PreferenceConnector.LOGINUSERTYPE, "").equals("4")) {
                svIntent.putExtra("role_id", 5);
            }
            svContext.startActivity(svIntent);
        } else if (("Distributor").equalsIgnoreCase(title)) {
            svIntent = new Intent(svContext, AllMemberList.class);
            svIntent.putExtra("role_id", 4);
            svContext.startActivity(svIntent);
        }else if (("Retailer").equalsIgnoreCase(title)) {
            svIntent = new Intent(svContext, AllMemberList.class);
            svIntent.putExtra("role_id", 5);
            svContext.startActivity(svIntent);
        }else if (("Personal Profile").equalsIgnoreCase(title)) {
            svIntent = new Intent(svContext, ActivityProfile.class);
            svContext.startActivity(svIntent);
        } else if (("Change Password").equalsIgnoreCase(title)) {
            svIntent = new Intent(svContext, ActivityChangePassword.class);
            svContext.startActivity(svIntent);
        } else if (title.equalsIgnoreCase("Wallet")) {
                svIntent = new Intent(svContext, ActivityWallet.class);
                svContext.startActivity(svIntent);
        }else if (title.equalsIgnoreCase(UPI_ADD_MONEY)) {
            if (PreferenceConnector.readBoolean(svContext, PreferenceConnector.ISRAZORPAYACTIVE, false)) {
                svIntent = new Intent(svContext, ActivityTopupWallet.class);
                svContext.startActivity(svIntent);
            } else {
                ActivityAddFundRequest.OpenAddFundRequest(svContext,type);
            }
        } else if (title.equalsIgnoreCase("R-Wallet Transfer")) {
            svIntent = new Intent(svContext, ActivityWalletTransfer.class);
            svContext.startActivity(svIntent);
        } else if (title.equalsIgnoreCase("E-Wallet Transfer")) {
            svIntent = new Intent(svContext, ActivityEWalletTransfer.class);
            svContext.startActivity(svIntent);
        } else if (title.equalsIgnoreCase("R-Wallet History")) {
            svIntent = new Intent(svContext, com.rechargeapp.activity.wallet.ActivityWalletHistory.class);
            svContext.startActivity(svIntent);
        } else if (title.equalsIgnoreCase("E-Wallet History")) {
            svIntent = new Intent(svContext, ActivityEWalletHistory.class);
            svContext.startActivity(svIntent);
        } else if (title.equals(R_WALLET_MYFUNDREQUEST) || title.equals(E_WALLET_MYFUNDREQUEST)) {
            svIntent = new Intent(svContext, ActivityAddFundRequestHistory.class);
            svIntent.putExtra("actType", title);
            svContext.startActivity(svIntent);
        } else if (("Recharge History").equalsIgnoreCase(title)) {
            svIntent = new Intent(svContext, ActivityRechargeHistory.class);
            svIntent.putExtra("selecteditem", 2);
            svContext.startActivity(svIntent);
        } else if (("Bill Pay History").equalsIgnoreCase(title)) {
            svIntent = new Intent(svContext, ActivityBillPayHistory.class);
            svContext.startActivity(svIntent);
        } else if (("BBPS History").equalsIgnoreCase(title)) {
            svIntent = new Intent(svContext, ActivityBbpsHistory.class);
            svContext.startActivity(svIntent);
        } else if (("Money Transfer History").equalsIgnoreCase(title)) {
            svIntent = new Intent(svContext, ActivityMoneyTransferHistory.class);
            svContext.startActivity(svIntent);
        } else if (title.equalsIgnoreCase("Recharge Commission History")) {
            svIntent = new Intent(svContext, ActivityRechargeCommisionHistory.class);
            svContext.startActivity(svIntent);
        } else if (title.equalsIgnoreCase("Fund Transfer Commission")) {
            svIntent = new Intent(svContext, ActivityFundTransferCommisionHistory.class);
            svContext.startActivity(svIntent);
        }else if (title.equalsIgnoreCase("AEPS Commission Report")) {
            svIntent = new Intent(svContext, ActivityAEPSCommisionHistory.class);
            svContext.startActivity(svIntent);
        } else if (title.equalsIgnoreCase("Recharge Commission")) {
            svIntent = new Intent(svContext, ActivityRechargeCommision.class);
            svContext.startActivity(svIntent);
        } else if (("Bill Pay Commission").equalsIgnoreCase(title)) {
            svIntent = new Intent(svContext, ActivityBillPayCommision.class);
            svContext.startActivity(svIntent);
        } else if (("BBPS Commission").equalsIgnoreCase(title)) {
            svIntent = new Intent(svContext, ActivityBBPSCommision.class);
            svContext.startActivity(svIntent);
        }else if (("Aadhar eKyc Charge").equalsIgnoreCase(title)) {
            svIntent = new Intent(svContext, ActivityAadharKYCCommision.class);
            svContext.startActivity(svIntent);
        }else if (("UTI Pancard Charge").equalsIgnoreCase(title)) {
            svIntent = new Intent(svContext, ActivityPanCardCommision.class);
            svContext.startActivity(svIntent);
            //
        }else if (("DMR Commission").equalsIgnoreCase(title)) {
            svIntent = new Intent(svContext, ActivityDMRCommision.class);
            svContext.startActivity(svIntent);
        } else if (("Account Verify Charge").equalsIgnoreCase(title)) {
            svIntent = new Intent(svContext, ActivityAccountVerifyCommision.class);
            svContext.startActivity(svIntent);
        }else if (("AEPS Commission").equalsIgnoreCase(title)) {
            svIntent = new Intent(svContext, ActivityAEPSCommision.class);
            svContext.startActivity(svIntent);
        } else if (title.equalsIgnoreCase("Transfer")) {
            if (PreferenceConnector.readBoolean(svContext, PreferenceConnector.ISXPRESSPAYOUTACTIVE, false)) {
                //svIntent = new Intent(svContext, ActivityPayoutTransfer.class);
                //svContext.startActivity(svIntent);
            } else {
                customToast.showCustomToast(svContext, "Not active for you.", customToast.ToastyError);
            }
        } else if (title.equalsIgnoreCase("Payout Report")) {
            if (PreferenceConnector.readBoolean(svContext, PreferenceConnector.ISXPRESSPAYOUTACTIVE, false)) {
                //svIntent = new Intent(svContext, ActivityPayoutTransferReport.class);
                //svContext.startActivity(svIntent);
            } else {
                customToast.showCustomToast(svContext, "Not active for you.", customToast.ToastyError);
            }
        } else if (title.equalsIgnoreCase("My Virtual Account")) {
            if (PreferenceConnector.readBoolean(svContext, PreferenceConnector.ISVIRTUALACCOUNT, false)) {
                svIntent = new Intent(svContext, ActivityVirtualAccount.class);
                svContext.startActivity(svIntent);
            } else {
                customToast.showCustomToast(svContext, "Not active for you.", customToast.ToastyError);
            }
        } else if (title.equalsIgnoreCase("Transaction Report")) {
            if (PreferenceConnector.readBoolean(svContext, PreferenceConnector.ISVIRTUALACCOUNT, false)) {
                svIntent = new Intent(svContext, VirtualTransactionReport.class);
                svContext.startActivity(svIntent);
            } else {
                customToast.showCustomToast(svContext, "Not active for you.", customToast.ToastyError);
            }
        } else if (title.equalsIgnoreCase("Create Ticket")) {
            svIntent = new Intent(svContext, ActivityHelpFeedback.class);
            svContext.startActivity(svIntent);
        } else if (title.equalsIgnoreCase("View Ticket")) {
            svIntent = new Intent(svContext, ActivityTicketList.class);
            svContext.startActivity(svIntent);
        } else if (title.equalsIgnoreCase("View Complaint")) {
            svIntent = new Intent(svContext, ActivityComplaintList.class);
            svContext.startActivity(svIntent);
        } else if (title.equalsIgnoreCase("Refer and Earn") ||
                title.equalsIgnoreCase("Referral") ||
                title.equalsIgnoreCase("Refer & Earn")) {
            svIntent = new Intent(svContext, ActivityShare.class);
            svContext.startActivity(svIntent);
        } else if (("Electricity").equalsIgnoreCase(title)) {
            svIntent = new Intent(svContext, ActivityRecharge.class);
            svIntent.putExtra("selecteditem", 2);
            svContext.startActivity(svIntent);
        } else if (("Privacy Policy").equalsIgnoreCase(title)) {
            svIntent = new Intent(svContext, ActivityPrivacyPolicy.class);
            svIntent.putExtra("selecteditem", 2);
            svIntent.putExtra("actTitle", title);
            svContext.startActivity(svIntent);
        } else if (("Change T-Pin").equalsIgnoreCase(title)) {
            svIntent = new Intent(svContext, ActivityChangeTPassword.class);
            svContext.startActivity(svIntent);
        } else if (("Kyc").equalsIgnoreCase(title)) {
            svIntent = new Intent(svContext, ActivityKyc.class);
            svContext.startActivity(svIntent);
        } else if (title.equalsIgnoreCase("Add Fund PG")) {
            svIntent = new Intent(svContext, ActivityTopupWalletRazorpay.class);
            svContext.startActivity(svIntent);
        }  else if (title.equalsIgnoreCase("Wallet History")) {
            svIntent = new Intent(svContext, ActivityWalletHistory.class);
            svContext.startActivity(svIntent);
        } else if (("Commission Wallet History").equalsIgnoreCase(title)) {
            svIntent = new Intent(svContext, ActivityComisionWalletHistory.class);
            svContext.startActivity(svIntent);
        } else if (("AEPS Wallet History").equalsIgnoreCase(title)) {
            svIntent = new Intent(svContext, ActivityAepsWalletHistory.class);
            svContext.startActivity(svIntent);
        } else if (title.equalsIgnoreCase("Notification")) {
            svIntent = new Intent(svContext, ActivityNotification.class);
            svContext.startActivity(svIntent);
        } else if (title.equalsIgnoreCase("Contact Us")) {
            svIntent = new Intent(svContext, ActivityContact.class);
            svContext.startActivity(svIntent);
        } else if (title.equalsIgnoreCase("Logout")) {
            ShowConfirmLogout(svContext, "Logout", "Are you confirm?",
                    "Are you are ready to end your current session. You have to enter login detail again");
        }
    }


    private static void showRateDialog(Context svContext) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(svContext);
        builder.setTitle("Rate this app");
        builder.setMessage("If you enjoy using this app, please take a moment to rate it. Thanks for your support!");
        builder.setPositiveButton("Rate now", (dialogInterface, i) -> {
            try {
                ((Activity) svContext).startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + svContext.getPackageName())));
            } catch (ActivityNotFoundException e) {
                ((Activity) svContext).startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + svContext.getPackageName())));
            }
            dialogInterface.dismiss();
        });
        builder.setNegativeButton("Later", (dialogInterface, i) -> dialogInterface.dismiss());
        builder.show();
    }

    private static void ShowConfirmLogout(Context svContext, String head, String strTitle, String strDesc) {
        final Dialog dialog = new Dialog(svContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_header_twobutton);

        TextView textTitle = (TextView) dialog.findViewById(R.id.dialog_title);
        textTitle.setText(strTitle);
        TextView textDesc = (TextView) dialog.findViewById(R.id.dialog_desc);
        textDesc.setText(strDesc);
        TextView textHead = (TextView) dialog.findViewById(R.id.dialog_head);
        textHead.setText(head);

        Button declineDialogButton = (Button) dialog.findViewById(R.id.bt_decline);
        declineDialogButton.setOnClickListener(v -> dialog.dismiss());

        Button confirmDialogButton = (Button) dialog.findViewById(R.id.bt_confirm);
        confirmDialogButton.setOnClickListener(v -> LogOut(svContext));

        dialog.show();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        CloseDrawer();
        return true;
    }

    private void CloseDrawer() {
        drawer.closeDrawer(GravityCompat.START);
    }

    private void OpenDrawer() {
        drawer.openDrawer(GravityCompat.START);
    }


    /**
     * Request For Permissions
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == APP_UPDATE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                if (appUpdateManager == null) {
                    appUpdateManager = AppUpdateManagerFactory.create(svContext);
                }
                appUpdateManager.completeUpdate();
            } else {
                // If the update is cancelled or fails, restart the update again.
                checkInAppUpdate();
            }
        }
    }

    /**
     * App Update Flow Process
     */
    private void checkInAppUpdate() {
        viewUpdate = (View) findViewById(R.id.lay_update);
        viewUpdate.setVisibility(View.GONE);

        appUpdateManager = AppUpdateManagerFactory.create(svContext);
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();

        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
            PreferenceConnector.writeBoolean(svContext, PreferenceConnector.PACKAGE_DOWNLOADED, false);
            switch (appUpdateInfo.updateAvailability()) {
                case UpdateAvailability.UPDATE_AVAILABLE:
                    if (appUpdateInfo.isUpdateTypeAllowed(UPDATE_TYPE)) {
                        Log.d(TAG, "Update available");
                        startAppUpdateFlow(appUpdateInfo);
                    } else {
                        Log.d(TAG, "Update available but update type not allowed.");
                    }
                    break;
                case UpdateAvailability.UPDATE_NOT_AVAILABLE:
                    Log.d(TAG, "Update Not Available");
                    break;
                case UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS:
                    Log.d(TAG, "Update In Progress.");
                    break;
                case UpdateAvailability.UNKNOWN:
                    Log.d(TAG, "Error In Update.");
                    break;
            }
        });
    }

    private void startAppUpdateFlow(AppUpdateInfo appUpdateInfo) {
        if (UPDATE_TYPE == AppUpdateType.FLEXIBLE) {
            appUpdateManager.registerListener(updatedListener);
        }

        try {
            appUpdateManager.startUpdateFlowForResult(
                    appUpdateInfo,
                    ActivityMain.this,
                    AppUpdateOptions.newBuilder(UPDATE_TYPE)
                            .setAllowAssetPackDeletion(true)
                            .build(),
                    APP_UPDATE_REQUEST_CODE);
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
        } finally {
            if (UPDATE_TYPE == AppUpdateType.FLEXIBLE) {
                appUpdateManager.unregisterListener(updatedListener);
            }
        }
    }

    private InstallStateUpdatedListener updatedListener = new InstallStateUpdatedListener() {
        @Override
        public void onStateUpdate(@NonNull InstallState installState) {
            switch (installState.installStatus()) {
                case InstallStatus.DOWNLOADED:
                    // After the update is downloaded, show a notification and request user confirmation to restart the app.
                    Log.d(TAG, "An update has been downloaded");
                    PreferenceConnector.writeBoolean(svContext, PreferenceConnector.PACKAGE_DOWNLOADED, true);
                    viewUpdate.setVisibility(View.VISIBLE);
                    findViewById(R.id.btn_update).setOnClickListener(view -> appUpdateManager.completeUpdate());
                    appUpdateManager.completeUpdate();
                    break;
                case InstallStatus.DOWNLOADING:
                    Log.d(TAG, "An update downloading");
                    long bytesDownloaded = installState.bytesDownloaded();
                    long totalBytesToDownload = installState.totalBytesToDownload();
                    // Implement progress bar.
                    break;
                case InstallStatus.CANCELED:
                case InstallStatus.FAILED:
                case InstallStatus.UNKNOWN:
                    Log.d(TAG, "An update failed");
                    break;
                case InstallStatus.INSTALLING:
                case InstallStatus.INSTALLED:
                case InstallStatus.REQUIRES_UI_INTENT:
                case InstallStatus.PENDING:
                    Log.d(TAG, "An update pending");
                    break;
            }
        }
    };

    /**
     * Firebase Notification Token
     */
    private void initFirebaseNotifyToken() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String token = task.getResult();
                        PreferenceConnector.writeString(svContext, PreferenceConnector.FCMID, token);
                        if (GlobalVariables.ISTESTING)
                            Log.d(TAG, "Success FCM Token.\n" + token);
                    } else {
                        if (GlobalVariables.ISTESTING)
                            Log.d(TAG, "Fetching FCM registration token failed!!\n" + task.getException());
                    }
                });
    }

    public static void openWebPage(Context context, String urlString) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlString));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setPackage("com.android.chrome");
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            // Chrome browser presumably not installed so allow user to choose instead
            intent.setPackage(null);
            context.startActivity(intent);
        }
    }

    public static void OpenAeps(String itemName, Context svContext, ShowCustomToast customToast) {
        if (PreferenceConnector.readBoolean(svContext, PreferenceConnector.ISAEPSACTIVE, false)) {
            if (PreferenceConnector.readBoolean(svContext, PreferenceConnector.ISAEPSKYCDONE, false)) {
                Intent svIntent = new Intent(svContext, ActivityFinoAEPSBankingService.class);
                svIntent.putExtra("selecteditem", itemName);
                svContext.startActivity(svIntent);
            } else {
                //Intent svIntent = new Intent(svContext, ActivityAEPSKyc.class);
                //svContext.startActivity(svIntent);
            }
        } else {

            customToast.showCustomToast(svContext, "AEPS Not active. Contact Admin", customToast.ToastyInfo);
        }
    }

    public boolean checkEWalletAndAddWallet(Float rechargeAmount, String actRefer, Context svContext, List<EditText> lstEditext, String strOperatorCode, String amount, String serviceId, ShowCustomToast customToast) {
        actReferTo = actRefer;
        boolean isWalletToLoad = false;
        float requiredAmountToAdd = 0;
        float walletAmount = Float.parseFloat(PreferenceConnector.readString(svContext, PreferenceConnector.EWALLETBAL, ""));
        if (rechargeAmount > walletAmount) {
            requiredAmountToAdd = rechargeAmount - walletAmount;
            isWalletToLoad = true;
        } else {
            requiredAmountToAdd = 0;
            isWalletToLoad = false;
        }

        if (isWalletToLoad) {
            if (PreferenceConnector.readBoolean(svContext, PreferenceConnector.ISRAZORPAYACTIVE, false)) {
                startPayment(requiredAmountToAdd, svContext);
            } else {
                ProcessRecharge(svContext,lstEditext,strOperatorCode,amount,serviceId,customToast);

            }
        } else {

        }
        return isWalletToLoad;
    }


    private void ProcessRecharge(Context svContext,List<EditText> lstEditext,String strOperatorCode,String amount,String serviceId, ShowCustomToast customToast) {
        if (actReferTo.equalsIgnoreCase("electric")) {
            ActivityBbpsElectricity frag = new ActivityBbpsElectricity();
            frag.RechargeProcess(svContext,lstEditext,strOperatorCode,amount,customToast);


        } else if (actReferTo.equalsIgnoreCase("Toll")) {
            ActivityBbpsTollTax frag = new ActivityBbpsTollTax();
            frag.RechargeProcess(svContext,lstEditext,strOperatorCode,amount,customToast);

        } else if (actReferTo.equalsIgnoreCase("Service")) {
            ActivityBbpsAllServices frag = new ActivityBbpsAllServices();
            frag.RechargeProcess(svContext,lstEditext,strOperatorCode,amount,serviceId,customToast);

        }

    }

    public void startPayment(float strAmount, Context svContext) {
        ShowCustomToast cToast = new ShowCustomToast(svContext);
        addAmountToWallet = strAmount + "";
        final Activity activity = ((Activity) svContext);
        String strRazorPayId = PreferenceConnector.readString(svContext, PreferenceConnector.RAZORPAYID, "");
        final Checkout co = new Checkout();
        co.setKeyID(strRazorPayId);
        if (strRazorPayId.length() == 0) {
            cToast.showCustomToast(svContext, "Razorpay Key Not Available", cToast.ToastyError);
        } else {
            try {
                JSONObject options = new JSONObject();
                options.put("name", activity.getResources().getString(R.string.app_name));
                options.put("description", "Add amount to wallet");
                //You can omit the image option to fetch the image from dashboard
                options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
                options.put("currency", "INR");
                options.put("amount", (strAmount * 100) + "");

                JSONObject preFill = new JSONObject();
                preFill.put("email", PreferenceConnector.readString(activity, PreferenceConnector.LOGINUSEREMAIL, ""));
                preFill.put("contact", PreferenceConnector.readString(activity, PreferenceConnector.LOGINUSERPHONE, ""));

                options.put("prefill", preFill);

                co.open(activity, options);
            } catch (Exception e) {
                cToast.showCustomToast(svContext, "Error in payment: " + e.getMessage(), cToast.ToastyError);
                e.printStackTrace();
            }
        }
    }
}