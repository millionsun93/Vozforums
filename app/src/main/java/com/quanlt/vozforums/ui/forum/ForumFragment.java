package com.quanlt.vozforums.ui.forum;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.quanlt.vozforums.R;
import com.quanlt.vozforums.ui.base.BaseFragment;

/**
 * A placeholder fragment containing a simple view.
 */
public class ForumFragment extends BaseFragment {

    public ForumFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_forum, container, false);
    }
}
