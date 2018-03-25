package com.umawallet.helper;

/**
 * Created by Shriji on 2/17/2018.
 */

public class AppConstants {
    private static final String BASE_HOST = String.format("%s", "http://umawallet.com/api/");
    public static String ResponseSuccess = "1";
    public static String DEFAULT_STRING = "";
    public static int FOOTER_HOME = 1;
    public static int FOOTER_STATES = 2;
    public static int FOOTER_SETTINGS = 3;

    public static String getBaseHost() {
        return BASE_HOST;
    }
}
