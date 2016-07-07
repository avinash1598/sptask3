package com.example.avinash.sptask3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by AVINASH on 7/5/2016.
 */

public class database extends SQLiteOpenHelper {
    public static final String dbname="movieapi";
    public static final String title = ("Title");
    public static final String year = ("year");
    public static final String type=("type");
    public static final String released = ("released");
    public static final String runtime = ("runtime");
    public static final String genre = ("genre");
    public static final String actors = ("actors");
    public static final String plot = ("plot");
    public static final String imdbRating = ("imdbRating");
    public static final String poster = ("Poster");
    public static final String Country=("Country");
    public static final String Rated=("Rated");
    public static final  String Writer=("Writer");
    public static final  String Language=("Language");
    public static final String Awards=("Awards");
    public static final String Director=("Director");


    database(Context c) {
        super(c, dbname, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table movieinfo" + "(id integer primary key,Title text,year text,type text,released text,runtime text,genre text,actors text,plot text,imdbRating text," +
                "Country text,Writer text,Language text,Awards text,Director text,Poster text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST movieinfo");
        onCreate(db);
    }
    public boolean insertContcts(String title,String year,String type,String released,String runtime,String genre,String actors,String plot,String imbdrating,
                                 String country,String writer,String language,String awards,String director,String poster){
        SQLiteDatabase db=this.getReadableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("Title",title); cv.put("year",year); cv.put("type", type); cv.put("released",released); cv.put("runtime",runtime); cv.put("genre",genre);
        cv.put("actors",actors); cv.put("plot",plot); cv.put("imdbRating",imbdrating); cv.put("Country",country);
        cv.put("Writer",writer); cv.put("Language",language); cv.put("Awards",awards); cv.put("Director",director);
        cv.put("Poster",poster);
        db.insert("movieinfo", null, cv);
        return true;
    }
    public Cursor getinfo(int id){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cr=db.rawQuery("select * from movieinfo where id="+id+"",null);
        return cr;
    }
    public Cursor orderby(){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cr=db.rawQuery("select * from movieinfo order by imdbRating desc",null);
        return cr;
    }
}
