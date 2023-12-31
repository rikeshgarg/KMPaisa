package com.commonutility;

import android.os.Environment;

import java.io.File;

public class GlobalVariables {
    public static final String defaultAppPath = Environment.getExternalStorageDirectory().getAbsolutePath() +
            File.separator + "Recharge/";

    public static final String CUSTOMFONTNAME       = "font/font_medium.ttf";
    public static final String CUSTOMFONTNAMEBOLD   = "font/font_bold.ttf";
    public static final String UPIPAYMENTGATEWAYKEY = "a69f485a-8d0c-41b2-bd71-f79b46c09cbd";
    public static final String FLAVOR_SONIKAPAY = "sonikapay";
    public static final String FLAVOR_SONIKAPAY_CHILD = "sonikapaychild";
    public static String ORDERID = "";
    public final static int REQUEST_WRITE_STORAGE_PERMISSION = 24564;
//    public static String PRE_URL_MAIN = "https://www.sonikapay.com/";
//    public static String PRE_URL = PRE_URL_MAIN + "appservice/auth/";
    public static String PRE_URL_MAIN = "https://www.kmpaisa.com/";
    public static String PRE_URL = PRE_URL_MAIN + "service/WebNew/";

    public static final String CURRENCYSYMBOL = "₹ ";
    public static final boolean ISTESTING = true;
    public static final String TAGPOSTTEXT = ".............tagprint..............";

    public static String TOKEN = "";
    public static String DEVICEID = "";
}