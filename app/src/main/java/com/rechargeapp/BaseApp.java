package com.rechargeapp;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

import com.commonutility.GlobalData;
import com.commonutility.GlobalVariables;
import com.commonutility.PreferenceConnector;
import com.codunite.rechargeapp.BuildConfig;

public class BaseApp extends Application {
    private static final int SCHEMA_VERSION = 0;
    public static final String TAG = BaseApp.class.getSimpleName();
    private static BaseApp mInstance;

    public static BaseApp getInstance(Context context) {
        return (BaseApp) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        PreferenceConnector.writeString(this, PreferenceConnector.DEVICE_ID, GlobalData.getDeviceId(this));
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);

        loadBaseUrl();
    }

    private void loadBaseUrl() {
        switch (BuildConfig.FLAVOR) {
            case GlobalVariables.FLAVOR_KMPAISA:
                GlobalVariables.PRE_URL_MAIN = "https://www.kmpaisa.com/";
                GlobalVariables.PRE_URL = GlobalVariables.PRE_URL_MAIN + "service/WebNew/";
                break;
            case GlobalVariables.FLAVOR_RECHARGETRADE:
                GlobalVariables.PRE_URL_MAIN = "https://www.rechargetrade.online/";
                GlobalVariables.PRE_URL = GlobalVariables.PRE_URL_MAIN + "service/WebNew/";
                break;
        }
    }
}
