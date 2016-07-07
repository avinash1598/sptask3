package com.example.avinash.sptask3;

/**
 * Created by AVINASH on 7/6/2016.
 */
public class databaseinfo {
    String title ;
    String year ;
    String type ;
    String released ;
    String runtime ;
    String genre ;
    String actors ;
    String plot ;
    String imdbRating ;
    String poster ;
    String Country ;
    String Rated ;
    String Writer ;
    String Language ;
    String Awards ;
    String Director ;
    public databaseinfo(String title,String genre,String poster){
        this.title=title;
        this.genre=genre;
        this.poster=poster;
    }

    public String getGenre() {
        return genre;
    }

    public String getTitle() {
        return title;
    }

    public String getImdbRating() {
        return imdbRating;
    }

    public String getPoster() {
        return poster;
    }
}
