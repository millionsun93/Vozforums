package com.quanlt.vozforums.di.module;

import android.app.Activity;
import android.content.Context;

import com.quanlt.vozforums.di.ActivityContext;

import dagger.Module;
import dagger.Provides;

/**
 * Created by quanlt on 01/01/2017.
 */

@Module
public class ActivityModule {

    private Activity mActivity;

    public ActivityModule(Activity activity) {
        this.mActivity = activity;
    }

    @Provides
    public Activity provideActivity() {
        return mActivity;
    }

    @Provides
    @ActivityContext
    Context provideApplication() {
        return mActivity;
    }

}
