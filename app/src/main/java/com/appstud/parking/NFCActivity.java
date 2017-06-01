package com.appstud.parking;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class NFCActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.parking_close)
    void closeConfirmation() {
        this.finish();
    }
}
