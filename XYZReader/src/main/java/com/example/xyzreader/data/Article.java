package com.example.xyzreader.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class Article implements Parcelable {

    public Article() {
    }

    @SerializedName("aspect_ratio")
	private String aspectRatio;

	@SerializedName("thumb")
	private String thumb;

	@SerializedName("author")
	private String author;

	@SerializedName("photo")
	private String photo;

	@SerializedName("id")
	private long id;

	@SerializedName("title")
	private String title;

	@SerializedName("body")
	private String body;

	@SerializedName("published_date")
	private String publishedDate;

	public void setAspectRatio(String aspectRatio){
		this.aspectRatio = aspectRatio;
	}

	public String getAspectRatio(){
		return aspectRatio;
	}

	public void setThumb(String thumb){
		this.thumb = thumb;
	}

	public String getThumb(){
		return thumb;
	}

	public void setAuthor(String author){
		this.author = author;
	}

	public String getAuthor(){
		return author;
	}

	public void setPhoto(String photo){
		this.photo = photo;
	}

	public String getPhoto(){
		return photo;
	}

	public void setId(long id){
		this.id = id;
	}

	public long getId(){
		return id;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	public void setBody(String body){
		this.body = body;
	}

	public String getBody(){
		return body;
	}

	public void setPublishedDate(String publishedDate){
		this.publishedDate = publishedDate;
	}

	public String getPublishedDate(){
		return publishedDate;
	}

	@Override
 	public String toString(){
		return new Gson().toJson(this);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.aspectRatio);
		dest.writeString(this.thumb);
		dest.writeString(this.author);
		dest.writeString(this.photo);
		dest.writeLong(this.id);
		dest.writeString(this.title);
		dest.writeString(this.body);
		dest.writeString(this.publishedDate);
	}

	protected Article(Parcel in) {
		this.aspectRatio = in.readString();
		this.thumb = in.readString();
		this.author = in.readString();
		this.photo = in.readString();
		this.id = in.readLong();
		this.title = in.readString();
		this.body = in.readString();
		this.publishedDate = in.readString();
	}

	public static final Parcelable.Creator<Article> CREATOR = new Parcelable.Creator<Article>() {
		@Override
		public Article createFromParcel(Parcel source) {
			return new Article(source);
		}

		@Override
		public Article[] newArray(int size) {
			return new Article[size];
		}
	};
}