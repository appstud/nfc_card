package com.appstud.parking;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.appstud.parking.MyHostApduService.IS_PARKING_EXIT;

public class NFCActivity extends AppCompatActivity {

    @BindView(R.id.parking_ticket_details)
    LinearLayout parkingTicketDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc);
        ButterKnife.bind(this);

        if (isParkingExit()) {
            parkingTicketDetails.setVisibility(View.VISIBLE);
        } else {
            parkingTicketDetails.setVisibility(View.GONE);
        }
    }

    private boolean isParkingExit() {
        return getIntent().getBooleanExtra(IS_PARKING_EXIT, false);
    }

    @OnClick(R.id.parking_close)
    void closeConfirmation() {
        this.finish();
    }
}
