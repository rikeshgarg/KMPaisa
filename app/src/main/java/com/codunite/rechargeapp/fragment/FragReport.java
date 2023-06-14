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
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codunite.commonutility.CheckInternet;
import com.codunite.commonutility.GlobalVariables;
import com.codunite.commonutility.ItemAnimation;
import com.codunite.commonutility.PreferenceConnector;
import com.codunite.commonutility.ShowCustomToast;
import com.codunite.commonutility.WebService;
import com.codunite.commonutility.WebServiceListener;
import com.codunite.commonutility.customfont.FontUtils;
import com.codunite.rechargeapp.R;
import com.codunite.rechargeapp.activity.ActivityMain;
import com.codunite.rechargeapp.adapter.DashboardAdapter;
import com.codunite.rechargeapp.model.DashboardModel;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class FragReport extends Fragment implements OnClickListener, WebServiceListener {
    private View aiView = null;
    private boolean mAlreadyLoaded = false;
    private List<DashboardModel> lstDashBoardReports = new ArrayList<>();
    private List<DashboardModel> lstDashBoardIncome = new ArrayList<>();
    private TextView todayCollection, totalCollection;
    public final String[] selectedItemReports = {"Main Wallet History", "AEPS Wallet History", "Commission Wallet History",
            "Point Wallet History", "Recharge History", "BBPS History", "AEPS History", "Fino AEPS History",
            "BBPS Live History", "Money Transfer History"};
    private int[] allDrawableReports = {R.drawable.main_wallet_history, R.drawable.aeps_wallet_history, R.drawable.commission_wallet_history,
            R.drawable.point_wallet_history, R.drawable.recharge_history, R.drawable.bbps_history,
            R.drawable.aeps_history, R.drawable.fino_aeps_history, R.drawable.bbps_history, R.drawable.acc_money_trans
    };

    public final String[] selectedItemIncome = {"Recharge Commission", "BBPS Commission", "Aeps Commission",
            "Recharge Income", "BBPS Income", "AEPS Income", "Direct Income", "Level Income"};
    private int[] allDrawableIncome = {R.drawable.recharge_commission_history, R.drawable.bbps_commission_history, R.drawable.aeps_commission_history,
            R.drawable.recharge_commission_history, R.drawable.bbps_income_history, R.drawable.aeps,
            R.drawable.direct_income, R.drawable.rep_level_income
    };

    public FragReport() {
    }

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

            default:
                break;
        }
    }

    public void resumeApp() {
        todayCollection = aiView.findViewById(R.id.today_bal_txt);
        totalCollection = aiView.findViewById(R.id.total_bal_txt);

        RecyclerView rvDashReports = (RecyclerView) aiView.findViewById(R.id.rv_dash_reports);
        RecyclerView rvDashIncome = (RecyclerView) aiView.findViewById(R.id.rv_dash_income);

        root.setVisibility(View.VISIBLE);

        ((TextView) aiView.findViewById(R.id.total_success)).setText(GlobalVariables.CURRENCYSYMBOL + PreferenceConnector.readString(svContext, PreferenceConnector.TOTALSUCCESS, "")+"/-");
        ((TextView) aiView.findViewById(R.id.total_pending)).setText(GlobalVariables.CURRENCYSYMBOL + PreferenceConnector.readString(svContext, PreferenceConnector.TOTALPENDING, "")+"/-");
        ((TextView) aiView.findViewById(R.id.total_failed)).setText(GlobalVariables.CURRENCYSYMBOL + PreferenceConnector.readString(svContext, PreferenceConnector.TOTALFAILED, "")+"/-");


        String todayBal = getFormatBalance(PreferenceConnector.readString(svContext, PreferenceConnector.TODAYINCOME, "0"));
        String totalBal = getFormatBalance(PreferenceConnector.readString(svContext, PreferenceConnector.TOTALINCOME, "0"));
        todayCollection.setText(GlobalVariables.CURRENCYSYMBOL + todayBal);
        totalCollection.setText(GlobalVariables.CURRENCYSYMBOL + totalBal);

        GridLayoutManager layoutManagerReports = new GridLayoutManager(svContext, 3);
        for (int j = 0; j < selectedItemReports.length; j++) {
            lstDashBoardReports.add(new DashboardModel(selectedItemReports[j], allDrawableReports[j], false,0));
        }
        rvDashReports.setLayoutManager(layoutManagerReports);
        rvDashReports.setHasFixedSize(true);
        DashboardAdapter mAdapterReports = new DashboardAdapter(svContext, lstDashBoardReports, animation_type);
        rvDashReports.setAdapter(mAdapterReports);
        mAdapterReports.setOnItemClickListener((view, obj, position) -> {
            ActivityMain.onDrawerItemClick(obj, svContext);
        });

        GridLayoutManager layoutManagerIncome = new GridLayoutManager(svContext, 3);
        for (int j = 0; j < selectedItemIncome.length; j++) {
            lstDashBoardIncome.add(new DashboardModel(selectedItemIncome[j], allDrawableIncome[j], false,0));
        }
        rvDashIncome.setLayoutManager(layoutManagerIncome);
        rvDashIncome.setHasFixedSize(true);
        DashboardAdapter mAdapterIncome = new DashboardAdapter(svContext, lstDashBoardIncome, animation_type);
        rvDashIncome.setAdapter(mAdapterIncome);
        mAdapterIncome.setOnItemClickListener((view, obj, position) -> {
            ActivityMain.onDrawerItemClick(obj, svContext);
        });
    }

    private String getFormatBalance(String bal) {
        try {
            double total = Double.parseDouble(bal);
            String strTotalBal = String.format("%.2f", total);
            return strTotalBal;
        } catch (Exception e) {
            e.printStackTrace();
            return bal;
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
}