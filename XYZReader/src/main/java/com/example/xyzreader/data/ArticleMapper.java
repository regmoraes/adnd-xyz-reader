package com.example.xyzreader.data;

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
}