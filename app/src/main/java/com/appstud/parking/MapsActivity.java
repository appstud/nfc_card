package com.appstud.parking;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

import butterknife.ButterKnife;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback, GoogleMap.OnMarkerClickListener {

    private static final String TAG = MapsActivity.class.toString();
    /**
     * Request code for location permission request.
     *
     * @see #onRequestPermissionsResult(int, String[], int[])
     */
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    /**
     * Flag indicating whether a requested permission has been denied after returning in
     * {@link #onRequestPermissionsResult(int, String[], int[])}.
     */
    private boolean mPermissionDenied = false;


    private GoogleMap mMap;
    private JSONArray listPlaces;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        ButterKnife.bind(this);

        loadParkingPlaces();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void loadParkingPlaces() {
        //load json list of places
        listPlaces = new JSONArray();
        try {
            InputStream is = getAssets().open("parkings.json");
            String jsonString = FileHelper.loadStringFromFile(is);
            JSONObject json = new JSONObject(jsonString);
            listPlaces = json.getJSONArray("data");
        } catch (IOException | JSONException e) {
            Log.e(TAG, "loadParkingPlaces: " + e.getMessage());
            e.printStackTrace();
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMarkerClickListener(this);

        int paddingInPixels = UIUtils.dpToPx(56, getResources().getDisplayMetrics());
        mMap.setPadding(0, paddingInPixels, 0, paddingInPixels);
        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = mMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.style_json));

            if (!success) {
                Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }

        // move the camera to Toulouse
        LatLng toulouse = new LatLng(43.604408, 1.446228);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(toulouse));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(15));

        addParkingMarkers();


        enableMyLocation();
    }


    /**
     * Enables the My Location layer if the fine location permission has been granted.
     */
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (mMap != null) {
            // Access to the location has been granted to the app.
            mMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation();
        } else {
            // Display the missing permission error dialog when the fragments resume.
            mPermissionDenied = true;
        }
    }


    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (mPermissionDenied) {
            // Permission was not granted, display error dialog.
            showMissingPermissionError();
            mPermissionDenied = false;
        }
    }

    /**
     * Displays a dialog with error message explaining that the location permission is missing.
     */
    private void showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog
                .newInstance(true).show(getSupportFragmentManager(), "dialog");
    }

    private void addParkingMarkers() {
        for (int i = 0; i < listPlaces.length(); i++) {
            JSONObject jsonPlace = new JSONObject();
            try {
                jsonPlace = (JSONObject) listPlaces.get(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Double latitude = null;
            Double longitude = null;
            String title = null;
            String type = null;
            String content = null;
            String picture = null;
            try {
                latitude = jsonPlace.getDouble("latitude");
                longitude = jsonPlace.getDouble("longitude");
                title = jsonPlace.getString("title");
                type = jsonPlace.getString("type");
                content = jsonPlace.getString("content");
                picture = "";
            } catch (JSONException e) {
                e.printStackTrace();
            }

            final PlaceModel placeTmp = new PlaceModel(
                    latitude,
                    longitude,
                    title,
                    type,
                    content,
                    picture
            );

            final MarkerOptions options = new MarkerOptions().position(new LatLng(placeTmp.getLatitude(), placeTmp.getLongitude())).title(placeTmp.getName());

            int sizeMarkerPx = UIUtils.dpToPx(60, getResources().getDisplayMetrics());

            int[] pictures = {R.drawable.capitole, R.drawable.carmes, R.drawable.esquirol};

            Glide.with(getApplicationContext()).load(pictures[i % 3])
                    .asBitmap()
                    .centerCrop()
                    .into(new SimpleTarget<Bitmap>(sizeMarkerPx, sizeMarkerPx) {
                        @Override
                        public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {

                            options.icon(BitmapDescriptorFactory.fromBitmap(bitmap));
                            Marker markerTmp = mMap.addMarker(options);
                            markerTmp.setTag(placeTmp);
                        }
                    });

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        nfcAdapter.disableReaderMode(this);
        nfcAdapter.disableForegroundDispatch(this);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        PlaceModel tmpPlaceModel = (PlaceModel) marker.getTag();

        if (tmpPlaceModel.getContent() != null) {
            BottomSheetDialogFragment bottomSheetDialogFragment = new ParkingDetailsBottomSheetDialogFragment();
            bottomSheetDialogFragment.show(getSupportFragmentManager(), bottomSheetDialogFragment.getTag());
        }

        return false;
    }
}
