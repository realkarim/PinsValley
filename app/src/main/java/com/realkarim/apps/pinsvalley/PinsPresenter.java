package com.realkarim.apps.pinsvalley;

import android.content.Context;

import com.google.gson.Gson;
import com.realkarim.apps.pinsvalley.models.Pin;
import com.realkarim.apps.pinsvalley.models.Urls;
import com.realkarim.apps.xfetcher.StringFetcher;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by Karim Mostafa on 1/28/17.
 */

public class PinsPresenter implements PinsContract.Presenter {

    Context context;
    PinsContract.View view;
    String IMAGE_URL;

    PinsPresenter(Context context, PinsContract.View view) {
        this.context = context;
        this.view = view;
        IMAGE_URL = context.getResources().getString(R.string.pins_url);
    }

    @Override
    public void updateList() {
        StringFetcher stringFetcher = new StringFetcher(context) {
            @Override
            public void onResponse(String response) {
                ArrayList<Pin> pins = new ArrayList<>();

                try {
                    Gson gson = new Gson();
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i=0;i<jsonArray.length();i++) {
                        Pin pin = gson.fromJson(jsonArray.getJSONObject(i).toString(), Pin.class);
                        pins.add(pin);
                    }
                } catch (JSONException e) {
                    onError("JSONException: " + e.getMessage());
                } finally {
                    view.receiveListUpdate(pins);
                }
            }


            @Override
            public void onError(String error) {
                    view.showMessage("Error while fetching images: " + error);
            }
        };

        stringFetcher.fetchFromURL(IMAGE_URL);
    }
}
