package com.mihirathwa.cse546.eventson_the_go;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.types.CoverPhoto;
import com.restfb.types.Event;
import com.restfb.types.ProfilePictureSource;
import com.restfb.types.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Mihir on 05/03/2017.
 */

public class FacebookEvents extends AsyncTask<EventObject, Integer, List<RestFBEventsObject>> {

    public EndpointResponse delegate = null;

    Context context;
    private static final String TAG = "FacebookEvents";
    String token;
    FacebookClient fbClient;
    GoogleMap eventMap;

    public FacebookEvents(Context context) {
        this.context = context;
    }


    @Override
    protected List<RestFBEventsObject> doInBackground(EventObject... eventObjects) {
        EventObject requestEvent = eventObjects[0];

        String fbToken = requestEvent.getToken();
        Log.d(TAG, "FB Token: " + fbToken);
        Double latitude = requestEvent.getLatitude();
        Double longitude = requestEvent.getLongitude();
        eventMap = requestEvent.getMap();

        fbClient = new DefaultFacebookClient(fbToken, Version.LATEST);

        Connection<Event> allEvents = fbClient.fetchConnection("me/events", Event.class);

        List<RestFBEventsObject> userEvents = EventsToList(allEvents);
        List<RestFBEventsObject> currentEvents = FilterCurrentEvents(userEvents);
        List<RestFBEventsObject> nearbyEvents = FilterDistantEvents(currentEvents, latitude, longitude);

        getAdditionalEventDetails(nearbyEvents);

        return nearbyEvents;
    }

    @Override
    protected void onPostExecute(List<RestFBEventsObject> restFBEventsObjects) {

        for (RestFBEventsObject object: restFBEventsObjects) {
            double latitude = object.getLatitude();
            double longitude = object.getLongitude();

            LatLng position = new LatLng(latitude, longitude);

            eventMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_my_event_icon))
                    .position(position)
                    .title(object.getName())
                    .snippet(object.getDescription()));
        }

        delegate.processFinish(restFBEventsObjects);
    }

    protected List<RestFBEventsObject> EventsToList(Connection<Event> eventsList) {

        List<RestFBEventsObject> userEvents = new ArrayList<RestFBEventsObject>();

        for (int i = 0; i < eventsList.getData().size(); i++) {
            RestFBEventsObject restFBEventsObject = new RestFBEventsObject();

            restFBEventsObject.setId(eventsList.getData().get(i).getId());
            restFBEventsObject.setName(eventsList.getData().get(i).getName());
            restFBEventsObject.setDescription(eventsList.getData().get(i).getDescription());

            if (eventsList.getData().get(i).toString().contains("latitude") &&
                    eventsList.getData().get(i).toString().contains("latitude")) {
                restFBEventsObject.setLatitude(eventsList
                        .getData().get(i).getPlace().getLocation().getLatitude());
                restFBEventsObject.setLongitude(eventsList
                        .getData().get(i).getPlace().getLocation().getLongitude());
                restFBEventsObject.setStartTime(eventsList.getData().get(i).getStartTime());

                userEvents.add(restFBEventsObject);
            }
        }

        return userEvents;
    }

    protected List<RestFBEventsObject> FilterCurrentEvents(List<RestFBEventsObject> userEvents) {

        List<RestFBEventsObject> filteredEvents = userEvents;
        Date todaysDate = new Date();

        for (int i = 0; i < filteredEvents.size(); i++) {
            if (filteredEvents.get(i).getStartTime().before(todaysDate)){
                filteredEvents.remove(i);
            }
        }

        return filteredEvents;
    }

    protected List<RestFBEventsObject> FilterDistantEvents(List<RestFBEventsObject> userEvents,
                                                           double appLatitude,
                                                           double appLongitude) {
        List<RestFBEventsObject> filteredEvents = userEvents;
        for (int i = 0; i < filteredEvents.size(); i++) {
            double greatCircle = distanceCalculator(filteredEvents.get(i).getLatitude(),
                    filteredEvents.get(i).getLongitude(),
                    appLatitude,
                    appLongitude);

            if (greatCircle > 100) {
                filteredEvents.remove(i);
            }

        }

        return filteredEvents;
    }

    protected void getAdditionalEventDetails(List<RestFBEventsObject> userEvents) {

        for (RestFBEventsObject object: userEvents) {
            Event eventObj = fbClient.fetchObject(object.getId(), Event.class,
                    Parameter.with("fields",
                            "attending_count,declined_count,interested_count,maybe_count," +
                                    "noreply_count,cover,category,picture,name"));

            object.setAttendingCount(eventObj.getAttendingCount());
            object.setDeclinedCount(eventObj.getDeclinedCount());
            object.setInterestedCount(eventObj.getInterestedCount());
            object.setMaybeCount(eventObj.getMaybeCount());
            object.setNoReplyCount(eventObj.getNoreplyCount());
            object.setCoverUrl(eventObj.getCover().getSource());
            object.setCategory(eventObj.getCategory());
            object.setProfileUrl(eventObj.getPicture().getUrl());
        }
    }

    // Used Orthodrome for Distance Calculation
    // Reference: https://en.wikipedia.org/wiki/Great-circle_distance
    protected static double distanceCalculator(double eventLatitude, double eventLongitude, double baseLatitude, double baseLongitude) {
        double theta = eventLongitude - baseLongitude;
        double distance = Math.sin(degeeToradian(eventLatitude))
                * Math.sin(degeeToradian(baseLatitude))
                + Math.cos(degeeToradian(eventLatitude))
                * Math.cos(degeeToradian(baseLatitude))
                * Math.cos(degeeToradian(theta));
        return  Math.acos(distance) * 60 * 1.1515;
    }

    protected static double degeeToradian(double degree) {
        return (degree * Math.PI / 180.0);
    }
    protected static double radianTodegree(double radian) {
        return (radian * 180.0 / Math.PI);
    }
}
