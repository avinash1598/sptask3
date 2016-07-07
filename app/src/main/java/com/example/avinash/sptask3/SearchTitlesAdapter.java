package com.example.avinash.sptask3;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.widget.TextView;

/**
 * Created by AVINASH on 7/5/2016.
 */
public class SearchTitlesAdapter extends SimpleCursorAdapter {
    public SearchTitlesAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView textView = (TextView) view.findViewById(R.id.tvMovieTitle);
        textView.setText(cursor.getString(4) + " : " + cursor.getString(1));
    }


}
