package com.quanlt.vozforums;

import android.app.Application;
import android.content.Context;

import com.quanlt.vozforums.di.component.ApplicationComponent;
import com.quanlt.vozforums.di.component.DaggerApplicationComponent;
import com.quanlt.vozforums.di.module.ApplicationModule;

/**
 * Created by quanlt on 01/01/2017.
 */

public class VozApplication extends Application {
    private ApplicationComponent mApplicationComponent;

    public static VozApplication get(Context context) {
        return (VozApplication) context.getApplicationContext();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    public synchronized ApplicationComponent getComponent() {
        if (mApplicationComponent == null) {
            mApplicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .build();
        }
        return mApplicationComponent;

    }

    public void setApplicationComponent(ApplicationComponent applicationComponent) {
        mApplicationComponent = applicationComponent;
    }
}
