package ua.in.zeusapps.mediar.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Event implements Parcelable{

    @SerializedName("id") @Expose private int id;
    @SerializedName("title") @Expose private String title;
    @SerializedName("location") @Expose private ARPoint arPoint;
    @SerializedName("start_date") @Expose private String startDate;
    @SerializedName("finish_date") @Expose private String finishDate;
    @SerializedName("card") @Expose private Card card;
    @SerializedName("description") @Expose private String description;

    private String _animationURL;

    protected Event(Parcel in) {
        id = in.readInt();
        title = in.readString();
        arPoint = in.readParcelable(ARPoint.class.getClassLoader());
        startDate = in.readString();
        finishDate = in.readString();
        card = in.readParcelable(Card.class.getClassLoader());
        description = in.readString();
        _animationURL = in.readString();
    }

    public static final Creator<Event> CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public ARPoint getArPoint() {
    return arPoint;
}
    public void setArPoint(ARPoint arPoint) {
        this.arPoint= arPoint;
    }

    public String getStartDate() {
        return startDate;
    }
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getFinishDate() {
        return finishDate;
    }
    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }

    public Card getCard() {
        return card;
    }
    public void setCards(Card card) {
        this.card = card;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getAnimationURL() {
        return _animationURL;
    };
    public void setAnimationURL(String url) {
        _animationURL = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeParcelable(arPoint, flags);
        dest.writeString(startDate);
        dest.writeString(finishDate);
        dest.writeParcelable(card, flags);
        dest.writeString(description);
        dest.writeString(_animationURL);
    }
}
