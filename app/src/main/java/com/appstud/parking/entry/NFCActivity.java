package com.appstud.parking.entry;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.appstud.parking.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.appstud.parking.nfcservice.MyHostApduService.IS_PARKING_EXIT;

public class NFCActivity extends AppCompatActivity {

    @BindView(R.id.exit_description)
    LinearLayout exitDescription;

    @BindView(R.id.entry_description)
    LinearLayout entryDescription;

    @BindView(R.id.parking_big_picture)
    ImageView parkingEntryPicture;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc);
        ButterKnife.bind(this);

        if (isParkingExit()) {
            exitDescription.setVisibility(View.VISIBLE);
            entryDescription.setVisibility(View.GONE);
            parkingEntryPicture.setImageResource(R.drawable.exit);
        } else {
            exitDescription.setVisibility(View.GONE);
            entryDescription.setVisibility(View.VISIBLE);
            parkingEntryPicture.setImageResource(R.drawable.entry);
        }
    }

    private boolean isParkingExit() {
        return getIntent().getBooleanExtra(IS_PARKING_EXIT, false);
    }

    @OnClick(R.id.parking_close)
    void closeConfirmation() {
        this.finish();
    }


    @OnClick(R.id.parking_receipt_btn)
    void getReceipt() {
        Toast.makeText(this, R.string.get_parking_receipt_toast, Toast.LENGTH_SHORT).show();
    }
}
