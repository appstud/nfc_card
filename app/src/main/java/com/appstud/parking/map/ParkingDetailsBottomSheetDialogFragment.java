package com.appstud.parking.map;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.appstud.parking.R;
import com.appstud.parking.data.model.PlaceModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.appstud.parking.map.MapsActivity.PARKING_DETAILS_KEY;

public class ParkingDetailsBottomSheetDialogFragment extends BottomSheetDialogFragment {

    @BindView(R.id.parking_details_name)
    TextView name;
    @BindView(R.id.parking_details_address)
    TextView address;
    @BindView(R.id.parking_details_picture)
    ImageView picture;


    private BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback = new BottomSheetBehavior.BottomSheetCallback() {

        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismiss();
            }

        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            //do nothing
        }
    };

    PlaceModel getPlace() {
        return getArguments().getParcelable(PARKING_DETAILS_KEY);
    }

    @OnClick(R.id.parking_details_cta)
    void navigateTo() {
        PlaceModel placeModel = getPlace();
        Uri gmmIntentUri = Uri.parse("google.navigation:q=" + placeModel.getLatitude() + "," + placeModel.getLongitude());
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }

    @OnClick(R.id.arrow)
    void dismissModal() {
        this.dismiss();
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View contentView = View.inflate(getContext(), R.layout.parking_dialog_fragment_bottom_sheet, null);
        ButterKnife.bind(this, contentView);

        dialog.setContentView(contentView);

        name.setText(getPlace().getName());
        address.setText(getPlace().getAddress());
        int pictureResource = Integer.parseInt(getPlace().getPicture());
        Drawable drawable = getResources().getDrawable(pictureResource);
        if (null != drawable) {
            picture.setImageDrawable(drawable);
        }

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) contentView.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();

        ((View) contentView.getParent()).setBackgroundColor(getResources().getColor(android.R.color.transparent));

        if (behavior != null && behavior instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(mBottomSheetBehaviorCallback);
        }

    }


}