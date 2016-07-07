package com.example.avinash.sptask3;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.GridView;


import java.util.ArrayList;

/**
 * Created by AVINASH on 7/6/2016.
 */
public class GridActivity extends AppCompatActivity {
    static ArrayList<databaseinfo> moviedb;
    static ArrayList<databaseinfo> seriesdb;
    static ArrayList<databaseinfo> alldb;
    AutoCompleteTextView autoCompleteTextView;
    Button button;
    ArrayList<databaseinfo> dbarr;
    MainActivity ma;
    database obj;
    databaseinfo dbin;
    GridView gv5,gv2;
    static int len,lenmovie,lenseries;
    public static int chkflag=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitygrid);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        TabLayout tl = (TabLayout) findViewById(R.id.tab_layout);
        tl.addTab(tl.newTab().setText("MOVIE"));
        tl.addTab(tl.newTab().setText("SERIES"));


        moviedb=new ArrayList<databaseinfo>();
        seriesdb=new ArrayList<databaseinfo>();
        alldb=new ArrayList<databaseinfo>();

        obj=new database(this);
        ma=new MainActivity();

        Intent i=getIntent();
        len=i.getIntExtra("dblen",0);
        lenmovie=i.getIntExtra("movlen",0);
        lenseries=i.getIntExtra("serlen",0);
        Log.d("detailsssssssssssssss",""+len+" "+lenmovie+" "+lenseries);

        gv5=(GridView)findViewById(R.id.gridView2);
        gv2=(GridView)findViewById(R.id.gridView3);


        final ViewPager vp = (ViewPager) findViewById(R.id.pager);
        final pageadapter pa = new pageadapter(getSupportFragmentManager(), tl.getTabCount());
        vp.setAdapter(pa);

        vp.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tl));

        vp.setOnDragListener(new ViewPager.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                Log.d("event", String.valueOf(event));
                return false;
            }
        });

        tl.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                vp.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.gridmenu, menu);
        autoCompleteTextView = (AutoCompleteTextView) menu.findItem(R.id.autoTextView).getActionView();
        button = (Button) menu.findItem(R.id.search_button).getActionView();
        Button sort=(Button)menu.findItem(R.id.sort_button).getActionView();
        //Button lsearch=(Button)menu.findItem(R.id.search_list).getActionView();

        tab1 t=new tab1();
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(GridActivity.this,R.layout.tabsearch,t.arr);
        autoCompleteTextView.setAdapter(arrayAdapter);
        autoCompleteTextView.setWidth(250);
        autoCompleteTextView.setDropDownWidth(450);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String t=autoCompleteTextView.getText().toString();
                //autoCompleteTextView.setIconified(true);
                autoCompleteTextView.clearFocus();
                (menu.findItem(R.id.autoTextView)).collapseActionView();
                Log.d("inside",t);
                Intent i=new Intent(GridActivity.this,completeinfo.class);
                i.putExtra("title",t);i.putExtra("pos",position);
                startActivity(i);
            }
        });
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if(id==R.id.search_button){
            Intent i=new Intent(GridActivity.this,MainActivity.class);
            startActivity(i);
            return true;
        }
        if(id==R.id.sort_button){
            chkflag=1;
            return true;
        }
        if(id==R.id.autoTextView){
            autoCompleteTextView.setText("");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
