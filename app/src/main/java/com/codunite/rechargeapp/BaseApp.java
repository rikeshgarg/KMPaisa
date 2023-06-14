package com.codunite.rechargeapp;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

import com.codunite.commonutility.GlobalData;
import com.codunite.commonutility.GlobalVariables;
import com.codunite.commonutility.PreferenceConnector;

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
            case GlobalVariables.FLAVOR_SONIKAPAY:
                GlobalVariables.PRE_URL_MAIN = "https://www.sonikapay.com/";
//                GlobalVariables.PRE_URL_MAIN = "https://www.sonikapay.com/demo/";
                GlobalVariables.PRE_URL = GlobalVariables.PRE_URL_MAIN + "appservice/auth/";
                break;
            case GlobalVariables.FLAVOR_SONIKAPAY_CHILD:
                GlobalVariables.PRE_URL_MAIN = "https://www.sonikapay.com/";
//                GlobalVariables.PRE_URL_MAIN = "https://www.sonikapay.com/demo/";
                GlobalVariables.PRE_URL = GlobalVariables.PRE_URL_MAIN + "appservice/auth/";
                break;
        }
    }
}
