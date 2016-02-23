package com.jianqi.poppy.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jianqi.poppy.R;
import com.jianqi.poppy.model.Movie;
import com.jianqi.poppy.model.MovieResult;

import java.util.List;

/**
 * Created by JianQi on 16-Feb-16.
 */
public class ImageAdapter extends ArrayAdapter<Movie> {


    private final String LOG_TAG = this.getClass().getSimpleName();
    private Context context;
    private int resource;
    private List<Movie> movieList;
    private int count;

    public ImageAdapter(Context context, int resource, List<Movie> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.movieList = objects;
    }


    @Override
    public int getCount() {
        return movieList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ImageView imageView = null;
        TextView textView = null;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.gridviewitem, parent, false);
        }
        //imageView = (ImageView)convertView.findViewById(R.id.imageView);
        textView = (TextView)convertView.findViewById(R.id.testTextView);

        //Glide.with(getContext()).load(movieList.get(position).getPosterPath()).into(imageView);

        return convertView;
    }


}
