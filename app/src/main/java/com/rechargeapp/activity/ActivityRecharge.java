package com.rechargeapp.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.commonutility.retrofit.ApiInterface;
import com.commonutility.spinner.ActivitySpinner;
import com.commonutility.spinner.SpinnerModel;
import com.codunite.rechargeapp.R;
import com.rechargeapp.adapter.ContactAdapter;
import com.rechargeapp.adapter.RecentRechargeHistoryAdapter;
import com.rechargeapp.fragment.FragMtransfer;
import com.rechargeapp.model.ContactModel;
import com.rechargeapp.fragment.FragDatacard;
import com.rechargeapp.fragment.FragDth;
import com.rechargeapp.fragment.FragLandline;
import com.rechargeapp.fragment.FragNew;
import com.rechargeapp.fragment.FragPrePostRecharge;
import com.rechargeapp.model.RecentRechargeHistoryModel;
import com.commonutility.AppExecutors;
import com.commonutility.GlobalVariables;
import com.commonutility.ItemAnimation;
import com.commonutility.NoInternetScreen;
import com.commonutility.PreferenceConnector;
import com.commonutility.ShowCustomToast;
import com.commonutility.WebService;
import com.commonutility.WebServiceListener;
import com.commonutility.customfont.FontUtils;
import com.rechargeapp.utility.CustomViewPager;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.tabs.TabLayout;
import com.razorpay.Checkout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import ir.androidexception.andexalertdialog.AndExAlertDialog;
import ir.androidexception.andexalertdialog.AndExAlertDialogListener;
import ir.androidexception.andexalertdialog.Font;

public class ActivityRecharge extends AppCompatActivity implements View.OnClickListener, WebServiceListener{
    private ImageView imgBack;
    private final EditText[] edTexts = {};
    private final String[] edTextsError = {"Enter phone number"};
    private final int[] editTextsClickId = {};

    private View[] allViewWithClick = {imgBack};
    private int[] allViewWithClickId = {R.id.img_back};


    private int pageNumber = 1;
    private TabLayout tabLayout;
    private CustomViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    public String[] selectedItem = {"Mobile", "DTH", "Datacard","LandLine"};
    private String addAmountToWallet;
    TextView tv_heading;

    public static final int FRAG_RECHARGE_CIRCLE_REQUEST_CODE = 435;
    public static final int FRAG_RECHARGE_OPERATOR_REQUEST_CODE = 436;
    public static final int FRAG_DTH_CIRCLE_REQUEST_CODE = 437;
    public static final int FRAG_DTH_OPERATOR_REQUEST_CODE = 438;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_recharge);
        StartApp();

        OnClickCombineDeclare(allViewWithClick);
        EditTextDeclare(edTexts);
        resumeApp();
    }

    private RecyclerView wallethistoryrv;

    public void resumeApp() {
        wallethistoryrv = (RecyclerView) findViewById(R.id.history_rv);
        int pageIndex = getIntent().getIntExtra("selecteditem", 0);


        tv_heading = (TextView)findViewById(R.id.tv_heading);
        viewPager = (CustomViewPager) findViewById(R.id.viewPager);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        if(pageIndex==0)
            tv_heading.setText("Mobile Recharge");
        else if(pageIndex==1)
            tv_heading.setText("DTH Recharge");
        else if(pageIndex==2)
            tv_heading.setText("Data Card Recharge");
        else if(pageIndex==3)
            tv_heading.setText("Landline Bill Payment");
        viewPager.setPagingEnabled(false);
        //tabLayout.setupWithViewPager(viewPager);

        //tabLayout.setScrollPosition(pageIndex, 0f, true);
        viewPager.setCurrentItem(pageIndex);
        //viewPager.beginFakeDrag();
        viewPager.setHorizontalScrollBarEnabled(false);

//        if (pageIndex == 0) {
//            tabLayout.setScrollPosition(pageIndex, 0f, true);
//            viewPager.setCurrentItem(pageIndex);
//        }else {
//            tabLayout.setScrollPosition(pageIndex-1, 0f, true);
//            viewPager.setCurrentItem(pageIndex-1);
//        }

        bottomSheet = findViewById(R.id.bottom_sheet);
        mBehavior = BottomSheetBehavior.from(bottomSheet);

        pageNumber = 1;
        LoadRechargeHistory(pageIndex);

    }

    private void LoadRechargeHistory(int pageIndex) {
        lstUploadData = new LinkedList<>();
        lstUploadData.add(PreferenceConnector.readString(svContext, PreferenceConnector.LOGINEDUSERID, ""));
        if(pageIndex==0){
            lstUploadData.add("1");
        } else if(pageIndex==1){
            lstUploadData.add("2");
        }

        lstUploadData.add("0");
        //lstUploadData.add("" + pageNumber);
        callWebService(ApiInterface.RECENTRECHARGEHISTORY, lstUploadData);
    }

    public static String getLastMonthDate() {
        Calendar today = Calendar.getInstance();
        int date = today.get(Calendar.DATE);
        int month = today.get(Calendar.MONTH);
        int year = today.get(Calendar.YEAR);

        return year + "-" + (month >= 10 ? month : "0" + month)
                + "-" + (date >= 10 ? date : "0" + date);
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
                        case R.id.img_back:
                            onBackPressed();
                            break;
                    }
                }
            });
        }
    }

    private Context svContext;
    private ShowCustomToast customToast;
    
    

    private void StartApp() {
        svContext = this;
        customToast = new ShowCustomToast(svContext);
        
        ViewGroup root = (ViewGroup) findViewById(R.id.headlayout);
        new NoInternetScreen(svContext, root, ActivityRecharge.this);
        if (!GlobalVariables.CUSTOMFONTNAME.equals("")) {
            Typeface font = Typeface.createFromAsset(getAssets(), GlobalVariables.CUSTOMFONTNAME);
            FontUtils.setFont(root, font);
        }
        hideKeyboard();
        loadToolBar();
    }

    private ImageView imgToolBarBack;

    private void loadToolBar() {
        imgToolBarBack = findViewById(R.id.img_back);
        imgToolBarBack.setOnClickListener(this);

        TextView toolbar_txt_walletbal = findViewById(R.id.toolbar_txt_walletbal);
        toolbar_txt_walletbal.setText(ActivityMain.ShowBalance(svContext));

        TextView toolbar_txt_ewalletbal = findViewById(R.id.toolbar_txt_ewalletbal);
        toolbar_txt_ewalletbal.setText(ActivityMain.ShoweBalance(svContext));
        LinearLayout imglogo = findViewById(R.id.img_dash_logo);

        LinearLayout imgToolBarWallet = findViewById(R.id.img_wallet);

        imgToolBarWallet.setOnClickListener(view -> ActivitySplash.OpenWalletActivity(svContext));

        LinearLayout imgToolBareWallet = findViewById(R.id.img_ewallet);
        imgToolBareWallet.setOnClickListener(view -> ActivitySplash.OpeneWalletActivity(svContext));
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.img_back) {
            finish();
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

    LinkedList<String> lstUploadData = new LinkedList<>();

    private void callWebService(String postUrl, LinkedList<String> lstUploadData) {
        WebService webService = new WebService(svContext, postUrl, lstUploadData, this);
        webService.LoadDataRetrofit(webService.callReturn());
    }

    public List<RecentRechargeHistoryModel> lstItems = new ArrayList<>();
    public static final String TAG_DATA = "data";
    public static final String TAG_AMOUNT = "amount";
    public static final String TAG_DATETIME = "datetime";
    public static final String TAG_DESCRIPTION = "description";
    public static final String TAG_ORDER_ID = "order_id";
    public static final String TAG_STATUS = "status";
    public static final String TAG_MESSAGE = "message";

    @Override
    public void onWebServiceActionComplete(String result, String url) {
        if (url.contains(ApiInterface.ADDWALLET)) {
            try {
                JSONObject json = new JSONObject(result);
                String str_status = json.getString("status");
                String str_msg = json.getString("message");
                if (str_status.equalsIgnoreCase("0")) {
                    customToast.showCustomToast(svContext, str_msg, customToast.ToastyError);
                } else {
                    customToast.showCustomToast(svContext, str_msg, customToast.ToastySuccess);
                    ProcessRecharge();
                }
            } catch (JSONException e) {
                customToast.showCustomToast(svContext, "Some error occured", customToast.ToastyError);
                e.printStackTrace();
            }
        } else if (url.contains(ApiInterface.RECENTRECHARGEHISTORY)) {
            try {
                lstItems = new ArrayList<>();

                JSONObject json = new JSONObject(result);
                String str_message = json.getString(TAG_MESSAGE);
                String str_json_status = json.getString(TAG_STATUS);
                if (str_json_status.equalsIgnoreCase("0")) {

                } else {
                    JSONArray data = json.getJSONArray(TAG_DATA);
                    for (int data_i = 0; data_i < (data).length(); data_i++) {
                        JSONObject data_obj = data.getJSONObject(data_i);
                        String str_amount = data_obj.getString("amount");
                        String str_datetime = data_obj.getString("datetime");
                        String str_status = data_obj.getString(TAG_STATUS);
                        String mobile = data_obj.getString("mobile");
                        String type = data_obj.getString("type");
                        String icon = data_obj.getString("icon");

                        lstItems.add(new RecentRechargeHistoryModel(type, mobile, str_amount, str_status, icon,str_datetime));
                    }


                    LinearLayoutManager layoutManager = new LinearLayoutManager(svContext, LinearLayoutManager.VERTICAL, false);
                    wallethistoryrv.setLayoutManager(layoutManager);
                    wallethistoryrv.setHasFixedSize(true);
                    int animation_type = ItemAnimation.LEFT_RIGHT;
                    RecentRechargeHistoryAdapter mAdapter = new RecentRechargeHistoryAdapter(svContext, lstItems, animation_type, true);
                    wallethistoryrv.setAdapter(mAdapter);
                }
            } catch (JSONException e) {


                customToast.showCustomToast(svContext, "Some error occured", customToast.ToastyError);
                e.printStackTrace();
            }
        }
    }



    private void ProcessRecharge() {
        Fragment page = getSupportFragmentManager().findFragmentByTag("android:switcher:" +
                R.id.viewPager + ":" + viewPager.getCurrentItem());
        if (actReferTo.equalsIgnoreCase("prepost")) {
            ((FragPrePostRecharge) page).RechargeProcess();
        }
        if (actReferTo.equalsIgnoreCase("dth")) {
            ((FragDth) page).RechargeProcess();
        }
//        if (actReferTo.equalsIgnoreCase("datacard")) {
//            ((FragDatacard) page).RechargeProcess();
//        }
    }

    @Override
    public void onWebServiceError(String result, String url) {
        customToast.showCustomToast(svContext, result, customToast.ToastyError);
    }

    public static String prePostpaid = "Prepaid";

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public class ViewPagerAdapter extends FragmentPagerAdapter {
        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            if (position == 0) {
                prePostpaid = "Prepaid";
                fragment = new FragPrePostRecharge();
            } else if (position == 1) {
                fragment = new FragDth();
            } else if (position == 2) {
                fragment = new FragDatacard();
            } else if (position == 3) {
                fragment = new FragLandline();
            } else {
                fragment = new FragNew();
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return selectedItem.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return selectedItem[position];
        }
    }

    public void ShowConfirmDialog(Context context, String message, String strScreen) {

            new AndExAlertDialog.Builder(context)
                    .setTitle("Do you want to confirm your Recharge")
                    .setMessage(message)
                    .setPositiveBtnText("Confirm")
                    .setNegativeBtnText("Cancel")
                    .setCancelableOnTouchOutside(false)
                    .setFont(Font.IRAN_SANS)
                    .setImage(R.drawable.app_logo, 35)
                    .OnPositiveClicked(new AndExAlertDialogListener() {
                        @Override
                        public void OnClick(String input) {
                            Fragment page = getSupportFragmentManager().findFragmentByTag("android:switcher:" +
                                    R.id.viewPager + ":" + viewPager.getCurrentItem());
                            if (strScreen.equalsIgnoreCase("mobile")) {
                                ((FragPrePostRecharge) page).ConfirmRecharge();
                            } else if (strScreen.equalsIgnoreCase("dth")) {
                                ((FragDth) page).ConfirmRecharge();
                            } else if (strScreen.equalsIgnoreCase("transfer")) {
                                ((FragMtransfer) page).ConfirmRecharge();
                            }
                        }
                    })
                    .OnNegativeClicked(new AndExAlertDialogListener() {
                        @Override
                        public void OnClick(String input) {

                        }
                    })
                    .setTitleTextColor(R.color.green_900)
                    .setMessageTextColor(R.color.black)
                    .setButtonTextColor(R.color.colorPrimary)
                    .build();

    }

    String actReferTo = "";

    public boolean checkRWalletAndAddWallet(int rechargeAmount, String actRefer) {
        actReferTo = actRefer;
        boolean isWalletToLoad = false;
        float requiredAmountToAdd = 0;
        float walletAmount = Float.parseFloat(PreferenceConnector.readString(svContext, PreferenceConnector.WALLETBAL, ""));
        if (rechargeAmount > walletAmount) {
            requiredAmountToAdd = rechargeAmount - walletAmount;
            isWalletToLoad = true;
        } else {
            requiredAmountToAdd = 0;
            isWalletToLoad = false;
        }
        return isWalletToLoad;
    }

    public boolean checkEWalletAndAddWallet(float rechargeAmount, String actRefer) {
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
                startPayment(requiredAmountToAdd);
            } else {
                ProcessRecharge();
            }
        } else {

        }
        return isWalletToLoad;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mBottomSheetDialog != null && mBottomSheetDialog.isShowing()) {
            mBottomSheetDialog.dismiss();
            mBottomSheetDialog = null;
        }
    }

    private View bottomSheet;
    private BottomSheetBehavior mBehavior;
    private List<ContactModel> lstContacts;
    private BottomSheetDialog mBottomSheetDialog;
    public final static int REQUEST_READ_CONTACTS_PERMISSION = 24563;

    public void showContactBottomSheet(String strScreen) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN &&
                ActivityCompat.checkSelfPermission(svContext, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermission(Manifest.permission.READ_CONTACTS, getString(R.string.permission_read_contacts_rationale), REQUEST_READ_CONTACTS_PERMISSION);
            return;
        }

        if (mBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }

        View view = getLayoutInflater().inflate(R.layout.bs_select_contact, null);
        //view.setBackgroundColor(ContextCompat.getColor(svContext, android.R.color.transparent));

        mBottomSheetDialog = new BottomSheetDialog(this, R.style.BottomSheetDialog);
        mBottomSheetDialog.setContentView(view);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mBottomSheetDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        mBottomSheetDialog.show();
        mBottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                mBottomSheetDialog = null;
            }
        });

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) mBottomSheetDialog.findViewById(R.id.searchview);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        EditText txtSearch = ((EditText) searchView.findViewById(androidx.appcompat.R.id.search_src_text));
        txtSearch.setHintTextColor(getResources().getColor(R.color.c_black));
        txtSearch.setTextColor(getResources().getColor(R.color.c_black));

        RecyclerView rvContactsList = mBottomSheetDialog.findViewById(R.id.rv_contacts);
        rvContactsList.setLayoutManager(new LinearLayoutManager(svContext, LinearLayoutManager.VERTICAL, false));
        rvContactsList.setHasFixedSize(true);
        lstContacts = new ArrayList<>();
        ContactAdapter mAdapter = new ContactAdapter(svContext, lstContacts, ItemAnimation.NONE);

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                lstContacts = getContactsList(svContext, "");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.updateData(lstContacts);
                    }
                });
            }
        });

        rvContactsList.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new ContactAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, ContactModel contactModel, int position) {
                String number = contactModel.getContactNumber();
                number = number.replaceAll("\\D", "");
                number = number.replaceAll("\\s", "");
                String getnumber = "";
                if (number.length() > 10) {
                    //if (number.startsWith("+91") || number.startsWith("0")) {
                    //    number = number.split("+91")[1];
                    //}
                    int startidx = number.length() - 10;
                    getnumber = number.substring(startidx, number.length());
                } else {
                    getnumber = number;
                }

                Fragment page = getSupportFragmentManager().findFragmentByTag("android:switcher:" +
                        R.id.viewPager + ":" + viewPager.getCurrentItem());
                if (strScreen.equalsIgnoreCase("mobile")) {
                    ((FragPrePostRecharge) page).SetSelectedContact(getnumber);
                    mBottomSheetDialog.dismiss();
                } else if (strScreen.equalsIgnoreCase("dth")) {
                    //((FragDth) page).ConfirmRecharge();
                } else if (strScreen.equalsIgnoreCase("transfer")) {
                    //((FragMtransfer) page).ConfirmRecharge();
                }
            }
        });

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                mAdapter.getFilter().filter(query);
                //mAdapter.updateData(getContactsList(svContext, query));
                return false;
            }
        });

    }

    public static List<ContactModel> getContactsList(Context context, String searchQuery) {
        if (searchQuery == null) searchQuery = "";
        List<ContactModel> contactsModels = new ArrayList<>();
        String selection = "(DISPLAY_NAME LIKE \"%" + searchQuery + "%\")";
        //selection = "DISPLAY_NAME = '" + searchQuery + "'";

        String sortOrder = "STARRED DESC, " + "TIMES_CONTACTED DESC, " + "LAST_TIME_CONTACTED DESC, " + "DISPLAY_NAME ASC";
        sortOrder = "DISPLAY_NAME ASC";

        ContentResolver cr = context.getContentResolver();
        Cursor cursor = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                new String[]{
                        ContactsContract.CommonDataKinds.Phone._ID,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                        ContactsContract.CommonDataKinds.Phone.NUMBER,
                        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                        ContactsContract.CommonDataKinds.Phone.PHOTO_URI},
                selection, null, sortOrder);


        if ((cursor != null ? cursor.getCount() : 0) > 0) {
            while (cursor.moveToNext()) {
//                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID));
//                String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));
//                String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
//                String image_uri = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));
                ContactModel contactModel = new ContactModel(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4)
                );
                //contactModel = new ContactModel(id, contactId, number, name);
                contactsModels.add(contactModel);
            }
        }

        if (cursor != null) cursor.close();
        return contactsModels;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_READ_CONTACTS_PERMISSION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showContactBottomSheet("mobile");
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    protected void requestPermission(final String permission, String rationale, final int requestCode) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
            showAlertDialog(getString(R.string.permission_title_rationale), rationale,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(ActivityRecharge.this,
                                    new String[]{permission}, requestCode);
                        }
                    }, getString(R.string.label_ok), null, getString(R.string.label_cancel));
        } else {
            ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
        }
    }


    private void showAlertDialog(String title, String message,
                                 DialogInterface.OnClickListener onPositiveButtonClickListener, String positiveText,
                                 DialogInterface.OnClickListener onNegativeButtonClickListener, String negativeText) {
        AlertDialog.Builder builder = new AlertDialog.Builder(svContext);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(positiveText, onPositiveButtonClickListener);
        builder.setNegativeButton(negativeText, onNegativeButtonClickListener);
        builder.show();
    }

    private String strDemoServiceName = "", dtrDemoServiceUrl = "";


    public void startPayment(float strAmount) {
        addAmountToWallet = strAmount + "";
        final Activity activity = this;
        String strRazorPayId = PreferenceConnector.readString(svContext, PreferenceConnector.RAZORPAYID, "");
        final Checkout co = new Checkout();
        co.setKeyID(strRazorPayId);
        if (strRazorPayId.length() == 0) {
            customToast.showCustomToast(svContext, "Razorpay Key Not Available", customToast.ToastyError);
        } else {
            try {
                JSONObject options = new JSONObject();
                options.put("name", getResources().getString(R.string.app_name));
                options.put("description", "Add amount to wallet");
                //You can omit the image option to fetch the image from dashboard
                options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
                options.put("currency", "INR");
                options.put("amount", (strAmount * 100) + "");

                JSONObject preFill = new JSONObject();
                preFill.put("email", PreferenceConnector.readString(svContext, PreferenceConnector.LOGINUSEREMAIL, ""));
                preFill.put("contact", PreferenceConnector.readString(svContext, PreferenceConnector.LOGINUSERPHONE, ""));

                options.put("prefill", preFill);

                co.open(activity, options);
            } catch (Exception e) {
                customToast.showCustomToast(svContext, "Error in payment: " + e.getMessage(), customToast.ToastyError);
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == RESULT_OK) {
            Bundle extras = intent.getExtras();
            if (extras == null) return;
            Fragment page = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.viewPager + ":" + viewPager.getCurrentItem());
            int position;
            SpinnerModel selectedSpinner;
            switch (requestCode) {
                case FRAG_RECHARGE_CIRCLE_REQUEST_CODE:
                    position = intent.getIntExtra(ActivitySpinner.EXTRA_SPINNER_POSITION, 0);
                    selectedSpinner = (SpinnerModel) intent.getSerializableExtra(ActivitySpinner.EXTRA_SPINNER_DATA);
                    break;
                case FRAG_RECHARGE_OPERATOR_REQUEST_CODE:
                    position = intent.getIntExtra(ActivitySpinner.EXTRA_SPINNER_POSITION, 0);
                    selectedSpinner = (SpinnerModel) intent.getSerializableExtra(ActivitySpinner.EXTRA_SPINNER_DATA);

                    if (page instanceof FragPrePostRecharge) {
                        ((FragPrePostRecharge) page).PopulateOperatorList(position, selectedSpinner);
                    }
                    break;
                case FRAG_DTH_OPERATOR_REQUEST_CODE:
                    position = intent.getIntExtra(ActivitySpinner.EXTRA_SPINNER_POSITION, 0);
                    selectedSpinner = (SpinnerModel) intent.getSerializableExtra(ActivitySpinner.EXTRA_SPINNER_DATA);

                    if (page instanceof FragDth) {
                        ((FragDth) page).PopulateDthOperatorList(position, selectedSpinner);
                    }
                    break;
                default:
                    break;
            }
        }
    }
}