package com.codunite.rechargeapp.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
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

import com.codunite.rechargeapp.activity.bbps.FragBBPSDashBoard;
import com.codunite.rechargeapp.activity.reports.ActivityAepsWalletHistory;
import com.codunite.rechargeapp.adapter.DashboardAdapter;
import com.codunite.rechargeapp.activity.mainwallet.ActivityFundRequest;
import com.codunite.rechargeapp.activity.ActivityMain;
import com.codunite.rechargeapp.activity.reports.ActivityComisionWalletHistory;
import com.codunite.rechargeapp.activity.reports.ActivityWalletHistory;
import com.codunite.rechargeapp.activity.bbps.ActivityBbpsAllServices;
import com.codunite.rechargeapp.activity.bbps.ActivityBbpsTollTax;
import com.codunite.rechargeapp.activity.ActivityRecharge;
import com.codunite.rechargeapp.R;
import com.codunite.rechargeapp.WebViewActivity;
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
    private List<DashboardModel> lstDashBoardAeps = new ArrayList<>();
    private List<DashboardModel> lstDashBoardMainAnim = new ArrayList<>();

    public static String[] selectedItemItemList = {"Postpaid","Prepaid", "DTH", "Data Card", "Broadband", "Landline", "Water", "Fasttag"};
    private int[] allDrawable = {R.drawable.postpaid, R.drawable.prepaid, R.drawable.dth, R.drawable.datacard,
            R.drawable.broadband, R.drawable.landline, R.drawable.water, R.drawable.fastag};

    private int[] allDrawableBgColor = {R.color.bg_postpaid, R.color.bg_prepaid, R.color.bg_dth, R.color.bg_datacard,
            R.color.bg_prepaid, R.color.bg_landline, R.color.bg_dth,R.color.bg_fasttag};

    public static String[] aepsliveItemList = {"AEPS", "Fino AEPS", "Add Money PG",
            "Fund Request", "Notification", "Sonika Store", "Fino AEPS Settlement", "NSDL Pan"};
    private int[] allDrawableaeps = {R.drawable.aeps, R.drawable.fino_aeps, R.drawable.add_money_pg,
            R.drawable.fund_request, R.drawable.dash_notification, R.drawable.sonikastore, R.drawable.fino_aeps, R.drawable.pancard};


    private CardView cardMarquee;
    private SwipeRefreshLayout layrefrsh;
    private View layIsFundRequest;

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

    private int animation_type = ItemAnimation.NONE;

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
        layIsFundRequest = (View) aiView.findViewById(R.id.lay_isfundrequest);
        cardMarquee = (CardView) aiView.findViewById(R.id.card_marque);
        layrefrsh = (SwipeRefreshLayout) aiView.findViewById(R.id.layrefrsh);

        (aiView.findViewById(R.id.btn_fundrequest)).setOnClickListener(view -> {
            Intent svIntent = new Intent(svContext, ActivityFundRequest.class);
            startActivity(svIntent);
        });

        if (PreferenceConnector.readBoolean(svContext, PreferenceConnector.IS_FUNDREQUEST_ACTIVE, false)) {
            layIsFundRequest.setVisibility(View.VISIBLE);
        } else {
            layIsFundRequest.setVisibility(View.GONE);
        }

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
            ((ActivityMain) requireActivity()).loadUserData();
            layrefrsh.setRefreshing(false);
            resumeApp();
        });

        layrefrsh.setColorSchemeColors(
                getResources().getColor(android.R.color.holo_blue_bright),
                getResources().getColor(android.R.color.holo_green_light),
                getResources().getColor(android.R.color.holo_orange_light),
                getResources().getColor(android.R.color.holo_red_light)
        );

        root.setVisibility(View.VISIBLE);

        LinearLayout mainwallet = aiView.findViewById(R.id.lay_main_wallet);
        mainwallet.setOnClickListener(v -> {
            Intent svIntent = new Intent(svContext, ActivityWalletHistory.class);
            startActivity(svIntent);
        });
        ((TextView) aiView.findViewById(R.id.txt_main_wallet)).setText(PreferenceConnector.readString(svContext, PreferenceConnector.WALLETBAL, "0"));

        LinearLayout aepswallet = aiView.findViewById(R.id.lay_aeps_wallet);
        aepswallet.setOnClickListener(v -> {
            Intent svIntent = new Intent(svContext, ActivityAepsWalletHistory.class);
            startActivity(svIntent);
        });
        ((TextView) aiView.findViewById(R.id.txt_aeps)).setText(PreferenceConnector.readString(svContext, PreferenceConnector.EWALLETBAL, "0"));

//        LinearLayout commissionwallet = aiView.findViewById(R.id.lay_commission_wallet);
//        commissionwallet.setOnClickListener(v -> {
//            Intent svIntent = new Intent(svContext, ActivityComisionWalletHistory.class);
//            startActivity(svIntent);
//        });
        //((TextView) aiView.findViewById(R.id.txt_commission_wallet)).setText(PreferenceConnector.readString(svContext, PreferenceConnector.COMMISIONBALANCE, "0"));


        //((TextView) aiView.findViewById(R.id.txt_points_wallet)).setText(PreferenceConnector.readString(svContext, PreferenceConnector.COINBALANCE, "0"));


        RecyclerView rvDashGeneology = (RecyclerView) aiView.findViewById(R.id.rv_anim_lay);

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


        LinearLayoutManager layoutManager = new LinearLayoutManager(svContext, LinearLayoutManager.HORIZONTAL, false);
        rvDashGeneology.setLayoutManager(layoutManager);
        rvDashGeneology.setHasFixedSize(true);

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
                svIntent.putExtra("selecteditem", 0);
                svContext.startActivity(svIntent);
            } else if (selectedItemItemList[2].equals(obj)) {
                Intent svIntent = new Intent(svContext, ActivityRecharge.class);
                svIntent.putExtra("selecteditem", 1);
                svContext.startActivity(svIntent);
            } else if (selectedItemItemList[3].equals(obj)) {
                Intent svIntent = new Intent(svContext, ActivityRecharge.class);
                svIntent.putExtra("selecteditem", 2);
                svContext.startActivity(svIntent);
            } else if (selectedItemItemList[4].equals(obj)) {
                startNewActivity(ActivityBbpsAllServices.class, "BROADBAND POSTPAID", "14");
            } else if (selectedItemItemList[5].equals(obj)) {
                Intent svIntent = new Intent(svContext, ActivityRecharge.class);
                svIntent.putExtra("selecteditem", 2);
                svContext.startActivity(svIntent);
            } else if (selectedItemItemList[6].equals(obj)) {
                startNewActivity(ActivityBbpsAllServices.class, "WATER", "8");
            } else if (selectedItemItemList[7].equals(obj)) {
                startNewActivity(ActivityBbpsTollTax.class);
            }
        });

        LoadBanking();
    }

    public void LoadBanking() {
        RecyclerView recyclerView = aiView.findViewById(R.id.rv_dashboard_aeps);
        lstDashBoardAeps = new ArrayList<>();
        for (int j = 0; j < aepsliveItemList.length; j++) {
            lstDashBoardAeps.add(new DashboardModel(aepsliveItemList[j], allDrawableaeps[j], false,0));
        }

        GridLayoutManager layoutManager = new GridLayoutManager(svContext, 4);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        DashboardAdapter mAdapter = new DashboardAdapter(svContext, lstDashBoardAeps, animation_type);
        recyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener((view, obj, position) -> {

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
        for (int j = 0; j < FragHomeDashBoard.lstSlider.size(); j++) {
            imageList.add(new SlideModel((FragHomeDashBoard.lstSlider.get(j).getBanner_img()).replaceAll("\\/", "/"), ScaleTypes.FIT));
        }

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
    }
}