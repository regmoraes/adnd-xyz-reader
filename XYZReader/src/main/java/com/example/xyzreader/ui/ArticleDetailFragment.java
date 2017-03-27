package com.example.xyzreader.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.xyzreader.data.Article;
import com.example.xyzreader.databinding.FragmentArticleDetailBinding;

/**
 * A fragment representing a single Article detail screen. This fragment is
 * either contained in a {@link ArticleListActivity} in two-pane mode (on
 * tablets) or a {@link ArticleDetailActivity} on handsets.
 */
public class ArticleDetailFragment extends Fragment {
    private static final String TAG = "ArticleDetailFragment";
    public static final String ARG_ARTICLE = "article";

    private Article mArticle;

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
        if (getArguments().containsKey(ARG_ARTICLE)) {
            mArticle = getArguments().getParcelable(ARG_ARTICLE);
        }
        setHasOptionsMenu(true);
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
        bindViews();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        Log.d(TAG, "onCreateOptionsMenu");
    }

    private void bindViews() {

        final String title = mArticle.getTitle();
        final String date = mArticle.getPublishedDate();

        final String author = mArticle.getAuthor();
        final String body = Html.fromHtml(mArticle.getBody()).toString();
        final String photo = mArticle.getPhoto();

        Glide.with(this)
                .setDefaultRequestOptions(
                        new RequestOptions()
                                .dontTransform()
                                .dontAnimate()
                                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                )
                .load(photo)
//                    .listener(new RequestListener<Drawable>() {
//                        @Override
//                        public boolean onLoadFailed(@Nullable GlideException e, Object model,
//                                                    Target<Drawable> target, boolean isFirstResource) {
//                            startPostponedEnterTransition();
//                            return false;
//                        }
//
//                        @Override
//                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                            startPostponedEnterTransition();
//                            return false;
//                        }
//                    })
                .into(viewBinding.photo);

        viewBinding.collapsingToolbar.setTitle(title);
        viewBinding.articleDate.setText(date);
        viewBinding.articleAuthor.setText(author);
        viewBinding.articleBody.setText(body);

//            final String aspectRatio = mCursor.getString(ArticleLoader.Query.ASPECT_RATIO);
//            final String ratio = String.format("%f", Float.valueOf(aspectRatio));
//            set.clone(viewBinding.constraintLayout);
//            //set.setDimensionRatio(viewBinding.photo.getId(), ratio);
//            set.applyTo(viewBinding.constraintLayout);

//            viewBinding.shareFab.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    startActivity(Intent.createChooser(ShareCompat.IntentBuilder.from(getActivity())
//                            .setType("text/plain")
//                           // .setText(body)
//                            .getIntent(), getString(R.string.action_share)));
//                }
//            });

    }
}


//    public static void hideTitleWhenExpanded(AppBarLayout appBarLayout,
//                                             final CollapsingToolbarLayout collapsingToolbarLayout,
//                                             final String title) {
//        // this section makes the title disappear when toolbar is expanded
//        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
//            boolean isShow = false;
//            int scrollRange = -1;
//
//            @Override
//            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
//                if (scrollRange == -1) {
//                    scrollRange = appBarLayout.getTotalScrollRange();
//                }
//                if (scrollRange + verticalOffset == 0) {
//                    collapsingToolbarLayout.setTitle(title);
//                    isShow = true;
//                } else if (isShow) {
//                    collapsingToolbarLayout.setTitle(" ");//careful there should a space between double quote otherwise it wont work
//                    isShow = false;
//                }
//            }
//        });
//    }
