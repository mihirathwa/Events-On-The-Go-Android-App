package com.mihirathwa.cse546.eventson_the_go;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class QuoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quote);
    }

    public void getQuotes(View view) {
        new EndpointsAsyncTask(this).execute();
    }
}
