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

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codunite.rechargeapp.activity.mainwallet.ActivityFundRequest;
import com.codunite.rechargeapp.activity.ActivityMain;
import com.codunite.rechargeapp.activity.mainwallet.ActivityMainWalletTransfer;
import com.codunite.rechargeapp.activity.reports.ActivityWalletHistory;
import com.codunite.rechargeapp.R;
import com.codunite.rechargeapp.activity.ActivityWithdrawalRequest;
import com.codunite.rechargeapp.adapter.DashboardAdapter;
import com.codunite.rechargeapp.model.DashboardModel;
import com.codunite.commonutility.GlobalVariables;
import com.codunite.commonutility.ItemAnimation;
import com.codunite.commonutility.ShowCustomToast;
import com.codunite.commonutility.WebService;
import com.codunite.commonutility.WebServiceListener;
import com.codunite.commonutility.customfont.FontUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class FragProfile extends Fragment implements OnClickListener, WebServiceListener {
    private View aiView = null;
    private boolean mAlreadyLoaded = false;
    private List<DashboardModel> lstDashBoard = new ArrayList<>();
    private List<DashboardModel> lstDashBoardwallet = new ArrayList<>();

    public static String[] selectedItem = {"My QR Code", "Personal Profile", "Change Password", "Change T-Pin",
    "Add Nominee", "Change Account"};
    private int[] allDrawable = {R.drawable.per_qr,R.drawable.per_profile, R.drawable.per_change_pass,
            R.drawable.per_change_pin, R.drawable.per_change_pin, R.drawable.per_change_pin};

    public static String[] walletTransferItemList = {"Add Money", "Withdraw", "Wallet Transfer", "Report"};
    private int[] allDrawablewallet = {R.drawable.ic_addfund, R.drawable.ic_withdraw, R.drawable.ic_wallettransfer, R.drawable.ic_report};

    private List<DashboardModel> lstDashBoardGeneology = new ArrayList<>();
    public final String[] selectedItemGeneology = {"Search Downline", "Direct Downline", "Direct Active",
            "Direct Deactive", "Total Downline", "Total Active", "Total Deactive", "Upline Details",
            "Rank Wise Team", "Level Wise Team"};
    private int[] allDrawableGeneology = {R.drawable.searchicon, R.drawable.ic_downline, R.drawable.ic_downline,
            R.drawable.ic_downline, R.drawable.ic_downline, R.drawable.ic_downline,
            R.drawable.ic_downline, R.drawable.ic_downline,
            R.drawable.ic_downline, R.drawable.ic_downline
    };

    public FragProfile() {}

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

    private int animation_type = ItemAnimation.NONE;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
        }
    }

    public void resumeApp() {
        RecyclerView recyclerViewWallet = aiView.findViewById(R.id.rv_dashboard_wallet);
        lstDashBoardwallet = new ArrayList<>();
        for (int j = 0; j < walletTransferItemList.length; j++) {
            lstDashBoardwallet.add(new DashboardModel(walletTransferItemList[j], allDrawablewallet[j], false,0));
        }
        GridLayoutManager layoutManagerWallet = new GridLayoutManager(svContext, 4);
        recyclerViewWallet.setLayoutManager(layoutManagerWallet);
        recyclerViewWallet.setHasFixedSize(true);

        DashboardAdapter mAdapterWallet = new DashboardAdapter(svContext, lstDashBoardwallet, animation_type);
        recyclerViewWallet.setAdapter(mAdapterWallet);
        mAdapterWallet.setOnItemClickListener((view, itemName, position) -> {
            if ((lstDashBoardwallet.get(position).getName()).equalsIgnoreCase("Add Money")) {
                Intent svIntent = new Intent(svContext, ActivityFundRequest.class);
                svContext.startActivity(svIntent);
            } else if ((lstDashBoardwallet.get(position).getName()).equalsIgnoreCase("Withdraw")) {
                Intent svIntent = new Intent(svContext, ActivityWithdrawalRequest.class);
                startActivity(svIntent);
            } else if ((lstDashBoardwallet.get(position).getName()).equalsIgnoreCase("Report")) {
                Intent svIntent = new Intent(svContext, ActivityWalletHistory.class);
                startActivity(svIntent);
            } else if ((lstDashBoardwallet.get(position).getName()).equalsIgnoreCase("Wallet Transfer")) {
                Intent svIntent = new Intent(svContext, ActivityMainWalletTransfer.class);
                svContext.startActivity(svIntent);
            }
        });

        RecyclerView rvDashGeneology = aiView.findViewById(R.id.rv_dash_geneology);
        for (int j = 0; j < selectedItemGeneology.length; j++) {
            lstDashBoardGeneology.add(new DashboardModel(selectedItemGeneology[j], allDrawableGeneology[j], false,0));
        }
        GridLayoutManager layoutManager = new GridLayoutManager(svContext, 3);
        rvDashGeneology.setLayoutManager(layoutManager);
        rvDashGeneology.setHasFixedSize(true);
        DashboardAdapter mAdapterGeneology = new DashboardAdapter(svContext, lstDashBoardGeneology, animation_type);
        rvDashGeneology.setAdapter(mAdapterGeneology);
        mAdapterGeneology.setOnItemClickListener((view, obj, position) -> {
            ActivityMain.onDrawerItemClick(obj, svContext);
        });

        for (int j = 0; j < selectedItem.length; j++) {
            lstDashBoard.add(new DashboardModel(selectedItem[j], allDrawable[j], false,0));
        }
        RecyclerView recyclerView = aiView.findViewById(R.id.rv_dashboard);
        root.setVisibility(View.VISIBLE);
        GridLayoutManager layoutManagerProfile = new GridLayoutManager(svContext, 3);
        recyclerView.setLayoutManager(layoutManagerProfile);
        recyclerView.setHasFixedSize(true);
        DashboardAdapter mAdapter = new DashboardAdapter(svContext, lstDashBoard, animation_type);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((view, obj, position) -> {
            ActivityMain.onDrawerItemClick(obj, svContext);
        });
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
    public void onWebServiceActionComplete(String result, String url) {}

    @Override
    public void onWebServiceError(String result, String url) {
        customToast.showToast(result, svContext);
    }
}