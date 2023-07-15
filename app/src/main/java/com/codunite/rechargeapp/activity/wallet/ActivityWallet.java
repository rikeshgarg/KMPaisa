package com.codunite.rechargeapp.activity.wallet;

import static com.codunite.rechargeapp.activity.ActivityMain.E_WALLET_MYFUNDREQUEST;
import static com.codunite.rechargeapp.activity.ActivityMain.R_WALLET_MYFUNDREQUEST;
import static com.codunite.rechargeapp.activity.ActivityMain.ShowBalance;
import static com.codunite.rechargeapp.activity.ActivityMain.ShoweBalance;
import static com.codunite.rechargeapp.activity.ActivityMain.UPI_ADD_MONEY;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codunite.commonutility.PreferenceConnector;
import com.codunite.rechargeapp.R;
import com.codunite.rechargeapp.activity.ActivityMain;
import com.codunite.rechargeapp.adapter.DashboardAdapter;
import com.codunite.rechargeapp.model.DashboardModel;

import java.util.ArrayList;
import java.util.List;

public class ActivityWallet extends AppCompatActivity {
    private Context svContext;

    public static String[] selectedItem = {UPI_ADD_MONEY, "R-Wallet Transfer", "R-Wallet History", R_WALLET_MYFUNDREQUEST};
    private int[] allDrawable = {R.drawable.ic_upi_add_money, R.drawable.ic_r_wallet_transfer, R.drawable.ic_r_wallet_history, R.drawable.ic_fund_request};
    private int[] allDrawableBgColor = {R.color.bg_postpaid, R.color.bg_prepaid, R.color.bg_datacard, R.color.bg_metro, R.color.bg_dth, R.color.bg_fasttag, R.color.bg_dth, R.color.bg_postpaid};

    public static String[] selectedItem1 = {UPI_ADD_MONEY, "E-Wallet Transfer", "E-Wallet History", E_WALLET_MYFUNDREQUEST};
    private int[] allDrawable1 = {R.drawable.ic_upi_add_money, R.drawable.ic_e_wallet_transfer, R.drawable.ic_e_wallet_history, R.drawable.ic_fund_request};
    private int[] allDrawableBgColor1 = {R.color.bg_postpaid, R.color.bg_dth, R.color.bg_fasttag, R.color.bg_metro, R.color.bg_dth, R.color.bg_fasttag, R.color.bg_dth, R.color.bg_postpaid};
    private RecyclerView rv_r_wallet,rv_e_wallet;
    TextView tv_r_wallet_balance,tv_e_wallet_balance;
    private List<DashboardModel> lstDashBoard = new ArrayList<>();
    private List<DashboardModel> lstDashBoarde = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_wallet);
        resumeApp();
    }

    public void resumeApp() {
        svContext = this;
        tv_r_wallet_balance = (TextView)findViewById(R.id.tv_r_wallet_balance);
        tv_e_wallet_balance = (TextView)findViewById(R.id.tv_e_wallet_balance);
        rv_r_wallet= (RecyclerView)findViewById(R.id.rv_r_wallet);
        rv_e_wallet= (RecyclerView)findViewById(R.id.rv_e_wallet);
        tv_r_wallet_balance.setText(ShowBalance(svContext));
        tv_e_wallet_balance.setText(ShoweBalance(svContext));

        loadToolBar();
        for (int j = 0; j < selectedItem.length; j++) {
            lstDashBoard.add(new DashboardModel(selectedItem[j], allDrawable[j], false, allDrawableBgColor[j]));
        }
        for (int j = 0; j < selectedItem1.length; j++) {
            lstDashBoarde.add(new DashboardModel(selectedItem1[j], allDrawable1[j], false, allDrawableBgColor1[j]));
        }

        GridLayoutManager layoutManager = new GridLayoutManager(svContext, 3);
        rv_r_wallet.setLayoutManager(layoutManager);
        rv_r_wallet.setHasFixedSize(true);

        DashboardAdapter mAdapter = new DashboardAdapter(svContext, lstDashBoard, 0);
        rv_r_wallet.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new DashboardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, String obj, int position) {
                ActivityMain.onDrawerItemClick(obj, view.getContext());
            }
        });


        GridLayoutManager layoutManagere = new GridLayoutManager(svContext, 3);
        rv_e_wallet.setLayoutManager(layoutManagere);
        rv_e_wallet.setHasFixedSize(true);

        DashboardAdapter mAdaptere = new DashboardAdapter(svContext, lstDashBoarde, 0);
        rv_e_wallet.setAdapter(mAdaptere);

        mAdaptere.setOnItemClickListener(new DashboardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, String obj, int position) {
                ActivityMain.onDrawerItemClick(obj, view.getContext());
            }
        });

    }
    private void loadToolBar() {
        ImageView imgToolBarBack = (ImageView) findViewById(R.id.img_back);
        imgToolBarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        TextView txtHeading = (TextView) findViewById(R.id.heading);
        txtHeading.setText("Wallet");
    }
}
