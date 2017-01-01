package com.quanlt.vozforums.data.model;

import android.os.Parcel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by quanlt on 01/01/2017.
 */

public class VozForum extends VozBase {
    private List<VozForum> forums;
    private List<VozThread> threads;
    private String viewerCount;
    private String threadCount;
    private String postCount;
    private List<VozBase> pages;
    private String nextPage;

    public VozForum(String title, String href) {
        super(title, href);
    }

    public List<VozForum> getForums() {
        return forums;
    }

    public void setForums(List<VozForum> forums) {
        this.forums = forums;
    }

    public List<VozThread> getThreads() {
        return threads;
    }

    public void setThreads(List<VozThread> threads) {
        this.threads = threads;
    }

    public String getViewerCount() {
        return viewerCount;
    }

    public void setViewerCount(String viewerCount) {
        this.viewerCount = viewerCount;
    }

    public String getThreadCount() {
        return threadCount;
    }

    public void setThreadCount(String threadCount) {
        this.threadCount = threadCount;
    }

    public String getPostCount() {
        return postCount;
    }

    public void setPostCount(String postCount) {
        this.postCount = postCount;
    }

    public List<VozBase> getPages() {
        return pages;
    }

    public void setPages(List<VozBase> pages) {
        this.pages = pages;
    }

    public String getNextPage() {
        return nextPage;
    }

    public void setNextPage(String nextPage) {
        this.nextPage = nextPage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeTypedList(this.forums);
        dest.writeTypedList(this.threads);
        dest.writeString(this.viewerCount);
        dest.writeString(this.threadCount);
        dest.writeString(this.postCount);
        dest.writeTypedList(this.pages);
        dest.writeString(this.nextPage);
    }

    public VozForum() {
        forums = new ArrayList<>();
        threads = new ArrayList<>();
        pages = new ArrayList<>();
    }

    protected VozForum(Parcel in) {
        super(in);
        this.forums = in.createTypedArrayList(VozForum.CREATOR);
        this.threads = in.createTypedArrayList(VozThread.CREATOR);
        this.viewerCount = in.readString();
        this.threadCount = in.readString();
        this.postCount = in.readString();
        this.pages = in.createTypedArrayList(VozBase.CREATOR);
        this.nextPage = in.readString();
    }

    public static final Creator<VozForum> CREATOR = new Creator<VozForum>() {
        @Override
        public VozForum createFromParcel(Parcel source) {
            return new VozForum(source);
        }

        @Override
        public VozForum[] newArray(int size) {
            return new VozForum[size];
        }
    };
}
