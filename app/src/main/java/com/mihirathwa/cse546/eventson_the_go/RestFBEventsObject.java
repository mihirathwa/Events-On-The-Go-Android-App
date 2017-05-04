package com.mihirathwa.cse546.eventson_the_go;

import java.util.Date;
import java.util.List;

/**
 * Created by Mihir on 05/04/2017.
 */

public class RestFBEventsObject {
    private String FbToken;
    private String Id;
    private String name;
    private String description;
    private String category;
    private Date startTime;
    private Date endTime;
    private double latitude;
    private double longitude;

    private int attendingCount;
    private int declinedCount;
    private long interestedCount;
    private int maybeCount;
    private int noReplyCount;

    private String coverUrl;
    private String profileUrl;
    private String ticketUrl;

    private String hotness;
    private List<String> tweets;

    public String getFbToken() {
        return FbToken;
    }

    public void setFbToken(String fbToken) {
        FbToken = fbToken;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getAttendingCount() {
        return attendingCount;
    }

    public void setAttendingCount(int attendingCount) {
        this.attendingCount = attendingCount;
    }

    public int getDeclinedCount() {
        return declinedCount;
    }

    public void setDeclinedCount(int declinedCount) {
        this.declinedCount = declinedCount;
    }

    public long getInterestedCount() {
        return interestedCount;
    }

    public void setInterestedCount(long interestedCount) {
        this.interestedCount = interestedCount;
    }

    public int getMaybeCount() {
        return maybeCount;
    }

    public void setMaybeCount(int maybeCount) {
        this.maybeCount = maybeCount;
    }

    public int getNoReplyCount() {
        return noReplyCount;
    }

    public void setNoReplyCount(int noReplyCount) {
        this.noReplyCount = noReplyCount;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public String getTicketUrl() {
        return ticketUrl;
    }

    public void setTicketUrl(String ticketUrl) {
        this.ticketUrl = ticketUrl;
    }

    public String getHotness() {
        return hotness;
    }

    public void setHotness(String hotness) {
        this.hotness = hotness;
    }

    public List<String> getTweets() {
        return tweets;
    }

    public void setTweets(List<String> tweets) {
        this.tweets = tweets;
    }
}
