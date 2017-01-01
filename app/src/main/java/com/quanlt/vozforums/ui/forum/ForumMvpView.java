package com.quanlt.vozforums.ui.forum;

import com.quanlt.vozforums.data.model.VozForum;
import com.quanlt.vozforums.data.model.VozThread;
import com.quanlt.vozforums.ui.base.BaseMvpView;

/**
 * Created by quanlt on 01/01/2017.
 */

public interface ForumMvpView extends BaseMvpView {
    void showForum(VozForum forum);
}
