package com.realkarim.apps.xfetcher;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by Karim Mostafa on 1/26/17.
 */

class WorkerLoader extends AsyncTaskLoader<byte[]> {

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
    public byte[] loadInBackground() {
        URL url = null;
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        try {
            url = new URL(getUrl());

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Return the input stream
            InputStream inputStream = urlConnection.getInputStream();
            if (inputStream == null)
                return new byte[0];

            return toByteArray(inputStream);

        } catch (MalformedURLException e) {
            Log.e(logTag, "MalformedURLException: " + e.getMessage());
        } catch (ProtocolException e) {
            Log.e(logTag, "ProtocolException: " + e.getMessage());
        } catch (IOException e) {
            Log.e(logTag, "IOException: " + e.getMessage());
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(logTag, "IOException: " + e.getMessage());
                }
            }
        }
        return new byte[0];
    }

    @Override
    protected void onStopLoading() {
        super.onStopLoading();
        cancelLoad();
    }

    public static byte[] toByteArray(InputStream is) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            byte[] b = new byte[1024];
            int n = 0;
            while ((n = is.read(b)) != -1) {
                output.write(b, 0, n);
            }
            return output.toByteArray();
        } finally {
            output.close();
        }
    }

}
