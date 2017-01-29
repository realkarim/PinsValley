package com.realkarim.apps.xfetcher;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * Created by Karim Mostafa on 1/25/17.
 */

abstract class BaseFetcher<T> {

    protected Context context;

    private HandlerThread mHandlerThread;
    protected Handler handler;

    //    DependenciesProvider dependenciesProvider;
    static int loaderID = 0;

    public BaseFetcher(Context context) {
        this.context = context;

        mHandlerThread = new HandlerThread("Converter");
        mHandlerThread.start();
        handler = new Handler(mHandlerThread.getLooper());
//        dependenciesProvider = new DependenciesProvider();
//        DaggerBuilder.buildDagger().inject(dependenciesProvider);

    }

    public synchronized void fetchFromURL(String url) {
        if (CacheFactory.isCached(url)) {
            byte[] cached = CacheFactory.getFromCache(url);
            CacheFactory.removeFromCache(url);
            CacheFactory.cache(url, cached); // update key value priority in the LinkedHashMap
            onRawResponse(new ByteArrayInputStream(cached));
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString("url", url);

        ((Activity) context).getLoaderManager().initLoader(url.hashCode() + loaderID++, bundle, loaderCallbacks).forceLoad();
    }

    protected abstract void onRawResponse(InputStream inputStream);

    public abstract void onResponse(T response);

    public abstract void onError(String error);

    private LoaderManager.LoaderCallbacks<byte[]> loaderCallbacks = new LoaderManager.LoaderCallbacks<byte[]>() {
        @Override
        public Loader<byte[]> onCreateLoader(int id, Bundle args) {
            return new WorkerLoader(context, args.getString("url"));
        }

        @Override
        public void onLoadFinished(Loader<byte[]> loader, byte[] data) {
            String url = ((WorkerLoader) loader).getUrl();
            CacheFactory.cache(url, data.clone());

            onRawResponse(new ByteArrayInputStream(data.clone()));
        }

        @Override
        public void onLoaderReset(Loader<byte[]> loader) {

        }
    };
}
