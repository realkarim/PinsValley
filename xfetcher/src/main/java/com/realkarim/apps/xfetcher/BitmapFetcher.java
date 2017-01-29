package com.realkarim.apps.xfetcher;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.support.v4.util.LruCache;
import android.util.Log;

import java.io.InputStream;

/**
 * Created by Karim Mostafa on 1/27/17.
 */

public abstract class BitmapFetcher extends BaseFetcher<Bitmap> {

    private String TAG = StringFetcher.class.getName();
    private LoaderManager.LoaderCallbacks<Bitmap> loaderCallbacks;

    private static int cacheSize = 4 * 1024 * 1024; // 4MiB
    private static LruCache<Integer, Bitmap> mMemoryCache = new LruCache<Integer, Bitmap>(cacheSize) {
        protected int sizeOf(Integer key, Bitmap value) {
            return value.getByteCount();
        }
    };

    static int loaderID = 0;

    public BitmapFetcher(Context context) {
        super(context);
    }

    @Override
    protected void onRawResponse(final InputStream inputStream) {
/*
        loaderCallbacks = new LoaderManager.LoaderCallbacks<Bitmap>() {
            @Override
            public Loader<Bitmap> onCreateLoader(int id, Bundle args) {
                return new BitmapDecodingLoader(context, inputStream);
            }

            @Override
            public void onLoadFinished(Loader<Bitmap> loader, Bitmap bitmap) {
                if (bitmap != null) {
                    addBitmapToMemoryCache(inputStream.hashCode(), bitmap);
                    onResponse(bitmap);
                } else {
                    onError("Bitmap is null!");
                    Log.e(TAG, "Bitmap is null!");
                }
            }

            @Override
            public void onLoaderReset(Loader<Bitmap> loader) {

            }
        };

        if(getBitmapFromMemCache(inputStream.hashCode())==null)
            ((Activity) context).getLoaderManager().initLoader(inputStream.hashCode() + loaderID++, null, loaderCallbacks).forceLoad();
        else{
            Bitmap bitmap = getBitmapFromMemCache(inputStream.hashCode());
            onResponse(bitmap);
        }*/
        final Handler mainHandler = new Handler(Looper.getMainLooper());

        handler.post(new Runnable() {
            @Override
            public void run() {
                final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (bitmap != null) {
                            onResponse(bitmap);
                        } else {
                            onError("Bitmap is null!");
                            Log.e(TAG, "Bitmap is null!");
                        }
                    }
                });
            }
        });


    }


    @Override
    public abstract void onResponse(Bitmap response);


    @Override
    public abstract void onError(String error);


    public void addBitmapToMemoryCache(Integer key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }

    public Bitmap getBitmapFromMemCache(Integer key) {
        return mMemoryCache.get(key);
    }
}
