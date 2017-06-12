package com.appstud.parking.utils;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

public class FileHelper {

    private static final String TAG = FileHelper.class.toString();

    private FileHelper() {
    }

    public static String loadStringFromFile(InputStream is) {
        String result;
        try {

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            result = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            Log.e(TAG, "loadStringFromFile: " + ex.getMessage());
            return null;
        }
        return result;

    }
}
