package com.example.mhwlibrary.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Maps implements Parcelable {
    private String maps_id;
    private String maps_name;
    private String maps_image;
    private String maps_desc;
    public Maps(){
    }
    public String getMaps_id() {
        return maps_id;
    }

    public void setMaps_id(String maps_id) {
        this.maps_id = maps_id;
    }

    public String getMaps_name() {
        return maps_name;
    }

    public void setMaps_name(String maps_name) {
        this.maps_name = maps_name;
    }

    public String getMaps_image() {
        return maps_image;
    }

    public void setMaps_image(String maps_image) {
        this.maps_image = maps_image;
    }

    public String getMaps_desc() {
        return maps_desc;
    }

    public void setMaps_desc(String maps_desc) {
        this.maps_desc = maps_desc;
    }

    protected Maps(Parcel in) {
        maps_id = in.readString();
        maps_name = in.readString();
        maps_image = in.readString();
        maps_desc = in.readString();
    }

    public static final Creator<Maps> CREATOR = new Creator<Maps>() {
        @Override
        public Maps createFromParcel(Parcel in) {
            return new Maps(in);
        }

        @Override
        public Maps[] newArray(int size) {
            return new Maps[size];
        }
    };
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(maps_id);
        dest.writeString(maps_name);
        dest.writeString(maps_image);
        dest.writeString(maps_desc);
    }
}
