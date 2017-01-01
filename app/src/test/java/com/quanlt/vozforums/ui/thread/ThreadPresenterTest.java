package com.quanlt.vozforums.ui.thread;

import com.quanlt.vozforums.RxSchedulerOverrideRule;
import com.quanlt.vozforums.data.VozDataRepository;
import com.quanlt.vozforums.data.model.VozThread;

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
public class ThreadPresenterTest {
    @Mock
    VozDataRepository mMockVozDataRepository;

    @Mock
    ThreadMvpView mMockThreadMvpView;

    @Rule
    public RxSchedulerOverrideRule nOverrideRule = new RxSchedulerOverrideRule();

    private ThreadPresenter mThreadPresenter;

    @Before
    public void setUp() throws Exception {
        mThreadPresenter = new ThreadPresenter(mMockVozDataRepository);
        mThreadPresenter.attachView(mMockThreadMvpView);
    }

    @Test
    public void testLoadThreadSuccess() throws Exception {
        VozThread thread = new VozThread();
        when(mMockVozDataRepository.getThread("https://vozforums.com/showthread.php?t=5600465"))
                .thenReturn(Observable.just(thread));
        mThreadPresenter.loadThread("https://vozforums.com/showthread.php?t=5600465");
        verify(mMockThreadMvpView).showLoading();
        verify(mMockThreadMvpView).showThread(thread);
        verify(mMockThreadMvpView).hideLoading();

    }

    @Test
    public void testLoadThreadFail() throws Exception {
        VozThread thread = new VozThread();
        when(mMockVozDataRepository.getThread("https://vozforums.com/showthread.php?t=5600465"))
                .thenReturn(Observable.error(new Throwable("blah blah")));
        mThreadPresenter.loadThread("https://vozforums.com/showthread.php?t=5600465");
        verify(mMockThreadMvpView).showLoading();
        verify(mMockThreadMvpView).showError("blah blah");
        verify(mMockThreadMvpView).hideLoading();

    }
}
