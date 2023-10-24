package com.rechargeapp.activity.wallet;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codunite.rechargeapp.R;
import com.rechargeapp.activity.ActivityMain;
import com.rechargeapp.adapter.DashboardAdapter;
import com.rechargeapp.model.DashboardModel;

import java.util.ArrayList;
import java.util.List;

public class ActivityWallet extends AppCompatActivity {
    private Context svContext;

    public static String[] selectedItem = {ActivityMain.UPI_ADD_MONEY, "R-Wallet Transfer", "R-Wallet History", ActivityMain.R_WALLET_MYFUNDREQUEST};
    private int[] allDrawable = {R.drawable.ic_upi_add_money, R.drawable.ic_r_wallet_transfer, R.drawable.ic_r_wallet_history, R.drawable.ic_fund_request};
    private int[] allDrawableBgColor = {R.color.bg_postpaid, R.color.bg_prepaid, R.color.bg_datacard, R.color.bg_metro, R.color.bg_dth, R.color.bg_fasttag, R.color.bg_dth, R.color.bg_postpaid};

    public static String[] selectedItem1 = {ActivityMain.UPI_ADD_MONEY, "E-Wallet Transfer", "E-Wallet History", ActivityMain.E_WALLET_MYFUNDREQUEST};
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
        tv_r_wallet_balance.setText(ActivityMain.ShowBalance(svContext));
        tv_e_wallet_balance.setText(ActivityMain.ShoweBalance(svContext));

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
                ActivityMain.onDrawerItemClick(obj, view.getContext(),"R-Wallet");
                //ActivityMain.onWalletDrawerItemClick(obj, view.getContext(),"1");
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
                ActivityMain.onDrawerItemClick(obj, view.getContext(),"E-Wallet");
                //ActivityMain.onWalletDrawerItemClick(obj, view.getContext(),"1");
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
