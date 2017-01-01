package com.quanlt.vozforums.data.model;

import android.os.Parcel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by quanlt on 01/01/2017.
 */

public class VozThread extends VozBase {
    private VozPost lastPost;
    private String repliesCount;
    private String viewsCount;
    private int rateCount;
    private String content;
    private List<VozPost> posts;
    private boolean isSticky;
    private VozUser author;
    private String latestHref;
    private List<VozBase> pages;

    public VozPost getLastPost() {
        return lastPost;
    }

    public void setLastPost(VozPost lastPost) {
        this.lastPost = lastPost;
    }

    public String getRepliesCount() {
        return repliesCount;
    }

    public void setRepliesCount(String repliesCount) {
        this.repliesCount = repliesCount;
    }

    public String getViewsCount() {
        return viewsCount;
    }

    public void setViewsCount(String viewsCount) {
        this.viewsCount = viewsCount;
    }

    public int getRateCount() {
        return rateCount;
    }

    public void setRateCount(int rateCount) {
        this.rateCount = rateCount;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<VozPost> getPosts() {
        return posts;
    }

    public void setPosts(List<VozPost> posts) {
        this.posts = posts;
    }

    public boolean isSticky() {
        return isSticky;
    }

    public void setSticky(boolean sticky) {
        isSticky = sticky;
    }

    public VozUser getAuthor() {
        return author;
    }

    public void setAuthor(VozUser author) {
        this.author = author;
    }

    public String getLatestHref() {
        return latestHref;
    }

    public void setLatestHref(String latestHref) {
        this.latestHref = latestHref;
    }

    public List<VozBase> getPages() {
        return pages;
    }

    public void setPages(List<VozBase> pages) {
        this.pages = pages;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeParcelable(this.lastPost, flags);
        dest.writeString(this.repliesCount);
        dest.writeString(this.viewsCount);
        dest.writeInt(this.rateCount);
        dest.writeString(this.content);
        dest.writeTypedList(this.posts);
        dest.writeByte(this.isSticky ? (byte) 1 : (byte) 0);
        dest.writeParcelable(this.author, flags);
        dest.writeString(this.latestHref);
        dest.writeTypedList(this.pages);
    }

    public VozThread() {
        posts = new ArrayList<>();
        pages = new ArrayList<>();
    }

    protected VozThread(Parcel in) {
        super(in);
        this.lastPost = in.readParcelable(VozPost.class.getClassLoader());
        this.repliesCount = in.readString();
        this.viewsCount = in.readString();
        this.rateCount = in.readInt();
        this.content = in.readString();
        this.posts = in.createTypedArrayList(VozPost.CREATOR);
        this.isSticky = in.readByte() != 0;
        this.author = in.readParcelable(VozUser.class.getClassLoader());
        this.latestHref = in.readString();
        this.pages = in.createTypedArrayList(VozBase.CREATOR);
    }

    public static final Creator<VozThread> CREATOR = new Creator<VozThread>() {
        @Override
        public VozThread createFromParcel(Parcel source) {
            return new VozThread(source);
        }

        @Override
        public VozThread[] newArray(int size) {
            return new VozThread[size];
        }
    };
}
