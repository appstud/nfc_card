package com.appstud.parking;

import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private JSONArray listPlaces;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        ButterKnife.bind(this);

        toolbar.setTitle(R.string.title_activity_maps);
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
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
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

        // move the camera to Toulouse
        LatLng toulouse = new LatLng(43.604408, 1.446228);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(toulouse));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(15));

        addParkingMarkers();
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
            try {
                latitude = jsonPlace.getDouble("latitude");
                longitude = jsonPlace.getDouble("longitude");
                title = jsonPlace.getString("title");
                type = jsonPlace.getString("type");
                content = jsonPlace.getString("content");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            PlaceModel placeTmp = new PlaceModel(
                    latitude,
                    longitude,
                    title,
                    type,
                    content
            );

            MarkerOptions options = new MarkerOptions().position(new LatLng(placeTmp.getLatitude(), placeTmp.getLongitude())).title(placeTmp.getName());

            Marker markerTmp = mMap.addMarker(options);
            markerTmp.setTag(placeTmp);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        nfcAdapter.disableReaderMode(this);
        nfcAdapter.disableForegroundDispatch(this);
    }
}
