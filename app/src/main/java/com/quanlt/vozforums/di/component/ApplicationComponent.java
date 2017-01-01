package com.quanlt.vozforums.di.component;

import android.app.Application;
import android.content.Context;

import com.quanlt.vozforums.data.VozDataRepository;
import com.quanlt.vozforums.di.ApplicationContext;
import com.quanlt.vozforums.di.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by quanlt on 01/01/2017.
 */

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    @ApplicationContext
    Context context();

    Application application();

    VozDataRepository vozDataRepository();
}
