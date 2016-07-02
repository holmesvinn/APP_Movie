package com.example.holmesvinn.the_movie_directory;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by Holmes Vinn on 01-Jul-16.
 */
public class JsonView extends Activity {

    private String json;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.json_view);

      Bundle b = getIntent().getExtras();
        json = b.getString("pass");
        textView = (TextView)findViewById(R.id.json_text);
        textView.setText("\n "+String.valueOf(json));





    }
}
