package com.example.android.popularmovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by luismunoz on 1/20/16.
 */

public class MoviesAdapter extends BaseAdapter {
    Context context;
    LayoutInflater mInflater;
    JSONArray mJsonArray;
    String imageData;
    final String IMAGE_URL_BASE = "http://image.tmdb.org/t/p/w185";
    int resourceLayoutID;


    public MoviesAdapter(Context context, JSONArray mJsonArray, int resourceLayoutID){
        super();
        this.context = context;
        this.mJsonArray = mJsonArray;
        this.resourceLayoutID = resourceLayoutID;
        //mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {

        return mJsonArray.length();
    }

    @Override
    public Object getItem(int position) {

        return mJsonArray.optJSONObject(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        ImageView imageView;
        View row = convertView;

        if (row == null){
            LayoutInflater inflater = LayoutInflater.from(context);
            row = inflater.inflate(R.layout.row_movies, null);

            //Create a new Holder
            holder = new ViewHolder();
            holder.thumbnailImageView = (ImageView) row.findViewById(R.id.img_thumbnail);

            //Hang onto this holder for future recyclage
            row.setTag(holder);

            //imageView = new ImageView(context);
            //imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            //imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }else {
            holder = (ViewHolder) row.getTag();
        }

        //Get the current movies data in JSON form
        JSONObject jsonObject = (JSONObject) getItem(position);

        //See if there is a cover ID in the Object
        if (jsonObject.has("poster_path")){
            String imageID = jsonObject.optString("poster_path");
            //Construct the image URL (specific to API)
            String imageURL = IMAGE_URL_BASE + imageID;

            //Use Piccaso to load the image
            //Temporarily have a placeholder in case it's slow to load

            Picasso.with(context).load(imageURL).placeholder(R.drawable.img1).into(holder.thumbnailImageView);
        }else{
            //If there is no cover ID in the object, use placeholder
            holder.thumbnailImageView.setImageResource(R.drawable.img1);
        }

        return row;
    }

    /*private Integer[] movies = {
            R.drawable.img1,
            R.drawable.img1,
            R.drawable.img1,
            R.drawable.img1,
            R.drawable.img1
    };*/

    private static class ViewHolder {
        public ImageView thumbnailImageView;
    }


}
