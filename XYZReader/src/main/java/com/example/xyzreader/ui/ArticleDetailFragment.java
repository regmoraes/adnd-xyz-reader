package com.example.xyzreader.ui;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ShareCompat;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.xyzreader.R;
import com.example.xyzreader.data.Article;
import com.example.xyzreader.databinding.FragmentArticleDetailBinding;

/**
 * A fragment representing a single Article detail screen. This fragment is
 * either contained in a {@link ArticleListActivity} in two-pane mode (on
 * tablets) or a {@link ArticleDetailActivity} on handsets.
 */
public class ArticleDetailFragment extends Fragment {
    public static final String ARG_ARTICLE = "article";

    private Article mArticle;
    private ParentActivityListener parentActivityListener;
    private FragmentArticleDetailBinding viewBinding;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ArticleDetailFragment() {

    }

    public static ArticleDetailFragment newInstance(Article article) {
        Bundle arguments = new Bundle();
        arguments.putParcelable(ARG_ARTICLE, article);
        ArticleDetailFragment fragment = new ArticleDetailFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        postponeEnterTransition();

        if (getArguments().containsKey(ARG_ARTICLE)) {
            mArticle = getArguments().getParcelable(ARG_ARTICLE);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewBinding = FragmentArticleDetailBinding.inflate(inflater, container, false);

        return viewBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        prepareAnimations();
        bindViews();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(getActivity() != null && getActivity() instanceof ParentActivityListener) {
            parentActivityListener = (ParentActivityListener) getActivity();
        } else {
            throw new ClassCastException(getActivity().getClass().getSimpleName()
                    + " must implement " + ParentActivityListener.class.getSimpleName());
        }
    }

    private void bindViews() {

        final String title = mArticle.getTitle();
        final String author = mArticle.getAuthor();
        final String body = Html.fromHtml(mArticle.getBody()).toString();
        final String photo = mArticle.getPhoto();

        viewBinding.articleBody.setText(body);

        Glide.with(this)
                .setDefaultRequestOptions(
                        new RequestOptions()
                                .dontTransform()
                                .dontAnimate()
                                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                )
                .load(photo)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model,
                                                Target<Drawable> target, boolean isFirstResource) {
                        startPostponedEnterTransition();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        startPostponedEnterTransition();
                        return false;
                    }
                })
                .into(viewBinding.photo);

        viewBinding.toolbar.setNavigationOnClickListener(view ->
                parentActivityListener.onHomeClicked());

        viewBinding.collapsingToolbar.setTitle(title);
        viewBinding.collapsingToolbar.setSubtitle(author);

        viewBinding.shareFab.setOnClickListener(view ->
                startActivity(Intent.createChooser(
                        ShareCompat.IntentBuilder.from(getActivity())
                                .setType("text/plain")
                                .setText(mArticle.getBody())
                                .getIntent(), getString(R.string.action_share))));
    }

    public void prepareAnimations() {

        viewBinding.appBar.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            if (appBarLayout.getHeight() / 2 < -verticalOffset) {
                viewBinding.shareFab.setVisibility(View.GONE);
            } else {
                viewBinding.shareFab.setVisibility(View.VISIBLE);
            }
        });
    }

    interface ParentActivityListener {
        void onHomeClicked();
    }
}
