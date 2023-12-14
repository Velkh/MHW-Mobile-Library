package com.example.mhwlibrary.Model;

import android.os.Parcelable;
import android.os.Parcel;

import androidx.annotation.NonNull;

public class Monsters implements Parcelable {
    private String mons_id;
    private String mons_name;
    private String mons_image;
    private String mons_desc;

    public Monsters (){
    }

    public String getMons_id() {
        return mons_id;
    }

    public void setMons_id(String mons_id) {
        this.mons_id = mons_id;
    }

    public String getMons_name() {
        return mons_name;
    }

    public void setMons_name(String mons_name) {
        this.mons_name = mons_name;
    }

    public String getMons_image() {
        return mons_image;
    }

    public void setMons_image(String mons_image) {
        this.mons_image = mons_image;
    }

    public String getMons_desc() {
        return mons_desc;
    }

    public void setMons_desc(String mons_desc) {
        this.mons_desc = mons_desc;
    }

    protected Monsters(Parcel in) {
        mons_id = in.readString();
        mons_name = in.readString();
        mons_image = in.readString();
        mons_desc = in.readString();
    }

    public static final Creator<Monsters> CREATOR = new Creator<Monsters>() {
        @Override
        public Monsters createFromParcel(Parcel in) {
            return new Monsters(in);
        }

        @Override
        public Monsters[] newArray(int size) {
            return new Monsters[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(mons_id);
        dest.writeString(mons_name);
        dest.writeString(mons_image);
        dest.writeString(mons_desc);
    }
}
