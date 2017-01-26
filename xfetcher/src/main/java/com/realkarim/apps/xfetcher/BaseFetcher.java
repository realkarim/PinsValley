package com.realkarim.apps.xfetcher;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.InputStream;

/**
 * Created by Karim Mostafa on 1/25/17.
 */

public abstract class BaseFetcher<T> implements LoaderManager.LoaderCallbacks<InputStream> {

    Context context;
    Handler handler = new Handler(Looper.getMainLooper());

    public BaseFetcher(Context context) {
        this.context = context;
    }

    public void fetchFromURL(String url) {
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        ((Activity) context).getLoaderManager().initLoader(url.hashCode(), bundle, this).forceLoad();


    }

    protected abstract void onRawResponse(InputStream inputStream);

    public abstract void onResponse(T response);

    public abstract void onError(String error);

    @Override
    public Loader<InputStream> onCreateLoader(int id, Bundle args) {
        return new WorkerLoader(context, args.getString("url"));
    }

    @Override
    public void onLoadFinished(Loader<InputStream> loader,final InputStream data) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                onRawResponse(data);    // Fire the callback on the main thread.
            }
        });
    }

    @Override
    public void onLoaderReset(Loader<InputStream> loader) {

    }
}
