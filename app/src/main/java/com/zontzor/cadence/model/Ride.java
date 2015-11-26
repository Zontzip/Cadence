package com.zontzor.cadence.model;

/**
 * Entity for a ride
 */
public class Ride {
    private int rideId;
    private String rideName;
    private String rideDate;
    private int rideDuration;
    private int rideDistance;
    private int userId;
    private int bicycleId;

    public Ride(int rideId, String rideName, String rideDate, int rideDuration, int rideDistance,
                int userId, int bicycleId) {
        setRideId(rideId);
        setRideName(rideName);
        setRideDate(rideDate);
        setRideDuration(rideDuration);
        setRideDistance(rideDistance);
        setUserId(userId);
        setBicycleId(bicycleId);
    }

    public int getRideId() {
        return rideId;
    }

    public void setRideId(int rideId) {
        this.rideId = rideId;
    }

    public String getRideName() {
        return rideName;
    }

    public void setRideName(String rideName) {
        this.rideName = rideName;
    }

    public String getRideDate() {
        return rideDate;
    }

    public void setRideDate(String rideDate) {
        this.rideDate = rideDate;
    }

    public int getRideDuration() {
        return rideDuration;
    }

    public void setRideDuration(int rideDuration) {
        this.rideDuration = rideDuration;
    }

    public int getRideDistance() {
        return rideDistance;
    }

    public void setRideDistance(int rideDistance) {
        this.rideDistance = rideDistance;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBicycleId() {
        return bicycleId;
    }

    public void setBicycleId(int bicycleId) {
        this.bicycleId = bicycleId;
    }
}
