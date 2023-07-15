package com.codunite.rechargeapp.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.codunite.commonutility.FragmentTAG;
import com.codunite.rechargeapp.activity.ActivityLoginTransfer;
import com.codunite.rechargeapp.activity.bbps.ActivityBBPSDashBoardActivity;
import com.codunite.rechargeapp.activity.bbps.ActivityBbpsElectricity;
import com.codunite.rechargeapp.activity.bbps.FragBBPSDashBoard;
import com.codunite.rechargeapp.activity.reports.ActivityAepsWalletHistory;
import com.codunite.rechargeapp.activity.wallet.ActivityAddFundRequest;
import com.codunite.rechargeapp.activity.wallet.ActivityEWalletHistory;
import com.codunite.rechargeapp.activity.wallet.ActivityWalletHistory;
import com.codunite.rechargeapp.adapter.DashboardAdapter;
import com.codunite.rechargeapp.activity.mainwallet.ActivityFundRequest;
import com.codunite.rechargeapp.activity.ActivityMain;
import com.codunite.rechargeapp.activity.reports.ActivityComisionWalletHistory;
import com.codunite.rechargeapp.activity.bbps.ActivityBbpsAllServices;
import com.codunite.rechargeapp.activity.bbps.ActivityBbpsTollTax;
import com.codunite.rechargeapp.activity.ActivityRecharge;
import com.codunite.rechargeapp.R;
import com.codunite.rechargeapp.WebViewActivity;
import com.codunite.rechargeapp.adapter.SliderAdapter;
import com.codunite.rechargeapp.model.DashboardAffilateModel;
import com.codunite.rechargeapp.model.DashboardModel;
import com.codunite.rechargeapp.model.SliderModel;
import com.codunite.commonutility.GlobalVariables;
import com.codunite.commonutility.ItemAnimation;
import com.codunite.commonutility.PreferenceConnector;
import com.codunite.commonutility.ShowCustomToast;
import com.codunite.commonutility.customfont.FontUtils;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;
import java.util.List;

public class FragHomeDashBoard extends Fragment implements OnClickListener {
    private View aiView = null;
    private boolean mAlreadyLoaded = false;
    private List<DashboardModel> lstDashBoardMain = new ArrayList<>();
    private List<DashboardModel> lstDashBoardBbps = new ArrayList<>();
    private List<DashboardModel> lstDashBoardAeps = new ArrayList<>();
    private List<DashboardModel> lstDashBoardMainAnim = new ArrayList<>();

    public static String[] selectedItemItemList = {"Prepaid", "DTH","Electricity", "Water","Gas","Insurance","Loan","More"};
    //public static String[] selectedItemItemList = {"Postpaid","Prepaid", "DTH", "Data Card", "Landline"};
    private int[] allDrawable = { R.drawable.prepaid, R.drawable.dth,R.drawable.bbps_electricity, R.drawable.bbps_water,R.drawable.bbps_lpg_gas,R.drawable.bbps_insurance,R.drawable.bbps_loan,R.drawable.ic_more};

    private int[] allDrawableBgColor = { R.color.bg_prepaid, R.color.bg_dth,R.color.bg_postpaid, R.color.bg_prepaid,R.color.bg_metro,R.color.bg_prepaid,R.color.bg_landline,0};


    public static String[] selectedItemListBBPS = {"Broadband","Fasttag", "Electricity", "Water","Gas","Insurance","Loan","More"};
    private int[] allDrawableBBPS = {R.drawable.bbps_boardband, R.drawable.fastag, R.drawable.bbps_electricity, R.drawable.bbps_water,R.drawable.bbps_lpg_gas,R.drawable.bbps_insurance,R.drawable.bbps_loan,R.drawable.ic_more};
    private int[] allDrawableBgColorBBPS = {R.color.bg_datacard, R.color.bg_fasttag, R.color.bg_postpaid, R.color.bg_prepaid,R.color.bg_metro,R.color.bg_prepaid,R.color.bg_landline,0};

    public static String[] aepsliveItemList = {"Money Transfer","Xpress IMPS","Micro ATM","AEPS", "Aadhar pay"};
    private int[] allDrawableaeps = {R.drawable.ic_money_transfer, R.drawable.ic_express_imps, R.drawable.ic_microatm,R.drawable.ic_aeps, R.drawable.ic_aadhaar_pay};
    private int[] allDrawableaepsColors = {R.color.bg_prepaid, R.color.bg_metro, R.color.bg_datacard, R.color.bg_dth,R.color.bg_postpaid};

    private CardView cardMarquee;
    private SwipeRefreshLayout layrefrsh;
    private View layIsFundRequest;
    private int animation_type = ItemAnimation.NONE;

    private ViewPager2 viewPager2;
    private Handler sliderHandler = new Handler();

    public FragHomeDashBoard() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (aiView == null) {
            aiView = inflater.inflate(R.layout.frag_home, container, false);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                break;
            default:
                break;
        }
    }

    public void switchContent(Fragment fragment) {
        hideFragmentkeyboard(svContext, aiView);
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

    public void switchContent(Fragment fragment, String tag) {
        hideFragmentkeyboard(svContext, aiView);
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(tag)
                .commit();
    }

    public void resumeApp() {
        //layIsFundRequest = (View) aiView.findViewById(R.id.lay_isfundrequest);
        cardMarquee = (CardView) aiView.findViewById(R.id.card_marque);
        layrefrsh = (SwipeRefreshLayout) aiView.findViewById(R.id.layrefrsh);
        viewPager2 =(ViewPager2) aiView.findViewById(R.id.viewPagerImageSlider);


//        (aiView.findViewById(R.id.btn_fundrequest)).setOnClickListener(view -> {
//            Intent svIntent = new Intent(svContext, ActivityFundRequest.class);
//            startActivity(svIntent);
//        });

        (aiView.findViewById(R.id.ll_addfund)).setOnClickListener(view -> {
            ActivityAddFundRequest.OpenAddFundRequest(svContext);
        });

        (aiView.findViewById(R.id.ll_passbook)).setOnClickListener(view -> {
            Intent svIntent = new Intent(svContext, com.codunite.rechargeapp.activity.wallet.ActivityWalletHistory.class);
            svContext.startActivity(svIntent);
        });

//        if (PreferenceConnector.readBoolean(svContext, PreferenceConnector.IS_FUNDREQUEST_ACTIVE, false)) {
//            layIsFundRequest.setVisibility(View.VISIBLE);
//        } else {
//            layIsFundRequest.setVisibility(View.GONE);
//        }

        ((TextView) aiView.findViewById(R.id.txt_direct_downline)).setText(PreferenceConnector.readString(svContext, PreferenceConnector.TOTALDIRECTDOWNLINE, "0"));
        ((TextView) aiView.findViewById(R.id.txt_direct_active)).setText(PreferenceConnector.readString(svContext, PreferenceConnector.TOTALDIRECTACTIVE, "0"));
        ((TextView) aiView.findViewById(R.id.txt_direct_deactive)).setText(PreferenceConnector.readString(svContext, PreferenceConnector.TOTALDIRECTDEACTIVE, "0"));
        ((TextView) aiView.findViewById(R.id.txt_total_downline)).setText(PreferenceConnector.readString(svContext, PreferenceConnector.TOTALDOWNLINE, "0"));
        ((TextView) aiView.findViewById(R.id.txt_active_downline)).setText(PreferenceConnector.readString(svContext, PreferenceConnector.TOTALACTIVEDOWNLINE, "0"));
        ((TextView) aiView.findViewById(R.id.txt_deactive_downline)).setText(PreferenceConnector.readString(svContext, PreferenceConnector.TOTALDEACTIVEDOWNLINE, "0"));
        ((TextView) aiView.findViewById(R.id.txt_direct_income)).setText(GlobalVariables.CURRENCYSYMBOL + PreferenceConnector.readString(svContext, PreferenceConnector.DIRECTINCOME, "0"));
        ((TextView) aiView.findViewById(R.id.txt_level_income)).setText(GlobalVariables.CURRENCYSYMBOL + PreferenceConnector.readString(svContext, PreferenceConnector.LEVELINCOME, "0"));
        ((TextView) aiView.findViewById(R.id.txt_recharge_income)).setText(GlobalVariables.CURRENCYSYMBOL + PreferenceConnector.readString(svContext, PreferenceConnector.RECHARGEINCOME, "0"));
        ((TextView) aiView.findViewById(R.id.txt_bbps_income)).setText(GlobalVariables.CURRENCYSYMBOL + PreferenceConnector.readString(svContext, PreferenceConnector.BBPSINCOME, "0"));
        ((TextView) aiView.findViewById(R.id.txt_cashback_income)).setText(GlobalVariables.CURRENCYSYMBOL + PreferenceConnector.readString(svContext, PreferenceConnector.CASHBACKINCOME, "0"));
        ((TextView) aiView.findViewById(R.id.txt_total_income)).setText(GlobalVariables.CURRENCYSYMBOL + PreferenceConnector.readString(svContext, PreferenceConnector.TOTALINCOME, "0"));
        ((TextView) aiView.findViewById(R.id.txt_membership)).setText(PreferenceConnector.readString(svContext, PreferenceConnector.MEMBERSHIP, "0"));
        ((TextView) aiView.findViewById(R.id.txt_rank)).setText(PreferenceConnector.readString(svContext, PreferenceConnector.RANK, "0"));

        TextView txtMarquee = (TextView) aiView.findViewById(R.id.text_marquee);
        String dashNEws = PreferenceConnector.readString(svContext, PreferenceConnector.DASHNEWS, "");
        if (dashNEws.equals("")) {
            cardMarquee.setVisibility(View.GONE);
        } else {
            cardMarquee.setVisibility(View.VISIBLE);
        }
        txtMarquee.setText(dashNEws);
        txtMarquee.setSelected(true);

        layrefrsh.setRefreshing(false);
        layrefrsh.setOnRefreshListener(() -> {
            ((ActivityMain) requireActivity()).loadUserData(false);
            layrefrsh.setRefreshing(false);
            resumeApp();
        });

        layrefrsh.setColorSchemeColors(
                getResources().getColor(R.color.colorAccent),
                getResources().getColor(R.color.colorAccent),
                getResources().getColor(R.color.colorAccent),
                getResources().getColor(R.color.colorAccent)
        );

        root.setVisibility(View.VISIBLE);

        LinearLayout lay_r_wallet = aiView.findViewById(R.id.lay_r_wallet);
        lay_r_wallet.setOnClickListener(v -> {
            Intent svIntent = new Intent(svContext, ActivityWalletHistory.class);
            startActivity(svIntent);
        });
        ((TextView) aiView.findViewById(R.id.txt_main_wallet)).setText(PreferenceConnector.readString(svContext, PreferenceConnector.WALLETBAL, "0"));

        LinearLayout lay_e_wallet = aiView.findViewById(R.id.lay_e_wallet);
        lay_e_wallet.setOnClickListener(v -> {
            Intent svIntent = new Intent(svContext, ActivityEWalletHistory.class);
            startActivity(svIntent);
        });
        ((TextView) aiView.findViewById(R.id.txt_aeps)).setText(PreferenceConnector.readString(svContext, PreferenceConnector.EWALLETBAL, "0"));

        //RecyclerView rvDashGeneology = (RecyclerView) aiView.findViewById(R.id.rv_anim_lay);

        lstDashBoardMainAnim = new ArrayList<>();
        if (PreferenceConnector.readBoolean(svContext, PreferenceConnector.PACKAGE_DOWNLOADED, false)) {
            lstDashBoardMainAnim.add(new DashboardModel("Update App", R.raw.update_app, true,0));
        }
        lstDashBoardMainAnim.add(new DashboardModel("Scan Pay", R.raw.scan_pay, true,0));
        lstDashBoardMainAnim.add(new DashboardModel("Upgrade", R.raw.upgrade,
                PreferenceConnector.readBoolean(svContext, PreferenceConnector.ISACCOUNTUPGRADE, false),0));
        lstDashBoardMainAnim.add(new DashboardModel("Zoom Meeting", R.raw.zoom_meeting, true,0));
        if (PreferenceConnector.readBoolean(svContext, PreferenceConnector.ISAEPSKYCDONE, false)) {
            lstDashBoardMainAnim.add(new DashboardModel("AEPS KYC", R.raw.kyc,
                    PreferenceConnector.readBoolean(svContext, PreferenceConnector.ISAEPSKYCDONE, false),0));
        }
        if (PreferenceConnector.readBoolean(svContext, PreferenceConnector.ISNEWAEPSKYCDONE, false)) {
            lstDashBoardMainAnim.add(new DashboardModel("Fino KYC", R.raw.kyc,
                    PreferenceConnector.readBoolean(svContext, PreferenceConnector.ISNEWAEPSKYCDONE, false),0));
        }
        lstDashBoardMainAnim.add(new DashboardModel("Profile", R.raw.profile_is,
                PreferenceConnector.readBoolean(svContext, PreferenceConnector.ISPROFILEUPDATED, false),0));
        lstDashBoardMainAnim.add(new DashboardModel("Refer & Earn", R.raw.refer_earn, true,0));


//        LinearLayoutManager layoutManager = new LinearLayoutManager(svContext, LinearLayoutManager.HORIZONTAL, false);
//        rvDashGeneology.setLayoutManager(layoutManager);
//        rvDashGeneology.setHasFixedSize(true);

        LoadSlider();
        LoadRechargeManu();
    }

    private void startNewActivity(Class<?> cls, String serviceType, String strServiceId) {
        Intent intent = new Intent(svContext, cls);
        intent.putExtra("servicetype", serviceType);
        FragBBPSDashBoard.strServiceId = strServiceId;
        intent.putExtra("serviceid", strServiceId);
        startActivity(intent);
    }

    private void startNewActivity(Class<?> cls) {
        Intent intent = new Intent(svContext, cls);
        svContext.startActivity(intent);
    }

    public void LoadRechargeManu() {
        RecyclerView recyclerView = aiView.findViewById(R.id.rv_dashboard);

        lstDashBoardMain = new ArrayList<>();
        for (int j = 0; j < selectedItemItemList.length; j++) {
            lstDashBoardMain.add(new DashboardModel(selectedItemItemList[j], allDrawable[j], false,allDrawableBgColor[j]));
        }

        GridLayoutManager layoutManager = new GridLayoutManager(svContext, 4);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        DashboardAdapter mAdapter = new DashboardAdapter(svContext, lstDashBoardMain, animation_type);
        recyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener((view, obj, position) -> {
            if (selectedItemItemList[0].equals(obj)) {
                Intent svIntent = new Intent(svContext, ActivityRecharge.class);
                svIntent.putExtra("selecteditem", 0);
                svContext.startActivity(svIntent);
            } else if (selectedItemItemList[1].equals(obj)) {
                Intent svIntent = new Intent(svContext, ActivityRecharge.class);
                svIntent.putExtra("selecteditem", 1);
                svContext.startActivity(svIntent);
            } else if (selectedItemListBBPS[2].equals(obj)) {
                startNewActivity(ActivityBbpsElectricity.class);
            } else if (selectedItemListBBPS[3].equals(obj)) {
                startNewActivity(ActivityBbpsAllServices.class, "WATER", "8");
            } else if (selectedItemListBBPS[4].equals(obj)) {
                startNewActivity(ActivityBbpsAllServices.class, "GAS", "3");
            } else if (selectedItemListBBPS[5].equals(obj)) {
                startNewActivity(ActivityBbpsAllServices.class, "INSURANCE", "2");
            } else if (selectedItemListBBPS[6].equals(obj)) {
                startNewActivity(ActivityBbpsAllServices.class, "LOAN", "1");
            } else if (selectedItemListBBPS[7].equals(obj)) {
                startNewActivity(ActivityBBPSDashBoardActivity.class);
                //switchContent(new FragBBPSDashBoard(), FragmentTAG.FragBBPSLiveDashBoard);
                //startNewActivity(ActivityBbpsTollTax.class);
            }


//            else if (selectedItemItemList[2].equals(obj)) {
//                Intent svIntent = new Intent(svContext, ActivityRecharge.class);
//                svIntent.putExtra("selecteditem", 1);
//                svContext.startActivity(svIntent);
//            } else if (selectedItemItemList[3].equals(obj)) {
//                Intent svIntent = new Intent(svContext, ActivityRecharge.class);
//                svIntent.putExtra("selecteditem", 2);
//                svContext.startActivity(svIntent);
//            } else if (selectedItemItemList[4].equals(obj)) {
//                Intent svIntent = new Intent(svContext, ActivityRecharge.class);
//                svIntent.putExtra("selecteditem", 3);
//                svContext.startActivity(svIntent);
//                //startNewActivity(ActivityBbpsAllServices.class, "BROADBAND POSTPAID", "14");
//            } else if (selectedItemItemList[5].equals(obj)) {
//                Intent svIntent = new Intent(svContext, ActivityRecharge.class);
//                svIntent.putExtra("selecteditem", 3);
//                svContext.startActivity(svIntent);
//            } else if (selectedItemItemList[6].equals(obj)) {
//                startNewActivity(ActivityBbpsTollTax.class);
//                //startNewActivity(ActivityBbpsAllServices.class, "WATER", "8");
//            } else if (selectedItemItemList[7].equals(obj)) {
//                //startNewActivity(ActivityBbpsTollTax.class);
//            }
        });

        LoadBanking();
        LoadBBPSMenu();
    }

    public void LoadBBPSMenu() {
        RecyclerView recyclerView = aiView.findViewById(R.id.rv_bbps);

        lstDashBoardBbps = new ArrayList<>();
        for (int j = 0; j < selectedItemListBBPS.length; j++) {
            lstDashBoardBbps.add(new DashboardModel(selectedItemListBBPS[j], allDrawableBBPS[j], false,allDrawableBgColorBBPS[j]));
        }

        GridLayoutManager layoutManager = new GridLayoutManager(svContext, 4);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        DashboardAdapter mAdapter = new DashboardAdapter(svContext, lstDashBoardBbps, animation_type);
        recyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener((view, obj, position) -> {
            if (selectedItemListBBPS[0].equals(obj)) {
                startNewActivity(ActivityBbpsAllServices.class, "BROADBAND POSTPAID", "14");
            } else if (selectedItemListBBPS[1].equals(obj)) {
                startNewActivity(ActivityBbpsTollTax.class);
            } else if (selectedItemListBBPS[2].equals(obj)) {
                startNewActivity(ActivityBbpsElectricity.class);
            } else if (selectedItemListBBPS[3].equals(obj)) {
                startNewActivity(ActivityBbpsAllServices.class, "WATER", "8");
            } else if (selectedItemListBBPS[4].equals(obj)) {
                startNewActivity(ActivityBbpsAllServices.class, "GAS", "3");
            } else if (selectedItemListBBPS[5].equals(obj)) {
                startNewActivity(ActivityBbpsAllServices.class, "INSURANCE", "2");
            } else if (selectedItemListBBPS[6].equals(obj)) {
                startNewActivity(ActivityBbpsAllServices.class, "LOAN", "1");
            } else if (selectedItemListBBPS[7].equals(obj)) {
                startNewActivity(ActivityBBPSDashBoardActivity.class);
                //switchContent(new FragBBPSDashBoard(), FragmentTAG.FragBBPSLiveDashBoard);
                //startNewActivity(ActivityBbpsTollTax.class);
            }
        });

    }

    public void LoadBanking() {
        RecyclerView recyclerView = aiView.findViewById(R.id.rv_dashboard_aeps);
        lstDashBoardAeps = new ArrayList<>();
        for (int j = 0; j < aepsliveItemList.length; j++) {
            lstDashBoardAeps.add(new DashboardModel(aepsliveItemList[j], allDrawableaeps[j], false,allDrawableaepsColors[j]));
        }

        GridLayoutManager layoutManager = new GridLayoutManager(svContext, 4);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        DashboardAdapter mAdapter = new DashboardAdapter(svContext, lstDashBoardAeps, animation_type);
        recyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener((view, obj, position) -> {
            if (position == 0) {
                Intent svIntent = new Intent(svContext, ActivityLoginTransfer.class);
                startActivity(svIntent);
                getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
            } else if (position == 1) {
                ActivityMain.onDrawerItemClick("Transfer", svContext);
            } else if (position == 3 || position == 4) {
                ActivityMain.OpenAeps(obj, svContext, customToast);
            } else {
                customToast.showCustomToast("Coming Soon", customToast.ToastyInfo);
            }
        });

        LoadAFFLIATEManu();
    }

    public static List<DashboardAffilateModel> lstDashBoardAffiliate = new ArrayList<>();
    public void LoadAFFLIATEManu() {
        FrameLayout layDashAffiliate = aiView.findViewById(R.id.card_affiliate);
        RecyclerView rvafffiliate = aiView.findViewById(R.id.rv_dashboard_affiliate);

        if (FragHomeDashBoard.lstDashBoardAffiliate.isEmpty()) {
            layDashAffiliate.setVisibility(View.GONE);

        } else {
            layDashAffiliate.setVisibility(View.VISIBLE);
            rvafffiliate.setLayoutManager(new GridLayoutManager(svContext, 4));
            rvafffiliate.setHasFixedSize(true);


        }
    }



    private Context svContext;
    private ShowCustomToast customToast;
    private ViewGroup root;

    private void StartApp() {
        svContext = getActivity();
        customToast = new ShowCustomToast(svContext);
        root = (ViewGroup) aiView.findViewById(R.id.mylayout);
        if (!GlobalVariables.CUSTOMFONTNAME.equals("")) {
            Typeface font = Typeface.createFromAsset(getActivity().getAssets(), GlobalVariables.CUSTOMFONTNAME);
            FontUtils.setFont(root, font);
        }
        root.setVisibility(View.INVISIBLE);
    }

    public static void hideFragmentkeyboard(Context meraContext, View meraView) {
        final InputMethodManager imm = (InputMethodManager) meraContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(meraView.getWindowToken(), 0);
    }

    public static List<SliderModel> lstSlider = new ArrayList<>();

    public void LoadSlider() {

        ImageSlider sliderView = aiView.findViewById(R.id.image_slider);
        List<SlideModel> imageList = new ArrayList<>();
        List<SlideModel> imageListSlider = new ArrayList<>();

        if (FragHomeDashBoard.lstSlider != null && FragHomeDashBoard.lstSlider.size() > 0) {
            viewPager2.setVisibility(View.VISIBLE);
        } else {
            viewPager2.setVisibility(View.GONE);
            return;
        }
        for (int j = 0; j < FragHomeDashBoard.lstSlider.size(); j++) {
            imageList.add(new SlideModel((FragHomeDashBoard.lstSlider.get(j).getBanner_img()).replaceAll("\\/", "/"), ScaleTypes.FIT));
        }

        viewPager2.setAdapter(new SliderAdapter(FragHomeDashBoard.lstSlider,viewPager2));
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        //viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(30));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                //page.setScaleY(0.85f + r * 0.15f);
            }
        });
        viewPager2.setPageTransformer(compositePageTransformer);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                sliderHandler.removeCallbacks(sliderRunnable);
                sliderHandler.postDelayed(sliderRunnable, 2000); // slide duration 2 seconds
            }
        });


        sliderView.setImageList(imageList, ScaleTypes.FIT);

        sliderView.setItemClickListener(i -> {
            PreferenceConnector.writeString(svContext, PreferenceConnector.WEBHEADING, FragHomeDashBoard.lstSlider.get(i).getBanner_name());
            PreferenceConnector.writeString(svContext, PreferenceConnector.WEBURL, FragHomeDashBoard.lstSlider.get(i).getBanner_url());
            Intent svIntent = new Intent(svContext, WebViewActivity.class);
            svContext.startActivity(svIntent);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        sliderHandler.postDelayed(sliderRunnable, 2000);
    }

    private Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
        }
    };

}