package com.quanlt.vozforums.data.model;

import android.os.Parcel;

/**
 * Created by quanlt on 01/01/2017.
 */

public class VozUser extends VozBase {
    private String username;
    private String joinDate;
    private String posts;
    private boolean isOnline;
    private String location;
    private String avatar;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
    }

    public String getPosts() {
        return posts;
    }

    public void setPosts(String posts) {
        this.posts = posts;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.username);
        dest.writeString(this.joinDate);
        dest.writeString(this.posts);
        dest.writeByte(this.isOnline ? (byte) 1 : (byte) 0);
        dest.writeString(this.location);
        dest.writeString(this.avatar);
    }

    public VozUser() {
    }

    protected VozUser(Parcel in) {
        super(in);
        this.username = in.readString();
        this.joinDate = in.readString();
        this.posts = in.readString();
        this.isOnline = in.readByte() != 0;
        this.location = in.readString();
        this.avatar = in.readString();
    }

    public static final Creator<VozUser> CREATOR = new Creator<VozUser>() {
        @Override
        public VozUser createFromParcel(Parcel source) {
            return new VozUser(source);
        }

        @Override
        public VozUser[] newArray(int size) {
            return new VozUser[size];
        }
    };
}
