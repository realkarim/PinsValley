package com.realkarim.apps.xfetcher;

import java.io.InputStream;

/**
 * Created by Karim Mostafa on 1/25/17.
 */

public abstract class BaseFetcher<T>{

    public void fetchFromURL(String url){

    }

    protected abstract void onRawResponse(InputStream inputStream);

    public abstract void onResponse(T response);

    public abstract void onError(String error);

}
