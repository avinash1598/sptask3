package com.example.avinash.sptask3;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.MatrixCursor;
import android.database.Cursor;
import android.graphics.Matrix;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Observable;
import java.util.concurrent.TimeUnit;

import retrofit.HttpException;
import retrofit.RxJavaCallAdapterFactory;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;



public class MainActivity extends AppCompatActivity {
    TextView sv;
    TextView tv;
    SearchView ev;
    int flag = 0;
    EditText e;
    public static boolean flag1=true;
    public static Object lock = new Object();
    public static Object lock2 = new Object();
    public Handler h;
    int i=0;
    customgrid cg;

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
    database obj=new database(this);
    databaseinfo dbin;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "data";
    static ArrayList<databaseinfo> moviedb;
    static ArrayList<databaseinfo> seriesdb;
    static ArrayList<databaseinfo> alldb;
    static int len=0,lenmovie=0,lenseries=0;
    GridView gv;

    //////////////////////////////////////////////////////////////////////////////////////


    private Subscription searchResultsSubscription;
    private SearchTitlesAdapter mSearchViewAdapter;
    private MatrixCursor matrixCursor;
    private OmbApiObservables mOmdbApiObservables;
    private volatile String mFilterSelection = "";
    private AutoCompleteTextView actv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sv = (TextView) findViewById(R.id.textView4);
        sv.setHint("selected movie display");
        mOmdbApiObservables = new OmbApiObservables();

        moviedb=new ArrayList<databaseinfo>();
        seriesdb=new ArrayList<databaseinfo>();
        alldb=new ArrayList<databaseinfo>();
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        try {
            String len1 = sharedpreferences.getString("start", null);
            len= Integer.parseInt(len1);
            lenmovie= Integer.parseInt(sharedpreferences.getString("movie",null));
            lenseries= Integer.parseInt(sharedpreferences.getString("series",null));
            Log.d("statussssss", "inside"+""+len);
        } catch (Exception e) {
            Toast.makeText(this, "CURRENTLY NO SAVED VALUE", Toast.LENGTH_SHORT).show();
        }

        h=new Handler(){
            public void handleMessage(android.os.Message m){

                        inserttodatabase();


            }
        };
    }

    public void result(View view) {
        getgridview();
        Intent i=new Intent(this,GridActivity.class);
        i.putExtra("dblen",len);
        i.putExtra("movlen",lenmovie);
        i.putExtra("serlen",lenseries);
        startActivity(i);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (searchResultsSubscription != null && !searchResultsSubscription.isUnsubscribed())
            searchResultsSubscription.unsubscribe();

        if (!matrixCursor.isClosed())
            matrixCursor.close();
    }


    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        ev = (SearchView) menu.findItem(R.id.search).getActionView();
        matrixCursor = new MatrixCursor(CPUtils.COLUMNS);
        mSearchViewAdapter = new SearchTitlesAdapter(MainActivity.this, R.layout.search_auto_complete_item_layout, matrixCursor, CPUtils.COLUMNS, null, -1100);
        ev.setSuggestionsAdapter(mSearchViewAdapter);
        ev.setQueryHint("Enter movie name to add in database");




        //**********************************************suggestion*************************************************

        ev.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionSelect(int position) {
                return false;
            }

            @Override
            public boolean onSuggestionClick(int position) {
                int check = 0;
                i = 0;
                {
                    Cursor cursor = (Cursor) ev.getSuggestionsAdapter().getItem(position);
                    String movieTitle = cursor.getString(4);
                    sv.setText(movieTitle);
                    ev.setIconified(true);
                    ev.clearFocus();
                    (menu.findItem(R.id.search)).collapseActionView();
                    Log.d("title", movieTitle);

                    if (check == 0) {
                        flag = 0;
                        info i = new info();
                        i.start();
                        check = 1;
                    }
                }
                return true;
            }
        });


        ev.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(MainActivity.this,"PLEASE SELECT FROM THE SUGGESTION LIST",Toast.LENGTH_LONG).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() > 2) {
                    try {
                        if (searchResultsSubscription != null && !searchResultsSubscription.isUnsubscribed()) {
                            searchResultsSubscription.unsubscribe();
                            matrixCursor = CPUtils.convertResultsToCursor(new ArrayList<Search>());
                            mSearchViewAdapter.changeCursor(matrixCursor);
                        }
                        String encodedQuery = URLEncoder.encode(newText, "UTF-8");
                        rx.Observable<SearchResults> observable = mOmdbApiObservables.getSearchResultsApi(encodedQuery, mFilterSelection);
                        searchResultsSubscription = observable
                                .debounce(250, TimeUnit.MILLISECONDS)
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribeOn(Schedulers.io())
                                .subscribe(searchResultsSubscriber());
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }

                return true;
            }


        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public class info extends Thread {
        @Override
        public void run() {
            while (true) {
                if (flag == 0) {
                    try {
                        searchResultsSubscription.unsubscribe();

                        // String selectedItem = jListFilms.getSelectedValue().toString().replace("\\s+", "+");
                        String selectedItem = sv.getText().toString();

                        InputStream input ;
                        input=new URL("http://www.omdbapi.com/?t=" + URLEncoder.encode(selectedItem, "UTF-8")).openStream();
                        Map<String, String> map = new Gson().fromJson(new InputStreamReader(input, "UTF-8"), new TypeToken<Map<String, String>>() {
                        }.getType());

                         title = map.get("Title");
                         year = map.get("Year");
                         type = map.get("Type");
                         released = map.get("Released");
                         runtime = map.get("Runtime");
                         genre = map.get("Genre");
                         actors = map.get("Actors");
                        plot = map.get("Plot");
                        imdbRating = map.get("imdbRating");
                         poster = map.get("Poster");
                         Country = map.get("Country");
                         Rated = map.get("Rated");
                         Writer = map.get("Writer");
                        Language = map.get("Language");
                        Awards = map.get("Awards");
                         Director = map.get("Director");

                        Log.d("yeeeeeeeeeeeeear",type);
                        i++;Log.d("val of i", "" + i);
                        if(i==1) {

                            Bundle b = new Bundle();
                            b.putInt("index", 1);
                            Message m = h.obtainMessage();
                            m.setData(b);
                            h.sendMessage(m);
                        }


                    } catch (JsonIOException | JsonSyntaxException | IOException e) {
                        System.out.println(e);
                    }
                    flag = 1;
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

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private Subscriber<SearchResults> searchResultsSubscriber() {
        return new Subscriber<SearchResults>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
//                HttpException exception = (HttpException) e;
               // Log.e(MovieSearchFragment.class.getName(), "Error: " + exception.code());
            }

            @Override
            public void onNext(SearchResults searchResults) {
                MatrixCursor matrixCursor = CPUtils.convertResultsToCursor(searchResults.getSearch());
                mSearchViewAdapter.changeCursor(matrixCursor);
            }
        };
    }

    public void inserttodatabase(){

        if(obj.insertContcts( title , year , type , released , runtime , genre, actors, plot, imdbRating, Country, Writer,Language, Awards
        ,Director,poster )){
            Log.d("meeeeeeeeeeeessage","inserted");len++;

            if(type.matches("movie")){
                SharedPreferences.Editor editor = sharedpreferences.edit();
                lenmovie++;
                editor.putString("movie", ""+lenmovie);
                editor.apply();
            }
            if(type.matches("series")){
                SharedPreferences.Editor editor = sharedpreferences.edit();
                lenseries++;
                editor.putString("series", ""+lenseries);
                editor.apply();
            }

            try {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("start", ""+len);
                editor.apply();
        }catch (Exception e){
                Log.d("error:    ",e.toString());
            }}
        else Log.d("meeeeesg", "errrrrrrrrrrrrrrrror");


    }
    public void getgridview(){
        Cursor c;
        moviedb.clear();
        seriesdb.clear();
        customgrid cg;
        String mtype;
        for(int i=0;i<len;i++){
            c = obj.getinfo(i+1);
            c.moveToFirst();
            String t=c.getString(c.getColumnIndex(database.title));
            String g=c.getString(c.getColumnIndex(database.genre));
            String p=c.getString(c.getColumnIndex(database.poster));
            mtype=c.getString(c.getColumnIndex(database.type));
            dbin=new databaseinfo(t,g,p);
            alldb.add(dbin);

            if(mtype.matches("movie"))
            {Log.d("mov",""+lenmovie);;
                moviedb.add(dbin);

            }
            if(mtype.matches("series")){
                Log.d("serrrrrrrr",""+lenseries);
            seriesdb.add(dbin);

            }
        }
        //cg=new customgrid(this,seriesdb);
        //gv.setAdapter(cg);
    }



}