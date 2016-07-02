package com.example.holmesvinn.the_movie_directory;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatCallback;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

/**
 * Created by Holmes Vinn on 30-Jun-16.
 */
public class Individual_data extends Activity implements AppCompatCallback {



    private String poster,moviename,lang,overview,date;
    private int vote;
    private double average;
    private ProgressBar prog;
    private Toolbar toolbar;
    private AppCompatDelegate delegate;

    private ImageView image;
    public  TextView release,language,overviews,voteCounts,title;
    public RatingBar rating;

    public AppBarLayout appBarLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        delegate = AppCompatDelegate.create(this, this);
        delegate.onCreate(savedInstanceState);



        //we use the delegate to inflate the layout
        delegate.setContentView(R.layout.content_scroll);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        delegate.setSupportActionBar(toolbar);


       delegate.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        delegate.getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        toolbar.setTitle(null);



        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()

                .cacheInMemory(true)
                .cacheOnDisk(true)

                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())

                .defaultDisplayImageOptions(defaultOptions)

                .build();
        ImageLoader.getInstance().init(config);


         image = (ImageView)findViewById(R.id.single_image);
        release = (TextView)findViewById(R.id.single_Release);
         language= (TextView)findViewById(R.id.single_language);
         overviews = (TextView)findViewById(R.id.single_Overview);
         voteCounts = (TextView)findViewById(R.id.single_Vote_Counts);

        prog = (ProgressBar)findViewById(R.id.single_progress);
        rating = (RatingBar)findViewById(R.id.ratingBar);
        appBarLayout = (AppBarLayout)findViewById(R.id.app_bar);




        Bundle b = getIntent().getExtras();
        poster = b.getString("posterb");
        moviename = b.getString("titleb");
        lang = b.getString("languageb");
        overview = b.getString("overviewb");
        date = b.getString("dateb");
        vote = b.getInt("voteb");
        average = b.getDouble("averageb");


        displaydata();

    }

    private void displaydata() {


        toolbar.setTitle(String.valueOf(moviename));
        release.setText("Release Date: \n"+String.valueOf(date));
        language.setText("Language: \n"+String.valueOf (lang));
        overviews.setText("OverView: \n"+String.valueOf(overview));
        voteCounts.setText("Voting Counts: "+String.valueOf(vote));
        rating.setRating((float) (average/2));

        ImageLoader.getInstance().displayImage("http://image.tmdb.org/t/p/w780"+poster, image, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                prog.setVisibility(view.VISIBLE);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

                prog.setVisibility(view.GONE);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                prog.setVisibility(view.GONE);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                prog.setVisibility(view.GONE);
            }
        });
    }

    @Override
    public void onSupportActionModeStarted(ActionMode mode) {

    }

    @Override
    public void onSupportActionModeFinished(ActionMode mode) {

    }

    @Nullable
    @Override
    public ActionMode onWindowStartingSupportActionMode(ActionMode.Callback callback) {
        return null;
    }
}