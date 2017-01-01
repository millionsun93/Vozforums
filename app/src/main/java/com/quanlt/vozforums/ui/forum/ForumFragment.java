package com.quanlt.vozforums.ui.forum;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.quanlt.vozforums.R;
import com.quanlt.vozforums.data.VozDataRepository;
import com.quanlt.vozforums.data.model.VozBase;
import com.quanlt.vozforums.data.model.VozForum;
import com.quanlt.vozforums.data.model.VozThread;
import com.quanlt.vozforums.ui.base.BaseFragment;
import com.quanlt.vozforums.utils.Constants;
import com.quanlt.vozforums.utils.EndlessRecyclerViewOnScrollListener;
import com.quanlt.vozforums.utils.InnovatubeUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

/**
 * A placeholder fragment containing a simple view.
 */
public class ForumFragment extends BaseFragment implements ForumMvpView {

    public static final String ARGUMENT_FORUM = "ARGUMENT_FORUM";

    @BindView(R.id.recycler_forum)
    RecyclerView mForumRecycler;
    @BindView(R.id.progress_loading)
    MaterialProgressBar mLoadingProgress;

    @Inject
    VozDataRepository mDataRepository;
    @Inject
    ForumPresenter mForumPresenter;

    ForumAdapter mForumAdapter;

    private VozForum mSelectedForum;
    private ForumItemListener mForumItemListener;
    private EndlessRecyclerViewOnScrollListener mEndlessRecyclerViewListener;

    public static ForumFragment newInstance(VozForum forum) {

        Bundle args = new Bundle();
        args.putParcelable(ARGUMENT_FORUM, forum);
        ForumFragment fragment = new ForumFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public ForumFragment() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ForumItemListener) {
            mForumItemListener = (ForumItemListener) context;
        } else {
            throw new IllegalStateException("context should implement ForumItemListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() == null) {
            mSelectedForum = new VozForum(getString(R.string.app_name), Constants.BASE_URL);
        } else {
            mSelectedForum = getArguments().getParcelable(ARGUMENT_FORUM);
        }
        getActivityComponent().inject(this);
        mForumPresenter.attachView(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forum, container, false);
        ButterKnife.bind(this, view);
        mForumAdapter = new ForumAdapter(new ArrayList<>(), mForumItemListener);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mForumRecycler.setLayoutManager(linearLayoutManager);
        mForumRecycler.setAdapter(mForumAdapter);
        mEndlessRecyclerViewListener = new EndlessRecyclerViewOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore() {
                if (!InnovatubeUtils.isEmpty(mSelectedForum.getNextPage())) {
                    mForumPresenter.loadMore(mSelectedForum);
                } else {
                    setLoading(false);
                }
            }
        };
        mForumRecycler.addOnScrollListener(mEndlessRecyclerViewListener);
        mForumPresenter.loadForum(mSelectedForum.getHref());
        return view;
    }

    @Override
    public void showForum(VozForum forum) {
        this.mSelectedForum = forum;
        mForumAdapter.addForums(forum.getForums());
        mForumAdapter.addThreads(forum.getThreads());
    }

    @Override
    public void showLoading() {
        mLoadingProgress.setVisibility(View.VISIBLE);
        mEndlessRecyclerViewListener.setLoading(false);
    }

    @Override
    public void hideLoading() {
        mLoadingProgress.setVisibility(View.GONE);
        mEndlessRecyclerViewListener.setLoading(false);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        mForumPresenter.detachView();
        super.onDestroy();
    }

    public static class ForumAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private List<VozBase> mVozItems;
        private ForumItemListener mForumItemListener;
        private final int VIEW_TYPE_FORUM = 1;
        private final int VIEW_TYPE_THREAD = 2;

        public ForumAdapter(List<VozBase> mVozItems, ForumItemListener mForumItemListener) {
            this.mVozItems = mVozItems;
            this.mForumItemListener = mForumItemListener;
        }


        public void addThreads(List<VozThread> threads) {
            mVozItems.addAll(threads);
            notifyItemRangeInserted(mVozItems.size() - threads.size(), threads.size());
        }

        public void addForums(List<VozForum> forums) {
            if (!mVozItems.isEmpty()) {
                if (mVozItems.get(0) instanceof VozForum) {
                    return;
                }
            }
            mVozItems.addAll(forums);
            notifyItemRangeInserted(mVozItems.size() - forums.size(), forums.size());
        }

        public void addItem(VozBase item) {
            mVozItems.add(item);
            notifyItemInserted(mVozItems.size() - 1);
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = null;
            if (viewType == VIEW_TYPE_FORUM) {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_forum,
                        parent, false);
                return new ForumHolder(view);
            } else {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_thread,
                        parent, false);
                return new ThreadHolder(view);
            }

        }

        @Override
        public int getItemViewType(int position) {
            VozBase item = mVozItems.get(position);
            if (item instanceof VozForum) {
                return VIEW_TYPE_FORUM;
            }
            return VIEW_TYPE_THREAD;
        }


        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof ForumHolder) {
                VozForum forum = (VozForum) mVozItems.get(position);
                ((ForumHolder) holder).mTitleTextView.setText(forum.getTitle());
                holder.itemView.setOnClickListener(view -> mForumItemListener.onForumClick(forum));
            } else {
                VozThread thread = (VozThread) mVozItems.get(position);
                ((ThreadHolder) holder).mAuthorTextView.setText(thread.getAuthor().getUsername());
                ((ThreadHolder) holder).mContentTextView.setText(thread.getContent());
                ((ThreadHolder) holder).mExtraTextView.setText(thread.getLastPost().getDate() +
                        ", " + thread.getRepliesCount() + " replies");
                ((ThreadHolder) holder).mTitleTextView.setText(thread.getTitle());
                holder.itemView.setOnClickListener(view -> mForumItemListener.onThreadClick(thread));
            }
        }

        @Override
        public int getItemCount() {
            return mVozItems.size();
        }

        public class ForumHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.text_forum_title)
            TextView mTitleTextView;

            public ForumHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }

        public class ThreadHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.text_thread_author)
            TextView mAuthorTextView;
            @BindView(R.id.text_thread_title)
            TextView mTitleTextView;
            @BindView(R.id.text_thread_extra)
            TextView mExtraTextView;
            @BindView(R.id.text_thread_content)
            TextView mContentTextView;

            public ThreadHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }

    public interface ForumItemListener {
        void onForumClick(VozForum clickedForum);

        void onThreadClick(VozThread clickedThread);
    }
}
