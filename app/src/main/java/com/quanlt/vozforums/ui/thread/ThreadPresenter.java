package com.quanlt.vozforums.ui.thread;

import com.quanlt.vozforums.data.VozDataRepository;
import com.quanlt.vozforums.ui.base.BasePresenter;

import javax.inject.Inject;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by quanlt on 01/01/2017.
 */

public class ThreadPresenter extends BasePresenter<ThreadMvpView> {
    private Subscription subscription;
    private VozDataRepository mVozDataRepository;

    @Inject
    public ThreadPresenter(VozDataRepository mVozDataRepository) {
        this.mVozDataRepository = mVozDataRepository;
    }

    public void loadThread(String path) {
        getMvpView().showLoading();
        subscription = mVozDataRepository.getThread(path)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(thread -> {
                            getMvpView().hideLoading();
                            getMvpView().showThread(thread);
                        },
                        error -> {
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
}
