package com.mihirathwa.cse546.eventson_the_go;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.mihirathwa.cse546.events_backend.facebookGraphApi.FacebookGraphApi;
import com.mihirathwa.cse546.events_backend.facebookGraphApi.model.FacebookGraph;

import java.io.IOException;

/**
 * Created by Mihir on 05/03/2017.
 */

public class FacebookGraphEndpoint extends AsyncTask<FacebookGraph, Integer, Void> {

    private static final String TAG = "FacebookGraphEndpoint";
    private static FacebookGraphApi facebookGraphApi = null;
    private Context context;

    public FacebookGraphEndpoint(Context context) {
        this.context = context;
    }

    @Override
    protected Void doInBackground(FacebookGraph... facebookGraphs) {
        String rootUrl = context.getString(R.string.appengine_url);

        if (facebookGraphApi == null) {
            FacebookGraphApi.Builder builder = new FacebookGraphApi
                    .Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    .setRootUrl(rootUrl);

            facebookGraphApi = builder.build();
        }

        int count = facebookGraphs.length;

        for (int i = 0; i < count; i++) {
            try {
                facebookGraphApi.insert(facebookGraphs[i]).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        Log.w(TAG, "Inserted Values");
    }
}
