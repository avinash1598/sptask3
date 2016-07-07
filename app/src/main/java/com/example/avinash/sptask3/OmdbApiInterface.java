package com.example.avinash.sptask3;




import retrofit.http.Query;
import retrofit.http.GET;
import rx.Observable;

/**
 * Created by AVINASH on 7/5/2016.
 */
public interface OmdbApiInterface {
    @GET("/")
    Observable<SearchResults> getSearchResults(@Query("s") String query,
                                               @Query("plot") String plot,
                                               @Query("type") String type,
                                               @Query("r") String format);

    @GET("/")
    Observable<Movie> getMovie(@Query("t") String title,
                               @Query("plot") String plot,
                               @Query("type") String type,
                               @Query("r") String format);
}
