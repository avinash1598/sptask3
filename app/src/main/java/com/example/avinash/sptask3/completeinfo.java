package com.example.avinash.sptask3;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by AVINASH on 7/7/2016.
 */
public class completeinfo extends AppCompatActivity {
    database obj;
    databaseinfo dbin;
    ArrayList<String> arrayList;
    int pos;String ctitle;
    MainActivity  ma;
    ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.infocomplete);
        obj = new database(this);
        ma=new MainActivity();
        arrayList=new ArrayList<String>();
        Intent i=getIntent();
        ctitle=i.getStringExtra("title");
        iv=(ImageView)findViewById(R.id.imageView2);
        pos=i.getIntExtra("pos", 0);
        getview(pos);
        ListView lv=(ListView)findViewById(R.id.listView);

        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,R.layout.listtextview,arrayList);
        lv.setAdapter(arrayAdapter);

    }

    public void getview(int pos) {
        Cursor c=null;
            Log.d("pos", "" + pos);
        String title=null;

        for(int i=0;i<ma.len;i++){
            c=obj.getinfo(i+1);
            c.moveToFirst();
             title = c.getString(c.getColumnIndex(database.title));
            if(title.matches(ctitle)){
                c.moveToFirst();
                break;}
        }


          arrayList.clear();
        { String year= c.getString(c.getColumnIndex(database.year)) ;
        String type= c.getString(c.getColumnIndex(database.type)) ;
        String released = c.getString(c.getColumnIndex(database.released));
        String runtime= c.getString(c.getColumnIndex(database.runtime)) ;
        String genre= c.getString(c.getColumnIndex(database.genre)) ;
        String actors = c.getString(c.getColumnIndex(database.actors));
        String plot= c.getString(c.getColumnIndex(database.plot)) ;
        String imdbRating= c.getString(c.getColumnIndex(database.imdbRating)) ;
        String poster = c.getString(c.getColumnIndex(database.poster));
           Picasso.with(this).load(poster).placeholder(R.drawable.ic_action_name4).error(R.drawable.ic_action_name5)
                   .resize(150,200).into(iv);
        String Country= c.getString(c.getColumnIndex(database.Country)) ;
        String Writer= c.getString(c.getColumnIndex(database.Writer)) ;
        String Language= c.getString(c.getColumnIndex(database.Language)) ;
        String Awards= c.getString(c.getColumnIndex(database.Awards)) ;
        String Director= c.getString(c.getColumnIndex(database.Director)) ;
            Log.d("titleeeeeeeeeeeeeee", "  " + title);
           arrayList.add(title);arrayList.add(year);arrayList.add(type);arrayList.add(released);arrayList.add(runtime);arrayList.add(genre);
        arrayList.add(actors);arrayList.add(plot);arrayList.add(imdbRating);arrayList.add(poster);arrayList.add(Country);
        arrayList.add(Writer);arrayList.add(Language);arrayList.add(Awards);arrayList.add(Director);
        }}
}
