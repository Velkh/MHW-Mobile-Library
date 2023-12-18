package com.example.mhwlibrary.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Model_Locations implements Parcelable {
    private String maps_id;
    private String maps_name;
    private String maps_image;
    private String maps_desc;
    public Model_Locations(){
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

    protected Model_Locations(Parcel in) {
        maps_id = in.readString();
        maps_name = in.readString();
        maps_image = in.readString();
        maps_desc = in.readString();
    }

    public static final Creator<Model_Locations> CREATOR = new Creator<Model_Locations>() {
        @Override
        public Model_Locations createFromParcel(Parcel in) {
            return new Model_Locations(in);
        }

        @Override
        public Model_Locations[] newArray(int size) {
            return new Model_Locations[size];
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
