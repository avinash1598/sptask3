package com.example.avinash.sptask3;

import retrofit.Retrofit;
import rx.Observable;

/**
 * Created by AVINASH on 7/5/2016.
 */
public class OmbApiObservables {
    private Retrofit mRetrofit;
    private RetrofitHelper mRetrofitHelper;
    private OmdbApiInterface apiInterface;

    public OmbApiObservables() {
        mRetrofitHelper = RetrofitHelper.getInstance();
        mRetrofit = mRetrofitHelper.getRetrofit();
        apiInterface = mRetrofit.create(OmdbApiInterface.class);
    }
    public Observable<SearchResults> getSearchResultsApi(String query, String type) {
        return apiInterface.getSearchResults(query, "short", type, "json");
    }
}
