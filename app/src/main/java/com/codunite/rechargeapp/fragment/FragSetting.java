package com.codunite.rechargeapp.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codunite.commonutility.GlobalData;
import com.codunite.commonutility.GlobalVariables;
import com.codunite.commonutility.ImageLoading;
import com.codunite.commonutility.PreferenceConnector;
import com.codunite.commonutility.WebServiceListener;
import com.codunite.rechargeapp.R;
import com.codunite.rechargeapp.activity.ActivityMain;
import com.codunite.rechargeapp.activity.profile.ActivityProfile;
import com.codunite.rechargeapp.adapter.SettingMenuAdapter;
import com.codunite.rechargeapp.model.SettingMenu;

import java.util.ArrayList;
import java.util.List;

public class FragSetting extends Fragment implements View.OnClickListener, WebServiceListener {
    private View aiView = null;
    private boolean mAlreadyLoaded = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        aiView = inflater.inflate(R.layout.frag_setting, container, false);
        StartApp();
        resumeApp();
        return aiView;
    }

    private RecyclerView rvMenu;
    private List<SettingMenu> lstSlider = new ArrayList<>();
    public void resumeApp() {
        rvMenu = aiView.findViewById(R.id.rv_setting_menu);
        rvMenu.setLayoutManager(new LinearLayoutManager(svContext, RecyclerView.VERTICAL, false));
        rvMenu.setHasFixedSize(true);

        TextView tvName = aiView.findViewById(R.id.menuheader_name);
        TextView tvMemberId = aiView.findViewById(R.id.menuheader_memberid);
        ImageView avatar = aiView.findViewById(R.id.menuheader_dp);

        tvName.setText(PreferenceConnector.readString(svContext, PreferenceConnector.LOGINUSERNAME, "NA"));
        tvMemberId.setText(PreferenceConnector.readString(svContext, PreferenceConnector.LOGINMEMBERID, "NA"));

        Typeface fontFamily = Typeface.createFromAsset(svContext.getAssets(), GlobalVariables.CUSTOMFONTNAMEBOLD);
        tvName.setTypeface(fontFamily);
        tvMemberId.setTypeface(fontFamily);

        try {
            ImageLoading.loadImages(PreferenceConnector.readString(svContext, PreferenceConnector.LOGINUSERPROFILEPIC, ""), avatar, R.drawable.users);
        } catch (Exception e) {
            e.printStackTrace();
        }

        avatar.setOnClickListener(view -> {
            Intent svIntent = new Intent(svContext, ActivityProfile.class);
            startActivity(svIntent);
        });

        aiView.findViewById(R.id.lay_callcompany).setOnClickListener(view ->
                GlobalData.dialCall(svContext, "contactcompany"));
        aiView.findViewById(R.id.lay_callsponsor).setOnClickListener(view ->
                GlobalData.dialCall(svContext, "contactsponsor"));

        lstSlider.add(new SettingMenu("Create Ticket", R.drawable.ic_round_contacts_24));
        lstSlider.add(new SettingMenu("Refer and Earn", R.drawable.wallet_refer));
        lstSlider.add(new SettingMenu("Rate Us", R.drawable.ic_profile));
        lstSlider.add(new SettingMenu("Account Deletion", R.drawable.ic_profile));
        lstSlider.add(new SettingMenu("Logout", R.drawable.ic_profile));
        SettingMenuAdapter sliderSdapter = new SettingMenuAdapter(svContext, lstSlider);
        sliderSdapter.setOnItemClickListener((view, obj) -> {
            ActivityMain.onDrawerItemClick(obj, svContext);
        });
        rvMenu.setAdapter(sliderSdapter);
    }



    private Context svContext;
    private ViewGroup root;
    private void StartApp() {
        svContext = getActivity();
        root = (ViewGroup) aiView.findViewById(R.id.mylayout);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
        }
    }

    public static void hideFragmentkeyboard(Context meraContext, View meraView) {
        final InputMethodManager imm = (InputMethodManager) meraContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(meraView.getWindowToken(), 0);
    }

    @Override
    public void onWebServiceActionComplete(String result, String url) {

    }

    @Override
    public void onWebServiceError(String result, String url) {
        Log.e("error", result);
    }
}