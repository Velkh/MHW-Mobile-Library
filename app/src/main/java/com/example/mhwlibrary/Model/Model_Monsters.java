package com.example.mhwlibrary.Model;

import android.os.Parcelable;
import android.os.Parcel;

import androidx.annotation.NonNull;

public class Model_Monsters implements Parcelable {
    private String mons_id;
    private String mons_name;
    private String mons_type;
    private String mons_image;

    private String mons_weak;
    private String mons_desc;

    public Model_Monsters(){
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

    public String getMons_type() {
        return mons_type;
    }

    public void setMons_type(String mons_type) {
        this.mons_type = mons_type;
    }

    public String getMons_weak() {
        return mons_weak;
    }

    public void setMons_weak(String mons_weak) {
        this.mons_weak = mons_weak;
    }

    protected Model_Monsters(Parcel in) {
        mons_id = in.readString();
        mons_name = in.readString();
        mons_type = in.readString();
        mons_image = in.readString();
        mons_desc = in.readString();
        mons_weak = in.readString();
    }

    public static final Creator<Model_Monsters> CREATOR = new Creator<Model_Monsters>() {
        @Override
        public Model_Monsters createFromParcel(Parcel in) {
            return new Model_Monsters(in);
        }

        @Override
        public Model_Monsters[] newArray(int size) {
            return new Model_Monsters[size];
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
        dest.writeString(mons_type);
        dest.writeString(mons_image);
        dest.writeString(mons_desc);
        dest.writeString(mons_weak);
    }
}
