package com.example.android.popularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

public class MovieDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView textView = (TextView) findViewById(R.id.textView);
        //Intent intent = new Intent();

        Bundle extras = getIntent().getExtras();

        if (extras != null){
            int num = extras.getInt("position");
            textView.setText("Seleccionó la imágen número: "+ num);
        }else{
            Toast.makeText(getBaseContext(), "no viene extra", Toast.LENGTH_SHORT).show();
        }
    }
    }
