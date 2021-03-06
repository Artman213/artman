package ua.in.zeusapps.mediar.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EventRequest {
    @Expose
    @SerializedName("center_latitude")
    private float _latitude;
    @Expose
    @SerializedName("center_longitude")
    private float _longitude;
    @Expose
    @SerializedName("radius")
    private float _radius;

    public EventRequest(float latitude, float longitude, float radius) {
        _latitude = latitude;
        _longitude = longitude;
        _radius = radius;
    }

    public double getLatitude() {
        return _latitude;
    }

    public void setLatitude(float latitude) {
        _latitude = latitude;
    }

    public double getLongitude() {
        return _longitude;
    }

    public void setLongitude(float longitude) {
        _longitude = longitude;
    }

    public float getRadius() {
        return _radius;
    }

    public void setRadius(float radius) {
        _radius = radius;
    }
}
