package com.mihirathwa.cse546.eventson_the_go;

import android.*;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.vision.face.Face;
import com.mihirathwa.cse546.events_backend.facebookGraphApi.model.FacebookGraph;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Version;
import com.restfb.types.Event;
import com.restfb.types.User;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public class EventsMapActivity extends AppCompatActivity implements OnMapReadyCallback, EndpointResponse {

    private FacebookEvents facebookEvents = new FacebookEvents(EventsMapActivity.this);

    private static final String TAG = "EventsMapsActivity";
    static final LatLng tutorial = new LatLng(33.419764, -111.915930);
    private String fbAccessToken = "";
    private List<RestFBEventsObject> userEvents;

    private MapFragment googleMap;
    private GoogleMap eventMap;

    private double myLatitude;
    private double myLongitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_map);

        facebookEvents.delegate = this;

        Intent intent = getIntent();
        fbAccessToken = intent.getStringExtra("fbAccessToken");

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

        if (eventMap != null) {
            eventMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                @Override
                public View getInfoWindow(Marker marker) {
                    return null;
                }

                @Override
                public View getInfoContents(Marker marker) {
                    View view = getLayoutInflater().inflate(R.layout.marker_info_window, null);

                    TextView name = (TextView) view.findViewById(R.id.MIW_Name);
                    TextView description = (TextView) view.findViewById(R.id.MIW_Description);

                    name.setText(marker.getTitle());
                    description.setText(marker.getSnippet());

                    return view;
                }
            });
        }

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
                FacebookGraph facebookGraph = new FacebookGraph();

                myLatitude = location.getLatitude();
                myLongitude = location.getLongitude();

                facebookGraph.setAccessToken(fbAccessToken);
                facebookGraph.setLatitude(Double.toString(myLatitude));
                facebookGraph.setLongitude(Double.toString(myLongitude));

                new FacebookGraphEndpoint(this).execute(facebookGraph);

                LatLng myLocation = new LatLng(myLatitude, myLongitude);
                CameraUpdate goToMyLocation = CameraUpdateFactory.newLatLngZoom(myLocation, 13);
                eventMap.animateCamera(goToMyLocation);

                //Getting current location locality
                Geocoder geocoder = new Geocoder(this);

                try {
                    List<Address> list = geocoder
                            .getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                    Address address = list.get(0);
                    String locality = address.getLocality();

                    Toast.makeText(this, "Locality: " + locality, Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            //Set Event Markers here
            EventObject eventObject = new EventObject();
            eventObject.setToken(fbAccessToken);
            eventObject.setLatitude(myLatitude);
            eventObject.setLongitude(myLongitude);
            eventObject.setMap(eventMap);

            facebookEvents.execute(eventObject);

            //demo marker
//            eventMap.addMarker(new MarkerOptions()
//            .position(tutorial)
//            .title("Tutorial Place")
//            .snippet("Long Press to get Details!"));

        } else {
            ActivityCompat.requestPermissions(this, new String[] {
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            }, 1);
        }

        eventMap.setOnInfoWindowLongClickListener(new GoogleMap.OnInfoWindowLongClickListener() {
            @Override
            public void onInfoWindowLongClick(Marker marker) {

                String eventName = marker.getTitle();
                String category = "";
                String description = "";
                Date startDate = null;
                String coverUrl = "";

                for (RestFBEventsObject object: userEvents) {
                    if (object.getName().contains(eventName)) {
                        category = object.getCategory();
                        description = object.getDescription();
                        startDate = object.getStartTime();
                        coverUrl = object.getCoverUrl();
                    }
                }

                Intent intent = new Intent(EventsMapActivity.this, PopUpActivity.class);
                intent.putExtra("name", eventName);
                intent.putExtra("category", category);
                intent.putExtra("description", description);
                intent.putExtra("startDate", startDate);
                intent.putExtra("coverUrl", coverUrl);

                startActivity(intent);
//                new EndpointsAsyncTask(EventsMapActivity.this).execute();
            }
        });
    }

    @Override
    public void processFinish(List<RestFBEventsObject> restFBEventsObjects) {
        userEvents = restFBEventsObjects;
    }
}
