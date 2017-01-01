package com.quanlt.vozforums.ui.base;

import android.support.v7.app.AppCompatActivity;

import com.quanlt.vozforums.VozApplication;
import com.quanlt.vozforums.di.component.ActivityComponent;
import com.quanlt.vozforums.di.component.DaggerActivityComponent;

/**
 * Created by TOIDV on 4/4/2016.
 */
public class BaseActivity extends AppCompatActivity {

    ActivityComponent activityComponent;

    public ActivityComponent getActivityComponent() {
        if (activityComponent == null) {
            activityComponent = DaggerActivityComponent.builder()
                    .applicationComponent(VozApplication.get(this).getComponent())
                    .build();
        }
        return activityComponent;
    }
}
