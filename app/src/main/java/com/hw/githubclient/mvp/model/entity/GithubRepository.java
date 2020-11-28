package com.hw.githubclient.mvp.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

public class GithubRepository implements Parcelable {

    @Expose private String id;
    @Expose private String name;
    @Expose private Integer forksCount;

    public GithubRepository(String id, String name, int forksCount) {
        this.id = id;
        this.name = name;
        this.forksCount = forksCount;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getForksCount() {
        return forksCount;
    }

    protected GithubRepository(Parcel in) {
        id = in.readString();
        name = in.readString();
        if (in.readByte() == 0) {
            forksCount = null;
        } else {
            forksCount = in.readInt();
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        if (forksCount == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(forksCount);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<GithubRepository> CREATOR = new Creator<GithubRepository>() {
        @Override
        public GithubRepository createFromParcel(Parcel in) {
            return new GithubRepository(in);
        }

        @Override
        public GithubRepository[] newArray(int size) {
            return new GithubRepository[size];
        }
    };
}
