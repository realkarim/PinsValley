package com.realkarim.apps.xfetcher;

import android.graphics.Bitmap;

/**
 * Created by Karim Mostafa on 1/25/17.
 */

public abstract class BaseFetcher<T>{

    public void fetchFromURL(String url){

    }

    protected abstract void onRawResponse(Byte[] raw);

    public abstract void onResponse(T response);

    public abstract void onError(String error);

}
