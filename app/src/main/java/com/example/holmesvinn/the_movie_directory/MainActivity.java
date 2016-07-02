package com.example.holmesvinn.the_movie_directory;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    public String jsdata="Cannot Get DATA... Refresh and try Again";
    private List<ModelClass> movieList;
    public ListView listView;
    public int length;
    public ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()

        .cacheInMemory(true)
                .cacheOnDisk(true)

        .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())

        .defaultDisplayImageOptions(defaultOptions)

        .build();
        ImageLoader.getInstance().init(config);

        listView = (ListView)findViewById(R.id.listView);


        connectivity_check();






    }

    private void connectivity_check() {

        ConnectivityManager cm =
                (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if(isConnected)
        {
            new JSONTask().execute("http://api.themoviedb.org/3/movie/popular?api_key=00253a9bc19586a5c5882f8d9877e6a5");
        }
        else
        {


            Toast.makeText(MainActivity.this, "Connect to INTERNET :)", Toast.LENGTH_SHORT).show();

          //  connectivity_check();


        }
    }


    public class JSONTask extends AsyncTask<String,String,List<ModelClass>> {

        private ProgressDialog pDialog = new ProgressDialog(MainActivity.this);


        @Override
        protected List<ModelClass> doInBackground(String... params) {
            HttpURLConnection connection = null;

            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();

                String line = " ";

                while ((line = reader.readLine()) != null) {

                    buffer.append(line);
                }



                jsdata = buffer.toString();



                JSONObject jsonObject = new JSONObject(jsdata);
                JSONArray jsonArray = jsonObject.getJSONArray("results");

                length = jsonArray.length();

                List<ModelClass> modelClassList = new ArrayList<>();
                for(int i=0;i<jsonArray.length();i++)
                {
                    JSONObject Results = jsonArray.getJSONObject(i);

                    ModelClass modelClass = new ModelClass();

                    modelClass.setPoster(Results.getString("poster_path"));
                    modelClass.setOverview(Results.getString("overview"));
                    modelClass.setLanguage(Results.getString("original_language"));
                    modelClass.setAverage(Results.getDouble("vote_average"));
                    modelClass.setBackposter(Results.getString("backdrop_path"));
                    modelClass.setVote(Results.getInt("vote_count"));
                    modelClass.setTitle(Results.getString("title"));
                    modelClass.setDate(Results.getString("release_date"));

                    modelClassList.add(modelClass);
                }
                return modelClassList;


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }


            return null;
        }


        @Override
        protected void onPreExecute() {
            this.pDialog.setMessage("Loading Data...");
            pDialog.show();



        }

        @Override
        protected void onPostExecute(List<ModelClass> result) {
            super.onPostExecute(result);
            pDialog.dismiss();

            MovieAdapter adapter =new MovieAdapter(getApplicationContext(),R.layout.single_item,result);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {



                    Intent intent = new Intent(MainActivity.this,Individual_data.class);

                    String title = movieList.get(position).getTitle();
                    String poster=movieList.get(position).getBackposter();
                    String overview = movieList.get(position).getOverview();
                    String date = movieList.get(position).getDate();
                    String language = movieList.get(position).getLanguage();
                    double average =movieList.get(position).getAverage();
                    int vote = movieList.get(position).getVote();


                    Bundle b = new Bundle();
                    b.putString("titleb",title);
                    b.putString("posterb",poster);
                    b.putString("overviewb",overview);
                    b.putString("dateb",date);
                    b.putString("languageb",language);
                    b.putDouble("averageb",average );
                    b.putInt("voteb", vote);
                    intent.putExtras(b);
                    startActivity(intent);

                }
            });

        }
    }



    public class MovieAdapter extends ArrayAdapter{


        private int resource;
        private LayoutInflater infalter;

        public MovieAdapter(Context context, int resource, List<ModelClass> objects) {
            super(context, resource, objects);
            movieList = objects;
            this.resource =resource;
            infalter =(LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if(convertView == null){
                convertView = infalter.inflate(resource,null);
            }
            ImageView imageicon;
            TextView title;
            TextView language;
            TextView release;
            TextView counts;
            final ProgressBar progressbar;

            imageicon = (ImageView)convertView.findViewById(R.id.imageView);
            title = (TextView) convertView.findViewById(R.id.tv_title);
            language = (TextView)convertView.findViewById(R.id.tv_lang);
            release = (TextView)convertView.findViewById(R.id.tv_release);
            progressbar = (ProgressBar)convertView.findViewById(R.id.progressBar);
            counts = (TextView)convertView.findViewById(R.id.tv_counts);


            title.setText(movieList.get(position).getTitle());

            language.setText("Language: "+movieList.get(position).getLanguage());
            counts.setText("Voting Counts: "+ movieList.get(position).getVote());
            release.setText("Release: "+movieList.get(position).getDate());
            ImageLoader.getInstance().displayImage("http://image.tmdb.org/t/p/w154" + movieList.get(position).getPoster(), imageicon, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {
                 progressbar.setVisibility(view.VISIBLE);
                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

                    progressbar.setVisibility(view.GONE);
                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    progressbar.setVisibility(view.GONE);
                }

                @Override
                public void onLoadingCancelled(String imageUri, View view) {
                    progressbar.setVisibility(view.GONE);
                }
            });
            return convertView;
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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



            ConnectivityManager cm =
                    (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            boolean isConnected = activeNetwork != null &&
                    activeNetwork.isConnectedOrConnecting();

            if(isConnected)
            {
                new JSONTask().execute("http://api.themoviedb.org/3/movie/popular?api_key=00253a9bc19586a5c5882f8d9877e6a5");
            }
            else
            {


                Toast.makeText(MainActivity.this, "Connect to INTERNET :)", Toast.LENGTH_SHORT).show();

                //  connectivity_check();


            }

        }


        if(id==R.id.action_view)
        {

            ConnectivityManager cm =
                    (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            boolean isConnected = activeNetwork != null &&
                    activeNetwork.isConnectedOrConnecting();

            if(isConnected)
            {
                Intent intent = new Intent(MainActivity.this,JsonView.class);
                Bundle b = new Bundle();
                b.putString("pass",jsdata);
                intent.putExtras(b);
                startActivityForResult(intent, 0);
                return true;
            }
            else
            {


                Toast.makeText(MainActivity.this, "Connect to INTERNET :)", Toast.LENGTH_SHORT).show();




            }

        }



        return super.onOptionsItemSelected(item);
    }
}
