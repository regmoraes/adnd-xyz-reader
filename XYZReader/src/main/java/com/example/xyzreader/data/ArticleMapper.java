package com.example.xyzreader.data;

import android.content.ContentValues;
import android.database.Cursor;

public class ArticleMapper {

	public static Article fromCursor(Cursor cursor) {

		Article article = new Article();
		article.setId(cursor.getLong(ArticleLoader.Query._ID));
		article.setAspectRatio(cursor.getString(ArticleLoader.Query.ASPECT_RATIO));
		article.setThumb(cursor.getString(ArticleLoader.Query.THUMB_URL));
		article.setAuthor(cursor.getString(ArticleLoader.Query.AUTHOR));
		article.setPhoto(cursor.getString(ArticleLoader.Query.PHOTO_URL));
		article.setTitle(cursor.getString(ArticleLoader.Query.TITLE));
		article.setBody(cursor.getString(ArticleLoader.Query.BODY));
		article.setPublishedDate(cursor.getString(ArticleLoader.Query.PUBLISHED_DATE));

		return article;
	}

	public static ContentValues toContentValues(Article article) {

		ContentValues values = new ContentValues();
		values.put(ItemsContract.Items.SERVER_ID, article.getId());
		values.put(ItemsContract.Items.AUTHOR, article.getAuthor());
		values.put(ItemsContract.Items.TITLE, article.getTitle());
		values.put(ItemsContract.Items.BODY, article.getBody());
		values.put(ItemsContract.Items.THUMB_URL, article.getThumb());
		values.put(ItemsContract.Items.PHOTO_URL, article.getPhoto());
		values.put(ItemsContract.Items.ASPECT_RATIO, article.getAspectRatio());
		values.put(ItemsContract.Items.PUBLISHED_DATE, article.getPublishedDate());

		return values;
	}
}