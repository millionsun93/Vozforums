package com.quanlt.vozforums.data.model;

import android.os.Parcel;

/**
 * Created by quanlt on 01/01/2017.
 */

public class VozPost extends VozBase {
    private VozUser author;
    private String date;
    private String content;
    private String signature;
    private boolean isDeleted;

    public VozUser getAuthor() {
        return author;
    }

    public void setAuthor(VozUser author) {
        this.author = author;
    }

    public String getDate() {
        return date == null ? "" : date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeParcelable(this.author, flags);
        dest.writeString(this.date);
        dest.writeString(this.content);
        dest.writeString(this.signature);
        dest.writeByte(this.isDeleted ? (byte) 1 : (byte) 0);
    }

    public VozPost() {
    }

    protected VozPost(Parcel in) {
        super(in);
        this.author = in.readParcelable(VozUser.class.getClassLoader());
        this.date = in.readString();
        this.content = in.readString();
        this.signature = in.readString();
        this.isDeleted = in.readByte() != 0;
    }

    public static final Creator<VozPost> CREATOR = new Creator<VozPost>() {
        @Override
        public VozPost createFromParcel(Parcel source) {
            return new VozPost(source);
        }

        @Override
        public VozPost[] newArray(int size) {
            return new VozPost[size];
        }
    };
}
