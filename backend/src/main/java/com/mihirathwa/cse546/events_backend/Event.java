package com.mihirathwa.cse546.events_backend;

import java.util.Date;
import java.util.List;

/**
 * Created by Mihir on 05/03/2017.
 */

public class Event {

    private String token;
    private String id;
    private String name;
    private String description;
    private EventPlace place;
    private Date startTime;
    private Date endTime;
    private List<String> tweets;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public EventPlace getPlace() {
        return place;
    }

    public void setPlace(EventPlace place) {
        this.place = place;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public List<String> getTweets() {
        return tweets;
    }

    public void setTweets(List<String> tweets) {
        this.tweets = tweets;
    }
}
