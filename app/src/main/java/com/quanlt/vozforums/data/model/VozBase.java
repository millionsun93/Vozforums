package com.quanlt.vozforums.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by quanlt on 01/01/2017.
 */

public class VozBase implements Parcelable {
    private String title;
    private String href;

    public VozBase(String title) {
        this.title = title;
    }

    public VozBase(String title, String href) {
        this.title = title;
        this.href = href;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public static final Creator<VozBase> CREATOR = new Creator<VozBase>() {
        @Override
        public VozBase createFromParcel(Parcel in) {
            return new VozBase(in);
        }

        @Override
        public VozBase[] newArray(int size) {
            return new VozBase[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.href);
    }

    public VozBase() {
    }

    protected VozBase(Parcel in) {
        this.title = in.readString();
        this.href = in.readString();
    }

}
