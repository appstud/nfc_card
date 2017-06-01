package com.appstud.parking;

import android.content.Intent;
import android.nfc.cardemulation.HostApduService;
import android.os.Bundle;
import android.util.Log;


public class MyHostApduService extends HostApduService {

    public static final String IS_PARKING_EXIT = "IS_PARKING_EXIT";
    private int messageCounter = 0;
    private static String OK = "client 123";
    private static String NOK = "Nothing to say";

    private static boolean exit = false;

    @Override
    public byte[] processCommandApdu(byte[] apdu, Bundle extras) {
        if (selectAidApdu(apdu)) {
            Log.i("HCEDEMO", OK);
            alertApp();
            return getMessage();
        } else {
            Log.i("HCEDEMO", NOK + " Received apdu: " + new String(apdu));
            return getNextMessage();
        }
    }

    private byte[] getMessage() {
        return OK.getBytes();
    }

    private byte[] getNextMessage() {
        return NOK.getBytes();
    }

    private boolean selectAidApdu(byte[] apdu) {
        return apdu.length >= 2 && apdu[0] == (byte) 0 && apdu[1] == (byte) 0xa4;
    }

    @Override
    public void onDeactivated(int reason) {
        Log.i("HCEDEMO", "Communication NFC Deactivated: " + reason);
    }

    private void alertApp() {

        Intent intent = new Intent();
        intent.setAction(getString(R.string.broadcast_notification_type));

        intent.putExtra(IS_PARKING_EXIT, exit);
        exit = !exit;

        sendBroadcast(intent);


    }
}