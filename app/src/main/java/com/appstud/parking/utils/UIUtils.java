package com.appstud.parking.utils;


import android.util.DisplayMetrics;

public class UIUtils {
    private UIUtils() {
    }

    public static int dpToPx(int dp, DisplayMetrics displayMetrics) {
        return dp * (displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }
}
