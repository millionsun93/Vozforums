package com.quanlt.vozforums.ui.thread;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.quanlt.vozforums.R;
import com.quanlt.vozforums.data.model.VozThread;

public class ThreadActivity extends AppCompatActivity {
    public static final String EXTRA_THREAD = "EXTRA_THREAD";
    private VozThread mSelectedThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mSelectedThread = getIntent().getParcelableExtra(EXTRA_THREAD);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment,
                    ThreadFragment.newInstance(mSelectedThread)).commit();
        }
    }

}
