package com.quanlt.vozforums.ui.thread;

import com.quanlt.vozforums.data.model.VozThread;
import com.quanlt.vozforums.ui.base.BaseMvpView;

/**
 * Created by quanlt on 01/01/2017.
 */

public interface ThreadMvpView extends BaseMvpView{
    void showThread(VozThread thread);
}
