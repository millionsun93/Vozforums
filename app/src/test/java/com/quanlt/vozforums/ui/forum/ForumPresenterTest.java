package com.quanlt.vozforums.ui.forum;

import com.quanlt.vozforums.RxSchedulerOverrideRule;
import com.quanlt.vozforums.data.VozDataRepository;
import com.quanlt.vozforums.data.model.VozForum;
import com.quanlt.vozforums.utils.Constants;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import rx.Observable;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by quanlt on 01/01/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class ForumPresenterTest {
    @Mock
    VozDataRepository mMockVozDataRepository;
    @Mock
    ForumMvpView mMockForumMvpView;

    @Rule
    public RxSchedulerOverrideRule mOverrideRule = new RxSchedulerOverrideRule();
    private ForumPresenter mForumPresenter;

    @Before
    public void setUp() throws Exception {
        mForumPresenter = new ForumPresenter(mMockVozDataRepository);
        mForumPresenter.attachView(mMockForumMvpView);
    }

    @Test
    public void testLoadHomePageSuccess() throws Exception {
        VozForum forum = new VozForum();
        when(mMockVozDataRepository.getForum(Constants.BASE_URL)).thenReturn(Observable.just(forum));
        mForumPresenter.loadForum(Constants.BASE_URL);
        verify(mMockForumMvpView).showLoading();
        verify(mMockForumMvpView).hideLoading();
        verify(mMockForumMvpView).showForum(forum);
    }
    @Test
    public void testLoadHomePageFail() throws Exception {
        when(mMockVozDataRepository.getForum(Constants.BASE_URL))
                .thenReturn(Observable.error(new Throwable("Blah blah")));
        mForumPresenter.loadForum(Constants.BASE_URL);
        verify(mMockForumMvpView).showLoading();
        verify(mMockForumMvpView).hideLoading();
        verify(mMockForumMvpView).showError("Blah blah");
    }

}
