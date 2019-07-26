package org.udacity.bakyflacky.recipe;

import android.os.Parcel;
import android.os.Parcelable;

public class Step implements Parcelable {
    public String shortDescription;
    public String description;
    public String videoURL;
    public String thumbnailURL;

    public Step(String shortDescription, String description,
                String videoURL, String thumbnailURL) {
        this.shortDescription = shortDescription;
        this.description = description;
        this. videoURL = videoURL;
        this.thumbnailURL = thumbnailURL;
    }

    public Step(Parcel parcel) {
        shortDescription = parcel.readString();
        description = parcel.readString();
        videoURL = parcel.readString();
        thumbnailURL = parcel.readString();
    }

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(shortDescription);
        dest.writeString(description);
        dest.writeString(videoURL);
        dest.writeString(thumbnailURL);
    }

    public static final Parcelable.Creator<Step> CREATOR = new Parcelable.Creator<Step>() {
        @Override
        public Step createFromParcel(Parcel source) {
            return new Step(source);
        }

        @Override
        public Step[] newArray(int size) {
            return new Step[size];
        }
    };
}
