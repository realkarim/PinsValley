package com.realkarim.apps.xfetcher;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;

import java.io.InputStream;

import dagger.DaggerBuilder;
import dagger.DependenciesProvider;

/**
 * Created by Karim Mostafa on 1/25/17.
 */

abstract class BaseFetcher<T> implements LoaderManager.LoaderCallbacks<InputStream> {

    Context context;

    DependenciesProvider dependenciesProvider;

    public BaseFetcher(Context context) {
        this.context = context;
        dependenciesProvider = new DependenciesProvider();
        DaggerBuilder.buildDagger().inject(dependenciesProvider);
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
        dependenciesProvider.getHandlerWithMainLooper().post(new Runnable() {
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
