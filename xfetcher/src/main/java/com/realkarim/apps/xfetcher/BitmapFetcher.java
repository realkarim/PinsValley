package com.realkarim.apps.xfetcher;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Karim Mostafa on 1/27/17.
 */

public abstract class BitmapFetcher extends BaseFetcher<Bitmap> {

//    String TAG = StringFetcher.class.getName();

    public BitmapFetcher(Context context) {
        super(context);
    }

    @Override
    protected void onRawResponse(InputStream inputStream) {
        try {
            inputStream.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        if (bitmap != null) {
            onResponse(bitmap);
        } else
            onError("Bitmap is null!");
    }


    @Override
    public abstract void onResponse(Bitmap response);


    @Override
    public abstract void onError(String error);
}
