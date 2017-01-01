package com.quanlt.vozforums.di.component;

import com.quanlt.vozforums.di.PerActivity;
import com.quanlt.vozforums.di.module.ActivityModule;
import com.quanlt.vozforums.ui.forum.ForumFragment;

import dagger.Component;

/**
 * Created by quanlt on 01/01/2017.
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(ForumFragment forumFragment);
}
