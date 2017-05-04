package com.mihirathwa.cse546.eventson_the_go;

import com.google.android.gms.maps.GoogleMap;

/**
 * Created by Mihir on 05/03/2017.
 */

public class EventObject {
    String token;
    Double latitude;
    Double longitude;
    GoogleMap map;

    public GoogleMap getMap() {
        return map;
    }

    public void setMap(GoogleMap map) {
        this.map = map;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
