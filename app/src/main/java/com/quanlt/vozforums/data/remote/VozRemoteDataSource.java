package com.quanlt.vozforums.data.remote;


import rx.Observable;

/**
 * Created by quanlt on 01/01/2017.
 */

public interface VozRemoteDataSource {
    Observable<String> get(String forumPath);
}
