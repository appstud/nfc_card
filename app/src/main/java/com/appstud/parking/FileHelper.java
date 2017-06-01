package com.appstud.parking;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by stephane on 23/11/2016.
 */

public class FileHelper {
    public static String loadStringFromFile(InputStream is) {
        String result = null;
        try {

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            result = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return result;

    }
}
