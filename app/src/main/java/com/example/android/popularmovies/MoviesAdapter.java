package com.example.android.popularmovies;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

/**
 * Created by luismunoz on 1/20/16.
 */
public class MoviesAdapter extends BaseAdapter {
    Context context;

    public MoviesAdapter(Context context){
        super();
        this.context = context;
    }

    @Override
    public int getCount() {
        return movies.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        View row = convertView;

        if (row == null){
            imageView = new ImageView(context);
            //imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }else {
            imageView = (ImageView) row;
        }

        imageView.setImageResource(movies[position]);
        return imageView;
    }

    private Integer[] movies = {
            R.drawable.img1,
            R.drawable.img1,
            R.drawable.img1,
            R.drawable.img1,
            R.drawable.img1
    };


}
