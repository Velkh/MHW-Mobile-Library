package com.example.mhwlibrary.Model;

import android.os.Parcelable;
import android.os.Parcel;

import androidx.annotation.NonNull;

public class Model_Quests implements Parcelable {
    private String quest_id;
    private String quest_name;
    private String quest_type;
    private String quest_image;
    private String quest_desc;

    public Model_Quests(){
    }

    protected Model_Quests(Parcel in) {
        quest_id = in.readString();
        quest_name = in.readString();
        quest_type = in.readString();
        quest_image = in.readString();
        quest_desc = in.readString();
    }

    public static final Creator<Model_Quests> CREATOR = new Creator<Model_Quests>() {
        @Override
        public Model_Quests createFromParcel(Parcel in) {
            return new Model_Quests(in);
        }

        @Override
        public Model_Quests[] newArray(int size) {
            return new Model_Quests[size];
        }
    };

    public String getQuest_id() {
        return quest_id;
    }
    public void setQuest_id(String quest_id) {
        this.quest_id = quest_id;
    }
    public String getQuest_name() {
        return quest_name;
    }
    public void setQuest_name(String quest_name) {
        this.quest_name = quest_name;
    }

    public String getQuest_type() {
        return quest_type;
    }

    public void setQuest_type(String quest_type) {
        this.quest_type = quest_type;
    }

    public String getQuest_image() {
        return quest_image;
    }
    public void setQuest_image(String quest_image) {
        this.quest_image = quest_image;
    }
    public String getQuest_desc() {
        return quest_desc;
    }

    public void setQuest_desc(String quest_desc) {
        this.quest_desc = quest_desc;
    }
    //
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(quest_id);
        dest.writeString(quest_name);
        dest.writeString(quest_type);
        dest.writeString(quest_image);
        dest.writeString(quest_desc);
    }
}
