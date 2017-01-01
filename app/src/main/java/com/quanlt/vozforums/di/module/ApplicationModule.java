package com.quanlt.vozforums.di.module;

import android.app.Application;
import android.content.Context;

import com.quanlt.vozforums.data.VozDataRepository;
import com.quanlt.vozforums.data.VozDataRepositoryImpl;
import com.quanlt.vozforums.data.remote.VozRemoteDataSource;
import com.quanlt.vozforums.data.remote.VozRemoteDataSourceImpl;
import com.quanlt.vozforums.di.ApplicationContext;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by quanlt on 01/01/2017.
 */

@Module
public class ApplicationModule {
    protected final Application mApplication;

    public ApplicationModule(Application mApplication) {
        this.mApplication = mApplication;
    }

    @Provides
    protected Application provideApplication() {
        return mApplication;
    }

    @Provides
    @ApplicationContext
    protected Context provideContext() {
        return mApplication;
    }


    @Provides
    @Singleton
    VozRemoteDataSource provideRemoteDataSource() {
        return new VozRemoteDataSourceImpl();
    }

    @Provides
    @Singleton
    VozDataRepository provideVozDataRepository(VozRemoteDataSource remoteDataSource) {
        return new VozDataRepositoryImpl(remoteDataSource);
    }

}
