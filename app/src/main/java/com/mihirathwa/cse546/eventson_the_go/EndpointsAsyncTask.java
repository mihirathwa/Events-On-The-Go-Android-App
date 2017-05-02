package com.mihirathwa.cse546.eventson_the_go;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;


import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.mihirathwa.cse546.events_backend.quoteApi.model.Quote;
import com.mihirathwa.cse546.events_backend.quoteApi.QuoteApi;


import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * Created by Mihir on 05/02/2017.
 */

public class EndpointsAsyncTask extends AsyncTask<Void, Void, List<Quote>> {
//    private static MyApi myApiService = null;
    private static QuoteApi myApiService = null;
    private Context context;

    EndpointsAsyncTask(Context context) {
        this.context = context;
    }


    @Override
    protected List<Quote> doInBackground(Void... params) {
//        if (myApiService == null) {
//            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
//                    new AndroidJsonFactory(), null)
//                    .setRootUrl("https://events-on-the-go.appspot.com/_ah/api/");
//
//            myApiService = builder.build();
//        }

        if (myApiService == null) {
           QuoteApi.Builder builder = new QuoteApi.Builder(AndroidHttp.newCompatibleTransport(),
                   new AndroidJsonFactory(), null)
                   .setRootUrl("https://events-on-the-go.appspot.com/_ah/api/");

            myApiService = builder.build();
        }

        try {
            return myApiService.list().execute().getItems();
        } catch (IOException e) {
            return Collections.EMPTY_LIST;
        }
    }

    @Override
    protected void onPostExecute(List<Quote> quotes) {
        for (Quote q : quotes) {
            Toast.makeText(context, q.getWho() + ": " + q.getWhat(), Toast.LENGTH_LONG).show();
        }
    }
}
