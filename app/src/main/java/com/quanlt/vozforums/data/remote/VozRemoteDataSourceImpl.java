package com.quanlt.vozforums.data.remote;


import com.kymjs.rxvolley.RxVolley;
import com.quanlt.vozforums.utils.Constants;

import rx.Observable;

/**
 * Created by quanlt on 01/01/2017.
 */

public class VozRemoteDataSourceImpl implements VozRemoteDataSource {
    @Override
    public Observable<String> get(String path) {
        return new RxVolley.Builder()
                .url(path)
                .httpMethod(RxVolley.Method.GET)
                .cacheTime(Constants.CACHE_TIME)
                .getResult()
                .map(result -> new String(result.data));
    }
}
