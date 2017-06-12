package com.appstud.parking.nfcservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.appstud.parking.entry.NFCActivity;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intentone = new Intent(context.getApplicationContext(), NFCActivity.class);
        intentone.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        intentone.putExtra(MyHostApduService.IS_PARKING_EXIT, intent.getBooleanExtra(MyHostApduService.IS_PARKING_EXIT, false));
        context.startActivity(intentone);


    }
}
