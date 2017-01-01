package com.quanlt.vozforums.ui.base;

import android.support.v4.app.Fragment;

import com.quanlt.vozforums.VozApplication;
import com.quanlt.vozforums.di.component.ActivityComponent;
import com.quanlt.vozforums.di.component.DaggerActivityComponent;

/**
 * Created by TOIDV on 5/19/2016.
 */
public class BaseFragment extends Fragment {

    ActivityComponent activityComponent;

    public ActivityComponent getActivityComponent() {
        if (activityComponent == null) {
            activityComponent = DaggerActivityComponent.builder()
                    .applicationComponent(VozApplication.get(getActivity()).getComponent())
                    .build();
        }
        return activityComponent;
    }

}
