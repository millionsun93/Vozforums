package com.quanlt.vozforums.ui.forum;

import com.quanlt.vozforums.data.VozDataRepository;
import com.quanlt.vozforums.data.model.VozForum;
import com.quanlt.vozforums.data.model.VozThread;
import com.quanlt.vozforums.ui.base.BasePresenter;

import javax.inject.Inject;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by quanlt on 01/01/2017.
 */

public class ForumPresenter extends BasePresenter<ForumMvpView> {
    private Subscription subscription;
    private VozDataRepository mVozDataRepository;

    @Inject
    public ForumPresenter(VozDataRepository mVozDataRepository) {
        this.mVozDataRepository = mVozDataRepository;
    }

    public void loadForum(String path) {
        getMvpView().showLoading();
        subscription = mVozDataRepository.getForum(path)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(forum -> {
                    getMvpView().hideLoading();
                    getMvpView().showForum(forum);
                }, error -> {
                    error.printStackTrace();
                    getMvpView().hideLoading();
                    getMvpView().showError(error.getMessage());
                });
    }

    @Override
    public void detachView() {
        if (subscription != null) {
            subscription.unsubscribe();
        }
        super.detachView();
    }

    public void loadMore(VozForum mSelectedForum) {
        subscription = mVozDataRepository.getForum(mSelectedForum.getNextPage())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(forum -> {
                    getMvpView().hideLoading();
                    getMvpView().showForum(forum);
                }, error -> {
                    error.printStackTrace();
                    getMvpView().hideLoading();
                    getMvpView().showError(error.getMessage());
                });

    }
}
