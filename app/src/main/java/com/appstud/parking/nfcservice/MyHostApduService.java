package com.appstud.parking.nfcservice;

import android.content.Intent;
import android.nfc.cardemulation.HostApduService;
import android.os.Bundle;
import android.util.Log;

import com.appstud.parking.R;


public class MyHostApduService extends HostApduService {

    public static final String IS_PARKING_EXIT = "IS_PARKING_EXIT";
    private static final String TAG = MyHostApduService.class.toString();
    private static String OK_CLIENT = "client 123";
    private static String NOK_CLIENT = "Nothing to say";

    private static boolean exit = false;

    @Override
    public byte[] processCommandApdu(byte[] apdu, Bundle extras) {
        if (selectAidApdu(apdu)) {
            Log.i(TAG, OK_CLIENT);
            alertApp();
            return getMessage();
        } else {
            Log.i(TAG, NOK_CLIENT + " Received apdu: " + new String(apdu));
            return getNextMessage();
        }
    }

    private byte[] getMessage() {
        return OK_CLIENT.getBytes();
    }

    private byte[] getNextMessage() {
        return NOK_CLIENT.getBytes();
    }

    private boolean selectAidApdu(byte[] apdu) {
        return apdu.length >= 2 && apdu[0] == (byte) 0 && apdu[1] == (byte) 0xa4;
    }

    @Override
    public void onDeactivated(int reason) {
        Log.i(TAG, "Communication NFC Deactivated: " + reason);
    }

    private void alertApp() {

        Intent intent = new Intent();
        intent.setAction(getString(R.string.broadcast_notification_type));

        intent.putExtra(IS_PARKING_EXIT, exit);
        exit = !exit;

        sendBroadcast(intent);


    }
}