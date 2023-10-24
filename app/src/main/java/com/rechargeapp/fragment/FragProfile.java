package com.rechargeapp.fragment;

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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.commonutility.PreferenceConnector;
import com.rechargeapp.WebViewActivity;
import com.rechargeapp.activity.ActivityMain;
import com.codunite.rechargeapp.R;
import com.rechargeapp.adapter.DashboardAdapter;
import com.rechargeapp.adapter.SliderAdapter;
import com.rechargeapp.model.DashboardModel;
import com.commonutility.GlobalVariables;
import com.commonutility.ItemAnimation;
import com.commonutility.ShowCustomToast;
import com.commonutility.WebService;
import com.commonutility.WebServiceListener;
import com.commonutility.customfont.FontUtils;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.interfaces.ItemClickListener;
import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class FragProfile extends Fragment implements OnClickListener, WebServiceListener {
    private Context svContext;
    private ShowCustomToast customToast;
    private ViewGroup root;
    private View aiView = null;
    private boolean mAlreadyLoaded = false;
    private List<DashboardModel> lstDashBoard = new ArrayList<>();
    private TextView totalSuccess, totalPending, totalFailed;
    public static String[] selectedItem = {"Personal Profile", "Change Password", "Create Ticket", "View Ticket"};
    private int[] allDrawable = {R.drawable.ic_personal_profile, R.drawable.ic_change_password_profile, R.drawable.ic_create_ticket, R.drawable.ic_view_ticket};
    private int[] allDrawableBgColor = {R.color.bg_postpaid, R.color.bg_prepaid, R.color.bg_dth, R.color.bg_datacard};

    private int animation_type = ItemAnimation.NONE;

    private ViewPager2 viewPager2;
    private Handler sliderHandler = new Handler();

    public FragProfile() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (aiView == null) {
            aiView = inflater.inflate(R.layout.frag_personprofile, container, false);
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
            default:
                break;
        }
    }

    public void resumeApp() {

        viewPager2 =(ViewPager2) aiView.findViewById(R.id.viewPagerImageSlider);
        totalSuccess = (TextView) aiView.findViewById(R.id.total_success);
        totalPending = (TextView) aiView.findViewById(R.id.total_pending);
        totalFailed = (TextView) aiView.findViewById(R.id.total_failed);

        totalSuccess.setText(GlobalVariables.CURRENCYSYMBOL + PreferenceConnector.readString(svContext, PreferenceConnector.TOTALSUCCESS, ""));
        totalPending.setText(GlobalVariables.CURRENCYSYMBOL + PreferenceConnector.readString(svContext, PreferenceConnector.TOTALPENDING, ""));
        totalFailed.setText(GlobalVariables.CURRENCYSYMBOL + PreferenceConnector.readString(svContext, PreferenceConnector.TOTALFAILED, ""));

        for (int j = 0; j < selectedItem.length; j++) {
            lstDashBoard.add(new DashboardModel(selectedItem[j], allDrawable[j], false, allDrawableBgColor[j]));
        }
        RecyclerView recyclerView = aiView.findViewById(R.id.rv_dashboard);
        root.setVisibility(View.VISIBLE);
        GridLayoutManager layoutManagerProfile = new GridLayoutManager(svContext, 3);
        recyclerView.setLayoutManager(layoutManagerProfile);
        recyclerView.setHasFixedSize(true);
        DashboardAdapter mAdapter = new DashboardAdapter(svContext, lstDashBoard, animation_type);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((view, obj, position) -> {
            ActivityMain.onDrawerItemClick(obj, svContext,"");
        });
        LoadSlider();
    }

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

    LinkedList<String> lstUploadData = new LinkedList<>();

    private void callWebService(String postUrl, LinkedList<String> lstUploadData) {
        WebService webService = new WebService(svContext, postUrl, lstUploadData, this);
        webService.LoadDataRetrofit(webService.callReturn());
    }

    @Override
    public void onWebServiceActionComplete(String result, String url) {
    }

    @Override
    public void onWebServiceError(String result, String url) {
        customToast.showToast(result, svContext);
    }

    private void LoadSlider() {
        ImageSlider sliderView = aiView.findViewById(R.id.image_slider);
        List<SlideModel> imageList = new ArrayList<>();

        CardView cardSlider = aiView.findViewById(R.id.card_slider);
        if (FragHomeDashBoard.lstSlider != null && FragHomeDashBoard.lstSlider.size() > 0) {
            cardSlider.setVisibility(View.GONE);
            viewPager2.setVisibility(View.VISIBLE);
        } else {
            cardSlider.setVisibility(View.GONE);
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
        sliderView.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemSelected(int i) {
                PreferenceConnector.writeString(svContext, PreferenceConnector.WEBHEADING,
                        FragHomeDashBoard.lstSlider.get(i).getBanner_name());
                PreferenceConnector.writeString(svContext, PreferenceConnector.WEBURL,
                        FragHomeDashBoard.lstSlider.get(i).getBanner_url());

                Intent svIntent = new Intent(svContext, WebViewActivity.class);
                svContext.startActivity(svIntent);
            }
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