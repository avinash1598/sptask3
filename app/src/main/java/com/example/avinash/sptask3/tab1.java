package com.example.avinash.sptask3;

import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.avinash.sptask3.R;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by AVINASH on 7/6/2016.
 */
public class tab1 extends Fragment {
    GridActivity ga = new GridActivity();
    ArrayList<databaseinfo> dbarr;
    MainActivity ma;
    database obj;
    databaseinfo dbin;
    GridView gv, gv2;
    static int len, lenmovie, lenseries;
    static ArrayList<databaseinfo> moviedb;
    static ArrayList<databaseinfo> seriesdb;
    static ArrayList<databaseinfo> alldb;
    View view;
    public static boolean flag1=true;
    public static Object lock = new Object();
    public static Object lock2 = new Object();
    checkbutonpressevent chk;
    Handler h;
    static ArrayList<String> arr;

    @Override
    public View onCreateView(LayoutInflater i, ViewGroup v, Bundle sia) {
        GridActivity ga = new GridActivity();
        view = i.inflate(R.layout.tab1layout, v, false);
        super.onCreate(sia);

        return view;

    }

    @Override
    public void onActivityCreated(Bundle sia) {
        super.onActivityCreated(sia);

        gv = (GridView) getActivity().findViewById(R.id.gridView2);
        Log.d("Fragment length", "" + ga.moviedb.size());
        moviedb = new ArrayList<databaseinfo>();
        seriesdb = new ArrayList<databaseinfo>();
        alldb = new ArrayList<databaseinfo>();
        chk=new checkbutonpressevent();
        obj = new database(view.getContext());
        arr=new ArrayList<String>();
        getgridview();
        if(chk.getState().toString().matches("NEW"))chk.start();
        else chk.play();

        h=new Handler(){
            public void handleMessage(android.os.Message m){
                orderby(obj.orderby());
            }
        };

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i=new Intent(view.getContext(),completeinfo.class);
                String title=moviedb.get(position).getTitle().toString();

                i.putExtra("pos",position);
                i.putExtra("title",title);
                startActivity(i);

                Log.d("posclicked",title);
            }

        });
    }

    public void getgridview() {
        Cursor c;
        moviedb.clear();
        seriesdb.clear();
        alldb.clear();
        arr.clear();
        custommovie cg;
        String mtype;

        for (int i = 0; i < ga.len; i++) {
            Log.d("posssssssssss", "" + i);
            c = obj.getinfo(i + 1);
            c.moveToFirst();
            Log.d("cursor ", "" + c.getCount());
            String t = c.getString(c.getColumnIndex(database.title));
            arr.add(t);
            String g = c.getString(c.getColumnIndex(database.genre));
            String p=c.getString(c.getColumnIndex(database.poster));
            mtype = c.getString(c.getColumnIndex(database.type));
            Log.d("titleeeeeeeeeeeeeee", "  " + t);
            dbin = new databaseinfo(t, g,p);
            alldb.add(dbin);

            if (mtype.matches("movie")) {
                Log.d("mov", "" + ga.lenmovie);
                ;
                moviedb.add(dbin);
            }
            if (mtype.matches("series")) {
                Log.d("serrrrrrrr", "" + ga.lenseries);
                seriesdb.add(dbin);

            }
        }
        Log.d("ggfghfhhhhhhhhhh", "" + alldb.size());
        cg = new custommovie(view.getContext(), moviedb);
        if (cg == null) {
            Log.d("ohhhhhhhhhhhhhh", "nulll");
        }
        gv.setAdapter(cg);
    }

    public void orderby(Cursor c) {
        //Cursor c;
        moviedb.clear();
        seriesdb.clear();
        alldb.clear();
        custommovie cg;
        String mtype;

        //c = obj.orderby();
        c.moveToFirst();
        for (int i = 0; i < ga.len; i++) {
            Log.d("posssssssssss", "" + i);
            Log.d("cursor ", "" + c.getCount());
            String t = c.getString(c.getColumnIndex(database.title));
            String g = c.getString(c.getColumnIndex(database.genre));
            String p=c.getString(c.getColumnIndex(database.poster));
            mtype = c.getString(c.getColumnIndex(database.type));
            Log.d("titleeeeeeeeeeeeeee", "  " + t);
            dbin = new databaseinfo(t, g,p);
            alldb.add(dbin);
            c.moveToNext();

            if (mtype.matches("movie")) {
                Log.d("mov", "" + ga.lenmovie);
                moviedb.add(dbin);
            }
            if (mtype.matches("series")) {
                Log.d("serrrrrrrr", "" + ga.lenseries);
                seriesdb.add(dbin);
            }
        }
        cg = new custommovie(view.getContext(), moviedb);
        Log.d("ggfghfhhhhhhhhhh", "" + alldb.size());
        if (cg == null) {
            Log.d("ohhhhhhhhhhhhhh", "nulll");
        }
        gv.setAdapter(cg);

    }

    public class checkbutonpressevent extends Thread {
        @Override
        public void run() {
            while (true) {
                if(ga.chkflag==1){
                    Bundle b = new Bundle();
                    b.putInt("index", 1);
                    Message m = h.obtainMessage();
                    m.setData(b);
                    h.sendMessage(m);

                    ga.chkflag=0;
                }

            }


        }
        public void check() {
            synchronized (lock2) {
                while (!flag1) {
                    try {
                        lock2.wait();
                    } catch (Exception e) {
                    }
                }
            }
        }

        public void pause() {
            flag1 = false;
        }

        public void play() {
            flag1 = true;
            synchronized (lock2) {
                lock2.notify();
            }
        }
    }
}
