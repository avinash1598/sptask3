package com.example.avinash.sptask3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by AVINASH on 7/6/2016.
 */
public class customgrid extends BaseAdapter {
    Context context;
    MainActivity m=new MainActivity();
    private static LayoutInflater inflater=null;

    public ArrayList<databaseinfo> arrlist=new ArrayList<databaseinfo>();
    public ArrayList<databaseinfo> temparrlist=new ArrayList<databaseinfo>();

    public customgrid(Context context, ArrayList<databaseinfo> list) {
        this.context=context;
        this.arrlist=list;
        this.temparrlist.addAll(list);
        inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return arrlist.size();
    }

    @Override
    public Object getItem(int position) {
        return arrlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public class Holder{
        TextView title,genre;
        ImageView img;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder=new Holder();
        View contactView;
        contactView=inflater.inflate(R.layout.moviegridlayout,null,false);
        holder.title=(TextView)contactView.findViewById(R.id.textView);
        holder.genre=(TextView)contactView.findViewById(R.id.textView2);
        holder.img=(ImageView)contactView.findViewById(R.id.imageView);
        holder.title.setText(arrlist.get(position).getTitle().toString());
        holder.genre.setText(arrlist.get(position).getGenre().toString());
        Picasso.with(context).load(arrlist.get(position).getPoster().toString()).placeholder(R.drawable.ic_action_name4).error(R.drawable.ic_action_name5)
                .resize(120,150).into(holder.img);
        return contactView;
    }
}
