package com.realkarim.apps.xfetcher;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Karim Mostafa on 1/27/17.
 */

public abstract class StringFetcher extends BaseFetcher<String> {

    String TAG = StringFetcher.class.getName();

    public StringFetcher(Context context) {
        super(context);
    }

    @Override
    protected void onRawResponse(InputStream inputStream) {
        try {
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                onResponse("");
                return;
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }
            onResponse(buffer.toString());

        } catch (IOException e) {
            onError("IOException: " + e.getMessage());
            Log.e(TAG, "IOException: " + e.getMessage());
        }
    }

    @Override
    public abstract void onResponse(String response);


    @Override
    public abstract void onError(String error);

    public JSONObject toJsonObject(String string){
        try {
            return new JSONObject(string);
        } catch (JSONException e) {
            Log.e(TAG, "JSONException: " + e.getMessage());
        }

        return new JSONObject();
    }

    public JSONArray toJsonArray(String string){
        try {
            return new JSONArray(string);
        } catch (JSONException e) {
            Log.e(TAG, "JSONException: " + e.getMessage());
        }

        return new JSONArray();
    }


}
