package com.example.avinash.sptask3;


import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import retrofit.GsonConverterFactory;


/**
 * Created by AVINASH on 7/5/2016.
 */
public class RetrofitHelper {
    private static final String BASE_URL = "http://www.omdbapi.com";
    private static RetrofitHelper mRetrofitHelper;
    private Retrofit mRetrofit;

    private RetrofitHelper() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    public static RetrofitHelper getInstance() {
        if (mRetrofitHelper == null) {
            synchronized (RetrofitHelper.class) {
                if (mRetrofitHelper == null)
                    mRetrofitHelper = new RetrofitHelper();
            }
        }
        return mRetrofitHelper;
    }

    public Retrofit getRetrofit() {
        return mRetrofit;
    }
}
