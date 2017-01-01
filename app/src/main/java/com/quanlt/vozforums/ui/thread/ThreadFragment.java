package com.quanlt.vozforums.ui.thread;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.quanlt.vozforums.R;
import com.quanlt.vozforums.data.VozDataRepository;
import com.quanlt.vozforums.data.model.VozPost;
import com.quanlt.vozforums.data.model.VozThread;
import com.quanlt.vozforums.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;


public class ThreadFragment extends BaseFragment implements ThreadMvpView {
    public static final String ARGUMENT_THREAD = "ARGUMENT_THREAD";

    @Inject
    VozDataRepository mVozDataRepository;

    @Inject
    ThreadPresenter mThreadPresenter;

    @BindView(R.id.recycler_post)
    RecyclerView mPostRecycler;
    @BindView(R.id.progress_loading)
    MaterialProgressBar mLoadingProgress;

    private VozThread mSelectedThread;
    private PostAdapter mPostAdapter;

    public static ThreadFragment newInstance(VozThread thread) {

        Bundle args = new Bundle();
        args.putParcelable(ARGUMENT_THREAD, thread);
        ThreadFragment fragment = new ThreadFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public ThreadFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        mSelectedThread = getArguments().getParcelable(ARGUMENT_THREAD);
        mThreadPresenter.attachView(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thread, container, false);
        ButterKnife.bind(this, view);
        mPostAdapter = new PostAdapter(new ArrayList<>());
        mPostRecycler.setAdapter(mPostAdapter);
        mPostRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mThreadPresenter.loadThread(mSelectedThread.getHref());
        return view;
    }

    @Override
    public void showThread(VozThread thread) {
        mPostAdapter.addPosts(thread.getPosts());
    }

    @Override
    public void showLoading() {
        mPostRecycler.setVisibility(View.GONE);
        mLoadingProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mPostRecycler.setVisibility(View.VISIBLE);
        mLoadingProgress.setVisibility(View.GONE);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        mThreadPresenter.detachView();
        super.onDestroy();
    }

    public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

        private List<VozPost> mPostItems;

        public PostAdapter(List<VozPost> mPostItems) {
            this.mPostItems = mPostItems;
        }

        public void addPosts(List<VozPost> posts) {
            mPostItems.addAll(posts);
            notifyItemRangeInserted(getItemCount() - posts.size(), posts.size());
        }

        @Override
        public PostAdapter.PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_post, parent, false);
            return new PostViewHolder(view);
        }

        @Override
        public void onBindViewHolder(PostAdapter.PostViewHolder holder, int position) {
            holder.mAuthorNameTextView.setText(mPostItems.get(position).getAuthor().getUsername());
            holder.mAuthorTitleTextView.setText(mPostItems.get(position).getAuthor().getTitle());
            holder.mAuthorExtraTextView.setText(mPostItems.get(position).getDate());
            Glide.with(getActivity())
                    .load(mPostItems.get(position).getAuthor().getAvatar())
                    .into(holder.mAvatarImage);
        }

        @Override
        public int getItemCount() {
            return mPostItems.size();
        }

        public class PostViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.image_post_avatar)
            CircleImageView mAvatarImage;
            @BindView(R.id.text_post_username)
            TextView mAuthorNameTextView;
            @BindView(R.id.text_post_user_title)
            TextView mAuthorTitleTextView;
            @BindView(R.id.text_post_user_info)
            TextView mAuthorExtraTextView;

            public PostViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }
}
