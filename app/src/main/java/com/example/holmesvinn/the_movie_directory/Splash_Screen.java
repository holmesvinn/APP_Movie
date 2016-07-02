package com.example.holmesvinn.the_movie_directory;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

/**
 * Created by Holmes Vinn on 01-Jul-16.
 */
public class Splash_Screen extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        final ImageView imageView = (ImageView)findViewById(R.id.imageView2);
        final Animation animation = AnimationUtils.loadAnimation(getBaseContext(),R.anim.rotate);
        final Animation animation1 = AnimationUtils.loadAnimation(getBaseContext(),R.anim.abc_fade_out);
        imageView.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                imageView.startAnimation(animation1);
                finish();
                Intent intetnt = new Intent(getBaseContext(),MainActivity.class);
                startActivity(intetnt);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
