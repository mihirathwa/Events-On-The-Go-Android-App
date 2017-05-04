package com.mihirathwa.cse546.eventson_the_go;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by Mihir on 05/04/2017.
 */
public class PopUpActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.popup_window);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        getWindow().setLayout((int)(width*.8), (int)(height*.8));

        ImageView cover = (ImageView) findViewById(R.id.PW_titleImage);
        TextView name = (TextView) findViewById(R.id.PW_name);
        TextView category = (TextView) findViewById(R.id.PW_name);
        TextView startDate = (TextView) findViewById(R.id.PW_name);
        TextView description = (TextView) findViewById(R.id.PW_name);
        TextView tweets = (TextView) findViewById(R.id.PW_name);

        Intent intent = getIntent();

//        if (intent.getStringExtra("startDate") != null) {
//            startDate.setText("Start Date: " + intent.getStringExtra("startDate"));
//        }

        name.setText("Name: " + intent.getStringExtra("name"));
        category.setText("Category: " + intent.getStringExtra("category"));
        description.setText("Description: " + intent.getStringExtra("description"));

        String coverUrl = intent.getStringExtra("coverUrl");

        Picasso.with(this).load(coverUrl).into(cover);
    }
}
