package com.example.xyzreader.remote;

import com.example.xyzreader.data.Article;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
public interface ArticleRestService {

    @GET("data.json")
    Call<List<Article>> getArticles();
}
