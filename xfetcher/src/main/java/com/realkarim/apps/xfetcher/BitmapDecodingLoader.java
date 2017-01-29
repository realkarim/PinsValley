package com.realkarim.apps.xfetcher;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.util.LruCache;

import java.io.InputStream;

/**
 * Created by Karim Mostafa on 1/28/17.
 */

public class BitmapDecodingLoader extends AsyncTaskLoader<Bitmap> {
    private InputStream inputStream;
    private Bitmap bitmap;



    public BitmapDecodingLoader(Context context, InputStream inputStream) {
        super(context);
        this.inputStream = inputStream;
    }

    @Override
    public Bitmap loadInBackground() {
        bitmap = BitmapFactory.decodeStream(inputStream);
        return bitmap;
    }
}
