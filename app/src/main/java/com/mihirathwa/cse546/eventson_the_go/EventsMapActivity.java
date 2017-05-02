package com.mihirathwa.cse546.eventson_the_go;

import android.*;
import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class EventsMapActivity extends AppCompatActivity implements OnMapReadyCallback {

    static final LatLng tutorial = new LatLng(40, -39);

    private MapFragment googleMap;
    private GoogleMap eventMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_map);

        try {
            if (googleMap == null) {
                googleMap = (MapFragment) getFragmentManager().findFragmentById(R.id.AEM_MapFragment);
                googleMap.getMapAsync(this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        eventMap = googleMap;

        eventMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        eventMap.setTrafficEnabled(true);
        eventMap.setBuildingsEnabled(true);
        eventMap.getUiSettings().setZoomControlsEnabled(true);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            eventMap.setMyLocationEnabled(true);
            eventMap.getUiSettings().setMyLocationButtonEnabled(true);

            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            Criteria criteria = new Criteria();
            String provider = locationManager.getBestProvider(criteria, true);
            Location location = locationManager.getLastKnownLocation(provider);

            if (location != null) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();

                LatLng myLocation = new LatLng(latitude, longitude);
                CameraUpdate goToMyLocation = CameraUpdateFactory.newLatLngZoom(myLocation, 1);
                eventMap.animateCamera(goToMyLocation);
            } else {
                ActivityCompat.requestPermissions(this, new String[] {
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                }, 1);
            }

            //Set Event Markers here

            //demo marker
            eventMap.addMarker(new MarkerOptions()
            .position(tutorial)
            .title("Tutorial Place")
            .snippet("Long Press to get Details!"));
        }

        eventMap.setOnInfoWindowLongClickListener(new GoogleMap.OnInfoWindowLongClickListener() {
            @Override
            public void onInfoWindowLongClick(Marker marker) {
                Toast.makeText(EventsMapActivity.this, "Clicked Details", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
