package com.quanlt.vozforums.ui.forum;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.quanlt.vozforums.R;
import com.quanlt.vozforums.ui.base.BaseActivity;

public class ForumActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

}
