package com.quanlt.vozforums.utils;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by quanlt on 01/01/2017.
 */

public abstract class EndlessRecyclerViewOnScrollListener extends RecyclerView.OnScrollListener {

    private static final int VISIBLE_THRESHOLD = 3;

    private LinearLayoutManager linearLayoutManager;
    private boolean loading = false;

    public EndlessRecyclerViewOnScrollListener(LinearLayoutManager layoutManager) {
        this.linearLayoutManager = layoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int totalItemCount = linearLayoutManager.getItemCount();
        int lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();

        boolean endHasBeenReached = lastVisibleItemPosition + VISIBLE_THRESHOLD >= totalItemCount;
        if (!loading && totalItemCount > 0 && endHasBeenReached) {
            loading = true;
            onLoadMore();
        }
    }

    public void setLoading(boolean loading) {
        this.loading = loading;
    }

    public abstract void onLoadMore();
}
