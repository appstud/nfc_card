package com.appstud.parking;


import android.util.DisplayMetrics;

public class UIUtils {
    public static int dpToPx(int dp, DisplayMetrics displayMetrics){
        return dp * (displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }
}
