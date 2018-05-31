package ua.in.zeusapps.mediar.models;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ARPoint implements Parcelable{
    @SerializedName("altitude") @Expose private double altitude;
    @SerializedName("floor") @Expose private int floor;
    @SerializedName("description") @Expose private String description;
    @SerializedName("coordinate") @Expose private Coordinate coordinate;

    protected ARPoint(Parcel in) {
        altitude = in.readDouble();
        floor = in.readInt();
        description = in.readString();
        coordinate = in.readParcelable(Coordinate.class.getClassLoader());
    }

    public static final Creator<ARPoint> CREATOR = new Creator<ARPoint>() {
        @Override
        public ARPoint createFromParcel(Parcel in) {
            return new ARPoint(in);
        }

        @Override
        public ARPoint[] newArray(int size) {
            return new ARPoint[size];
        }
    };

    public double getAltitude() {
        return altitude;
    }
    public void setAltitude(double alt) {
        this.altitude = altitude;
    }

    public int getFloor() {
        return floor;
    }
    public void setFloor(int floor) {
        this.floor = floor;
    }

    public String getDescription() {
        return description;
    }
    public void setFloor(String description) {
        this.description = description;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }
    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public Location getLocation() {
        if (getCoordinate().getLatitude() != 0 && getCoordinate().getLongitude() != 0) {
            Location location = new Location("");
            location.setLatitude(getCoordinate().getLatitude());
            location.setLongitude(getCoordinate().getLongitude());
            location.setAltitude(getAltitude());
            return location;
        } else {
            return null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(altitude);
        dest.writeInt(floor);
        dest.writeString(description);
        dest.writeParcelable(coordinate, flags);
    }
}
