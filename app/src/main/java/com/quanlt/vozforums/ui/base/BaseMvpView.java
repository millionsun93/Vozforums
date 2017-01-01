package com.quanlt.vozforums.ui.base;

/**
 * Created by TOIDV on 4/5/2016.
 */
public interface BaseMvpView extends MvpView {

    void showLoading();

    void hideLoading();

    void showError(String message);

}
