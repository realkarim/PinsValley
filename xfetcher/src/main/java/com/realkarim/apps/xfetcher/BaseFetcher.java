package com.realkarim.apps.xfetcher;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
    static int loaderID = 0;

    public BaseFetcher(Context context) {
        this.context = context;
//        dependenciesProvider = new DependenciesProvider();
//        DaggerBuilder.buildDagger().inject(dependenciesProvider);

    }

    public synchronized void fetchFromURL(String url) {
        if(CacheFactory.cacheContainer.containsKey(url)){
            byte[] cached = CacheFactory.cacheContainer.get(url);
            CacheFactory.removeFromCache(url);
            CacheFactory.cache(url, cached); // update key value priority in the LinkedHashMap
            onRawResponse(new ByteArrayInputStream(cached));
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString("url", url);

        ((Activity) context).getLoaderManager().initLoader(url.hashCode()+loaderID++, bundle, loaderCallbacks).forceLoad();
    }

    protected abstract void onRawResponse(InputStream inputStream);

    public abstract void onResponse(T response);

    public abstract void onError(String error);

    LoaderManager.LoaderCallbacks<byte[]> loaderCallbacks = new LoaderManager.LoaderCallbacks<byte[]>() {
        @Override
        public Loader<byte[]> onCreateLoader(int id, Bundle args) {
            return new WorkerLoader(context, args.getString("url"));
        }

        @Override
        public void onLoadFinished(Loader<byte[]> loader, byte[] data) {
            String url = ((WorkerLoader)loader).getUrl();
            CacheFactory.cache(url, data.clone());

            onRawResponse(new ByteArrayInputStream(data.clone()));
        }

        @Override
        public void onLoaderReset(Loader<byte[]> loader) {

        }
    };
}
