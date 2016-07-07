package com.example.avinash.sptask3;

import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.avinash.sptask3.R;

import java.util.ArrayList;

/**
 * Created by AVINASH on 7/6/2016.
 */
public class tab2 extends Fragment {
    GridActivity ga=new GridActivity();
    ArrayList<databaseinfo> dbarr;
    MainActivity ma;
    database obj;
    databaseinfo dbin;
    GridView gv,gv2;
    static int len,lenmovie,lenseries;
    static ArrayList<databaseinfo> moviedb;
    static ArrayList<databaseinfo> seriesdb;
    static ArrayList<databaseinfo> alldb;
    View view;
    public static boolean flag1=true;
    public static Object lock = new Object();
    public static Object lock2 = new Object();
    checkbutonpressevent2 chk;
    Handler h;

    public String orderbyflag;

    @Override
        public View onCreateView(LayoutInflater i, ViewGroup v, Bundle sia) {
            GridActivity ga=new GridActivity();
            view=i.inflate(R.layout.tab2layout, v, false);
            return view;
        }
    @Override
    public void onActivityCreated(Bundle sia){
        super.onActivityCreated(sia);

        gv2=(GridView)getActivity().findViewById(R.id.gridView3);
        Log.d("Fragment length", "" + ga.moviedb.size());
        moviedb=new ArrayList<databaseinfo>();
        seriesdb=new ArrayList<databaseinfo>();
        alldb=new ArrayList<databaseinfo>();
        obj=new database(view.getContext());
        getgridview();
        chk=new checkbutonpressevent2();
        if(chk.getState().toString().matches("NEW"))chk.start();
        else chk.play();

        h=new Handler(){
            public void handleMessage(android.os.Message m){
                orderby(obj.orderby());
            }
        };

        ////////////////////////////////////////////////////////////////////////////////////////////gfdgffgfgfg

        gv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i=new Intent(view.getContext(),completeinfo.class);
                String title=seriesdb.get(position).getTitle().toString();

                i.putExtra("pos",position);
                i.putExtra("title",title);
                startActivity(i);

                Log.d("posclicked",title);
            }

        });
    }

    public void orderby(Cursor c) {

        //Cursor c;
        moviedb.clear();
        seriesdb.clear();
        alldb.clear();
        customgrid cg;
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
                ;
                moviedb.add(dbin);
            }
            if (mtype.matches("series")) {
                Log.d("serrrrrrrr", "" + ga.lenseries);
                seriesdb.add(dbin);
            }
        }
        cg = new customgrid(view.getContext(), seriesdb);
        gv2.setAdapter(cg);

    }


    public void getgridview(){
        Cursor c;
        moviedb.clear();
        seriesdb.clear();
        alldb.clear();
        custommovie cg;
        String mtype;

        for(int i=0;i<ga.len;i++){
            Log.d("posssssssssss",""+i);
            c = obj.getinfo(i+1);
            c.moveToFirst();
            Log.d("cursor ",""+c.getCount());
            String t=c.getString(c.getColumnIndex(database.title));
            String g=c.getString(c.getColumnIndex(database.genre));
            String p=c.getString(c.getColumnIndex(database.poster));
            mtype=c.getString(c.getColumnIndex(database.type));
            Log.d("titleeeeeeeeeeeeeee","  "+t);
            dbin=new databaseinfo(t,g,p);
            alldb.add(dbin);

            if(mtype.matches("movie"))
            {Log.d("mov", "" + ga.lenmovie);;
                moviedb.add(dbin);
            }
            if(mtype.matches("series")){
                Log.d("serrrrrrrr",""+ga.lenseries);
                seriesdb.add(dbin);
            }
        }
        cg= new custommovie(view.getContext(),seriesdb);
        gv2.setAdapter(cg);
    }



    public class checkbutonpressevent2 extends Thread {
        @Override
        public void run() {
            while (true&&!currentThread().isInterrupted()) {
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

