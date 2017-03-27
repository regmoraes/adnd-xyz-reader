package com.example.xyzreader.remote;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
public final class Network {

    private static final String BASE_URL = "https://nspf.github.io/XYZReader/";

    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private static final ArticleRestService articlesRestService = retrofit.create(ArticleRestService.class);

    public static ArticleRestService getArticlesRestService() {
        return articlesRestService;
    }
}
