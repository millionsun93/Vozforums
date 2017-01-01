package com.quanlt.vozforums.data;

import com.quanlt.vozforums.data.model.VozForum;
import com.quanlt.vozforums.data.model.VozThread;
import com.quanlt.vozforums.data.remote.VozRemoteDataSource;
import com.quanlt.vozforums.utils.Constants;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import rx.Observable;
import rx.observers.TestSubscriber;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * Created by quanlt on 01/01/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class VozDataRepositoryTest {
    @Mock
    private VozRemoteDataSource mMockRemoteDataSource;
    private VozDataRepository mVozDataRepository;

    @Before
    public void setUp() throws Exception {
        mVozDataRepository = new VozDataRepositoryImpl(mMockRemoteDataSource);
    }

    @Test
    public void testGetHomePage() throws Exception {
        InputStreamReader reader = new InputStreamReader(VozDataRepositoryTest.class
                .getResourceAsStream("/homepage.html"));
        String line;
        StringBuilder builder = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(reader);
        while ((line = bufferedReader.readLine()) != null) {
            builder.append(line);
        }
        when(mMockRemoteDataSource.get(Constants.BASE_URL))
                .thenReturn(Observable.just(builder.toString()));
        TestSubscriber<VozForum> testSubscriber = new TestSubscriber();
        mVozDataRepository.getForum(Constants.BASE_URL)
                .subscribe(testSubscriber);
        testSubscriber.assertCompleted();
        testSubscriber.assertNoErrors();
        List<VozForum> forums = testSubscriber.getOnNextEvents();
        assertTrue(forums.size() == 1);
        assertTrue(forums.get(0).getForums().size() > 0);
    }

    @Test
    public void testGetForumWithSubForum() throws Exception {
        InputStreamReader reader = new InputStreamReader(VozDataRepositoryTest.class
                .getResourceAsStream("/f17.html"));
        String line;
        StringBuilder builder = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(reader);
        while ((line = bufferedReader.readLine()) != null) {
            builder.append(line);
        }
        when(mMockRemoteDataSource.get(Constants.BASE_URL))
                .thenReturn(Observable.just(builder.toString()));
        TestSubscriber<VozForum> testSubscriber = new TestSubscriber();
        mVozDataRepository.getForum(Constants.BASE_URL)
                .subscribe(testSubscriber);
        testSubscriber.assertCompleted();
        testSubscriber.assertNoErrors();
        List<VozForum> forums = testSubscriber.getOnNextEvents();
        assertTrue(forums.size() == 1);
        assertTrue(forums.get(0).getForums().size() > 0);
    }

    @Test
    public void testGetForumWithoutSubForum() throws Exception {
        InputStreamReader reader = new InputStreamReader(VozDataRepositoryTest.class
                .getResourceAsStream("/f33.html"));
        String line;
        StringBuilder builder = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(reader);
        while ((line = bufferedReader.readLine()) != null) {
            builder.append(line);
        }
        when(mMockRemoteDataSource.get(Constants.BASE_URL))
                .thenReturn(Observable.just(builder.toString()));
        TestSubscriber<VozForum> testSubscriber = new TestSubscriber();
        mVozDataRepository.getForum(Constants.BASE_URL)
                .subscribe(testSubscriber);
        testSubscriber.assertCompleted();
        testSubscriber.assertNoErrors();
        List<VozForum> forums = testSubscriber.getOnNextEvents();
        assertTrue(forums.size() == 1);
        assertTrue(forums.get(0).getForums().size() == 0);
        assertTrue(forums.get(0).getThreads().size() == 25);
    }

    @Test
    public void testGetThreadNormal() throws Exception {
        InputStreamReader reader = new InputStreamReader(VozDataRepositoryTest.class
                .getResourceAsStream("/thread_normal.html"));
        String line;
        StringBuilder builder = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(reader);
        while ((line = bufferedReader.readLine()) != null) {
            builder.append(line);
        }
        when(mMockRemoteDataSource.get("https://vozforums.com/showthread.php?t=5625558"))
                .thenReturn(Observable.just(builder.toString()));
        TestSubscriber<VozThread> testSubscriber = new TestSubscriber();
        mVozDataRepository.getThread("https://vozforums.com/showthread.php?t=5625558")
                .subscribe(testSubscriber);
        testSubscriber.assertCompleted();
        testSubscriber.assertNoErrors();
        List<VozThread> threads = testSubscriber.getOnNextEvents();
        assertTrue(threads.size() == 1);
        assertTrue(threads.get(0).getPosts().size() == 10);
        assertTrue(threads.get(0).getPosts().get(0).getAuthor().getUsername().equals("l0000gold"));
    }
}
