package com.realkarim.apps.xfetcher;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by Karim Mostafa on 1/26/17.
 */

class WorkerLoader extends AsyncTaskLoader<InputStream> {

    String logTag = this.getClass().getName();
    String url;

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }


    public WorkerLoader(Context context, String url) {
        super(context);
        setUrl(url);
    }

    @Override
    public InputStream loadInBackground() {
        URL url = null;
        HttpURLConnection urlConnection = null;
        try {
            url = new URL(getUrl());

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Return the input stream
            InputStream inputStream = urlConnection.getInputStream();

            // Clone input stream
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = inputStream.read(buffer)) > -1 ) {
                baos.write(buffer, 0, len);
            }
            baos.flush();

            InputStream clonnedInputStream = new ByteArrayInputStream(baos.toByteArray());


            return clonnedInputStream;

        } catch (MalformedURLException e) {
            Log.e(logTag, "MalformedURLException: " + e.getMessage());
        } catch (ProtocolException e) {
            Log.e(logTag, "ProtocolException: " + e.getMessage());
        } catch (IOException e) {
            Log.e(logTag, "IOException: " + e.getMessage());
        }
        return null;
    }

    @Override
    protected void onStopLoading() {
        super.onStopLoading();
        cancelLoad();
    }

}
