package com.example.android.popularmovies;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * A placeholder fragment containing a simple view.
 */
public class MoviesFragment extends Fragment {

    public MoviesFragment() {
    }

    GridView gridView;
    MoviesAdapter mAdapter;
    ArrayList urlImagen = new ArrayList();

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_main, container, false);
        gridView = (GridView) view.findViewById(R.id.gridView);
        //mAdapter = new MoviesAdapter(getContext());
        //gridView.setAdapter(mAdapter);
        getResponse();



        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int num = position + 1;
                Bundle bundle = new Bundle();
                bundle.putInt("position", num);

                Intent intent = new Intent(getActivity(), MovieDetail.class);
                intent.putExtras(bundle);
                startActivity(intent);

                //Toast.makeText(getContext(), "Pelicula " + pos + " seleccionada", Toast.LENGTH_SHORT).show();

            }
        });

        return view;
    }

    private void getResponse() {
        urlImagen.clear();
        String urlString1 = "";
        String urlString = null;
        String builtUrl2 = null;
        String apiKey = "e6f2d5d6585cf0240d01f9da39a43d21";
        final String MOVIES_BASE_URL = "http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc";
        final String API_KEY = "api_key";

        Uri builtUrl = Uri.parse(MOVIES_BASE_URL).buildUpon()
                .appendQueryParameter(API_KEY, apiKey)
                .build();

        try {
            urlString1 = builtUrl.toString();
            urlString = URLEncoder.encode(urlString1, "UTF-8");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Error" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(urlString, new JsonHttpResponseHandler(){
            @TargetApi(Build.VERSION_CODES.KITKAT)


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);


                if (statusCode == 200){
                    super.onSuccess(statusCode, headers, response);
                    //Toast.makeText(getContext(), "Success!", Toast.LENGTH_SHORT).show();
                    //Log.d("OMG Android", response.toString());
                    final String RESULTS = "results";
                    final String POSTER_PATH = "poster_path";

                    try {

                        JSONObject jsonObject = new JSONObject(response.toString());
                        JSONArray results = new JSONArray();

                        results = jsonObject.getJSONArray(RESULTS);
                        gridView.setAdapter(new MoviesAdapter(getContext(), results, R.layout.row_movies));


                        /*
                        for (int i = 0; i<results.length(); i++){
                            JSONObject data = results.getJSONObject(i);
                            String dataImage = data.getString(POSTER_PATH);
                            Log.d("TAG", "DATA IMAGE: "+ dataImage);

                            gridView.setAdapter(new MoviesAdapter(getContext(), results));






                            //Log.d("TAG", "STRING: "+ results);
                            //Toast.makeText(getContext(), " String; " + jsonObject, Toast.LENGTH_SHORT).show();
                            //urlImagen.add(jsonObject.getJSONArray("poster_path"));
                            //urlImagen.add(jsonObject.getJSONObject(i).getString("poster_path"));
                        }*/
                        //gridView.setAdapter(new MoviesAdapter(getContext()));

                    }catch (JSONException e){
                        e.printStackTrace();

                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(getContext(), "Error: " + statusCode + " " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("OMG Android", statusCode + "" + throwable.getMessage());
            }
        });

    }









    /*
    public class FetchMoviesTask extends AsyncTask<String, Void, String[]> {

        final String LOG_TAG = FetchMoviesTask.class.getSimpleName();

        public String[] getMoviesDataFromJson (String moviesFromJsonStr) throws JSONException{


            return null;

        }

        protected String[] doInBackground(String... params) {

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String moviesJsonStr = null;

            String apiKey = "e6f2d5d6585cf0240d01f9da39a43d21";

            try {
                final String MOVIES_BASE_URL = "http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc";
                final String API_KEY = "api_key";

                Uri builtUrl = Uri.parse(MOVIES_BASE_URL).buildUpon()
                        .appendQueryParameter(API_KEY, apiKey)
                        .build();

                Log.v(LOG_TAG, "URL Built: "+builtUrl);

                URL url = new URL(builtUrl.toString());

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();

                if (inputStream == null){
                    return null;
                }

                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;

                while ((line = reader.readLine()) != null){
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0){
                    return null;
                }

                moviesJsonStr = buffer.toString();
                Log.v(LOG_TAG, "Movies Json: " + moviesJsonStr);

            }catch (IOException e){
                Log.e(LOG_TAG, "Error: ", e);
                return null;

            }finally {
                if (urlConnection != null){
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "ERROR Closing Reader: ", e);
                    }
                }
            }

            try {
                return getMoviesDataFromJson(moviesJsonStr);

            }catch (JSONException e){
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }
            return null;
        }
    }*/

}
