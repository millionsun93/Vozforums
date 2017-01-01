package com.quanlt.vozforums.ui.forum;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.quanlt.vozforums.R;
import com.quanlt.vozforums.data.model.VozForum;
import com.quanlt.vozforums.data.model.VozThread;
import com.quanlt.vozforums.ui.base.BaseActivity;
import com.quanlt.vozforums.utils.Constants;

public class ForumActivity extends BaseActivity implements ForumFragment.ForumItemListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment,
                    new ForumFragment())
                    .addToBackStack(Constants.BASE_URL)
                    .commit();
        }
    }

    @Override
    public void onForumClick(VozForum clickedForum) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment, ForumFragment.newInstance(clickedForum))
                .addToBackStack(clickedForum.getHref())
                .commit();
    }

    @Override
    public void onThreadClick(VozThread clickedThread) {

    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() <= 1) {
            finish();
        } else {
            getSupportFragmentManager().popBackStack();
        }

    }
}
