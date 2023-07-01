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
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codunite.commonutility.CheckInternet;
import com.codunite.commonutility.GlobalData;
import com.codunite.commonutility.GlobalVariables;
import com.codunite.commonutility.ItemAnimation;
import com.codunite.commonutility.PreferenceConnector;
import com.codunite.commonutility.ShowCustomToast;
import com.codunite.commonutility.WebService;
import com.codunite.commonutility.WebServiceListener;
import com.codunite.commonutility.customfont.FontUtils;
import com.codunite.rechargeapp.R;
import com.codunite.rechargeapp.WebViewActivity;
import com.codunite.rechargeapp.activity.ActivityMain;
import com.codunite.rechargeapp.activity.wallet.ActivityWalletHistory;
import com.codunite.rechargeapp.adapter.DashboardAdapter;
import com.codunite.rechargeapp.model.DashboardModel;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.interfaces.ItemClickListener;
import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class FragReport extends Fragment implements OnClickListener {
    private View aiView = null;
    private boolean mAlreadyLoaded = false;
    private List<DashboardModel> lstDashBoard = new ArrayList<>();
    public static String[] selectedItem = {"Recharge History", "Bill Pay History", "BBPS History", "Money Transfer History","Recharge Commission", "BBPS Commission", "DMR Commission", "AEPS Commission"};
    private int[] allDrawable = {R.drawable.main_wallet_history, R.drawable.main_wallet_history, R.drawable.main_wallet_history, R.drawable.main_wallet_history, R.drawable.main_wallet_history,
            R.drawable.main_wallet_history, R.drawable.main_wallet_history, R.drawable.main_wallet_history};

    private TextView totalSuccess, totalPending, totalFailed;
    private FrameLayout cardRecharge;
    private Context svContext;
    private ShowCustomToast customToast;
    private CheckInternet checkNetwork;
    private ViewGroup root;

    public FragReport() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (aiView == null) {
            aiView = inflater.inflate(R.layout.frag_report, container, false);
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
    public void resumeApp() {
        cardRecharge = (FrameLayout) aiView.findViewById(R.id.card_recharge);

        totalSuccess = (TextView) aiView.findViewById(R.id.total_success);
        totalPending = (TextView) aiView.findViewById(R.id.total_pending);
        totalFailed = (TextView) aiView.findViewById(R.id.total_failed);

        totalSuccess.setText(GlobalVariables.CURRENCYSYMBOL + PreferenceConnector.readString(svContext, PreferenceConnector.TOTALSUCCESS, ""));
        totalPending.setText(GlobalVariables.CURRENCYSYMBOL + PreferenceConnector.readString(svContext, PreferenceConnector.TOTALPENDING, ""));
        totalFailed.setText(GlobalVariables.CURRENCYSYMBOL + PreferenceConnector.readString(svContext, PreferenceConnector.TOTALFAILED, ""));


        if (PreferenceConnector.readBoolean(svContext, PreferenceConnector.ISRECHARGEACTIVE, false)) {
            cardRecharge.setVisibility(View.VISIBLE);
        } else {
            cardRecharge.setVisibility(View.GONE);
        }

        for (int j = 0; j < selectedItem.length; j++) {
            if ((selectedItem[j]).equals("M Transfer")) {
                if (PreferenceConnector.readBoolean(svContext, PreferenceConnector.ISTRANSFERACTIVE, false)) {
                    lstDashBoard.add(new DashboardModel(selectedItem[j], allDrawable[j], false,0));
                }
            } else {
                lstDashBoard.add(new DashboardModel(selectedItem[j], allDrawable[j], false,0));
            }

        }
        RecyclerView recyclerView = (RecyclerView) aiView.findViewById(R.id.rv_dash_reports);

        LoadSlider();
        root.setVisibility(View.VISIBLE);

        GridLayoutManager layoutManager = new GridLayoutManager(svContext, 3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        DashboardAdapter mAdapter = new DashboardAdapter(svContext, lstDashBoard, animation_type);
        recyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new DashboardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, String obj, int position) {
                ActivityMain.onDrawerItemClick(obj,view.getContext());
            }
        });
    }


    private void StartApp() {
        svContext = getActivity();
        customToast = new ShowCustomToast(svContext);
        checkNetwork = new CheckInternet(svContext);
        root = (ViewGroup) aiView.findViewById(R.id.mylayout);
        if (!GlobalVariables.CUSTOMFONTNAME.equals("")) {
            Typeface font = Typeface.createFromAsset(getActivity().getAssets(), GlobalVariables.CUSTOMFONTNAME);
            FontUtils.setFont(root, font);
        }
        if (PreferenceConnector.readBoolean(svContext, PreferenceConnector.ISDARKTHEME, false)) {
//            FontUtils.setThemeColor(root, svContext, true);
        } else {
//            FontUtils.setThemeColor(root, svContext, false);
        }

        root.setVisibility(View.INVISIBLE);

        GlobalData.SetLanguage(svContext);
    }

    public static void hideFragmentkeyboard(Context meraContext, View meraView) {
        final InputMethodManager imm = (InputMethodManager) meraContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(meraView.getWindowToken(), 0);
    }

    private void LoadSlider() {
        ImageSlider sliderView = aiView.findViewById(R.id.image_slider);
        List<SlideModel> imageList = new ArrayList<>();

        CardView cardSlider = aiView.findViewById(R.id.card_slider);
        if (FragHomeDashBoard.lstSlider != null && FragHomeDashBoard.lstSlider.size() > 0) {
            cardSlider.setVisibility(View.VISIBLE);
        } else {
            cardSlider.setVisibility(View.GONE);
            return;
        }

        for (int j = 0; j < FragHomeDashBoard.lstSlider.size(); j++) {
            imageList.add(new SlideModel((FragHomeDashBoard.lstSlider.get(j).getBanner_img()).replaceAll("\\/", "/"), ScaleTypes.FIT));
        }

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
}