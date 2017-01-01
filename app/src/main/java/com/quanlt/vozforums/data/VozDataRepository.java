package com.quanlt.vozforums.data;

import com.quanlt.vozforums.data.model.VozForum;
import com.quanlt.vozforums.data.model.VozThread;

import rx.Observable;

/**
 * Created by quanlt on 01/01/2017.
 */

public interface VozDataRepository {

    Observable<VozForum> getForum(String path);

    Observable<VozThread> getThread(String path);
}
