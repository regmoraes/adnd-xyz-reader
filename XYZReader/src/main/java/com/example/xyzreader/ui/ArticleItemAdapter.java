package com.example.xyzreader.ui;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.xyzreader.R;
import com.example.xyzreader.data.ArticleLoader;
import com.example.xyzreader.util.DateUtils;

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
public class ArticleItemAdapter extends RecyclerView.Adapter<ArticleItemAdapter.ViewHolder> {

    private static final String TAG = ArticleListActivity.class.toString();

    private Cursor mCursor;

    private AdapterClickListener listener;

    private ConstraintSet set = new ConstraintSet();

    public ArticleItemAdapter(Cursor cursor, AdapterClickListener listener) {
        this.mCursor = cursor;
        this.listener = listener;
    }

    @Override
    public long getItemId(int position) {
        mCursor.moveToPosition(position);
        return mCursor.getLong(ArticleLoader.Query._ID);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_article, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        mCursor.moveToPosition(position);

        String title = mCursor.getString(ArticleLoader.Query.TITLE);
        String author = mCursor.getString(ArticleLoader.Query.AUTHOR);
        String imageUrl = mCursor.getString(ArticleLoader.Query.THUMB_URL);
        String aspectRatio = mCursor.getString(ArticleLoader.Query.ASPECT_RATIO);

        holder.titleView.setText(title);
        holder.authorView.setText(author);
        holder.dateView.setText(DateUtils.parsePublishedDate(
                mCursor.getString(ArticleLoader.Query.PUBLISHED_DATE))
        );

        Glide.with(holder.thumbnailView.getContext())
                .load(imageUrl)
                .into(holder.thumbnailView);

        String ratio = String.format("%f", Float.valueOf(aspectRatio));
        set.clone(holder.constraintLayout);
        set.setDimensionRatio(holder.thumbnailView.getId(), ratio);
        set.applyTo(holder.constraintLayout);

        ViewCompat.setTransitionName(holder.thumbnailView,
                "thumbnail" + holder.getAdapterPosition());
    }

    @Override
    public int getItemCount() {
        return mCursor != null ? mCursor.getCount() : 0;
    }

    interface AdapterClickListener {
        void onArticleClicked(long articleId, ImageView thumbnail);
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ConstraintLayout constraintLayout;
        ImageView thumbnailView;
        TextView titleView;
        TextView authorView;
        TextView dateView;

        ViewHolder(View view) {
            super(view);
            constraintLayout = view.findViewById(R.id.constraint);
            thumbnailView = view.findViewById(R.id.thumbnail);
            titleView = view.findViewById(R.id.article_title);
            authorView = view.findViewById(R.id.article_author);
            dateView = view.findViewById(R.id.article_date);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            listener.onArticleClicked(getItemId(), thumbnailView);
        }
    }

    public void setData(Cursor cursor){
        this.mCursor = cursor;
        notifyDataSetChanged();
    }
}
