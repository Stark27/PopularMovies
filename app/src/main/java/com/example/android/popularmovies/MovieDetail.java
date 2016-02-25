package com.example.android.popularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class MovieDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String name = null;
        String description = null;
        String urlImage = null;
        String anioString = null;
        String anio = null;
        String dataNumber = null;
        final  String BASE_URL_IMAGE = "http://image.tmdb.org/t/p/w185";


        //TextView textView = (TextView) findViewById(R.id.textView);
        TextView textView2 = (TextView) findViewById(R.id.nameMovie);
        TextView textView3 = (TextView) findViewById(R.id.descriptionMovie);
        ImageView imageDetail = (ImageView) findViewById(R.id.imageDetail);
        TextView anioTextView = (TextView) findViewById(R.id.anioMovie);
        TextView dataTexView = (TextView) findViewById(R.id.data);
        Button buttonFavorite = (Button) findViewById(R.id.buttonFavorite);
        //Intent intent = new Intent();

        Bundle extras = getIntent().getExtras();

        if (extras != null){
            //int num = extras.getInt("position");
            String data = extras.getString("algo");

            try {
                JSONObject object = new JSONObject(data);

                if (object.has("original_title")){
                    name = object.optString("original_title");
                }

                if (object.has("overview")){
                    description = object.optString("overview");
                }

                if (object.has("backdrop_path")){
                    urlImage = object.optString("poster_path");
                    String imageFinal = BASE_URL_IMAGE + urlImage;

                    Picasso.with(getApplicationContext()).load(imageFinal).into(imageDetail);
                }

                if (object.has("release_date")){
                    anioString = object.optString("release_date");
                    anio = anioString.substring(0, 4);
                }

                if (object.has("vote_average")){
                    dataNumber = object.optString("vote_average");
                    dataNumber = dataNumber + "/10";

                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

            textView2.setText(name);
            textView3.setText(description);
            anioTextView.setText(anio);
            dataTexView.setText(dataNumber);


        }else{
            Toast.makeText(getBaseContext(), "no viene extra", Toast.LENGTH_SHORT).show();
        }

        buttonFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Toast.makeText(getApplicationContext(), "Save as favorite", Toast.LENGTH_SHORT).show();
            }
        });
    }


    }
