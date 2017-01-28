package com.realkarim.apps.xfetcher;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by Karim Mostafa on 1/25/17.
 */

abstract class BaseFetcher<T>{

    Context context;

//    DependenciesProvider dependenciesProvider;

    static LinkedHashMap<String, byte[]> cacheContainer = new LinkedHashMap<>();
    int MAX_CACHE_SIZE = 50;

    public BaseFetcher(Context context) {
        this.context = context;
//        dependenciesProvider = new DependenciesProvider();
//        DaggerBuilder.buildDagger().inject(dependenciesProvider);

    }

    public void fetchFromURL(String url) {
        if(cacheContainer.containsKey(url)){
            byte[] cached = cacheContainer.get(url);
            removeFromCache(url);
            cache(url, cached); // update key value priority in the LinkedHashMap
            onRawResponse(new ByteArrayInputStream(cached));
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        ((Activity) context).getLoaderManager().initLoader(url.hashCode(), bundle, loaderCallbacks).forceLoad();
    }

    protected abstract void onRawResponse(InputStream inputStream);

    public abstract void onResponse(T response);

    public abstract void onError(String error);

    void cache(String url, byte[] bytes){
        if(cacheContainer.size() == MAX_CACHE_SIZE){    // check if cache size reached the maximum
            String key = cacheContainer.keySet().iterator().next(); // get first key inserted
            removeFromCache(key); // remove it
        }
        cacheContainer.put(url, bytes);
    }

    void removeFromCache(String key){
        cacheContainer.remove(key);
    }

    LoaderManager.LoaderCallbacks<byte[]> loaderCallbacks = new LoaderManager.LoaderCallbacks<byte[]>() {
        @Override
        public Loader<byte[]> onCreateLoader(int id, Bundle args) {
            return new WorkerLoader(context, args.getString("url"));
        }

        @Override
        public void onLoadFinished(Loader<byte[]> loader, byte[] data) {
            String url = ((WorkerLoader)loader).getUrl();
            cache(url, data.clone());

            onRawResponse(new ByteArrayInputStream(data.clone()));
        }

        @Override
        public void onLoaderReset(Loader<byte[]> loader) {

        }
    };
}
